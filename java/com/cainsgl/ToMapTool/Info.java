package com.cainsgl.ToMapTool;

import java.io.Serializable;

public class Info implements Serializable
{
    public  double marks;
    public String name;

    public Info(double marks, String name)
    {
        this.marks = marks;
        this.name = name;
    }
    public Info()
    {

    }
}
