package com.cainsgl.CustomScript;

import com.cainsgl.GameFrame.Script;
import com.cainsgl.GameFrame.Scripts.RecCollider;
import com.cainsgl.GameFrame.Scripts.Render;
import com.cainsgl.GameFrame.Scripts.Rigidbody2D;
import com.cainsgl.GameFrame.gameObject;
import javafx.application.Platform;

public class fixDirection extends Script
{
    Rigidbody2D rigidbody2D;
    Render render;
    RecCollider recCollider;
    public double scale=1;

    public fixDirection(gameObject gameObject)
    {
        super(gameObject);
        rigidbody2D = mainGameObject.getScript(Rigidbody2D.class);
        render = mainGameObject.getScript(Render.class);
        recCollider = mainGameObject.getScript(RecCollider.class);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void fixUpdate()
    {
        double distance= Math.sqrt(rigidbody2D.velocity.lengthSquared());
        Platform.runLater(()->{
            double fitHeight = render.img.getImage().getHeight();
            double fitWidth = render.img.getImage().getWidth();
            if(Math.abs(rigidbody2D.velocity.x  ) >Math.abs(rigidbody2D.velocity.y))
            {
                recCollider.SetWidthAndHeight(fitWidth*scale, fitHeight*scale);
                recCollider.offsetY=0;
                recCollider.offsetX=0;
            }else
            {
                recCollider.SetWidthAndHeight(fitHeight*scale, fitWidth*scale);
                recCollider.offsetY= -( fitWidth/2-fitHeight/2);
                recCollider.offsetX= -(fitHeight/2-fitWidth/2);
            }
            render.img.setFitWidth( fitWidth*scale  );
            render.img.setFitHeight( fitHeight*scale  );
            render.img.setRotate(  Math.toDegrees( Math.atan2(  rigidbody2D.velocity.y/distance, rigidbody2D.velocity.x/distance ) )+180  );
        });
    }
}
