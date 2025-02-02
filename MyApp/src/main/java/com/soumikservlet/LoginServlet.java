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

        String jdbcURL = "jdbc:postgresql://localhost:5432/employee";
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
