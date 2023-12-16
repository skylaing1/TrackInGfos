<%--
  Created by IntelliJ IDEA.
  User: cumae
  Date: 05.12.2023
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="with=device-width, initial-scale=1.0">
  <link rel="icon" href="bilder/iconoffice.png" type="image/icon type"> <!-- Icon steht neben dem Titel !-->
  <link rel="stylesheet" href="css/style.css"> <!-- CSS datei Verbindung -->
  <title>Registrierung | Flex Office</title>

</head>
<body class="VScrollbar">
<nav>
  <ul>
    <li><a href="dashboard.jsp">Dashboard</a></li>
    <li><a href="">Ipsum</a></li>
    <li><a href="">Dolor</a></li>
    <li><a href="">Sit</a></li>
    <h2 class="login_title">ACME GmbH</h2>
  </ul>
</nav>
<section class="wrapper">
  <div class="content">
    <header>
      <h1>Passwort erstellen</h1>
    </header>
    <section>
      <form action="index.html" class="login-form">  <!-- Bei Abschluss(Knopf) wird Dash.html geöffnet -->
        <div class="input-group">
          <label for="E-Mail">E-Mail</label>
          <input type="text" placeholder="Max.Mustermann@TrackIn.com" id="E-Mail" required> <!-- Eingabe benutzername -->
        </div>
        <div class="input-group">
          <label for="personalnummer">Personalnummer</label>
          <input type="text" placeholder="Personalnummer" id="personalnummer" required> <!-- Eingabe Personalnummer -->
        </div>
        <div class="input-group">
          <label for="geburtsdatum">Geburtsdatum</label>
          <input type="date" placeholder="1.1.2000" id="geburtsdatum" required> <!-- Eingabe Geburtsdatum -->
        </div>
        <div class="input-group">
          <label for="passwort">Passwort</label>
          <input type="password" placeholder="Passwort" id="passwort" required> <!-- Eingabe Passwort -->
        </div>
        <div class="input-group">
          <label for="passwordrepeat">Passwort wiederholen</label>
          <input type="password" placeholder="Passwort" id="passwordrepeat" required> <!-- Eingabe Passwort wiederholen -->
        </div>
        <a class="NoDecoration" href="index.jsp">Du hast bereits ein Passwort?</a> <!-- Link zu Login -->
        <div class="input-group"><button name="button" type="submit">Registrieren</button></div> <!-- Knopf Form submit -->

      </form>
    </section>
  </div>
</section>
</body>
</html>
