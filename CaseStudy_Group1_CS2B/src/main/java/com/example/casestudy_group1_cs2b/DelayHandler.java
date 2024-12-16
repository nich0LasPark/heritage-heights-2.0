package com.example.casestudy_group1_cs2b;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class DelayHandler {
    // Method to delay a task
    public static void executeWithDelay(Runnable task, double delayInMilliseconds) {
        PauseTransition pause = new PauseTransition(Duration.millis(delayInMilliseconds));
        pause.setOnFinished(event -> task.run());
        pause.play();
    }
}
