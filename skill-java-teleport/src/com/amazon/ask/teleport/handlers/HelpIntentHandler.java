package com.amazon.ask.teleport.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.display.*;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static com.amazon.ask.request.Predicates.intentName;

public class HelpIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Image image = getImage("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/background5.png");
        String title = "Teleport";
        String speechText = "I can tell you stories. Say  Tell me a story to get to select one. For Kelpie story say Kelpie. For Little story say Little Story";
        String writtenText = "<font size=\"7\">   I can tell you stories. Say \"Tell me a story\" to get to select one. For Kelpie story say \"Kelpie\". For Little story say \"Little Story\". </font>";
        String primaryText = writtenText;
        String secondaryText = "";
        Template template = getBodyTemplate1(title, primaryText, secondaryText, image);

        // Device supports display interface
        if(null!=input.getRequestEnvelope().getContext().getDisplay()) {
            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard(title, primaryText)
                    .addRenderTemplateDirective(template)
                    .withReprompt(speechText)
                    .build();
        } else {
            // Headless device
            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard(title, primaryText)
                    .withReprompt(speechText)
                    .build();
        }
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
 * Helper method to create the image object for display interfaces
 * @param imageUrl the url of the image
 * @return Image that is used in a body template
 */
private Image getImage(String imageUrl) {
    List<ImageInstance> instances = getImageInstance(imageUrl);
    return Image.builder()
            .withSources(instances)
            .build();
}

    /**
     * Helper method to create a body template 6
     * @param title the title to be displayed on the template
     * @param primaryText the primary text to be displayed on the template
     * @param secondaryText the secondary text to be displayed on the template
     * @param image  the url of the image
     * @return Template
     */
     // template 6 has no title
    private Template getBodyTemplate1(String title, String primaryText, String secondaryText, Image image) {
        return BodyTemplate1.builder()
                .withBackgroundImage(image)
                //hide the backbutton
                .withBackButton(BackButtonBehavior.HIDDEN)
                .withTitle(title)
                .withTextContent(getTextContent(primaryText, secondaryText))
                .build();
    }

    /**
     * Helper method that returns text content to be used in the body template.
     * @param primaryText
     * @param secondaryText
     * @return RichText that will be rendered with the body template
     */
    private TextContent getTextContent(String primaryText, String secondaryText) {
        return TextContent.builder()
                .withPrimaryText(makeRichText(primaryText))
                .withSecondaryText(makeRichText(secondaryText))
                .build();
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