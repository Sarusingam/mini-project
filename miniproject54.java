import java.util.Scanner;

public class MiniProject {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter first parameter (DOB= or AGE= followed by the date):");
        String firstParam = in.nextLine().trim();
        System.out.println("Enter second parameter (reference date):");
        String secondParam = in.nextLine().trim();
        System.out.println("Enter date format (e.g., DDMMYYYY):");
        String format = in.nextLine().trim();
        System.out.println("Enter delimiter for output (e.g., - or /):");
        String delimiter = in.nextLine().trim();
        in.close();

        // Determine operation
        String operation = firstParam.substring(0, 4);

        // Extract dates from inputs
        String dobString = firstParam.substring(4);
        String refDateString = secondParam;

        try {
            // Parse dates based on format
            int[] dob = parseDate(dobString, format);
            int[] refDate = parseDate(refDateString, format);

            // Validate parsed dates
            if (!isValidDate(dob[0], dob[1], dob[2]) || !isValidDate(refDate[0], refDate[1], refDate[2])) {
                System.out.println("Invalid date(s) provided.");
                return;
            }

            if (operation.equals("DOB=")) {
                calculateAge(dob, refDate);
            } else if (operation.equals("AGE=")) {
                calculateDOB(dob, refDate, format, delimiter);
            } else {
                System.out.println("Invalid operation. Use DOB= or AGE= as the first parameter.");
            }
        } catch (Exception e) {
            System.out.println("Error parsing inputs. Please ensure the date and format are correct.");
        }
    }

    // Parses a date string based on the given format
    public static int[] parseDate(String date, String format) {
        int day = 0, month = 0, year = 0;
        int formatIndex = 0;

        for (char c : format.toCharArray()) {
            if (c == 'D') {
                day = day * 10 + (date.charAt(formatIndex) - '0');
            } else if (c == 'M') {
                month = month * 10 + (date.charAt(formatIndex) - '0');
            } else if (c == 'Y') {
                year = year * 10 + (date.charAt(formatIndex) - '0');
            }
            formatIndex++;
        }

        return new int[]{day, month, year};
    }

    // Validates a date
    public static boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > getDaysInMonth(month, year)) {
            return false;
        }
        return true;
    }

    // Returns the number of days in a given month, accounting for leap years
    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 2: // February
                return (isLeapYear(year)) ? 29 : 28;
            case 4: case 6: case 9: case 11: // April, June, September, November
                return 30;
            default: // All other months
                return 31;
        }
    }

    // Checks if a year is a leap year
    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

    // Calculates and prints age based on the given dates
    public static void calculateAge(int[] dob, int[] refDate) {
        int day = refDate[0] - dob[0];
        int month = refDate[1] - dob[1];
        int year = refDate[2] - dob[2];

        if (day < 0) {
            day += getDaysInMonth(refDate[1] - 1, refDate[2]);
            month--;
        }
        if (month < 0) {
            month += 12;
            year--;
        }

        if (year < 0) {
            System.out.println("Person is not yet born.");
        } else {
            System.out.println("Person is " + year + " years, " + month + " months, and " + day + " days old.");
        }
    }

    // Calculates and prints the DOB in the specified format and delimiter
    public static void calculateDOB(int[] dob, int[] refDate, String format, String delimiter) {
        int day = dob[0];
        int month = dob[1];
        int year = dob[2];

        StringBuilder dobOutput = new StringBuilder();

        for (char c : format.toCharArray()) {
            if (c == 'D') {
                dobOutput.append(String.format("%02d", day)).append(delimiter);
            } else if (c == 'M') {
                dobOutput.append(String.format("%02d", month)).append(delimiter);
            } else if (c == 'Y') {
                dobOutput.append(year).append(delimiter);
            }
        }

        // Remove trailing delimiter
        System.out.println(dobOutput.substring(0, dobOutput.length() - 1));
    }
}
