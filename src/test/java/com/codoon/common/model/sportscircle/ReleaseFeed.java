package com.codoon.common.model.sportscircle;

import com.codoon.common.model.SportsCirclePage;
import com.codoon.common.util.DeviceHelper;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.sikuppium.driver.CustomImageElement;
import io.sikuppium.driver.ImageElement;
import io.sikuppium.driver.SikuppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangjingqing on 17/3/27.
 */
public class ReleaseFeed {
    private static ReleaseFeed  instance;
    private SikuppiumDriver driver;
    DeviceHelper mHelper= DeviceHelper.getInstance(driver);

    public static By selectIcon = By.id("com.codoon.gps:id/isselected");
    public static By photoBtn = By.id("com.codoon.gps:id/btn_photo");
    public static By cameraBtn = By.id("com.codoon.gps:id/btn_camera");
    public static By finish = By.id("com.codoon.gps:id/finish");

    public static By content = By.id("com.codoon.gps:id/et_content");
    public static By emo= By.id("com.codoon.gps:id/btnEmo");
    public static By location= By.id("com.codoon.gps:id/btn_location");
    public static By tagTxt = By.id("com.codoon.gps:id/tag_txt");
    public static By delImg = By.id("com.codoon.gps:id/del_img");


    public static By backBtn = By.id("com.codoon.gps:id/btnReturnback");
    public static By close = By.id("com.codoon.gps:id/iv_close");

    public static By addFeed= By.id("com.codoon.gps:id/btn_add_feed");
    public static By initagTxT= By.name("# 添加话题标签");


    public static String TEXT="this is a joke";


    private ReleaseFeed(SikuppiumDriver driver) {
      //  PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
        this.driver = driver;
    }

    public static ReleaseFeed getInstance(SikuppiumDriver driver) {
        if(instance == null) {
            instance = new ReleaseFeed(driver);
        }
        return instance;
    }

     /*------------------------------find---------------------------------------*/




    /*------------------------------operate--------------------------------------*/
     public void gotoReleaseTable(){
         driver.findElement(ReleaseFeed.selectIcon).click();
         driver.findElement(ReleaseFeed.finish).click();
         driver.findElement(content).click();
     }

    public void addTagTxt(String tag){
        driver.findElement(tagTxt).click();
        driver.findElement(By.id("com.codoon.gps:id/searchbar_edit")).sendKeys(tag);
        driver.findElement(By.id("com.codoon.gps:id/create_label_btn")).click();
    }

     public void releaseFeed(boolean hasEmo,String local,String hasTopic) throws InterruptedException {
         if(!mHelper.isExistBySelector(driver,location,5)) {
             gotoReleaseTable();
         }

         driver.findElement(content).sendKeys("this is a joke");
         if(hasEmo){
            driver.findElement(emo).click();
            mHelper.waitForImageElement("emo",5).tap();
        }
         if(local!=null){
            driver.findElement(location).click();
            driver.findElement(By.name("成都市")).click();
            driver.findElement(close).click();
        }
         if(hasTopic!=null){
            addTagTxt("jokes");
        }
         driver.findElement(addFeed).click();

    }






}
