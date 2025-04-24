package com.cainsgl.CustomScript;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Scripts.RecCollider;
import com.cainsgl.GameFrame.Scripts.Rigidbody2D;
import com.cainsgl.GameFrame.Tool.Vector2;
import com.cainsgl.GameFrame.gameObject;

public class CatchFish extends Script
{
    Rigidbody2D rigidbody2D;
    public CatchFish(gameObject gameObject)
    {
        super(gameObject);
        this.rigidbody2D=gameObject.getScript(Rigidbody2D.class);
        gameObject.getScript(RecCollider.class).isTrigger=true;
        gameObject.RemoveAllCollisionEvent();
        gameObject.removeScript(fixDirection.class);
    }
    @Override
    public void update()
    {

        rigidbody2D.velocity=   new Vector2(-( mainGameObject.position.x - 421),-(mainGameObject.position.y - 480)).normalize();
        rigidbody2D.velocity.x*=10;
        rigidbody2D.velocity.y*=10;

        //检测他是否进入炮台范围
    }

    @Override
    public void fixUpdate()
    {

    }
}
