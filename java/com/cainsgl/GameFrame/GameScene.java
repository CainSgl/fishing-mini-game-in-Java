package com.cainsgl.GameFrame;

import com.cainsgl.GameFrame.Config.FuncInterFace;
import com.cainsgl.GameFrame.Scripts.RecCollider;
import com.cainsgl.GameFrame.Tool.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameScene implements Runnable, Serializable
{

    public GameScene(Pane root)
    {
        this.pane = root;
    }

    public FuncInterFace EndHandler;


    public Pane pane;
    @JsonIgnore
    public final List<gameObject> gameObjects = new ArrayList<>();

    void Update()
    {
        for (int i = 0; i < gameObjects.size(); i++)
        {
            if (gameObjects.get(i).isOpen)
            {
                gameObjects.get(i).Update();
            }
        }
    }

    void fixUpdate()
    {
        for (int i = 0; i < gameObjects.size(); i++)
        {
            if (gameObjects.get(i).isOpen)
            {
                gameObjects.get(i).fixUpdate();
            }
        }
    }

    long startTime;
    boolean isEnd = false;
    long pastTime = 0;

    @Override
    public void run()
    {
        while (true)
        {
            if (isNOPause)
            {
                startTime = System.currentTimeMillis();
                this.Update();
                try
                {
                    Thread.sleep(10);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                TimeUtil.DeltaTime = System.currentTimeMillis() - startTime;
                pastTime += TimeUtil.DeltaTime;
                for (; pastTime > 20; pastTime -= 20)
                {
                    //牺牲fps
                    this.fixUpdate();
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
                if (isEnd)
                {
                    for (gameObject s : gameObjects)
                    {
                        s.removeMe();
                    }
                    for (FuncInterFace event : willRemoveDoEvents)
                    {
                        event.handle();
                    }
                    RecCollider.AllRecCollider.clear();
                    willRemoveDoEvents.clear();
                    if (EndHandler != null)
                        EndHandler.handle();
                    return;
                }
            }
            else
            {
                try
                {
                    Thread.sleep(20);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isNOPause = true;

    public void Terminate()
    {
        isEnd = true;
    }

    public void Continue(boolean Pause)
    {
        isNOPause = Pause;
    }


    private boolean willRemove = false;
    private final List<FuncInterFace> willRemoveDoEvents = Collections.synchronizedList(new ArrayList<>());

    public void removeGameObject(gameObject o)
    {
        willRemove = true;
        willRemoveDoEvents.add(() ->
        {
            gameObjects.remove(o);
        });
    }

    public void RemoveAllDrawable()
    {
        pane=null;
        for (int i = 0; i < gameObjects.size(); i++)
        {
            gameObjects.get(i).RemoveAllDrawAble();
        }
    }
}
