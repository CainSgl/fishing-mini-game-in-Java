package com.cainsgl.GameFrame.Tool;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class RemoveController
{
    public static void RemoveController(Node node)
    {
        Platform.runLater(() -> {
            Pane parent = (Pane)node.getParent();
            parent.getChildren().remove(node);
        });
    }
}
