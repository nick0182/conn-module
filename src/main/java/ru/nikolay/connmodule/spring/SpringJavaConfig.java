package ru.nikolay.connmodule.spring;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.nikolay.connmodule.ui.ViewHolder;
import ru.nikolay.connmodule.ui.controllers.RootLayoutController;
import ru.nikolay.connmodule.ui.controllers.Test;

import java.io.IOException;

@Configuration
@ComponentScan("ru.nikolay")
public class SpringJavaConfig {

    @Bean
    public ViewHolder rootLayout() throws IOException {
        return loadView("/fxml/RootLayout.fxml");
    }

    @Bean
    public RootLayoutController rootLayoutController() throws IOException {
        return (RootLayoutController) rootLayout().getController();
    }

    @Bean
    @Autowired
    public Test test(RootLayoutController rootLayoutController) {
        return new Test(rootLayoutController);
    }

    @SuppressWarnings("unchecked")
    private ViewHolder loadView(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path));
        ViewHolder res = new ViewHolder();

        res.setView(fxmlLoader.load());
        res.setController(fxmlLoader.getController());
        return res;
    }

}
