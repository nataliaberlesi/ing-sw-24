module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires com.google.gson;

    opens it.polimi.ingsw.Server.Network to com.google.gson;

    // I need to add the path of the directory containing the GUIApplication class to the module for it to work
    opens it.polimi.ingsw.Client.View.GUI to javafx.fxml;
    exports it.polimi.ingsw.Client.View.GUI;
    exports it.polimi.ingsw.Client.View;
    opens it.polimi.ingsw.Client.View to javafx.fxml;
    exports it.polimi.ingsw.Client.Network;
    exports it.polimi.ingsw.Server.Model;
}