package com.iau.dailyplanner;

import com.iau.dailyplanner.db.DatabaseConnection;
import com.iau.dailyplanner.ui.WelcomeFrame;


public class App {
    public static void main(String[] args) {
        DatabaseConnection.createDatabaseAndTables();

        WelcomeFrame welcomeFrame = new WelcomeFrame();
        welcomeFrame.setVisible(true);
    }
}
