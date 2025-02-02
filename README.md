# Employee Management System Project
This project is a web-based department management system using Java Servlets and PostgreSQL. It allows users to create, update, delete, and show department records through a user-friendly interface. The backend handles database interactions seamlessly, ensuring data integrity.

# JSP ( Java Server Pages )
JavaServer Pages (JSP) is a server-side technology that allows developers to create dynamic, platform-independent web applications. JSP is part of the Java EE (Enterprise Edition) platform and is an extension of the servlet technology.

<br>

=> download file ( apache-tomcat-10.1.34 )

<br>

# Employee Management System :



``` bash
MyApp
├── src
|   └── main
|       ├── java
|       |   └── com.soumikservlet
|       |        |
|       |        └── LoginServlet.java
|       |        └── RegisterServlet.java
|       └── webapp
|           ├── WEB-INF
|           |   └── web.xml
|           └── index.jsp
|           └── register.html
├── pom.xml

```
#pom.xml

```cs

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.example</groupId>
  <artifactId>MyApp</artifactId>
  <packaging>war</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>MyApp Maven Webapp</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>


    </dependency>

<!---------------------------------------------------------------------------------------------------------------------->

                             <!-- Jakarta Servlet API -->

    <!-- https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api -->
    <dependency>
      <groupId>jakarta.servlet</groupId>
      <artifactId>jakarta.servlet-api</artifactId>
      <version>6.0.0</version>
      <scope>provided</scope>
    </dependency>

                             <!-- PostgreSQL JDBC Driver -->
    
    <!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>42.6.0</version>
    </dependency>

<!-------------------------------------------------------------------------------------------------------------------------->

  </dependencies>


  <build>
    <finalName>MyApp</finalName>
  </build>
</project>

```
#LoginServlet.java

```cs
package com.soumikservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();

        String jdbcURL = "jdbc:postgresql:
        String dbUser = "postgres";
        String dbPassword = "123";

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
                switch (action.toLowerCase()) {
                    case "login":
                        handleLogin(request, connection, response, out);
                        break;
                    case "showall":
                        showAllRecords(connection, out);
                        break;
                    default:
                        out.println("<p>Invalid action</p>");
                }
            }
        } catch (ClassNotFoundException e) {
            out.println("<p>PostgreSQL JDBC Driver not found. Include it in your library path.</p>");
            e.printStackTrace(out);
        } catch (SQLException e) {
            e.printStackTrace(out); // Print error details to the response
        }
        out.close();
    }

    private void handleLogin(HttpServletRequest request, Connection connection, HttpServletResponse response, PrintWriter out) throws SQLException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Hardcoding the user details for login verification
        if ("soumik".equals(username) && "123456789".equals(password)) {
            response.sendRedirect("register.html");
        } else {
            out.println("<p>Invalid username or password.</p>");
        }
    }

    private void showAllRecords(Connection connection, PrintWriter out) throws SQLException {
        String sql = "SELECT * FROM dept";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            out.println("<html><head><title>All Records</title>");
            out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css' rel='stylesheet'/>");
            out.println("<link href='https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap' rel='stylesheet'/>");
            out.println("<style>");
            out.println("body, html { margin: 0; padding: 0; width: 100%; height: 100%; font-family: 'Roboto', sans-serif; display: flex; justify-content: center; align-items: center; background-color: #0b0e14; color: #fff; }");
            out.println(".container { width: 90%; max-width: 800px; padding: 50px; background-color: #1a1d2e; border-radius: 10px; color: #fff; display: flex; flex-direction: column; align-items: center; }");
            out.println(".container h1 { margin: 0; font-size: 28px; font-weight: 700; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println("th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; color: #fff; }");
            out.println("th { background-color: #00aaff; color: white; }");
            out.println("tr:hover { background-color: #f5f5f5; }");
            out.println("tr:nth-child(even) { background-color: #2a2d3e; }");
            out.println("tr:nth-child(odd) { background-color: #1a1d2e; }");
            out.println("</style></head><body>");
            out.println("<div class='container'>");
            out.println("<h1>All Records</h1>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Age</th><th>Gender</th><th>Mobile Number</th></tr>");
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getInt("id") + "</td>");
                out.println("<td>" + resultSet.getString("name") + "</td>");
                out.println("<td>" + resultSet.getInt("age") + "</td>");
                out.println("<td>" + resultSet.getString("gender") + "</td>");
                out.println("<td>" + resultSet.getString("mobilenumber") + "</td>");
                out.println("</tr>");
            }
            out.println("</table></div></body></html>");
        }
    }

}

```
#RegisterServlet.java

```cs
package com.soumikservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String ageStr = request.getParameter("age");
        String gender = request.getParameter("gender");
        String mobilenumber = request.getParameter("mobilenumber");

        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(idStr);

        String jdbcURL = "jdbc:postgresql://localhost:5432/employee";
        String dbUser = "postgres";
        String dbPassword = "123";

        try {
            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
                switch (action.toLowerCase()) {
                    case "create":
                        createRecord(connection, id, name, ageStr, gender, mobilenumber, out);
                        break;
                    case "update":
                        updateRecord(connection, id, name, ageStr, gender, mobilenumber, out);
                        break;
                    case "delete":
                        deleteRecord(connection, id, out);
                        break;
                    case "show":
                        showRecord(connection, id, out);
                        break;
                    default:
                        out.println("<p>Invalid action</p>");
                }
            }
        } catch (ClassNotFoundException e) {
            out.println("<p>PostgreSQL JDBC Driver not found. Include it in your library path.</p>");
            e.printStackTrace(out);
        } catch (SQLException e) {
            e.printStackTrace(out); 
        }
        out.close();
    }

    private void createRecord(Connection connection, int id, String name, String ageStr, String gender, String mobilenumber, PrintWriter out) throws SQLException {
        int age = Integer.parseInt(ageStr);
        String sql = "INSERT INTO dept (id, name, age, gender, mobilenumber) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setInt(3, age);
            statement.setString(4, gender);
            statement.setString(5, mobilenumber);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<p>Record inserted successfully!</p>");
                out.println("<p><strong>ID:</strong> " + id + "</p>");
                out.println("<p><strong>Name:</strong> " + name + "</p>");
                out.println("<p><strong>Age:</strong> " + age + "</p>");
                out.println("<p><strong>Gender:</strong> " + gender + "</p>");
                out.println("<p><strong>Mobile Number:</strong> " + mobilenumber + "</p>");
            } else {
                out.println("<p>Failed to insert the record.</p>");
            }
        }
    }

    private void updateRecord(Connection connection, int id, String name, String ageStr, String gender, String mobilenumber, PrintWriter out) throws SQLException {
        int age = Integer.parseInt(ageStr);
        String sql = "UPDATE dept SET name = ?, age = ?, gender = ?, mobilenumber = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, gender);
            statement.setString(4, mobilenumber);
            statement.setInt(5, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("<p>Record updated successfully!</p>");
                out.println("<p><strong>ID:</strong> " + id + "</p>");
                out.println("<p><strong>Name:</strong> " + name + "</p>");
                out.println("<p><strong>Age:</strong> " + age + "</p>");
                out.println("<p><strong>Gender:</strong> " + gender + "</p>");
                out.println("<p><strong>Mobile Number:</strong> " + mobilenumber + "</p>");
            } else {
                out.println("<p>Failed to update the record.</p>");
            }
        }
    }

    private void deleteRecord(Connection connection, int id, PrintWriter out) throws SQLException {
        String sql = "DELETE FROM dept WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                out.println("<p>Record deleted successfully!</p>");
                out.println("<p><strong>ID:</strong> " + id + "</p>");
            } else {
                out.println("<p>Failed to delete the record.</p>");
            }
        }
    }

    private void showRecord(Connection connection, int id, PrintWriter out) throws SQLException {
        String sql = "SELECT * FROM dept WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<p><strong>ID:</strong> " + resultSet.getInt("id") + "</p>");
                out.println("<p><strong>Name:</strong> " + resultSet.getString("name") + "</p>");
                out.println("<p><strong>Age:</strong> " + resultSet.getInt("age") + "</p>");
                out.println("<p><strong>Gender:</strong> " + resultSet.getString("gender") + "</p>");
                out.println("<p><strong>Mobile Number:</strong> " + resultSet.getString("mobilenumber") + "</p>");
            } else {
                out.println("<p>No record found with ID: " + id + "</p>");
            }
        }
    }
}


```

#index.jsp ---( login html file )

```cs
<html>
 <head>
  <title>
   Login
  </title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&amp;display=swap" rel="stylesheet"/>
  <style>
   body, html {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            font-family: 'Roboto', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #0b0e14;
        }
        .container {
            width: 70%;
            max-width: 350px;
            padding: 50px;
            background-color: #1a1d2e;
            border-radius: 10px;
            color: #fff;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .container h1 {
            margin: 0;
            font-size: 28px;
            font-weight: 700;
        }
        .container form {
            width: 100%;
            margin-top: 30px;
            display: flex;
            flex-direction: column;
        }
        .container form input {
            width: 100%;
            padding: 15px;
            margin-bottom: 20px;
            border: none;
            border-radius: 5px;
            background-color: #2a2d3e;
            color: #fff;
            font-size: 16px;
        }
        .container form input::placeholder {
            color: #6c757d;
        }
        .container form button {
            padding: 15px;
            background-color: #00aaff;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-size: 18px;
            cursor: pointer;
            margin-bottom: 10px;
        }
        .container form button:last-child {
            background-color: #6c757d;
        }
  </style>
 </head>
 <body>
  <div class="container">
   <h1>
    Login
   </h1>
   <form action="login" method="post">
    <input name="username" placeholder="Username" required="" type="text"/>
    <input name="password" placeholder="Password" required="" type="password"/>
    <button name="action" type="submit" value="login">
     Login
    </button>
    <button name="action" type="submit" value="showall">
     Show Records
    </button>
   </form>
  </div>
 </body>
</html>


```

#register.html ---( register html file )

```cs
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Page</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet"/>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            font-family: 'Roboto', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #0b0e14;
        }
        .container {
            width: 90%;
            max-width: 700px;
            padding: 50px;
            background-color: #1a1d2e;
            border-radius: 10px;
            color: #fff;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .container h1 {
            margin: 0;
            font-size: 28px;
            font-weight: 700;
        }
        .form-container {
            width: 100%;
            margin-top: 30px;
            display: flex;
            justify-content: space-between;
        }
        .form-container .input-group, .form-container .button-group {
            width: 48%;
        }
        .form-container .input-group input {
            width: 100%;
            padding: 15px;
            margin-bottom: 20px;
            border: none;
            border-radius: 5px;
            background-color: #2a2d3e;
            color: #fff;
            font-size: 16px;
        }
        .form-container .input-group input::placeholder {
            color: #6c757d;
        }
        .form-container .button-group {
            margin-left: 20px; 
            display: flex;
            flex-direction: column;
            align-items: center; 
        }
        .form-container .button-group button {
            padding: 10px 15px;
            background-color: #00aaff;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-size: 14px;
            cursor: pointer;
            margin-bottom: 10px;
            width: 80%;
        }
        .form-container .button-group button:last-child {
            background-color: #6c757d;
        }
        .form-container form {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Register</h1>
    <div class="form-container">
        <form action="register" method="post">
            <div class="input-group">
                <input name="id" placeholder="ID" required type="text"/>
                <input name="name" placeholder="Name" required type="text"/>
                <input name="age" placeholder="Age" required type="number"/>
                <input name="gender" placeholder="Gender" required type="text"/>
                <input name="mobilenumber" placeholder="Mobile Number" required type="text"/>
            </div>
            <div class="button-group">
                <button name="action" type="submit" value="create">Create</button>
                <button name="action" type="submit" value="update">Update</button>
                <button name="action" type="submit" value="delete">Delete</button>
                <button name="action" type="submit" value="show">Show</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>


```

#web.xml

```cs
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>
</web-app>


```

#Database Table (create database in postgresql) 
#--> *DATABASE NAME=> employee  
#--> *TABLE NAME => dept

```cs
CREATE TABLE dept (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    age INT,
    gender VARCHAR(10),
    mobilenumber VARCHAR(15)
);

```
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# Install Tomcat Server
```cs
Step 1: Install Tomcat Server
Download and Install Tomcat: Download the Tomcat server from the official website and install it on your machine.

Set Up Environment Variables: Set up the CATALINA_HOME and JAVA_HOME environment variables to point to your Tomcat and Java installations, respectively.

Step 2: Install IntelliJ IDEA Plugin
Open IntelliJ IDEA: Launch IntelliJ IDEA.

Install Tomcat Plugin: Go to File > Settings > Plugins. Search for "Tomcat" and install the Tomcat plugin2.

Step 3: Configure Tomcat Server in IntelliJ IDEA
Open Settings: Go to File > Settings.

Navigate to Tomcat: Go to Build, Execution, Deployment > Application Servers.

Add New Server: Click the + button and select Tomcat Server.

Configure Server: Set the Application server to the path where Tomcat is installed.

Save Configuration: Click OK to save the configuration.

Step 4: Create a New Project or Open an Existing One
Create a New Project: Go to File > New > Project and select Web Application.

Open Existing Project: Open your existing project if you already have one.

Step 5: Deploy Your Application to Tomcat
Open Project Structure: Go to File > Project Structure.

Set Deployment Configuration: Go to Project: [Your Project Name] > Project Bases and set the deployment configuration to the Tomcat server you configured.

Deploy Application: Right-click on your project in the Project view and select Run > Run 'Tomcat Server'.

Step 6: Verify Deployment
Start Tomcat Server: Ensure the Tomcat server is running.

Access Application: Open your web browser and navigate to http://localhost:8080/YourAppName to verify that your application is deployed and running correctly.
```

<a id='ssFeatures'>Soumik Mukherjee</a>
