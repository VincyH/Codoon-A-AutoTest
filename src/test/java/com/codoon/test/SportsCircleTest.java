package com.codoon.test;

import com.codoon.common.model.MainPage;
import com.codoon.common.model.CommunityPage;
import com.codoon.common.model.drycargo.DynamicState;
import com.codoon.common.model.drycargo.ReleaseFeed;
import com.codoon.common.model.drycargo.handpick.Topic;
import com.codoon.common.util.Description;
import com.codoon.common.util.DeviceHelper;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.sikuppium.driver.ImageElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.*;
import java.util.Set;
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

  }

    /*-------------------------------------每条case执行后运行---------------------------------------*/

    @AfterMethod
    public void after() {
        super.after();
    }

    /*-----------------------------------------testCase--------------------------------------------*/

    @Test
    @Description(steps = "test",
                 expectation = "成功",
                 priority = Description.P1)
    public void test000Test() throws InterruptedException {
        driver.findElement(CommunityPage.banner).click();
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextName);

        }

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
        driver.findElement(CommunityPage.dynamicState).click();
        ImageElement el=waitForImageElement("addfeed",5);
        boolean feedAddIcon=(el!=null);

        driver.findElement(CommunityPage.special).click();
        driver.findElement(CommunityPage.addFeed).click();
        releaseFeed.gotoReleaseTable();

        boolean hasTopicTag=mHelper.isExistBySelector(driver,ReleaseFeed.initagTxT,5);

        Assert.assertTrue(feedAddIcon&&hasTopicTag,"Error in feedAddIcon is "+feedAddIcon+" and hasTopicTag is "+hasTopicTag);

    }

    @Test
    public void test002CheckBanner() throws Exception {

        driver.findElement(CommunityPage.banner).click();

        boolean idCheck=mHelper.isExistBySelector(driver, CommunityPage.friendsAdd,1);
        Assert.assertTrue(!idCheck,"Error in banner txt title is exists");
    }

    @Test
    public void test003AddFeed() throws InterruptedException {
        driver.findElement(CommunityPage.addFeed).click();
        releaseFeed.releaseFeed(true,null,null);

        mHelper.swipeDown();
        boolean feedRealse=mHelper.searchTextContainsBySelector(DynamicState.tvContent,ReleaseFeed.TEXT,60000);

        Assert.assertTrue(feedRealse,"Error is not find the feed text contains "+ReleaseFeed.TEXT);
    }

    @Test
    public void test004CheckTopicTag(){
        driver.findElement(CommunityPage.addFeed).click();
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
        driver.findElement(CommunityPage.topic).click();

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
        driver.findElement(CommunityPage.dynamicState).click();
        driver.findElement(MainPage.find).click();


        boolean handPickTab=driver.findElement(CommunityPage.handPick).isSelected();
        boolean dynamicStateTab=driver.findElement(CommunityPage.dynamicState).isSelected();

        Assert.assertTrue(!handPickTab&&dynamicStateTab,"Error is !handPickTab:"+!handPickTab+" dynamicStateTab"+dynamicStateTab);
    }

    @Test
    public void test007ScorllCheckTab() throws InterruptedException {
        boolean tab1=driver.findElement(CommunityPage.handPick).isSelected();

        mHelper.swipeLeft();
        boolean tab1_2=driver.findElement(CommunityPage.handPick).isSelected();
        boolean tab2=driver.findElement(CommunityPage.dynamicState).isSelected();

        Assert.assertTrue(tab1&&!tab1_2&&tab2,"Error is tab1:"+tab1+" tab1_2:"+tab1_2+" tab2:"+tab2);
    }

    @Test
    public void test008TopicElemnt(){
        driver.findElement(CommunityPage.topic).click();

        boolean title=mHelper.isExistBySelector(driver,Topic.title,5);
        boolean img=mHelper.isExistBySelector(driver,Topic.img,5);
        boolean personer=mHelper.isExistBySelector(driver,Topic.personer,5);
        boolean tvCount=mHelper.isExistBySelector(driver,Topic.tvCount,5);

        Assert.assertTrue(title&&img&&personer&&tvCount,"Error is title:"+title+" img:"+img+" personer:"+personer+" tvCount:"+tvCount);

    }


}
