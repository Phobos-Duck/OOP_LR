package webapp.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.Book;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    @Override
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            File envFile = new File(System.getProperty("user.dir"), ".env");
            if (!envFile.exists()) {
                throw new FileNotFoundException(".env файл не найден: " + envFile.getAbsolutePath());
            }

            Properties props = new Properties();
            try (InputStream input = new FileInputStream(envFile)) {
                props.load(input);
                dbUrl = props.getProperty("DB_URL");
                dbUser = props.getProperty("DB_USER");
                dbPassword = props.getProperty("DB_PASSWORD");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Book> books = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("level"),
                        rs.getString("topic")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(gson.toJson(books));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Book book = gson.fromJson(req.getReader(), Book.class);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO books (title, author, year, level, topic) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYear());
            stmt.setString(4, book.getLevel());
            stmt.setString(5, book.getTopic());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {

            stmt.setInt(1, Integer.parseInt(idParam));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Book book = gson.fromJson(req.getReader(), Book.class);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE books SET title = ?, author = ?, year = ?, level = ?, topic = ? WHERE id = ?")) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYear());
            stmt.setString(4, book.getLevel());
            stmt.setString(5, book.getTopic());
            stmt.setInt(6, book.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}