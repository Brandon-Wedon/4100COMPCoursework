package bookingSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Console {

	// These variables are here as they are used in multiple methods and stating
	// them here means that the same code does not have to be re-written.
	private static final String PATH = "M:\\data\\seats.txt";
	private static Scanner FR, S = new Scanner(System.in);;
	private static LinkedList<Seat> seats = new LinkedList<Seat>();
	private static PrintWriter P;
	private static String eMail = "";
	private static String num = "";
	private static boolean exitLoop = false;
	private static String inputError = "Please type only Y or N";

	public static void main(String[] args) {
		// The main holds this first line instead of the menu method so that it is not
		// repeated every time the menu is loaded
		System.out.println("--Seat Booking System--");
		loadData();

		String choice = "";

		// Menu
		do {
			System.out.println("");
			System.out.println("--MAIN MENU--");
			System.out.println("1-Reserve Seat");
			System.out.println("2-Cancel Seat");
			System.out.println("3-Search and Reserve Seat");
			System.out.println("Q-Quit");

			choice = S.next().toUpperCase();

			switch (choice) {
			default:
				System.out.println("Please only type the shown parameters: '1,2,3,Q' ");
				break;
			case "1":
				bookSeats();
				break;
			case "2":
				cancelSeats();
				break;
			case "3":
				searchReserve();
				break;
			case "Q":
				quit();
				break;
			}
		} while (!choice.equals("Q"));
		S.close();
	}

	private static void loadData() {

		try {
			FR = new Scanner(new FileReader(PATH));
			// As the file has different file types they must be added to a list to create
			// an object. The object is then added to the LinkedList.
			while (FR.hasNext()) {
				seats.add(new Seat(FR.next(), FR.next(), FR.nextBoolean(), FR.nextBoolean(), FR.nextBoolean(),
						FR.nextDouble(), FR.next()));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		// The finally has an if statement to check whether or not the FileReader loads
		// to know if it needs to be closed or not.
		finally {
			if (FR != null) {
				FR.close();
			}
		}
	}

	private static void bookSeats() {
		System.out.println("Would you like to see all available seats? [Y/N]:");
		if (S.next().toUpperCase().equals("Y")) {
			viewAllSeats();
		}

		seatCheck();

		for (Seat s : seats) {
			// The bookSeat method in the Seat class validates if the seat is reserved or
			// not.
			// The if statement is there to validation whether or not the seat number is
			// correct.
			if (s.getNum().equals(num)) {
				exitLoop = true;
				try {
					s.bookSeat(eMail);
					System.out.println("Seat has been reserved successfully.");
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		}
		if (!exitLoop) {
			System.out.println("The seat number entered is incorrect please try again");
			System.out.println("");
		}
	}

	private static void cancelSeats() {

		seatCheck();

		for (Seat s : seats) {
			// The if statement validates if both the email and seat number are valid so
			// that only the correct user can cancel a seat.
			if (s.getNum().equals(num) && s.geteMail().equals(eMail)) {
				exitLoop = true;
				s.cancelSeat(eMail);
				System.out.println("Seat has been cancelled successfully.");
			}
		}
		if (!exitLoop) {
			System.out.println("The details entered are incorrect please try again");
			System.out.println("");
		}
	}

	private static void viewAllSeats() {
		for (Seat s : seats) {
			System.out.println(s.toString());
		}
		System.out.println("");
	}

	// Not sure how to get this to work
	private static void searchReserve() {

		boolean window = false;
		boolean aisle = false;
		boolean table = false;
		System.out.println("Please enter which type of seat you would like [1ST/STD]: ");
		String type = S.next();
		type = type.toUpperCase();
		System.out.println("Please enter if you would like to have a window seat or not [Y/N]: ");
		
		String windowAnswer = S.next().toUpperCase();
		if (windowAnswer.equals("Y")) {
			window = true;
		} 
		else if (windowAnswer.equals("N")) {
			window = false;
		} else {
			System.out.println(inputError);
		}
		System.out.println("Please enter if you would like to have an aisle seat or not [Y/N]: ");
		
		String aisleAnswer = S.next().toUpperCase();
		if (aisleAnswer.equals("Y")) {
			aisle = true;
		} 
		else if (aisleAnswer.equals("N")) {
			aisle = false;
		} else {
			System.out.println(inputError);
		}
		System.out.println("Please enter if you would like to have an table seat or not [Y/N]: ");
		
		String tableAnswer = S.next().toUpperCase();
		if (tableAnswer.equals("Y")) {
			table = true;
		} 
		else if (tableAnswer.equals("N")) {
			table = false;
		} else {
			System.out.println(inputError);
		}

		System.out.println("Please enter the maximum price of the ticket you would like: ");
		double maxPrice = Double.valueOf(S.next().toUpperCase());

		int i = 0;

		for (int j = 5; j >= 3; j--) {
			for (Seat s : seats) {
				if (s.getType().equals(type)) {
					i++;
				}
				if (s.isWindow() == window) {
					i++;
				}
				if (s.isAisle() == aisle) {
					i++;
				}
				if (s.isTable() == table) {
					i++;
				}
				if (s.getPrice() <= maxPrice) {
					i++;
				}

				if (i == j) {
					System.out.println(s.toString());
					exitLoop = true;
				}
				i = 0;
			}
			if (!exitLoop) {
				System.out.println("No seat that match " + j + " of you requirements. Showing similar seats:");
			}

		}
		System.out.println("Would you like to book a seat? [Y/N]");
		if (S.next().toUpperCase().equals("Y")) {
			bookSeats();
		}
	}

	private static void saveData() {
		try {
			P = new PrintWriter(PATH);
			for (Seat s : seats) {
				P.println(s.toSave());
			}

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		// The finally has an if statement to check whether or not the Scanner loads to
		// know if it needs to be closed or not.
		finally {
			if (P != null) {
				P.close();
			}
		}
	}

	private static void quit() {
		String quitChoice = "";
		// This loop is to ensure that only the correct options are used.
		do {
			System.out.println("Are you sure you wish to quit? [Y/N]: ");
			quitChoice = S.next().toUpperCase();

			if (quitChoice.equals("Y")) {
				saveData();
				System.out.println("Data has been saved");
				System.exit(0);
				;
			}
			if (quitChoice.equals("N")) { // This option is here to give the end-user to option to change their mind.
				break;
			} else { // This part of the loop is the validation
				System.out.println("Please type Y or N");
			}
		} while (!(quitChoice.equals("Y") || quitChoice.equals("N")));

	}

	// This method is used to validate the user input to avoid transcription errors
	// and remove repeated code.
	// This method uses the email and num variable which is opened at the start of
	// the script.
	private static void seatCheck() {
		// Although the seat number is here to remove the need for repeated code
		System.out.println("Please Enter the Seat Number: ");
		num = S.next().toUpperCase();
		// The int below is used to give the user an exit from the loop if they want
		// one.
		int i = 0;
		do {
			System.out.println("Please Enter your Email address: ");
			eMail = S.next();
			if (!eMail.contains("@")) {
				System.out.println("Your email is missing an @ symbol. Please try again.");
			}
			// All emails require a .co.uk or a .com at the end. This ensures that the email
			// has them.
			if (!eMail.contains(".co")) {
				System.out.println("Your email is missing a .co.uk or a .com. Please try again.");
			}

			i++;
			// The if statements give the user 3 attempts to exit the system before it
			// terminates.
			// This is to prevent users from spamming the system with the loop.
			if ((i == 3) || (i == 6)) {
				System.out.println("If you would like to exit to the menu enter [M]: ");
				if (S.next().toUpperCase().equals("M")) {
					break;
				}
			}
			if (i == 9) {
				System.out.println(
						"This is you last chance to enter the email correctly. If you would like to exit to the menu enter [M]: ");
				if (S.next().toUpperCase().equals("M")) {
					break;
				}
			}
			if (i == 10) {
				System.out.println("Your email has been entered incorrect too many times.");
				break;
			}
		} while (!(eMail.contains("@") && eMail.contains(".co")));
	}
}
