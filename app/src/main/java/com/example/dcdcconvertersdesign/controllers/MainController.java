package com.example.dcdcconvertersdesign.controllers;

import com.example.dcdcconvertersdesign.views.MainView;

public class MainController {
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
