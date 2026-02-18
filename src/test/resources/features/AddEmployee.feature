Feature: Add Employee to HRMS


  Background: common steps
    Given user is logged in with valid credentials
    And user navigates to Add employee page

  @regression
  Scenario:adding employee without providing ID
    When user adds employees from data table
      | firstName | middleName | lastName |
      | John      | B          | Smith    |
      | Alex      | D          | Murphy   |
      | Christine | A          | Dane     |
    Then all added employees should be present in Employee List


@regression
  Scenario: adding employee and providing own ID
    When user adds employees from excel file sheet "AddEmployees"
    Then all added employees should be present in Employee List