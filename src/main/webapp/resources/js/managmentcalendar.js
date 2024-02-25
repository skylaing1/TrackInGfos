function deleteEvent(e) {
    fetch(`/calendar?id=${e.id}`, {
        method: 'DELETE',
    })
    var dataSource = calendar.getDataSource();

    calendar.setDataSource(dataSource.filter(event => event.id !== e.id));
}

const calendar = new Calendar('#calendar', {
    language: 'de',
    style: 'background',
    maxDate: new Date(new Date().getTime() + 365 * 24 * 60 * 60 * 1000 * 10),
    allowOverlap: false,
    disabledWeekDays: [0],
    enableContextMenu: true,
    displayWeekNumber: true,
    enableRangeSelection: false,
    contextMenuItems:[
        {
            text: 'Löschen',
            click: deleteEvent
        }
    ],
    contextMenuShow: function(e) {
        if (e.contextMenuElement) {
            e.contextMenuElement.classList.add('text-dark');
            e.contextMenuElement.classList.add('managmentcontextmenu');
        }
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
        if(e.element.popoverInstance) {
            e.element.popoverInstance.dispose();
            e.element.popoverInstance = null; // Set to null after disposing
        }
    },
    dayContextMenu: function(e) {
        if(e.element && e.element.popoverInstance) {
            e.element.popoverInstance.dispose();
            e.element.popoverInstance = null; // Set to null after disposing
        }
    },
})

document.querySelectorAll('.calendar table.month td.day .day-content, .calendar table.month th.day-header, .event-presentHours, .event-sickHours, .event-description, .event-status').forEach(function(element) {
    element.classList.add('text-dark');
});



    const MyModal = new bootstrap.Modal(document.getElementById("modalcalendar"), {});

    const eyeIcons = document.querySelectorAll('.iconeye');

    eyeIcons.forEach(icon => {
        icon.addEventListener('click', function (event) {
            event.preventDefault();


            // Clear the existing data
            calendar.setDataSource([]);

            MyModal.show();



            const idToGet = icon.getAttribute('data-id');

            // Fetch data from the server
            fetch('/getDays?id=' + idToGet)
                .then(response => response.json()) // Parse the response as JSON
                .then(days => {


                    // Use the data to populate the calendar
                    const dataSource = days.map(day => {
                        return {
                            id: day.daysId,
                            startDate: new Date(formatDateStr(day.date)),
                            endDate: new Date(formatDateStr(day.date)),
                            status: day.status,
                            color: day.color,
                            description: day.description,
                            sickHours: day.sickHours,
                            presentHours: day.presentHours,
                        };
                    });

                    // Update the calendar's data source
                    calendar.setDataSource(dataSource);
                })
                .catch(error => console.error('Error:', error));
        });
    });

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