package project2;

import java.util.Properties;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class NamesByState {
	
	// Instantiate Global ArrayLists to keep track of data
	ArrayList<ArrayList<String>> femaleNames = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> maleNames = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<Integer>> femaleOcc = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> maleOcc = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> yearsOcc = new ArrayList<Integer>();
	ArrayList<Integer> maxYear = new ArrayList<Integer>();
	ArrayList<Integer> occur = new ArrayList<Integer>();
	
	/*
	 * Reads data from file given state and directory
	 */
	private void readData(String state, String directory) {
		try {
			File file = new File(directory + "/" + state + ".TXT");
			Scanner scan = new Scanner(file);
			
			// counter for when adding into both female and male lists
			int yearF = 1910;
			int yearM = 1910;
			
			//new slots for first index
			femaleNames.add(new ArrayList<String>());
			maleNames.add(new ArrayList<String>());
			femaleOcc.add(new ArrayList<Integer>());
			maleOcc.add(new ArrayList<Integer>());
			
			//loop through entire file 
			while (scan.hasNextLine()) {
				String line = scan.nextLine(); // AK,F,1910,Mary,8
				String [] arr = line.split(",");// arr = [AK,F,1910,Mary,8]
				
				//store female names and occurrences of each name
				if (arr[1].equals("F")) {
					if (Integer.parseInt(arr[2]) == yearF) {
						femaleNames.get(yearF-1910).add(arr[3]);//add names to the same year slot
						femaleOcc.get(yearF-1910).add(Integer.parseInt(arr[4]));
						
					} else {
						yearF++;
						femaleNames.add(new ArrayList<String>());//increment the slot
						femaleNames.get(yearF-1910).add(arr[3]);//add names
						femaleOcc.add(new ArrayList<Integer>());
						femaleOcc.get(yearF-1910).add(Integer.parseInt(arr[4]));
					}
					
				}
				
				//store male names and occurrences of each name
				if (arr[1].equals("M")) {
					if (Integer.parseInt(arr[2]) == yearM) {
						maleNames.get(yearM-1910).add(arr[3]);
						maleOcc.get(yearM-1910).add(Integer.parseInt(arr[4]));
					} else {
						yearM++;
						maleNames.add(new ArrayList<String>());//increment the slot
						maleNames.get(yearM-1910).add(arr[3]);//add names
						maleOcc.add(new ArrayList<Integer>());
						maleOcc.get(yearM-1910).add(Integer.parseInt(arr[4]));// add occurrences
					}
				}	
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Gets occurrences of a given name based on gender, returns an ArrayList containing every occurrence
	 */
	private ArrayList<Integer> getOccurrences (String name, String gender) {
		try {
			if (gender.equals("F")) { // if gender is female, look through the female lists
				for (int i = 0; i < femaleNames.size(); i++) {
					for (int k = 0; k < femaleNames.get(i).size(); k++) {
						if (femaleNames.get(i).get(k).equals(name)) {
							occur.add(femaleOcc.get(i).get(k)); // find all occurrences
							yearsOcc.add(i + 1910); // actual year of occurrence
						}
					}
				}
				
			} else { // if gender is male, look through the male lists
				for (int i = 0; i < maleNames.size(); i++) {
					for (int k = 0; k < maleNames.get(i).size(); k++) {
						if (maleNames.get(i).get(k).equals(name)) {
							occur.add(maleOcc.get(i).get(k));
							yearsOcc.add(i + 1910);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return occur;
	}
	
	/*
	 * Finds the max occurrence from the occurrence ArrayList parameter
	 */
	private int getMax(ArrayList<Integer> arr) {
		int max = 0;
		try {
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i) > max) { // if an occurrence is higher than the current max, change the current max
					max = arr.get(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return max;
	}
	
	/*
	 * Finds how many times the max number occurs in the occur ArrayList, returns the number of times said max number occurs
	 */
	private int getMaxOcc (int max) {
		try {
			for (int i = 0; i < occur.size(); i++) {
				if (occur.get(i) == max) { // if an occurrence == the max, add it to the maxYears list 
					maxYear.add(yearsOcc.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxYear.size(); // returns the size of maxYears to determine what to print
	}
	
	/*
	 * Prints the likely age(s) or a range of ages for a particular name taking in the number of max occurrences of the name, the name itself, and the state in question
	 */
	private void print (int count, String name, String state) {
		try {
			if (count == 0) { // if there are no occurrences of the name in a state, print name does not exist
				System.out.println("The name you are looking for in " + state + " does not exist");
			} else if (count == 1) { // if there is one max occurrence of the name in a state, print the likely age of the person
				int age = 2021 - maxYear.get(0);
				System.out.println(name + ", born in " + state + " is most likely around " + age + " years old.");
			} else if (count == 2) { // if there are two max occurrences of the name in a state, print the two likely ages of the person
				int firstAge = 2021 - maxYear.get(0);
				int secondAge = 2021 - maxYear.get(1);
				
				System.out.println(name + ", born in " + state + " is either " + secondAge + " or " + firstAge + " years old.");
				
			} else { // if there are more than two max occurrences of the name in a state, print the range of ages the person is most likely aged
				int firstAge = 2021 - maxYear.get(0);
				int secondAge = 2021 - maxYear.get(maxYear.size() - 1);
				
				System.out.println(name + ", born in " + state + " is aged " + secondAge + " to " + firstAge + " years old.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * helper function to determine if the user entered a valid state or not
	 */
	public boolean checkState (String code, String[] states) {
		for (String state : states) {
			if (code.equals(state)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		
		String directory = "";
		try {
			Properties prop = new Properties();
			InputStream in = new FileInputStream(new File(args[0]));
			prop.load(in);
			directory = prop.getProperty("Directory");
			String listType = prop.getProperty("ListType");
			if (directory.isEmpty()) {
				System.out.println("Directory not found, program will now exit.");
				System.exit(1);
			}
			if (!listType.equals("ArrayList") && !listType.equals("LinkedList")) {
				System.out.println("List Type not found, program will now exit.");
				System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] states = {
				"AK", "AL", "AR", "AZ", "CA", "CO", "CT",
				"DC", "DE", "FL", "GA", "HI", "IA", "ID",
				"IL", "IN", "KS", "KY", "LA", "MA", "MD",
				"ME", "MI", "MN", "MO", "MS", "MT", "NC",
				"ND", "NE", "NH", "NJ", "NM", "NV", "NY",
				"OH", "OK", "OR", "PA", "RI", "SC", "SD",
				"TN", "TX", "UT", "VA", "VT", "WA", "WI",
				"WV", "WY"
				};
		
		while (true) { // loop to continuously prompt user
			NamesByState n = new NamesByState();
			
			Scanner scan = new Scanner(System.in);
			
			System.out.println("Name of person (or EXIT to quit): ");
			String name = scan.next();
			if (name.equalsIgnoreCase("exit")) { // if the user enters "exit", leave the program
				System.out.println("The program will now exit, have a nice day.");
				break;
			}
			
			System.out.println("Gender (M/F): ");
			String gender = scan.next();
			
			// valid input check for gender
			if (!gender.equals("M") && !gender.equals("F")) {
				boolean validGender = false;
				while (validGender == false) {
					System.out.println("Please enter either 'M' or 'F' as a capital letter: ");
					gender = scan.next();
					if (gender.equals("M") || gender.equals("F")) {
						validGender = true;
					}
				}
			}
			
			System.out.println("State of Birth (two-letter state code): ");
			String sob = scan.next();
			
			// valid input check for state
			boolean validState = false;
			while (validState == false) {
				validState = n.checkState(sob, states);
				if (validState == false) {
					System.out.println("Please enter a valid two-letter state code (ie CA for California): ");
					sob = scan.next();
				}
			}
			
			n.readData(sob, directory);
			try {
				n.getOccurrences(name, gender);
				int maxOcc = n.getMaxOcc(n.getMax(n.occur));
				n.print(maxOcc, name, sob);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
