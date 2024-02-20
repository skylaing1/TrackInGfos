// Selektieren der alerts
const alertElements = document.querySelectorAll('.alertnew');

// Konvertieren der Alerts zu Bootstrap Alerts
const alertsToClose = Array.from(alertElements).map(element => new bootstrap.Alert(element));

// Loop durch alle alerts und schließt diese nach 5 Sekunden
alertsToClose.forEach((alert, index) => {
    // Schließt den Alert nach 5 Sekunden
    setTimeout(() => {
        alert.close();
    }, 5000);
});