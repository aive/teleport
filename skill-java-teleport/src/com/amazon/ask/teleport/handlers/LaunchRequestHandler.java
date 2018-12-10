package com.amazon.ask.teleport.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.display.*;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;
import static com.amazon.ask.request.Predicates.intentName;

public class LaunchRequestHandler implements RequestHandler {

  //@Override
  //public boolean canHandle(HandlerInput input) {
  //    return input.matches(intentName("TeleportIntent").or(intentName("MyStoryIsIntent")));
      //return input.matches(intentName("TeleportIntent").or(intentName("AMAZON.YesIntent").or(intentName("AMAZON.NextIntent"))));
  //}


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class).or(intentName("MyStorySelectionIntent")));


    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String title = "Teleport";
        String primaryText = "Kelpie";
        String secondaryText = "Little Story";
        String speechText = "Welcome to Teleport! I can tell you stories about mythical creatures. Would you like to hear a Kelpie story or a Little Story?";
        String imageUrl1 = "https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie_blue1024-x-600_s.png";
        String imageUrl2 = "https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/little_story_thumb.jpg";
        Image image1 = getImage(imageUrl1);
        Image image2 = getImage(imageUrl2);
        Image backgroundimage = getImage("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/background5.png");
        Template template = getListTemplate2(title, primaryText, secondaryText, image1, image2, backgroundimage );

        // Device supports display interface
        if(null!=input.getRequestEnvelope().getContext().getDisplay()) {
            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard(title, speechText)
                    .addRenderTemplateDirective(template)
                    .withReprompt(speechText)
                    .build();
        } else {
            //Headless device
            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard(title, speechText)
                    .withReprompt(speechText)
                    .build();
        }
    }

    /**
     * Helper method to create a list template 2
     * @param title the title to be displayed on the template
     * @param primaryText heading 1
     * @param secondaryText heading 2
     * @param image1  the url of the 1st image
     * @param image2  the url of the 2nd image
     * @return Template
     */
// todo getListItem, getTextContent, getPlainText helper methods
// takes a listof stories, not 2, title, list of headings, list of images, inside is a loop
    private Template getListTemplate2(String title, String primaryText, String secondaryText, Image image1, Image image2, Image backgroundimage) {
    //  private Template getBodyTemplate6(String primaryText, String secondaryText, Image image) {
        return ListTemplate2.builder()
                .addListItemsItem(ListItem.builder().withImage(image1).withTextContent(TextContent.builder().withPrimaryText(PlainText.builder().withText(primaryText).build()).build()).build())
                .addListItemsItem(ListItem.builder().withImage(image2).withTextContent(TextContent.builder().withPrimaryText(PlainText.builder().withText(secondaryText).build()).build()).build())
                .withBackgroundImage(backgroundimage)
                .withBackButton(BackButtonBehavior.HIDDEN)
                .withTitle("Teleport")
                .build();
    }

    /**
     * Helper method to create the image object for display interfaces
     * @param imageUrl1 the url of the 1st image
        * @param imageUrl2 the url of the 1st image
     * @return Image that is used in a body template
     */
    private Image getImage(String imageUrl) {
        List<ImageInstance> instances = getImageInstance(imageUrl);
        return Image.builder()
                .withSources(instances)
                .build();
    }

    /**
     * Helper method to create List of image instances
     * @param imageUrl the url of the image
     * @return instances that is used in the image object
     */
    private List<ImageInstance> getImageInstance(String imageUrl) {
        List<ImageInstance> instances = new ArrayList<>();
        ImageInstance instance = ImageInstance.builder()
                .withUrl(imageUrl)
                .build();
        instances.add(instance);
        return instances;
    }

    /**
     * Helper method that returns the rich text that can be set as the text content for a body template.
     * @param text The string that needs to be set as the text content for the body template.
     * @return RichText that will be rendered with the body template
     */
    private RichText makeRichText(String text) {
        return RichText.builder()
                .withText(text)
                .build();
    }

}
