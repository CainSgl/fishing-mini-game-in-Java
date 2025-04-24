package com.cainsgl.CustomScript.ui;

import com.cainsgl.CustomScript.StaticResourceHolder;
import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Scripts.drawable;
import com.cainsgl.GameFrame.Tool.RemoveController;
import com.cainsgl.GameFrame.Tool.TimeUtil;
import com.cainsgl.GameFrame.gameObject;
import com.cainsgl.views.Controller.controlers.WinMenu;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.IOException;


public class showMarks extends Script implements drawable
{
  public   double target=0;
    Rectangle rectangle;
    Label label;
    public showMarks(gameObject gameObject)
    {
        super(gameObject);
        rectangle=new Rectangle();
        rectangle.setWidth(20);
        rectangle.setHeight(0);
        rectangle.setX(0);
        rectangle.setY(480);
        rectangle.setRotate(360);
        rectangle.setFill(Color.YELLOWGREEN);
        label=new Label();
        label.setLayoutX(25);
        label.setLayoutY(480-36);
        label.setFont(Font.font(36));

        label.setTextFill(Color.YELLOW);

        gameObject.mainGameScene.pane.getChildren().add(rectangle);
        gameObject.mainGameScene.pane.getChildren().add(label);
    }

    public boolean ShouldPause=true;
    public void WhenWin()
    {
        if(ShouldPause)
        {
            if(target>StaticResourceHolder.WinTargetMark)
            {
                //TODO
                System.out.println("赢了");
                exitMenuMananger script = mainGameObject.getScript(exitMenuMananger.class);
                if(script!=null)
                {
                    mainGameObject.removeScript(exitMenuMananger.class);
                }
                mainGameObject.mainGameScene.Continue(false);
                try{
                    FXMLLoader WinMenuFXMLoader = new FXMLLoader(getClass().getResource("/WinMenu.fxml"));
                    Pane load = WinMenuFXMLoader.load();
                    load.getStylesheets().add(getClass().getResource("/GameMenu.css").toExternalForm());
                    load.setLayoutX(170);
                    load.setLayoutY(10);
                    WinMenu controller =(WinMenu) WinMenuFXMLoader.getController();
                    controller.showMarksManager=this;
                    controller.pane=load;
                    Platform.runLater(()->{
                        mainGameObject.mainGameScene.pane.getChildren().add(load);
                    });
                }
                catch (IOException e)
                {
                    //错误
                    e.printStackTrace();
                    mainGameObject.mainGameScene.Continue(true);
                    ShouldPause=false;
                }catch (Exception e)
                {
                    e.printStackTrace();
                    mainGameObject.mainGameScene.Continue(true);
                    ShouldPause=false;
                }
            }
        }
    }

    @Override
    public void update()
    {
        if(target< StaticResourceHolder.markS)
        {
            target+=40* TimeUtil.DeltaTime;
        }
        WhenWin();
        draw();
    }
    @Override
    public void fixUpdate()
    {

    }

    @Override
    public void onClose()
    {
        RemoveController.RemoveController(rectangle);
        RemoveController.RemoveController(label);
    }

    @Override
    public void draw()
    {
        Platform.runLater(() ->
        {
            mainGameObject.mainGameScene.pane.getChildren().remove(rectangle);
            mainGameObject.mainGameScene.pane.getChildren().add(rectangle);
            rectangle.setHeight(target/400);
            rectangle.setY(480-target/400);
            label.setText(""+(int) StaticResourceHolder.markS/100);
        });
    }

    @Override
    public void OnCopy()
    {
        label=null;
        rectangle=null;
    }
}
