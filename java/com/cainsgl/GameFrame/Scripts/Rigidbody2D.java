package com.cainsgl.GameFrame.Scripts;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Tool.Vector2;
import com.cainsgl.GameFrame.gameObject;

public class Rigidbody2D extends Script
{
    public double mass;
    public double gravity;
    public double xGravity;
    public Vector2 velocity;

    public Rigidbody2D(gameObject gameObject, double mass, double gravity)
    {
        this(gameObject, mass, gravity, new Vector2());
    }

    public Rigidbody2D(gameObject gameObject, double mass, double gravity, Vector2 velocity)
    {
        super(gameObject);
        this.mass = mass;
        this.gravity = gravity;
        this.velocity = velocity;
    }


    @Override
    public void update()
    {

    }

    @Override
    public void fixUpdate()
    {
        velocity.x+=xGravity;
        velocity.y+=gravity;
        mainGameObject.position.x+=velocity.x;
        mainGameObject.position.y+=velocity.y;
    }
}
