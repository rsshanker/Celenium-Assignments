package com.qa.Scripts;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qa.Pages.OrangeHRMPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ListenerTest11to12 extends BasicTest01 {
	public static WebDriver driver;
	OrangeHRMPage OrangeOR;
	public static FileInputStream fileLoc;
	public static Properties prop;

	
	//test 6 : Launch a browser in @Beforesuite annotation and open url . url - https://opensource-demo.orangehrmlive.com/
	@Parameters({"browser"})
	@BeforeSuite
	public void setUp(String browser) {
		
		try {
			fileLoc = new FileInputStream(System.getProperty("user.dir")+"\\configuration\\config.properties");
			prop = new Properties();
			prop.load(fileLoc);
		}catch(Exception e) {
			e.printStackTrace();
		}
				
		
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if(browser.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} 
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
		
	}
	
	// test 7 : Login to application again in @Test method, set priority 1 of the same method After logging
	@Test(priority=1)
	public void LogInToApp() throws InterruptedException {
		try {
			fileLoc = new FileInputStream("C:\\Users\\velurih\\eclipse-workspace\\SeleniumAssignments\\src\\test\\java\\com\\qa\\utilities\\Testdata.properties");
			prop = new Properties();
			prop.load(fileLoc);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		OrangeOR=new OrangeHRMPage(driver);
		
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		
		OrangeOR.EnterUserName(username);
		OrangeOR.EnterPassword(password);
		OrangeOR.ClickOnLogInButton();
		Thread.sleep(3000);
		String strURL;
		strURL=OrangeOR.getURL();
		Assert.assertTrue(strURL.contains("dashboard"));
		Reporter.log("Page logged in successful, Dashboard opened", true);
	}
	
		
	@Test(priority=2 , retryAnalyzer=com.qa.listener.RetryAnalyzer.class)
    public void ListenerFunction() throws InterruptedException, IOException   {
		String HeaderText;
		HeaderText=OrangeOR.getDashBoardHeaderText();
		boolean result = HeaderText.equalsIgnoreCase("Dashboard");
		if(result) {
			Thread.sleep(4000);
            captureScreenshot(driver,"DashBoard navigation Passed");
            Assert.assertTrue(true); 
            Reporter.log("Dashboard Menu contains user", true);
		}
		else {
			int i = 1; 
            captureScreenshot(driver,"DashBoard page validation Failed Interation = "+ i);
            Assert.assertTrue(false); 
            Reporter.log("Dashboard Menu not contained in page", true);
            i++;
		}
			
		
	}
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
   
}
 