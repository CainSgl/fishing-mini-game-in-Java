package com.cainsgl.views;

import com.cainsgl.GameFrame.Config.FuncInterFace;
import com.cainsgl.views.Controller.Tools.TipSettings;
import com.cainsgl.views.Controller.controlers.loadmenu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class loadMenuApp
{

    public Stage stage;
    public static loadMenuApp menuApp;
    public static int id=0;
    public loadMenuApp start(Stage loadStage) throws Exception
    {
        menuApp = this;
        this.stage = loadStage;
        loadStage.initStyle(StageStyle.TRANSPARENT);
        loadStage.setTitle("登录界面");
        loadStage.getIcons().add(new Image(getClass().getResourceAsStream("/闪电.png")));
        FXMLLoader loadmenuFXML=new FXMLLoader(getClass().getResource("/loadmenu.fxml"));
        Scene loadmenuScene=new Scene(loadmenuFXML.load());
        loadmenuScene.setFill(Color.TRANSPARENT);
        loadmenuScene.getStylesheets().add(getClass().getResource("/loadmenu.css").toExternalForm());
        loadStage.setScene(loadmenuScene);
        ((loadmenu)loadmenuFXML.getController()).setStage(loadStage);
        loadStage.show();
        return this;
    }
    FuncInterFace funcInterFace;
    public void then(FuncInterFace funcInterFace)
    {
       this.funcInterFace=funcInterFace;
    }
    public void call()
    {
        menuApp=null;
        stage=null;
        funcInterFace.handle();
        funcInterFace=null;
    }
    public static void loadSuccess()
    {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame=new KeyFrame(Duration.seconds(0.5),(e)->{
            menuApp.call();
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}
