package CompleteWorkFlow;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.Timer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SchedulerMain {
	public static void main(String args[]) throws InterruptedException, IOException {
		String Source_System;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the SourceSystem : ");
		Source_System = sc.next();
		run(Source_System);

		return;
	}

	public static void run(String Source_System) throws InterruptedException, IOException {

		InputStream input = new FileInputStream("config.properties");
		Properties prop = new Properties();

		// load a properties file
		prop.load(input);

		// Object timer_millisec = prop.get("scheduler_Timer");
//		Object loop = prop.getProperty("scheduler_Loop");

		// Timer time = new Timer(); // Instantiate Timer Object

		for (;;) {
			System.out.println("Execution in Main Thread....");
			Thread.sleep((1000));
			// TO DO:
			// replace the hard coded source system with input argument to main function
			SchedulerTask st = new SchedulerTask(Source_System); // Instantiate ScheduledTask class
			st.run();
//			if (i == 5) {
//				System.out.println("Application Terminates");
//				System.exit(0);

		}
	}
}
