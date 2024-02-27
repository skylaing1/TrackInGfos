<%@ page import="org.example.entities.Mitarbeiter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html data-bs-theme="light" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Verwaltung - TrackIn</title>
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
    <link rel="stylesheet" href="../resources/css/datatable.css">
    <link rel='stylesheet' href="../resources/boxicons/css/boxicons.min.css">
    <link rel="stylesheet" href="../resources/lib/js-year-calendar/js-year-calendar.css">
    <script src="../resources/lib/js-year-calendar/js-year-calendar.min.js"></script>
    <script src="../resources/lib/js-year-calendar/js-year-calendar.de.js"></script>
    <link rel="stylesheet" href="../resources/css/calendar.css">
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
                        <a href="${pageContext.request.contextPath}/profile">
                            <i class='bx bx-user icon' ></i>
                            <span class="text nav-text">Profil</span>
                        </a>
                    </li>
                   <c:if test="<%=mitarbeiter.getAdmin()%>">
                        <li class="nav-link">
                            <a class="selected" href="${pageContext.request.contextPath}/managment">
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
    <div class="d-flex flex-column" id="content-wrapper" style="background: var(--bs-lvl1); height: 100vh;">
        <div id="content">
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
                <h3 class="text-dark mb-4">Verwaltung</h3>
                <div class="card shadow">
                    <div class="card-header py-3" style="background: var(--bs-card-bg);">
                        <p class="text-primary float-start m-0 fw-bold" style="font-size: 21px;padding-top: 4px;">Mitarbeiter</p>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="d-flex justify-content-start dataTables_filter" id="dataTable_filter">
                                    <label class="form-label">
                                        <input type="search" class="form-control form-control-m" aria-controls="dataTable" placeholder="Suche">
                                    </label>
                                </div>
                                <button class="btn btn-primary" type="button" data-bs-toggle="modal" data-bs-target="#mitarbeiter">
                                    <i class="fas fa-plus"></i><span style="margin-left: 10px;">Hinzufügen</span>
                                </button>
                            </div>
                            <div id="dataTable-1" class="table-responsive table mt-2" role="grid"
                                 aria-describedby="dataTable_info">
                                <table id="dataTable" class="table table-hover my-0" style="white-space: nowrap;">
                                    <thead>
                                    <tr>
                                        <th style="width: 20px;" data-bs-placement="bottom" data-bs-toggle="tooltip" title="Personalnummer">Nr.</th>
                                        <th class="col-auto" >Name</th>
                                        <th >Geburtsdatum</th>
                                        <th>Eintrittsdatum</th>
                                        <th>Position</th>
                                        <th>Wochenstunden</th>
                                        <th>Verbleibende Urlaubstage</th>
                                        <th>Stundenfortschritt (wöchentlich)</th>
                                        <th class="text-center" style="width: 100px;">Aktion</th>
                                    </tr>
                                    </thead>
                                    <tbody class="table-hover" data-total-rows="<%=request.getAttribute("totalRows")%>">
                                    <c:forEach var="mitarbeiter_inTable" items="${mitarbeiterList}">
                                        <tr>
                                            <td class="col-auto">${mitarbeiter_inTable.personalNummer}</td>
                                            <td class="col-auto"><div class="c-avatar"><img class="rounded-circle me-2" height="30" src="${mitarbeiter_inTable.profilePicture}" width="30"/><span class="c-avatar__status_${mitarbeiter_inTable.present}"></span></div>${mitarbeiter_inTable.vorname} ${mitarbeiter_inTable.name}</td>
                                            <td class="col-auto">${mitarbeiter_inTable.geburtsdatumFormatted}</td>
                                            <td class="col-auto">${mitarbeiter_inTable.einstellungsdatumFormatted}</td>
                                            <td class="col-auto">${mitarbeiter_inTable.position}</td>
                                            <td class="col-auto">${mitarbeiter_inTable.wochenstundenFormatted}</td>
                                            <td class="col-auto">${mitarbeiter_inTable.verbleibendeUrlaubstage} von 28 Tagen</td>
                                            <td>
                                                <div class="row g-0 align-items-center">
                                                    <div class="col-3">
                                                        <div class="text-dark fw-bold h8 me-1">
                                                            <span>${mitarbeiter_inTable.wochenstundenProgressInPercent}%</span></div>
                                                    </div>
                                                    <div class="col me-10">
                                                        <div class="progress">
                                                            <div class="progress-bar bg-success" style="width: ${mitarbeiter_inTable.wochenstundenProgressInPercent}%;"></div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="col-auto">
                                                <div class="text-end">
                                                <i class="far fa-eye iconeye" data-id='${mitarbeiter_inTable.personalNummer}'></i>
                                                <i class='far fa-edit iconedit' data-id='${mitarbeiter_inTable.personalNummer}' data-name='${mitarbeiter_inTable.name}' data-vorname='${mitarbeiter_inTable.vorname}' data-geburtsdatum='${mitarbeiter_inTable.geburtsdatum}' data-einstellungsdatum='${mitarbeiter_inTable.einstellungsdatum}' data-position='${mitarbeiter_inTable.position}' data-wochenstunden='${mitarbeiter_inTable.wochenstunden}' data-admin='${mitarbeiter_inTable.admin}'></i>
                                                <i class="far fa-trash-alt icontrash" data-id='${mitarbeiter_inTable.personalNummer}'></i>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="row">
                                <div class="col-md-6 align-self-center">
                                    <p>Mitarbeiter: ${totalRows}: davon Heute Anwesend: ${presentCounter}: davon ist Anwesend: ${wasPresentTodayCounter}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>
    </div>
    <div class="modal fade" id="myModal" data-bs-backdrop="static" data-bs-keyboard="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><i class="bi bi-exclamation-triangle" style="color: red;"></i> Achtung!</h4>
                </div>
                <div class="modal-body">
                    Möchten sie den Mitarbeiter wirklich löschen?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Abbrechen</button>
                    <button type="button" class="btn btn-secondary" id="DeleteConfirm" data-bs-dismiss="modal">
                        Bestätigen
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="mitarbeiter">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">
                        <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 24 24" width="1em" fill="currentColor" style="font-size: 34px;"><path d="M0 0h24v24H0z" fill="none"></path><path d="M15 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm-9-2V7H4v3H1v2h3v3h2v-3h3v-2H6zm9 4c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"></path></svg>&nbsp; Mitarbeiter Hinzufügen
                    </h4>
                    <button class="btn-close" type="button" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <form action="${pageContext.request.contextPath}/addMitarbeiter" method="post">
                            <div class="row">
                                <div class="col">
                                    <div class="mb-3"><label class="form-label" for="service_name"><strong>Vorname
                                        *</strong></label><input class="form-control" type="text" id="service_name-1" name="input_vorname" placeholder="Max" required=""></div>
                                    <div class="mb-3"><label class="form-label" for="Datalist"><strong>Personalnummer *</strong></label><input onchange="resetIfInvalid(this);" id="Datalist" class="form-control" name="input_personalnummer" list="datalistOptions2" placeholder="1234" required>
                                        <datalist id="datalistOptions2">
                                            <c:forEach var="personalNummer" items="${allAvailablePersonalNummer}">
                                                <option value="${personalNummer}">${personalNummer}</option>
                                            </c:forEach>
                                        </datalist>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-3">
                                        <label class="form-label" for="service_name"><strong>Name *</strong></label>
                                        <input class="form-control" type="text" id="service_name" name="input_nachname" placeholder="Mustermann" required="">
                                    </div>
                                    <div class="mb-2">
                                        <label class="form-label"><strong>Administrator *</strong></label>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="input_admin" id="flexRadioDefault1">
                                            <label class="form-check-label" for="flexRadioDefault1">Ja</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="input_admin" id="flexRadioDefault2" checked>
                                            <label class="form-check-label" for="flexRadioDefault2">Nein</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col">
                                    <div class="mb-3"><label class="form-label" for="input_geburtsdatum"><strong>Geburtsdatum *</strong></label>
                                        <input class="form-control" id="input_geburtsdatum" type="date" name="input_geburtsdatum" required="">
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-3">
                                        <label class="form-label" for="input_einstellungsdatum"><strong>Einstellungsdatum *</strong><br></label>
                                        <input class="form-control" id="input_einstellungsdatum" type="date" name="input_einstellungsdatum" required="">
                                    </div>
                                </div>
                            </div>
                            <div class="text-end mb-3"></div>
                            <div class="row mb-2">
                                <div class="col">
                                    <div class="mb-3">
                                        <label class="form-label" for="position"><strong>Position *</strong></label>
                                        <div class="form-group mb-3"><select class="form-select" id="position" name="input_position" required="">
                                            <option value="Geschäftsführer">Geschäftsführung</option>
                                            <option value="Mitarbeiter" selected="">Mitarbeiter</option>
                                            <option value="Auszubildender">Auszubildender</option>
                                            <option value="Praktikant">Praktikant</option>
                                        </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-3"><label class="form-label" for=wochenstunden><strong>Wochenstunden *</strong></label>
                                        <div class="form-group mb-3" id="wochenstunden"><select class="form-select" name="input_wochenstunden" required="">
                                            <option value="40" selected="">Vollzeit (40h)</option>
                                            <option value="20">Teilzeit (20h)</option>
                                        </select></div>
                                    </div>
                                </div>
                            </div>
                            <div class="mb-4">
                                <label class="form-label" for="input_einstellungsdatum"><strong>Einmalpasswort *</strong><br></label>
                                <input class="form-control" type="password" placeholder="Max1234Mustermann" name="input_password" required="">
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-danger" data-bs-dismiss="modal">Abbrechen</button>
                                <button class="btn btn-primary" type="submit">Mitarbeiter Erstellen</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" role="dialog" id="modaledit">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">
                        <svg class="icon icon-tabler icon-tabler-user-edit" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round" style="font-size: 34px;"><path stroke="none" d="M0 0h24v24H0z" fill="none"></path><path d="M8 7a4 4 0 1 0 8 0a4 4 0 0 0 -8 0"></path><path d="M6 21v-2a4 4 0 0 1 4 -4h3.5"></path><path d="M18.42 15.61a2.1 2.1 0 0 1 2.97 2.97l-3.39 3.42h-3v-3l3.42 -3.39z"></path></svg>  Mitarbeiter Bearbeiten
                    </h4>
                    <button class="btn-close" type="button" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <form action="${pageContext.request.contextPath}/managment" method="post">
                            <div class="row">
                                <div class="col">
                                    <div class="mb-3">
                                        <label class="form-label" for="input_edit_vorname_label"><strong>Vorname *</strong></label>
                                        <input id="input_edit_vorname_label" class="form-control" type="text" name="input_edit_vorname" placeholder="Max" required/>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label mb-2" for="input_edit_personalnummer_label"><strong>Personalnummer *</strong></label>
                                        <input onchange="resetIfInvalid(this);" id="input_edit_personalnummer_label" class="form-control" name="input_edit_personalnummer" list="datalistOptions_edit" required disabled>
                                        <datalist id="datalistOptions_edit">
                                            <option value="" selected></option>
                                        </datalist>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-3">
                                        <label class="form-label" for="input_edit_nachname_label"><strong>Name *</strong></label>
                                        <input id="input_edit_nachname_label" class="form-control" type="text" name="input_edit_nachname" placeholder="Mustermann" required/>
                                    </div>
                                    <div class="mb-2">
                                        <label class="form-label"><strong>Administrator *</strong></label>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="input_edit_admin" value="true" id="input_edit_admin_label1">
                                            <label class="form-check-label" for="input_edit_admin_label1">Ja</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="input_edit_admin" value="false" id="input_edit_admin_label2" checked>
                                            <label class="form-check-label" for="input_edit_admin_label2">Nein</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col">
                                        <div class="mb-3">
                                            <label class="form-label" for="input_edit_geburtsdatum_label"><strong>Geburtsdatum *</strong></label>
                                            <input id="input_edit_geburtsdatum_label" class="form-control" type="date" name="input_edit_geburtsdatum" required/>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="mb-3">
                                            <label class="form-label" for="input_edit_einstellungsdatum_label"><strong>Einstellungsdatum *</strong><br/></label>
                                            <input id="input_edit_einstellungsdatum_label" class="form-control" type="date" name="input_edit_einstellungsdatum" required/>
                                        </div>
                                    </div>
                                </div>
                                <div class="text-end mb-3"></div>
                                <div class="row mb-2">
                                    <div class="col">
                                        <div class="mb-3">
                                            <label class="form-label" for="input_edit_position_label"><strong>Position *</strong></label>
                                            <div class="form-group mb-3">
                                                <select id="input_edit_position_label" class="form-select" name="input_edit_position" required>
                                                    <option value="Geschäftsführer">Geschäftsführung</option>
                                                    <option value="Mitarbeiter" selected="">Mitarbeiter</option>
                                                    <option value="Auszubildender">Auszubildender</option>
                                                    <option value="Praktikant">Praktikant</option>
                                            </select></div>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="mb-3">
                                            <label class="form-label" for="input_edit_wochenstunden_label"><strong>Wochenstunden *</strong></label>
                                            <div class="form-group mb-3">
                                                <select id="input_edit_wochenstunden_label" class="form-select" name="input_edit_wochenstunden" required>
                                                <option id="40" value="40">Vollzeit (40h)</option>
                                                <option id="20" value="20">Teilzeit (20h)</option>
                                            </select></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-3">
                                        <label class="form-label" for="input_edit_password_label"><strong>Anmeldeinformationen zurücksetzen</strong></label>
                                        <input id="input_edit_password_label" class="form-control" type="password" name="input_edit_password" placeholder="Neues Einmal Passwort"/></div>
                                </div>
                            </div>
                    <div class="modal-footer">
                        <button class="btn btn-danger" type="button" data-bs-dismiss="modal">Verwerfen</button>
                        <button class="btn btn-primary" type="submit">Speichern</button>
                    </div>
                            <input type="hidden" name="input_edit_personalnummer_hidden" id="input_edit_personalnummer_hidden">
                    </form>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </div>
<div class="modal fade modal-xl" role="dialog" id="modalcalendar">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><svg class="icon icon-tabler-calendar" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round" style="font-size: 34px;">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path><path d="M12.5 21h-6.5a2 2 0 0 1 -2 -2v-12a2 2 0 0 1 2 -2h12a2 2 0 0 1 2 2v5"></path><path d="M16 3v4"></path><path d="M8 3v4"></path><path d="M4 11h16"></path><path d="M16 19h6"></path><path d="M19 16v6"></path>
                </svg>  Kalender</h4><button class="btn-close" type="button" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div id="calendar"></div>
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
    <script src="../resources/js/sidebar.js"></script>
    <script src="../resources/js/datatable.js"></script>
    <script src="../resources/js/managmentcalendar.js"></script>
    <script src="../resources/js/alertsAndMessages.js"></script>
</body>
</html>