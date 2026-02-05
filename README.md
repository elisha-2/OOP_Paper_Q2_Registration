OOP Paper – Question 2 (Student Registration Form – Java Swing)

This repository contains the solution for QUESTION TWO of the OOP coursework paper.

It is a Java Swing Desktop Application that replicates a New Student Registration Form and includes full validation, DOB logic, record display, and CSV saving.


Project Details

\- Language: Java

\- IDE: Apache NetBeans IDE 25

\- GUI Framework: Swing

\- Package Name: oop.paper.q2

\- Data Storage: students.csv

Features Implemented

**Form Inputs**

\- First Name / Last Name

\- Email / Confirm Email

\- Password / Confirm Password

\- DOB Combo Boxes: Year, Month, Day  

\- Day count auto-updates based on month/year (leap years supported)

\- Gender (Male/Female) using radio buttons (single-select)

\- Department Combo Box:

\- Civil, CSE, Electrical, E\&C, Mechanical

\- Submit and Cancel buttons

\- Read-only output area labeled: **"Your Data is Below:"**


**Validation Rules**

\- All fields are required and trimmed

\- Email must be valid and match Confirm Email

\- Password must:

&nbsp; - Be 8–20 characters

&nbsp; - Contain at least one letter

&nbsp; - Contain at least one digit

&nbsp; - Match Confirm Password

\- Age calculated from DOB must be 16–60 inclusive

\- Exactly one gender must be selected

\- Department must be selected



Record Output Format

When valid, a record is displayed and saved in the format:


ID: 2026-00002 | Expert\_Me 256 | M | Electrical | 2003-06-17 | expertme256@gmail.com



CSV Saving


\- Records are appended to students.csv

\- The file is created automatically if missing

\- Student IDs are generated as: YYYY-000xx



Where 000xx is a per-year counter (e.g., 00001, 00002, 00003…)



**How to Run (NetBeans 25)**

1\. Open Apache NetBeans IDE 25

2\. Click File - Open Project

3\. Select the folder: OOP\_Paper\_Q2\_Registration

4\. Expand:

&nbsp;  Source Packages - oop.paper.q2

5\. Right-click:

&nbsp;  StudentRegistrationForm.java

6\. Click Run File



Files Included


\- StudentRegistrationForm.java: Main Swing GUI and validation logic.

\- StudentCsvService.java: Handles ID generation and appending records to students.csv.



Notes


\- The generated `students.csv` file will appear in the project folder after the first successful submission.

\- The application supports leap years and correct day counts per month.


Author: expertme256
Student Coursework Submission (OOP Paper)