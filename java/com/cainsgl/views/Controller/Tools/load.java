package com.cainsgl.views.Controller.Tools;

public class load
{
    public static String load(String user)
    {
        if (user.equals("123"))
        {
            return "捕鱼小达人";
        }else if(user.equals("456"))
        {
            return "迷宫小游戏";
        }
        return null;
    }
}
