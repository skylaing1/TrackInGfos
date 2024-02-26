package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.Alert;
import org.example.database.MitarbeiterTransaction;
import org.example.entities.Mitarbeiter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet(name = "profileServlet", value = "/profile")
@MultipartConfig
public class profileServlet extends HttpServlet {

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Alert alert = (Alert) session.getAttribute("alert");
        session.removeAttribute("alert");
        if (alert != null) {
            request.setAttribute("alert", alert);
        }
        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        Part filePart = request.getPart("file");

        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String extension = "";

        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            extension = originalFileName.substring(i);
        }

        //Unique filename um Überschreiben zu vermeiden und Errors
        String fileName = "avatar_" + System.currentTimeMillis() + extension;

        String appPath = request.getServletContext().getRealPath("");

        String savePath = appPath + File.separator + "resources" + File.separator + "img" + File.separator + "avatars";
        filePart.write(savePath + File.separator + fileName);

        MitarbeiterTransaction.updateProfilePicture(mitarbeiter, fileName, appPath);

        String filePath = "../resources/img/avatars/" + fileName;

        mitarbeiter.setProfilePicture(filePath);

        session.setAttribute("SessionMitarbeiter", mitarbeiter);

        Alert alert = Alert.successAlert("Profilbild erfolgreich geändert", "Ihr Profilbild wurde erfolgreich geändert.");


        request.setAttribute("alert", alert);
        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);

    }
}