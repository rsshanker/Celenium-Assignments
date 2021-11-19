package com.qa.Scripts;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.Pages.OrangeHRMPage;

public class LoginAssignment4 {
	WebDriver driver;
	OrangeHRMPage OrangeOR;
	public static FileInputStream fileLoc;
	public static Properties prop;
		
	@BeforeClass
	public void OpenUrlWithBrowser() {
		
		
			try {
				fileLoc = new FileInputStream(System.getProperty("user.dir")+"\\configuration\\config.properties");
				prop = new Properties();
				prop.load(fileLoc);
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		OrangeOR=new OrangeHRMPage(driver);
		driver.manage().deleteAllCookies();
		
		driver.get(prop.getProperty("url"));
		//driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.manage().window().maximize();	
	}
	@Test
	public void LogInToApp() throws InterruptedException {
		try {
			fileLoc = new FileInputStream("C:\\Users\\velurih\\eclipse-workspace\\SeleniumAssignments\\src\\test\\java\\com\\qa\\utilities\\Testdata.properties");
			prop = new Properties();
			prop.load(fileLoc);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}