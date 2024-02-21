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
    <link rel="stylesheet" href="../resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i&amp;display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700&amp;display=swap">
    <link rel="stylesheet" href="../resources/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="../resources/css/style.css">
    <link rel="stylesheet" href="../resources/css/sidebar.css">
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
</head>

<body id="page-top" style="background: var(--bs-lvl1);">
<div id="wrapper">
    <nav class="sidebarnew close">
        <header>
            <div class="image-text">
            <span class="image">
               <img src="../resources/img/sidebar_logo.png">
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
                    <c:if test="${mitarbeiter.getAdmin() == true}">
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
            <div class="container-fluid">
                <h3 class="text-dark mb-1" style="font-weight: bold;"> Datenschutzrichtlinie </h3>
            </div>
            <p><br>Letzte Aktualisierung: 06.01.2024<br><br>Willkommen auf der Website von Trackin. Diese Datenschutzrichtlinie erklärt, wie wir Informationen sammeln, verwenden, teilen und schützen, die im Zusammenhang mit Ihrer Nutzung unserer Website stehen.</p>
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
                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                     viewBox="0 0 16 16" class="bi bi-facebook text-light">
                    <path d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951"></path>
                </svg>
            </li>
            <li class="list-inline-item me-4">
                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                     viewBox="0 0 16 16" class="bi bi-twitter text-light">
                    <path d="M5.026 15c6.038 0 9.341-5.003 9.341-9.334 0-.14 0-.282-.006-.422A6.685 6.685 0 0 0 16 3.542a6.658 6.658 0 0 1-1.889.518 3.301 3.301 0 0 0 1.447-1.817 6.533 6.533 0 0 1-2.087.793A3.286 3.286 0 0 0 7.875 6.03a9.325 9.325 0 0 1-6.767-3.429 3.289 3.289 0 0 0 1.018 4.382A3.323 3.323 0 0 1 .64 6.575v.045a3.288 3.288 0 0 0 2.632 3.218 3.203 3.203 0 0 1-.865.115 3.23 3.23 0 0 1-.614-.057 3.283 3.283 0 0 0 3.067 2.277A6.588 6.588 0 0 1 .78 13.58a6.32 6.32 0 0 1-.78-.045A9.344 9.344 0 0 0 5.026 15"></path>
                </svg>
            </li>
            <li class="list-inline-item">
                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                     viewBox="0 0 16 16" class="bi bi-instagram text-light">
                    <path d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.917 3.917 0 0 0-1.417.923A3.927 3.927 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.916 3.916 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.926 3.926 0 0 0-.923-1.417A3.911 3.911 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0h.003zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599.28.28.453.546.598.92.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.47 2.47 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.478 2.478 0 0 1-.92-.598 2.48 2.48 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233 0-2.136.008-2.388.046-3.231.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92.28-.28.546-.453.92-.598.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045v.002zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92zm-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217zm0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334"></path>
                </svg>
            </li>
        </ul>
        <p class="text-muted mb-0">Copyright © 2024 TrackIn</p>
    </div>
</footer>
<script src="../resources/bootstrap/js/bootstrap.js"></script>
<script src="../resources/js/sidebar.js"></script>
</body>

</html>