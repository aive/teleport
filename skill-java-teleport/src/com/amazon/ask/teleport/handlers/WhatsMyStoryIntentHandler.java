package com.amazon.ask.teleport.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class WhatsMyStoryIntentHandler implements RequestHandler {
    public static final String STORY_KEY = "STORY";
    public static final String STORY_SLOT = "Story";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("WhatsMyStoryIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;
        String favoriteStory = (String) input.getAttributesManager().getSessionAttributes().get(STORY_KEY);

        if (favoriteStory != null && !favoriteStory.isEmpty()) {
            speechText = String.format("Your selected story is \"%s\". If you want to hear it say \"Teleport me!\"", favoriteStory);
            if (favoriteStory.equals("kelpie")||favoriteStory.equals("kelpie story") ) {
                // then play the Kelpie story
                input.getAttributesManager().getSessionAttributes().put("story",0);
                input.getAttributesManager().getSessionAttributes().put("chapter",-1);
            } else {
              //play the Little story
              input.getAttributesManager().getSessionAttributes().put("story",1);
              input.getAttributesManager().getSessionAttributes().put("chapter",-1);
            }
        } else {
            // Since the user's favorite story is not set render an error message.
            speechText =
                    "I'm not sure what your story is. You can say, my story is "
                            + "\"Kelpie\"" + "or my story is" + "\"Little Story\"";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Teleport", speechText)
                .withShouldEndSession(false)
                .build();
    }
}
