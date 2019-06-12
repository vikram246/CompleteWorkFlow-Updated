/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteWorkFlow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//import demo.testing;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 * @author Vikram
 */
public class QueryAccounts extends Thread {

	String p_sourceSystemName;
	String p_lastExeTime;
	String p_entityName;
	ArrayList<String> p_accountResultList;

	ArrayList<String> GetAccountResultList() {
		return p_accountResultList;
	}

	QueryAccounts(String sourceSystemName, String lastExeTime, String entityName) {
		p_sourceSystemName = sourceSystemName;
		p_lastExeTime = lastExeTime;
		p_entityName = entityName;
	}

	@Override
	public void run() {

		///////////////////////////////////////////////////////////////

		try {

			InputStream input = new FileInputStream("config.properties");

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);
			String url = prop.getProperty("db.url");
			String user = prop.getProperty("db.user");
			String passwd = prop.getProperty("db.password");
			String driver = prop.getProperty("db.driverclass");

			// TO DO:
			// Get the query from XML file
			String acc_query = prop.getProperty("acc_query");
			String con_query = prop.getProperty("con_query");
			String add_query = prop.getProperty("add_query");

			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, passwd);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;

			org.w3c.dom.Document dom = null;
			Object obj = new Object();
			db = dbf.newDocumentBuilder();
			// dom = db.parse(obj.getClass().getResourceAsStream("config.xml"));
			dom = db.parse(new FileInputStream("config.xml"));
			Element docEle = dom.getDocumentElement();
			NodeList sourceSystems_Account = docEle.getElementsByTagName("Account");

			int i;
			for (i = 0; i < sourceSystems_Account.getLength(); i++) {
				Element sourceSystem_acc = (Element) sourceSystems_Account.item(i);
				// Account_Queries
				String Acc_Query = sourceSystem_acc.getAttribute("SQL_Account");
				String Acc_TimeStamp = sourceSystem_acc.getAttribute("TimeStamp_Account");

				String LastModified = p_lastExeTime;
				Acc_TimeStamp = Acc_TimeStamp.replace("%1", LastModified);
				// System.out.println(Acc_Query + Acc_TimeStamp);

				String[] arrEntity = { " account ", " contact ", " address " };
				for (String EntityValue : arrEntity) {
					//System.out.println(Acc_Query + EntityValue + Acc_TimeStamp);

					Statement st1 = con.createStatement();
					// TO DO
					// Replace the hard coded where cluase from the XML config
					// and replace %1 variables with time value

					// ResultSet rs1 = st1.executeQuery(Acc_Query + EntityValue + Acc_TimeStamp);
					ResultSet rs1 = st1.executeQuery(
							"SELECT accountid FROM account  WHERE updated BETWEEN '2019-06-12 23:33:12.3312' and current_timestamp");

					ResultSetMetaData rsmd = rs1.getMetaData();
					int columnCount = rsmd.getColumnCount();
					p_accountResultList = new ArrayList<String>(columnCount);

					while (rs1.next()) {
						int j = 1;
						while (j <= columnCount) {
							p_accountResultList.add(rs1.getString(j++));
						}
					}

					// rs2.close();

					rs1.close();
					// con.close();
					// return set;
				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		// return null;

	}
}
