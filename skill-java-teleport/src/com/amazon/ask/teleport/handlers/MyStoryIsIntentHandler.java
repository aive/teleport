/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.amazon.ask.teleport.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.response.ResponseBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.teleport.handlers.WhatsMyStoryIntentHandler.STORY_KEY;
import static com.amazon.ask.teleport.handlers.WhatsMyStoryIntentHandler.STORY_SLOT;
import static com.amazon.ask.request.Predicates.intentName;

public class MyStoryIsIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("MyStoryIsIntent"));

    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        // Get the story slot from the list of slots.
        Slot favoriteStorySlot = slots.get(STORY_SLOT);

        String speechText, repromptText;
        boolean isAskResponse = false;

        // Check for favorite story and create output to user.
        if (favoriteStorySlot != null) {
            // Store the user's favorite story in the Session and create response.
            String favoriteStory = favoriteStorySlot.getValue();
            input.getAttributesManager().setSessionAttributes(Collections.singletonMap(STORY_KEY, favoriteStory));

            speechText =
                    //String.format("I now know that your favorite story is %s. You can ask me your "
                    String.format("I now know the story you want to hear. You can ask me your "
                            + "story by saying, \"What's my story?\"", favoriteStory);

            repromptText =
                    "You can ask me your story by saying, what's my story?";

        } else {
            // Render an error since we don't know what the users favorite story is.
            speechText = "I'm not sure what your story is, please try again";
            repromptText =
                    "I'm not sure what your story is. You can choose your  "
                            + "story by saying, \"I want to hear Kelpie story.\" or \"I want to hear Little Story.\"";
            isAskResponse = true;
        }

        ResponseBuilder responseBuilder = input.getResponseBuilder();
        responseBuilder.withSimpleCard("Teleport", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false);
        if (isAskResponse) {
            responseBuilder.withShouldEndSession(false)
                    .withReprompt(repromptText);
        }
        return responseBuilder.build();
    }

}
