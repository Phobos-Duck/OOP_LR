package webapp.servlet;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private String dbUrl;
    private final Gson gson = new Gson();

    @Override
    public void init() {
        try {
            // Загрузка драйвера SQLite
            Class.forName("org.sqlite.JDBC");

            // Получаем абсолютный путь до базы данных внутри проекта
            dbUrl = "jdbc:sqlite:books.sqlite";

            // Инициализация таблицы, если она не существует
            try (Connection conn = DriverManager.getConnection(dbUrl);
                 Statement stmt = conn.createStatement()) {

                stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "title TEXT NOT NULL," +
                        "author TEXT NOT NULL," +
                        "year INTEGER NOT NULL," +
                        "level TEXT NOT NULL," +
                        "topic TEXT NOT NULL)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Получение всех книг — JSON-массив
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Book> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            while (rs.next()) {
                Book b = new Book();
                b.title = rs.getString("title");
                b.author = rs.getString("author");
                b.year = rs.getInt("year");
                b.level = rs.getString("level");
                b.topic = rs.getString("topic");
                books.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(books));
    }

    // Добавление книги
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Book newBook = gson.fromJson(req.getReader(), Book.class);

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO books (title, author, year, level, topic) VALUES (?, ?, ?, ?, ?)")) {

            ps.setString(1, newBook.title);
            ps.setString(2, newBook.author);
            ps.setInt(3, newBook.year);
            ps.setString(4, newBook.level);
            ps.setString(5, newBook.topic);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // Вложенный класс модели книги
    static class Book {
        String title;
        String author;
        int year;
        String level;
        String topic;
    }
}