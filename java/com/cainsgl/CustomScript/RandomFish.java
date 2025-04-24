package com.cainsgl.CustomScript;

import com.cainsgl.Resouce.resourcesManager;
import com.cainsgl.views.App;
import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Scripts.*;
import com.cainsgl.GameFrame.Tool.TimeUtil;
import com.cainsgl.GameFrame.gameObject;
import javafx.scene.image.Image;


import java.util.Random;

public class RandomFish extends Script
{
    Random random = new Random();

    public RandomFish(gameObject gameObject)
    {
        super(gameObject);
    }


    long pasttime;

    public final static int[] weights = {11, 9, 8, 7, 7, 6, 6, 6, 5, 5, 2};

    int RandomType()
    {
        int type = random.nextInt(0, 73);
        int sum = 0;
        for (int i = 0; i < weights.length; i++)
        {
            sum += weights[i];
            if (type <= sum)
            {
                return i;
            }
        }
        return 100;
    }

    void InitFish(gameObject fish)
    {
        int randInt = random.nextInt(1, 5);
        double x = -300, y = -300;
        int type = RandomType();
        double w = StaticResourceHolder.fishAnimationS.get(type)[0].getHeight();
        double h = StaticResourceHolder.fishAnimationS.get(type)[0].getWidth();
        double maxHw = Math.max(w, h);
        switch (randInt)
        {
        case 1 ->
        {
            x = random.nextDouble(-200, 1000);
            Rigidbody2D rigidbody2D = new Rigidbody2D(fish, w * h, random.nextDouble(-0.02, 0.08));
            rigidbody2D.xGravity = random.nextDouble(-0.04, 0.04);
            rigidbody2D.velocity.x = random.nextDouble(-1, 1);
            rigidbody2D.velocity.y = random.nextDouble(0 + rigidbody2D.gravity < 0 ? -rigidbody2D.gravity * 2.5 : -rigidbody2D.gravity, 2);
            y = random.nextDouble(-300 - maxHw, 0 - maxHw);
        }
        case 2 ->
        {
            x = random.nextDouble(-200 - maxHw, 0 - maxHw);
            Rigidbody2D rigidbody2D = new Rigidbody2D(fish, w * h, random.nextDouble(-0.04, 0.04));
            rigidbody2D.xGravity = random.nextDouble(-0.02, 0.08);
            rigidbody2D.velocity.x = random.nextDouble(0 + rigidbody2D.gravity < 0 ? -rigidbody2D.gravity * 2.5 : -rigidbody2D.gravity, 2);
            rigidbody2D.velocity.y = random.nextDouble(-1, 1);
            y = random.nextDouble(-300, 780);
        }
        case 3 ->
        {
            x = -300 + random.nextDouble(-200, 1000);
            Rigidbody2D rigidbody2D = new Rigidbody2D(fish, w * h, random.nextDouble(-0.08, 0.02));
            rigidbody2D.xGravity = random.nextDouble(-0.04, 0.04);
            rigidbody2D.velocity.x = random.nextDouble(-1, 1);
            rigidbody2D.velocity.y = random.nextDouble(-2, 0 + rigidbody2D.gravity < 0 ? -rigidbody2D.gravity * 2.5 : -rigidbody2D.gravity);
            y = random.nextDouble(480, 780);
        }
        case 4 ->
        {
            x = random.nextDouble(800, 1000);
            Rigidbody2D rigidbody2D = new Rigidbody2D(fish, w * h, random.nextDouble(-0.04, 0.04));
            rigidbody2D.xGravity = random.nextDouble(-0.04, 0.02);
            rigidbody2D.velocity.x = random.nextDouble(-2, 0 + rigidbody2D.gravity < 0 ? -rigidbody2D.gravity * 2.5 : -rigidbody2D.gravity);
            rigidbody2D.velocity.y = random.nextDouble(-1, 1);
            y = random.nextDouble(-200, 780);
        }
        default ->
        {
            throw new RuntimeException("错误的");
        }
        }
        ;
        fish.position.x = x;
        fish.position.y = y;


        new RecCollider(fish, w, h);
        new AutoClear(fish);
        new Render(fish, new Image(App.class.getResourceAsStream("/image/test.jpg")));
        new fixDirection(fish);
        new Animator(fish, 40, StaticResourceHolder.fishAnimationS.get(type));
        new markDisplay(fish, w * h, type);
    }

    @Override
    public void update()
    {
        pasttime += TimeUtil.DeltaTime;
        if (pasttime > 200)
        {
            pasttime = 0;
            gameObject fish = new gameObject(this.mainGameObject.mainGameScene);
            new CollisionEvent(fish)
            {
                @Override
                public void Call(gameObject gameObject, RecCollider This, RecCollider Other)
                {
                    Render script = gameObject.getScript(Render.class);
                    Render script2 = Other.mainGameObject.getScript(Render.class);
                    if (script != null && script2 != null)
                    {
                        double allSize1 = script.img.getFitWidth() * script.img.getFitHeight();
                        double allSize2 = script2.img.getFitWidth() * script2.img.getFitHeight();
                        fixDirection fixDirection = gameObject.getScript(fixDirection.class);
                        fixDirection fixDirection2 = Other.mainGameObject.getScript(fixDirection.class);
                        if (fixDirection2 == null)
                            return;
                        if (allSize1 / 2 * fixDirection.scale > allSize2 * fixDirection2.scale)
                        {

                            if (fixDirection.scale < 2.5)
                            {

                                fixDirection.scale += allSize2 * fixDirection2.scale / 3 / (allSize1 * fixDirection.scale);
                            }
                            Other.mainGameObject.removeMe();
                        }
                    }
                }
            };

            fish.isOpen = false;
            InitFish(fish);
            fish.isOpen = true;
        }
    }

    @Override
    public void fixUpdate()
    {

    }
}
