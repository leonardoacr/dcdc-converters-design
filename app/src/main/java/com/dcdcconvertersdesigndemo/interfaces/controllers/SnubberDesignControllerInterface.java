package com.example.dcdcconvertersdesigndemo.interfaces.controllers;

import android.os.Bundle;

public interface SnubberDesignControllerInterface {
    void onCreateController(Bundle bundle);

    void onResultsClicked(boolean btn);

    void updateUI(String state);
}
