package org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.entities.Mitarbeiter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@WebServlet(name = "saveProfilePictureServlet", value = "/saveProfilePicture")
@MultipartConfig
public class saveProfilePictureServlet extends HttpServlet {

}