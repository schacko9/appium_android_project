package pageObjects;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WebPage {

	protected static AndroidDriver<MobileElement> driverr;
	
	public WebPage(AndroidDriver<MobileElement> driver)
	{
		driverr = driver;
		
		try{
			PageFactory.initElements(driverr, this);
		} catch (Exception e) {
	        e.printStackTrace();
		}
	}
	
	
	@FindBy(name="q")
	public WebElement searchBtn;


	public WebElement getsearchBtn()
	{	
		return searchBtn;
	}
	
	
	
	public void setwebcontext() throws InterruptedException{
		Thread.sleep(5000);
		Set<String> contextNames = driverr.getContextHandles();
		for (String contextName : contextNames) {
		    System.out.println(contextName); 											// Prints out something like NATIVE_APP \n WEBVIEW_1
		}
		
		driverr.context((String) contextNames.toArray()[1]); 							// Set context to WEBVIEW_1				
	}
	
	public void gobacktoapp() {
		driverr.pressKey(new KeyEvent(AndroidKey.BACK));		

	}
}