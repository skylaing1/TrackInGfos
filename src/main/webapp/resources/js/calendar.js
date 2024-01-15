document.addEventListener('DOMContentLoaded', function () {
    const yearElement = document.getElementById('year');
    const monthsContainer = document.getElementById('months-container');

    function renderCalendar(year) {
        yearElement.textContent = year;

        monthsContainer.innerHTML = '';

        for (let month = 0; month < 12; month++) {
            const monthContainer = document.createElement('div');
            monthContainer.classList.add('month');

            const monthHeader = document.createElement('h3');
            monthHeader.textContent = new Date(year, month, 1).toLocaleString('default', { month: 'long' });

            const weekdaysContainer = document.createElement('div');
            weekdaysContainer.classList.add('weekdays');

            const weekdays = ['Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa', 'So'];

            for (const weekday of weekdays) {
                const weekdayElement = document.createElement('div');
                weekdayElement.textContent = weekday;
                weekdaysContainer.appendChild(weekdayElement);
            }

            const daysContainer = document.createElement('div');
            daysContainer.classList.add('days');

            const daysInMonth = new Date(year, month + 1, 0).getDate();

            for (let day = 1; day <= daysInMonth; day++) {
                const dayElement = document.createElement('div');
                dayElement.classList.add('day');
                dayElement.textContent = day;

                markSpecialDays(year, month, day, dayElement);

                daysContainer.appendChild(dayElement);
            }

            monthContainer.appendChild(monthHeader);
            monthContainer.appendChild(weekdaysContainer);
            monthContainer.appendChild(daysContainer);

            monthsContainer.appendChild(monthContainer);
        }
    }

    function markSpecialDays(year, month, day, dayElement) {
        if (isSickDay(year, month, day)) {
            dayElement.classList.add('sick');
        }
        if (isPresentDay(year, month, day)) {
            dayElement.classList.add('present');
        }
        if (isAbsentDay(year, month, day)) {
            dayElement.classList.add('absent');
        }
        if (isVacationRequestedDay(year, month, day)) {
            dayElement.classList.add('vacation-requested');
        }
        if (isVacationDay(year, month, day)) {
            dayElement.classList.add('vacation');
        }
        if (isHolidayDay(year, month, day)) {
            dayElement.classList.add('holiday');
        }
        if (isPartialAbsentDay(year, month, day)) {
            dayElement.classList.add('partial-absent');
        }
        if (isBusinessTripDay(year, month, day)) {
            dayElement.classList.add('business-trip');
        }
    }

    function isPresentDay(year, month, day) {
        // Hier deine Logik für anwesende Tage implementieren
        // Beispiel: Alle Tage im Monat März sind anwesend
        return month === 2;
    }

    function isAbsentDay(year, month, day) {
        // Hier deine Logik für abwesende Tage implementieren
        // Beispiel: Alle Tage im Monat Dezember sind abwesend
        return month === 11;
    }

    function isVacationRequestedDay(year, month, day) {
        // Hier deine Logik für beantragten Urlaub implementieren
        // Beispiel: Alle Tage im Monat April haben beantragten Urlaub
        return month === 3;
    }

    function isVacationDay(year, month, day) {
        // Hier deine Logik für genehmigten Urlaub implementieren
        // Beispiel: Alle Tage im Monat Mai haben genehmigten Urlaub
        return month === 4;
    }

    function isHolidayDay(year, month, day) {
        // Hier deine Logik für Feiertage implementieren
        // Beispiel: Alle Tage im Monat Juni sind Feiertage
        return month === 5;
    }

    function isPartialAbsentDay(year, month, day) {
        // Hier deine Logik für teilweise abwesende Tage implementieren
        // Beispiel: Alle Tage im Monat Juli sind teilweise abwesend
        return month === 6;
    }

    function isBusinessTripDay(year, month, day) {
        // Hier deine Logik für Dienstreisen implementieren
        // Beispiel: Alle Tage im Monat August sind Dienstreisetage
        return month === 7;
    }

    function isSickDay(year, month, day) {
        // Hier deine Logik für kranke Tage implementieren
        // Beispiel: Alle Tage im Monat September sind krank
        return month === 8;
    }

    function changeYear(delta) {
        const currentYear = parseInt(yearElement.textContent, 10);
        renderCalendar(currentYear + delta);
    }

    // Initial rendering
    renderCalendar(new Date().getFullYear());

    // Expose the changeYear function to the global scope for button clicks
    window.changeYear = changeYear;
});
