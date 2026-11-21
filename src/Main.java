import java.util.Scanner;

/**
 * COLLEGE AUTOMATED GRADING SYSTEM
 * A simple CLI program that computes the GWA of a student.
 *
 * Concepts used: Scanner, arrays, loops, conditionals, and methods.
 */
public class Main {

    // The only grades the system accepts (from the grading scale).
    static final double[] VALID_GRADES = {1.00, 1.25, 1.50, 1.75, 2.00, 2.25, 2.50, 2.75, 3.00, 5.00};

    static final int LINE_WIDTH = 60;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        showWelcomeScreen();

        // Step 1: Student info
        String studentName = readText(input, "Enter your full name: ", "Name");
        int totalSubjects = readSubjectCount(input);

        // Step 2: Subjects and grades (parallel arrays)
        String[] subjectNames = new String[totalSubjects];
        double[] grades = new double[totalSubjects];

        for (int i = 0; i < totalSubjects; i++) {
            System.out.println();
            System.out.println("--- Subject " + (i + 1) + " of " + totalSubjects + " ---");
            subjectNames[i] = readText(input, "Subject name: ", "Subject name");
            grades[i] = readGrade(input);
        }

        // Step 3: Compute and display
        double gwa = computeGWA(grades);
        displayReport(studentName, subjectNames, grades, gwa);

        input.close();
    }

    // ---------------------------------------------------------------
    // Screens
    // ---------------------------------------------------------------

    static void showWelcomeScreen() {
        printLine('=');
        System.out.println(center("COLLEGE AUTOMATED GRADING SYSTEM"));
        printLine('=');
        System.out.println();
        System.out.println("Valid grades: 1.00, 1.25, 1.50, 1.75, 2.00,");
        System.out.println("              2.25, 2.50, 2.75, 3.00, 5.00");
        System.out.println();
    }

    static void displayReport(String studentName, String[] subjectNames, double[] grades, double gwa) {
        // Make the Subject column as wide as the longest subject name.
        int subjectWidth = "Subject".length();
        for (String name : subjectNames) {
            if (name.length() > subjectWidth) {
                subjectWidth = name.length();
            }
        }
        String rowFormat = "%-" + subjectWidth + "s  %-7s  %-12s  %-8s%n";
        int tableWidth = subjectWidth + 2 + 7 + 2 + 12 + 2 + 8;
        int reportWidth = Math.max(LINE_WIDTH, tableWidth);

        System.out.println();
        printLine('=', reportWidth);
        System.out.println(center("GRADE REPORT", reportWidth));
        printLine('=', reportWidth);
        System.out.println("Student Name   : " + studentName);
        System.out.println("Total Subjects : " + grades.length);
        printLine('-', reportWidth);

        System.out.printf(rowFormat, "Subject", "Grade", "Description", "Remarks");
        printLine('-', reportWidth);

        int failedCount = 0;
        for (int i = 0; i < grades.length; i++) {
            String remarks = getRemarks(grades[i]);
            if (remarks.equals("FAILED")) {
                failedCount++;
            }
            System.out.printf(rowFormat, subjectNames[i], formatGrade(grades[i]),
                    getDescription(grades[i]), remarks);
        }

        printLine('-', reportWidth);
        System.out.println("Computed GWA     : " + formatGrade(gwa));
        System.out.println("Subjects Passed  : " + (grades.length - failedCount));
        System.out.println("Subjects Failed  : " + failedCount);
        System.out.println("Academic Standing: " + getAcademicStanding(gwa, failedCount));
        printLine('=', reportWidth);
    }

    // ---------------------------------------------------------------
    // Input methods (with validation)
    // ---------------------------------------------------------------

    // Keeps asking until the user types something that is not blank.
    static String readText(Scanner input, String prompt, String label) {
        while (true) {
            System.out.print(prompt);
            String text = input.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("  Invalid: " + label + " cannot be empty. Try again.");
        }
    }

    // Keeps asking until the user enters a whole number of at least 1.
    static int readSubjectCount(Scanner input) {
        while (true) {
            System.out.print("How many subjects are you currently enrolled in? ");
            String text = input.nextLine().trim();
            try {
                int count = Integer.parseInt(text);
                if (count >= 1) {
                    return count;
                }
                System.out.println("  Invalid: Enter at least 1 subject.");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid: Please enter a whole number (e.g., 5).");
            }
        }
    }

    // Keeps asking until the user enters a grade found in VALID_GRADES.
    static double readGrade(Scanner input) {
        while (true) {
            System.out.print("Grade (1.00 - 5.00): ");
            String text = input.nextLine().trim();
            try {
                double grade = Double.parseDouble(text);
                if (isValidGrade(grade)) {
                    return grade;
                }
                System.out.println("  Invalid: " + text + " is not in the grading scale.");
                System.out.println("  Allowed: 1.00, 1.25, 1.50, 1.75, 2.00, 2.25, 2.50, 2.75, 3.00, 5.00");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid: Please enter a number (e.g., 1.75).");
            }
        }
    }

    // ---------------------------------------------------------------
    // Grading logic
    // ---------------------------------------------------------------

    // True only if the grade matches one of the grades in the scale.
    static boolean isValidGrade(double grade) {
        for (double valid : VALID_GRADES) {
            if (Math.abs(grade - valid) < 0.001) {
                return true;
            }
        }
        return false;
    }

    // GWA = sum of all grades / number of subjects (equal units).
    static double computeGWA(double[] grades) {
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }

    // Description of a single subject grade based on the scale.
    static String getDescription(double grade) {
        if (grade <= 1.00 + 0.001) {
            return "Excellent";
        } else if (grade <= 1.75 + 0.001) {
            return "Very Good";
        } else if (grade <= 2.25 + 0.001) {
            return "Good";
        } else if (grade <= 2.75 + 0.001) {
            return "Satisfactory";
        } else if (grade <= 3.00 + 0.001) {
            return "Passed";
        } else {
            return "Failed";
        }
    }

    // 1.00 to 3.00 is PASSED; 5.00 is FAILED.
    static String getRemarks(double grade) {
        if (grade <= 3.00 + 0.001) {
            return "PASSED";
        }
        return "FAILED";
    }

    // Overall standing based on the GWA. Any failed subject lowers the standing.
    static String getAcademicStanding(double gwa, int failedCount) {
        if (failedCount > 0) {
            return "Needs Improvement (" + failedCount + " failed subject"
                    + (failedCount > 1 ? "s" : "") + ")";
        }
        if (gwa <= 1.25) {
            return "Excellent Standing";
        } else if (gwa <= 1.75) {
            return "Very Good Standing";
        } else if (gwa <= 2.25) {
            return "Good Standing";
        } else if (gwa <= 2.75) {
            return "Satisfactory Standing";
        } else {
            return "Passing Standing";
        }
    }

    // ---------------------------------------------------------------
    // Formatting helpers
    // ---------------------------------------------------------------

    static String formatGrade(double value) {
        return String.format("%.2f", value);
    }

    static void printLine(char symbol) {
        printLine(symbol, LINE_WIDTH);
    }

    static void printLine(char symbol, int width) {
        for (int i = 0; i < width; i++) {
            System.out.print(symbol);
        }
        System.out.println();
    }

    static String center(String text) {
        return center(text, LINE_WIDTH);
    }

    static String center(String text, int width) {
        int spaces = (width - text.length()) / 2;
        if (spaces < 0) {
            spaces = 0;
        }
        return " ".repeat(spaces) + text;
    }
}
