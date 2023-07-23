package com.example.dcdcconvertersdesigndemo.controllers;

import com.example.dcdcconvertersdesigndemo.interfaces.controllers.MainControllerInterface;
import com.example.dcdcconvertersdesigndemo.views.MainView;

public class MainController implements MainControllerInterface {
    private final MainView view;

    public MainController(MainView view) {
        this.view = view;
    }

    public void onUsualDesignClicked() {
        view.navigateToUsualDesign();
    }

    public void onReverseDesignClicked() {
        view.navigateToReverseEngineering();
    }
}
