package com.cainsgl.views.Controller.controlers;

import com.cainsgl.views.GameSenseManager;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class GameMenu
{

    public Button newGame;
    public Button loadMap;
    public Button setting;
    public Button exit;
    public AnchorPane pane;


    public void initialize()
    {
        newGame.setOnAction((e)->{
            //开启新游戏
            GameSenseManager.catchFishSense();
        });
        loadMap.setOnAction((e)->{
            GameSenseManager.LoadMap("1");
        });
        exit.setOnAction((e)->{
            System.exit(0);
        });

    }
}
