package webapp.servlet;

import webapp.service.VolumeService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/calculateVolume")
public class VolumeCalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VolumeService volumeService = new VolumeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        double param1 = Double.parseDouble(request.getParameter("param1"));
        double param2 = request.getParameter("param2") != null ? Double.parseDouble(request.getParameter("param2")) : 0;
        String shape = request.getParameter("shape");

        double volume = volumeService.calculateVolume(shape, param1, param2);
        PrintWriter out = response.getWriter();
        out.print(volume);
    }
}
