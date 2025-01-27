package tests.roomTests.home;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import constants.Constants;
import init.DriverFactory;
import listner.ReportListener;
import pages.base.BasePage;
import pages.home.HomePage;
import utils.EncryptionDecryption;
import utils.FileOperations;

public class HomeTests   {

	SoftAssert softAssert;
	Logger log=null;
	RemoteWebDriver driver;
	BasePage  basepage = new BasePage();
	DriverFactory driverFactory;
	Path currentRelativePath = Paths.get("");
	String testDataPath = currentRelativePath.toAbsolutePath().toString();
	String testDataFile = testDataPath + File.separator + "testdata" + File.separator;
	String filePath=System.getProperty("user.dir")+File.separator +"testDownloadedItems"+File.separator;
	String imageTestPath=testDataFile+"images"+ File.separator;
	Constants constants = new Constants();
	FileOperations fileOperations = new FileOperations();
	EncryptionDecryption encryptionDecryptionObj = new EncryptionDecryption();
	HomePage homePage = new HomePage();
	String sauceLabsBackpackText = fileOperations.readColumnValueUsingKeyFromExcel(testDataFile, "HomeTestData.xlsx","Home_ValidData", "SauceLabsBackpackText");
	String sauceLabsBackpackPrice = fileOperations.readColumnValueUsingKeyFromExcel(testDataFile, "HomeTestData.xlsx","Home_ValidData", "SauceLabsBackpackPrice");
	By userName = By.xpath("//input[@placeholder='Username']");
	By password = By.xpath("//input[@placeholder='Password']");
	By submit = By.xpath("//input[@type='submit']");
	

	@BeforeClass(alwaysRun = true)
	public void startUp() throws Exception
	{
		log = Logger.getLogger(HomeTests.class);
		PropertyConfigurator.configure(constants.CONFIG_LOG4J_FILE_PATH);
		this.driverFactory = new DriverFactory();
		this.driver = DriverFactory.getDriver();
		DriverFactory.setDriverFactory(this.driverFactory);
		this.driver.get(fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "appUrl"));
		driver.findElement(userName).sendKeys(fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "username"));
		driver.findElement(password).sendKeys(fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "password"));
		Thread.sleep(2000);
		driver.findElement(submit).click();
	   log.info("URL is entered in browser");
		}

	/*
	 * Test case id - 001
	 * Test Case Description - : verify Sauce Labs Backpack Order Add To Cart And Remove
	 * Author :  ashish saklani
	 */
	@Test(groups = { "home" }, singleThreaded=true)
	public void verifySauceLabsBackpackOrderAddToCartAndRemove()
	{
		log.info("verifySauceLabsBackpackOrderAddToCartAndRemove() Test Started");
		softAssert =new SoftAssert();
		// click On sauce Labs Back pack
		homePage.clickOnsauceLabsBackpack(driver);
		// verify sauce Labs Backpack Text
		softAssert.assertEquals(homePage.getTextOfSauceLabsBackpack(driver), sauceLabsBackpackText, "sauce Labs Backpack Text is not same as expected");
		//verify sauce Labs Backpack price
		softAssert.assertEquals(homePage.getSauceLabsBackpackPrice(driver), sauceLabsBackpackPrice, "sauce Labs Backpack price  is not same as expected");
		// click on add cart
		homePage.clickOnAddToCart(driver);
		//  verify order is  selected 
		softAssert.assertTrue(homePage.checkOrderIsSelected(driver), "Order is not Selected as Expected");
		//  check remove button
		softAssert.assertTrue(homePage.isRemoveButtonDispalyed(driver), "Remove Button is not Dispalyed as Expected");
		// click on remove button 
        homePage.clickOnRemoveButton(driver);
        // check order is on cart after remove order
		softAssert.assertFalse(homePage.checkOrderIsSelected(driver), "Order is Selected after remove order is not expected");
		// click on Back To Product
		homePage.clickOnBackToProduct(driver);
		softAssert.assertAll();
        log.info("verifySauceLabsBackpackOrderAddToCartAndRemove() Test Completed");
	}
    @AfterClass(alwaysRun = true)
	public void tearDown()
	{
		this.driver.quit();
		this.driver = null;
	}
}