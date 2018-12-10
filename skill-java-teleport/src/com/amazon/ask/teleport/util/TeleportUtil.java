package com.amazon.ask.teleport.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeleportUtil {

    public static Map getTeleportMap() {

        Map<String, List> textMap = new HashMap<>();
        textMap.put("Kelpie", getStory1Chapters());
        textMap.put("Nessie", getStory2Chapters());

        return textMap;
    }

    public static Map getImageMap() {
        Map<String, List> imageMap = new HashMap<>();
        imageMap.put("Kelpie", getStory1Images());
        imageMap.put("Nessie", getStory2Images());
        return imageMap;
    }

    public static List getKeys() {
        List<String> storyNames = new ArrayList<>();
        storyNames.add("Kelpie");
        storyNames.add("Nessie");
        return storyNames;
    }

/* CHAPTERS */

    // my List of chapters for Story1
    public static List getStory1Chapters() {
        List<String> chapters = new ArrayList<>();
        chapters.add("There is a dangerous creature that roams the lonely lochs and rivers of Scotland. A conniving beast. <break time=\"1s\"/> It lures its unsuspecting victims to the water only to mercilessly drown them.");
        chapters.add("The Kelpie does not like lying. It has the best ears for picking up lies. So beware. Do not lie! Or it will hunt you down, it will find you and it will kill you. A little girl, once made a mistake of telling a lie. ");
        chapters.add("A few days later as she was walking on the beach, she could hear someone trudging slowly behind her. She turned around. It was a fierce beast, with flaming eyes and seaweed stuck in its mane.");
        chapters.add("She started to run but the creature was gaining on her fast. I will catch you and I will drown you, she heard the Kelpie growl behind her! ");
        chapters.add("She spotted a large rock ahead and quickly crawled behind it. The Kelpie was panting angrily and kicking up pebbles, looking for her. The rotting smell of seaweed filled her nostrils as the Kelpie leaned forward. ");
        chapters.add("Suddenly a big black beast jumped out from behind the rock and licked her face with its enormous tongue.");
        chapters.add("Fluffy! It's you! I have missed you! <break time=\"1s\"/> Fluffy was her dog that went missing a week ago. Thank you for scaring away the Kelpie! You're my best friend! <break time=\"1s\"/> She gave Fluffy a big hug.");
        chapters.add(" The little girl never told a lie again. And they all drank lemonade! The end! <break time=\"1s\"/> ");
        //"<speak> " + primaryText + "<break time=\"1s\"/>  That's you then. If you want to hear another story say. Tell me another story. Otherwise say. Stop!" +  " </speak>";
        return chapters;
    }

    // my List of chapters for Story2
    public static List getStory2Chapters() {
        List<String> chapters = new ArrayList<>();
        chapters.add("Once upon the time, at the very beginning of times, there was a little story, roaming the primordial landscape of words. It was tiny and had a very small voice, more like a whisper.");
        chapters.add("The bigger stories were eating a lot of words and getting bigger and bigger. But the little story was too small and feeble to catch any words of its own. ");
        chapters.add("The bigger stories took pity on the little story, they lent it some of their words. The little story started to get bigger and bigger and sound better and better. ");
        chapters.add("The little story liked the way it sounded and looked. It fluffed its verbs, and groomed its similes and bursting with vanity spurted out. Look at me! I am the most beautiful, the most eloquent story in the whole wide world. I am much better than you all.");
        chapters.add("One by one the other stories walked up to the little story and plucked out the words and sentences they had lent it. There was no more little story, not even a whisper in the wind. Here’s what it sounded like. <break time=\"5s\"/>");
        return chapters;
    }


/*IMAGES*/

    // my List of images for Story1
    public static List getStory1Images() {
        List<String> images = new ArrayList<>();
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie/kelpie7.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie/kelpie7.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie/kelpie_walk.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie/kelpie_chase2.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie/kelpie_stone.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie/Dog_lick.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie/fluffy-hug.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/kelpie/theend.jpg");
        return images;
    }

    // my List of images for Story2
    public static List getStory2Images() {
        //Image img1 = "https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/pexels-photo-355288_water.jpeg";
        List<String> images = new ArrayList<>();
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/littlestory/little_story1.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/littlestory/little_story3.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/littlestory/little_story_give_word2.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/littlestory/little_story_mrword.jpg");
        images.add("https://s3-eu-west-1.amazonaws.com/tsinkplekkpang/littlestory/little_story_vanish.jpg");
        return images;
    }

}
