const startTimeInput = document.querySelector('#entry-modal input[name="input_zeit_von"]');
const endTimeInput = document.querySelector('#entry-modal input[name="input_zeit_bis"]');
const statusInput = document.querySelector('#entry-modal select[name="input_status"]');
const descriptionInput = document.querySelector('#entry-modal textarea[name="input_notizen"]');
const icons = document.querySelectorAll(".delete-icon");

icons.forEach(icon => {
    icon.addEventListener('click', function (event) {
        let idToDelete = icon.getAttribute('data-id');

        fetch(`/deleteEntry?id=${idToDelete}`, {
            method: 'DELETE',
        })

            //Wenn antwort mit 200 OK
            .then(response => {
                if(response.ok) {

                    icon.parentElement.remove();
                } else {
                    console.error('Delete request failed');
                }
            })
            .catch(error => console.error('Error:', error));
    });
});

startTimeInput.addEventListener('change', function() {
    endTimeInput.min = startTimeInput.value;
});

// Event Listener damit die Zeit nicht größer als die Endzeit sein kann
endTimeInput.addEventListener('change', function() {
    startTimeInput.max = endTimeInput.value;
});


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