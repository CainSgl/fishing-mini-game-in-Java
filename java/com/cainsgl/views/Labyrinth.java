package com.cainsgl.views;

import com.cainsgl.GameFrame.GameScene;
import com.cainsgl.GameFrame.Scripts.RecCollider;
import com.cainsgl.GameFrame.Scripts.Render;
import com.cainsgl.GameFrame.gameObject;
import com.cainsgl.views.LabyrinthTool.CustomScript.addWall;
import com.cainsgl.views.LabyrinthTool.CustomScript.dragAble;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Labyrinth
{
    public void start(Stage stage)
    {
        stage.setTitle("迷宫小游戏");
        stage.setWidth(1080);
        stage.setHeight(800);
        AnchorPane pane = new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        Scene scene = new Scene(pane);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        LabyrinthSense(stage,pane);


        stage.show();
    }

    public void LabyrinthSense(Stage stage, Pane pane)
    {
        GameScene gameScene=new GameScene(pane);
        gameObject block=new gameObject(gameScene);
        new addWall(block);
        new Thread(gameScene).start();
        Render render = new Render(block, new Image(Labyrinth.class.getResourceAsStream("/Labyrinth/wall.png")));
        new dragAble(block);
        render.img.setFitWidth(64);
        render.img.setFitHeight(64);
        new RecCollider(block,64,64).isTrigger=true;


        gameObject blockManager=new gameObject(gameScene);


        stage.setOnCloseRequest((e)->{
            gameScene.Terminate();
        });
    }
}
