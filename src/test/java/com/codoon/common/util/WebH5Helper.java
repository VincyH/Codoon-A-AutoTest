package com.codoon.common.util;

import io.sikuppium.driver.SikuppiumDriver;

import java.util.Set;

/**
 * Created by huangjingqing on 17/5/11.
 */
public class WebH5Helper {
    private static WebH5Helper instance;
    private SikuppiumDriver driver;

    private WebH5Helper(SikuppiumDriver driver) {
        this.driver = driver;
    }

    public static WebH5Helper getInstance(SikuppiumDriver driver) {
        if(instance == null) {
            instance = new WebH5Helper(driver);
        }
        return instance;
    }

    /**
     * NATIVE_APP , WEBVIEW_undefined
     * @param context
     */
    public void switchContext(String context){
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextName);
            if (contextName.contains(context)){
                driver.context(contextName);
            }else
            {
                System.out.println("no "+context);
            }
        }
    }
}
