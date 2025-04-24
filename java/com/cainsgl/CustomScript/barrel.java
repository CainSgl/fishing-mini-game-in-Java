package com.cainsgl.CustomScript;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Scripts.RecCollider;
import com.cainsgl.GameFrame.Scripts.Render;
import com.cainsgl.GameFrame.Scripts.Rigidbody2D;
import com.cainsgl.GameFrame.Tool.Vector2;
import com.cainsgl.GameFrame.gameObject;
import javafx.application.Platform;
import javafx.event.EventTarget;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

public class barrel extends Script
{
    double mouseX;
    double mouseY;
    Render render;

    public barrel(gameObject gameObject)
    {
        super(gameObject);
        StaticResourceHolder.markS=0;
        gameObject.mainGameScene.pane.setOnMouseMoved(event ->
        {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
        gameObject.mainGameScene.pane.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouseEvent) ->
        {
            EventTarget target = mouseEvent.getTarget();
            if (target.getClass().equals(ImageView.class))
            {
                //开炮
                gameObject boom=new gameObject(this.mainGameObject.mainGameScene);
                boom.isOpen=false;
                boom.position.x=421;
                boom.position.y=480;
                Render render1 = new Render(boom, new Image(barrel.class.getResourceAsStream("/image/boom.png")));
                render1.img.setFitWidth(16);
                render1.img.setFitHeight(16);
                //获取角度
                double deltaX = mouseX - 421;
                double deltaY = mouseY - 480;
                Vector2 vector2 = new Vector2(deltaX, deltaY).normalize();
                vector2.x*=10;
                vector2.y*=10;
                new Rigidbody2D(boom,1000,0,vector2);
                new RecCollider(boom,16,16).isTrigger=true;
                new AutoClearBoom(boom);
                new boom(boom);
                boom.isOpen=true;
            }
        });
        render = mainGameObject.getScript(Render.class);
        render.img.getTransforms().add(new Rotate(20, 400 + 21, 480));
    }

    @Override
    public void update()
    {
        //  render.img.setRotate(100);



        Platform.runLater(() ->
        {
            double deltaX = mouseX - 421;
            double deltaY = mouseY - 480;
            //         System.out.println(deltaX+","+deltaY);
            double radians = Math.atan2(deltaY, deltaX);
            double degrees = Math.toDegrees(radians);
            render.img.getTransforms().remove(0);
            render.img.getTransforms().add(new Rotate(degrees + 90, 400 + 21, 480));
        });
    }

    @Override
    public void fixUpdate()
    {

    }
}
