package com.cainsgl.ToMapTool;

import java.io.Serializable;

public class boomData implements Serializable
{

    public double x;
    public double y;
    public double xSpeed;
    public double ySpeed;

    public boomData(double x, double y, double xSpeed, double ySpeed)
    {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public boomData()
    {

    }
}
