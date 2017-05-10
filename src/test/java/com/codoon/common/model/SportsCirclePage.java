package com.codoon.common.model;

import com.codoon.common.util.DeviceHelper;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.sikuppium.driver.SikuppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangjingqing on 17/3/27.
 */
public class SportsCirclePage {
    private static SportsCirclePage instance;
    private SikuppiumDriver driver;
    DeviceHelper mHelper= DeviceHelper.getInstance(driver);

    public static By friendsAdd = By.id("com.codoon.gps:id/friends_add");
    public static By addFeed = By.id("com.codoon.gps:id/btn_add_feed");

    public static By handPick = By.name("精选");
    public static By dynamicState= By.name("动态");
    public static By banner= By.id("com.codoon.gps:id/auto_scroll_adver_pager");


    public static By specialColumn = By.name("专栏");
    public static By topic= By.name("话题");
    public static By live = By.name("直播");
    public static By vicinity= By.name("附近");



    public static By specialFile= By.name("精选文章");
    public static By hotTopic= By.name("热门话题");
    public static By article_img = By.id("com.codoon.gps:id/article_img");

    public static By recommendationActivity = By.name("推荐活动");
    public static By equipmentEvaluat = By.name("装备评测");
    public static By discussion= By.name("讨论专区");
    public static By article_video_img = By.id("com.codoon.gps:id/article_video_img");

    public static By recommendationUser= By.name("推荐用户");
    public static By gallery_item_image= By.id("com.codoon.gps:id/gallery_item_image");

    public static By special = By.name("精选");
    public static By feed_img = By.id("com.codoon.gps:id/feed_left");


    public static By more = By.id("com.codoon.gps:id/title_bar_more");


    private SportsCirclePage(SikuppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
        this.driver = driver;
    }

    public static SportsCirclePage getInstance(SikuppiumDriver driver) {
        if(instance == null) {
            instance = new SportsCirclePage(driver);
        }
        return instance;
    }



    /*------------------------------find---------------------------------------*/




    /*------------------------------operate--------------------------------------*/
    public void  gotoMainPage(){
        driver.findElement(MainPage.sportsCircle).click();
        if(!driver.findElement(SportsCirclePage.specialColumn).isSelected()){
            driver.findElement(SportsCirclePage.specialColumn).click();
        }
    }





}
