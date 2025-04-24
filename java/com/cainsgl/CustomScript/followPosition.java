package com.cainsgl.CustomScript;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Scripts.RecCollider;
import com.cainsgl.GameFrame.Scripts.Render;
import com.cainsgl.GameFrame.gameObject;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.Map;

public class followPosition extends Script
{


    public gameObject otherGameObject;
    public final static Map<gameObject,gameObject> ThisToOther=new HashMap<gameObject,gameObject>();
    public final static Map<gameObject,gameObject> OtherToThis=new HashMap<gameObject,gameObject>();
    public Render render;
    public RecCollider OtherRecCollider;
    public followPosition(gameObject gameObject,gameObject otherGameObject)
    {
        super(gameObject);
        this.otherGameObject=otherGameObject;
        otherGameObject.SetWhenRemove(()->{
            ThisToOther.remove(gameObject);
            OtherToThis.remove(otherGameObject);
        });
        ThisToOther.put(gameObject,otherGameObject);
        OtherToThis.put(otherGameObject,gameObject);
        render=gameObject.getScript(Render.class);
        OtherRecCollider=otherGameObject.getScript(RecCollider.class);
    }

    @Override
    public void update()
    {
        mainGameObject.position.x=otherGameObject.position.x+OtherRecCollider.offsetX-OtherRecCollider.width/3  ;
        mainGameObject.position.y=otherGameObject.position.y+OtherRecCollider.offsetY  -OtherRecCollider.height/3 ;
    }

    @Override
    public void fixUpdate()
    {
        Platform.runLater(()->{
            render.img.setFitWidth(OtherRecCollider.width*1.5);
            render.img.setFitHeight(OtherRecCollider.height*1.5);
        });
    }
}
