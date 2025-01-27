package init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import constants.Constants;
import utils.FileOperations;

public class DriverFactory {

	ThreadLocal<RemoteWebDriver> webDriver = new ThreadLocal<RemoteWebDriver>();
	private RemoteWebDriver remoteWebDriver;
	private static Logger log = Logger.getLogger(DriverFactory.class);
	FileOperations fileOperations=new FileOperations();
	Constants constants=new Constants();
	static Map<String,RemoteWebDriver> map = new HashMap<String,RemoteWebDriver>();


	@BeforeSuite
	public void checkJenkinsEnvironmentVariables() throws ConfigurationException, IOException {
		List<String> envVariable = new ArrayList<>();
		envVariable.add(System.getenv("roomID"));
		envVariable.add(System.getenv("roomName"));
		envVariable.add(System.getenv("username"));
		envVariable.add(System.getenv("callTreeSaveViewName"));
		envVariable.add(System.getenv("password"));
		
		for(String s: envVariable) {
			if(s == null) {
				break;
			}
			else {
				try {
					Thread.sleep(2000);
			fileOperations.updateValueToPropertyFile(constants.CONFIG_WEB_FILE_PATH, "roomID", envVariable.get(0));
			Thread.sleep(5000);
			fileOperations.updateValueToPropertyFile(constants.CONFIG_WEB_FILE_PATH, "roomName", envVariable.get(1));
			Thread.sleep(5000);
			fileOperations.updateValueToPropertyFile(constants.CONFIG_WEB_FILE_PATH, "username", envVariable.get(2));
			Thread.sleep(5000);
			fileOperations.updateValueToPropertyFile(constants.CONFIG_WEB_FILE_PATH, "CallTreeSaveViewName", envVariable.get(3));
			Thread.sleep(5000);
			fileOperations.updateValueToPropertyFile(constants.CONFIG_WEB_FILE_PATH, "password", envVariable.get(4));
			break;
				}
				catch (Exception e) {
					System.out.println("Code have some error");
				}
			}
		}
	}
	/**
	 * Create a WebDriver Instance
	 * 
	 * @param browser
	 * @param locale
	 * @throws Exception
	 */
	public DriverFactory() throws Exception {
		String browser=fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "browserName");
		String osType = getOperatingSystemType();
		checkJenkinsEnvironmentVariables();
		
		switch (browser.toLowerCase()) {
		case "ie":
			log.info("Initializing driver for Internet Explorer...");
			if (osType.equals("Windows")) {
				System.setProperty("webdriver.ie.driver", "");
				InternetExplorerOptions options = new InternetExplorerOptions();
				options.introduceFlakinessByIgnoringSecurityDomains();
				webDriver.set(new InternetExplorerDriver(options));
				log.info("Driver for Internet Explorer initialized.");
				break;
			} else {
				throw new Exception("You are not running the tests on windows operating system");
			}
		case "edge":
			log.info("Initializing driver for Edge...");
			if (osType.equals("Windows")) {
				System.setProperty("webdriver.edge.driver", "");
				EdgeOptions options = new EdgeOptions(); // we can add the options for edge in future as we need
				webDriver.set(new EdgeDriver(options));
				log.info("Driver for Edge initialized.");
				break;
			} else {
				throw new Exception("You are not running the tests on windows operating system");
			}
		case "chrome":
			Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			String downloadLoc = s + File.separator + "testDownloadedItems" + File.separator;
			FileOperations fileOpObj = new FileOperations();
			log.info("Initializing driver for Chrome...");
			DesiredCapabilities cap  =  new DesiredCapabilities();
			List<String> arguments = new ArrayList<String>();
			arguments.add("--lang=en");
			arguments.add("--allow-file-access-from-files");


			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
			chromePrefs.put("download.default_directory", downloadLoc);
			
			chromePrefs.put("credentials_enable_service", false);
			chromePrefs.put("profile.password_manager_enabled", false);
			chromePrefs.put("plugins.plugins_disabled", new String[]{
					"Adobe Flash Player", "Chrome PDF Viewer"});


			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);// Added by selenium+4
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--enable-gpu-rasterization");
			chromeOptions.addArguments("--use-gpu-memory-buffers-for-capture");
			chromeOptions.addArguments("--use-gpu-in-tests");
			chromeOptions.addArguments("--enable-gpu-memory-buffer-compositor-resources");
			chromeOptions.addArguments("--enable-gpu-memory-buffer-video-frames");
			chromeOptions.setExperimentalOption("prefs", chromePrefs);
			chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			chromeOptions.setExperimentalOption("useAutomationExtension", false);
			chromeOptions.addArguments("start-maximized");
			chromeOptions.addArguments(arguments);
			chromeOptions.setCapability("screen-resolution","1920x1080");



			if (osType.equals("MacOS")) {
				System.setProperty("webdriver.chrome.driver","src/../drivers/chromedrivermac");
				try {
					remoteWebDriver=new ChromeDriver(chromeOptions);
					setDriver(remoteWebDriver);
				} catch(Exception e){
					e.printStackTrace();
				}
				log.info("Driver for Chrome initialized.");
				break;
			} else if (osType.equals("Windows")) {
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				try {
					remoteWebDriver=new ChromeDriver(chromeOptions);
					setDriver(remoteWebDriver);
				} catch(Exception e){
					e.printStackTrace();
				}
				log.info("Driver for Chrome initialized.");
				break;
			} else if (osType.equals("Linux")) {
				try {
					chromeOptions.addArguments("--headless");
					remoteWebDriver=new ChromeDriver(chromeOptions);
					setDriver(remoteWebDriver);
				} catch(Exception e){
					e.printStackTrace();
				}
				log.info("Driver for Chrome initialized.");
				break;

			} else {
				throw new Exception("Please check your operating system");
			}
		case "firefox":
			log.info("Initializing driver for Firefox...");
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			firefoxProfile.setPreference("intl.accept_languages", "en");
			firefoxProfile.setPreference("browser.download.folderList", 2);
			firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/plain application/zip application/tar");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setProfile(firefoxProfile);
			webDriver.set(new FirefoxDriver(firefoxOptions));
			log.info("Driver for Firefox initialized.");
			break;
		default:
			break;
		}
		try {
			Thread.sleep(2000);
			getDriver().manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
		} catch (NoSuchSessionException e) {
			getDriver().manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
		}
		//Resize current window to the set dimension
//		try {
//			getDriver().manage().window().maximize();
//
//		} catch (Exception e) {
//		}

	}

	//	public RemoteWebDriver getDriver() {
	//		return webDriver.get();
	//	}

	/**
	 * detect the OS on which tests are running
	 */
	public static String getOperatingSystemType() {
		String OS = System.getProperty("os.name", "generic").toLowerCase();
		String identifiededOS;

		if ((OS.indexOf("mac") >= 0)) {
			identifiededOS = "MacOS";
		} else if (OS.indexOf("win") >= 0) {
			identifiededOS = "Windows";
		} else if (OS.indexOf("nux") >= 0) {
			identifiededOS = "Linux";
		} else {
			identifiededOS = "Other";
		}

		return identifiededOS;
	}
	public static synchronized void setDriver(RemoteWebDriver remote) {
		String id=Thread.currentThread().getName().trim();
		map.put(id, remote);
	}

	public static synchronized RemoteWebDriver getDriver() {

		String id=Thread.currentThread().getName().trim();
		//		System.out.println(map);
		return map.get(id); 
	}

	public static synchronized void setDriverFactory(DriverFactory factory) {
		factory = factory;
	}
}
