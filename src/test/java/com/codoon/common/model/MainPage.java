package com.codoon.common.model;

import com.codoon.common.util.DeviceHelper;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.sikuppium.driver.SikuppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangjingqing on 17/3/27.
 */
public class MainPage {
    private static MainPage instance;
    private SikuppiumDriver driver;
    DeviceHelper mHelper= DeviceHelper.getInstance(driver);

    public static By community = By.name("社区");
    public static By find= By.name("发现");
    public static By sports = By.name("运动");
    public static By dryCargo= By.name("干货");
    public static By mine = By.name("我的");


    private MainPage(SikuppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
        this.driver = driver;
    }

    public static MainPage getInstance(SikuppiumDriver driver) {
        if(instance == null) {
            instance = new MainPage(driver);
        }
        return instance;
    }


    /*------------------------------find---------------------------------------*/


    /*------------------------------operate--------------------------------------*/

    public void gotoMainPage(){

    }

    public void gotoSportsCircle(){
        gotoMainPage();
        driver.findElement(community).click();

    }

    public void gotoFind(WebElement el){
        gotoMainPage();
        driver.findElement(find).click();

    }

    public void gotoSports(){
        gotoMainPage();
        driver.findElement(sports).click();
    }

    public void gotoMall(){
        gotoMainPage();
        driver.findElement(dryCargo).click();
    }

    public void gotoMine(){
        gotoMainPage();
        driver.findElement(mine).click();
    }










}
