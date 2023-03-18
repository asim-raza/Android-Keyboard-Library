package com.contactbackup.cloud.keyboard.keyboard;


import com.contactbackup.cloud.keyboard.R;

public class RawWrapper {
    public static int getRaw(String filename) {
        int i = -1;
        int raw = -1;
        switch (filename) {
            case "android.mp3":
                raw = R.raw.android;
                break;
            case "samsung.mp3":
                raw = R.raw.samsung;
                break;
            case "iphone.mp3":
                raw = R.raw.iphone;
                break;
            case "water.mp3":
                raw = R.raw.water;
                break;
            case "wood.mp3":
                raw = R.raw.wood;
                break;
            case "hard.mp3":
                raw = R.raw.hard;
                break;
            case "typewriter.mp3":
                raw = R.raw.typewriter;
                break;
        }
        return raw;
//
//        if (filename == null) {
//            return -1;
//        }
//        switch (filename.hashCode()) {
//            case -215289447:
//                if (filename.equals("water.mp3")) {
//                    i = 3;
//                    break;
//                }
//                break;
//            case -189215705:
//                if (filename.equals("iphone.mp3")) {
//                    i = 2;
//                    break;
//                }
//                break;
//            case -58640337:
//                if (filename.equals("wood.mp3")) {
//                    i = 4;
//                    break;
//                }
//                break;
//            case 114746221:
//                if (filename.equals("hard.mp3")) {
//                    i = 5;
//                    break;
//                }
//                break;
//            case 190976431:
//                if (filename.equals("typewriter.mp3")) {
//                    i = 6;
//                    break;
//                }
//                break;
//            case 747291388:
//                if (filename.equals("samsung.mp3")) {
//                    i = 1;
//                    break;
//                }
//                break;
//            case 936376049:
//                if (filename.equals("android.mp3")) {
//                    i = 0;
//                    break;
//                }
//                break;
//        }
//        switch (i) {
//            case 0:
//                raw = R.raw.android;
//                break;
//            case 1:
//                raw = R.raw.samsung;
//                break;
//            case 2:
//                raw = R.raw.iphone;
//                break;
//            case 3:
//                raw = R.raw.water;
//                break;
//            case 4:
//                raw = R.raw.wood;
//                break;
//            case 5:
//                raw = R.raw.hard;
//                break;
//            case 6:
//                raw = R.raw.typewriter;
//                break;
//        }
//        return raw;
    }
}
