package com.example.dcdcconvertersdesign.controllers;

import com.example.dcdcconvertersdesign.views.MainActivity;

public class MainController {
    private MainActivity view;

    public MainController(MainActivity view) {
        this.view = view;
    }

    public void onUsualDesignClicked() {
        view.navigateToUsualDesign();
    }

    public void onReverseDesignClicked() {
        view.navigateToReverseEngineering();
    }
}
