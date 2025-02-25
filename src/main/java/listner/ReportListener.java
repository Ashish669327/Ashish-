package listner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Test;

import constants.Constants;
import init.DriverFactory;
import utils.EncryptionDecryption;
import utils.FileOperations;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IClassListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportListener implements ITestListener, IClassListener, ISuiteListener {

	Path currentRelativePath = Paths.get("");
	String s = currentRelativePath.toAbsolutePath().toString();
	String basePath = s + File.separator;

	private static ExtentReports extent;
	private static ThreadLocal<ISuite> ACCESS = new ThreadLocal<ISuite>();
	private ExtentSparkReporter htmlReporter;
	private static ScreenCapture med;
	private static ThreadLocal<Integer> testMethodCount = new ThreadLocal<Integer>();
//	private ThreadLocal<String> testRunStatus = new ThreadLocal<String>();
	String testRunStatus = null;

	private static String filePathex = System.getProperty("user.dir") + "/Extent_Reports/extentreport.html";
	private static Map<String, RemoteWebDriver> driverMap = new HashMap<String, RemoteWebDriver>();
	private static Map<String, ExtentTest> extentTestMap = new HashMap<String, ExtentTest>();
//	String startTime, endTime, totalTime;
	String totalTime;
	LocalDateTime startDateTime, endDateTime;
	boolean executionFinished;

	Constants constants = new Constants();
	FileOperations fileOperations = new FileOperations();
	EncryptionDecryption encryptionDecryptionObj = new EncryptionDecryption();

	public synchronized ExtentTest startTest(String testName) {
		ExtentTest classNode = getExtent().createTest(testName);
		extentTestMap.put(testName, classNode);
		return classNode;
	}

	public static synchronized ExtentTest getTest(String testName) {
		return extentTestMap.get(testName);
	}

	public void onStart(ISuite suite) {
		try {
			fileOperations.concateValueToPropertyFile(constants.CONFIG_WEB_FILE_PATH, "body", suite.getName());
		} catch (ConfigurationException | IOException e) {
		}
		startDateTime = LocalDateTime.now();  
//		startTime = dtf.format(now);
		
		this.extent = getExtent();
		suite.setAttribute("extent", this.extent);
//		testRunStatus.set("pass");
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		String testDownloadedItems=s+File.separator+"testDownloadedItems"+File.separator;
		FileOperations fileOpObj = new FileOperations();
		fileOpObj.cleanDir(s + File.separator + "Zipped Report/");
		fileOpObj.cleanDir(s + File.separator + "Extent_Reports/Failure Screenshots/");
		fileOpObj.cleanDir(s + File.separator + "Extent_Reports/Passed Screenshots/");
		fileOpObj.cleanDir(s + File.separator + "Extent_Reports/Skipped Screenshots/");
		fileOpObj.cleanDir(testDownloadedItems);
		
		testRunStatus ="pass";
		
			}
		
	

	public void onStart(ITestContext context) {
	}

	public void onBeforeClass(ITestClass testclass) {
		startTest(testclass.getRealClass().getSimpleName());
		testMethodCount.set(-1);
	}

	public void onTestStart(ITestResult result) {		
		String testClassName = result.getTestClass().getRealClass().getSimpleName();
		System.out.println("class name: "+testClassName+" with instance id :"+DriverFactory.getDriver());
		driverMap.put(testClassName, DriverFactory.getDriver());
		getTest(result.getInstanceName().replace(".", " ").split(" ")[result.getInstanceName().replace(".", " ").split(" ").length-1].trim())
		.createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
		.log(Status.INFO, "Test method " + result.getMethod().getMethodName() + " started");

		Integer testMethodCountValue = testMethodCount.get();
		testMethodCountValue = testMethodCountValue + 1;
		testMethodCount.set(testMethodCountValue);
	}

	public void onTestSuccess(ITestResult result) {
		String methodName = result.getName().trim();
		String filePath = System.getProperty("user.dir") + "/Extent_Reports/Passed Screenshots/";
		takeScreenShot(filePath, methodName, driverMap.get(result.getTestClass().getRealClass().getSimpleName()));

		Log logObj = new Log();  
		logObj.setDetails("Test method " + result.getMethod().getMethodName() + " completed successfully");
		logObj.setStatus(Status.PASS);

		med	= new ScreenCapture("", "PassedScreenshot", "", "Passed Screenshots/" + methodName + ".png");
		List<Test> tests = getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length-1].trim())
				.getModel()
				.getChildren();

		for(Test test: tests) {
			if(test.getName().trim().equals(methodName)) {
				test.addLog(logObj);
				test.addMedia(med);
				break;
			}
		}
	}

	public void onTestFailure(ITestResult result) {
//		testRunStatus.set("fail");
		testRunStatus = "fail";
		String methodName = result.getName().trim();
		String filePath = System.getProperty("user.dir") + "/Extent_Reports/Failure Screenshots/";
		takeScreenShot(filePath, methodName, driverMap.get(result.getTestClass().getRealClass().getSimpleName()));

		Log logObj = new Log();
		logObj.setDetails("Test method " + result.getMethod().getMethodName() + " failed due to Exception -----> " + Reporter.getCurrentTestResult().getThrowable().toString());
		logObj.setStatus(Status.FAIL);

		med	= new ScreenCapture("", "FailedScreenshot", "", "Failure Screenshots/" + methodName + ".png");
		List<Test> tests = getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length-1].trim())
				.getModel()
				.getChildren();

		for(Test test: tests) {
			if(test.getName().trim().equals(methodName)) {
				test.addLog(logObj);
				test.addMedia(med);
				break;
			}
		}
	}

	public void onTestSkipped(ITestResult result) {	
		testRunStatus = "fail";
		try {			
			String testClassName = result.getTestClass().getRealClass().getSimpleName();
			System.out.println("class name: "+testClassName+" with instance id :"+DriverFactory.getDriver());
			driverMap.put(testClassName, DriverFactory.getDriver());
			getTest(result.getInstanceName().replace(".", " ").split(" ")[result.getInstanceName().replace(".", " ").split(" ").length-1].trim())
			.createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
			.log(Status.SKIP, "Test method " + result.getMethod().getMethodName() + " skipped");
//
//			Integer testMethodCountValue = testMethodCount.get();
//			testMethodCountValue = testMethodCountValue + 1;
//			testMethodCount.set(testMethodCountValue);

			String methodName = result.getName().trim();
			String filePath = System.getProperty("user.dir") + "/Extent_Reports/Skipped Screenshots/";
			takeScreenShot(filePath, methodName, driverMap.get(result.getTestClass().getRealClass().getSimpleName()));

			Log logObj = new Log();
			logObj.setDetails("Test method " + result.getMethod().getMethodName() + " skpped due to Exception -----> " + Reporter.getCurrentTestResult().getThrowable().toString());
			logObj.setStatus(Status.SKIP);

			med	= new ScreenCapture("", "SkippedScreenshot", "", "Skipped Screenshots/" + methodName + ".png");
			List<Test> tests = getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length-1].trim())
					.getModel()
					.getChildren();

			for(Test test: tests) {
				if(test.getName().trim().equals(methodName)) {
					test.addLog(logObj);
					test.addMedia(med);
					break;
				}
			}

			System.out.println("Screenshot will be captured");
		} catch (Exception e) {
			String testClassName = result.getTestClass().getRealClass().getSimpleName();
			System.out.println("class name: "+testClassName+" with instance id :"+DriverFactory.getDriver());
			driverMap.put(testClassName, DriverFactory.getDriver());
			getTest(result.getInstanceName().replace(".", " ").split(" ")[result.getInstanceName().replace(".", " ").split(" ").length-1].trim())
			.createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
			.log(Status.SKIP, "Test method " + result.getMethod().getMethodName() + " skipped");
//
//			Integer testMethodCountValue = testMethodCount.get();
//			testMethodCountValue = testMethodCountValue + 1;
//			testMethodCount.set(testMethodCountValue);
			
			String methodName = result.getName().trim();
			
			Log logObj = new Log();
			logObj.setDetails("Test method " + result.getMethod().getMethodName() + " skipped due to Exception -----> " + Reporter.getCurrentTestResult().getThrowable().toString());
			logObj.setStatus(Status.SKIP);

			List<Test> tests = getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length-1].trim())
					.getModel()
					.getChildren();

			for(Test test: tests) {
				if(test.getName().trim().equals(methodName)) {
					test.addLog(logObj);
					break;
				}
			}
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void onAfterClass(ITestClass testclass) {
	}

	public void onFinish(ITestContext context) {
	}

	public void onFinish(ISuite suite) {
		executionFinished = true;
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		endDateTime = LocalDateTime.now();  
//		endTime = dtf.format(now);
		
		this.extent = getExtent();
		
		if(testRunStatus.equalsIgnoreCase("fail")) {
			try {
				fileOperations.updateValueToPropertyFile(constants.CONFIG_WEB_FILE_PATH, "testRunStatus", "fail");
			} catch (ConfigurationException | IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				fileOperations.updateValueToPropertyFile(constants.CONFIG_WEB_FILE_PATH, "testRunStatus", "pass");
			} catch (ConfigurationException | IOException e) {
				e.printStackTrace();
			}
		}
		this.extent.flush();
	}

	public void takeScreenShot(String filePath, String methodName, RemoteWebDriver driver) {
		try {
			TakesScreenshot d = ((TakesScreenshot) driver);
			File scrFile = d.getScreenshotAs(OutputType.FILE);
			File scrFile2 = new File(filePath + methodName + ".png");
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			FileUtils.copyFile(scrFile, scrFile2);
			System.out.println("***Placed screen shot in " + filePath + "***");
			Reporter.setEscapeHtml(false);
			Reporter.log("<a href='"+scrFile2.toString()+"'><img src ='"+scrFile2.toString()+"' width='200' height='200'></a>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExtentReports getExtent() {
		if (extent == null) {
			extent = new ExtentReports();
			String name = "Veoci"; 
			try { 
				name = InetAddress.getLocalHost().getHostName(); 
			} catch (UnknownHostException e) {
				e.printStackTrace(); 
			}
			extent.setSystemInfo("User Name", name);
			
			extent.setSystemInfo("Java Version", System.getProperty("java.version")); 
			extent.setSystemInfo("OS", System.getProperty("os.name", "generic").toLowerCase());
			extent.setSystemInfo("Environment", "Stage");
			extent.attachReporter(getHtmlReporter());
		}
		Duration diff = null;
		if (executionFinished) {
//			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");			
			diff = Duration.between(startDateTime, endDateTime);
			String hm = String.format("%d:%02d", 
                    diff.toHours(), 
                    diff.minusHours(diff.toHours()).toMinutes());
			System.out.println("====================== " + startDateTime + endDateTime + hm + " ======================");
			extent.setSystemInfo("Total Execution Time", hm);
		}
		return extent;
	}

	private ExtentSparkReporter getHtmlReporter() {
		htmlReporter = new ExtentSparkReporter(filePathex);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("Veoci Automation Report");
		htmlReporter.config().setReportName("Test Automation Report");
		return htmlReporter;
	}

	public static void logToReport(String msg) {	
		String methodName = Reporter.getCurrentTestResult().getMethod().getMethodName().trim();
		Log logObj = new Log();
		logObj.setDetails(msg);
		logObj.setStatus(Status.INFO);

		

		
		}
	}
