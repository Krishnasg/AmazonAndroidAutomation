package com.amazon.util;

import java.io.File;

public class Constants {
    public static final String ROOT_DIR = System.getProperty("user.dir")+File.separator;
    public static final String DRIVER_PROPERTIES_FILE_PATH = ROOT_DIR+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"config.properties";
    public static final String EXTENT_REPORT_FILE = ROOT_DIR+"report.html";
}
