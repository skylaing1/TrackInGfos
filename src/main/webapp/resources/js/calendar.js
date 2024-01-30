var myModal = new bootstrap.Modal(document.getElementById('event-modal'));


function editEvent(entry) {
    console.log(entry);

    document.querySelector('#event-modal input[name="entry-id"]').value = (entry ? entry.id : '');
    document.querySelector('#event-modal input[name="input_datum_von"]').value = (entry ? entry.startDate : '');
    document.querySelector('#event-modal input[name="input_datum_bis"]').value = (entry ? entry.endDate : '');
    document.querySelector('#event-modal select[name="input_status"]').value = (entry ? entry.status : '');
    document.querySelector('#event-modal input[name="input_uhrzeit_von"]').value = (entry ? entry.startTime : '');
    document.querySelector('#event-modal input[name="input_uhrzeit_bis"]').value = (entry ? entry.endTime : '');
    document.querySelector('#event-modal textarea[name="input_notizen"]').value = (entry ? entry.description : '');

    myModal.show();
}

function saveEvent() {
    var entry = {


        status: document.querySelector('#event-modal select[name="input_status"]').value,
        startDate: new Date(document.querySelector('#event-modal input[name="input_datum_von"]').value),
        endDate: new Date(document.querySelector('#event-modal input[name="input_datum_bis"]').value),
        startTime: document.querySelector('#event-modal input[name="input_uhrzeit_von"]').value,
        endTime: document.querySelector('#event-modal input[name="input_uhrzeit_bis"]').value,
        description: document.querySelector('#event-modal textarea[name="input_notizen"]').value,

    };

    console.log(entry);

    myModal.hide()
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
        var startDate = e.startDate.toISOString().split('T')[0];
        var endDate = e.endDate.toISOString().split('T')[0];
        editEvent({startDate: startDate, endDate: endDate});
    },
})
document.getElementById('form').addEventListener('submit', function (e) {
    e.preventDefault();
    saveEvent();
});
