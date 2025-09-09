import java.io.*;
import java.util.*;

public class FlightBookingApp {
    private static final String BOOKINGS_FILE = "bookings.ser";
    private static Map<String, Flight> flights = new HashMap<>();
    private static List<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        loadSampleFlights();
        loadBookings();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nFlight Booking System");
            System.out.println("1. List Flights");
            System.out.println("2. View Flight Details");
            System.out.println("3. Book Seat");
            System.out.println("4. View My Bookings");
            System.out.println("5. Admin: Add Flight");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            switch (ch) {
                case 1: listFlights(); break;
                case 2: viewFlight(sc); break;
                case 3: bookSeat(sc); break;
                case 4: viewBookings(sc); break;
                case 5: adminAddFlight(sc); break;
                case 6: saveBookings(); sc.close(); System.exit(0);
                default: System.out.println("Invalid");
            }
        }
    }

    private static void loadSampleFlights() {
        // sample flights
        flights.put("AI101", new Flight("AI101", "Bengaluru", "Delhi", 12000, 10));
        flights.put("6E202", new Flight("6E202", "Mumbai", "Kolkata", 8000, 8));
        flights.put("SG303", new Flight("SG303", "Chennai", "Hyderabad", 5000, 12));
    }

    private static void listFlights() {
        System.out.println("Available Flights:");
        for (Flight f : flights.values()) {
            System.out.println(f.brief());
        }
    }

    private static void viewFlight(Scanner sc) {
        System.out.print("Enter Flight No: ");
        String fn = sc.nextLine().trim();
        Flight f = flights.get(fn);
        if (f == null) System.out.println("No such flight.");
        else System.out.println(f);
    }

    private static void bookSeat(Scanner sc) {
        System.out.print("Enter Flight No: ");
        String fn = sc.nextLine().trim();
        Flight f = flights.get(fn);
        if (f == null) { System.out.println("Flight not found."); return; }
        System.out.print("Your name: ");
        String name = sc.nextLine().trim();
        System.out.print("Number of seats: ");
        int seats = Integer.parseInt(sc.nextLine().trim());
        if (seats <= 0) { System.out.println("Invalid seats."); return; }
        if (f.availableSeats < seats) { System.out.println("Not enough seats. Available: " + f.availableSeats); return; }

        f.availableSeats -= seats;
        String bookingId = UUID.randomUUID().toString().substring(0, 8);
        Booking b = new Booking(bookingId, fn, name, seats, f.pricePerSeat * seats);
        bookings.add(b);
        saveBookings();
        System.out.println("Booked! Booking ID: " + bookingId + " | Total: ₹" + b.totalPrice);
    }

    private static void viewBookings(Scanner sc) {
        System.out.print("Enter your name to view bookings: ");
        String name = sc.nextLine().trim().toLowerCase();
        boolean found = false;
        for (Booking b : bookings) {
            if (b.customerName.toLowerCase().equals(name)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("No bookings for " + name);
    }

    private static void adminAddFlight(Scanner sc) {
        System.out.print("Flight No: "); String fn = sc.nextLine().trim();
        if (flights.containsKey(fn)) { System.out.println("Flight exists."); return; }
        System.out.print("From: "); String from = sc.nextLine().trim();
        System.out.print("To: "); String to = sc.nextLine().trim();
        System.out.print("Price per seat: "); double price = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Seats: "); int seats = Integer.parseInt(sc.nextLine().trim());
        flights.put(fn, new Flight(fn, from, to, price, seats));
        System.out.println("Flight added.");
    }

    @SuppressWarnings("unchecked")
    private static void loadBookings() {
        File f = new File(BOOKINGS_FILE);
        if (!f.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            bookings = (List<Booking>) in.readObject();
            // restore seats from bookings
            for (Booking b : bookings) {
                Flight fl = flights.get(b.flightNo);
                if (fl != null) fl.availableSeats -= b.seats;
            }
        } catch (Exception e) {
            System.out.println("No previous bookings or error reading bookings: " + e.getMessage());
        }
    }

    private static void saveBookings() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            out.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    // --- inner classes ---
    static class Flight implements Serializable {
        String flightNo;
        String from;
        String to;
        double pricePerSeat;
        int availableSeats;

        Flight(String flightNo, String from, String to, double pricePerSeat, int availableSeats) {
            this.flightNo = flightNo; this.from = from; this.to = to; this.pricePerSeat = pricePerSeat; this.availableSeats = availableSeats;
        }

        String brief() {
            return flightNo + " : " + from + " -> " + to + " | ₹" + pricePerSeat + " | Seats: " + availableSeats;
        }

        public String toString() {
            return "Flight " + flightNo + " (" + from + " -> " + to + ") Price: ₹" + pricePerSeat + " | Available seats: " + availableSeats;
        }
    }

    static class Booking implements Serializable {
        String bookingId;
        String flightNo;
        String customerName;
        int seats;
        double totalPrice;

        Booking(String bookingId, String flightNo, String customerName, int seats, double totalPrice) {
            this.bookingId = bookingId; this.flightNo = flightNo; this.customerName = customerName;
            this.seats = seats; this.totalPrice = totalPrice;
        }

        public String toString() {
            return "BookingID: " + bookingId + " | Flight: " + flightNo + " | Name: " + customerName + " | Seats: " + seats + " | Total: ₹" + totalPrice;
        }
    }
}

