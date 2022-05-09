package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import utils.base;

public class CheckoutPage {
	
	
	protected static AndroidDriver<MobileElement> driverr;
	
	public CheckoutPage(AndroidDriver<MobileElement> driver)
	{
		driverr = driver;
		
		try{
			//PageFactory.initElements(driverr, this);
			PageFactory.initElements(new AppiumFieldDecorator(driverr, Duration.ofSeconds(10)), this);
		} catch (Exception e) {
	        e.printStackTrace();
		}
	}
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/productName")
	public List<MobileElement> productNameLIST;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/productPrice")
	public List<MobileElement> productPriceLIST;

	@AndroidFindBy(xpath="//*[@text='Send me e-mails on discounts related to selected products in future']")
	public MobileElement checkBox;
	
	@AndroidFindBy(xpath="//*[@text='Please read our terms of conditions']")
	public MobileElement conditions;
	
	@AndroidFindBy(id="android:id/button1")
	public MobileElement popupclose;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/btnProceed")
	public MobileElement proceedBtn;
	

	
	
	public List<MobileElement> getproductNameLIST()
	{	
		return productNameLIST;
	}
	
	public List<MobileElement> getproductPriceLIST()
	{	
		return productPriceLIST;
	}
	
	public MobileElement getcheckBox()
	{	
		return checkBox;
	}
	
	public MobileElement getconditions()
	{	
		return conditions;
	}
	
	public void getpopupclose()
	{	
		popupclose.click();
	}
	
	public void getproceedBtn()
	{	
		proceedBtn.click();
	}
	
	
	
	
	public List<String> checkoutlist() {
		List<String> checkedout = new ArrayList<String>();

		int count = productNameLIST.size();
		for(int j=0; j<count; j++) {
			String product = productNameLIST.get(j).getText();		
				checkedout.add(product);				
		}
		
		return checkedout;	
	}
	
	public double checkoutprice() {
		double checkoutsum = 0;
		
		int count = productNameLIST.size();
		for (int i=0; i< count; i++)
		{
			String price = productPriceLIST.get(i).getText();
			double checkoutamount = base.getAmount(price);
			checkoutsum += checkoutamount;
		}
		
		return checkoutsum;	
	}
	
	public void assertions(List<String> Actual1, List<String> Expected1, double Actual2, double Expected2) {
		Assert.assertEquals(Actual1, Expected1);																	
		Assert.assertEquals(Actual2, Expected2);
	}
	
	
	
	
	
	
}
