import java.util.Scanner;

public class App {
    private static int NoSeatsAvailable = -1;
    private static Passenger EmptySeat = null;
    
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Passenger[] seats = new Passenger[12];

        System.out.println("Welcome to SkyBox Ltd Passenger Manager");
        System.out.println("Type a number to choose an action.\n");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(sc, "Your choice: ");

            switch (choice) {
                case 1 -> insertPassenger(sc, seats);
                case 2 -> removePassenger(sc, seats);
                case 3 -> switchSeats(sc, seats);
                case 4 -> renamePassenger(sc, seats);
                case 5 -> System.out.println("Passengers on board: " + countPassengers(seats));
                case 6 -> printManifest(seats);
                case 7 -> {
                    System.out.println("Exiting. Have a nice flight!");
                    running = false;
                }
                default -> System.out.println("Unknown option. Please choose 1-7");
            }
            System.out.println();
        }
        sc.close();
    }

    // meny och utskrift
    private static void printMenu() {
        System.out.println("What would you like to do?");
        System.out.println(" 1) Insert passenger");
        System.out.println(" 2) Remove passenger");
        System.out.println(" 3) Switch seats");
        System.out.println(" 4) Rename passenger");
        System.out.println(" 5) Count passengers");
        System.out.println(" 6) Print passenger manifest");
        System.out.println(" 7) Exit");
    }

    private static void printManifest(Passenger[] seats) {
        System.out.println("###### PASSENGER MANIFEST ######");
        System.out.println("");
        System.out.println("SkyBox Ltd");
        System.out.println("");
        System.out.println("Seat\tName\tAge");

        for (int i = 0; i < seats.length; i++) {
            int rowNumber = i + 1;
            if (seats[i] == null) {
                // om raden är tom skriver bara ut radnumret
                System.out.println(rowNumber);
            } else {
                System.out.println(rowNumber + "\t" + seats[i].getName() + "\t" + seats[i].getAge());
            }
        }
    }

    private static void insertPassenger(Scanner sc, Passenger[] seats) {
        int freeIndex = firstFreeIndex(seats);
        if (freeIndex == NoSeatsAvailable) {
            System.out.println("Flight is full. No free seats available");
            return;
        }
        String name = readNonEmptyLine(sc, "Enter passengers name: ");
        int age = readPositiveInt(sc, "Enter passengers age: ");
        // på platsen i arrayen som freeindex pekar på tilldelas en ny passagerarobjekt, namn och age
        seats[freeIndex] = new Passenger(name, age);
        System.out.println("Passenger inserted at seat " + (freeIndex + 1));

    }

    private static void removePassenger(Scanner sc, Passenger[] seats) {
        int seatNo = readSeatNo(sc, "Enter seat number to remove (1-12): ");
        int idx = seatNo - 1;
        if (seats[idx] == EmptySeat) {
            System.out.println("That seat is already empty.");
            return;
        }
        // tar bort passageraren, genom att sätta nytt värde på seats idx till null
        seats[idx] = EmptySeat;
        System.out.println("Passenger removed from seat " + seatNo);
    }

    private static void switchSeats(Scanner sc, Passenger[] seats) {
        int a = readSeatNo(sc, "Enter first seat (1-12): ");
        int b = readSeatNo(sc, "Enter second seat (1-12): ");
        if (a == b) {
            System.out.println("Both seat numbers are the same. Nothing to switch.");
            return;
        }
        int ia = a - 1;
        int ib = b - 1;
        Passenger temp = seats[ia];
        seats[ia] = seats[ib];
        seats[ib] = temp;
        System.out.println("Switched seats " + a + " ↔ " + b);
    }

    private static void renamePassenger(Scanner sc, Passenger[] seats) {
        int seatNo = readSeatNo(sc, "Enter seat to rename (1-12): ");
        int idx = seatNo - 1;
        if (seats[idx] == EmptySeat) {
            System.out.println("That seat is empty. Cannot rename.");
            return;
        }
        String newName = readNonEmptyLine(sc, "Enter new name: ");
        Passenger toRename = seats[idx];
        toRename.setName(newName);
        System.out.println("Seat" + seatNo + " updated");
    }

    private static int firstFreeIndex(Passenger[] seats) {
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == EmptySeat)
                return i;
        }
        return NoSeatsAvailable;
    }

    private static int countPassengers(Passenger[] seats) {
        int numberOfTakenSeats = 0;
        // for loop som går igenom hela passenger arrayen
        for (Passenger seat : seats) {
            if (seat != EmptySeat)
                numberOfTakenSeats++;
        }
        return numberOfTakenSeats;
    }

    private static int readInt(Scanner sc, String prompt) {
        // loop, programmet körs till vi lyckas returnera en int
        while (true) {
            System.out.println(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static int readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            int value = readInt(sc, prompt);
            if (value > 0)
                return value;
            System.out.println("Please enter a number greater than 0.");
        }
    }

    private static String readNonEmptyLine(Scanner sc, String prompt) {
        while (true) {
            System.out.println(prompt);
            String s = sc.nextLine().trim();
            // om s INTE är tomt, dvs om användaren skrev in nåt, returnera s
            if (!s.isEmpty())
                return s;
            // om s isempty
            System.out.println("Input cannot be empty.");
        }
    }

    private static int readSeatNo(Scanner sc, String prompt) {
        // metoden körs tills användaren skrivit in en siffra 1-12
        while (true) {
            int seat = readInt(sc, prompt);
            if (seat >= 1 && seat <= 12)
                return seat;
            System.out.println("Seat must be between 1 and 12.");
        }
    }
}
