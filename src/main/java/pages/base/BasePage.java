

package pages.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import init.DriverFactory;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import utils.FileOperations;

public class BasePage {
	FileOperations fileOperations = new FileOperations();
	final int WAIT_FOR_SECONDS = 15;
	final int WAIT_FOR_ONE_MINUTE = 90;
	DriverFactory driverFactory;
	Path currentRelativePath = Paths.get("");
	String testDataPath = currentRelativePath.toAbsolutePath().toString();
	String testDataFile = testDataPath + File.separator + "testdata" + File.separator;
	RemoteWebDriver driver;
   
	
	
	Logger log = null;

	public BasePage() 
	{
		log = Logger.getLogger(BasePage.class);
	}

	
	
	/**
	 * This method is used to enter message in chat box of RTE Page
	 */
	

	

	public void waitForElementVisibility(RemoteWebDriver driver, WebElement elem) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
			webDriverWait.until(ExpectedConditions.visibilityOf(elem));
		} catch (Exception e) {
		}
	}
	
	public void waitForElementVisibility(RemoteWebDriver driver, By locator) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator)); 
			webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
		}
	}
	
	public void waitForElementVisibilityforOneMinute(RemoteWebDriver driver, By locator) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_ONE_MINUTE);
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator)); 
		webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}	

	/**
	 * @param driver
	 * @param elem
	 */
	public void waitForElementInVisibility(RemoteWebDriver driver, WebElement elem) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
		webDriverWait.until(ExpectedConditions.invisibilityOf(elem));
	}
	
	
	
	/**
	 * @param driver
	 * @param elem
	 */
	public void waitForElemenVisibility(RemoteWebDriver driver, By locator) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
		}
	}

	/**
	 * @param driver
	 * @param locator
	 */
	public void waitForElementInVisibility(RemoteWebDriver driver, By locator) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	/**
	 * @param driver
	 * @param locator
	 */
	public void waitForElementClickability(RemoteWebDriver driver, By locator) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
			webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
		}
	}

	/**
	 * @param driver
	 * @param locator
	 */
	public void waitForElementClickability(RemoteWebDriver driver, WebElement locator) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
			webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
		}
	}

	/**
	 * @param driver
	 * @param locator
	 */
	public void waitForAllElementPresenceByElement(RemoteWebDriver driver, By locator) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * @param driver
	 * @param locator
	 */
	public void waitForvisibilityOfAllElements(RemoteWebDriver driver, List<WebElement> element) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
		webDriverWait.until(ExpectedConditions.visibilityOfAllElements(element));
	}

	/**
	 * @param driver
	 * @param locator
	 * @throws InterruptedException
	 */
	public void enterData(RemoteWebDriver driver, By locator, String value) {
	    waitForElementClickability(driver, locator);
		hardWait(2000);
		driver.findElement(locator).click();
		hardWait(2000);
		driver.findElement(locator).clear();
		hardWait(2000);
		driver.findElement(locator).sendKeys(value);
	}
	
	public void enterDataWithoutClick(RemoteWebDriver driver, By locator, String value) {
		waitForElementClickability(driver, locator);
		hardWait(1000);
		driver.findElement(locator).clear();
		hardWait(2000);
		driver.findElement(locator).sendKeys(value);
	}
	
	public void enterDataWithoutClearTxtFieldValue(RemoteWebDriver driver, By locator, String value) {
		waitForElementClickability(driver, locator);
		hardWait(1000);
		driver.findElement(locator).click();
		hardWait(1000);
		driver.findElement(locator).sendKeys(value);
	}

	public void enterDataAfterDeleteAllDefaultValue(RemoteWebDriver driver, By locator, String value) {
		waitForElementClickability(driver, locator);
	    driver.findElement(locator).sendKeys(Keys.CONTROL + "a");
		driver.findElement(locator).sendKeys(Keys.DELETE);
		hardWait(2000);
		driver.findElement(locator).sendKeys(value);
	}
	

	public void deleteAllDefaultValue(RemoteWebDriver driver, By locator) {
		waitForElementClickability(driver, locator);
		driver.findElement(locator).sendKeys(Keys.CONTROL + "a");
		driver.findElement(locator).sendKeys(Keys.DELETE);
		
	}
	public void enterDataAfterDeleteAllDefaultValue(RemoteWebDriver driver, WebElement locator, String value) {
		waitForElementClickability(driver, locator);
		locator.sendKeys(Keys.CONTROL + "a");
		locator.sendKeys(Keys.DELETE);
		hardWait(3000);
		locator.sendKeys(value);
	}

	public void enterDataAfterDeleteValue(RemoteWebDriver driver, By locator, String value) {
		waitForElementClickability(driver, locator);
		driver.findElement(locator).sendKeys(Keys.CONTROL + "a");
		driver.findElement(locator).sendKeys(Keys.DELETE);
		hardWait(3000);
		driver.findElement(locator).sendKeys(value);
		driver.findElement(locator).sendKeys(Keys.ENTER);
	}

	public void enterDataAndPressEnter(RemoteWebDriver driver, By locator, String value) {
		waitForElementClickability(driver, locator);
		hardWait(2000);
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(value);
		hardWait(2000);
		driver.findElement(locator).sendKeys(Keys.ENTER);
	}
	
	public void enterDataAndPressSpace(RemoteWebDriver driver, By locator) {
		waitForElementClickability(driver, locator);
		hardWait(2000);
		driver.findElement(locator).sendKeys(Keys.SPACE);
	}

	public void enterDataAndPressEnterAfterArrowDown(RemoteWebDriver driver, By locator, String value) {
		try {
			 waitForElementClickability(driver, locator);
		} catch (Exception e) {}
		hardWait(2000);
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(value);
		hardWait(2000);
		driver.findElement(locator).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(locator).sendKeys(Keys.ENTER);
	}
	
	/**
	 * @param driver
	 * @param locator
	 * @param value
	 * @throws InterruptedException
	 */
	public void enterDataWithoutClearTextField(RemoteWebDriver driver, By locator, String value) {
		hardWait(1000);
	    waitForElementVisibility(driver, locator);
		driver.findElement(locator).sendKeys(value);
	}

	/*
	 * @param driver
	 * @param locator
	 * @throws InterruptedException
	 */
	public void click(RemoteWebDriver driver, By viewSelector) {
		try {
			waitForElemenVisibility(driver, viewSelector);
			waitForElementClickability(driver, viewSelector);
			driver.findElement(viewSelector).click();
			log.info("Click action performed successfully on element " + driver);
		} catch (Exception e) {
			hardWait(2000);
			JSClick(driver, driver.findElement(viewSelector));
		} 
	}

	public void doubleClickAndEnterData(RemoteWebDriver driver, By locator, String value) {
		hardWait(2000);
		waitForElementVisibility(driver, locator);
		waitForElementClickability(driver, locator);
		Actions action = new Actions(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", getWebElement(driver, locator));
		action.doubleClick(getWebElement(driver, locator)).sendKeys(value).build().perform();
	}

	public void enterDataWithdoubleClick(RemoteWebDriver driver, WebElement locator, String value) {
		waitForElementVisibility(driver, locator);
		waitForElementClickability(driver, locator);
		Actions action = new Actions(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", locator);
		action.doubleClick(locator).sendKeys(value).build().perform();
	}

	public void doubleClick(RemoteWebDriver driver, By locator) {
		waitForElementVisibility(driver, locator);
		waitForElementClickability(driver, locator);
		Actions action = new Actions(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", getWebElement(driver, locator));
		action.doubleClick(getWebElement(driver, locator)).build().perform();
	}

	public void JSClick(RemoteWebDriver driver, WebElement element) {
		JavascriptExecutor jse2 = driver;
		jse2.executeScript("arguments[0].click();", element);
	}

	public int generateRandomNumber() {
		Random random = new Random();
		return random.nextInt(1000000000);
	}

	/**
	 * @param driver
	 * @param locator
	 * @param text
	 */
	public void dropdownSelectByText(RemoteWebDriver driver, By locator, String text) {
		try {
			waitForElementVisibility(driver, locator);
			Select dropdown = new Select(driver.findElement(locator));
			dropdown.selectByVisibleText(text);
			log.info("SelectByVisibleText action performed successfully on element " + driver);
		} catch (TimeoutException e) {
			log.error("Timeout occurs while selecting dropdrown option for element " + locator);
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			log.error("NoSuchElementException occurs while selecting dropdrown option " + locator);
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param text
	 */
	public void dropdownSelectByText(RemoteWebDriver driver, WebElement element, String text) {
		hardWait(3000);
		try {
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(text);
			log.info("SelectByVisibleText action performed successfully on element " + driver);
		} catch (TimeoutException e) {
			log.error("Timeout occurs while selecting dropdrown option for element " + element);
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			log.error("NoSuchElementException occurs while selecting dropdrown option " + element);
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param index
	 */
	public void dropdownSelectByIndex(RemoteWebDriver driver, By locator, int index) {
		try {
			waitForElementVisibility(driver, locator);
			Select dropdown = new Select(driver.findElement(locator));
			dropdown.selectByIndex(index);
			log.info("SelectByIndex action performed successfully on element " + driver);
		} catch (TimeoutException e) {
			log.error("Timeout occurs while selecting dropdrown option for element " + locator);
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			log.error("NoSuchElementException occurs while selecting dropdrown option " + locator);
		}
	}

	

	/**
	 * @param driver
	 * @param locator
	 * @param value
	 */
	public void dropdownSelectByValue(RemoteWebDriver driver, By locator, String value) {
		try {
			waitForElementVisibility(driver, locator);
			Select dropdown = new Select(driver.findElement(locator));
			dropdown.selectByValue(value);
			log.info("SelectByValue action performed successfully on element " + driver);
		} catch (TimeoutException e) {
			log.error("Timeout occurs while selecting dropdrown option for element " + locator);
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			log.error("NoSuchElementException occurs while selecting dropdrown option " + locator);
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param value
	 */
	
	/**
	 * @param driver
	 * @param locator
	 * @param value
	 */
	
	

	public String getCurrentDateTime() {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
		Date date = new Date();
		String currentDate = dateformat.format(date).toUpperCase();
		return currentDate;
	}
	
	public String getCurrentDateWithDate(String Actual) throws Exception { 
		
           String result="";
            String []arr=Actual.split(" ");
            String [] date=arr[0].split("-");
            for(int i=date.length-1;i>=0;i--)
            {
                  result=result+date[i];
                  if(i>=date.length-2){
                     result=result+"-";  
                  }
                  
            }
            String S1 = (String) arr[1].subSequence(0, 5);
            result=result+" "+S1;
            System.out.println("Result : " +result);
            return result;
        }    

	public String getThreeDaysDifferenceFromCurrentDate(String curDate, int daysDifference) {
		String nextDate = "";
		try {
			Calendar today = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("yyyy-MMM-d");
			Date date = format.parse(curDate);
			today.setTime(date);
			today.add(Calendar.DAY_OF_YEAR, daysDifference);
			nextDate = format.format(today.getTime()).toUpperCase();
		} catch (Exception e) {
			return nextDate;
		}
		return nextDate;
	}

	public String getThreeDaysDifferenceFromCurrentDateWithFormate(String curDate, int daysDifference) {
		String nextDate = "";
		try {
			Calendar today = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("MMMM d yyyy");
			Date date = format.parse(curDate);
			today.setTime(date);
			today.add(Calendar.DAY_OF_YEAR, daysDifference);
			nextDate = format.format(today.getTime());
		} catch (Exception e) {
			return nextDate;
		}
		return nextDate;
	}

	public String getThreeDaysDifferenceFromCurrentDateWithFormat(String curDate, String dateFormat, int daysDifference) {
		String nextDate = "";
		try {
			Calendar today = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat(dateFormat);
			Date date = format.parse(curDate);
			today.setTime(date);
			today.add(Calendar.DAY_OF_YEAR, daysDifference);
			nextDate = format.format(today.getTime());
		} catch (Exception e) {
			return nextDate;
		}
		return nextDate;
	}
	
	public String getOneYearDifferenceFromCurrentDateWithFormat(String curDate, String dateFormat, int yearDifference) {//sel+25
		String nextDate = "";
		try {
			Calendar today = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat(dateFormat);
			Date date = format.parse(curDate);
			today.setTime(date);
			today.add(Calendar.YEAR, yearDifference);
			nextDate = format.format(today.getTime());
		} catch (Exception e) {
			return nextDate;
		}
		return nextDate;
	}
	
	
	/**
	 * @param drive
	 * @param locators
	 * @return
	 * @throws InterruptedException
	 */
	public boolean isElementPresent(RemoteWebDriver driver, By locators) {
		boolean status = false;
		try {
			driver.findElement(locators).isDisplayed();
			status = true;
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	public boolean isElementPresent(RemoteWebDriver driver, WebElement element) {
		boolean status = false;
		try {
			element.isDisplayed();
			status = true;
		} catch (NoSuchElementException e) {
			status = false;
		}
		return status;
	}

	public boolean checkIfElementIsClickable(RemoteWebDriver driver, By locator) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
			webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkIfElementIsClickable(RemoteWebDriver driver, By locator, int waitTimeForSeconds) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, waitTimeForSeconds);
			webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkIfElementIsClickable(RemoteWebDriver driver, WebElement element) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, WAIT_FOR_SECONDS);
			webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkIfElementIsClickable(RemoteWebDriver driver, WebElement element, int waitTimeForSeconds) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, waitTimeForSeconds);
			webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<WebElement> getWebElements(RemoteWebDriver driver, By locator) {
		return driver.findElements(locator);
	}

	public WebElement getWebElement(RemoteWebDriver driver, By locator) {
		return driver.findElement(locator);
	}

	public WebElement getDynamicWebElementByText(RemoteWebDriver driver, String xpath) {
		return driver.findElement(By.xpath(xpath));
	}

	/**
	 * @param driver
	 * @param locator
	 * @return
	 */
	public String getText(RemoteWebDriver driver, By locator) {
		try {
			waitForElemenVisibility(driver, locator);
			return driver.findElement(locator).getText().trim();
		} catch (StaleElementReferenceException e) {
			return driver.findElement(locator).getText().trim();
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @return
	 * @throws InterruptedException
	 */
	public boolean getCheckBoxStaus(RemoteWebDriver driver, By locator) throws InterruptedException {
		boolean status = false;
		try {
			status = driver.findElement(locator).isSelected();
		} catch (NoSuchElementException e) {
			status = false;
		}
		return status;
	}

	public boolean getCheckBoxStaus(RemoteWebDriver driver, WebElement element) throws InterruptedException {
		boolean status = false;
		try {
			status = element.isSelected();
		} catch (NoSuchElementException e) {
			status = false;
		}
		return status;
	}

	/**
	 * @param driver
	 * @param index
	 */
	public void switchWindows(RemoteWebDriver driver, Integer index) {   //sel+3
		try {
			hardWait(2000);
			Set<String> allWindows = driver.getWindowHandles();
			String[] windows = allWindows.toArray(new String[allWindows.size()]);
			driver.switchTo().window(windows[index]);
		} catch (Exception e) {
			System.out.println("Unexpected issue occurred in switching windows: " + e);
		}
		hardWait(5000);
	}

	public void closeCurrentWindow(RemoteWebDriver driver) {
		driver.close();
	}

	/**
	 * @param driver
	 * @param locator
	 * @param attributeName
	 * @return
	 */
	public String getValueFromAttribute(RemoteWebDriver driver, By locator, String attributeName) {
		waitForElementVisibility(driver, locator);
		return driver.findElement(locator).getAttribute(attributeName);
	}

	/**
	 * @param driver
	 * @param locator
	 * @return
	 */
	public String getElementTagName(RemoteWebDriver driver, By locator) {
		return driver.findElement(locator).getTagName();
	}

	/**
	 * @param driver
	 * @param locator
	 * @return
	 */
	public String getElementCSSColor(RemoteWebDriver driver, By locator) {
		waitForElementVisibility(driver, locator);
		return driver.findElement(locator).getCssValue("color");
	}


	/**
	 * @param driver
	 * @param locator
	 */
	public void mouseHoverAndClick(RemoteWebDriver driver, By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(locator)).click().build().perform();
	}

	/**
	 * @param driver
	 * @param element
	 */
	public void mouseHoverAndClickByElement(RemoteWebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().perform();
	}

	/**
	 * @param driver
	 * @param locator
	 */
	public void mouseHover(RemoteWebDriver driver, By locator) {
		Actions action = new Actions(driver);
		WebElement target = driver.findElement(locator);
		action.moveToElement(target).perform();
	}

	/**
	 * @param driver
	 * @param locator Limitations:Without help of ActionClass we are not able to
	 *                draw rectangle on map Methods :moveToElement() ,moveByOffset()
	 *                ,doubleClick()
	 */
	public void drawRectangleUsingAction(RemoteWebDriver driver, By locator) {
		hardWait(10000);
		Actions builder = new Actions(driver);
		Action drawAction = builder.moveToElement(getWebElement(driver, locator), 15, 10) // start points x axis and y axis.
				.click().moveByOffset(20, 30) // 1st points (x1,y1)
				.click().moveByOffset(10, 15).click()// 2nd points (x2,y2)
				.pause(java.time.Duration.ofSeconds(2)).moveByOffset(50, 20) // 3rd points (x1,y1)
				.pause(java.time.Duration.ofSeconds(2)).doubleClick().build();
		hardWait(2000);
		drawAction.perform();
		hardWait(1000);
	}

	/**
	 * @param driver
	 * @param locator Limitations:Without help of Action Class we are not able to draw circle on map Methods:moveToElement(),moveByOffset(),doubleClick()
	 */
	public void drawCricleUsingAction(RemoteWebDriver driver, By locator) {
		Actions builder = new Actions(driver);
		Action drawAction = builder.moveToElement(getWebElement(driver, locator), 35, 15) // start points x axis and y axis.														
				.click().moveByOffset(10, 20) // 1st points (x1,y1)
				.click().moveByOffset(10, 30).click()// 2nd points (x2,y2)
				.moveByOffset(15, 30) // 3rd points (x1,y1)
				.doubleClick().build();
		drawAction.perform();
		hardWait(1000);
	}

	/**
	 * @param driver
	 * @param locator Limitations:Without help of ActionClass we are not able to draw line on map Methods :moveToElement() ,moveByOffset(),doubleClick()
	 */
	public void drawLineOnMapUsingAction(RemoteWebDriver driver, By locator) {
		hardWait(10000);
		Actions builder = new Actions(driver);
		Action drawAction = builder.moveToElement(getWebElement(driver, locator), 15, 10) // start points x axis and y
				.click().moveByOffset(20, 30) // 1st points (x1,y1)
				.click().moveByOffset(30, 40) // 2rd points (x1,y1)
				.doubleClick().build();
		hardWait(2000);
		drawAction.perform();
		hardWait(2000);
	}

	public void drawLineOnMapUsingActions(RemoteWebDriver driver, By locator) {
		hardWait(10000);
		Actions builder = new Actions(driver);
		Action drawAction = builder.moveToElement(getWebElement(driver, locator), 15, 10) // start points x axis and y
				.click().moveByOffset(20, 30) // 1st points (x1,y1)
				.doubleClick().build();
		hardWait(2000);
		drawAction.perform();
		hardWait(2000);
	}
	
	public void drawLineOnMap(RemoteWebDriver driver, By locator) {
		hardWait(10000);
		Actions builder = new Actions(driver);
		Action drawAction = builder.moveToElement(getWebElement(driver, locator), 55, 50) // start points x axis and y
				.click().moveByOffset(60, 70) // 1st points (x1,y1)
				.click().moveByOffset(70, 80) // 2rd points (x1,y1)
				.doubleClick().build();
		hardWait(2000);
		drawAction.perform();
		hardWait(2000);
	}

	/**
	 * @param driver
	 * @param element
	 */
	public void mouseHover(RemoteWebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	/**
	 * @param driver
	 */
	

	
	
	/**
	 * @param milliSeconds
	 * @throws InterruptedException
	 */
	public void hardWait(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void backToNavigate(RemoteWebDriver driver) {
		driver.navigate().back();
	}

	/**
	 * @param driver
	 * @param index
	 * @throws InterruptedException
	 */
	public void switchToFrameByIndex(RemoteWebDriver driver, int index) {
		driver.switchTo().frame(index);
	}

	public void switchOutFromIframe(RemoteWebDriver driver) {
		driver.switchTo().defaultContent();
		hardWait(1000);
	}

	/*
	 * @param driver
	 * @param dateValue
	 * @throws InterruptedException
	 */
	
	
	/**
	 * @param driver
	 * @param locationToSelect
	 */
	
	/**
	 * @param driver
	 * @param value
	 */
	
	public void selectValue(RemoteWebDriver driver, String value) {//sel+56
		hardWait(3000);
		By data = By.xpath("//div[text()='"+value+"']");
		hardWait(3000);
		click(driver, data);
		
	}
	
	

	
	
	

	
	public void selectValueInList(RemoteWebDriver driver, String value,By locator) {
		hardWait(2000);
		List<WebElement> compositeQueryTypeElements = getWebElements(driver, locator);
		for (WebElement compositeQueryTypeElement : compositeQueryTypeElements) {
			if (compositeQueryTypeElement.getText().trim().contains(value)) {
				hardWait(1000);
				compositeQueryTypeElement.click();
				hardWait(1000);
				break;
			}
		}
	}
	
	public void selectEqualValueInList(RemoteWebDriver driver, String value,By locator) {
		hardWait(5000);
		List<WebElement> compositeQueryTypeElements = getWebElements(driver, locator);
		for (WebElement compositeQueryTypeElement : compositeQueryTypeElements) {
			if (compositeQueryTypeElement.getText().trim().equals(value)) {
				hardWait(1000);
				compositeQueryTypeElement.click();
				hardWait(1000);
				break;
			}
		}
	}
	
	public boolean getValueStaus(RemoteWebDriver driver, String value,By locator) {
		hardWait(3000);
		boolean status = false;
		List<WebElement> compositeQueryTypeElements = getWebElements(driver, locator);
		for (WebElement compositeQueryTypeElement : compositeQueryTypeElements) {
			if (compositeQueryTypeElement.getText().trim().contains(value)) {
				hardWait(2000);
				status= true;	
				break;
			}
		}
		return status;
	}


		
	
	/**
	 * @param driver
	 * @param time
	 * @throws InterruptedException
	 */
	
	/**
	 * @param driver
	 */
	public void scrollDown(RemoteWebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000)");
	}

	/**
	 * @param driver
	 * @param locator
	 */
	public void scrollToElement(RemoteWebDriver driver, By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(locator));
	}

	/**
	 * @param driver
	 * @param element
	 */
	public void scrollToElement(RemoteWebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	public void scrollToTop(RemoteWebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	/**
	 * @param driver
	 * @param pdfURL
	 * @param pdfFileName
	 * @return
	 * @throws IOException
	 */
	public String readPDFInURL(RemoteWebDriver driver, String pdfURL, String pdfFileName) throws IOException {
		// page with example pdf document
		driver.get(pdfURL + pdfFileName);
		String output = null;
		URL url = new URL(driver.getCurrentUrl());
		InputStream is = url.openStream();
		BufferedInputStream fileToParse = new BufferedInputStream(is);
		org.apache.pdfbox.pdmodel.PDDocument document = null;
		try {
			document = PDDocument.load(fileToParse);
			output = new PDFTextStripper().getText(document);
		} finally {
			if (document != null) {
				document.close();
			}
			fileToParse.close();
			is.close();
		}
		hardWait(2000);
		driver.navigate().back();
		hardWait(3000);
		return output;
	}

	public int getWordsSizeInPDFInURL(RemoteWebDriver driver, String pdfURL, String pdfFileName) throws IOException {
		driver.get(pdfURL + pdfFileName);
		String output = null;
		URL url = new URL(driver.getCurrentUrl());
		InputStream is = url.openStream();
		BufferedInputStream fileToParse = new BufferedInputStream(is);
		org.apache.pdfbox.pdmodel.PDDocument document = null;
		try {
			document = PDDocument.load(fileToParse);
			output = new PDFTextStripper().getText(document);
		} finally {
			if (document != null) {
				document.close();
			}
			fileToParse.close();
			is.close();
		}
		hardWait(2000);
		String values[] = output.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
		System.out.println(values.length);
		driver.navigate().back();
		hardWait(3000);
		return values.length;
	}

	/**
	 * @param formate
	 * @return
	 */
	public String getCurrentDateWithUserFormate(String format) {
		DateFormat dateformat = new SimpleDateFormat(format);
		Date date = new Date();
		String currentDate = dateformat.format(date);
		System.out.println(currentDate);
		return currentDate;
	}

	public void doubleClickOnElement(RemoteWebDriver driver, By elem) {
		Actions actions = new Actions(driver);
		WebElement elementLocator = driver.findElement(elem);
		actions.doubleClick(elementLocator).perform();
	}

	public RemoteWebDriver openNewIncognitoTab() {
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_setting_values.geolocation", 1);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities = capabilities.merge(DesiredCapabilities.chrome());
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		RemoteWebDriver driverIncognito = new ChromeDriver(options);
		driverIncognito.manage().window().maximize();
		return driverIncognito;
	}

	public RemoteWebDriver openNewWindow() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		RemoteWebDriver driverIncognito = new ChromeDriver();
		driverIncognito.manage().window().maximize();
		return driverIncognito;
	}

	public void clickAllowLocation() {
		Robot robot;
		try {
			robot = new Robot();
			robot.delay(5000);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			hardWait(2000);
		} catch (AWTException e) {
			log.error("Not able to click on allow location");
		}
	}
	public void selectAllText() {
		Robot robot;
		try {
			robot = new Robot();
			robot.delay(5000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
			hardWait(2000);
		} catch (AWTException e) {
			log.error("Not able to Select All Text");
		}
	}

	public void pressEnter() {
		Robot robot;
		try {
			robot = new Robot();
			robot.delay(5000);
			robot.keyPress(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			log.error("Not able press Enter");
		}
	}

	
	

	
	public void closeExtraTab(RemoteWebDriver driver) {
		ArrayList<String> handles = new ArrayList<String>(driver.getWindowHandles());
		if (handles.size() > 1) {
			driver.switchTo().window(handles.get(1));
			driver.close();
			driver.switchTo().window(handles.get(0));
			hardWait(2000);
		}
	}
	
	public String getTextOfPrintPreviewPage(RemoteWebDriver driver) {
		String text = driver.findElement(By.xpath("//div")).getText();
		return text;
			}


	


	/**
	 * @param driver
	 * @throws UnsupportedFlavorException
	 */
	public String getCopyMessageFromCopyToClipboard(RemoteWebDriver driver) {
		String clipBoradMsg = null;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		try {
			clipBoradMsg = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return clipBoradMsg;

	}

	public void pressEnterButton(RemoteWebDriver driver) {
		hardWait(3000);
		Actions actions = new Actions(driver);
		By noteContains = By.cssSelector(".fr-element.fr-view");
		actions.moveToElement(driver.findElement(noteContains));
		actions.click().build().perform();
		driver.findElement(noteContains).sendKeys(Keys.chord(Keys.ENTER));
	}

	
	
	public void verifyASCAndDESCOrder(RemoteWebDriver driver, SoftAssert softAssert, String header, String listCount,
			String listElements, Boolean sorted) {
		hardWait(4000);
		By heading = By.xpath(header);
		if (!sorted) {
			hardWait(4000);
			click(driver, heading);
		}
		hardWait(8000);
		List<WebElement> allSavedViewName = getWebElements(driver, By.xpath(listCount));
		int count = allSavedViewName.size();
		List<String> actualAscNamelist = new ArrayList<String>();
		List<String> actualDescNamelist = new ArrayList<String>();
		List<String> expectAscNamelist = new ArrayList<String>();
		List<String> expectDescNamelist = new ArrayList<String>();
		String[] eleXpath = listElements.split("count");
		hardWait(5000);
		for (int i = 1; i < count; i++) {
			hardWait(1000);
			System.out.println(getWebElement(driver, By.xpath(eleXpath[0] + (i) + eleXpath[1])).getText().trim());
			expectAscNamelist.add(getWebElement(driver, By.xpath(eleXpath[0] + (i) + eleXpath[1])).getText().trim());

		}
		hardWait(1000);
		Collections.sort(expectAscNamelist, String.CASE_INSENSITIVE_ORDER);// Ascending sort
		hardWait(1000);

		/************************ expected asc list ******************/
		/*
		 * System.out.println("expected asc list"); for (String str : expectAscNamelist)
		 * { System.out.println(str); }
		 */
		/*************************************************************/
		for (int i = 1; i < count; i++) {
			hardWait(1000);
			System.out.println(getWebElement(driver, By.xpath(eleXpath[0] + (i) + eleXpath[1])).getText().trim());
			actualAscNamelist.add(getWebElement(driver, By.xpath(eleXpath[0] + (i) + eleXpath[1])).getText().trim());
		}
		/************************ Actual asc list ******************/
		/*
		 * System.out.println(" Actual asc list"); for (String str : actualAscNamelist)
		 * { System.out.println(str); }
		 */
		/*************************************************************/
		hardWait(4000);
		click(driver, heading);
		hardWait(10000);
		for (int i = 1; i < count; i++) {
			hardWait(1000);
			System.out.println(getWebElement(driver, By.xpath(eleXpath[0] + (i) + eleXpath[1])).getText().trim());
			expectDescNamelist.add(getWebElement(driver, By.xpath(eleXpath[0] + (i) + eleXpath[1])).getText().trim());
		}
		hardWait(1000);
		Collections.sort(expectDescNamelist, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
		/************************ Expected desc list ******************/
		/*
		 * System.out.println("Expected desc list "); for (String str :
		 * expectDescNamelist) { System.out.println(str); }
		 */
		/*************************************************************/
		for (int i = 1; i < count; i++) {
			hardWait(1000);
			System.out.println(getWebElement(driver, By.xpath(eleXpath[0] + (i) + eleXpath[1])).getText().trim());
			actualDescNamelist.add(getWebElement(driver, By.xpath(eleXpath[0] + (i) + eleXpath[1])).getText().trim());
		}
		/************************ Actual desc list ******************/
		/*
		 * System.out.println("Actual desc list"); for (String str : actualDescNamelist)
		 * { System.out.println(str); }
		 */
		/*************************************************************/
		hardWait(3000);
		softAssert.assertEquals(actualAscNamelist, expectAscNamelist, "Ascending order is not working");
		softAssert.assertEquals(actualDescNamelist, expectDescNamelist, "Descending order is not working");
	}

	public HashMap<String, String> getVeociHexaColorCode() {
		HashMap<String, String> hexaColor = new HashMap<String, String>();
		hexaColor.put("Aqua", "#0198AE");
		hexaColor.put("Blue", "#1E70E0");
		hexaColor.put("Green", "#11AA44");
		hexaColor.put("Black", "#000000");
		hexaColor.put("Brown", "#6C2516");
		hexaColor.put("Fuchisa", "#CF31BB");
		hexaColor.put("Gray", "#626262");
		hexaColor.put("Lime", "#93C934");
		hexaColor.put("Maroon", "#970502");
		hexaColor.put("Navy", "#173C6F");
		hexaColor.put("Orange", "#F25C01");
		hexaColor.put("Purple", "#6A30C1");
		hexaColor.put("Red", "#E31111");
		hexaColor.put("Yellow", "#E2B501");
		return hexaColor;
	}

	public String getHexaColorCodeFromRGB(RemoteWebDriver driver, By locator) {
		waitForElementVisibility(driver, locator);
		String value = getValueFromAttribute(driver, locator, "style");
		String rgbColor[] = value.split(":|;");
		String hexaColor = Color.fromString(rgbColor[1]).asHex();
		return hexaColor.toUpperCase();
	}

	public void openCopiedLinkInNewWindow(RemoteWebDriver driver) throws IOException, UnsupportedFlavorException {
		driver.executeScript("window.open('','_blank');");
		hardWait(3000);
		switchWindows(driver, 1);
		driver.get(getCopyMessageFromCopyToClipboard(driver));
	}

	public void openNewTab(RemoteWebDriver driver) {
		driver.executeScript("window.open('','_blank');");
		hardWait(3000);
		switchWindows(driver, 1);
	}

	public void openLinkNewTab(RemoteWebDriver driver, By locator) {
		try {
			mouseHover(driver, locator);
			getWebElement(driver, locator).sendKeys(Keys.CONTROL, Keys.ENTER);
			hardWait(3000);
			switchWindows(driver, 1);
		} catch (Exception e) {
		}
	}

	public void clickOnMapImage(RemoteWebDriver driver, By locator) {
		try {
			Actions builder = new Actions(driver);
			Action drawAction = builder.moveToElement(getWebElement(driver, locator), 15, 10) // start points x axis and y axis
					.click().moveByOffset(10, 20) // 1st points (x1,y1)
					.click().moveByOffset(10, 15).click()// 2nd points (x2,y2)
					.moveByOffset(10, 15) // 3rd points (x1,y1)
					.doubleClick().build();
			drawAction.perform();
		} catch (Exception e) {
			//System.out.println("Map image is not clicked");
		}
		hardWait(1000);
	}

	public void openSecondChildWindow(RemoteWebDriver driver) {
		driver.executeScript("window.open('','_blank');");
		switchWindows(driver, 2);
	}


	public void minimizeWindow(RemoteWebDriver driver) {
		driver.manage().window().setPosition(new Point(-2000, 0));
	}

	public boolean readPDFInURLt(RemoteWebDriver driver) throws IOException {
		PDPage page = new PDPage();
		PDRectangle mediaBox = page.getMediaBox();
		boolean isLandscape = mediaBox.getWidth() > mediaBox.getHeight();
		System.out.println(isLandscape);
		return isLandscape;
	}

	public boolean readPDFOrientationInExportedPDF(RemoteWebDriver driver, String pdfURL, String pdfFileName)
			throws IOException {
		driver.get(pdfURL + pdfFileName);
		URL url = new URL(driver.getCurrentUrl());
		InputStream is = url.openStream();
		BufferedInputStream fileToParse = new BufferedInputStream(is);
		org.apache.pdfbox.pdmodel.PDDocument document = null;
		try {
			document = PDDocument.load(fileToParse);
			String titleGuess = document.getDocumentInformation().getTitle();
			System.out.println("This is PDF Title" + titleGuess);

			int pageCount = document.getNumberOfPages();
			System.out.println(pageCount);
			for (int i = 0; i < pageCount; i++) {
				PDRectangle pageSize = document.getPage(i).getMediaBox();
				int degree = document.getPage(i).getRotation();
				if ((pageSize.getWidth() > pageSize.getHeight()) || (degree == 90) || (degree == 270)) {
					document.close();
					System.out.println("PDF document is landscape Orientation");
					System.out.println("This is PDF Width Size" + pageSize.getWidth());
					System.out.println("This is PDF Height Size" + pageSize.getHeight());

					return true; // document is landscape
				}
				System.out.println("PDF document is Portrait Orientation");
				System.out.println("This is PDF Width Size" + pageSize.getWidth());
				System.out.println("This is PDF Height Size" + pageSize.getHeight());
			}
		} finally {
			if (document != null) {
				document.close();
			}
			fileToParse.close();
			is.close();
		}
		return false;
	}

	public String readPDFFileNameFromUrlPDF(RemoteWebDriver driver, String pdfURL, String pdfFileName)
			throws IOException {
		driver.get(pdfURL + pdfFileName);
		URL url = new URL(driver.getCurrentUrl());
		String[] pdfurl = url.toString().split("/");
		String spiliturl = pdfurl[pdfurl.length - 1].replaceAll("%20", " ");
			if (spiliturl.contains(pdfFileName)) {
				} 
			else {
					}
		return spiliturl;
	}

	public String readTPDFFilePageNumberOne(RemoteWebDriver driver, String pdfURL, String pdfFileName)
			throws IOException {
		driver.get(pdfURL + pdfFileName);
		String result = null;
		URL url = new URL(driver.getCurrentUrl());
		InputStream is = url.openStream();
		BufferedInputStream fileToParse = new BufferedInputStream(is);
		org.apache.pdfbox.pdmodel.PDDocument document = null;
		try {
			document = PDDocument.load(fileToParse);
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setStartPage(1);
			stripper.setEndPage(1);
			result = stripper.getText(document);
			System.out.println("PDF document is Portrait Orientation" + result);
			return result;
		}

		finally {
			if (document != null) {
				document.close();
			}
			fileToParse.close();
			is.close();
		}

	}

	public String readTPDFFilePageNumberTwo(RemoteWebDriver driver, String pdfURL, String pdfFileName)
			throws IOException {
		driver.get(pdfURL + pdfFileName);
		String result = null;
		URL url = new URL(driver.getCurrentUrl());
		InputStream is = url.openStream();
		BufferedInputStream fileToParse = new BufferedInputStream(is);
		org.apache.pdfbox.pdmodel.PDDocument document = null;
		try {
			document = PDDocument.load(fileToParse);
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setStartPage(2);
			stripper.setEndPage(2);
			result = stripper.getText(document);
			System.out.println("PDF document is Portrait Orientation" + result);
			return result;
		}

		finally {
			if (document != null) {
				document.close();
			}
			fileToParse.close();
			is.close();
		}
	}

	public ArrayList<Object> readPDFIimage(RemoteWebDriver driver, String pdfURL, String pdfFileName) throws IOException {
	ArrayList<Object> pdfImageCount = new ArrayList<>();
	Boolean IsImagePdf=false;
	int imageCount=0;
	driver.get(pdfURL + pdfFileName);
	URL url = new URL(driver.getCurrentUrl());
	InputStream is = url.openStream();
	BufferedInputStream fileToParse = new BufferedInputStream(is);
	org.apache.pdfbox.pdmodel.PDDocument document = null;
	try {
		document = PDDocument.load(fileToParse);
		 PDPageTree list = document.getPages();

		    for (PDPage page : list) {
		        PDResources pdResources = page.getResources();
		        for (COSName c : pdResources.getXObjectNames()) {
		            PDXObject o = pdResources.getXObject(c);
		            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {
		           IsImagePdf=true;
		           imageCount++;
		         
		            }
		        }
		    }
		       pdfImageCount.add(IsImagePdf);
	           pdfImageCount.add(imageCount);
	} finally {
		if (document != null) {
			document.close();
		}
		fileToParse.close();
		is.close();
	}
	hardWait(2000);
	driver.navigate().back();
	hardWait(3000);
	return pdfImageCount;
}
	
public String readFile(String pdfURL, String pdfFileName) {
	String data = "";
	File f = new File(pdfURL+pdfFileName);
	try {
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) != null){
			data+= line+"\n";
		}
		br.close();
	}catch (FileNotFoundException e) {

		e.printStackTrace();
	}catch (IOException e) {

		e.printStackTrace();
	}catch (Exception e) {

		e.printStackTrace();
	}
	System.out.println("------------"+data);
	return data;
}

	
public void dynamicScrollToLoadAllEntriesField(RemoteWebDriver driver, By locator) {//sel+21
	List <WebElement>  li = getWebElements(driver, locator);
	for (int j = 0; j <li.size(); j++) {
	List <WebElement>  li1 = getWebElements(driver, locator);
	scrollToElement(driver, li1.get(j));
	}
}

public void waitMoreSec(RemoteWebDriver driver, By locator) {//sel+56
	if (!(getWebElements(driver, locator).size()>0)) {
		hardWait(5000);	
	}
}

public void fluentWaitClick (RemoteWebDriver driver, By locator) { //sel+25
	try {
	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
			.pollingEvery(Duration.ofSeconds(2)).ignoring(Exception.class);
    wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	
}
catch(Exception e) {
	JSClick(driver, driver.findElement(locator));
	} 
}




}
