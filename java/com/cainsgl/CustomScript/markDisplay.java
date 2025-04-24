package com.cainsgl.CustomScript;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Scripts.RecCollider;
import com.cainsgl.GameFrame.Scripts.drawable;
import com.cainsgl.GameFrame.gameObject;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class markDisplay extends Script implements drawable
{
  public  double mark;
    double markCache;
    Label markLabel;
    RecCollider recCollider;
    fixDirection fixDirection;
    public int type;
    static Font font=new Font("Arial",30);
    public markDisplay(gameObject gameObject,double mark,int type)
    {
        super(gameObject);
        this.type=type;
        this.mark = mark;
         markLabel = new Label(""+(int)mark/100 );
        markLabel.setTextFill(Color.WHITE);
        recCollider=mainGameObject.getScript(RecCollider.class);
        fixDirection=mainGameObject.getScript(fixDirection.class);
        markLabel.setFont(font);
        Platform.runLater(()->
        {
            gameObject.mainGameScene.pane.getChildren().add(markLabel);
        });
        markCache =mark;
    }

    @Override
    public void update()
    {
        draw();
    }

    @Override
    public void fixUpdate()
    {
        mark=markCache*fixDirection.scale;
    }

    @Override
    public void draw()
    {
        Platform.runLater(()->{
            markLabel.setText((int)mark/100+"");
            markLabel.setLayoutX(mainGameObject.position.x+recCollider.offsetX+recCollider.width/2-10   );
            markLabel.setLayoutY(mainGameObject.position.y+recCollider.offsetY+recCollider.height/2-10);
        });
    }

    @Override
    public void OnCopy()
    {
        markLabel=null;
        font=null;
    }

    @Override
    public void onClose()
    {
        Platform.runLater(()->{
            mainGameObject.mainGameScene.pane.getChildren().remove(markLabel);
        });
    }
}
