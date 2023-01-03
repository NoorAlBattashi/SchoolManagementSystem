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

import javax.swing.plaf.synth.SynthFormattedTextFieldUI;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author LAP-9
 *
 */
public class SchoolManagementSystem {

	public static final String FILE_PATH = "data/StudentDetails.json";

	/**
	 * 1-School Management App should be able to register the students. 
	 * 2-School Management App should be able to search the registered students. 
	 * 3-School Management App should be able to retain student registration information.
	 * Such that, when the application restarts, we can still search for the existing registered students. 
	 * 4-School Management App should be able to update student information. 
	 * 5-School Management App should be able to remove any registered student. 
	 * 6-School Management App should be able to retain student registration information on application reload.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create array for the menus
		String[] menu = { "1-Admission" };
		String[] admissionSubMenu = { "1-Register Student", "2-Search For Student", "3-Update Student information",
				"4-Delete Student" };

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
			HashMap<String, String[]> storeStudentDetails = new HashMap<String, String[]>();
			String[] StudentDetails = new String[2];

			// ask the user to fill the details
			if (submenuinput == 1) {
				System.out.println();
				System.out.println("Fill the below Details: ");

				// Request for the student role number
				System.out.print("1-Write the role number of the student: ");
				Scanner roleInput = new Scanner(System.in);
				String studentRoleNumber = roleInput.nextLine();

				// Request for the student name
				System.out.print("2-Write the student name: ");
				Scanner nameInput = new Scanner(System.in);
				String studentName = nameInput.nextLine();

				// Request for the student email address
				System.out.print("3-Write the email address of the student: ");
				Scanner emailInput = new Scanner(System.in);
				String studentEmail = emailInput.nextLine();

				// Show the student details
				studentDetails(studentRoleNumber, studentName, studentEmail);

				// store the details in hashmap
				StudentDetails[0] = studentName;
				StudentDetails[1] = studentEmail;
				storeStudentDetails.put(studentRoleNumber, StudentDetails);

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
				// ask user to enter the role number of the student
				String studentRoleNumberFile = StudentRoleNumber();
				// Read json file
				File dataFile = new File(FILE_PATH);
				try {
					Scanner fileScanner = new Scanner(dataFile);
					while (fileScanner.hasNextLine()) {
						// read from json file, convert json to hash map
						Gson gson = new GsonBuilder().create();
						Type type = new TypeToken<Map<String, String[]>>() {
						}.getType();
						Map<String, String[]> myMap = gson.fromJson(fileScanner.nextLine(), type);
						HashMap<String, String> checkHashMap = new HashMap<String, String>();
						checkHashMap.put(studentRoleNumberFile, null);
						// check for particular student
						// i reads the key only
						for (String i : myMap.keySet()) {
							// check if the key of mymap = the key of checkhashmsp
							if (checkHashMap.containsKey(i)) {
								// Show the student details
								studentDetails(i, myMap.get(i)[0], myMap.get(i)[1]);
							}

						} // main for loop ends
					} // while ends

					fileScanner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // sub2
			else if (submenuinput == 3) {
				// ask user to enter the role number of the student
				String studentRoleNumberFile = StudentRoleNumber();

				// Read json file
				File dataFile = new File(FILE_PATH);
				try {
					Scanner fileScanner = new Scanner(dataFile);
					HashMap<String, String[]> storeFileData = new HashMap<String, String[]>();
					while (fileScanner.hasNextLine()) {
						// read from json file, convert json to hash map
						Gson gson = new GsonBuilder().create();
						Type type = new TypeToken<Map<String, String[]>>() {
						}.getType();
						Map<String, String[]> myMap = gson.fromJson(fileScanner.nextLine(), type);

						for (String i : myMap.keySet()) {
							// store the data in hashmap
							storeFileData.put(i, myMap.get(i));

						} // main for loop ends
					} // while ends

					// display the previous data
					HashMap<String, String> storeRoleNumberHashMap = new HashMap<String, String>();
					storeRoleNumberHashMap.put(studentRoleNumberFile, null);
					for (String a : storeFileData.keySet()) {

						if (storeRoleNumberHashMap.containsKey(a)) {
							// Show the student details
							studentDetails(a, storeFileData.get(a)[0], storeFileData.get(a)[1]);

							System.out.println("What do you want to change?: ");
							System.out.println("1-The Name");
							System.out.println("2-The Emal");
							System.out.print("Insert here: ");
							Scanner selectOptionSc = new Scanner(System.in);
							int option = selectOptionSc.nextInt();
							if (option == 1) {
								// change student name
								System.out.println();
								System.out.print("Write the student name: ");
								Scanner nameChange = new Scanner(System.in);
								String studentNameChange = nameChange.nextLine();
								String[] addDetailStrings = { studentNameChange, storeFileData.get(a)[1] };
								storeFileData.put(studentRoleNumberFile, addDetailStrings);
							} else if (option == 2) {
								// change the student email address
								System.out.println();
								System.out.print("Write the email address of the student: ");
								Scanner emailChange = new Scanner(System.in);
								String studentEmailChange = emailChange.nextLine();
								String[] addDetailStrings = { storeFileData.get(a)[0], studentEmailChange };
								storeFileData.put(studentRoleNumberFile, addDetailStrings);
							} else {
								System.out.println("Wrong Selection !!");
							}
						}
					}

					// re-write in a file
					writeInFile(storeFileData);

					fileScanner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // sub3
			else if (submenuinput == 4) {
				// ask user to enter the role number of the student
				String studentRoleNumberFile = StudentRoleNumber();

				// Read json file
				File dataFile = new File(FILE_PATH);
				try {
					Scanner fileScanner = new Scanner(dataFile);
					HashMap<String, String[]> storeFileData = new HashMap<String, String[]>();
					while (fileScanner.hasNextLine()) {
						// read from json file, convert json to hash map
						Gson gson = new GsonBuilder().create();
						Type type = new TypeToken<Map<String, String[]>>() {
						}.getType();
						Map<String, String[]> myMap = gson.fromJson(fileScanner.nextLine(), type);

						for (String i : myMap.keySet()) {
							// store the data in hashmap
							storeFileData.put(i, myMap.get(i));

						} // main for loop ends
					} // while ends

					// display the previous data
					HashMap<String, String> storeRoleNumberHashMap = new HashMap<String, String>();
					storeRoleNumberHashMap.put(studentRoleNumberFile, null);
					for (String a : storeFileData.keySet()) {

						if (storeRoleNumberHashMap.containsKey(a)) {
							// Show the student details
							studentDetails(a, storeFileData.get(a)[0], storeFileData.get(a)[1]);

							// delete the desired student
							String[] addDetailStrings = { storeFileData.get(a)[0], storeFileData.get(a)[1] };
							storeFileData.remove(studentRoleNumberFile);
						}
					}

					// re-write in a file
					writeInFile(storeFileData);

					fileScanner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // sub4
			else {
				System.out.println("Wrong Selection !!");
			}

		} // input1
		else {
			System.out.println("Wrong Selection !!");
		}

	}// main end

	/**
	 * This method used to write the student details in json file
	 * @param storeFileData
	 */
	public static void writeInFile(HashMap<String, String[]> storeFileData) {
		try (FileWriter filrWriter = new FileWriter(FILE_PATH)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(storeFileData, filrWriter);
			System.out.println("Done Successfuly!");
			filrWriter.write("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method used to display the student details
	 * @param id
	 * @param name
	 * @param email
	 */
	public static void studentDetails(String id, String name, String email) {
		System.out.println("------------------------------------");
		System.out.println("Student Details:");
		System.out.println("Role number: " + id);
		System.out.println("Name: " + name);
		System.out.println("Email address: " + email);
		System.out.println("------------------------------------");
	}

	/**
	 * This method used to ask user to enter the role number of the student
	 * @return studentRoleNumberFile
	 */
	public static String StudentRoleNumber() {
		System.out.println();
		System.out.print("Enter the role number of the student: ");
		Scanner roleNumberSc = new Scanner(System.in);
		String studentRoleNumberFile = roleNumberSc.nextLine();
		System.out.println();
		return studentRoleNumberFile;
	}
}// class end