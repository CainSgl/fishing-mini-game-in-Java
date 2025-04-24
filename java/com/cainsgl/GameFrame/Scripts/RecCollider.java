package com.cainsgl.GameFrame.Scripts;

import com.cainsgl.GameFrame.Config.Config;
import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Tool.RemoveController;
import com.cainsgl.GameFrame.Tool.Vector2;
import com.cainsgl.GameFrame.gameObject;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;



public class RecCollider extends Script implements drawable
{
    public double width;
    public double height;
    public double offsetX;
    public double offsetY;
    public boolean isTrigger=false;
    public static final List<RecCollider> AllRecCollider =new ArrayList<RecCollider>();
    public double rotation;

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }
   public Rectangle rectangle = new Rectangle();


    public void SetWidthAndHeight(double width, double height)
    {
        this.width = width;
        this.height = height;
        rectangle.setWidth(width);
        rectangle.setHeight(height);
    }

    public RecCollider(gameObject gameObject, double width, double height)
    {
       this(gameObject,width,height,Color.BLACK);
    }
    public RecCollider(gameObject gameObject, double width, double height,Color color)
    {
        super(gameObject);
        AllRecCollider.add(this);
        this.width = width;
        this.height = height;
        if (Config.devEnvironment)
        {
            rectangle.setX(-1000);
            rectangle.setY(-1000);
            rectangle.setWidth(width);
            rectangle.setHeight(height);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(color);
            rectangle.setStrokeWidth(2);
            Platform.runLater(() -> {
                mainGameObject.mainGameScene.pane.getChildren().add(rectangle);
            });
        }
    }

    @Override
    public void update()
    {
        draw();
    }

    @Override
    public void fixUpdate()
    {
        //和其他的检测
        for (int i=0;i<AllRecCollider.size();i++)
        {
            this.handleElasticCollision(AllRecCollider.get(i));
        }
//        for (RecCollider recCollider : AllRecCollider)
//        {
//            this.handleElasticCollision(recCollider);
//        }
    }

    public boolean checkCollision(RecCollider Other)
    {
        boolean overlapX = (this.mainGameObject.position.x+offsetX) < (Other.mainGameObject.position.x+Other.offsetX) + Other.width &&
                (Other.mainGameObject.position.x+Other.offsetX) < (this.mainGameObject.position.x+offsetX) + this.width;
        boolean overlapY = (this.mainGameObject.position.y+offsetY) < (Other.mainGameObject.position.y+Other.offsetY) + Other.height &&
                (Other.mainGameObject.position.y+Other.offsetY) < (this.mainGameObject.position.y+offsetY) + this.height;
        return overlapX && overlapY;
    }

    public void handleElasticCollision(RecCollider Other)
    {
        if (this == Other)
            return;
        boolean isColliding = this.checkCollision(Other);
        if (isColliding)
        {
            this.mainGameObject.CollisionEventCall(this, Other);
            if(isTrigger||Other.isTrigger)
            {
                return;
            }
            Rigidbody2D thisRb = this.mainGameObject.getScript(Rigidbody2D.class);
            Rigidbody2D otherRb = Other.mainGameObject.getScript(Rigidbody2D.class);
            if (thisRb == null)
                return;
            double thisCenterX = (this.mainGameObject.position.x+offsetX) + this.width / 2;
            double thisCenterY = (this.mainGameObject.position.y+offsetY) + this.height / 2;
            double otherCenterX = (Other.mainGameObject.position.x+Other.offsetX) + Other.width / 2;
            double otherCenterY = (Other.mainGameObject.position.y+Other.offsetY) + Other.height / 2;
            //这里简单点，直接让碰撞中心点在中间

            double ElasticCollCenterX = (thisCenterX + otherCenterX) / 2;
            double ElasticCollCenterY = (thisCenterY + otherCenterY) / 2;

            double dX = ElasticCollCenterX - thisCenterX;
            double dY = ElasticCollCenterY - thisCenterY;
            double directionLength = Math.sqrt(dX * dX + dY * dY);
            if (directionLength == 0)
                return;
            dX /= directionLength;
            dY /= directionLength;
            double reverseSpeed = -thisRb.velocity.y * dY - thisRb.velocity.x * dX;
            if(otherRb==null)
            {
                thisRb.velocity.x += 2*reverseSpeed * dX;
                thisRb.velocity.y += 2*reverseSpeed * dY;
            }else
            {
                double allMass=thisRb.mass+otherRb.mass;
                thisRb.velocity.x += 2*reverseSpeed * dX*(otherRb.mass/allMass);
                thisRb.velocity.y += 2*reverseSpeed * dY*(otherRb.mass/allMass);
                otherRb.velocity.x -= 2*reverseSpeed * dX*(thisRb.mass/allMass);
                otherRb.velocity.y -= 2*reverseSpeed * dY*(thisRb.mass/allMass);
            }
        }
    }


    @Override
    public void draw()
    {
        if (!Config.devEnvironment)
        {
            return;
        }
        Platform.runLater(()->{
            rectangle.setWidth(width);
            rectangle.setHeight(height);
            rectangle.setX(mainGameObject.position.x+offsetX);
            rectangle.setY(mainGameObject.position.y+offsetY);
        });
    }

    @Override
    public void OnCopy()
    {
        rectangle=null;
    }


    @Override
    public void onClose()
    {
        AllRecCollider.remove(this);
        if (!Config.devEnvironment)
        {
            return;
        }
        RemoveController.RemoveController(rectangle);
    }
}
