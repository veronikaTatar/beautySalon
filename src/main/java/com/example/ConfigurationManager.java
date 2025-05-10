package com.example;

import java.util.ResourceBundle;

public class ConfigurationManager {

    private static ConfigurationManager instance;
    private ResourceBundle resourceBundle;

    //класс извлекает информацию из файла config.properties
    private static final String BUNDLE_NAME = "config";

    public static final String DATABASE_DRIVER_NAME =
            "DATABASE_DRIVER_NAME";
    public static final String DATABASE_URL =
            "DATABASE_URL";
    public static final String ERROR_PAGE_PATH =
            "ERROR_PAGE_PATH";
    public static final String LOGIN_PAGE_PATH =
            "LOGIN_PAGE_PATH";
    public static final String CLIENT_PAGE_PATH =
            "CLIENT_PAGE_PATH";
    public static final String ADMIN_PAGE_PATH =
            "ADMIN_PAGE_PATH";
    public static final String VIEW_SERVICES_PAGE = "/viewServices.jsp";

    public static final String REGISTRATION_PAGE_PATH =
            "REGISTRATION_PAGE_PATH";

    public static final String  BOOK_PAGE_PATH = "BOOK_PAGE_PATH";


    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
            instance.resourceBundle =
                    ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }
    public String getProperty(String key) {
        return (String)resourceBundle.getObject(key);
    }
}

