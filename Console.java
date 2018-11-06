package bookingSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Console {

	private static String PATH = "M:\\data\\seats.txt";
	private static Scanner FR, S;
	private static LinkedList<Seat> seats = new LinkedList<Seat>();
	private static PrintWriter P;
	
	public static void main(String[] args) {
		loadData();
		menu();
	}

	public static void loadData(){
		
		try {
			FR = new Scanner(new FileReader(PATH));
			
			while(FR.hasNext()) {
				Seat list = new Seat(FR.next(),FR.next(),FR.nextBoolean(),FR.nextBoolean(),FR.nextBoolean(),FR.nextDouble(),FR.next());
				seats.add(list);
			}
			FR.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void menu() {

		S = new Scanner(System.in);
		
		//Menu
		System.out.println("--Seat Booking System--");
		System.out.println("");
		System.out.println("--MAIN MENU--");
		System.out.println("1-Reserve Seat");
		System.out.println("2-Cancel Seat");
		System.out.println("3-View Seat Reservations");
		System.out.println("Q-Quit");
		
		switch(S.next().toUpperCase()) {
			default: 
				System.out.println("Please only type the shown parameters: '1,2,3,Q' ");
				break;
			case "1":
				bookData();
				break;
			case "2":
				cancelData();
			case "3":
				viewData();
				break;
			case "Q":
				quit();
				break;
			}
		S.close();
	}
	
	public static void bookData() {
		//Find the seat then S.next()
		System.out.println("Please Enter the Seat Number: ");
		for (Seat S : seats) {
			if (S.isReserved()) {
				System.out.println("This seat is currently reserved please choose another");
			} else {
				
			}
		}
		
	}
	public static void cancelData() {
		//Find the seat then cancelSeat()
		//Seat.cancelSeat();
	}
	
	
	
	public static void viewAvailableData() {
		for (Seat S : seats) {
			if (!S.isReserved()) {
				System.out.println(S.toString());
			}
		}

	}
	
	public static void searchReserve() {
		
	}
	
	public static void viewData() {
		for (Seat S : seats) {
			System.out.println(S.toString());
		}
		System.out.println("");
		menu();
	}
	
	public static void saveData() {
		try {
			P = new PrintWriter(PATH);
			for (Seat S : seats) {
				P.println(S.toSave());
			}
			P.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}

	}
	
	public static void quit() {
		System.out.println("Are you sure you wish to quit? [Y/N]: ");
		String quitChoice = S.next().toUpperCase();
		
		do {
		if (quitChoice.equals("Y")) {
				saveData();
				System.out.println("Data has been saved");
				System.exit(0);
		} 
		if (quitChoice.equals("N")) {
			menu();
		} else {
				System.out.println("Please type only Y or N");
		}
		} while (!quitChoice.equals("Y") || !quitChoice.equals("N"));
		
	}
}
