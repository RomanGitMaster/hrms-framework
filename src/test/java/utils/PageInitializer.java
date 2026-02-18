package utils;

import pages.DashboardPage;
import pages.LoginPage;

public class PageInitializer {


    public static LoginPage loginPage;
    public static DashboardPage dashPage;

    public static void initializePageObjects() {
        loginPage = new LoginPage();
        dashPage = new DashboardPage();
    }
}
