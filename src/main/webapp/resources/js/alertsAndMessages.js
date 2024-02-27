// Selektieren der alerts
const alertElements = document.querySelectorAll('.alertnew');

// Konvertieren der Alerts zu Bootstrap Alerts
const alertsToClose = Array.from(alertElements).map(element => new bootstrap.Alert(element));

// Loop durch alle alerts und schließt diese nach 15 Sekunden
alertsToClose.forEach((alert, index) => {
    setTimeout(() => {
        alert.close();
    }, 15000);
});

document.getElementById('messageDropdown').addEventListener('click', function() {

    document.getElementById('messageCount').textContent = '';

    // Ajax um die Nachrichten zu markieren
    var xhr = new XMLHttpRequest();
    xhr.open('PUT', '/messages', true);
    xhr.send();
});