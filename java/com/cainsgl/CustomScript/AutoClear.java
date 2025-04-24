package com.cainsgl.CustomScript;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.gameObject;

public class AutoClear extends Script
{
    public AutoClear(gameObject gameObject)
    {
        super(gameObject);
    }

    @Override
    public void update()
    {
        if(this.mainGameObject.position.x>1400||this.mainGameObject.position.y>1080||this.mainGameObject.position.x<-500||this.mainGameObject.position.y<-500)
        {
            mainGameObject.removeMe();
        }
    }

    @Override
    public void fixUpdate()
    {

    }
}
