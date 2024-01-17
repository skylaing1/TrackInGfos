<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-bs-theme="dark" lang="en" data-bss-forced-theme="dark">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login - TrackIn</title>
    <link rel="stylesheet" href="../resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i&amp;display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700&amp;display=swap">
    <link rel="stylesheet" href="../resources/css/bs-theme-overrides.css">
    <link rel="stylesheet" href="../resources/css/farben.compiled.css">
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
</head>

<body class="bg-gradient-primary" style="background: url(&quot;resources/img/background/pexels-photo-6985128.jpeg&quot;) top / cover no-repeat;">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-9 col-lg-12 col-xl-10">
            <div class="card shadow-lg o-hidden border-0 my-5">
                <div class="card-body p-0">
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-flex">
                            <div class="flex-grow-1 bg-login-image" style="background: url(&quot;resources/img/Mobile%20login-rafiki%20(1).png&quot;) center / contain no-repeat, var(--bs-purple);"></div>
                        </div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h4 class="text-dark mb-4">Willkommen Zurück!</h4>
                                </div>
                                <form class="user" action="${pageContext.request.contextPath}/login" method="post">
                                    <div class="mb-3"><input class="form-control form-control-user" type="email" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Email Adresse" name="email"></div>
                                    <div class="mb-3"><input class="form-control form-control-user" type="password" id="exampleInputPassword" placeholder="Passwort" name="password"></div>
                                    <div class="mb-3">
                                        <div class="custom-control custom-checkbox small">
                                            <div class="form-check"><input class="form-check-input custom-control-input" type="checkbox" id="formCheck-1" name="rememberMe" value="true"><label class="form-check-label custom-control-label" for="formCheck-1">Angemeldet Bleiben</label></div>
                                        </div>
                                    </div><button class="btn btn-primary d-block btn-user w-100" type="submit">Login</button>
                                    <hr>
                                </form>
                                <div class="text-center"><a class="small" href="forgot-password.jsp">Passwort vergessen?</a></div>
                                <div class="text-center"><a class="small" href="/register">Erstellen sie einen Benutzer!</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="../resources/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>