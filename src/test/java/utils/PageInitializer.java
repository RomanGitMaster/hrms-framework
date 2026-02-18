package utils;

import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.EmployeeInfoPage;
import pages.LoginPage;

public class PageInitializer {


    public static LoginPage loginPage;
    public static DashboardPage dashPage;
    public static AddEmployeePage addEmpPage;
    public static EmployeeInfoPage empInfoPage;

    public static void initializePageObjects() {
        loginPage = new LoginPage();
        dashPage = new DashboardPage();
        addEmpPage = new AddEmployeePage();
        empInfoPage = new EmployeeInfoPage();
    }
}
