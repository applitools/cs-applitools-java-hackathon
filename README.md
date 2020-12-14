# cs-applitools-java-hackathon

## Pre-requisites:
1. Install Maven from [here](https://maven.apache.org/install.html), if you are running on mac, you can also install it using the brew,
simply run ```brew install maven```
2. Install ChromeDriver from [here](https://chromedriver.chromium.org/downloads) if you are running on mac, you can also install it using the brew,
   simply run ```brew install chromedriver```
3. Register to Applitools and [create an account](https://auth.applitools.com/users/register)  
4. Ensure you have your Applitools API Key 


   
## Hackathon Overview
Imagine you wrote tests for the [1st Version of the App (V1)](https://demo.applitools.com/hackathon.html)

Then Next [Version (V2)](https://demo.applitools.com/hackathonV2.html) came along later and has changes. (including bugs) 
Some of these bugs are functional bugs, and some of are visual bugs. 

### The Challenge
Write an automated test for both versions which leverages Applitools.
Run both a traditional test (provided), and the new test you wrote.
Compare the results.

### Instructions

1) Review Traditional Script (provided) 
TraditionalTestSuite has been provided to you, as we assume you have already written these scripts before.
Analyze them, make sure they are ok, and feel free to add any additional coverage/lines of code you see fit.

2) Run the test suite against both Version 1 and Version 2.
You are going to find a lot of failures in Version 2. (changes have been made, including bugs)

3) Review the scripts again, and review how many assertions and locators required to cover all the elements in the page.

4) Open the VisualAISuite and set your ApiKey in string 'config.setApiKey("...")' (or comment the string and set APPLITOOLS_API_KEY environment variable).

5) Modified the different tests to include visual assertion to achieve the same coverage as with the TraditionalSuiteEmptyTemplate.

5) Run the same test again and see all the differences between the screenshots of the 1st version and the 2nd version of the app.

Note: When you run the tests against V2, you’ll see differences in screenshots because the app is actually different. 
You should see exactly what those differences are (highlighted in pink) in Applitools dashboard. 

### Advanced Use Cases
1) AB Testing -  [This page](https://abtestautomation.com) comes in two modes, A and B which are randomly selected when navigating to the page.

2) Testing Canvas Element - Assume the values of the chart are coming from a test data and the test data will not change across versions.  Validate that the bar chart and representing that data (number of bars and their heights). They should remain the same across versions. Then click on the “Show data for next year” button. This should add the data for the year 2019. Verify that this data set is added for the year 2019.

3) Cross Browser Testing - Run the above tests across multiple browsers using [Appitlools Ultra Fast Grid](https://applitools.com/product-ultrafast-test-cloud/), see VisualAISuiteFullSolutionUFG example.

4) Accessibility Testing - Use [Applitools Contrast Afvisor](https://applitools.com/docs/features/contrast-accessibility.html) to validate for accessibility violations.  

## Running the example:
 1. Download the example
    * Option 1: `git clone https://github.com/applitools/cs-applitools-java-hackathon`
    * Option 2: Download it as a Zip file and extract it
    
2. Run the project
### In order to run the project from IDE perform next steps:

   2.1. Navigate to the recently cloned folder cs-applitools-java-hackathon.
   
   2.2.  Import the project as a Maven project in IntelliJ IDEA or Eclipse.
   
   2.3. Set Project SDK to your JDK (installed in Pre-requisites) in Intellij - File > Project Structure > Project.
   
   2.4. Run or Debug class TraditionalSuiteEmptyTemplate or method test().
   
### In order to run the tests from commandLine:
   4.1. Open a commandLine window
   
   4.2. Navigate to the recently cloned folder cs-applitools-java-hackathon.
   
   4.3. Run one of the following commands:
   Run the Traditional Test Suite on Version 1
   
    mvn -Dtest=TraditionalSuiteEmptyTemplate test -DargLine="-DisOriginalApp=true"

   Run the Traditional Test Suite on Version 2
       
    mvn -Dtest=TraditionalSuiteEmptyTemplate test -DargLine="-DisOriginalApp=false"
    
   Run the VisualAISuite Test Suite on Version 1
       
    mvn -Dtest=VisualAISuite test -DargLine="-DisOriginalApp=true"
    
   Run the VisualAISuite Test Suite on Version 2
           
    mvn -Dtest=VisualAISuite test -DargLine="-DisOriginalApp=false"
   
## To see the full solution 
  5. Run the VisualAISuiteFullSolution
  
  Run the VisualAISuiteFullSolution Test Suite on Version 1
        
      mvn -Dtest=VisualAISuiteFullSolution test -DargLine="-DisOriginalApp=true"
      
  Run the VisualAISuiteFullSolution Test Suite on Version 2
             
      mvn -Dtest=VisualAISuiteFullSolution test -DargLine="-DisOriginalApp=false"
      
      
## Cross Browser Testing Using the Ultra Fast Grid 
  5. Run the VisualAISuiteFullSolutionUFG
  
  Run the VisVisualAISuiteFullSolutionUFG Test Suite on Version 1
        
      mvn -Dtest=VisualAISuiteFullSolutionUFG test -DargLine="-DisOriginalApp=true"
      
  Run the VisualAISuiteFullSolutionUFG Test Suite on Version 2
             
      mvn -Dtest=VisualAISuiteFullSolutionUFG test -DargLine="-DisOriginalApp=false"      
