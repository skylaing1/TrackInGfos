<%@ page import="org.example.entities.Mitarbeiter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html data-bs-theme="light" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Kalender - TrackIn</title>
    <link rel="apple-touch-icon" sizes="180x180" href="../resources/img/favicon_io/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../resources/img/favicon_io/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../resources/img/favicon_io/favicon-16x16.png">
    <link rel="manifest" href="../resources/img/favicon_io/site.webmanifest">
    <link rel="stylesheet" href="../resources/lib/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i&amp;display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700&amp;display=swap">
    <link rel="stylesheet" href="../resources/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="../resources/css/style.css">
    <link rel="stylesheet" href="../resources/css/sidebar.css">
    <link rel="stylesheet" href="../resources/lib/js-year-calendar/js-year-calendar.css">
    <link rel="stylesheet" href="../resources/boxicons/css/boxicons.min.css">
    <link rel="stylesheet" href="../resources/css/calendar.css">
    <script src="../resources/lib/js-year-calendar/js-year-calendar.min.js"></script>
    <script src="../resources/lib/js-year-calendar/js-year-calendar.de.js"></script>
    <script>
        var days = <%= request.getAttribute("days") %>;
    </script>
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
                        <a class="selected" href="${pageContext.request.contextPath}/calendar">
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
                        <a href="${pageContext.request.contextPath}/profile">
                            <i class='bx bx-user icon'></i>
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
    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content" style="background: var(--bs-lvl1);">
            <nav class="navbar navbar-expand sticky-top mb-4 topbar static-top navbar-light" style="background: var(--bs-lvl3);">
                <div class="container-fluid"><button class="btn d-md-none rounded-circle me-3 " id="sidebarToggleTop" type="button" ><i class="fas fa-bars"></i></button>
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
                            <div class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" data-bs-toggle="dropdown" href="#"><span class="d-none d-sm-inline text-dark me-2 small"><%=mitarbeiter.getVorname() + " " + mitarbeiter.getName()%></span><img class="border rounded-circle img-profile" src="<%=mitarbeiter.getProfilePicture()%>"></a>
                                <div class="dropdown-menu shadow dropdown-menu-end animated--grow-in"><a class="dropdown-item" href="${pageContext.request.contextPath}/profile"><i class="fas fa-user fa-sm fa-fw me-2 text-gray-400"></i>&nbsp;Profil</a>
                                    <div class="dropdown-divider"></div><a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt fa-sm fa-fw me-2 text-gray-400"></i>&nbsp;Ausloggen</a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="container-fluid">
                <div class="d-sm-flex justify-content-between align-items-center mb-4">
                    <h3 class="text-dark mb-0">Kalender</h3>
                </div>
                <div class="row">
                    <div class="card card-calendar shadow">
                        <div class="card-body">
                            <div id="calendar"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>
</div>
<div id="event-modal" class="modal fade" role="dialog" tabindex="-1">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><svg class="icon icon-tabler-calendar-plus" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round" style="font-size: 34px;">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path><path d="M12.5 21h-6.5a2 2 0 0 1 -2 -2v-12a2 2 0 0 1 2 -2h12a2 2 0 0 1 2 2v5"></path><path d="M16 3v4"></path><path d="M8 3v4"></path><path d="M4 11h16"></path><path d="M16 19h6"></path><path d="M19 16v6"></path>
                </svg>  Tag Bearbeiten</h4><button class="btn-close" type="button" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form id="form">
                        <input name="entry-id" type="hidden">
                        <div class="row">
                            <div class="col">
                                <div class="mb-3"><label class="form-label" for="status"><strong>Status</strong></label><select name="input_status" id="status" class="form-select" required>
                                    <option value="Anwesend" selected>Anwesend</option>
                                    <option value="Krank">Krank</option>
                                    <option value="Urlaub">Urlaub</option>
                                    <option value="Dienstreise">Dienstreise</option>
                                    <option value="Abwesend">Abwesend</option>
                                </select></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="mb-3"><label class="form-label" for="datum"><strong>Datum</strong></label>
                                    <div class="input-group" id="datum"><span class="input-group-text">von:</span><input name="input_datum_von" class="form-control" type="date" required /><span class="input-group-text">bis:</span><input name="input_datum_bis" class="form-control" type="date" required /></div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="mb-3"><label class="form-label form-label" for="notizen"><strong>Notizen</strong></label><textarea name="input_notizen" id="notizen" class="form-control form-control-sm" maxlength="254"></textarea></div>
                            </div>
                        </div>
            <div class="modal-footer"><a class="btn btn-danger" role="button" data-bs-dismiss="modal">Verwerfen</a><input class="btn btn-primary" type="submit" value="Speichern" id="save-event"></div>
                </form>
            </div>
            </div>
        </div>
    </div>
</div>
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

<script src="../resources/lib/bootstrap/bootstrap.min.js"></script>
<script src="../resources/js/calendar.js"></script>
<script src="../resources/js/sidebar.js"></script>
<script src="../resources/js/alertsAndMessages.js"></script>
</body>
</html>