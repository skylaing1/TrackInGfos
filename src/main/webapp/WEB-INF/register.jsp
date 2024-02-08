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
    <link rel="stylesheet" href="../resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i&amp;display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700&amp;display=swap">
    <link rel="stylesheet" href="../resources/css/style.css">
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
</head>

<body class="bg-gradient-primary" style="background: url(&quot;resources/img/background/pexels-photo-6985128.jpeg&quot;) center / cover no-repeat;">
<div class="container" style="opacity: 1;padding-bottom: 0px;">
    <div class="card shadow-lg o-hidden border-0 my-5">
        <div class="card-body p-0">
            <div class="row">
                <div class="col-lg-5 d-none d-lg-flex">
                    <div class="flex-grow-1 bg-register-image" style="opacity: 1;background: url(&quot;resources/img/Forgot%20password-rafiki.png&quot;) center / cover no-repeat, var(--bs-purple);"></div>
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
                            <hr>
                        </form>
                        <div class="text-center"></div>
                        <div class="text-center"><a class="small" href="${pageContext.request.contextPath}/login">Du hast bereits einen Benutzer? Login!</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="../resources/bootstrap/js/bootstrap.min.js"></script>

</body>

</html>