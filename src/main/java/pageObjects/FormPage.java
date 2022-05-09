package pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.base;

public class FormPage {

	protected static AndroidDriver<MobileElement> driverr;

	public FormPage(AndroidDriver<MobileElement> driver) {
		driverr = driver;

		try {
			// PageFactory.initElements(driverr, this);
			PageFactory.initElements(new AppiumFieldDecorator(driverr, Duration.ofSeconds(10)), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	private MobileElement nameField;

	@AndroidFindBy(xpath = "//*[@text='Female']")
	private MobileElement femaleOption;

	@AndroidFindBy(id = "android:id/text1")
	private MobileElement countryDropdown;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	private MobileElement letsshop;

	
	
	public MobileElement getNameField() {
		return nameField;
	}

	public void getFemaleOption() {
		femaleOption.click();
	}

	public void getcountrySelection() throws IOException, InterruptedException {
		String country = base.getProperty("country");

		countryDropdown.click();
			Thread.sleep(1000);
		base.uiscrollable(driverr, country);
	    driverr.findElement(By.xpath("//*[@text=\"" + country + "\"]")).click();
	}

	public void getletsshopbtn() {
		letsshop.click();
	}

	public void genderselection(String gender) {
		if (gender.equalsIgnoreCase("Female")) {
			getFemaleOption();
		} else {
			// Otherwise already selected male options
		}
	}

}
