/**
 * 
 */
package io.schoolmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author LAP-9
 *
 */
public class SchoolManagementSystem {

	/**
	 * practice Maven
	 * 
	 * @param args
	 */
	public static final String FILE_PATH = "data/StudentDetails.json";

	public static void main(String[] args) {

		// create array for the menus
		String[] menu = { "1-Admission" };
		String[] admissionSubMenu = { "1-Register Student", "2-Search For Student" };

		// display the main menu
		System.out.println("Please select from the menu:");
		for (String i : menu) {
			System.out.println(i);
		}
		// user select from the main menu
		System.out.print("Here: ");
		Scanner menuInput = new Scanner(System.in);
		int input = menuInput.nextInt();
		System.out.println();

		// admission sub menu
		if (input == 1) {

			// display the admission sub menu
			System.out.println("Admission submenu: ");
			for (String i : admissionSubMenu) {
				System.out.println(i);
			}

			// user select from the sum menu
			System.out.print("Please select from the admission submenu here: ");
			Scanner subMenuSc = new Scanner(System.in);
			int submenuinput = subMenuSc.nextInt();

			// Hash map to store the student details from an array
			HashMap<String, String> storeStudentDetails = new HashMap<String, String>();
			// String [] StudentDetails = new String [3];

			// ask the user to fill the details
			if (submenuinput == 1) {
				System.out.println();
				System.out.println("Fill the below Details:");
				// Request for the student name
				System.out.print("1-Write the student name: ");
				Scanner nameInput = new Scanner(System.in);
				String studentName = nameInput.nextLine();

				// Request for the student role number
				System.out.print("2-Write the role number of the student: ");
				Scanner roleInput = new Scanner(System.in);
				String studentRoleNumber = roleInput.nextLine();

				// Request for the student email address
				System.out.print("3-Write the email address of the student: ");
				Scanner emailInput = new Scanner(System.in);
				String studentEmail = emailInput.nextLine();

				// Show the student details
				System.out.println("------------------------------------");
				System.out.println("Student Details:");
				System.out.println("Name: " + studentName);
				System.out.println("Role number: " + studentRoleNumber);
				System.out.println("Email address: " + studentEmail);
				System.out.println("------------------------------------");

				// store the details in hashmap
				storeStudentDetails.put("id", studentRoleNumber);
				storeStudentDetails.put("name", studentName);
				storeStudentDetails.put("email", studentEmail);

				// write the record in a file
				try (FileWriter filrWriter = new FileWriter(FILE_PATH, true)) {
					Gson gson = new GsonBuilder().create();
					gson.toJson(storeStudentDetails, filrWriter);
					System.out.println("Added Successfuly!");
					filrWriter.write("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // sub1
			else if (submenuinput == 2) {
				//ask user to enter the role number of the student
				System.out.println();
				System.out.print("Enter the role number of the student: ");
				Scanner roleNumberSc = new Scanner(System.in);
				String studentRoleNumberFile = roleNumberSc.nextLine();
				System.out.println();
				
				// Read json file
				File dataFile = new File(FILE_PATH);
				try {
					Scanner fileScanner = new Scanner(dataFile);
					while (fileScanner.hasNextLine()) {
						//read from json file, convert json to hash map
						Gson gson = new GsonBuilder().create();
						Type type = new TypeToken<Map<String, String>>() {
						}.getType();
						Map<String, String> myMap = gson.fromJson(fileScanner.nextLine(), type);
						HashMap<String, String> checkHashMap = new HashMap<String, String>();
						checkHashMap.put("id", studentRoleNumberFile);
						
						//check for particular student
						for (String i : myMap.keySet()) {
							if (checkHashMap.containsKey(i)) {
								if (checkHashMap.containsValue(myMap.get(i))) {
									for (String aString : myMap.keySet()) {
										System.out.println(aString + " : " + myMap.get(aString));
									}
								}
							}
						}// main for loop ends
					}//while ends
					
					fileScanner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // sub2
			else {
				System.out.println("Wrong Selection !!");
			}

		} // input1
		else {
			System.out.println("Wrong Selection !!");
		}

	}// main end

}// class end
