package com.amazon.ask.teleport.handlers;

import com.amazon.ask.teleport.util.TeleportUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.display.*;

import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;
// my comm https://developer.amazon.com/docs/custom-skills/display-interface-reference.html#supported-markup
public class TeleportIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        //return input.matches(intentName("TeleportIntent").or(intentName("MyStoryIsIntent")));
        return input.matches(intentName("TeleportIntent").or(intentName("AMAZON.YesIntent").or(intentName("AMAZON.NextIntent"))));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        // gets the session attributes from the attributes manager from the ether, to remember a story
        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();

        Map<String, List> stories = TeleportUtil.getTeleportMap();
        Map<String, List> images = TeleportUtil.getImageMap();
        List<String> keys = TeleportUtil.getKeys();
        String speechText;

        //FIND OUT IF A STORY HAS BEEN STARTED
        /*
        if user hasnt picked one, if it has, we want to know which story it is and what chapter it is.
         Tehn for example if it is chapter 3 we want to move to chapter 4
        */

        // declare variables
        Image image;
        String primaryText;
        String title;

        if (sessionAttributes.get("story") != null && !sessionAttributes.get("story").equals(-1)) {
            //get story and chapter
            int currentStory = (Integer) sessionAttributes.get("story");
            int currentChapter = (Integer) sessionAttributes.get("chapter") + 1;
            String key = keys.get(currentStory);
            title = keys.get(currentStory);
            // get the 0th element of the Chapters list , downcasting object into string
            primaryText = (String) stories.get(key).get(currentChapter);
            // get the 0th element of the images list , downcasting object into string
            String imageUrl = (String) images.get(key).get(currentChapter);
            image = getImage(imageUrl);

            // how many chapters are in our current story, check if we are on the last one
            if (currentChapter + 1 < stories.get(key).size() ) {
                //detect if it is the last chapter and then break out of if () {
                sessionAttributes.put("chapter", currentChapter);
                speechText = "<speak> " + primaryText + "<break time=\"1s\"/>  Do you want to hear more?" + " </speak>";
            }

            else{
              sessionAttributes.put("story",-1);
              sessionAttributes.put("chapter",-1);
              //set some string to
              speechText = "<speak> " + primaryText + "<break time=\"1s\"/>  That's you then. If you want to hear another story say. Tell me another story. Otherwise say. Stop!" +  " </speak>";

            }

        }

        else {
              primaryText = "Oh pumpkin! Try saying Tell me a story!";
              speechText = "<speak> " + primaryText + "<break time=\"1s\"/>  " +  " </speak>";

              title = "Oops, something went wrong. Try saying: Tell me a story!";

              String imageUrl = "https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie_rain_background.jpg";
              image = getImage(imageUrl);

        }

        String sloganText = "";
        // changes text size
        String writtenText = "<font size=\"6\"> " + sloganText + " </font>";
        String secondaryText = "";
        Template template = getBodyTemplate6(title, writtenText, secondaryText, image);

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
     * Helper method to create a body template 6
     * @param title the title to be displayed on the template
     * @param primaryText the primary text to be displayed on the template
     * @param secondaryText the secondary text to be displayed on the template
     * @param image  the url of the image
     * @return Template
     */
     // template 6 has no title
    private Template getBodyTemplate6(String title, String primaryText, String secondaryText, Image image) {
        return BodyTemplate6.builder()
                .withBackgroundImage(image)
                //hide the backbutton
                .withBackButton(BackButtonBehavior.HIDDEN)
                .withTextContent(getTextContent(primaryText, secondaryText))
                .build();
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
