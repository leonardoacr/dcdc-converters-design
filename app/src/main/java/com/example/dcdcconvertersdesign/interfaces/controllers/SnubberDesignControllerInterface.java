package com.example.dcdcconvertersdesign.interfaces.controllers;

import android.os.Bundle;

public interface SnubberDesignControllerInterface {
    void onCreateController(Bundle bundle);

    void onResultsClicked(boolean btn);

    void updateUI(String state);
}
