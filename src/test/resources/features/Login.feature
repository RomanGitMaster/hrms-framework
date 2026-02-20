Feature: Login Validation for HRMs Portal

  Background:
    Given user is on the login page of HRMS

  @smoke
  Scenario Outline: empty username or password show Required error message
    When user enters username "<username>" and password "<password>"
    Then user is prompted with error "<errorMsg>"
    Examples:
      | username | password | errorMsg |
      |          | pass123  | Required |
      | 123_user |          | Required |


  @smoke
  Scenario Outline: login fails if either  username or password is incorrect
    When user enters username "<username>" and password "<password>"
    Then user is prompted with "<errorMsg>" message
    Examples:
      | username | password | errorMsg            |
      | 123_user | pass123  | Invalid credentials |
      | user@hrm | user@123 | Invalid credentials |

  @smoke
  Scenario:after invalid credentials user is able to attempt valid login
    When user enters incorrect  "invalidUser" or "invalidPass"
    Then user is prompted with "Invalid credentials" message
    When user enters valid username and password
    Then user should be logged in successfully