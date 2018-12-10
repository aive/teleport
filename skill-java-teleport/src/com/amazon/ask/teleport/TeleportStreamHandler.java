package com.amazon.ask.teleport;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.teleport.handlers.*;

public class TeleportStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new TeleportIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new WhatsMyStoryIntentHandler(),
                        new MyStoryIsIntentHandler(),
                        new FallBackIntentHandler())

                // skill id to enable skill ID verification
                .withSkillId("amzn1.ask.skill.d8ab5b44-7b20-4d9c-8bdd-5a71203dfd3d")
                .build();
    }

    public TeleportStreamHandler() {
        super(getSkill());
    }

}
