package com.codoon.common.model;

import com.codoon.common.util.DeviceHelper;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.sikuppium.driver.SikuppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangjingqing on 17/5/11.
 */
public class DryCargoPage {
    private static DryCargoPage instance;
    private SikuppiumDriver driver;
    DeviceHelper mHelper= DeviceHelper.getInstance(driver);

    public static By friendsAdd = By.id("com.codoon.gps:id/friends_add");
    public static By addFeed = By.id("com.codoon.gps:id/btn_add_feed");

    public static By recommend = By.name("推荐");
    public static By run= By.name("跑步");
    public static By fitness = By.name("健身");
    public static By ride= By.name("骑行");
    public static By personal= By.name("个性");

    public static By headtitle1= By.name("");
    public static By headtitle2= By.name("");
    public static By headtitle3= By.name("");
    public static By headtitle4= By.name("");
    public static By headtitle5= By.name("");

    private DryCargoPage(SikuppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
        this.driver = driver;
    }

    public static DryCargoPage getInstance(SikuppiumDriver driver) {
        if(instance == null) {
            instance = new DryCargoPage(driver);
        }
        return instance;
    }


}
