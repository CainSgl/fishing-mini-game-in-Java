package com.cainsgl.views;

import com.cainsgl.CustomScript.*;
import com.cainsgl.CustomScript.ui.exitMenuMananger;
import com.cainsgl.CustomScript.ui.showMarks;
import com.cainsgl.GameFrame.GameScene;
import com.cainsgl.GameFrame.Scripts.*;
import com.cainsgl.GameFrame.Tool.Vector2;
import com.cainsgl.GameFrame.gameObject;
import com.cainsgl.ToMapTool.AllData;
import com.cainsgl.ToMapTool.Info;
import com.cainsgl.ToMapTool.boomData;
import com.cainsgl.ToMapTool.fishData;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GameSenseManager
{
    public static Stage stage;
    public static double fixWidth = 800;

    public static void catchFishSense()
    {
        AnchorPane pane = new AnchorPane();
        StaticResourceHolder.markS = 0;
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        fixWidth = 800;
        if (stage.getWidth() == fixWidth)
        {
            stage.setWidth(++fixWidth);
        }
        else
        {
            stage.setWidth(fixWidth);
        }
        GameScene MainGameScene = new GameScene(pane);
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(GameSenseManager.class.getResourceAsStream("/image/bg.jpg")));
        pane.getChildren().add(imageView);
        gameObject FishManager = new gameObject(MainGameScene);
        new RandomFish(FishManager);
        gameObject barrelObject = new gameObject(MainGameScene);
        barrelObject.position.x = 400;
        barrelObject.position.y = 480 - 62;
        new Render(barrelObject, new Image(GameSenseManager.class.getResourceAsStream("/image/barrel.png")), pane);
        new barrel(barrelObject);


        RecCollider recCollider = new RecCollider(barrelObject, 300, 124);
        recCollider.offsetX = -150 + 21;
        recCollider.offsetY = -31;
        recCollider.isTrigger = true;
        new CollisionEvent(barrelObject)
        {
            @Override
            public void Call(gameObject gameObject, RecCollider This, RecCollider Other)
            {
                CatchFish script = Other.mainGameObject.getScript(CatchFish.class);
                if (script != null)
                {
                    markDisplay markDisplay = Other.mainGameObject.getScript(markDisplay.class);
                    StaticResourceHolder.markS += markDisplay.mark;
                    Other.mainGameObject.removeMe();
                }
            }
        };
        gameObject MarksManager = new gameObject(MainGameScene);
        new showMarks(MarksManager);
        new exitMenuMananger(MarksManager);


        Thread thread = new Thread(MainGameScene);
        thread.setDaemon(true);
        thread.start();
        stage.setOnCloseRequest((e) ->
        {
            MainGameScene.Terminate();
        });
        stage.show();
    }


    public static void RunMap()
    {

    }

    public static String fileDir;

    static
    {
        fileDir = new File("").getAbsolutePath();
        fileDir = fileDir.substring(0, fileDir.lastIndexOf("\\") + 1);
    }

    public static void SaveMap(GameScene gameScene, String mapName)
    {


        List<fishData> fishDataList = new ArrayList<>();
        List<boomData> boomDataList = new ArrayList<>();
        //保存鱼的位置信息，以及大小等
        for (gameObject g : gameScene.gameObjects)
        {
            AutoClear script = g.getScript(AutoClear.class);
            if (script != null)
            {
                //说明是鱼
                int type;
                double scale = 1;
                double x;
                double y;
                double xGravity;
                double yGravity;
                double xSpeed;
                double ySpeed;
                boolean isCatch;
                markDisplay markDisplay = g.getScript(markDisplay.class);
                Rigidbody2D rigidbody2D = g.getScript(Rigidbody2D.class);
                xSpeed = rigidbody2D.velocity.x;
                ySpeed = rigidbody2D.velocity.y;
                xGravity = rigidbody2D.xGravity;
                yGravity = rigidbody2D.gravity;
                x = g.position.x;
                y = g.position.y;
                type = markDisplay.type;
                CatchFish catchFish = g.getScript(CatchFish.class);
                //被抓了
                isCatch = catchFish != null;
                fishData fishData = new fishData(type, scale, x, y, xGravity, yGravity, xSpeed, ySpeed, isCatch);
                fishDataList.add(fishData);
            }
            else
            {
                AutoClearBoom autoClearBoom = g.getScript(AutoClearBoom.class);

                if (autoClearBoom != null)
                {
                    //说明是子弹
                    double x;
                    double y;
                    double xSpeed;
                    double ySpeed;
                    Rigidbody2D rigidbody2D = g.getScript(Rigidbody2D.class);

                    x = g.position.x;
                    y = g.position.y;
                    xSpeed = rigidbody2D.velocity.x;
                    ySpeed = rigidbody2D.velocity.y;
                    boomDataList.add(new boomData(x,y,xSpeed,ySpeed));
                }
            }


        }
        Info info = new Info(StaticResourceHolder.markS, mapName);
        AllData allData = new AllData(fishDataList, info,boomDataList);
        File file = new File(fileDir + "\\" + mapName + ".json");
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            //说明存在
        }
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, allData);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void InitFish(List<fishData> fishDataList, GameScene MainGameScene)
    {
        for(fishData fishData : fishDataList)
        {


            gameObject fish=new gameObject(MainGameScene);

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
                            //  resourcesManager.eat.start();
                            Other.mainGameObject.removeMe();
                        }
                    }
                }
            };

            fish.position.x=fishData.x;
            fish.position.y=fishData.y;
            double h = StaticResourceHolder.fishAnimationS.get(fishData.type)[0].getHeight();
            double w = StaticResourceHolder.fishAnimationS.get(fishData.type)[0].getWidth();
            Rigidbody2D rigidbody2D = new Rigidbody2D(fish, w * h, fishData.yGravity );
            rigidbody2D.xGravity=fishData.xGravity;
            rigidbody2D.velocity.x=fishData.xSpeed;
            rigidbody2D.velocity.y=fishData.ySpeed;

            new RecCollider(fish, w, h);
            new AutoClear(fish);
            Render render1 = new Render(fish, new Image(App.class.getResourceAsStream("/image/test.jpg")));
            new fixDirection(fish);
            new Animator(fish, 40, StaticResourceHolder.fishAnimationS.get(fishData.type));
            new markDisplay(fish, w * h, fishData.type);

            if(fishData.isCatch)
            {

                fish.removeScript(Animator.class);
                fish.WillDo(()->{
                    new Animator(fish,40, StaticResourceHolder.fishCatchAnimation.get(fishData.type));
                });
                com.cainsgl.GameFrame.gameObject net = new gameObject(MainGameScene);
                Render render = new Render(net, StaticResourceHolder.netImages[0], MainGameScene.pane);
                new followPosition(net,fish);
                render.img.setFitWidth(w);
                render.img.setFitHeight(h);
                new Animator(net,40, StaticResourceHolder.netImages);
                new CatchFish(fish);
                fish.SetWhenRemove(()->{
                    net.removeMe();
                });
            }

        }
    }

    public static void InitBoom(List<boomData> boomDataList, GameScene MainGameScene)
    {
        for(boomData boomData : boomDataList)
        {
            gameObject boom=new gameObject(MainGameScene);
            boom.isOpen=false;
            boom.position.x=boomData.x;
            boom.position.y=boomData.y;
            Render render = new Render(boom, new Image(barrel.class.getResourceAsStream("/image/boom.png")));
            render.img.setFitWidth(16);
            render.img.setFitHeight(16);
            new Rigidbody2D(boom,1000,0,new Vector2(boomData.xSpeed, boomData.ySpeed));
            new RecCollider(boom,16,16).isTrigger=true;
            new AutoClearBoom(boom);
            new boom(boom);
            boom.isOpen=true;
        }
    }

    public static void LoadMap(String mapName)
    {

        File file = new File(fileDir + "\\" + mapName + ".json");
        ObjectMapper mapper = new ObjectMapper();
        AllData allData=null;
        try{
           allData = mapper.readValue(file, AllData.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        AnchorPane pane = new AnchorPane();
        StaticResourceHolder.markS = 0;
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        fixWidth = 800;
        if (stage.getWidth() == fixWidth)
        {
            stage.setWidth(++fixWidth);
        }
        else
        {
            stage.setWidth(fixWidth);
        }

        GameScene MainGameScene = new GameScene(pane);
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(GameSenseManager.class.getResourceAsStream("/image/bg.jpg")));
        pane.getChildren().add(imageView);
        gameObject FishManager = new gameObject(MainGameScene);
        new RandomFish(FishManager);
        gameObject barrelObject = new gameObject(MainGameScene);
        barrelObject.position.x = 400;
        barrelObject.position.y = 480 - 62;
        new Render(barrelObject, new Image(GameSenseManager.class.getResourceAsStream("/image/barrel.png")), pane);
        new barrel(barrelObject);


        RecCollider recCollider = new RecCollider(barrelObject, 300, 124);
        recCollider.offsetX = -150 + 21;
        recCollider.offsetY = -31;
        recCollider.isTrigger = true;
        new CollisionEvent(barrelObject)
        {
            @Override
            public void Call(gameObject gameObject, RecCollider This, RecCollider Other)
            {
                CatchFish script = Other.mainGameObject.getScript(CatchFish.class);
                if (script != null)
                {
                    markDisplay markDisplay = Other.mainGameObject.getScript(markDisplay.class);
                    StaticResourceHolder.markS += markDisplay.mark;
                    Other.mainGameObject.removeMe();
                }
            }
        };
        gameObject MarksManager = new gameObject(MainGameScene);
        showMarks showMarks = new showMarks(MarksManager);
        new exitMenuMananger(MarksManager);

        //把鱼和子弹new出来
        if(allData != null)
        {
            InitFish(allData.fishDataList, MainGameScene );
            InitBoom(allData.boomDataList, MainGameScene);
            StaticResourceHolder.markS= allData.info.marks;
            if(allData.info.marks>=StaticResourceHolder.WinTargetMark)
            {
                showMarks.ShouldPause=false;
                showMarks.target=allData.info.marks;
            }
        }






        Thread thread = new Thread(MainGameScene);
        thread.setDaemon(true);
        thread.start();
        stage.setOnCloseRequest((e) ->
        {
            MainGameScene.Terminate();
        });
        stage.show();
    }
}
