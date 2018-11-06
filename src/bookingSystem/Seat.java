package bookingSystem;

public class Seat {

	private String num;
	private String type;
	private boolean window;
	private boolean aisle;
	private boolean table;
	private double price;
	private String eMail;

	public Seat(String n, String t, boolean w, boolean a, boolean ta, double p, String e) {
		num = n;
		type = t;
		window = w;
		aisle = a;
		table = ta;
		price = p;
		eMail = e;
	}

	public String getNum() {
		return num;
	}

	public String getType() {
		return type;
	}

	public boolean isWindow() {
		return window;
	}

	public boolean isAisle() {
		return aisle;
	}

	public boolean isTable() {
		return table;
	}

	public double getPrice() {
		return price;
	}

	public String geteMail() {
		return eMail;
	}
			
	@Override
	public String toString() {
		return "Reserved:" + (isReserved() ? "Y" : "N") + " No:" + num + " Type:" + type + " Window:" + (window ? "Y":"N") + " Aisle:" + (aisle ? "Y":"N") + " Table:" + (table ? "Y":"N") + " Price:£" + String.format("%.2f", price) + " Email:" + ((eMail.equals("free")) ? "" : eMail);
	}

	public String toSave() {
		return (num + " " + type + " " + window + " " + aisle + " " + table + " " + price + " " + eMail);
	}

	public void bookSeat(String e) throws Exception {
		if (!isReserved()) {
			eMail = e;
		} else {
			throw new Exception("This seat has been reserved please pick another.");
		}
	}

	public void cancelSeat(String e) {
		// This method has less validation than bookSeat because the code would validate
		// the number and email in the Console class. It is reserved so it doesn't need
		// to be checked if it is reserved or not.
		eMail = "free";
	}

	public boolean isReserved() {
		return !eMail.equals("free");
	}
}
