package com.cainsgl.GameFrame.Scripts;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Tool.TimeUtil;
import com.cainsgl.GameFrame.gameObject;
import javafx.application.Platform;
import javafx.scene.image.Image;


public class Animator extends Script
{
    Image[] images;
    public boolean isRun = true;
    long timer;
    Render render;

    public Animator(gameObject gameObject, long timer, Image... images)
    {
        super(gameObject);
        this.images = images;
        this.timer = timer;
        this.nowViewRender = 0;
        render = mainGameObject.getScript(Render.class);
        Platform.runLater(() ->
        {
            if (render!= null)
                render.img.setImage(images[nowViewRender]);
        });
    }

    long pastTime;
    int nowViewRender;

    @Override
    public void update()
    {
        if (isRun)
        {
            pastTime += TimeUtil.DeltaTime;
            if (pastTime > timer)
            {
                nowViewRender++;
                nowViewRender %= images.length;
                Platform.runLater(() ->
                {
                    if (render != null)
                        render.img.setImage(images[nowViewRender]);
                });
                pastTime = 0;
            }
        }
    }

    @Override
    public void fixUpdate()
    {

    }
    //这里不用状态机了，麻烦


    @Override
    public void onClose()
    {
        isRun = false;
    }
}
