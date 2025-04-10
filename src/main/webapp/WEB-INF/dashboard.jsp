<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.example.entities.Mitarbeiter" %>

<!DOCTYPE html>
<html data-bs-theme="light" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Dashboard - TrackIn</title>
    <link rel="apple-touch-icon" sizes="180x180" href="../resources/img/favicon_io/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../resources/img/favicon_io/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../resources/img/favicon_io/favicon-16x16.png">
    <link rel="stylesheet" href="../resources/lib/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i&amp;display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700&amp;display=swap">
    <link rel="stylesheet" href="../resources/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="../resources/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../resources/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="../resources/css/style.css">
    <link rel="stylesheet" href="../resources/css/sidebar.css">
    <link rel="stylesheet" href="../resources/boxicons/css/boxicons.min.css">

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
                        <a class="selected" href="${pageContext.request.contextPath}/dashboard">
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
            <nav class="navbar navbar-expand sticky-top mb-4 topbar static-top navbar-light" style="background: var(--bs-lvl3);">
                <div class="container-fluid"><button class="btn d-md-none rounded-circle me-3 " id="sidebarToggleTop" type="button"><i class="fas fa-bars"></i></button>
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
                    <h3 class="text-dark mb-0">Dashboard</h3>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="card shadow mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center" style="background: var(--bs-card-bg);">
                                <h6 class="fw-bold text-primary m-0">Tagesablauf</h6><button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#entry-modal" type="button"><span style="color: #1b1b1e">Neuer Eintrag</span></button>
                            </div>
                            <div class="card-body d-flex flex-row align-items-stretch entry-card" style="color: #000000;">
                                <c:forEach items="${entries}" var="entry">
                                <div class="activity-box mx-2 my-2" style="min-width: 230px; width: ${entry.entryWidth}%;background: ${entry.cardColor}; min-height: 100px">
                                    <h6 class="fw-bold mb-2" >${entry.state}</h6>
                                    <p class="m-0">${entry.startTime}-${entry.endTime}</p><small style="color: #1b1b1e;">${entry.description}</small><span class="position-absolute top-0 end-0 p-2 delete-icon" style="cursor: pointer;" data-id="${entry.entryId}"><i class="fas fa-times-circle"></i></span>
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 col-xl-3 mb-4">
                        <div class="card shadow border-start-info py-2">
                            <div class="card-body">
                                <div class="row align-items-center no-gutters">
                                    <div class="col me-2">
                                        <div class="text-uppercase text-success fw-bold text-xs mb-1"><span>Aktueller Wochenstundenfortschritt</span></div>
                                        <div class="row g-0 align-items-center">
                                            <div class="col-auto">
                                                <div class="text-dark fw-bold h5 mb-0 me-3"><span>${geleisteteStundenInProzent}%</span></div>
                                            </div>
                                            <div class="col">
                                                <div class="progress progress-sm">
                                                    <div class="progress-bar bg-success" style="width: ${geleisteteStundenInProzent}%;"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-auto"><i class="fas fa-clipboard-list fa-2x text-gray-300"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-xl-3 mb-4">
                        <div class="card shadow border-start-primary py-2">
                            <div class="card-body">
                                <div class="row align-items-center no-gutters">
                                    <div class="col me-2">
                                        <div class="text-uppercase text-primary fw-bold text-xs mb-1"><span>Urlaubstage (Verbleibend)</span></div>
                                        <div class="text-dark fw-bold h5 mb-0"><span>${VerbleibenderUrlaubAnspruch} von 28</span></div>
                                    </div>
                                    <div class="col-auto"><i class="fas fa-calendar fa-2x text-gray-300"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-xl-3 mb-4">
                        <div class="card shadow border-start-success py-2">
                            <div class="card-body">
                                <div class="row align-items-center no-gutters">
                                    <div class="col me-2">
                                        <div class="text-uppercase text-success fw-bold text-xs mb-1"><span>Offene Wochenstunden</span></div>
                                        <div class="text-dark fw-bold h5 mb-0"><span>${stundenKontingentInStunden - geleisteteStunden}</span></div>
                                    </div>
                                    <div class="col-auto"><i class="fas fa-clock fa-2x text-gray-300"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-xl-3 mb-4">
                        <div class="card shadow border-start-warning py-2">
                            <div class="card-body">
                                <div class="row align-items-center no-gutters">
                                    <div class="col me-2">
                                        <div class="text-uppercase text-warning fw-bold text-xs mb-1"><span>Krankheitsbedingte Abwesenheit (in Tagen)</span></div>
                                        <div class="text-dark fw-bold h5 mb-0"><span>${krankTage} </span></div>
                                    </div>
                                    <div class="col-auto"><i class="fa fa-times-circle fa-2x text-gray-300"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-7 col-xl-8">
                        <div class="card shadow mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center" style="background: var(--bs-card-bg);">
                                <h6 class="text-primary fw-bold mb-1 mt-1">Stundenverteilung nach Wochentagen</h6>
                            </div>
                            <div class="card-body" style="background: var(--bs-body-bg);">
                                <div class="chart-area" style="position: relative;height: 365px;">
                                    <canvas data-bss-chart='{
        "type":"bar",
        "data":{
            "labels":["Montag","Dienstag","Mittwoch","Donnerstag","Freitag","Samstag"],
            "datasets":[
                {
                    "label":"Anwesend",
                    "backgroundColor":"#00CB62",
                    "borderColor":"rgb(255,255,255)",
                    "stacked":"true",
                    "data": ${presentHoursArray}

                },
                {
                    "label":"Pause",
                    "backgroundColor":"#ffee00",
                    "borderColor":"rgb(255,255,255)",
                    "stacked":"true",
                    "data": ${breakHoursArray}

                },
                {
                    "label":"Abwesend",
                    "backgroundColor":"#e74a3b",
                    "borderColor":"rgb(255,255,255)",
                    "stacked":"true",
                    "data": ${absentHoursArray}

                },
                {
                    "label":"Krank",
                    "backgroundColor":"#f28224",
                    "borderColor":"rgb(255,255,255)",
                    "stacked":"true",
                    "data": ${sickHoursArray}

                }
            ]
        },
        "options":{
            "responsive":true,
            "maintainAspectRatio":false,
            "legend":{

                "display":true,
                "labels":{"fontStyle":"bold","fontColor":"rgb(79,97,192)"},
                "position":"top",
                "reverse":false
            },
            "title":{"fontStyle":"normal"},
            "scales":{
                "xAxes":[
                    {
                        "stacked":true,
                        "gridLines":{
                            "color":"rgb(125, 28, 241)",
                            "zeroLineColor":"rgb(125, 28, 241)",
                            "drawBorder":true,
                            "drawTicks":true,
                            "borderDash":["4"],
                            "zeroLineBorderDash":["4"],
                            "drawOnChartArea":true
                        },
                         "ticks":{
                                        "fontStyle":"bold",
                                        "beginAtZero":true,
                                        "padding":7,
                                        "fontColor":"rgb(79,97,192)"
                                    }
                                }
                            ],
                "yAxes":[
                    {
                        "scaleLabel":{"display":true,"labelString":"Stunden","fontStyle":"bold","fontColor":"rgb(79,97,192)" },
                        "stacked":true,
                        "gridLines":{
                            "color":"rgb(125, 28, 241)",
                            "zeroLineColor":"rgb(125, 28, 241)",
                            "drawBorder":true,
                            "drawTicks":true,
                            "borderDash":["4"],
                            "zeroLineBorderDash":["4"],
                            "drawOnChartArea":true
                        },

                        "ticks":{

                          "fontStyle":"bold",
                          "beginAtZero":true,
                          "padding":7,
                          "fontColor":"rgb(79,97,192)"
                          }
                    }
                ]
            }
        }
    }'>
                                    </canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5 col-xl-4">
                        <div class="card shadow mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center" style="background: var(--bs-card-bg);">
                                <h6 class="text-primary fw-bold mb-1 mt-1">Stundenverteilung</h6>
                            </div>
                            <div class="card-body" style="background: var(--bs-card-bg);">
                                <div class="chart-area">
                                    <canvas data-bss-chart='{"type":"doughnut","data":{"labels":[" Anwesend"," Pause"," Abwesend"," Krank"],"datasets":[{"label":"","backgroundColor":["#00CB62","#ffee00","#e74a3b","#f28224"],"borderColor":["#ffffff","#ffffff","#ffffff","#ffffff"],"data":${pieChartArray}}]},  "options":{"maintainAspectRatio":false,"legend":{"display":false,"labels":{"fontStyle":"normal"}},"title":{"fontStyle":"normal"}}}'>
                                    </canvas>
                                </div>
                                <div class="text-center small mt-4"><span class="me-2"><i class="fas fa-circle" style="color: var(--bs-success);"></i>&nbsp;Anwesend</span><span class="me-2"><i class="fas fa-circle" style="color: var(--bs-yellow);"></i>&nbsp;Pause</span><span class="me-2"><i class="fas fa-circle" style="color: var(--bs-danger);"></i>&nbsp;Abwesend</span><span class="me-2"><i class="fas fa-circle" style="color: var(--bs-orange);"></i>&nbsp;Krank</span></div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
        </div>

    </div><a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>
</div>
<div id="entry-modal" class="modal fade" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><svg class="icon icon-tabler icon-tabler-calendar-plus" fill="none" height="1em" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" style="font-size: 34px;" viewBox="0 0 24 24" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M0 0h24v24H0z" fill="none" stroke="none"></path><path d="M12.5 21h-6.5a2 2 0 0 1 -2 -2v-12a2 2 0 0 1 2 -2h12a2 2 0 0 1 2 2v5"></path><path d="M16 3v4"></path><path d="M8 3v4"></path><path d="M4 11h16"></path><path d="M16 19h6"></path><path d="M19 16v6"></path>
                </svg>  Neuer Eintrag</h4><button class="btn-close"  data-bs-dismiss="modal" type="button"></button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form id="form" action="${pageContext.request.contextPath}/dashboard" method="post">
                        <div class="row">
                            <div class="col">
                                <div class="mb-3"><label class="form-label" for="status"><strong>Status</strong></label><select id="status" class="form-select" name="input_status" required>
                                    <option value="Anwesend" selected>Anwesend</option>
                                    <option value="Krank">Krank</option>
                                    <option value="Abwesend">Abwesend</option>
                                    <option value="Pause">Pause</option>
                                </select></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="mb-3"><label class="form-label" for="datum"><strong>Uhrzeit</strong></label>
                                    <div id="datum" class="input-group"><span class="input-group-text">von:</span><input class="form-control" name="input_zeit_von" required type="time" step="300"/><span class="input-group-text">bis:</span><input class="form-control" name="input_zeit_bis" required type="time" step="300"/></div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="mb-3"><label class="form-label" for="notizen"><strong>Notizen</strong></label><textarea id="notizen" class="form-control" maxlength="255" name="input_notizen"></textarea></div>
                            </div>
                        </div>
                        <div class="modal-footer"><a class="btn btn-danger" role="button" data-bs-dismiss="modal">Verwerfen</a><input id="save-event" class="btn btn-primary" type="submit" value="Speichern"/></div>
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
<script src="../resources/lib/chartjs/chart.min.js"></script>
<script src="../resources/lib/chartjs/bs-init.js"></script>
<script src="../resources/js/sidebar.js"></script>
<script src="../resources/js/alertsAndMessages.js"></script>
<script src="../resources/js/dashboard.js"></script>
</body>

</html>