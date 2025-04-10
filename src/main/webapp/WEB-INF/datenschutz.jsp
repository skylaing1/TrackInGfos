<%@ page import="org.example.entities.Mitarbeiter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html data-bs-theme="light" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Datenschutz - TrackIn</title>
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
    <link rel='stylesheet' href="../resources/boxicons/css/boxicons.min.css">
    <%
        String darkMode = (String) request.getAttribute("darkMode");
        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");
    %>
</head>

<body id="page-top" data-bs-theme="<%=darkMode.equals("true") ? "dark" : "light"%>">
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
                        <a href="${pageContext.request.contextPath}/profile">
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
    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content" style="background: var(--bs-lvl1);color: var(--bs-emphasis-color);">
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
                <h3 class="text-dark mb-1" style="font-weight: bold;"> Datenschutzrichtlinie </h3>

            <p><br>Letzte Aktualisierung: 26.02.2024<br><br>Willkommen auf der Website von Trackin. Diese Datenschutzrichtlinie erklärt, wie wir Informationen sammeln, verwenden, teilen und schützen, die im Zusammenhang mit Ihrer Nutzung unserer Website stehen.</p>
            <h4 style="font-weight: bold;">1. Gesammelte Informationen</h4>
            <h5 style="font-weight: bold;">1.1. Personenbezogene Daten</h5>
            <p>Bei der Nutzung unserer Website können wir personenbezogene Daten von Ihnen erfassen, wie z.B. Ihren Namen, Ihre E-Mail-Adresse und Ihren Arbeitsort. Diese Informationen werden nur für interne Zwecke verwendet und nicht ohne Ihre Zustimmung an Dritte weitergegeben.</p>
            <h5 style="font-weight: bold;">1.2. Automatisch gesammelte Informationen</h5>
            <p>Wir können automatisch Informationen über Ihre Interaktion mit unserer Website sammeln, wie z.B. Ihre IP-Adresse, Browsertyp, Betriebssystem und besuchte Seiten. Diese Informationen dienen dazu, die Funktionalität der Website zu verbessern und anonyme statistische Analysen durchzuführen.</p>
            <h4 style="font-weight: bold;">2. Verwendung von Informationen</h4>
            <p>Wir verwenden die gesammelten Informationen, um Ihnen personalisierte Inhalte bereitzustellen, unsere Dienstleistungen zu verbessern und sicherzustellen, dass die Website ordnungsgemäß funktioniert.</p>
            <h4 style="font-weight: bold;">3. Datensicherheit</h4>
            <p>Wir setzen angemessene Sicherheitsmaßnahmen ein, um die Sicherheit Ihrer persönlichen Informationen zu gewährleisten und unbefugten Zugriff, Nutzung oder Offenlegung zu verhindern.</p>
            <h4 style="font-weight: bold;">4. Weitergabe von Informationen</h4>
            <p>Wir geben Ihre personenbezogenen Daten nicht ohne Ihre ausdrückliche Zustimmung an Dritte weiter, es sei denn, dies ist gesetzlich vorgeschrieben.</p>
            <h4 style="font-weight: bold;">5. Cookies</h4>
            <p>Unsere Website verwendet Cookies, um die Benutzerfreundlichkeit zu verbessern. Sie können die Verwendung von Cookies in Ihren Browsereinstellungen deaktivieren, aber dies könnte die Funktionalität der Website beeinträchtigen.</p>
            <h4 style="font-weight: bold;">6. Änderungen an dieser Datenschutzrichtlinie</h4>
            <p>Wir behalten uns das Recht vor, diese Datenschutzrichtlinie jederzeit zu ändern. Änderungen werden auf dieser Seite veröffentlicht.</p>
            <h4 style="font-weight: bold;">7. Kontakt</h4>
            <p>Bei Fragen oder Bedenken bezüglich unserer Datenschutzpraktiken können Sie uns unter&nbsp;<a href="#">Max.Mustermann@Trackin.com</a> erreichen.</p>
        </div>
        </div>

    </div><a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>
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
<script src="../resources/js/sidebar.js"></script>
<script src="../resources/js/alertsAndMessages.js"></script>

</body>

</html>