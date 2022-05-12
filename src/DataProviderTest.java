

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class DataProviderTest extends Base {

	@BeforeClass
	public void setUp() {
		try {
			initialization();
		} catch (IOException e) {

			e.printStackTrace();
		}
		driver.get("https://mytestingthoughts.com/Sample/home.html");

	}

	@Test(dataProvider = "test2")
	public void testForm(String firstName, String lastName, String gender, String hobbies, String department,
			String username, String password, String confirmPassword, String email, String contact,
			String additionalInfo) {

		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(lastName);

		driver.findElement(By.xpath("//input[@id='inlineRadio" + gender + "']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;

		Select select1 = new Select(driver.findElement(By.xpath("//select[@id='exampleFormControlSelect2']")));
		select1.selectByVisibleText(hobbies);

		Select select2 = new Select(driver.findElement(By.xpath("//select[@name='department']")));
		select2.selectByVisibleText(department);

		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@placeholder='Confirm Password']")).sendKeys(confirmPassword);
		driver.findElement(By.xpath("//input[@placeholder='E-Mail Address']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@placeholder='(639)']")).sendKeys(contact);
		driver.findElement(By.xpath("//textarea[@id='exampleFormControlTextarea1']")).sendKeys(additionalInfo);
		driver.findElement(By.xpath("//span[@class='glyphicon glyphicon-send']")).click();

		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		int rowSize = driver.findElements(By.xpath("//tbody[1]/tr")).size();
		int colSize = driver.findElements(By.xpath("//tbody[1]/tr[1]/td")).size();

		SoftAssert softAsset = new SoftAssert();

		softAsset.assertEquals(driver.findElement(By.xpath("//tbody[1]/tr[" + rowSize + "]/td[1]")).getText(),
				firstName);
		softAsset.assertEquals(driver.findElement(By.xpath("//tbody[1]/tr[" + rowSize + "]/td[2]")).getText(),
				lastName);
		softAsset.assertEquals(driver.findElement(By.xpath("//tbody[1]/tr[" + rowSize + "]/td[3]")).getText(),
				department);
		softAsset.assertEquals(driver.findElement(By.xpath("//tbody[1]/tr[" + rowSize + "]/td[4]")).getText(), email);
		softAsset.assertEquals(driver.findElement(By.xpath("//tbody[1]/tr[" + rowSize + "]/td[5]")).getText(), contact);

		softAsset.assertAll();

	}

	@AfterClass
	public void tearDown() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.close();

	}

	@DataProvider(name = "test1")
	public Object[][] getData() throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\thndr\\Desktop\\Selenium Revision\\ExportExcel.xlsx");
		XSSFSheet sheet = workbook.getSheet("RegFormData");

		int rowSize = sheet.getLastRowNum();
		int colSize = sheet.getRow(0).getLastCellNum();
		Object[][] tempData = new Object[rowSize][colSize];
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				Cell cell = sheet.getRow(i + 1).getCell(j);
				tempData[i][j] = cell.getStringCellValue();

			}
			System.out.println();
		}
		workbook.close();
		return tempData;

	}

	@DataProvider(name = "test2")
	public Iterator<Object[]> getData2() throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\thndr\\Desktop\\Selenium Revision\\ExportExcel.xlsx");
		XSSFSheet sheet = workbook.getSheet("RegFormData");

		int rowSize = sheet.getLastRowNum();
		int colSize = sheet.getRow(0).getLastCellNum();
		ArrayList<Object[]> list = new ArrayList<>();
		Object[] o = new Object[colSize];

		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				Cell cell = sheet.getRow(i + 1).getCell(j);

				o[j] = cell.getStringCellValue();

			}
			list.add(o.clone());

		}
		workbook.close();
		return list.iterator();

	}

}