new Calendar('#calendar', {
    language: 'de',
    style: 'background',
    maxDate: new Date(new Date().getTime() + 365 * 24 * 60 * 60 * 1000),
    allowOverlap: false,
    disabledWeekDays: [0],
    enableContextMenu: true,
    displayWeekNumber: true,
    enableRangeSelection: true,
contextMenuItems: [
        {
            text: 'Update',
            click: updateEvent
        },
        {
            text: 'Delete',
            click: deleteEvent
        }
    ],

})
