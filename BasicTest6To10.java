package com.qa.Scripts;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.Pages.OrangeHRMPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicTest6To10 {
	
	public static WebDriver driver;
	OrangeHRMPage OrangeOR;
	public static FileInputStream fileLoc;
	public static Properties prop;
	//
	//
	//test 9 :  Write a method (avoid using Test annotation) to minimize the window.
	public void MinimizeBrowser() {
		Point p = driver.manage().window().getPosition();
		Dimension d = driver.manage().window().getSize();
		driver.manage().window().setPosition(new Point((d.getHeight()-p.getX()), (d.getWidth()-p.getY())));
	} 
	
	
	//test 6 : Launch a browser in @Beforesuite annotation and open url . url - https://opensource-demo.orangehrmlive.com/
	@Parameters({"browser"})
	@BeforeSuite
	public void setUp(String browser) throws InterruptedException {
		
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
		// Used constructor for Minimize the browser without using annotation test 9
		BasicTest6To10 NewConst=new BasicTest6To10();
		NewConst.MinimizeBrowser();
		Thread.sleep(3000);
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
	
	
	//test 8 : Write Test method and page title for different menus- in order(Admin, PIM, Leave Dashboard, Directory and Maintenance)
	@Test(priority=2)
	public void ClickOnTabsAndValidateTitle() throws InterruptedException {
		String strTitle;
		String strURL;
		
		
		OrangeOR.ClickOnAdminLink();
		Thread.sleep(3000);
		strTitle=OrangeOR.checkTitle();
		Reporter.log("Page title displyed as :'" + strTitle +"'", true);
		strURL=OrangeOR.getURL();
		Assert.assertTrue(strURL.contains("admin"));
		
		
		OrangeOR.ClickOnPIMLink();
		Thread.sleep(3000);
		strTitle=OrangeOR.checkTitle();
		strURL=OrangeOR.getURL();
		Reporter.log("Page title displyed as :'" + strTitle +"'", true);
		Assert.assertTrue(strURL.contains("pim"));
		
		OrangeOR.ClickOnDashBoardLink();
		Thread.sleep(3000);
		strTitle=OrangeOR.checkTitle();
		strURL=OrangeOR.getURL();
		Reporter.log("Page title displyed as :'" + strTitle +"'", true);
		Assert.assertTrue(strURL.contains("dashboard"));
		
		
		
		OrangeOR.ClickOnDirectoryLink();
		Thread.sleep(3000);
		strTitle=OrangeOR.checkTitle();
		strURL=OrangeOR.getURL();
		Reporter.log("Page title displyed as :'" + strTitle +"'", true);
		Assert.assertTrue(strURL.contains("directory"));
		
		OrangeOR.ClickOnMaintenanceLink();
		Thread.sleep(3000);
		strTitle=OrangeOR.checkTitle();
		strURL=OrangeOR.getURL();
		Reporter.log("Page title displyed as :'" + strTitle +"'", true);
		Assert.assertTrue(strURL.contains("maintenance"));
	}
	
	/// Test 10 : Read Dashboard heading using [driver.findelement(by.xpath()).gettext()].
	@Test(priority=3)
	public void ReadDashBoardHeader() throws InterruptedException {
		/// test 10
		String strText;
		OrangeOR.ClickOnDashBoardLink();
		Thread.sleep(3000);
		strText=driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/h1")).getText();
		Reporter.log("Header of the DashBoard page get displayed as :'" + strText +"'", true);
	}
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
	
}
