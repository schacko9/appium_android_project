package test;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import pageObjects.CheckoutPage;
import pageObjects.FormPage;
import pageObjects.ProductPage;
import pageObjects.WebPage;
import utils.base;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static java.time.Duration.ofSeconds;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GeneralStoreTestCLOUD extends base {
	
	public static AndroidDriver<MobileElement> driver;
	public static AppiumDriverLocalService service;
	public double productsum;
	public List<String> wishList;
	public List<String> checkedout;
	public double checkoutsum;
	public WebElement checkbox;
	public WebElement conditions;

	
	@BeforeTest
	public void setup() throws InterruptedException, IOException {
		driver = runcapabilites("GeneralStoreApp", true);	
		Thread.sleep(5000);
	}
	

	@Test
	public void FormPageTest() throws IOException, InterruptedException{
		
		FormPage formpage = new FormPage(driver); 
		String gender = getProperty("gender");
		
		formpage.getNameField().sendKeys(getProperty("name"));
			driver.hideKeyboard();
		formpage.genderselection(gender);
		formpage.getcountrySelection();
		formpage.getletsshopbtn();	
		
		Thread.sleep(2000);
	}
		
	
	@Test(dependsOnMethods = {"FormPageTest"})
	public void ProductPageTest() throws IOException, InterruptedException{

		ProductPage productpage = new ProductPage(driver); 
		
		productsum = productpage.getProductsandPrice();
		productpage.getAppBarBtn();
		
		Thread.sleep(2000);	
	}
	
	
	@Test(dependsOnMethods = {"ProductPageTest"})
	public void CheckoutPageTest() throws IOException, InterruptedException{
	
		CheckoutPage checkout = new CheckoutPage(driver);

		wishList = new ArrayList<String>(Arrays.asList(getProperty("wishlist").split(",")));
		checkedout = checkout.checkoutlist();
		checkoutsum = checkout.checkoutprice();
		checkout.assertions(checkedout, wishList, checkoutsum, productsum);
		
		checkbox = checkout.getcheckBox();
		conditions = checkout.getconditions();
			tap(driver, checkbox);
			longpress(driver, conditions);		
		
		checkout.getpopupclose();
		checkout.getproceedBtn();
		
		Thread.sleep(4000);		
	}

	
	@Test(dependsOnMethods = {"CheckoutPageTest"})
	public void WebPageTest() throws IOException, InterruptedException{			
		
		WebPage webpage = new WebPage(driver);
		String keyword = getProperty("google");

		webpage.setwebcontext();
		webpage.getsearchBtn().sendKeys(keyword);
		webpage.getsearchBtn().sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		
		webpage.gobacktoapp();
		Thread.sleep(2000);
	}
	
	@AfterTest
	public void teardown() throws InterruptedException, IOException {
		driver.quit();
	}
	

}

