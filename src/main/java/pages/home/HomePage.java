package pages.home;



import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import pages.base.BasePage;

public class HomePage extends BasePage {

	By sauceLabsBackpack = By.xpath("//div[text()='Sauce Labs Backpack']");
	By sauceLabsBackpackText = By.xpath("//div[@class='inventory_details_desc large_size']");
	By sauceLabsBackpackPrice = By.xpath("//div[@class='inventory_details_price']");
	By addToCart = By.xpath("//button[text()='Add to cart']");
	By sopingCartbadge  = By.xpath("//span[@class='shopping_cart_badge']");
	By removeButton = By.xpath("//button[@name='remove']");
	By backToProduct = By.xpath("//button[@name='back-to-products']");
	
	
	//==============================================================Locators==========================================================================================***=============================================

	
	
	public void clickOnsauceLabsBackpack(RemoteWebDriver driver) {
		hardWait(5000);
	   click(driver, sauceLabsBackpack);
	
}	

	public void clickOnAddToCart(RemoteWebDriver driver) {
		hardWait(2000);
	   click(driver, addToCart);
	
}	
	public void clickOnBackToProduct(RemoteWebDriver driver) {
		hardWait(2000);
	   click(driver, backToProduct);
	
}

	public String  getTextOfSauceLabsBackpack(RemoteWebDriver driver) {
		 return   getText(driver, sauceLabsBackpackText);

}

	public boolean  checkOrderIsSelected(RemoteWebDriver driver) {
		return isElementPresent(driver, sopingCartbadge);

}
	public boolean  isRemoveButtonDispalyed(RemoteWebDriver driver) {
		return isElementPresent(driver, removeButton);
	
}
	
	public void  clickOnRemoveButton(RemoteWebDriver driver) {
		click(driver, removeButton);
	}
	
	public String  getSauceLabsBackpackPrice(RemoteWebDriver driver) {
		 return   getText(driver, sauceLabsBackpackPrice);

}

	
	
	
}
