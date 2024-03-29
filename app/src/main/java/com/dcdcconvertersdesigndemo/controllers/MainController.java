package com.dcdcconvertersdesigndemo.controllers;

import com.dcdcconvertersdesigndemo.interfaces.controllers.MainControllerInterface;
import com.dcdcconvertersdesigndemo.views.MainView;

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
