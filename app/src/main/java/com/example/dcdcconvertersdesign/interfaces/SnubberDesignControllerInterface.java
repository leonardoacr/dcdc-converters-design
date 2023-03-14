package com.example.dcdcconvertersdesign.interfaces;

import android.os.Bundle;

public interface SnubberDesignControllerInterface {
    void onCreateController(Bundle bundle);

    void onResultsClicked(boolean btn);

    void updateUI(String state);
}
