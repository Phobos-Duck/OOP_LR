package webapp.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import webapp.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    private File dataFile;

    @Override
    public void init() {
        String path = getServletContext().getRealPath("/WEB-INF/data/books.json");
        dataFile = new File(path);

        if (!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(dataFile), StandardCharsets.UTF_8)) {
                    gson.toJson(new ArrayList<>(), writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Book> books = readBooksFromFile();

        if ("true".equals(req.getParameter("ajax"))) {
            resp.setContentType("application/json; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            gson.toJson(books, resp.getWriter());
        } else {
            req.setAttribute("books", books);
            req.getRequestDispatcher("/books.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Book book = gson.fromJson(req.getReader(), Book.class);
        List<Book> books = readBooksFromFile();
        books.add(book);
        writeBooksToFile(books);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private List<Book> readBooksFromFile() {
        try (Reader reader = new InputStreamReader(new FileInputStream(dataFile), StandardCharsets.UTF_8)) {
            Type listType = new TypeToken<List<Book>>() {}.getType();
            List<Book> books = gson.fromJson(reader, listType);
            return books != null ? books : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeBooksToFile(List<Book> books) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(dataFile), StandardCharsets.UTF_8)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}