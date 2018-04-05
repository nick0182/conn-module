package ru.nikolay.connmodule.ui.controllers;


import javax.annotation.PostConstruct;

public class Test {
    private RootLayoutController rootLayoutController;

    public Test(RootLayoutController rootLayoutController) {
        this.rootLayoutController = rootLayoutController;
    }

    @PostConstruct
    private void init() {
        rootLayoutController.sayHello();
    }
}
