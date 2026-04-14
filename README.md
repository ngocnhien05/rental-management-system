

Rental Management System

Description

A Java desktop application for managing rental rooms, tenants, and rental records.
The system replaces manual tracking methods and provides a simple interface for storing, updating, and searching rental data.



Features
 • Manage rooms (add, update, delete, search)
 • Manage tenants (add, update, delete, search)
 • Manage rental records
 • Search rentals by room or tenant
 • Export rental information to PDF


Technologies
 • Java (Swing)
 • MySQL
 • JDBC


Database Setup
 • Import the provided database.sql file into MySQL
 • Create a database (e.g., nhatro_db) before importing



Configuration

Update database connection in DBConnect.java:

private static String url = "jdbc:mysql://localhost:3306/nhatro_db";
private static String user = "your_username";
private static String password = "your_password";




How to Run
 1. Open the project in Eclipse
 2. Ensure MySQL is running
 3. Configure database connection
 4. Run the MainFrame class


Project Structure
 • model: Data classes (Room, Customer, Rental)
 • dao: Data access layer (CRUD operations)
 • view: User interface (Java Swing)
 • utils: Utility classes (PDF export)


Limitations
 • No authentication or user roles
 • No billing or invoice management
 • No reporting dashboard


Future Improvements
 • Add authentication and user roles
 • Add billing and payment management
 • Export reports (PDF/Excel)
 • Migrate to a web-based system


Author
 • Nguyễn Hoà Ngọc Nhiên
 • Information Technology Student


Built using a layered architecture (Model – DAO – View) following object-oriented design principles.
