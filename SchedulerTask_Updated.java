/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteWorkFlow;

/**
 *
 * @author Vikram
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import CompleteWorkFlow.AttributeMapper;

public class SchedulerTask {

//	Date now; // to display current time
	AttributeMapper MQuery; // Object created for class Multiple Query

	String p_sourceSystemName;

	public SchedulerTask(String sourceSystemName) {
		p_sourceSystemName = sourceSystemName;
	}
	// Add your task here

	public void run() throws InterruptedException, IOException {

		MQuery = new AttributeMapper(); // Created a object of Multiple Query class so

		// TO DO: Convert following time to mili second level
		// write in the file current time and read current time
		File fh = new File("C:\\tests\\MyLog.txt");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
		String lastExeTime = sdf.format(fh.lastModified());
		System.out.println("LastModified " + lastExeTime);

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
		String currentExec = formatter.format(date);
		System.out.println("Date Format with yyyy-MM-dd HH:mm:ss.ms : " + currentExec);

		FileWriter fileWriter = new FileWriter("C:\\tests\\MyLog.txt");
		fileWriter.write(currentExec);
		fileWriter.close();

//		File file = new File("C:\\tests\\MyLog.txt");
//		Scanner sc = new Scanner(file);
//
//		while (sc.hasNextLine())
//			System.out.println(sc.nextLine());
//		
//		
//		File fh = new File("C:\\tests\\MyLog.txt");
//
//		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
//		//String lastExeTime = sdf.format(fh.lastModified());
//		String lastExeTime = sc.next();
//		System.out.println(lastExeTime);
//		Date date = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
//		String currentExec = formatter.format(date);
//		System.out.println(currentExec);
//		FileWriter fileWriter = new FileWriter("C:\\tests\\MyLog.txt");
//		fileWriter.write(lastExeTime);
//		fileWriter.close();

		// TO DO:
		// create three threads of QueryAccounts
		// one for each Account, Contact and Address
		// wait for each thread to complete
		// combine three lists into one set
//		QueryAccounts acc_QueryAccount = new QueryAccounts(p_sourceSystemName, lastExeTime, "Account");
//		QueryAccounts con_QueryAccount = new QueryAccounts(p_sourceSystemName, lastExeTime, "Contact");
//		QueryAccounts add_QueryAccount = new QueryAccounts(p_sourceSystemName, lastExeTime, "Address");

		QueryAccounts acc_QueryAccount = new QueryAccounts(p_sourceSystemName, lastExeTime, "Account");
		QueryAccounts con_QueryAccount = new QueryAccounts(p_sourceSystemName, lastExeTime, "Contact");
		QueryAccounts add_QueryAccount = new QueryAccounts(p_sourceSystemName, lastExeTime, "Address");

		acc_QueryAccount.run();
		con_QueryAccount.run();
		add_QueryAccount.run();

		acc_QueryAccount.join();
		con_QueryAccount.join();
		add_QueryAccount.join();

		ArrayList<String> IDsFromAccount = acc_QueryAccount.GetAccountResultList();
		ArrayList<String> IDsFromContact = con_QueryAccount.GetAccountResultList();
		ArrayList<String> IDsFromAddress = add_QueryAccount.GetAccountResultList();

		// System.out.println("addressList : " + addressResultList);
		ArrayList<String> finalResultList = new ArrayList<String>();
		finalResultList.addAll(IDsFromAccount);
		finalResultList.addAll(IDsFromContact);
		finalResultList.addAll(IDsFromAddress);

		// System.out.println("FinalList : " + FinalResultList);
		HashSet<String> set = new HashSet<String>(finalResultList);

		System.out.println(set);

		AttributeMapper obj = new AttributeMapper();
		for (int i = 0; i < set.size(); i++) {
			// Set<String> pass_set = set.parallelStream().collect(Collectors.toSet());

			obj.PrepareCanonicalJSON(p_sourceSystemName, (String) set.toArray()[i]);
		}

//		now = new Date(); // initialize date
//		System.out.println("Time is :" + now);// Display current time

	}
}
