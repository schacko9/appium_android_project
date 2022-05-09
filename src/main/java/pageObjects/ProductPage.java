package pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import utils.base;


public class ProductPage {
	
	protected static AndroidDriver<MobileElement> driverr;
	
	public ProductPage(AndroidDriver<MobileElement> driver)
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
	public List<MobileElement> productName;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/productAddCart")
	public List<MobileElement> addToCart;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/productPrice")
	public List<MobileElement> productPrice;

	@AndroidFindBy(id="com.androidsample.generalstore:id/appbar_btn_cart")
	public WebElement appBarBtn;


	
	
	public List<MobileElement> getProductName()
	{	
		return productName;
	}
	
	public List<MobileElement> getProductPrice()
	{	
		return productPrice;
	}
	
	public List<MobileElement> getAddToCart()
	{	
		return addToCart;
	}

	public void getAppBarBtn()
	{	
		appBarBtn.click();
	}
	
	
	
	public double getProductsandPrice() throws InterruptedException, IOException
	{	
		String[] wishlist = base.getProperty("wishlist").split(",");
		double productsum = 0;
		
		for(int i=0; i<wishlist.length; i++) {
			base.uiscrollable2(driverr,wishlist[i]);
			Thread.sleep(3000);
			
			int count = getProductName().size();
			for(int j=0; j<count; j++) {
				String product = getProductName().get(j).getText();
	
				if(Arrays.stream(wishlist).anyMatch(product::equals)){
					getAddToCart().get(j).click();				
					
					String price = getProductPrice().get(j).getText();				
					double productprice = base.getAmount(price);

					productsum += productprice;
				}		
			}
		}
		
		return productsum;
	}
	
}
















