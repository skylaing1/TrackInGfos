var myModal = new bootstrap.Modal(document.getElementById('event-modal'));
function editEvent(event) {
    document.querySelector('#event-modal select[name="input_status"]').value = (event ? event.status : '');
    document.querySelector('#event-modal input[name="input_datum_von"]').value = (event ? event.startDate : '');
    document.querySelector('#event-modal input[name="input_datum_bis"]').value = (event ? event.endDate : '');
    document.querySelector('#event-modal input[name="input_uhrzeit_von"]').value = (event ? event.startTime : '');
    document.querySelector('#event-modal input[name="input_uhrzeit_bis"]').value = (event ? event.endTime : '');
    document.querySelector('#event-modal textarea[name="input_notizen"]').value = (event ? event.description : '');


    myModal.show();
}

function saveEvent() {
    var event = {

        status: document.querySelector('#event-modal select[name="input_status"]').value,
        startDate: new Date(document.querySelector('#event-modal input[name="input_datum_von"]').value),
        endDate: new Date(document.querySelector('#event-modal input[name="input_datum_bis"]').value),
        startTime: document.querySelector('#event-modal input[name="input_uhrzeit_von"]').value,
        endTime: document.querySelector('#event-modal input[name="input_uhrzeit_bis"]').value,
        description: document.querySelector('#event-modal textarea[name="input_notizen"]').value,

    };

    console.log(event);

    myModal.hide()
}


new Calendar('#calendar', {
    language: 'de',
    style: 'background',
    maxDate: new Date(new Date().getTime() + 365 * 24 * 60 * 60 * 1000),
    allowOverlap: false,
    disabledWeekDays: [0],
    enableContextMenu: true,
    displayWeekNumber: true,
    enableRangeSelection: true,
    dataSource: entries.map(function(entry) {
        return {
            startDate: new Date(entry.startDate),
            endDate: new Date(entry.endDate),
            name: entry.status
        };
    }),
    selectRange: function(e) {
        console.log("test");
        editEvent({ startDate: e.startDate, endDate: e.endDate });
    },
})
document.getElementById('save-event').addEventListener('click', function() {
    saveEvent();
});