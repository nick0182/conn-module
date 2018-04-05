package ru.nikolay.connmodule.ui;

import javafx.scene.Parent;

public class ViewHolder {

    private Object controller;
    private Parent view;

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Parent getView() {
        return view;
    }

    public void setView(Parent view) {
        this.view = view;
    }
}
