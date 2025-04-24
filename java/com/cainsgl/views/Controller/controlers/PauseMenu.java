package com.cainsgl.views.Controller.controlers;

import com.cainsgl.CustomScript.ui.exitMenuMananger;
import com.cainsgl.CustomScript.ui.showMarks;
import com.cainsgl.views.GameMenu;
import com.cainsgl.views.GameSenseManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PauseMenu
{
    public Button conPlay;
    public Button noSave;
    public Button Save;
    public exitMenuMananger exitmenuMananger;
    public Pane pane;
    public void initialize(){
        conPlay.setOnAction(e->{
            exitmenuMananger.mainGameObject.mainGameScene.pane.getChildren().remove(pane);
            pane=null;
            exitmenuMananger.pane=null;
            exitmenuMananger.mainGameObject.mainGameScene.Continue(true);
        });
        noSave.setOnAction(e->{
            exitmenuMananger.mainGameObject.mainGameScene.Terminate();
            exitmenuMananger.mainGameObject.mainGameScene.Continue(true);
            exitmenuMananger.mainGameObject.mainGameScene.EndHandler = () ->
            {
                Platform.runLater(() ->
                {
                    new GameMenu().start(GameSenseManager.stage);
                });
            };
        });
        Save.setOnAction(e->{
            GameSenseManager.SaveMap(exitmenuMananger.mainGameObject.mainGameScene,"1");
            exitmenuMananger.mainGameObject.mainGameScene.Terminate();
            exitmenuMananger.mainGameObject.mainGameScene.Continue(true);
            exitmenuMananger.mainGameObject.mainGameScene.EndHandler = () ->
            {
                Platform.runLater(() ->
                {
                    new GameMenu().start(GameSenseManager.stage);
                });
            };
        });
    }
}
