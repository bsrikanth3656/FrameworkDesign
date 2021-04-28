### Common Framework Design
** Common Framework Design **

### README Contents

	-[What is a Framework]
	-[Why do we need Automation framework]
	-[Data-Driven Frameworks]
	-[Keyword-Driven Framework]
	-[Purpose of Framework]
	-[Frameworks Tools]
	-[Framework Structure]

	    - [Keywords Library]
	    - [Object Repository]
	    - [Generate reports]
	    - [Screenshot]
	    - [Driver Script]
**[Example codes]**

    - [Create an instance of WebDriver]
    - [Navigate to the Totsy home page]
    - [Find an element by ID, className]
    - [Display the title of the page]
    - [Mouse hover to the Element]
    - [JavaScripts in Selenium]

**[Important Links]**

    - [Official Website]
    - [Selenium Documentation]
    - [Selenium WebDriver]
    - [Selenium Download Page]
    - [Selenium Support Page]
    - [Platforms Supports]
    - [Language Supports]
    - [Testing Frameworks]
    - [Test Design Considerations]
    - [UI Mapping]
    - [Page Object Design Pattern]
    - [Data Driven Testing]
    - [Java Documentation]

## What is a Framework?
In general, a framework is a real or conceptual structure intended to serve as a support or guide for the building of something that expands the structure into something useful.

#### Prior to knowing about the Keyword Test Automation Framework, we should know about the existing frameworks. Generally we have, 

 * **Data Driven Framework**
 * **Test Script Modularity Framework**
 * **Test Library Architecture Framework**
    
## Why do we need Automation framework?
#### Using Framework, we can solve many issues like

* Writing code once and reusing it. Significant Reduction in Testing Cycle Time
* Running the script with different set of data.
* Executing the scripts end-to-end without any manual intervention. ( If any error occurs from tool or application, Script run will stop. If we use framework, it will skip or fail that testcase and run with the next testcase.)
* With basic knowledge on tool also anyone can run and write the script. (All the script, Keywords has been written by experts, we have to know how to use those keywords)
* Can able to run scenarios by selecting YES or NO. (Modular Driven Framework)
* Maintenance becomes very easy.

### Combination of above all framework is nothing but keyword Driven Framework.

**Function Driven Framework:** Dividing the scripts into functions and reusing them.

#### Write a script for one time. Make it as a function and reuse the same function

* Read all the scenarios.
* Identify the repeated steps.
* Convert them into functions

#### Examples: Click(), Hover(), HighlightElement(). Functions

**Advantages :**

* Write once (saves time)
* Reusable
* Easy Maintenance

**Disadvantages :**

* Data is hardcoded, we can't run with multiple sets of data.
* Data Driven Framework: is a framework where test input and output values are read from data files (Excel, CSV, Database) and are loaded into variables in captured or manually coded scripts.
If we see the above example, For Login(uname) we can run the script with any data picking it from excel or CSV.


### Data-Driven Frameworks
--

A data-driven framework is where test input and output values are read from data files (ODBC sources, CVS files, Excel files, DAO objects, ADO objects, and such) and are loaded into variables in captured or manually coded scripts. In this framework, variables are used for both input values and output verification values. Navigation through the program, reading of the data files, and logging of test status and information are all coded in the test script. This is similar to table-driven testing (which is discussed shortly) in that the test case is contained in the data file and not in the script; the script is just a "driver," or delivery mechanism, for the data. In data-driven testing, only test data is contained in the data files. 

### Keyword-Driven Framework
--

This requires the development of data tables and keywords, independent of the test automation tool used to execute them and the test script code that "drives" the application-under-test and the data. Keyword-driven tests look very similar to manual test cases. In a keyword-driven test, the functionality of the application-under-test is documented in a table as well as in step-by-step instructions for each test. In this method, the entire process is data-driven, including functionality.

Before getting into the framework, lets discuss on what are the external files which will be required. The list of files that will be required in addition to selenium client driver and selenium server jar files are as below

* **TestNG**: in order to data drive our test, we would require the latest version of testNG jar file which is now testng-5.14.1.jar and can be downloaded from http://testng.org/doc/download.html

* **Apache POI**: in order to use Microsoft Excel files as data source we would need the Apache POI jar file

* **TestNG plugin for Eclipse**: This will be required to run the TestNG scripts in eclipse and the content of this plugin has to be placed inside the dropin folder inside the eclipse directory. The file can be downloaded from http://testng.org/doc/download.htmlframeworks

* **Allure in System**: in order to generate allure reports we need to install allure in our systems to install please refer https://docs.qameta.io/allure/

### Purpose:
--
To build a Keyword Driven Automation Framework which can be used across different web based applications. In this approach, the endeavor is to build a lot of applications independent reusable keyword components so that they can directly used for another web application without spending any extra effort. With this framework in place, whenever we need to automate a web based application, we would not need to start from scratch, but use the application independent keyword components to the extent possible and create application specific components for the specific needs.

### Tools
-- 

 * **Eclipse Java EE**
 * **Java 7+**
 * **WebDriver 2+**
 * **BrowserStack**
 * **SauceLabs**
 * **Object Repository**
 * **Selenium Server**
 * **Apacha POI**
 * **Apache Maven**
 * **TestNG**
 * **Jenkins**
 * **Log4j**
 * **Bugzilla**
 * **Pcloudy**
 * **Rest API**
 * **Appium**
 * **Github**
 * **XCode**
 * **Android Studio**

### Framework Structure:
--

The framework consists of the following components.

- **com.common.function.library**
    - APIFunctionLibrary.java
    - MobileFunctionLibrary.java
    - WebFunctionLibrary.java
    - TestListener.java
- **com.driver.factory** 
    - DriverScript.java
    - Apptest.java
- **com.utilities** 
    - Excelfileutil.java
    - Propertiesfileutil.java
- **test(starting point of execution)** 
    - Apptest.java
- **ConfigFile**
    - Environment.properties
    - ObjectRepository.properties
- **allure-results**
    - allure results
- **Testinput**
	- Inputsheet.xlsx
- **Testoutput**
	- Outputsheet.xlsx           
- **POM File**
	- pom.xml
- **TestNg File**
	- testng.xml
- **Readme File**
	- readme.md
	
### Keywords Library:
--
	Keyword are available in excel sheet and mapped in Driverscript.java file

### Object Repository:
--
All the element locator are kept in the Object Repository property file in config folder.

### Generate allure reports:
--
**Execute below command in command prompt after script execution complete to Generate Allure Reports**
	
	- allure serve (folder path of allure-results)

### Screenshot
--
- **For Every Step**

	Screenshot will taken and saved in Screenshot folder for every step
- **For Failed Step**

	Screenshot will taken and added in allure report	

### Driver Script:
--
	Driver script will map the keywords in excel sheet to common functions libraries.

### Example codes,
--

**Example,** Create an instance of WebDriver

 java
    WebDriver driver = new FirefoxDriver();
 

**Example,** Navigate to the example home page

 java
    driver.get("http://www.example.com");
 

**Example** Find an element by ID, className, and (ummm...)

 java
    WebElement searchBox = driver.findElement(By.name("someElement"));
 

**Example,** Display the title of the page

 java
    System.out.println("Title: " + driver.getTitle());
 

**Example,** Mouse hover to the Element

 java
    WebElement menuOption = driver.findElement(By.xpath("//a[normalize-space()='Registrar']"));
    Actions builder = new Actions(driver);    
    builder.moveToElement(menu).build().perform();
    menuOption.click();
 

**Example,** JavaScripts in Selenium

 java
    ((JavascriptExecutor)driver).executeScript("document.getElementById('someElement').click();");
 

**Example,** Selenium Regex 

Selenium supports a few methods that help match text patterns. However, selenium locators don�t accept regular expressions. Only patterns or values accept them.

**Example,** Globbing:
 java
    selenium.click("link=glob:*Gifts"); // Clicks on any link with text suffixed with 'Gifts'
    selenium.verifyTextPresent("glob:*Gifts*");
 

**Example,** Regular Expressions:[regexp, regexpi]
 java
    selenium.click("link=regexpi:^Over \\$[0-9]+$");  //matches links such as 'Over $75', 'Over $85' etc
 

**Example,** Contains:
 java
    selenium.highlight("//div[contains(@class,'cnn_sectbin')]");  //highlights the first div with class attribute that contains 'cnn_sectbin'
    selenium.highlight("css=div#cat_description:contains(\"to last\")");  //locating a div containing the text 'to last' using css selector
 

**Example,** Starts-with:
java
    selenium.click("//img[starts-with(@id,'cat_prod_image')]");  //clicks on the first image that has an id attribute that starts with 'cat_prod_image'
    selenium.click("//div[starts-with(@id,'tab_dropdown')]/a[last()]");  //clicks on the last link within the div that has a class attribute starting with 'tab_dropdown'
    selenium.click("//div[starts-with(@id,'tab_dropdown')]/a[position()=2]"); //clicks on the second link within the div that has a class attribute starting with 'tab_dropdown'
    selenium.highlight("css=div[class^='samples']"); //highlights div with class that starts with 'samples'


**Example,** Ends-with:
java
    selenium.highlight("css=div[class$='fabrics']"); //highlights div with class that ends with 'fabrics'
    selenium.click("//img[ends-with(@id,'cat_prod_image')]"); //clicks on the first image that has an id attribute that ends with 'cat_prod_image'


### Important Links


[Official Website](http://selenium-grid.seleniumhq.org)

[Selenium Documentation](http://seleniumhq.org/docs/)

[Selenium WebDriver](http://seleniumhq.org/projects/webdriver/)

[Selenium Download Page](http://seleniumhq.org/download/)

[Selenium Support Page](http://seleniumhq.org/support/)

[Platforms Supports](http://seleniumhq.org/about/platforms.html)

[Language Supports](http://seleniumhq.org/about/platforms.html#programming-languages)

[Testing Frameworks](http://seleniumhq.org/about/platforms.html#testing-frameworks)

[Test Design Considerations](http://seleniumhq.org/docs/06_test_design_considerations.html)

[UI Mapping](http://seleniumhq.org/docs/06_test_design_considerations.html#ui-mapping)

[Page Object Design Pattern](http://seleniumhq.org/docs/06_test_design_considerations.html#page-object-design-pattern)

[Data Driven Testing](http://seleniumhq.org/docs/06_test_design_considerations.html#data-driven-testing)

[Java Documentatuin](http://selenium.googlecode.com/svn/trunk/docs/api/java/index.html)w