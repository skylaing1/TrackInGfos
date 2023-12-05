<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="with=device-width, initial-scale=1.0">
    <link rel="icon" href="bilder/iconoffice.png" type="image/icon type"> <!-- Icon steht neben dem Titel !-->
    <link rel="stylesheet" href="css/style.css"> <!-- CSS datei Verbindung -->
    <title>Login | Flex Office</title>
</head>
<body>
<nav>
    <ul>
        <li><a href="">Lorem</a></li>
        <li><a href="">Ipsum</a></li>
        <li><a href="">Dolor</a></li>
        <li><a href="">Sit</a></li>
        <h2 class="login_title">ACME GmbH</h2>
    </ul>
</nav>
<section class="wrapper">
    <div class="content">
        <header>
            <h1>Willkommen zurück</h1>
        </header>
        <section>
            <form action="dashboard.jsp" class="login-form">  <!-- Bei Abschluss(Knopf) wird Dash.html geöffnet -->
                <div class="input-group">
                    <label for="personalnummer">Personalnummer</label>
                    <input type="text" placeholder="Personalnummer" id="personalnummer" required> <!-- Eingabe Personalnummer -->
                </div>
                <div class="input-group">
                    <label for="passwort">Passwort</label>
                    <input type="password" placeholder="Passwort" id="passwort" required> <!-- Eingabe Passwort -->
                </div>
                <a class="NoDecoration"  href="register.jsp">Noch Kein Passwort?</a>
                <div class="input-group"><button name="button" type="submit">Anmelden</button></div> <!-- Knopf Form submit -->
            </form>
        </section>
    </div>
</section>
</body>
</html>

<!-- Todo
Design verbessern
Obere Leiste einbauen
Hintergrund?
Ladebalken? -->