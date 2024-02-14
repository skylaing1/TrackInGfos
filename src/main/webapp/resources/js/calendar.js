const myModal = new bootstrap.Modal(document.getElementById('event-modal'));
const startDateInput = document.querySelector('#event-modal input[name="input_datum_von"]');
const endDateInput = document.querySelector('#event-modal input[name="input_datum_bis"]');
const daysIdInput = document.querySelector('#event-modal input[name="entry-id"]');
const statusInput = document.querySelector('#event-modal select[name="input_status"]');
const descriptionInput = document.querySelector('#event-modal textarea[name="input_notizen"]');


// Event Listener damit das Zeit nicht kleiner als das Startdatum sein kann
startDateInput.addEventListener('change', function() {
    endDateInput.min = startDateInput.value;
});

// Event Listener damit das ZEit nicht größer als das Enddatum sein kann
endDateInput.addEventListener('change', function() {
    startDateInput.max = endDateInput.value;
});

// Mapping of German month names to their numerical equivalents
var monthMapping = {
    "Jan.": "01",
    "Feb.": "02",
    "März": "03",
    "Apr.": "04",
    "Mai": "05",
    "Juni": "06",
    "Juli": "07",
    "Aug.": "08",
    "Sept.": "09",
    "Okt.": "10",
    "Nov.": "11",
    "Dez.": "12"
};

function formatDateStr(dateStr) {
    var dateParts = dateStr.split(" ");
    dateParts[0] = monthMapping[dateParts[0]];
    return dateParts.join(" ");
}

function editEvent(days) {
    console.log(days);


    // Setzen der Werte in das Modal aus Datenbank falls vorhanden
    daysIdInput.value = (days ? days.id : '') ?? null;
    startDateInput.value = (days ? days.startDate : '');
    endDateInput.value = (days ? days.endDate : '');
    statusInput.value = (days ? days.status : '');
    descriptionInput.value = (days ? days.description : '') ?? null;


    // setzen der Min und Max Werte für das Datum
    endDateInput.min = startDateInput.value;
    startDateInput.max = endDateInput.value;

    //ToDo: Datepicker beim Updaten deaktivieren
    //TODO: Modal datepicker limitieren auf benutzte tage

    myModal.show();
}

function saveEvent() {



    var days = {

        //TODO: Entfernen wenn nicht mehr benötigt
        status: document.querySelector('#event-modal select[name="input_status"]').value,
        startDate: new Date(document.querySelector('#event-modal input[name="input_datum_von"]').value),
        endDate: new Date(document.querySelector('#event-modal input[name="input_datum_bis"]').value),
        description: document.querySelector('#event-modal textarea[name="input_notizen"]').value,


    };

    console.log(days);

    form.action = "/calendar";
    form.method = "POST";
    form.submit();
}


new Calendar('#calendar', {
    language: 'de',
    style: 'background',
    maxDate: new Date(new Date().getTime() + 365 * 24 * 60 * 60 * 1000 * 10),
    allowOverlap: false,
    disabledWeekDays: [0],
    setNumberMonthsDisplayed: 12,
    enableContextMenu: true,
    displayWeekNumber: true,
    enableRangeSelection: true,
    dataSource: days.map(function (days) {
        return {
            id: days.daysId,
            startDate: new Date(formatDateStr(days.date)),
            endDate: new Date(formatDateStr(days.date)),
            status: days.status,
            color: days.color,
            description: days.description,
            sickHours: days.sickHours,
            presentHours: days.presentHours,
        };
    }),
    contextMenuItems:[
        {
            text: 'Update',
            click: function(e) {
                var startDate = new Date(e.startDate.getTime() - e.startDate.getTimezoneOffset() * 60000).toISOString().split('T')[0];
                var endDate = new Date(e.endDate.getTime() - e.endDate.getTimezoneOffset() * 60000).toISOString().split('T')[0];
                editEvent({ id: e.id, startDate: startDate, endDate: endDate, status: e.status, description: e.description});
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
    mouseOnDay: function(e) {
        if(e.events.length > 0) {
            var content = '';
            for(var i in e.events) {
                content += '<div class="event-tooltip-content">'
                    + '<div class="event-status" style="color:' + e.events[i].color + '">' + e.events[i].status + '</div>'
                    + '<div class="event-presentHours" style="color: green"> Anwesend: ' + e.events[i].presentHours + 'h </div>'
                    + '<div class="event-sickHours" style="color: yellow"> Krank: ' + e.events[i].sickHours + 'h </div>'
                    + '<div class="event-description">' + e.events[i].description
                    + '</div>';
            }

            var popover = new bootstrap.Popover(e.element, {
                content: content,
                html: true,
                trigger: 'manual',
                placement: 'right'
            });

            popover.show();
            e.element.popoverInstance = popover;
        }
    },
    mouseOutDay: function(e) {
        if(e.events.length > 0 && e.element && e.element.popoverInstance) {
            e.element.popoverInstance.dispose();
        }
    },
    dayContextMenu: function(e) {
        if(e.element && e.element.popoverInstance) {
            e.element.popoverInstance.dispose();
        }
    },
})
document.getElementById('form').addEventListener('submit', function (e) {
    e.preventDefault();
    saveEvent();
});

console.log(days);