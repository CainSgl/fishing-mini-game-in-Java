package com.cainsgl.GameFrame.Tool;

public class Vector2 implements java.io.Serializable
{
    public double x;
    public double y;

    @Override
    public String toString()
    {
        return "Vector2{" + "x=" + x + ", y=" + y + '}';
    }

    public Vector2()
    {

    }

    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other)
    {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public double dot(Vector2 v)
    {
        return this.x * v.x + this.y * v.y;
    }

    public Vector2 subtract(Vector2 other)
    {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public double lengthSquared()
    {
        return dot(this);
    }

    public Vector2 normalize()
    {
        double len = Math.sqrt(lengthSquared());
        if (len > 0)
        {
            return new Vector2(x / len, y / len);
        }
        return this;
    }

    public static double Dot(Vector2 a, Vector2 b)
    {
        return a.dot(b);
    }
}
