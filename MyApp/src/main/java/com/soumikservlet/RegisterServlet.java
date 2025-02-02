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
        int id = Integer.parseInt(idStr); // Cast id to integer

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
            e.printStackTrace(out); // Print error details to the response
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
