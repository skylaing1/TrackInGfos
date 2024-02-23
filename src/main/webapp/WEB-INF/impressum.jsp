<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-bs-theme="light" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Impressum - TrackIn</title>
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
</head>

<body id="page-top" style="background: var(--bs-lvl1);">
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
        <div id="content" style="background: var(--bs-lvl1);">
            <nav class="navbar navbar-expand sticky-top mb-4 topbar static-top navbar-light" style=" ;background: var(--bs-lvl3);">
                <div class="container-fluid"><button class="btn d-md-none rounded-circle me-3 " id="sidebarToggleTop" type="button" onclick="mobileToggle"><i class="fas fa-bars"></i></button>
                    <form class="d-none d-sm-inline-block me-auto ms-md-3 my-2 my-md-0 mw-100 navbar-search">
                        <div class="input-group" style="border-width: 1px;"><input class="form-control border-0 small" type="text" placeholder="Suche  ..."><button class="btn btn-primary py-0" type="button"><i class="fas fa-search"></i></button></div>
                    </form>
                    <ul class="navbar-nav flex-nowrap ms-auto" style="color: rgb(221, 223, 235);">
                        <li class="nav-item dropdown d-sm-none no-arrow"><a class="dropdown-toggle nav-link" aria-expanded="false" data-bs-toggle="dropdown" href="#"><i class="fas fa-search"></i></a>
                            <div class="dropdown-menu dropdown-menu-end p-3 animated--grow-in" aria-labelledby="searchDropdown">
                                <form class="me-auto navbar-search w-100">
                                    <div class="input-group"><input class="bg-light form-control border-0 small" type="text" placeholder="Search for ...">
                                        <div class="input-group-append"><button class="btn btn-primary py-0" type="button"><i class="fas fa-search"></i></button></div>
                                    </div>
                                </form>
                            </div>
                        </li>
                        <li class="nav-item dropdown no-arrow mx-1">
                            <div class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" aria-expanded="false" data-bs-toggle="dropdown" href="#"><span class="badge bg-danger badge-counter">3+</span><i class="fas fa-bell fa-fw" style="color: #a7a7a7;"></i></a>
                                <div class="dropdown-menu dropdown-menu-end dropdown-list animated--grow-in">
                                    <h6 class="dropdown-header">alerts center</h6><a class="dropdown-item d-flex align-items-center" href="#">
                                    <div class="me-3">
                                        <div class="bg-primary icon-circle"><i class="fas fa-file-alt text-white"></i></div>
                                    </div>
                                    <div><span class="small text-gray-500">December 12, 2019</span>
                                        <p>A new monthly report is ready to download!</p>
                                    </div>
                                </a><a class="dropdown-item d-flex align-items-center" href="#">
                                    <div class="me-3">
                                        <div class="bg-success icon-circle"><i class="fas fa-donate text-white"></i></div>
                                    </div>
                                    <div><span class="small text-gray-500">December 7, 2019</span>
                                        <p>$290.29 has been deposited into your account!</p>
                                    </div>
                                </a><a class="dropdown-item d-flex align-items-center" href="#">
                                    <div class="me-3">
                                        <div class="bg-warning icon-circle"><i class="fas fa-exclamation-triangle text-white"></i></div>
                                    </div>
                                    <div><span class="small text-gray-500">December 2, 2019</span>
                                        <p>Spending Alert: We've noticed unusually high spending for your account.</p>
                                    </div>
                                </a><a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
                                </div>
                            </div>
                        </li>
                        <div class="d-none d-sm-block topbar-divider"></div>
                        <li class="nav-item dropdown no-arrow">
                            <div class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" aria-expanded="false" data-bs-toggle="dropdown" href="#"><span class="d-none d-lg-inline me-2 text-gray-600 small" style="color: rgb(0,27,232);"><%=mitarbeiter.getVorname() + " " + mitarbeiter.getName()%></span><img class="border rounded-circle img-profile" src="img/avatars/avatar1.jpeg"></a>
                                <div class="dropdown-menu shadow dropdown-menu-end animated--grow-in"><a class="dropdown-item" href="#"><i class="fas fa-user fa-sm fa-fw me-2 text-gray-400"></i>&nbsp;Profile</a><a class="dropdown-item" href="#"><i class="fas fa-cogs fa-sm fa-fw me-2 text-gray-400"></i>&nbsp;Settings</a><a class="dropdown-item" href="#"><i class="fas fa-list fa-sm fa-fw me-2 text-gray-400"></i>&nbsp;Activity log</a>
                                    <div class="dropdown-divider"></div><a class="dropdown-item" href="#"><i class="fas fa-sign-out-alt fa-sm fa-fw me-2 text-gray-400"></i>&nbsp;Logout</a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="container-fluid" style="color: var(--headername-colour);text-align: left;">
                <h3 class="text-dark mb-1" style="font-weight: bold;">Impressum</h3>
                <h5 style="font-weight: bold;">Verantwortlich für den Inhalt:</h5>
                <p>TrackIn GmbH<br>Dahnstraße 50&nbsp;<br>45144 Essen<br>Germany<br><svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 24 24" width="1em" fill="currentColor">
                    <path d="M0 0h24v24H0z" fill="none"></path>
                    <path d="M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z"></path>
                </svg>&nbsp;TrackIn@Impressum.com<br><svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 24 24" width="1em" fill="currentColor">
                    <path d="M0 0h24v24H0z" fill="none"></path>
                    <path d="M6.62 10.79c1.44 2.83 3.76 5.14 6.59 6.59l2.2-2.2c.27-.27.67-.36 1.02-.24 1.12.37 2.33.57 3.57.57.55 0 1 .45 1 1V20c0 .55-.45 1-1 1-9.39 0-17-7.61-17-17 0-.55.45-1 1-1h3.5c.55 0 1 .45 1 1 0 1.25.2 2.45.57 3.57.11.35.03.74-.25 1.02l-2.2 2.2z"></path>
                </svg>&nbsp;+49 1760 00000</p><iframe allowfullscreen="" frameborder="0" loading="lazy" src="https://www.google.com/maps/embed/v1/place?key=AIzaSyBqi2UDUeuiBSz1JtItC9Bnzt9jmk8k_po&amp;q=Dahnstra%C3%9Fe+50&amp;zoom=17" width="100%" height="400" style="padding-right: 593px;"></iframe>
                <h5 style="font-weight: bold;">Vertretungsberechtigte Person:</h5>
                <p>Geschäftsführer: Max Mustermann</p>
                <h5 style="font-weight: bold;">Umsatzsteuer-Identifikationsnummer:</h5>
                <p>VAT-ID/USt-IdNr.: DE XXXXXX.</p>
                <h5 style="font-weight: bold;">Urheberrecht und Haftungshinweis:</h5>
                <p>Die Inhalte dieser Website sind urheberrechtlich geschützt. Alle Rechte für den Inhalt und die Gestaltung stehen TrackIn GmbH zu. Eine Verwendung, Vervielfältigung oder Verbreitung der Inhalte ist ohne ausdrückliche Zustimmung von TrackIn GmbH nicht gestattet.</p>
                <h5 style="font-weight: bold;">Haftungshinweis:</h5>
                <p>Trotz sorgfältiger inhaltlicher Kontrolle übernehmen wir keine Haftung für die Inhalte externer Links. Für den Inhalt der verlinkten Seiten sind ausschließlich deren Betreiber verantwortlich.</p>
                <h5 style="font-weight: bold;">Datenschutz:</h5>
                <p>Informationen zum Datenschutz findest du in unserer <a href="${pageContext.request.contextPath}/datenschutz">Datenschutzrichtlinie</a>.</p>
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
    </div><a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>
</div>
<script src="../resources/bootstrap/bootstrap.min.js"></script>
<script src="../resources/js/sidebar.js"></script>
</body>

</html>