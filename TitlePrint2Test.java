package com.qa.Scripts;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.Pages.OrangeHRMPage;




public class TitlePrint2Test {
	
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
	public void PrintTitle() {
		String strTitle;
		strTitle=OrangeOR.checkTitle();
		Reporter.log("Page title is displayed as :'" + strTitle +"'", true);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
