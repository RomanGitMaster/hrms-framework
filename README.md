# HRMS Automation Framework
UI automation framework for HRMS application using Selenium 4 + Cucumber (BDD).

## Tech Stack
- Java 17
- Maven
- Selenium 4
- Cucumber
- JUnit/TestNG

## Framework Features
- Page Object Model
- Reusable utilities (Browser handling, Waits, Actions)
- ConfigReader (run tests via properties file)
- Screenshot capture on pass or failure
- Reporting integration

## How to run
Run all tests:
mvn test

## Reports 
Reports are generated in :
target/cucumber-html-reports/

## Tags
@smoke, @regression