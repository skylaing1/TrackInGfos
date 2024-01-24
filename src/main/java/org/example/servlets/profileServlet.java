package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.database.LoginDataDAO;
import org.example.entities.Mitarbeiter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@WebServlet(name = "profileServlet", value = "/profile")
@MultipartConfig
public class profileServlet extends HttpServlet {

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");

        HttpSession session = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String extension = "";

        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            extension = originalFileName.substring(i);
        }

        // Append the current timestamp to the filename
        String fileName = "avatar_" + System.currentTimeMillis() + extension;

        String appPath = request.getServletContext().getRealPath("");

        String savePath = appPath + File.separator + "resources" + File.separator + "img" + File.separator + "avatars";

        File fileSaveDir = new File(savePath);

        filePart.write(savePath + File.separator + fileName);

        mitarbeiter.setAvatar(fileName);

        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
    }
}