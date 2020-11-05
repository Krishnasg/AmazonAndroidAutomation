# Amazon Android Automation

## Directory Structure

```java|-- app
|   `-- Amazon_shopping.apk
|-- src
|   |-- main
|   |   |-- java
|   |   |   `-- com
|   |   |       `-- amazon
|   |   |           |-- enums
|   |   |           |   `-- ScrollDirection.java
|   |   |           |-- GlobalResource.java
|   |   |           |-- util
|   |   |           |   |-- Constants.java
|   |   |           |   |-- HelperUtil.java
|   |   |           |   |-- ScrollAndSwipe.java
|   |   |           |   |-- CustomSoftAlert.java
|   |   |           |   `-- XlDataReader.java
|   |   |           |-- dataprovider
|   |   |           |   `-- DataP.java
|   |   |           |-- testnglisteners
|   |   |           |   |-- TestListener.java
|   |   |           |   `-- RetryAnalyzer.java
|   |   |           `-- pages
|   |   |               |-- CartPage.java
|   |   |               |-- DeliverAddressPage.java
|   |   |               |-- PaymentPage.java
|   |   |               |-- Product.java
|   |   |               |-- ProductDetailsPage.java
|   |   |               |-- ProductListPage.java
|   |   |               |-- BasePage.java
|   |   |               |-- CheckoutPage.java
|   |   |               |-- HomePage.java
|   |   |               |-- AccountBasePage.java
|   |   |               `-- LoginPage.java
|   |   `-- resources
|   |       |-- config.properties
|   |       |-- log4j.properties
|   |       `-- password.txt
|   `-- test
|       |-- resources
|       |   |-- AmazonTestData.xlsx
|       |   `-- amazon.xml
|       `-- java
|           `-- com
|               `-- ui
|                   `-- test
|                       |-- PurchaseTest.java
|                       |-- BaseTest.java
|                       `-- LoginTest.java
|-- pom.xml
|-- log
|   `-- logging.log
|-- report.html
|-- screenshots
|   `-- ValidateLoginSuccess.png
```
### resources ###
name | desc
------------ | -------------
AmazonTestData.xlsx | All tests data are put in this file. Each Test class name under com.test package becomes a sheet name in the file and each sheet will contain test data for all methods of the test class.
amazon.xml | testng suite file
log4j.properties | log4j librarie's config
config.properties | contains Appium driver capabilities, imlicity and explicit values

### com.amazon.utils ###
name | desc
------------ | -------------
Utils.java | Contains one method to get the random integer for a given bound value
XlDataReader.java | Implements xlsx file read functionality using apache.poi library

### test ###
name | desc
------------ | -------------
pages | based on the app flow, pages are created. each page represents a screen and it's operations are defined as methods
dataprovider | DataP.java class is data provider class which fetches the test data from AmazonTestData.xlsx based on the current running test
testnglisteners | TestListener -> Extent report configured. RetryAnalyzer -> Test retry logic is implemented ( if test fails)
util | Contains a scroll and swipe implemented class and constants class
test | Contains all the test flows
BasePage.java | Base class which contains helper methods for fetching elements 
GlobalResource.java | Singleton class. which loads config.properties


### Test Result ###
* if test is failed, a screenshot will be created and it will be present in screenshots folder. image file name will be test method name
* report.html file gets created in root directory of the project. This is an output of extent report library
* logging.log file gets created in root directory of the project. This is an output of log4j library

### Run ###
* clone/download the repo
* Update the driver capabilities in config.properties file
* Update valid user credentials in AmazonTestData.xlsx and run the following
* Have the appium server running
```bash
 mvn clean test
```
