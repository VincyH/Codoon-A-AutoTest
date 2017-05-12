package com.codoon.common.model.drycargo.handpick;

import com.codoon.common.util.DeviceHelper;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.sikuppium.driver.SikuppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangjingqing on 17/3/27.
 */
public class SpecialColumn {
    private static SpecialColumn  instance;
    private SikuppiumDriver driver;
    DeviceHelper mHelper= DeviceHelper.getInstance(driver);

    public static By column = By.id("咕咚专栏");
    public static By adImg = By.id("com.codoon.gps:id/ad_img");
    public static By img = By.id("com.codoon.gps:id/imgViewIcon");

    public static By viewTitle = By.id("com.codoon.gps:id/txtViewTitle");
    public static By viewSummary = By.id("com.codoon.gps:id/txtViewSummary");

    public static By mine= By.id("com.codoon.gps:id/btnMine");


    private SpecialColumn(SikuppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
        this.driver = driver;
    }

    public static SpecialColumn getInstance(SikuppiumDriver driver) {
        if(instance == null) {
            instance = new SpecialColumn(driver);
        }
        return instance;
    }

     /*------------------------------find---------------------------------------*/




    /*------------------------------operate--------------------------------------*/


}
