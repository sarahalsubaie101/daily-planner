## Description 
The Daily Planner System is a web-based application designed to help users organize and 
manage their daily activities efficiently. The system allows users to create, edit, delete, and view 
tasks, as well as categorize them for better organization. It provides a simple, user-friendly 
interface that supports clear task visualization and smooth interaction.


## Daily Planner Features
- User Authentication (Log in/Sign up): Implement user registration and login functionality to ensure secure access to the system.
- Task Management (User Module): Provide users with the ability to create, view, update, and delete tasks. Each task includes a title, 
due date, and optional details such as location and description.
- Administration Module: Enable the administrator to manage users and their tasks. The admin can view user accounts, 
add/edit/delete tasks for any user and remove user accounts if necessary.

## Tools Used
- Netbeans (IDE)
- MySQL Workbench (for database)
- Java Swing (for Framework)
- JDBC (for Database Connectivity)
- JUnit for testing
- Cipher for user password encryption


## Usage
#### User
* Log in using valid credentials to access the personal task dashboard.
* Log out securely to end the session.
* Create a new task by entering Title, Date/Time, and Category.
* Edit an existing task, including updating Title, Date/Time, and Category.
* Delete any personal task permanently.
* Mark a task as completed and update its status.
* View all personal tasks in an organized list.
* Receive error messages for missing required fields (Title, Date/Time, Category).
* Receive confirmation messages after adding, deleting, or completing a task
#### Admin
* Log in using admin credentials. (Note: the login credentials for Admin has been predetermined.)
* View all registered users in the system.
* Access all tasks created by any user.
* Create tasks for any user when needed.
* Edit any user’s task, including Title, Date/Time, and Category.
* Delete tasks from any user’s list.
* Manage user accounts by creating, updating, or deleting users.
* Monitor system activity, including the status of tasks across all users.

## Project Structure
Some of the important files in the project:
* DatabaseConnection: to connect the program with the database
* PasswordEncryptor: to implement password encryption
* App: which runs all the interfaces designed in netbeans
* RegisterFrameTest: tests the program using JUnit


##### Note: this was from a student group project which I took part in, solely shared for educational purposes.


