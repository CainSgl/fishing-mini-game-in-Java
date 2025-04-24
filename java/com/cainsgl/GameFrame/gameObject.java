package com.cainsgl.GameFrame;

import com.cainsgl.GameFrame.Config.FuncInterFace;
import com.cainsgl.GameFrame.Scripts.CollisionEvent;
import com.cainsgl.GameFrame.Scripts.RecCollider;
import com.cainsgl.GameFrame.Scripts.drawable;
import com.cainsgl.GameFrame.Tool.Vector2;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class gameObject implements Serializable
{
    private final List<Script> Scripts = new ArrayList<>();

    private final Map<Class<?>, Script> ScriptsMap = new ConcurrentHashMap<>();

    private final List<CollisionEvent> CollisionEvents = new ArrayList<>();
  public   void RemoveAllDrawAble()
    {
        for(Script s : Scripts)
        {
            if(s instanceof drawable)
            {
                ((drawable) s).OnCopy();
            }
        }
    }
    public void RemoveAllCollisionEvent()
    {
        CollisionEvents.clear();
    }
    public void CollisionEventCall(RecCollider This, RecCollider Other)
    {
        for (CollisionEvent event : CollisionEvents)
        {
            event.Call(this, This, Other);
        }
    }

    public void addCollisionEvent(CollisionEvent c)
    {
        CollisionEvents.add(c);
    }

    public void removeCollisionEvent(CollisionEvent c)
    {
        CollisionEvents.remove(c);
    }

    public void addScript(Script s)
    {
        Scripts.add(s);
        ScriptsMap.put(s.getClass(), s);
    }

    public Vector2 position = new Vector2();

    private boolean willRemove = false;
    private final List<FuncInterFace> willRemoveDoEvents = Collections.synchronizedList(new ArrayList<>());

    public void removeScript(Class<? extends Script> ScriptClass)
    {
        willRemove = true;
        for (Script s : Scripts)
        {
            if (s.getClass().equals(ScriptClass))
            {
                willRemoveDoEvents.add(() ->
                {
                    s.onClose();
                    Scripts.remove(s);
                    ScriptsMap.remove(ScriptClass);
                });
                break;
            }
        }

    }
    public  FuncInterFace whenRemove;
    public void SetWhenRemove(FuncInterFace f)
    {
        whenRemove=f;
    }
    public void WillDo(FuncInterFace f)
    {
        willRemove = true;
        willRemoveDoEvents.add(f);
    }

    public void removeAllScript()
    {
        willRemove = true;
        willRemoveDoEvents.add(() ->
        {
            for (Script s : Scripts)
            {
                s.onClose();
            }
            Scripts.clear();
            ScriptsMap.clear();
            mainGameScene.removeGameObject(this);
        });
    }
    public void removeMe()
    {
        if(whenRemove!=null)
        {
            whenRemove.handle();
        }
        removeAllScript();
    }


    public <T> T getScript(Class<T> tClass)
    {
        Script script = ScriptsMap.get(tClass);
        if (script == null)
            return null;
        return (T) script;
    }

    public boolean isOpen = true;
    public GameScene mainGameScene;


    public gameObject(GameScene mainGameScene)
    {
        this.mainGameScene = mainGameScene;
        mainGameScene.gameObjects.add(this);
    }


    void Update()
    {
        if (isOpen)
        {
            for (Script s : Scripts)
            {
                if (s.isOpen)
                {
                    s.update();
                }
            }
            if (willRemove)
            {
                for (FuncInterFace event : willRemoveDoEvents)
                {
                    event.handle();
                }
                willRemoveDoEvents.clear();
                willRemove = false;
            }
        }
    }

    void fixUpdate()
    {
        for (Script s : Scripts)
        {
            if (s.isOpen)
            {
                s.fixUpdate();
            }

        }
    }
}
