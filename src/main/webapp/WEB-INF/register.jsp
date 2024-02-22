<%@ page import="org.example.Alert" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <%
        Alert alert = (Alert) request.getAttribute("alert");
    %>
</head>

<body class="bg-gradient-primary" style="background: url(&quot;resources/img/staticpictures/background_log_reg.jpeg&quot;) center / cover no-repeat;">
<c:if test="${alert != null}">
    <div class="alert alertnew alert-${alert.alertType} alert-dismissible fade show" role="alert" style="position: fixed;">
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        <h4><i class="fa fa-${alert.alertIcon}"></i>${alert.alertTitle}</h4>
        <p class="mb-0">${alert.alertMessage}</p>
    </div>
</c:if>
<div class="container">
    <div class="card shadow-lg o-hidden border-0 my-5">
        <div class="card-body p-0">
            <div class="row">
                <div class="col-lg-5 d-none d-lg-flex">
                    <div class="flex-grow-1 bg-register-image" style="background: url(&quot;resources/img/staticpictures/register_picture.png&quot;) center / cover no-repeat, var(--bs-purple);"></div>
                </div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h4 class="text-dark mb-4">Benutzer Registrieren!</h4>
                        </div>
                        <form class="user" action="${pageContext.request.contextPath}/register" method="POST">
                            <div class="mb-3"><input class="form-control form-control-user" type="email" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Email Adresse" name="email" required=""></div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control form-control-user" type="number" id="exampleFirstName" placeholder="Personalnummer" inputmode="numeric" name="personalnummer" required=""></div>
                                <div class="col-sm-6"><input class="form-control form-control-user" type="password" id="exampleLastName" name="oneTimePassword" required="" placeholder="Einmal Passwort" title="Das Einmalpasswort erhalten Sie direkt von der Personalabteilung."></div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control form-control-user" type="password" id="examplePasswordInput" placeholder="Neues Passwort" name="password" minlength="8" maxlength="67" required=""></div>
                                <div class="col-sm-6"><input class="form-control form-control-user" type="password" id="exampleRepeatPasswordInput" placeholder="Neues Passwort Wiederholen" name="password_repeat" required=""></div>
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

</body>

</html>