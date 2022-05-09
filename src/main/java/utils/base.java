package utils;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class base {
	public static AndroidDriver<MobileElement> driver;
	
	public AndroidDriver<MobileElement> runcapabilites(String appName, Boolean cloud) throws IOException, InterruptedException {
		if (cloud) {
			return cloudcapabilites(appName);
		} else {
			return capabilites(appName);
		}
	}

	public static AndroidDriver<MobileElement> capabilites(String appName) throws IOException, InterruptedException {
		
		File appDir = new File("src/main/java/resources");
		File app = new File(appDir, base.getProperty(appName));
		String device = System.getProperty("deviceName");

		startEmulator();

		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, device);
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		caps.setCapability("chromeOptions", ImmutableMap.of("w3c", false));

		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Driver Running");

		return driver;
	}

	public static AndroidDriver<MobileElement> cloudcapabilites(String appName) throws IOException, InterruptedException {
		DesiredCapabilities caps = new DesiredCapabilities();

		if (appName.equalsIgnoreCase("GeneralStoreApp")) {
			caps.setCapability("app", "bs://9130637d50908c99619c55f97fe650b0de8e46d6");
		}

    	caps.setCapability("browserstack.user", "slomochacko_Mnv40z");
    	caps.setCapability("browserstack.key", "4CEquPR9zXGhjehRkZGh");
		caps.setCapability("device", "Google Pixel 6");
		caps.setCapability("os_version", "12.0");
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		caps.setCapability("chromeOptions", ImmutableMap.of("w3c", false));

		driver = new AndroidDriver<MobileElement>(new URL("http://hub.browserstack.com/wd/hub"), caps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Cloud Driver Running");

		return driver;
	}

	public static void startEmulator() throws IOException, InterruptedException {
		String type = System.getProperty("type");
		if (type.equalsIgnoreCase("emulator")) {
			Runtime.getRuntime().exec(System.getProperty("user.dir") + "/src/main/java/resources/Emulatorstart.bat");
			System.out.println("Emulator Started");
			Thread.sleep(25000);
		}
	}

	public static void stopEmulator() throws IOException, InterruptedException {
		String udid = System.getProperty("udid");
		String type = System.getProperty("type");
		if (type.equalsIgnoreCase("emulator")) {
			Process p = Runtime.getRuntime().exec(new String[] { "bash", "-l", "-c", "adb -s " + udid + " emu kill" });
			System.out.println("Emulator Stopped");
		}
	}

	public static String getProperty(String value) throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/resources/global.properties");
		Properties prop = new Properties();
		prop.load(fis);

		return (String) prop.get(value);
	}

	public static MobileElement uiscrollable(AndroidDriver<MobileElement> driver, String text) {
		return driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
				+ ".scrollIntoView(new UiSelector().text(\"" + text + "\"))"));
	}

	public static MobileElement uiscrollable2(AndroidDriver<MobileElement> driver, String text) {
		return driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\").scrollable(true))"
						+ ".scrollIntoView(new UiSelector().text(\"" + text + "\"))"));
	}

	public static double getAmount(String value) {
		value = value.substring(1);
		double amount2value = Double.parseDouble(value);

		return amount2value;
	}

	public static void tap(AndroidDriver<MobileElement> driver, WebElement Value) {
		TouchAction t = new TouchAction(driver);
		t.tap(TapOptions.tapOptions().withElement(ElementOption.element(Value))).perform();
	}

	public static void longpress(AndroidDriver<MobileElement> driver, WebElement Value) {
		TouchAction t = new TouchAction(driver);
		t.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(Value))
				.withDuration(ofSeconds(2))).release().perform();

	}

	public static void getScreenshot(String testName) throws IOException {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "/reports/" + testName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
	}

}
