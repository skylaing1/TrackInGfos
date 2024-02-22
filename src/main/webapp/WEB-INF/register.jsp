<%@ page import="org.example.Alert" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html data-bs-theme="dark" lang="en" data-bss-forced-theme="dark">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Register - TrackIn</title>
    <link rel="apple-touch-icon" sizes="180x180" href="../resources/img/favicon_io/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../resources/img/favicon_io/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../resources/img/favicon_io/favicon-16x16.png">
    <link rel="manifest" href="../resources/img/favicon_io/site.webmanifest">
    <link rel="stylesheet" href="../resources/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/css/style.css">
    <link rel="stylesheet" href="../resources/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="../resources/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../resources/fonts/fontawesome5-overrides.min.css">
</head>

<body class="bg-gradient-primary" style="background: url('../resources/img/staticpictures/background_log_reg.jpeg') top / cover no-repeat;">

<c:if test="${alert != null}">
    <div class="alert alertnew alert-${alert.alertType} alert-dismissible fade show" role="alert" style="position: fixed;max-width: 450px; top: 20px; right: 20px;">
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        <h4 class="alert-heading"><strong><i class="fa fa-${alert.alertIcon}"></i>${alert.alertTitle}</strong></h4>
        <hr><p class="mb-0">${alert.alertMessage}</p>
    </div>
</c:if>
<div class="container">
    <div class="card shadow-lg o-hidden border-0 my-5">
        <div class="card-body p-0">
            <div class="row">
                <div class="col-lg-5 d-none d-lg-flex">
                    <div class="flex-grow-1 bg-register-image" style="background: url('../resources/img/staticpictures/register_picture.png') center / cover no-repeat, var(--bs-purple);"></div>
                </div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h4 class="text-dark mb-4">Benutzer Registrieren!</h4>
                        </div>
                        <form class="user" action="${pageContext.request.contextPath}/register" method="POST">
                            <div class="mb-3"><input class="form-control form-control-user" type="email" placeholder="Email Adresse" name="email" required=""></div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control form-control-user" type="number" placeholder="Personalnummer" inputmode="numeric" name="personalnummer" required=""></div>
                                <div class="col-sm-6"><input class="form-control form-control-user" type="password"  name="oneTimePassword" required="" placeholder="Einmal Passwort" title="Das Einmalpasswort erhalten Sie direkt von der Personalabteilung."></div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control form-control-user" type="password" placeholder="Neues Passwort" name="password" minlength="8" maxlength="67" required=""></div>
                                <div class="col-sm-6"><input class="form-control form-control-user" type="password" placeholder="Neues Passwort Wiederholen" name="password_repeat" required=""></div>
                            </div>
                            <hr><button class="btn btn-primary d-block btn-user w-100" type="submit">Benutzer Registrieren!</button>
                        </form>
                        <div class="text-center mt-3"><a class="small" href="${pageContext.request.contextPath}/login">Du hast bereits einen Benutzer? Login!</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="../resources/bootstrap/bootstrap.min.js"></script>
<script src="../resources/js/alertsAndMessages.js"></script>
</body>

</html>