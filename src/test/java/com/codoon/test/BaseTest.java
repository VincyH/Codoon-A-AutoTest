package com.codoon.test;


import io.sikuppium.driver.CapabilitiesFactory;
import io.sikuppium.driver.ImageElement;
import io.sikuppium.driver.SikuppiumDriver;

import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.testng.annotations.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertTrue;

public class BaseTest {

    protected SikuppiumDriver driver;

    // @BeforeSuite(groups = "testA")
    @BeforeClass
    public void setUp() throws Exception {
        driver = new SikuppiumDriver(
                new URL("http://127.0.0.1:4723/wd/hub"),
                CapabilitiesFactory.getCapabilities()
        );
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.setDriver(driver);
        driver.setSimilarityScore(0.95);
        driver.setWaitSecondsAfterClick(2);
        driver.setWaitSecondsForImage(10);


    }


   // @BeforeSuite(groups = "testA")
    @AfterMethod
    public void after() {

        if (driver != null) {
            driver.quit();
        }
    }

    protected ImageElement waitForImageElement(String resourceName, int secondsToWait) throws InterruptedException {
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

    protected boolean isImageExist(String resourceName, int secondsToWait) throws InterruptedException {
        sleep(2000);
        boolean result=false;
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
                result=true;
                break;
            }
        }
        if(image !=null){
            result=true;
        }
        return result;
    }


    protected boolean imageMatch(String resourceName, Dimension sion, int secondsToWait) throws InterruptedException, IOException {
        sleep(2000);
        boolean result=false;
        String prefix = String.valueOf(driver.getSize().getWidth()) + "x_";
        resourceName = prefix.replace(".0", "") + resourceName + ".png";
        URL resource = this.getClass().getClassLoader().getResource(resourceName);
        System.out.println(resource);
        ImageElement image = driver.findImageElement(resource,sion);

        int attempts = 0;
        while (image == null && attempts < secondsToWait / 10) {
            sleep(10000);

            image = driver.findImageElement(resource,sion);
            attempts++;
            if (image != null) {
                result=true;
                break;
            }
        }
        result=(image!=null);


        return result;
    }



}
