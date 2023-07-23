package com.example.dcdcconvertersdesign.controllers;

import com.example.dcdcconvertersdesign.interfaces.controllers.MainControllerInterface;
import com.example.dcdcconvertersdesign.views.MainView;

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
