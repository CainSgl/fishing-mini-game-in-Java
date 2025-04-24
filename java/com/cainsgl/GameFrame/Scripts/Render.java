package com.cainsgl.GameFrame.Scripts;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Tool.RemoveController;
import com.cainsgl.GameFrame.gameObject;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

public class Render extends Script implements drawable
{
    public ImageView img;

    public Render(gameObject gameObject, Image image)
    {
        super(gameObject);
        img = new ImageView(image);
        img.setX(-1000);
        img.setY(-1000);
        Platform.runLater(() ->
                {
                    gameObject.mainGameScene.pane.getChildren().add(img);
                }
        );

    }
    public Render(gameObject gameObject, Image image,Pane pane)
    {
        super(gameObject);
        img = new ImageView(image);
        img.setX(-1000);
        img.setY(-1000);
        Platform.runLater(() ->
                {
                    gameObject.mainGameScene.pane.getChildren().add(img);
                }
        );
       this.pane = pane;
    }
    Pane pane;
    public void SetWidthAndHeight(double width, double height)
    {
        img.setFitWidth(width);
        img.setFitHeight(height);
    }

    @Override
    public void update()
    {
        draw();
    }

    @Override
    public void fixUpdate()
    {

    }

    @Override
    public void draw()
    {
        Platform.runLater(() ->{
            img.setX(mainGameObject.position.x);
            img.setY(mainGameObject.position.y);
            if(pane!=null)
            {
               pane.getChildren().remove(img);
               pane.getChildren().add(img);
            }
        });

    }

    @Override
    public void OnCopy()
    {
        img=null;
    }

    @Override
    public void onClose()
    {
        RemoveController.RemoveController(img);
    }

}
