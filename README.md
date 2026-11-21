# College Automated Grading System

A beginner-friendly Java program that runs entirely in the command line.
It asks for a student's subjects and grades, then computes the GWA and
prints a clean grade report.

## Project Structure

```
AutomatedGradingSystem/
├── src/Main.java   # The whole program
├── run.bat         # Compile and run (Windows)
├── run.sh          # Compile and run (macOS / Linux)
└── README.md
```

## How to Run

Requires a JDK (Java 8 or later).

**Easiest:** double-click `run.bat` (Windows) or run `./run.sh` (macOS/Linux).

**Manually**, from the project folder:

```
javac -d out src/Main.java
java -cp out Main
```

## How It Works

1. Shows the welcome screen.
2. Asks for the student's full name and number of subjects.
3. Loops once per subject, asking for the subject name and grade.
4. Rejects any grade not in the scale (e.g. 1.30, 2.80, 4.00) and asks again.
5. Computes `GWA = sum of grades / number of subjects` (equal units).
6. Prints the report: name, subject count, each subject with its grade,
   description and PASSED/FAILED remark, the GWA, and the overall standing.

## Grading Scale

| Grade | Description |
|---|---|
| 1.00 | Excellent |
| 1.25, 1.50, 1.75 | Very Good |
| 2.00, 2.25 | Good |
| 2.50, 2.75 | Satisfactory |
| 3.00 | Passed |
| 5.00 | Failed |

A subject is **PASSED** with 1.00 – 3.00 and **FAILED** with 5.00.

## Overall Academic Standing

If any subject is failed, the standing is **Needs Improvement**. Otherwise it
follows the GWA: up to 1.25 Excellent, up to 1.75 Very Good, up to 2.25 Good,
up to 2.75 Satisfactory, and up to 3.00 Passing.

## Sample Output

```
============================================================
              COLLEGE AUTOMATED GRADING SYSTEM
============================================================
...
===============================================================
                         GRADE REPORT
===============================================================
Student Name   : Juan Dela Cruz
Total Subjects : 3
---------------------------------------------------------------
Subject                         Grade    Description   Remarks
---------------------------------------------------------------
Programming 2                   1.25     Very Good     PASSED
Data Structures and Algorithms  5.00     Failed        FAILED
Web Systems                     2.00     Good          PASSED
---------------------------------------------------------------
Computed GWA     : 2.75
Subjects Passed  : 2
Subjects Failed  : 1
Academic Standing: Needs Improvement (1 failed subject)
===============================================================
```
