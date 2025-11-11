package ru.skoblenkov.devicecontrol.utils;

import javafx.application.Platform;

public class FxUtils {
    public static void runOnFxThread(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }

    public static void runAsync(Runnable task) {
        new Thread(task).start();
    }
}
