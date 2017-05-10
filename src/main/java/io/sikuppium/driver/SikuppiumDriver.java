package io.sikuppium.driver;

import io.appium.java_client.AppiumDriver;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.sikuli.api.DefaultScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SikuppiumDriver extends AndroidDriver{

    private final static Logger LOG = LoggerFactory.getLogger(SikuppiumDriver.class);

    private int waitSecondsAfterClick = 2;
    private  double similarityScore = 0.7;
    private int waitSecondsForImage = 10;
    private AppiumDriver driver;
    private PhoneScreen driverScreen;

    public SikuppiumDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }


    public ImageElement findImageElement(URL imageUrl) {
        try {
            driverScreen = new PhoneScreen(driver);
        } catch (IOException e1) {
            throw new RuntimeException("Unable to init SikkupiumDriver");
        }

        ScreenRegion webdriverRegion = new DefaultScreenRegion(driverScreen);
        webdriverRegion.setScore(similarityScore);

        ImageTarget target = new ImageTarget(imageUrl);
        ScreenRegion imageRegion = webdriverRegion.wait(target, waitSecondsForImage);

        Rectangle rectangle;

        if (imageRegion != null) {
            rectangle = imageRegion.getBounds();
            LOG.debug("Image is found at {} {} {} {}", rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        } else {
            LOG.debug("Image is not found");
            return null;
        }

        return new CustomImageElement(
                this.driver,
                rectangle.x,
                rectangle.y,
                rectangle.width,
                rectangle.height,
                this.waitSecondsAfterClick
        );
    }

    public ImageElement findImageElement(URL imageUrl, org.openqa.selenium.Dimension dimension) throws IOException {
        try {
            driverScreen = new PhoneScreen(driver);
        } catch (IOException e1) {
            throw new RuntimeException("Unable to init SikkupiumDriver");
        }

        DefaultScreenRegion webdriverRegion = new DefaultScreenRegion(driverScreen);//,516,66,556,138);//,936,66,144,140);
        webdriverRegion.setScore(similarityScore);

//      webdriverRegion.setScore(1.0);
        System.out.println("ScreenRegion :"+webdriverRegion);

        System.out.println("ScreenRegion Score:"+ webdriverRegion.getScore());

        ImageTarget target = new ImageTarget(imageUrl);
        ScreenRegion imageRegion = webdriverRegion.wait(target, waitSecondsForImage);

        Rectangle rectangle;

        if (imageRegion != null) {
            rectangle = imageRegion.getBounds();

            System.out.println("Image is found at {} {} {} {}"+rectangle.x+" "+rectangle.y+" "+ rectangle.width+" "+rectangle.height);
            System.out.println("Image bounds:"+rectangle);
        } else {
            System.out.println("Image is not found");
            return null;
        }
        System.out.println("Score:"+imageRegion.getScore());
        BufferedImage bufferedImage=imageRegion.capture();

        ImageIO.write(bufferedImage, "png", new File("/Users/huangjingqing/Downloads/cc.png"));

        return new CustomImageElement(
                this.driver,
                rectangle.x,
                rectangle.y,
                rectangle.width,
                rectangle.height,
                this.waitSecondsAfterClick
        );
    }



    public Dimension getSize() {
        try {
            driverScreen = new PhoneScreen(driver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return driverScreen.getSize();
    }

    public void setWaitSecondsAfterClick(int waitSecondsAfterClick) {
        this.waitSecondsAfterClick = waitSecondsAfterClick;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    }

    public void setWaitSecondsForImage(int waitSecondsForImage) {
        this.waitSecondsForImage = waitSecondsForImage;
    }

    public void setDriver(AppiumDriver driver) {
        this.driver = driver;
    }

}
