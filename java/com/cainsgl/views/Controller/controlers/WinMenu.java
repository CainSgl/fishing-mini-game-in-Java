package com.cainsgl.views.Controller.controlers;

import com.cainsgl.CustomScript.ui.exitMenuMananger;
import com.cainsgl.CustomScript.ui.showMarks;
import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.gameObject;
import com.cainsgl.views.GameMenu;
import com.cainsgl.views.GameSenseManager;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WinMenu
{
    public Button newGame;
    public Button exit;
    public Button conPlay;

    public showMarks showMarksManager;
    public Pane pane;

    public void initialize()
    {
        newGame.setOnAction(actionEvent ->
        {
            showMarksManager.ShouldPause = false;
            showMarksManager.mainGameObject.mainGameScene.Terminate();
            showMarksManager.mainGameObject.mainGameScene.Continue(true);
            showMarksManager.mainGameObject.mainGameScene.EndHandler = () ->
            {
                Platform.runLater(() ->
                {
                    GameSenseManager.catchFishSense();
                });
            };

        });
        exit.setOnAction(actionEvent ->
        {
            showMarksManager.ShouldPause = false;
            showMarksManager.mainGameObject.mainGameScene.Terminate();
            showMarksManager.mainGameObject.mainGameScene.Continue(true);
            showMarksManager.mainGameObject.mainGameScene.EndHandler = () ->
            {
                Platform.runLater(() ->
                {
                    new GameMenu().start(GameSenseManager.stage);
                });
            };
        });
        conPlay.setOnAction(actionEvent ->
        {
           new exitMenuMananger(showMarksManager.mainGameObject);
            showMarksManager.mainGameObject.mainGameScene.pane.getChildren().remove(pane);
            pane=null;
            showMarksManager.ShouldPause = false;
            showMarksManager.mainGameObject.mainGameScene.Continue(true);
        });
    }


}
