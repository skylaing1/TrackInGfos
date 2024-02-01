const myModal = new bootstrap.Modal(document.getElementById('event-modal'));
const startDateInput = document.querySelector('#event-modal input[name="input_datum_von"]');
const endDateInput = document.querySelector('#event-modal input[name="input_datum_bis"]');
const entryIdInput = document.querySelector('#event-modal input[name="entry-id"]');
const startTimeInput = document.querySelector('#event-modal input[name="input_uhrzeit_von"]');
const endTimeInput = document.querySelector('#event-modal input[name="input_uhrzeit_bis"]');
const descriptionInput = document.querySelector('#event-modal textarea[name="input_notizen"]');
const statusInput = document.querySelector('#event-modal select[name="input_status"]');


// Event Listener damit das Datum nicht kleiner als das Startdatum sein kann
startDateInput.addEventListener('change', function() {
    endDateInput.min = startDateInput.value;
});

// Event Listener damit das Datum nicht größer als das Enddatum sein kann
endDateInput.addEventListener('change', function() {
    startDateInput.max = endDateInput.value;
});



function editEvent(entry) {
    console.log(entry);


    // Setzen der Werte in das Modal aus Datenbank falls vorhanden
    entryIdInput.value = (entry ? entry.id : '') ?? null;
    startDateInput.value = (entry ? entry.startDate : '');
    endDateInput.value = (entry ? entry.endDate : '');
    statusInput.value = (entry ? entry.status : '');
    startTimeInput.value = (entry ? entry.startTime : '');
    endTimeInput.value = (entry ? entry.endTime : '');
    descriptionInput.value = (entry ? entry.description : '') ?? null;


    // setzen der Min und Max Werte für die Uhrzeit wenn nur ein Tag ausgewählt ist
    if (startDateInput.value === endDateInput.value) {
        console.log("gleich");
        endTimeInput.min = startTimeInput.value;
        startTimeInput.max = endTimeInput.value;
    }

    // setzen der Min und Max Werte für das Datum
    endDateInput.min = startDateInput.value;
    startDateInput.max = endDateInput.value;






    myModal.show();
}

function saveEvent() {



    var entry = {

        //TODO: Entfernen wenn nicht mehr benötigt
        status: document.querySelector('#event-modal select[name="input_status"]').value,
        startDate: new Date(document.querySelector('#event-modal input[name="input_datum_von"]').value),
        endDate: new Date(document.querySelector('#event-modal input[name="input_datum_bis"]').value),
        startTime: document.querySelector('#event-modal input[name="input_uhrzeit_von"]').value,
        endTime: document.querySelector('#event-modal input[name="input_uhrzeit_bis"]').value,
        description: document.querySelector('#event-modal textarea[name="input_notizen"]').value,

    };

    console.log(entry);

    form.action = "/calendar";
    form.method = "POST";
    form.submit();

}


new Calendar('#calendar', {
    language: 'de',
    style: 'background',
    maxDate: new Date(new Date().getTime() + 365 * 24 * 60 * 60 * 1000),
    allowOverlap: true,
    disabledWeekDays: [0],
    enableContextMenu: true,
    displayWeekNumber: true,
    enableRangeSelection: true,
    dataSource: entries.map(function (entry) {
        return {

            id: entry.entryId,
            startDate: new Date(entry.startDate),
            endDate: new Date(entry.endDate),
            status: entry.status,
            startTime: entry.startTime,
            endTime: entry.endTime,
            description: entry.description,

        };
    }),
    contextMenuItems:[
        {
            text: 'Update',
            click: function(e) {
                var startDate = e.startDate.toISOString().split('T')[0];
                var endDate = e.endDate.toISOString().split('T')[0];
                editEvent({ id: e.id, startDate: startDate, endDate: endDate, status: e.status, startTime: e.startTime, endTime: e.endTime, description: e.description });
            }
        }
    ],
    selectRange: function (e) {
        console.log(e.startDate);

        // Extrahieren das Datum im ISO-Format ohne Zeitzone
        var startDate = new Date(e.startDate.getTime() - e.startDate.getTimezoneOffset() * 60000).toISOString().split('T')[0];
        var endDate = new Date(e.endDate.getTime() - e.endDate.getTimezoneOffset() * 60000).toISOString().split('T')[0];

        console.log(startDate);

        editEvent({ startDate: startDate, endDate: endDate });
    },
})
document.getElementById('form').addEventListener('submit', function (e) {
    e.preventDefault();
    saveEvent();
});
