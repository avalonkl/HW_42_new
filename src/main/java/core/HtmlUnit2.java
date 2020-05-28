package core;

import java.io.*;
import java.util.Properties;
import java.util.logging.*;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;

public class HtmlUnit2 {
	static Properties p = new Properties();
	static Writer report;
	static String ls = System.getProperty("line.separator");
	static WebClient driver;

	public static boolean isElementPresentHtmlUnit(HtmlPage page, String by) {
		if (page.getElementsById(by).size() > 0)
			return true;
		else
			return false;
	}

	public static void setValueHtmlUnit(HtmlPage page, String by, String value) {
		if (isElementPresentHtmlUnit(page, by))
			page.getElementById(by).setTextContent(value);
	}

	public static String getValueHtmlUnit(HtmlPage page, String by) {
		if (isElementPresentHtmlUnit(page, by))
			return page.getElementById(by).getTextContent().trim();
		else
			return "null";
	}

	public static void main(String[] args) throws Exception {
		Logger.getLogger("").setLevel(Level.OFF);
		p.load(new FileInputStream("./input2.properties"));
		report = new FileWriter("./report_01.csv", false);
		driver = new WebClient();
		driver.setCssErrorHandler(new SilentCssErrorHandler());
		driver.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
		HtmlPage index_page = driver.getPage(p.getProperty("url"));
		System.out.println(getValueHtmlUnit(index_page, "id_quotes").toUpperCase());
// HEADER
		System.out.println("#,Browser,Page,Field,isPresent,Value");
		report.write("#,Browser,Page,Field,isPresent,Value");
		report.write(ls);
		HtmlForm form = index_page.getFormByName(p.getProperty("form"));
// 01 :: First Name
		report.write(
				"01,HtmlUnit,index.php,First Name," + isElementPresentHtmlUnit(index_page, p.getProperty("fname_id"))
						+ "," + p.getProperty("fname_value") + "\n");
		System.out.print(
				"01,HtmlUnit,index.php,First Name," + isElementPresentHtmlUnit(index_page, p.getProperty("fname_id"))
						+ "," + p.getProperty("fname_value") + "\n");
		setValueHtmlUnit(index_page, p.getProperty("fname_id"), p.getProperty("fname_value"));
// 02 :: Last Name
		report.write(
				"02,HtmlUnit,index.php,Last Name," + isElementPresentHtmlUnit(index_page, p.getProperty("lname_id"))
						+ "," + p.getProperty("lname_value") + "\n");
		System.out.print(
				"02,HtmlUnit,index.php,Last Name," + isElementPresentHtmlUnit(index_page, p.getProperty("lname_id"))
						+ "," + p.getProperty("lname_value") + "\n");
		setValueHtmlUnit(index_page, p.getProperty("lname_id"), p.getProperty("lname_value"));
// 03 :: Email
		report.write("03,HtmlUnit,index.php,Email," + isElementPresentHtmlUnit(index_page, p.getProperty("email_id"))
				+ "," + p.getProperty("email_value") + "\n");
		System.out
				.print("03,HtmlUnit,index.php,Email," + isElementPresentHtmlUnit(index_page, p.getProperty("email_id"))
						+ "," + p.getProperty("email_value") + "\n");
		setValueHtmlUnit(index_page, p.getProperty("email_id"), p.getProperty("email_value"));
// 04 :: Phone
		report.write("04,HtmlUnit,index.php,Phone," + isElementPresentHtmlUnit(index_page, p.getProperty("phone_id"))
				+ "," + p.getProperty("phone_value") + "\n");
		System.out
				.print("04,HtmlUnit,index.php,Phone," + isElementPresentHtmlUnit(index_page, p.getProperty("phone_id"))
						+ "," + p.getProperty("phone_value") + "\n");
		setValueHtmlUnit(index_page, p.getProperty("phone_id"), p.getProperty("phone_value"));
// SUBMIT
		HtmlSubmitInput button = form.getInputByValue("Submit");
		HtmlPage confirmation_page = button.click();
		Thread.sleep(1000);
		System.out.println(getValueHtmlUnit(confirmation_page, "id_quotes").toUpperCase());
// 05 :: First Name
		report.write("05,HtmlUnit,confirmation.php,First Name,"
				+ isElementPresentHtmlUnit(index_page, p.getProperty("fname_id")) + ","
				+ getValueHtmlUnit(confirmation_page, p.getProperty("fname_id")) + "\n");
		System.out.print("05,HtmlUnit,confirmation.php,First Name,"
				+ isElementPresentHtmlUnit(index_page, p.getProperty("fname_id")) + ","
				+ getValueHtmlUnit(confirmation_page, p.getProperty("fname_id")) + "\n");
// 06 :: Last Name
		report.write("06,HtmlUnit,confirmation.php,Last Name,"
				+ isElementPresentHtmlUnit(index_page, p.getProperty("lname_id")) + ","
				+ getValueHtmlUnit(confirmation_page, p.getProperty("lname_id")) + "\n");
		System.out.print("06,HtmlUnit,confirmation.php,Last Name,"
				+ isElementPresentHtmlUnit(index_page, p.getProperty("lname_id")) + ","
				+ getValueHtmlUnit(confirmation_page, p.getProperty("lname_id")) + "\n");
// 07 :: Email
		report.write(
				"07,HtmlUnit,confirmation.php,Email," + isElementPresentHtmlUnit(index_page, p.getProperty("email_id"))
						+ "," + getValueHtmlUnit(confirmation_page, p.getProperty("email_id")) + "\n");
		System.out.print(
				"07,HtmlUnit,confirmation.php,Email," + isElementPresentHtmlUnit(index_page, p.getProperty("email_id"))
						+ ", " + getValueHtmlUnit(confirmation_page, p.getProperty("email_id")) + "\n");
// 08 :: Phone
		report.write(
				"08,HtmlUnit,confirmation.php,Phone," + isElementPresentHtmlUnit(index_page, p.getProperty("phone_id"))
						+ "," + getValueHtmlUnit(confirmation_page, p.getProperty("phone_id")) + "\n");
		System.out.print(
				"08,HtmlUnit,confirmation.php,Phone," + isElementPresentHtmlUnit(index_page, p.getProperty("phone_id"))
						+ "," + getValueHtmlUnit(confirmation_page, p.getProperty("phone_id")) + "\n");
		report.flush();
		report.close();
		driver.close();
	}
}