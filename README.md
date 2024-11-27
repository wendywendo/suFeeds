# SU Feeds

SU Feeds is a Java-based desktop application designed to manage user interactions such as login, signup, unit management, and weekly updates within an academic setting. The application uses the Swing framework for its graphical user interface (GUI) and JDBC for database connectivity.

## Functions

The application allows:

1. A student to have an account and save the classes that they are attending that semester.
2. The student to add topics learned every week for each class.
3. The student to upload comments on the topics learned every week, which can be viewed by other students using the app.

## Requirements

- Java 8 or later
- Swing Framework (included in JDK)
- MySQL Database

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/wendywendo/suFeeds.git
   cd repository-name
   ```

2. **Database Setup**:
   - Locate the `setup.mysql` file in the repository.
   - Import this file into your MySQL database to create the necessary tables and schema.

3. **Configuration**:
   - Create a `config.properties` file in the root directory with the following properties:
     ```properties
     DATABASE_URL=jdbc:mysql://localhost:3306/your_database_name
     USER=your_username
     PASSWORD=your_password
     ```
   - Replace `your_database_name`, `your_username`, and `your_password` with your actual MySQL credentials.

4. **Run the Application**:
   - Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
   - Run the `main` method in `Main.java`.

## Project Structure

- **LoginFrame.java**:
  - Handles user login functionality.

- **SignupFrame.java**:
  - Manages user registration.

- **UnitsFrame.java**:
  - Enables users to view, add, and delete units.

- **Main.java**:
  - Manages weekly topics and comment submissions.

## Author

- **Wendy Wendo Ochieng**  

## License

This project is licensed under the MIT License. See the LICENSE file for details.
