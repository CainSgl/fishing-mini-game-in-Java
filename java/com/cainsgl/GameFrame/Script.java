package com.cainsgl.GameFrame;


import java.io.Serializable;

public abstract class Script implements Serializable
{
   public  boolean isOpen=true;
    public void SetOnOff(boolean status)
    {
        isOpen=status;
    }
   public gameObject mainGameObject;
    public Script(gameObject gameObject)
    {
      this.mainGameObject=gameObject;
      mainGameObject.addScript(this);
    }
    public abstract void update();

    public abstract void fixUpdate();

    public  void onClose(){}
}
