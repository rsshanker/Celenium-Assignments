package com.qa.Scripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.Pages.OrangeHRMPage;

public class SampleTest13 extends BasicTest01 {
	WebDriver driver;
	OrangeHRMPage OrangeOR;
	public static FileInputStream fileLoc;
	public static Properties prop;
		
	@BeforeTest
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
	
	@BeforeClass
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

	// test 13:
	@Test
	public void validateEmployeeSearch() throws IOException, InterruptedException {
		String strURL;
		String LName;
		String FName;
		OrangeOR.ClickOnPIMLink();
		strURL=OrangeOR.getURL();
		Assert.assertTrue(strURL.contains("pim"));
		
		OrangeOR.EnterEmployeeName("Linda Anderson");
		OrangeOR.ClickOnSearch();
		
		FName=OrangeOR.getFirstNameText();
		LName=OrangeOR.getLastNameText();
		boolean fNameresult = FName.contains("Linda");
		boolean lNameresult = LName.contains("Anderson");
		if(fNameresult & lNameresult)  {
			Thread.sleep(4000);
            captureScreenshot(driver,"Search Success");
            Assert.assertTrue(true); 
            Reporter.log("Search operation sucessful, Found the First and last name in serach", true);
		}
		else {
            captureScreenshot(driver,"Search Failed");
            Assert.assertTrue(false); 
            Reporter.log("In serach user not able to found first and last name", true);
		}
		
	}
	
	
	/*@AfterTest
	public void tearDown() {
		driver.quit();
	}*/
}
