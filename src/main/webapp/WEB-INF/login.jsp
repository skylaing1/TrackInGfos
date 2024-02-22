<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-bs-theme="dark" lang="en" data-bss-forced-theme="dark">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login - TrackIn</title>
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
        <h4 class="alert-heading"><strong><i class="fa fa-${alert.alertIcon}"></i>${alert.alertTitle}</strong></h4><hr>
        <p class="mb-0">${alert.alertMessage}</p>
    </div>
</c:if>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-9 col-lg-12 col-xl-10">
            <div class="card shadow-lg o-hidden border-0 my-5">
                <div class="card-body p-0">
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-flex">
                            <div class="flex-grow-1 bg-login-image" style="background: url('../resources/img/staticpictures/login_picture.png') center / contain no-repeat, var(--bs-purple);"></div>
                        </div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h4 class="text-dark mb-4">Willkommen Zurück!</h4>
                                </div>
                                <form class="user" action="${pageContext.request.contextPath}/login" method="post">
                                    <div class="mb-3"><input class="form-control form-control-user" type="email" id="exampleInputEmail" placeholder="Email Adresse" name="email"></div>
                                    <div class="mb-3"><input class="form-control form-control-user" type="password" id="exampleInputPassword" placeholder="Passwort" name="password"></div>
                                    <div class="mb-3">
                                        <div class="custom-control custom-checkbox small">
                                            <div class="form-check"><input class="form-check-input custom-control-input" type="checkbox" id="formCheck-1" name="rememberMe" value="true"><label class="form-check-label custom-control-label" for="formCheck-1">Angemeldet Bleiben</label></div>
                                        </div>
                                    </div><button class="btn btn-primary d-block btn-user w-100" type="submit">Login</button>
                                    <hr>
                                </form>
                                <div class="text-center"><a class="small" href="${pageContext.request.contextPath}/register">Erstellen sie einen Benutzer!</a></div>
                            </div>
                        </div>
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