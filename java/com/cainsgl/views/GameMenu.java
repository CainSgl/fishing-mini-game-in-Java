package com.cainsgl.views;

import com.cainsgl.views.Controller.controlers.loadmenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GameMenu
{
    public void start(Stage stage)
    {
        stage.setTitle("捕鱼小达人");
        FXMLLoader loadmenuFXML=new FXMLLoader(getClass().getResource("/GameMenu.fxml"));
        try{
            Scene loadmenuScene=new Scene(loadmenuFXML.load());
            loadmenuScene.getStylesheets().add(getClass().getResource("/GameMenu.css").toExternalForm());
            stage.setScene(loadmenuScene);
            stage.show();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
