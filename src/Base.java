

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Base {

	public static WebDriver driver;
	public static Properties prop;

	public static void initialization() throws IOException{
			
		FileInputStream fileInput = new FileInputStream(new File("C:\\Users\\thndr\\eclipse-workspace\\SeleniumBackOnTrack\\src\\config.properties"));
		prop = new Properties();
		
		prop.load(fileInput);
		
		System.out.println(prop.getProperty("system_property_chrome"));
		System.out.println(prop.getProperty("chromedriver_path"));

		System.setProperty(prop.getProperty("system_property_chrome"), prop.getProperty("chromedriver_path"));
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		//driver.get(prop.getProperty("url"));

	}

	public static void takeScreenshot(String methodName) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File file = screenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file, new File(
					"C:\\Users\\thndr\\eclipse-workspace\\SeleniumBackOnTrack\\src\\Screenshot\\"+methodName+"_"+System.currentTimeMillis()+".png"));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
