# Amazon Android Automation

## Directory Structure

```java
app -- Amazon_shopping.apk
screenshots -- 
src
|-- /main
|   |-- /resources
|   |   |-- log4j.properties
|   |   `-- config.properties
|   `--  java
|       `--  com
|           `--  amazon
|               `--  utils
|                   |--  Utils.java
|                   `--  XlDataReader.java
|--  test
|   |--  java
|   |   `--  com
|   |       |--  enums
|   |       |   `--  ScrollDirection.java
|   |       |--  GlobalResource.java
|   |       |--  modules
|   |       |   |--  BasePage.java
|   |       |   |--  checkout
|   |       |   |   |--  PaymentPage.java
|   |       |   |   |--  DeliverAddressPage.java
|   |       |   |   `--  CheckoutPage.java
|   |       |   |--  home
|   |       |   |   `--  HomePage.java
|   |       |   |--  product
|   |       |   |   |--  Product.java
|   |       |   |   |--  ProductDetailsPage.java
|   |       |   |   `--  ProductListPage.java
|   |       |   |--  account
|   |       |   |   |--  AccountPage.java
|   |       |   |   `--  LoginPage.java
|   |       |   `--  cart
|   |       |       `--  CartPage.java
|   |       |--  dataprovider
|   |       |   `--  DataP.java
|   |       |--  testnglisteners
|   |       |   |--  RetryAnalyzer.java
|   |       |   `--  TestListener.java
|   |       |--  util
|   |       |   |--  ScrollAndSwipe.java
|   |       |   `--  Constants.java
|   |       `--  test
|   |           |--  BaseTest.java
|   |           |--  LoginTest.java
|   |           `--  PurchaseTest.java
|    `--  resources
|       |--  amazon.xml
|       `--  AmazonTestData.xlsx
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
modules | based on the app flow, modules are created. each module contains classes which represents a screen and it's operations are defined as methods
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
