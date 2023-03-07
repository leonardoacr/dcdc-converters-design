package com.example.dcdcconvertersdesign.controller;

import com.example.dcdcconvertersdesign.view.MainActivity;

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
