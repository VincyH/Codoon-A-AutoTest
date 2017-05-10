package io.sikuppium.driver;

import org.openqa.selenium.remote.DesiredCapabilities;

public class CapabilitiesFactory {

    protected static DesiredCapabilities capabilities;

    public static DesiredCapabilities getCapabilities() throws Exception {

        capabilities = new DesiredCapabilities();

//        capabilities.setCapability("platformName", System.getenv("PLATFORM_NAME"));
//        capabilities.setCapability("appium-version", System.getenv("APPIUM_VERSION"));
//        capabilities.setCapability("name", System.getenv("NAME"));
//        capabilities.setCapability("platformVersion", System.getenv("PLATFORM_VERSION"));
//        capabilities.setCapability("deviceName", System.getenv("DEVICE"));
//        capabilities.setCapability("app", System.getenv("APP"));

        capabilities.setCapability("deviceName", "621QECPP278ZS");
//        621QECPP278ZS
     //   capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "6.0");
//      capabilities.setCapability("autoWebview", "true");
        capabilities.setCapability("automationName","Appium");
        //以下两项可以通过appium客户端查看
        //com.codoon.gps/.ui.SlideActivity
        capabilities.setCapability("appPackage", "com.codoon.gps");
        capabilities.setCapability("appActivity", ".ui.SlideActivity");


//        capabilities.setCapability("appPackage", "com.meizu.flyme.wallet");
//        capabilities.setCapability("appActivity", ".activity.WalletTabActivity");





        //支持中文输入
        capabilities.setCapability("unicodeKeyboard", "True");
        //重置输入法为系统默认
        capabilities.setCapability("resetKeyboard", "True");
        capabilities.setCapability("sessionOverride", true);
        return capabilities;
    }

}
