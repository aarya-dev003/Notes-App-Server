# Note App with Ktor and PostgreSQL

This project is a simple Note App developed using the Ktor framework for building asynchronous servers and PostgreSQL for database management. The app includes JWT authentication and supports CRUD operations on user notes.

## Features

- **JWT Authentication**: Users can securely authenticate themselves using JSON Web Tokens (JWT).
- **CRUD Operations**: Users can perform Create, Read, Update, and Delete operations on their notes.
- **Secure Data Storage**: User notes are securely stored in a PostgreSQL database.

## Technologies Used

- **Ktor**: Ktor is a framework for building asynchronous servers and clients in connected systems using Kotlin.
- **PostgreSQL**: PostgreSQL is a powerful, open-source relational database management system.
- **JWT**: JSON Web Tokens (JWT) are used for secure authentication.

## Getting Started

To run this project locally, follow these steps:

1. **Clone the repository**:

    ```bash
    git clone https://github.com/your-username/note-app.git
    cd note-app
    ```

2. **Set up PostgreSQL database**:

    Create a PostgreSQL database and update the database URL in the `application.conf` file. Set the `JDBC_DRIVER` environment variable to the JDBC driver for PostgreSQL.

3. **Set up environment variables**:

    Export the following environment variables:
    - `JWT_SECRET`: Secret key for JWT authentication.
    - `HASH_SECRET_KEY`: Secret key for hashing passwords.
    - `DATABASE_URL`: URL of the PostgreSQL database.
    - `JDBC_DRIVER`: JDBC driver class name for PostgreSQL.

4. **Build and run the project**:

    Use the following command to build and run the project:

    ```bash
    ./gradlew run
    ```

5. **Access the app**:

    Once the server is up and running, you can access the app at `http://localhost:8080`.

## API Endpoints

The following endpoints are available:

- **Authentication**:
  - `POST /login`: Authenticates a user and returns a JWT token.
  - `POST /register`: Registers a new user.

- **Note Operations**:
  - `GET /notes/get`: Retrieves all notes belonging to the authenticated user.
  - `POST /notes/create`: Creates a new note.
  - `PUT /notes/update`: Updates an existing note.
  - `DELETE /notes/delete/{id}`: Deletes a note.

## Configuration

You can configure the application through the `application.conf` file. Update the database connection settings and any other necessary configurations there.

## Dependencies

- Ktor: [GitHub](https://github.com/ktorio/ktor)
- PostgreSQL JDBC Driver: [Maven Repository](https://mvnrepository.com/artifact/org.postgresql/postgresql)
- Kotlinx Serialization: [GitHub](https://github.com/Kotlin/kotlinx.serialization)
- JWT: [GitHub](https://github.com/auth0/java-jwt)
