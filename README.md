# Library Management System

## Overview
The Library Management System is a web application designed to help manage and track books in a library. It allows users to add, update, and search for books, as well as download book in PDF format.

## Features
- **Add Books**: Add new books to the library database with details such as title, author, genre, and cover image.
- **Update Books**: Update information of existing books.
- **Search Books**: Search for books by title or author.
- **List Books**: Display a list of all books in the library.
- **Download PDF**: Download book information in PDF format.
- **User Authentication**: Ensure secure access to the system with login/logout functionality.

## Technologies Used
- **Java**: Backend logic and servlet implementation.
- **JSP**: For the front-end user interface.
- **PostgreSQL**: Database management system.
- **Bootstrap**: Styling and responsive design.
- **JSTL**: JavaServer Pages Standard Tag Library for dynamic content.

## Setup and Installation
1. **Clone the Repository**:
    ```sh
    git clone https:https://github.com/Prathamesh4545/Library-Management-System
    ```
2. **Database Setup**:
    - Install PostgreSQL and create a database named `practiceDB`.
    - Run the SQL scripts in the `sql` directory to set up the necessary tables.
    - create 1st table `books` where column are
    - `bookId integer`,`name text`,`author text`,`genre text`,`isAvailable`,`img bytea`,`book_pdf bytea`.
    - create 2nd table `users` where column are
    - `id integer`,`name text`,`email varchar(30)`,`phone_number`,`password`.

3. **Configure Database Connection**:
    - Update the database connection details in your JSP and Servlet files if necessary:
    ```java
    String url = "jdbc:postgresql://localhost:5432/practiceDB";
    String username = "postgres";
    String password = "root8080";
    ```

4. **Deploy the Application**:
    - Deploy the application on a web server (e.g., Apache Tomcat).
    - Ensure the server has access to the PostgreSQL database.

5. **Run the Application**:
    - Open a web browser and navigate to the application URL (e.g., `http://localhost:8080/library-management-system`).

## Usage
- **Add a Book**: Navigate to the "Add Book" page, fill in the book details, and submit the form.
- **Update a Book**: Search for a book and use the "Update" button to modify its details.
- **Search for a Book**: Use the search bar to find books by title or author.
- **List Books**: View all books in the library on the main page.
- **Download PDF**: Click the "Download PDF" button to download book information in PDF format.
- **User Authentication**: Log in to access additional features and manage books.

