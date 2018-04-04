package ru.nikolay.connmodule.main;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nikolay.connmodule.main.AbstractJavaFxApplicationSupport;
import ru.nikolay.connmodule.ui.ViewHolder;

public class ConnModuleApplication extends AbstractJavaFxApplicationSupport {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Connection module");

        ViewHolder rootLayoutViewHolder = context.getBean("rootLayout", ViewHolder.class);
        Parent rootLayout = rootLayoutViewHolder.getView();

        Scene scene = new Scene(rootLayout, 600 , 300);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launchApp(ConnModuleApplication.class, args);
    }

}
