import java.io.*;
import java.util.*;

abstract class Room {
    protected int roomNo;
    protected String category;
    protected double price;
    protected boolean available;

    public Room(int roomNo, String category, double price){
        this.roomNo = roomNo;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    public abstract void showRoom();

    public int getRoomNo() {
        return roomNo; 
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean status){
        available = status;
    }
}
//For standard room
class StRoom extends Room {
    public StRoom(int roomNo) {
        super(roomNo, "Standard", 2000);
    }

    @Override
    public void showRoom() {
        System.out.print(roomNo+" ");
        System.out.print(category+" ");
        System.out.print(price+" ");
         System.out.print(available+" ");
         System.out.println();
    }
}

//for Deluxe Room
class DeluxeRoom extends Room {
    public DeluxeRoom(int roomNo) {
    super(roomNo, "Deluxe", 3000);
    }

    @Override
    public void showRoom() {
    System.out.println(roomNo+" ");
    System.out.print(category+" ");
    System.out.print(price+" ");
    System.out.print(available+" ");
    System.out.println();
    }
}

// for Suite room
class SuiteRoom extends Room {
    public SuiteRoom(int roomNo) {
        super(roomNo, "Suite", 5000);
    }

    @Override
    public void showRoom() {
    System.out.println(roomNo+" ");
    System.out.print(category+" ");
    System.out.print(price+" ");
    System.out.print(available+" ");
    System.out.println();
    }
}

// Booking Class
class Booking {
    private String customerName;
    private int roomNo;
    private String category;
    private double amount;

    public Booking(String customerName, int roomNo, String category, double amount) {
    this.customerName = customerName;
    this.roomNo = roomNo;
    this.category = category;
    this.amount = amount;
    }

    public String toFileString() {
     return customerName + "," +roomNo + "," +category + "," +amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRoomNo() {
        return roomNo;
    }

    @Override
    public String toString() {
    return customerName + "," +roomNo + "," +category + "," +amount;
    }
}

// Hotel Class
class Hotel {
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();

    public Hotel() {

        // Standard Rooms
        rooms.add(new StRoom(100));
        rooms.add(new StRoom(101));

        // Deluxe Rooms
        rooms.add(new DeluxeRoom(200));
        rooms.add(new DeluxeRoom(201));

        // Suite Rooms
        rooms.add(new SuiteRoom(300));
        rooms.add(new SuiteRoom(301));

        loadBookings();
    }

    // Search Rooms
    public void searchRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                room.showRoom();
            }
        }
    }

    // Book Room
 public void bookRoom(Scanner sc) {

     System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

 System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

    for (Room room : rooms) {

     if (room.getRoomNo() == roomNo &&
                room.isAvailable()) {

             room.setAvailable(false);

         System.out.println("Payment Amount:  "+
                        room.getPrice());

   System.out.print(" Payment? (yes/no): ");
            String payment = sc.nextLine();

        if (payment.equalsIgnoreCase("yes")){

 Booking booking =new Booking(name,roomNo,room.getCategory(), room.getPrice());

            bookings.add(booking);

            saveBooking(booking);

     System.out.println("\nRoom Book Successfully                                               !");
     return;
         } else {
        room.setAvailable(true);
             System.out.println("Payment Failed.");
                 return;
                }
            }
        }

        System.out.println("Room not available.");
    }

    // Cancel Reservation
    public void cancelReservation(Scanner sc) {

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        Booking found = null;

        for (Booking b : bookings) {
            if (b.getRoomNo() == roomNo) {
                found = b;
                break;
            }
        }

        if (found != null) {

            bookings.remove(found);

            for (Room room : rooms) {
                if (room.getRoomNo() == roomNo) {
                    room.setAvailable(true);
                }
            }

            rewriteFile();

            System.out.println("Reservation Cancelled.");
        } else {
            System.out.println("Booking Not Found.");
        }
    }
    
    // View Bookings
    public void viewBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No Bookings Found.");
            return;
        }

        for (Booking booking : bookings) {
            System.out.println("\n----------------");
            System.out.println(booking);
        }
    }

    // Save Booking
    private void saveBooking(Booking booking) {

        try (BufferedWriter bw = new BufferedWriter(
            new FileWriter( "bookings.txt", true))) {

            bw.write(booking.toFileString());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("File Error.");
        }
    }

    // Load Bookings
    private void loadBookings() {

        File file = new File("bookings.txt");

        if (!file.exists())
            return;

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

         String[] data = line.split(",");

Booking booking = new Booking( data[0],Integer.parseInt(data[1]),data[2],Double.parseDouble(data[3]));

                bookings.add(booking);

                for (Room room : rooms) {
     if (room.getRoomNo() == booking.getRoomNo()) {
                        room.setAvailable(false);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Loading Error.");
        }
    }

    // Rewrite File
    private void rewriteFile() {

 try (BufferedWriter bw = new BufferedWriter( new FileWriter( "bookings.txt"))) {

            for (Booking booking : bookings) {

                bw.write(booking.toFileString());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("File Error.");
        }
    }
}

// Main Class
public class Reservation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Hotel hotel = new Hotel();

        while (true) {

            System.out.println("\n====== HOTEL RESERVATION SYSTEM ======");

            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    hotel.searchRooms();
                    break;

                case 2:
                    hotel.bookRoom(sc);
                    break;

                case 3:
                    hotel.cancelReservation(sc);
                    break;

                case 4:
                    hotel.viewBookings();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}