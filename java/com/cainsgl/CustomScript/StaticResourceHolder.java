package com.cainsgl.CustomScript;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;

public class StaticResourceHolder implements Serializable
{
    public static Image[] netImages = new Image[7];
    public static ArrayList<Image[]> fishAnimationS = new ArrayList<>();
    public static ArrayList<Image[]> fishCatchAnimation = new ArrayList<>();
    public static double markS = 0;
    public static String MapName = null;
    public static double WinTargetMark=150000;
    static
    {
        for (int i = 0; i < 7; i++)
        {
            netImages[i] = new Image(boom.class.getResourceAsStream("/image/net_" + (i + 1) + ".png"));
        }
        for (int j = 1; j < 10; j++)
        {
            Image[] images = new Image[10];
            Image[] catchImages = new Image[2];
            for (int i = 1; i <= 9; i++)
            {
                images[i - 1] = new Image(RandomFish.class.getResourceAsStream("/image/fish0" + j + "_0" + i + ".png"));
                if (i <= 2)
                {
                    catchImages[i - 1] = new Image(RandomFish.class.getResourceAsStream("/image/fish0" + j + "_catch_0" + i + ".png"));
                }
            }
            images[9] = new Image(RandomFish.class.getResourceAsStream("/image/fish0" + j + "_10.png"));
            fishAnimationS.add(images);
            fishCatchAnimation.add(catchImages);
        }
        for (int j = 13; j <= 14; j++)
        {
            Image[] images = new Image[10];
            Image[] catchImages = new Image[2];
            for (int i = 1; i <= 9; i++)
            {
                images[i - 1] = new Image(RandomFish.class.getResourceAsStream("/image/fish" + j + "_0" + i + ".png"));
                if (i <= 2)
                {
                    catchImages[i - 1] = new Image(RandomFish.class.getResourceAsStream("/image/fish" + j + "_catch_0" + i + ".png"));
                }
            }
            images[9] = new Image(RandomFish.class.getResourceAsStream("/image/fish" + j + "_10.png"));
            fishAnimationS.add(images);
            fishCatchAnimation.add(catchImages);
        }
    }
}
