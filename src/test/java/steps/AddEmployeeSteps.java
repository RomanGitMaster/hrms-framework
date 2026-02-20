package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.BaseClass;
import utils.Constants;
import utils.ExcelReader;

import java.util.*;

public class AddEmployeeSteps extends BaseClass {

    private List<Map<String, String>> addedEmployees = new ArrayList<>();
    private String expectedEmp;
    private String actualEmp;
    private List<String> actualMissFieldErrorMsg = new ArrayList<>();


    @And("user navigates to Add employee page")
    public void user_navigates_to_add_employee_page() {
        click(dashPage.pim);
        click(dashPage.addEmpTab);
    }


    @When("user adds employees from data table")
    public void user_adds_employees_from_data_table(io.cucumber.datatable.DataTable dataTable) {
        addedEmployees.clear();
        List<Map<String, String>> employees = dataTable.asMaps();
        for (Map<String, String> row : employees) {

            Map<String, String> addedEmp = new LinkedHashMap<>(row);
            String empIdvalue = addEmpPage.idAutoGenField.getAttribute("value");
            addedEmp.put("empId", empIdvalue);

            sendText(addEmpPage.firstNameField, row.get("firstName"));
            sendText(addEmpPage.middleNameField, row.get("middleName"));
            sendText(addEmpPage.lastNameField, row.get("lastName"));
            addedEmployees.add(addedEmp);

            click(addEmpPage.submitBtn);
            waitForFormLoader(addEmpPage.formLoaderLocator);
            click(dashPage.addEmpTab);
        }
    }

    @Then("all added employees should be present in Employee List")
    public void all_added_employees_should_be_present_in_employee_list() {
        click(dashPage.empListTab);
        for (Map<String, String> emp : addedEmployees) {
            clearAndType(empInfoPage.empIdSearchfield, emp.get("empId"));
            click(empInfoPage.searchBtn);
            empInfoPage.verifyEmp(emp, expectedEmp, actualEmp);
            scenarioScreenshot();
            Assert.assertEquals(expectedEmp, actualEmp);
            empInfoPage.deleteVerifiedEmp();//deleting employee after verifying to not overload the HRM system
            click(dashPage.empListTab);
        }
    }


    @When("user adds employees from excel file sheet {string}")
    public void user_adds_employees_from_excel_file_sheet(String sheet) {
        addedEmployees.clear();

        List<Map<String, String>> employees = ExcelReader.readExcelAsListOfMaps(Constants.EXCEL_FILE_PATH, sheet);
        for (Map<String, String> row : employees) {

            sendText(addEmpPage.firstNameField, row.get("firstName"));
            sendText(addEmpPage.middleNameField, row.get("middleName"));
            sendText(addEmpPage.lastNameField, row.get("lastName"));
            clearAndType(addEmpPage.idAutoGenField, row.get("empId"));
            //sendText(addEmpPage.idAutoGenField, row.get("id"));
            addedEmployees.add(row);

            click(addEmpPage.submitBtn);
            waitForFormLoader(addEmpPage.formLoaderLocator);
            click(dashPage.addEmpTab);
        }
    }


    @When("user tries to add employee without required fields")
    public void user_tries_to_add_employee_without_required_fields(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> employees = dataTable.asMaps();
        for (Map<String, String> row : employees) {
            typeIfNotEmpty(addEmpPage.firstNameField, row.get("firstName"));
            typeIfNotEmpty(addEmpPage.middleNameField, row.get("middleName"));
            typeIfNotEmpty(addEmpPage.lastNameField, row.get("lastName"));
            click(addEmpPage.submitBtn);
            actualMissFieldErrorMsg.add(addEmpPage.getErrorMsg(addEmpPage.missingFieldErrorMsg));
            scenarioScreenshot();
            clearField(addEmpPage.firstNameField);
        }
    }


    @Then("system should provide  appropriate error message {string}")
    public void system_should_provide_appropriate_error_message(String expected) {
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(expected, actualMissFieldErrorMsg.get(i));
        }
    }


}


