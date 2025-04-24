package com.cainsgl.CustomScript;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.gameObject;

public class AutoClearBoom extends Script
{
    public AutoClearBoom(gameObject gameObject)
    {
        super(gameObject);
    }

    @Override
    public void update()
    {
        if(this.mainGameObject.position.x>810||this.mainGameObject.position.y>490||this.mainGameObject.position.x<-10||this.mainGameObject.position.y<-10)
        {
            mainGameObject.removeMe();
        }
    }

    @Override
    public void fixUpdate()
    {

    }
}
