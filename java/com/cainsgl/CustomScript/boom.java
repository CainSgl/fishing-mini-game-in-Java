package com.cainsgl.CustomScript;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Scripts.Animator;
import com.cainsgl.GameFrame.Scripts.CollisionEvent;
import com.cainsgl.GameFrame.Scripts.RecCollider;
import com.cainsgl.GameFrame.Scripts.Render;
import com.cainsgl.GameFrame.gameObject;

public class boom extends Script
{


    public boom(gameObject gameObject)
    {
        super(gameObject);
        new CollisionEvent(gameObject)
        {
            @Override
            public void Call(com.cainsgl.GameFrame.gameObject gameObject, RecCollider This, RecCollider Other)
            {
                markDisplay markDisplay = Other.mainGameObject.getScript(markDisplay.class);
                if(markDisplay != null)
                {
                    //打到鱼了
                    if(followPosition.OtherToThis.containsKey(Other.mainGameObject))
                    {
                        return;
                    }
                    gameObject.removeMe();
               //     barrel.markS+=markDisplay.mark;
                    Other.mainGameObject.removeScript(Animator.class);
                    Other.mainGameObject.WillDo(()->{
                        new Animator(Other.mainGameObject,40, StaticResourceHolder.fishCatchAnimation.get(markDisplay.type));
                    });

                    com.cainsgl.GameFrame.gameObject net = new gameObject(gameObject.mainGameScene);
                    Render render = new Render(net, StaticResourceHolder.netImages[0], Other.mainGameObject.mainGameScene.pane);
                    new followPosition(net,Other.mainGameObject);
                    Render otherRender = Other.mainGameObject.getScript(Render.class);
                    render.img.setFitWidth(otherRender.img.getFitWidth());
                    render.img.setFitHeight(otherRender.img.getFitHeight());
                    new Animator(net,40, StaticResourceHolder.netImages);
                    new CatchFish(Other.mainGameObject);

                    Other.mainGameObject.SetWhenRemove(()->{
                        net.removeMe();
                    });



                    //控制他往炮台走
                }
            }
        };
    }

    @Override
    public void update()
    {

    }

    @Override
    public void fixUpdate()
    {

    }
}
