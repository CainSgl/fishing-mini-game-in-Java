package com.cainsgl.views.LabyrinthTool.CustomScript;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Scripts.Render;
import com.cainsgl.GameFrame.gameObject;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class dragAble extends Script
{
    Render render;
    double mouseX, mouseY;
    Rectangle rectangle;

    public dragAble(gameObject gameObject)
    {
        super(gameObject);
        render = gameObject.getScript(Render.class);
        rectangle = new Rectangle();
        rectangle.setWidth(64);
        rectangle.setHeight(64);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.RED);
        rectangle.setStrokeWidth(4);
        rectangle.setVisible(false);
        mainGameObject.mainGameScene.pane.getChildren().add(rectangle);
        render.img.setOnMouseDragged((e) ->
                {
                    gameObject.position.x += e.getSceneX() - mouseX;
                    gameObject.position.y += e.getSceneY() - mouseY;
                    rectangle.setX(gameObject.position.x);
                    rectangle.setY(gameObject.position.y);
                    mouseX = e.getSceneX();
                    mouseY = e.getSceneY();
                }
        );
        render.img.setOnMousePressed(
                e ->
                {
                    rectangle.setVisible(true);
                    mouseX = e.getSceneX();
                    mouseY = e.getSceneY();
                }
        );
        render.img.setOnMouseReleased(e ->
        {
            Platform.runLater(() ->
            {
                rectangle.setVisible(false);
            });
        });
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
