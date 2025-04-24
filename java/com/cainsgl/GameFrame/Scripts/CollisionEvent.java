package com.cainsgl.GameFrame.Scripts;

import com.cainsgl.GameFrame.gameObject;

import java.io.Serializable;

public abstract class CollisionEvent implements Serializable
{
    public CollisionEvent(gameObject gameObject)
    {
        gameObject.addCollisionEvent(this);
    }
    public CollisionEvent()
    {

    }
    public abstract void Call(gameObject gameObject,RecCollider This,RecCollider Other);

}
