package com.cainsgl.views;

import javafx.application.Application;

import javafx.stage.Stage;


public class App extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        new loadMenuApp().start(stage).then(() ->
        {
            if(loadMenuApp.id==1)
            {
                stage.close();
                Stage stage1 = new Stage();
                GameSenseManager.stage=stage1;
                new GameMenu().start(stage1);
            }else
            {
                stage.close();
                Stage stage1 = new Stage();
                new Labyrinth().start(stage1);
            }
        });
    }
}
