package com.cainsgl.CustomScript.ui;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.gameObject;
import com.cainsgl.views.Controller.controlers.PauseMenu;
import com.cainsgl.views.Controller.controlers.WinMenu;
import com.cainsgl.views.GameSenseManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class exitMenuMananger extends Script
{
   public Pane pane;
    EventHandler<KeyEvent> eventEventHandler;

    public exitMenuMananger(gameObject gameObject)
    {
        super(gameObject);
        exitMenuMananger cache=this;
        eventEventHandler=new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if (event.getCode() == KeyCode.ESCAPE)
                {
                    if (pane != null)
                    {
                        Platform.runLater(() ->
                        {
                            mainGameObject.mainGameScene.pane.getChildren().remove(pane);
                            pane = null;
                        });

                        //游戏继续
                        mainGameObject.mainGameScene.Continue(true);
                    }
                    else
                    {
                        mainGameObject.mainGameScene.Continue(false);
                        FXMLLoader WinMenuFXMLoader = new FXMLLoader(getClass().getResource("/Pause.fxml"));
                        pane = null;
                        try
                        {
                            pane = WinMenuFXMLoader.load();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        pane.getStylesheets().add(getClass().getResource("/GameMenu.css").toExternalForm());
                        pane.setLayoutX(170);
                        pane.setLayoutY(10);
                        PauseMenu controller = WinMenuFXMLoader.getController();
                        controller.pane=pane;
                        controller.exitmenuMananger=cache;
                        Pane finalLoad = pane;
                        Platform.runLater(() ->
                        {
                            mainGameObject.mainGameScene.pane.getChildren().add(finalLoad);
                        });
                    }
                }
            }
        };

        GameSenseManager.stage.addEventHandler(KeyEvent.KEY_PRESSED, eventEventHandler);
    }

    @Override
    public void onClose()
    {
        GameSenseManager.stage.removeEventHandler(KeyEvent.KEY_PRESSED ,eventEventHandler);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void fixUpdate()
    {

    }
}
