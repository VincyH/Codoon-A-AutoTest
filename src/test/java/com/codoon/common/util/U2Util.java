package com.codoon.common.util;

import io.sikuppium.driver.SikuppiumDriver;

/**
 * Created by huangjingqing on 17/5/11.
 */
public class U2Util {
    private static U2Util instance;
    private SikuppiumDriver driver;

    private U2Util(SikuppiumDriver driver) {
        this.driver = driver;
    }

    public static U2Util getInstance(SikuppiumDriver driver) {
        if(instance == null) {
            instance = new U2Util(driver);
        }
        return instance;
    }

    public void clickByU2(String u2){
        // demo    driver.findElementByAndroidUIAutomator("new UiSelector().clickable(true)").click();
        driver.findElementByAndroidUIAutomator(u2).click();

    }


}
