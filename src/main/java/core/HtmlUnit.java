package core;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;

public class HtmlUnit {
	
	static Properties p = new Properties();
	static Writer report;
	static String ls = System.getProperty("line.separator");
	static WebClient driver;
	
	public static boolean isElementPresent(HtmlPage page, String by) {
		return page.getElementsById(by).size() == 1;
	}
	
	public static void setValue(HtmlPage page, String by, String value) {
		if (isElementPresent(page, by) && page.getElementById(by).isDisplayed())
		page.getElementById(by).setTextContent(value);
	}
	
	public static String getValue(HtmlPage page, String by) {
		return isElementPresent(page, by) && page.getElementById(by).isDisplayed() ? page.getElementById(by).getTextContent() : "null";
	}

	public static void main(String[] args) throws IOException {
		
		Logger.getLogger("").setLevel(Level.OFF);
		p.load(new FileInputStream("input.properties"));
		report = new FileWriter("./report_01.csv",false);
		String driverPath = "";
		if (System.getProperty("os.name").toUpperCase().contains("MAC") || System.getProperty("os.name").toUpperCase().contains("LINUX"))
	    	driverPath = "/usr/local/bin/geckodriver.sh";
	    else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) 
	    	driverPath = "c:\\windows\\geckodriver.exe";
	    else throw new IllegalArgumentException("Unknown OS");
		System.setProperty("webdriver.gecko.driver", driverPath); 
		driver = new WebClient();
		driver.setCssErrorHandler(new SilentCssErrorHandler()); 
		driver.setJavaScriptErrorListener(new SilentJavaScriptErrorListener()); 
		
		String browserName = "HtmlUnit";
		
		HtmlPage index_page = driver.getPage(p.getProperty("url"));
		HtmlForm form = index_page.getFormByName(p.getProperty("form"));
		
		System.out.println("#,Browser,Page,Field,isPresent,Value");
		
		report.write("#,Browser,Page,Field,isPresent,Value"); 
		report.write(ls);
		
		String[] idElement = p.getProperty("id").split(",");
		String[] valueElement = p.getProperty("value").split(",");
		String[] nameElement = p.getProperty("name").split(",");
		
		for (int i = 0; i < idElement.length; i++) {
			report.write((i + 1) + "," + browserName + ",index.php," + nameElement[i] + "," + isElementPresent(index_page, idElement[i]) + "," + valueElement[i] + "\n");
			System.out.print((i + 1) + "," + browserName + ",index.php," + nameElement[i] + "," + isElementPresent(index_page, idElement[i]) + "," + valueElement[i] + "\n");
			setValue(index_page, idElement[i], valueElement[i]);
		}
		
		HtmlSubmitInput button = form.getInputByValue(p.getProperty("submit_id"));
		HtmlPage confirmation_page = button.click();

		
		for (int i = 0; i < idElement.length; i++) {
			report.write((i + 1 + idElement.length) + "," + browserName + ",confirmation.php," + nameElement[i] + "," + isElementPresent(confirmation_page, idElement[i]) + 
					"," + getValue(confirmation_page, idElement[i]) + "\n");
			System.out.print((i + 1 + idElement.length) + "," + browserName + ",confirmation.php," + nameElement[i] + "," + isElementPresent(confirmation_page, idElement[i]) + 
					"," + getValue(confirmation_page, idElement[i]) + "\n");
		}
		report.flush(); report.close(); driver.close();
	}

}

