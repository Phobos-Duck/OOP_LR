package webapp.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private File dataFile;

    @Override
    public void init() {
        String path = getServletContext().getRealPath("/WEB-INF/data/books.json");
        dataFile = new File(path);

        if (!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
                try (Writer writer = new FileWriter(dataFile)) {
                    gson.toJson(new ArrayList<>(), writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Book> books = readBooksFromFile();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(books));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Book newBook = gson.fromJson(req.getReader(), Book.class);
        List<Book> books = readBooksFromFile();

        books.add(newBook);
        writeBooksToFile(books);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private List<Book> readBooksFromFile() {
        try (Reader reader = new FileReader(dataFile)) {
            Type listType = new TypeToken<List<Book>>() {}.getType();
            List<Book> books = gson.fromJson(reader, listType);
            return books != null ? books : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeBooksToFile(List<Book> books) {
        try (Writer writer = new FileWriter(dataFile)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static class Book {
        String title;
        String author;
        int year;
        String level;
        String topic;
    }
}