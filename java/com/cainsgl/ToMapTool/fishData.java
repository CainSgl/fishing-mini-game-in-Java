package com.cainsgl.ToMapTool;

import java.io.Serializable;

public class fishData implements Serializable
{
   public int type;
    public double scale;
    public  double x;
    public double y;
    public  double xGravity;
    public  double yGravity;
    public double xSpeed;
    public  double ySpeed;
    public    boolean isCatch;

    public fishData(int type, double scale, double x, double y, double xGravity, double yGravity, double xSpeed, double ySpeed, boolean isCatch)
    {
        this.type = type;
        this.scale = scale;
        this.x = x;
        this.y = y;
        this.xGravity = xGravity;
        this.yGravity = yGravity;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.isCatch = isCatch;
    }
    public fishData()
    {

    }
}
