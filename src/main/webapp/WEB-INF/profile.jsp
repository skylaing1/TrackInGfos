<%@ page import="org.example.entities.Mitarbeiter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html data-bs-theme="light" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Profil - TrackIn</title>
    <link rel="apple-touch-icon" sizes="180x180" href="../resources/img/favicon_io/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../resources/img/favicon_io/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../resources/img/favicon_io/favicon-16x16.png">
    <link rel="manifest" href="../resources/img/favicon_io/site.webmanifest">
    <link rel="stylesheet" href="../resources/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i&amp;display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700&amp;display=swap">
    <link rel="stylesheet" href="../resources/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="../resources/css/style.css">
    <link rel="stylesheet" href="../resources/css/sidebar.css">
    <link rel='stylesheet' href="../resources/boxicons/css/boxicons.min.css">
    <%
        String darkMode = (String) request.getAttribute("darkMode");
        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");
    %>
</head>

<body id="page-top" data-bs-theme="<%=darkMode.equals("true") ? "dark" : "light"%>">

<c:if test="${alert != null}">
    <div class="alert alertnew alert-${alert.alertType} alert-dismissible fade show" role="alert" style="position: fixed;max-width: 450px;">
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        <h4 class="alert-heading"><strong><i class="fa fa-${alert.alertIcon}"></i>${alert.alertTitle}</strong></h4>
        <hr><p class="mb-0">${alert.alertMessage}</p>
    </div>
</c:if>
<div id="wrapper">
    <nav class="sidebarnew close">
        <header>
            <div class="image-text">
            <span class="image">
               <img src="../resources/img/staticpictures/sidebar_logo.png">
            </span>
                <div class="text logo-text">
                    <span class="name">TrackIn</span>
                    <span class="profession">Zeiterfassung
                </span>
                </div>
            </div>
            <i class='bx bx-chevron-right toggle'></i>
        </header>
        <div class="menu-bar">
            <div class="menu">
                <ul class="menu-links">
                    <li class="nav-link">
                        <a href="${pageContext.request.contextPath}/dashboard">
                            <i class='bx bx-home-alt icon'></i>
                            <span class="text nav-text">Dashboard</span>
                        </a>
                    </li>
                    <li class="nav-link">
                        <a href="${pageContext.request.contextPath}/calendar">
                            <i class='bx bx-calendar icon'></i>
                            <span class="text nav-text">Kalender</span>
                        </a>
                    </li>
                    <li class="nav-link">
                        <a href="${pageContext.request.contextPath}/messages">
                            <i class='bx bx-bell icon'></i>
                            <span class="text nav-text">Nachrichten</span>
                        </a>
                    </li>
                    <li class="nav-link">
                        <a class="selected" href="${pageContext.request.contextPath}/profile">
                            <i class='bx bx-user icon' ></i>
                            <span class="text nav-text">Profil</span>
                        </a>
                    </li>
                   <c:if test="<%=mitarbeiter.getAdmin()%>">
                        <li class="nav-link">
                            <a href="${pageContext.request.contextPath}/managment">
                                <i class='bx bx-group icon' ></i>
                                <span class="text nav-text">Mitarbeiter</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <div class="bottom-content">
                <li>
                    <a href="${pageContext.request.contextPath}/logout">
                        <i class='bx bx-log-out icon' ></i>
                        <span class="text nav-text">Ausloggen</span>
                    </a>
                </li>
                <li class="mode">
                    <div class="sun-moon">
                        <i class='bx bx-moon icon moon'></i>
                        <i class='bx bx-sun icon sun'></i>
                    </div>
                    <span class="mode-text text">Dark mode</span>
                    <div class="toggle-switch" id="darkModeSwitch">
                        <span class="switch"></span>
                    </div>
                </li>
            </div>
        </div>
    </nav>
    <div class="d-flex flex-column" id="content-wrapper" style="background: var(--body-color);">
        <div id="content" style="background: var(--bs-lvl1);">
            <nav class="navbar navbar-expand sticky-top mb-4 topbar static-top navbar-light" style="background: var(--bs-lvl3);">
                <div class="container-fluid"><button class="btn d-md-none rounded-circle me-3 " id="sidebarToggleTop" type="button" onclick="mobileToggle"><i class="fas fa-bars"></i></button>
                    <ul class="navbar-nav flex-nowrap ms-auto" style="color: rgb(221, 223, 235);">
                        <li class="nav-item dropdown no-arrow mx-1">
                            <div class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" data-bs-toggle="dropdown" href="#" id="messageDropdown"><span class="badge bg-danger badge-counter" id="messageCount">${messageCount}</span><i class="fas fa-bell fa-fw" style="color: #a7a7a7;"></i></a>
                                <div class="dropdown-menu dropdown-menu-end dropdown-list animated--grow-in">
                                    <h6 class="dropdown-header">Benachrichtigungen</h6>
                                    <c:forEach items="${messages}" var="message">
                                        <a class="dropdown-item d-flex align-items-center" href="#">
                                            <div class="me-3">
                                                <div class="bg-${message.status} icon-circle"><i class="fas ${message.icon} text-white"></i></div>
                                            </div>
                                            <div><span class="small text-gray-500">${message.messageDateFormatted}</span>
                                                <p>${message.message}</p>
                                            </div>
                                        </a>
                                    </c:forEach>
                                    </a><a class="dropdown-item text-center small text-gray-500" href="${pageContext.request.contextPath}/messages">Zeige Alle Benachrichtigungen</a>
                                </div>
                            </div>
                        </li>
                        <div class="d-none d-sm-block topbar-divider"></div>
                        <li class="nav-item dropdown no-arrow">
                            <div class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" data-bs-toggle="dropdown" href="#"><span class="d-none d-lg-inline text-dark me-2 small"><%=mitarbeiter.getVorname() + " " + mitarbeiter.getName()%></span><img class="border rounded-circle img-profile" src="<%=mitarbeiter.getProfilePicture()%>" alt=""></a>
                                <div class="dropdown-menu shadow dropdown-menu-end animated--grow-in"><a class="dropdown-item" href="${pageContext.request.contextPath}/profile"><i class="fas fa-user fa-sm fa-fw me-2 text-gray-400"></i>&nbsp;Profie</a>
                                    <div class="dropdown-divider"></div><a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt fa-sm fa-fw me-2 text-gray-400"></i>&nbsp;Logout</a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="container-fluid">
                <h3 class="text-dark mb-4">Profil</h3>
                <div class="row mb-3">
                    <div class="col-lg-4">
                        <div class="card mb-3">
                            <div class="card-body text-center shadow"><img id="profile-image" class="rounded-circle mb-3 mt-4" src="<%=mitarbeiter.getProfilePicture()%>" width="160" height="160" alt="">
                                <form action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
                                    <div class="mb-3">
                                        <input type="file" name="file" id="image-input" style="display:none;" accept="image/*">
                                        <button class="btn btn-primary btn-sm" type="button" onclick="changeImage()">Ändern</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                            <div class="col">
                                <div class="card shadow mb-3">
                                    <div class="card-header py-3" style="color: rgb(221, 223, 235);background: var(--bs-card-bg);">
                                        <p class="text-primary m-0 fw-bold">Stammdaten</p>
                                    </div>
                                    <div class="card-body">
                                        <form action="${pageContext.request.contextPath}/stammdaten" method="post">
                                            <div class="row">
                                                <div class="col">
                                                    <div class="mb-3"><label class="form-label" for="email"><strong>E-mail Adresse</strong></label><input class="form-control" type="email" id="email" placeholder="benutzer@beispiel.com" name="email" value="<%=mitarbeiter.getLoginData().getEmail()%>" required=""></div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="mb-3"><label class="form-label" for="password"><strong>Aktuelles Passwort</strong></label><input class="form-control" name="input_password_old" type="password" id="password" placeholder="Passwort" required=""></div>
                                                </div>
                                                <div class="col">
                                                    <div class="mb-3"><label class="form-label" for="password1"><strong>Neues Passwort</strong></label><input class="form-control" name="input_password_new" type="password" id="password1" minlength="8" placeholder="Passwort"></div>
                                                </div>
                                            </div>
                                            <div class="mb-3"><button class="btn btn-primary btn-sm" type="submit">Speichern</button></div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div><a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>
<footer class="text-center" style="background: #3b3b3b;">
    <div class="container py-4 py-lg-5">
        <ul class="list-inline">
            <li class="list-inline-item me-4"><a class="link-light" href="${pageContext.request.contextPath}/impressum" target="_blank">Impressum</a>
            </li>
            <li class="list-inline-item me-4"><a class="link-light" href="${pageContext.request.contextPath}/datenschutz" target="_blank">Datenschutz</a>
            </li>
        </ul>
        <ul class="list-inline">
            <li class="list-inline-item me-4">
                <i  class='bx bxl-facebook' style="font-size: 20px;"></i>
            </li>
            <li class="list-inline-item me-4">
                <i  class='bx bxl-twitter' style="font-size: 20px;"></i>
            </li>
            <li class="list-inline-item">
                <i  class='bx bxl-instagram' style="font-size: 20px;"></i>
            </li>
        </ul>
        <p class="text-muted mb-0">Copyright © 2024 TrackIn</p>
    </div>
</footer>
<script>
        function changeImage() {
            var fileInput = document.getElementById('image-input');
            var form = fileInput.form;

            // öffne das Datei-Dialogfenster
            fileInput.click();

            // Warte bis ein Bild ausgewählt wurde
            fileInput.addEventListener('change', function (e) {
                var file = e.target.files[0];

                // Wenn ein Bild ausgewählt wurde
                if (file) {
                    var reader = new FileReader();

                    // Warte bis das Bild geändert wurde
                    reader.onload = function (e) {
                        // ändere das Bild auf der Seite
                        document.getElementById('profile-image').src = e.target.result;

                        e.preventDefault();
                        form.submit();

                        fileInput.value = '';
                    };

                    reader.readAsDataURL(file);
                }
            });
        }
</script>
<script src="../resources/bootstrap/bootstrap.min.js"></script>
<script src="../resources/js/sidebar.js"></script>
<script src="../resources/js/alertsAndMessages.js"></script>
</body>
</html>