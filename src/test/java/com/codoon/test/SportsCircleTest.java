package com.codoon.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Base64;
import com.codoon.common.model.MainPage;
import com.codoon.common.model.SportsBeginPage;
import com.codoon.common.model.SportsCirclePage;
import com.codoon.common.model.sportscircle.DynamicState;
import com.codoon.common.model.sportscircle.ReleaseFeed;
import com.codoon.common.model.sportscircle.handpick.Topic;
import com.codoon.common.util.DeviceHelper;
import com.codoon.common.util.ImageMatch;
import com.codoon.common.util.MatchPoint;
import com.meizu.flymecommon.util.ContrastPhoto;
import com.meizu.flymecommon.util.FileUtils;
import com.sun.jna.platform.unix.X11;
import com.sun.org.glassfish.gmbal.Description;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.sikuppium.driver.CapabilitiesFactory;
import io.sikuppium.driver.CustomImageElement;
import io.sikuppium.driver.ImageElement;
import io.sikuppium.driver.SikuppiumDriver;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.sikuli.api.Screen;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class SportsCircleTest extends BaseTest {
    public static   DeviceHelper mHelper;
    static ReleaseFeed releaseFeed;



    /*------------------------------------每条case执行前运行--------------------------------------*/

    @BeforeMethod
    public void before() throws Exception {
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
        mHelper=DeviceHelper.getInstance(driver);
        releaseFeed=ReleaseFeed.getInstance(driver);

        driver.findElement(MainPage.sportsCircle).click();
        if(mHelper.isExistBySelector(driver,SportsCirclePage.handPick,5)&&!driver.findElement(SportsCirclePage.handPick).isSelected()){
            driver.findElement(SportsCirclePage.handPick).click();
        }
  }

    /*----------------------------------每条case执行后运行---------------------------------------*/

    @AfterMethod
    public void after() {
        super.after();
    }

    /*----------------------------------------testCase--------------------------------------------*/

    @Test
    public void test000Test() throws InterruptedException {


    }


    @Test//(groups = { "testA" })
    public void test000() throws InterruptedException, IOException {
    //初始化
//        List<MatchPoint> data = ImageMatch.findObject("test.jpg");//匹配图片
//        if (data != null) {//如果data不为null，说明匹配成功
//          //  mDevice.click(data.get(0).point.x, data.get(0).point.y);//点击
//            System.out.println("********** true");
//        }else{
//            System.out.println("********** true");
//        }
      //  mHelper.isExistImageElement("feedadd",10);
        boolean imageElement=imageMatch("cc2",null,10);

     // ImageElement imageElement2=waitForImageElement("addfeed",10);
        if (imageElement) {
            System.out.println("********** ImageElement is true");
        }else{
            System.out.println("********** ImageElement is false");
        }

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test//(groups = { "testB" })
    public void test001TopicList() throws InterruptedException {
        driver.findElement(SportsCirclePage.dynamicState).click();
        ImageElement el=waitForImageElement("addfeed",5);
        boolean feedAddIcon=(el!=null);

        driver.findElement(SportsCirclePage.special).click();
        driver.findElement(SportsCirclePage.addFeed).click();
        releaseFeed.gotoReleaseTable();

        boolean hasTopicTag=mHelper.isExistBySelector(driver,ReleaseFeed.initagTxT,5);

        Assert.assertTrue(feedAddIcon&&hasTopicTag,"Error in feedAddIcon is "+feedAddIcon+" and hasTopicTag is "+hasTopicTag);

    }

    @Test
    public void test002CheckBanner() throws Exception {

        driver.findElement(MainPage.sportsCircle).click();
        driver.findElement(SportsCirclePage.banner).click();

        boolean idCheck=mHelper.isExistBySelector(driver,SportsCirclePage.friendsAdd,1);
        Assert.assertTrue(!idCheck,"Error in banner txt title is exists");
    }

    @Test
    public void test003AddFeed() throws InterruptedException {
        driver.findElement(SportsCirclePage.addFeed).click();
        releaseFeed.releaseFeed(true,null,null);

        mHelper.swipeDown();
        boolean feedRealse=mHelper.searchTextContainsBySelector(DynamicState.tvContent,ReleaseFeed.TEXT,60000);

        Assert.assertTrue(feedRealse,"Error is not find the feed text contains "+ReleaseFeed.TEXT);
    }

    @Test
    public void test004CheckTopicTag(){
        driver.findElement(SportsCirclePage.addFeed).click();
        releaseFeed.gotoReleaseTable();
        releaseFeed.addTagTxt("test004");

        boolean hasTagTxT=mHelper.isExistBySelector(driver,By.name("test004"),5);
        boolean topicTag=mHelper.isExistBySelector(driver,ReleaseFeed.initagTxT,5);
        driver.findElement(ReleaseFeed.delImg).click();
        boolean hasTopicTag=mHelper.isExistBySelector(driver,ReleaseFeed.initagTxT,5);

        Assert.assertTrue(hasTagTxT&&!topicTag&&hasTopicTag,"Error is hasTagTxt:"+hasTagTxT+" !topicTag:"+!topicTag+" hasTopicTag:"+hasTopicTag);
    }

    @Test
    public void test005TopicListAddFeed() throws InterruptedException {
        driver.findElement(SportsCirclePage.topic).click();

        driver.findElement(Topic.img).click();
        String txt=driver.findElement(Topic.title).getText().substring(1).trim();

        driver.findElement(Topic.join).click();
        releaseFeed.gotoReleaseTable();
        boolean hasTagTxT=mHelper.isExistBySelector(driver,By.name(txt),5);
        boolean delImg=mHelper.isExistBySelector(driver,ReleaseFeed.delImg,5);

        releaseFeed.releaseFeed(false,null,null);
        mHelper.swipeDown();
        boolean feedRealse=mHelper.searchTextContainsBySelector(DynamicState.tvContent,ReleaseFeed.TEXT,60000);

        Assert.assertTrue(hasTagTxT&&!delImg&&feedRealse,"Error is hasTagTxt:"+hasTagTxT+" !delImg:"+!delImg+" feedRealse:"+feedRealse);

    }

    @Test
    public void test006CheckTab(){
        driver.findElement(SportsCirclePage.dynamicState).click();
        driver.findElement(MainPage.find).click();

        driver.findElement(MainPage.sportsCircle).click();
        boolean handPickTab=driver.findElement(SportsCirclePage.handPick).isSelected();
        boolean dynamicStateTab=driver.findElement(SportsCirclePage.dynamicState).isSelected();

        Assert.assertTrue(!handPickTab&&dynamicStateTab,"Error is !handPickTab:"+!handPickTab+" dynamicStateTab"+dynamicStateTab);
    }

    @Test
    public void test007ScorllCheckTab() throws InterruptedException {
        boolean tab1=driver.findElement(SportsCirclePage.handPick).isSelected();

        mHelper.swipeLeft();
        boolean tab1_2=driver.findElement(SportsCirclePage.handPick).isSelected();
        boolean tab2=driver.findElement(SportsCirclePage.dynamicState).isSelected();

        Assert.assertTrue(tab1&&!tab1_2&&tab2,"Error is tab1:"+tab1+" tab1_2:"+tab1_2+" tab2:"+tab2);
    }

    @Test
    public void test008TopicElemnt(){
        driver.findElement(SportsCirclePage.topic).click();

        boolean title=mHelper.isExistBySelector(driver,Topic.title,5);
        boolean img=mHelper.isExistBySelector(driver,Topic.img,5);
        boolean personer=mHelper.isExistBySelector(driver,Topic.personer,5);
        boolean tvCount=mHelper.isExistBySelector(driver,Topic.tvCount,5);

        Assert.assertTrue(title&&img&&personer&&tvCount,"Error is title:"+title+" img:"+img+" personer:"+personer+" tvCount:"+tvCount);

    }


}
