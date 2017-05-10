package com.codoon.common.util;

import android.view.accessibility.AccessibilityNodeInfo;
import io.appium.java_client.TouchAction;
import io.sikuppium.driver.ImageElement;
import io.sikuppium.driver.SikuppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.SystemClock;
import org.openqa.selenium.remote.server.handler.BySelector;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import android.graphics.Rect;



import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Thread.sleep;
import static org.testng.Assert.assertTrue;

/**
 * Created by huangjingqing on 17/3/28.
 */
public class DeviceHelper {

    private static DeviceHelper instance;
    private SikuppiumDriver driver;
    private  static  int DISPLAY_WIDTH;
    private  static  int DISPLAY_HEIGHT;

    private DeviceHelper(SikuppiumDriver driver) {
        this.driver = driver;
    }

    public static DeviceHelper getInstance(SikuppiumDriver driver) {
        if(instance == null) {
            DISPLAY_WIDTH=driver.manage().window().getSize().width;
            DISPLAY_HEIGHT=driver.manage().window().getSize().height;
            instance = new DeviceHelper(driver);
        }

        return instance;
    }

    /**
     * 滑动
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param steps 步长
     * @throws InterruptedException
     */
    public void swipe(int startX, int startY, int endX, int endY, int steps) throws InterruptedException {
        Thread.sleep(1000L);
        driver.swipe(startX, startY, endX, endY, steps);
    }

    /**
     * 以常速向上滑动半屏
     * @throws InterruptedException
     */
    public void swipeUp() throws InterruptedException {
        Thread.sleep(1000L);
        driver.swipe(DISPLAY_WIDTH/ 2, DISPLAY_HEIGHT/ 2, DISPLAY_WIDTH/2, 5, 4000);
    }

    /**
     * 以常速向上滑动半屏
     * @param times 步数
     * @throws InterruptedException
     */
    public void swipeUp(int times) throws InterruptedException {
        Thread.sleep(1000L);
        for(int i=0;i<times;i++){
            driver.swipe(DISPLAY_WIDTH/ 2, DISPLAY_HEIGHT/ 2, DISPLAY_WIDTH/2, 5, 4000);
            Thread.sleep(1000L);
        }
    }

    /**
     * 以常速向下滑动半屏
     * @throws InterruptedException
     */
    public void swipeDown() throws InterruptedException {
        Thread.sleep(1000L);
        driver.swipe(DISPLAY_WIDTH/ 2, DISPLAY_HEIGHT/ 2, DISPLAY_WIDTH/ 2, DISPLAY_HEIGHT-5, 4000);
    }

    /**
     * 以常速向上滑动半屏
     * @param times 步数
     * @throws InterruptedException
     */
    public void swipeDown(int times) throws InterruptedException {
        Thread.sleep(1000L);
        for(int i=0;i<times;i++){
            driver.swipe(DISPLAY_WIDTH/ 2, DISPLAY_HEIGHT/ 2, DISPLAY_WIDTH/ 2, DISPLAY_HEIGHT-5, 4000);
            Thread.sleep(1000L);
        }

    }

    /**
     * 以常速向左滑动
     * @throws InterruptedException
     */
    public void swipeLeft() throws InterruptedException {
        Thread.sleep(1000L);
        this.swipe((DISPLAY_WIDTH*3) / 4, DISPLAY_HEIGHT/ 2, 5, DISPLAY_HEIGHT/ 2, 4000);
    }

    /**
     * 以常速向左滑动
     * @param times 步数
     * @throws InterruptedException
     */
    public void swipeLeft(int times) throws InterruptedException {
        Thread.sleep(1000L);
        for(int i=0;i<times;i++){
            this.swipe((DISPLAY_WIDTH*3) / 4, DISPLAY_HEIGHT/ 2, 5, DISPLAY_HEIGHT/ 2, 4000);
            Thread.sleep(1000L);
        }

    }

    /**
     * 以常速向右滑动
     * @throws InterruptedException
     */
    public void swipeRight() throws InterruptedException {
        Thread.sleep(1000L);
        this.swipe(DISPLAY_WIDTH / 4, DISPLAY_HEIGHT/ 2, DISPLAY_WIDTH-5, DISPLAY_HEIGHT / 2, 4000);
    }

    /**
     * 以常速向右滑动
     * @param times 步长
     * @throws InterruptedException
     */
    public void swipeRight(int times) throws InterruptedException {
        Thread.sleep(1000L);
        for(int i=0;i<times;i++){
            this.swipe(DISPLAY_WIDTH / 4, DISPLAY_HEIGHT/ 2, DISPLAY_WIDTH-5, DISPLAY_HEIGHT / 2, 4000);
            Thread.sleep(1000L);
        }

    }

    /**
     * 长按
     * @param x
     * @param y
     * @param duration 持续时间
     */
    public void longClick(int x, int y, int duration){
        new TouchAction(driver).longPress(x,y,duration);
    }

    /**
     * 长按
     * @param el
     * @param duration
     */
    public void longClick(WebElement el, int duration){
        new TouchAction(driver).longPress(el,duration);
    }

    /**
     * 移动
     * @param el
     * @param x
     * @param y
     */
    public void moveTo(WebElement el,int x, int y) {
        new TouchAction(driver).longPress(el)
                .moveTo(x,y).release().perform();
    }

    /**
     * 移动
     * @param moveE1
     * @param toE2
     */
    public void moveTo(WebElement moveE1,WebElement toE2) {
        new TouchAction(driver).longPress(moveE1)
                .moveTo(toE2).release().perform();
    }

    /**
     * 按下电源键
     */
    public void pressPower() {
         driver.pressKeyCode(26);
    }

    /**
     * 音量+
     */
    public void pressVolumeeUp() {
        driver.pressKeyCode(24);
    }

    /**
     * 音量-
     */
    public void pressVolumeDown() {
        driver.pressKeyCode(25);
    }


    /**
     * 扬声器静音键
     */
    public void pressVolumeMute() {
        driver.pressKeyCode(164);
    }

    /**
     * 按下home键
     */
    public void pressHome() {
        driver.pressKeyCode(3);
    }

    /**
     * back返回
     * @param times
     * @return
     * @throws InterruptedException
     */
    public boolean pressBack(int times) throws InterruptedException {
        if(times <= 0) {
            return false;
        } else {
            int i = 0;

            while(i < times ) {
                ++i;
                driver.pressKeyCode(4);
                Thread.sleep(1000L);
            }
            return true;
        }
    }

    /**
     * back返回
     * @return
     * @throws InterruptedException
     */
    public boolean pressBack() throws InterruptedException {
        return this.pressBack(1);
    }

    /**
     * back 返回桌面
     * @return
     * @throws InterruptedException
     */
    public boolean pressBackToHome() throws InterruptedException {
        for(int defaultTime = 8; defaultTime > 0 && !driver.currentActivity().contains("launcher"); --defaultTime) {
            this.pressBack(1);
        }
        return driver.currentActivity().contains("launcher");
    }


    /**
     * 向上滑动查找text
     * @param text
     * @param timeout 超时
     * @return
     * @throws InterruptedException
     */
    public boolean searchText(String text, long timeout) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < timeout) {
            if(waitForVisible(driver,By.name(text),1)) {
                return true;
            }
            this.swipeUp();
        }
        return false;
    }

    /**
     * 向上滑动查找id
     * @param id
     * @param timeout 超时
     * @return
     * @throws InterruptedException
     */
    public boolean searchId(String id, long timeout) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < timeout) {
            if(waitForVisible(driver,By.id(id),1)) {
                return true;
            }
            this.swipeUp();
        }
        return false;
    }

    /**
     * 向上滑动查找包含text
     * @param by
     * @param text
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    public boolean searchTextContainsBySelector(By by, String text ,long timeout) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        while(System.currentTimeMillis() - startTime < timeout) {
            try {
                if (driver.findElement(by).getText().contains(text)) {
                    return true;
                }else{
                    swipeUp();
                }
            }catch (Exception e){
                this.swipeUp();
            }
        }
        return false;
    }

    /**
     * 等待元素可见
     * @param driver
     * @param by
     * @param waitTime
     * @return
     */
    public boolean waitForVisible(WebDriver driver, final By by, int waitTime) {
        boolean result=false;
        for (int attempt = 0; attempt < waitTime; attempt++) {
            try {
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                driver.findElement(by);
                result=true;
                break;
            } catch (Exception e) {
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            }
        }
        return result;
    }


    private Rect intersect(Rect rect1, Rect rect2) {
        int top = rect1.top > rect2.top?rect1.top:rect2.top;
        int bottom = rect1.bottom < rect2.bottom?rect1.bottom:rect2.bottom;
        int left = rect1.left > rect2.left?rect1.left:rect2.left;
        int right = rect1.right < rect2.right?rect1.right:rect2.right;
        return new Rect(left, top, right, bottom);
    }

    /**
     * 相对元素下方找到目标元素
     * @param bySelector
     * @param instance
     * @param targetBySelector
     * @return
     */
    public WebElement findElementToDown(By bySelector, int instance, By targetBySelector) {
        int dist;
        WebElement targetObject;
        WebElement found = null;
        WebElement object = (WebElement)driver.findElements(bySelector).get(instance);

        List targetObject2List = driver.findElements(targetBySelector);
        Iterator var8 = targetObject2List.iterator();
        do {
            if(!var8.hasNext()) {
                return found;
            }
            targetObject = (WebElement)var8.next();

            Rect rectObject=getVisibleBounds(object);
            Rect rectTargetObject=getVisibleBounds(targetObject);

            Rect bounds = this.intersect(rectObject, rectTargetObject);

            int top = bounds.left;
            int bottom= bounds.right;

            dist = top < bottom?rectTargetObject.top - rectObject.bottom:-1;
        } while(dist < 0);

        found = targetObject;
        return found;
    }

    /**
     * 相对元素上方找到目标元素
     * @param bySelector
     * @param instance
     * @param targetBySelector
     * @return
     */
    public WebElement findElementToUp(By bySelector, int instance, By targetBySelector) {
        int dist;
        WebElement targetObject;
        WebElement found = null;
        WebElement object = (WebElement)driver.findElements(bySelector).get(instance);

        List targetObject2List = driver.findElements(targetBySelector);
        Iterator var8 = targetObject2List.iterator();
        do {
            if(!var8.hasNext()) {
                return found;
            }
            targetObject = (WebElement)var8.next();

            Rect rectObject=getVisibleBounds(object);
            Rect rectTargetObject=getVisibleBounds(targetObject);

            Rect bounds = this.intersect(rectObject, rectTargetObject);

            int top = bounds.left;
            int bottom= bounds.right;

            dist = top < bottom?rectObject.top - rectTargetObject.bottom:-1;
        } while(dist < 0);

        found = targetObject;
        return found;
    }

    /**
     * 相对元素左方找到元素
     * @param bySelector
     * @param instance
     * @param targetBySelector
     * @return
     */
    public WebElement findElementToLeft(By bySelector, int instance, By targetBySelector) {
        int dist;
        WebElement targetObject;
        WebElement found = null;
        WebElement object = (WebElement)driver.findElements(bySelector).get(instance);

        List targetObject2List = driver.findElements(targetBySelector);
        Iterator var8 = targetObject2List.iterator();
        do {
            if(!var8.hasNext()) {
                return found;
            }
            targetObject = (WebElement)var8.next();

            Rect rectObject=getVisibleBounds(object);
            Rect rectTargetObject=getVisibleBounds(targetObject);

            Rect bounds = this.intersect(rectObject, rectTargetObject);

            int top = bounds.left;
            int bottom= bounds.right;

            dist = top < bottom?rectObject.left - rectTargetObject.right:-1;
        } while(dist < 0);

        found = targetObject;
        return found;
    }

    /**
     * 相对元素右方找到目标元素
     * @param bySelector
     * @param instance
     * @param targetBySelector
     * @return
     */
    public WebElement findElementToRight(By bySelector, int instance, By targetBySelector) {
        int dist;
        WebElement targetObject;
        WebElement found = null;
        WebElement object = (WebElement)driver.findElements(bySelector).get(instance);

        List targetObject2List = driver.findElements(targetBySelector);
        Iterator var8 = targetObject2List.iterator();
        do {
            if(!var8.hasNext()) {
                return found;
            }
            targetObject = (WebElement)var8.next();

            Rect rectObject=getVisibleBounds(object);
            Rect rectTargetObject=getVisibleBounds(targetObject);

            Rect bounds = this.intersect(rectObject, rectTargetObject);

            int top = bounds.left;
            int bottom= bounds.right;

            dist = top < bottom?rectTargetObject.left - rectObject.right:-1;
        } while(dist < 0);

        found = targetObject;
        return found;
    }

    public int  getInstanceByClass(String classname, String bounds) throws InterruptedException {
        int start = 0;
        int end = 0;
        int instance;
        bounds = bounds.replace(",","][");
        int[] arr = new int[4];
        int flag = 0;
        for(int i=0;i<bounds.length();i++){
            if(bounds.charAt(i) == '['){
                start = i;
            }else if(bounds.charAt(i) == ']'){
                end = i;
                arr[flag] = Integer.parseInt(bounds.substring(start+1,end));
                flag++;
            }
        }
        Thread.sleep(1000);
        String Rec = "Rect("+arr[0]+", "+arr[1]+" - "+arr[2]+", "+arr[3]+")";
        int cnt =driver.findElements(By.className(classname)).size();

        for(instance=0; instance < cnt; instance++){
            WebElement el= (WebElement) driver.findElements(By.className(classname)).get(instance);
            if(getVisibleBounds(el).toString().equals(Rec)){
                break;
            }
        }
        return instance;
    }

    public Rect getVisibleBounds(WebElement el){
        Rect  rect=new Rect();
        int left=el.getLocation().x;
        int right=el.getLocation().x+el.getSize().width;
        int top=el.getLocation().y;
        int bottom=el.getLocation().y+el.getSize().height;
//      System.out.println("left right top bottom is "+left+" "+right+" "+top+" "+bottom);
//      System.out.println("x y width height is "+el.getLocation().x+" "+el.getLocation().y+" "+el.getSize().width+" "+el.getSize().height+" "+el.getSize());
        rect.set(left,top,right,bottom);
        return rect;
    }


    /////////////////////////////////////////////////////////////

    public void clickByText(String text) {
        driver.findElement(By.name(text)).click();
    }
//
//    public void clickByText(String text, long mTimeout) {
//        this.getObject2(By.text(text), mTimeout).click();
//    }
//
//    public void clickByText(Pattern regex) {
//        this.getObject2(By.text(regex)).click();
//    }
//
//    public void clickByText(Pattern regex, long mTimeout) {
//        this.getObject2(By.text(regex), mTimeout).click();
//    }
//
//    public void clickByTextInList(String text, int instance) {
//        ((UiObject2)this.getObject2List(By.text(text)).get(instance)).click();
//    }
//
//    public void clickByTextInList(String text, int instance, long mTimeout) {
//        ((UiObject2)this.getObject2List(By.text(text), mTimeout).get(instance)).click();
//    }
//
//    public void clickByTextInList(Pattern regex, int instance) {
//        ((UiObject2)this.getObject2List(By.text(regex)).get(instance)).click();
//    }
//
//    public void clickByTextInList(Pattern regex, int instance, long mTimeout) {
//        ((UiObject2)this.getObject2List(By.text(regex), mTimeout).get(instance)).click();
//    }
//
//    public void clickById(String res) {
//        this.getObject2(By.res(res)).click();
//    }
//
//    public void clickById(String res, long mTimeout) {
//        this.getObject2(By.res(res), mTimeout).click();
//    }
//
//    public void clickById(Pattern regex) {
//        this.getObject2(By.res(regex)).click();
//    }
//
//    public void clickById(Pattern regex, long mTimeout) {
//        this.getObject2(By.res(regex), mTimeout).click();
//    }
//
//    public void clickByIdInList(String res, int instance) {
//        ((UiObject2)this.getObject2List(By.res(res)).get(instance)).click();
//    }
//
//    public void clickByIdInList(String res, int instance, long mTimeout) {
//        ((UiObject2)this.getObject2List(By.res(res), mTimeout).get(instance)).click();
//    }
//
//    public void clickByIdInList(Pattern regex, int instance) {
//        ((UiObject2)this.getObject2List(By.res(regex)).get(instance)).click();
//    }
//
//    public void clickByIdInList(Pattern regex, int instance, long mTimeout) {
//        ((UiObject2)this.getObject2List(By.res(regex), mTimeout).get(instance)).click();
//    }
//
//    public void clickByDesc(String desc) {
//        this.getObject2(By.desc(desc)).click();
//    }
//
//    public void clickByDesc(String desc, long mTimeout) {
//        this.getObject2(By.desc(desc), mTimeout).click();
//    }
//
//    public void clickByDesc(Pattern regex) {
//        this.getObject2(By.desc(regex)).click();
//    }
//
//    public void clickByDesc(Pattern regex, long mTimeout) {
//        this.getObject2(By.desc(regex), mTimeout).click();
//    }
//
//    public void clickByDescInList(String desc, int instance) {
//        ((UiObject2)this.getObject2List(By.desc(desc)).get(instance)).click();
//    }
//
//    public void clickByDescInList(String desc, int instance, long mTimeout) {
//        ((UiObject2)this.getObject2List(By.desc(desc), mTimeout).get(instance)).click();
//    }
//
//    public void clickByDescInList(Pattern regex, int instance) {
//        ((UiObject2)this.getObject2List(By.desc(regex)).get(instance)).click();
//    }
//
//    public void clickByDescInList(Pattern regex, int instance, long mTimeout) {
//        ((UiObject2)this.getObject2List(By.desc(regex), mTimeout).get(instance)).click();
//    }
//
//    public void clickByClassInList(String clazz, int instance) {
//        ((UiObject2)this.getObject2List(By.clazz(clazz)).get(instance)).click();
//    }
//
//    public void clickByObj(UiObject obj) throws UiObjectNotFoundException {
//        obj.waitForExists(this.timeout);
//        obj.click();
//    }
//
//    public void clickByObj(UiObject obj, long mTimeout) throws UiObjectNotFoundException {
//        obj.waitForExists(mTimeout);
//        obj.click();
//    }
//
//    public void clickByObj2(BySelector bySel) {
//        this.getObject2(bySel).click();
//    }
//
//    public void clickByObj2(BySelector bySel, long mTimeout) {
//        this.getObject2(bySel, mTimeout).click();
//    }
//
//    public void clickByObj2InList(BySelector bySel, int instance) {
//        ((UiObject2)this.getObject2List(bySel).get(instance)).click();
//    }
//
//    public void clickByObj2InList(BySelector bySel, int instance, long mTimeout) {
//        SystemClock.sleep(200L);
//        ((UiObject2)this.getObject2List(bySel, mTimeout).get(instance)).click();
//    }
//
//    public boolean isTextExists(String text, long timeout) {
//        return this.isObject2Exists(By.text(text), timeout);
//    }
//
//    public boolean isTextExists(Pattern regex, long timeout) {
//        return this.isObject2Exists(By.text(regex), timeout);
//    }
//
//    public boolean isIdExists(String id, long timeout) {
//        return this.isObject2Exists(By.res(id), timeout);
//    }
//
//    public boolean isIdExists(Pattern regex, long timeout) {
//        return this.isObject2Exists(By.res(regex), timeout);
//    }
//
//    public boolean isDescExists(String desc, long timeout) {
//        return this.isObject2Exists(By.desc(desc), timeout);
//    }
//
//    public boolean isDescExists(Pattern regex, long timeout) {
//        return this.isObject2Exists(By.desc(regex), timeout);
//    }
//
//    public boolean isObjectExists(UiObject obj, long timeout) {
//        return obj.waitForExists(timeout);
//    }
//
//    public boolean isObject2Exists(BySelector bySel, long timeout) {
//        return ((Boolean)this.mUiDevice.wait(Until.hasObject(bySel), timeout)).booleanValue();
//    }
//
//    public boolean isObject2Exists(BySelector bySel, int instance, long timeout) {
//        return ((List)this.mUiDevice.wait(Until.findObjects(bySel), timeout)).size() > instance;
//    }
//
//    public UiObject2 getObject2(BySelector bySel) {
//        return (UiObject2)this.mUiDevice.wait(Until.findObject(bySel), this.timeout);
//    }
//
//    public UiObject2 getObject2(BySelector bySel, long mTimeout) {
//        return (UiObject2)this.mUiDevice.wait(Until.findObject(bySel), mTimeout);
//    }
//
//    public List<UiObject2> getObject2List(BySelector bySel) {
//        return (List)this.mUiDevice.wait(Until.findObjects(bySel), this.timeout);
//    }
//
//    public List<UiObject2> getObject2List(BySelector bySel, long mTimeout) {
//        return (List)this.mUiDevice.wait(Until.findObjects(bySel), mTimeout);
//    }
//
//    public String getTextById(String id) {
//        return this.getObject2(By.res(id)).getText();
//    }
//
//    public String getTextByDesc(String desc) {
//        return this.getObject2(By.desc(desc)).getText();
//    }
//
//    public void setTextById(String content, String id) {
//        this.getObject2(By.res(id)).setText(content);
//    }
//
//    public void setTextByText(String content, String text) {
//        this.getObject2(By.text(text)).setText(content);
//    }
//
//    public void setTextByDesc(String content, String desc) {
//        this.getObject2(By.desc(content)).setText(content);
//    }
//
//    public void longPressVolumeUp() {
//        Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
//        final InstrumentationUiAutomatorBridge mUiAutomationBridge = new InstrumentationUiAutomatorBridge(mInstrumentation.getContext(), mInstrumentation.getUiAutomation());
//        Runnable command = new Runnable() {
//            public void run() {
//                long eventTime = SystemClock.uptimeMillis();
//                KeyEvent downEvent = new KeyEvent(eventTime, eventTime, 0, 24, 0, 0, -1, 0, 0, 257);
//
//                for(int upEvent = 0; upEvent < 20; ++upEvent) {
//                    mUiAutomationBridge.injectInputEvent(downEvent, true);
//
//                    try {
//                        Thread.sleep(100L);
//                    } catch (InterruptedException var6) {
//                        var6.printStackTrace();
//                    }
//                }
//
//                KeyEvent var7 = new KeyEvent(eventTime, eventTime, 1, 24, 0, 0, -1, 0, 0, 257);
//                mUiAutomationBridge.injectInputEvent(var7, true);
//            }
//        };
//        command.run();
//    }
//
//    public void longPressVolumeDown() {
//        Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
//        final InstrumentationUiAutomatorBridge mUiAutomationBridge = new InstrumentationUiAutomatorBridge(mInstrumentation.getContext(), mInstrumentation.getUiAutomation());
//        Runnable command = new Runnable() {
//            public void run() {
//                long eventTime = SystemClock.uptimeMillis();
//                KeyEvent downEvent = new KeyEvent(eventTime, eventTime, 0, 25, 0, 0, -1, 0, 0, 257);
//
//                for(int upEvent = 0; upEvent < 20; ++upEvent) {
//                    mUiAutomationBridge.injectInputEvent(downEvent, true);
//
//                    try {
//                        Thread.sleep(100L);
//                    } catch (InterruptedException var6) {
//                        var6.printStackTrace();
//                    }
//                }
//
//                KeyEvent var7 = new KeyEvent(eventTime, eventTime, 1, 25, 0, 0, -1, 0, 0, 257);
//                mUiAutomationBridge.injectInputEvent(var7, true);
//            }
//        };
//        command.run();
//    }
//
//    public void longPressHome() {
//        try {
//            this.mUiDevice.executeShellCommand("input keyevent --longpress 3");
//        } catch (IOException var2) {
//            var2.printStackTrace();
//        }
//
//    }

    public void clickElement(By by) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement el=driver.findElement(by);
        el.click();
    }

    public void clickElement(By by,int instance) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> els=driver.findElements(by);
        els.get(instance).click();
    }

    public void clickElementByClass(By by,String bound) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        int instance=getInstanceByClass(by.toString(),bound);
        List<WebElement> els=driver.findElements(by);
        els.get(instance).click();
    }

    public boolean isExistBySelector(WebDriver driver, By by, int waitTime) {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        boolean result = false;
        for (int attempt = 0; attempt < waitTime; attempt++) {
            try {
                driver.findElement(by);
                result = true;
                break;
            } catch (Exception e) {
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            }
        }
        return result;
    }







    public boolean isExistImageElement(String resourceName, int secondsToWait) throws InterruptedException {
        sleep(2000);
        String prefix = String.valueOf(driver.getSize().getWidth()) + "x_";
        resourceName = prefix.replace(".0", "") + resourceName + ".png";
        URL resource = this.getClass().getClassLoader().getResource(resourceName);
        System.out.println(resource);
        ImageElement image = driver.findImageElement(resource);

        int attempts = 0;
        while (image == null && attempts < secondsToWait / 10) {
            sleep(10000);

            image = driver.findImageElement(resource);
            attempts++;
            if (image != null) {
                break;
            }
        }

        return image!=null;
    }

    public ImageElement waitForImageElement(String resourceName, int secondsToWait) throws InterruptedException {
        sleep(2000);
        String prefix = String.valueOf(driver.getSize().getWidth()) + "x_";
        resourceName = prefix.replace(".0", "") + resourceName + ".png";
        URL resource = this.getClass().getClassLoader().getResource(resourceName);
        System.out.println(resource);
        ImageElement image = driver.findImageElement(resource);

        int attempts = 0;
        while (image == null && attempts < secondsToWait / 10) {
            sleep(10000);

            image = driver.findImageElement(resource);
            attempts++;
            if (image != null) {
                break;
            }
        }

        assertTrue(image != null, String.format("Cannot find image %s in %s seconds",
                resourceName, String.valueOf(secondsToWait)));

        return image;
    }


}
