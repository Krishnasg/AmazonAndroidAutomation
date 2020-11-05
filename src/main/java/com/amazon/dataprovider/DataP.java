package com.amazon.dataprovider;

import com.amazon.GlobalResource;
import com.amazon.util.XlDataReader;
import com.amazon.util.Constants;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Testng data provider class
 * Data read for each test method follows the following steps
 * 1. Each method is triggered to get the data for the test
 * 2. Method's class name becomes the Sheet name in the Xl sheet
 * 3. test method name becomes the test (first column) in the sheet
 * 4. gets all the test data rows for the tes metho
 * 5. Returns the Object array of Map<String, String> object
 *
 */
public class DataP {
    static Logger logger =  Logger.getLogger(DataP.class);

    /**
     * Fetches the test data from the xl file
     * @return the Object array
     */
    @DataProvider(name = "data-provider")
    public Object[][] dpMethod(Method method) {
        String sheetName = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        System.out.println(String.format("Fetching %s method data", methodName));
        logger.info(String.format("Fetching %s method data", methodName));
        XlDataReader dataReader = null;
        String testDataFilePath = Constants.ROOT_DIR+ GlobalResource.getInstance().getProperties().getProperty("data.file");
        try {
            dataReader = new XlDataReader(testDataFilePath);
        } catch (IOException e) {
            logger.info(String.format("Test data file exception, file path = %s", testDataFilePath));
        }
        dataReader.setSheet(sheetName);

        List<Map<String, Object>> dataList = dataReader.getTestData(methodName);
        Object[][] objects = new Object[dataList.size()][1];
        for(int i = 0; i < dataList.size(); i++) {
            objects[i][0] = dataList.get(i);
        }
        return objects.length == 0 ? new Object[1][1] : objects;
    }
}
