function createPaginationLink(text, page, active, isFirstPage, isLastPage) {
    const li = document.createElement('li');
    li.className = 'page-item' + (active ? ' active' : '') + (isFirstPage || isLastPage ? ' disabled' : '');

    const a = document.createElement('a');
    a.className = 'page-link';
    a.href = '#';
    a.textContent = text;
    a.dataset.page = page;

    if (isFirstPage || isLastPage) {
        a.tabIndex = -1;
        a.setAttribute('aria-disabled', 'true');
    }

    $('#DeleteConfirm').modal('show');


    li.appendChild(a);
    return li;
}

document.addEventListener('DOMContentLoaded', function () {
    const paginationContainer = document.querySelector('.pagination');

    // Bekommen die Anzahl der Zeilen
    const totalRows = parseInt(document.querySelector('tbody').dataset.totalRows);
    const totalPages = Math.ceil(totalRows / 30); // 30 Zeilen

    // Aktuelle Seite aus URL Parameter lesen
    const urlParams = new URLSearchParams(window.location.search);
    let currentPage = parseInt(urlParams.get('page')) || 1;

    // Seiten Links erstellen
    paginationContainer.innerHTML = '';
    paginationContainer.appendChild(createPaginationLink('«', currentPage > 1 ? currentPage - 1 : 1, currentPage === 1, currentPage === 1));
    for (let i = Math.max(1, currentPage - 1); i <= Math.min(totalPages, currentPage + 1); i++) {
        paginationContainer.appendChild(createPaginationLink(i, i, i === currentPage));
    }
    paginationContainer.appendChild(createPaginationLink('»', currentPage < totalPages ? currentPage + 1 : totalPages, currentPage === totalPages, false, currentPage === totalPages));

    // Klick auf die Links dann wird die Seite neu geladen (DoGet)
    paginationContainer.querySelectorAll('.page-link').forEach(function (link) {
        link.addEventListener('click', function (event) {
            event.preventDefault();

            // Update die aktuelle Seite
            currentPage = parseInt(this.dataset.page);

            // Update die URL
            window.location.href = '?page=' + currentPage;
        });
    });
});
document.addEventListener('DOMContentLoaded', function () {
    const searchBar = document.querySelector('input[aria-controls="dataTable"]');
    const tableRows = document.querySelectorAll('#dataTable tbody tr');

    searchBar.addEventListener('input', function () {
        const searchValue = this.value.toLowerCase();

        tableRows.forEach(row => {
            const rowText = row.textContent.toLowerCase();
            row.style.display = rowText.includes(searchValue) ? '' : 'none';
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const deleteIcons = document.querySelectorAll('.icontrash');
    const myModal = new bootstrap.Modal(document.getElementById("myModal"), {});
    let idToDelete;

    deleteIcons.forEach(icon => {
        icon.addEventListener('click', function (event) {
            event.preventDefault(); // Prevent the default action

            // Store the id of the row to be deleted
            idToDelete = icon.getAttribute('data-id');

            // Show the modal
            myModal.show();
        });
    });

    document.getElementById('DeleteConfirm').addEventListener('click', function () {
        // Send a DELETE request to the server
        fetch(`/deleteMitarbeiter?id=${idToDelete}`, {
            method: 'DELETE',
        });

        // Remove the corresponding row from the table
        document.querySelector(`.icontrash[data-id="${idToDelete}"]`).closest('tr').remove();

        // Hide the modal
        myModal.hide();
    });

    document.querySelector('.btn.btn-secondary').addEventListener('click', function () {
        // Hide the modal
        myModal.hide();
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const editIcons = document.querySelectorAll('.iconedit');

    editIcons.forEach(icon => {
        icon.addEventListener('click', function (event) {
            event.preventDefault();

            const idToEdit = icon.getAttribute('data-id');
            const nameToEdit = icon.getAttribute('data-name');
            const vornameToEdit = icon.getAttribute('data-vorname');
            const positionToEdit = icon.getAttribute('data-position');
            const adminToEdit = icon.getAttribute('data-admin');
            const einstellungsdatumToEdit = icon.getAttribute('data-einstellungsdatum');
            const geburtsdatumToEdit = icon.getAttribute('data-geburtsdatum');
            const wochenstundenToEdit = icon.getAttribute('data-wochenstunden');

            document.querySelector('#modaledit input[name="input_edit_nachname"]').value = nameToEdit;
            document.querySelector('#modaledit input[name="input_edit_vorname"]').value = vornameToEdit;
            document.querySelector('#modaledit input[name="input_edit_einstellungsdatum"]').value = einstellungsdatumToEdit;
            document.querySelector('#modaledit input[name="input_edit_geburtsdatum"]').value = geburtsdatumToEdit;
            const optionElement = document.querySelector('#modaledit input[name="input_edit_personalnummer"]');
            optionElement.value = idToEdit;
            optionElement.innerText = idToEdit;

            const selectElement = document.querySelector('#modaledit select[name="input_edit_position"]');
            selectElement.value = positionToEdit

            const workhoursElement = document.querySelector('#modaledit select[name="input_edit_wochenstunden"]');

            if (wochenstundenToEdit === "40") {
                workhoursElement.selectedIndex = 0;
            }
            if (wochenstundenToEdit === "20") {
                workhoursElement.selectedIndex = 1;
            }

            const adminElement = document.querySelector('#modaledit input[name="input_edit_admin"]');
            if (adminToEdit === "true") {
                adminElement.checked = true;
            } else {
                const otherAdminElement = document.querySelector('input[id="input_edit_admin_label2"]');
                otherAdminElement.checked = true;
            }



            document.querySelector('#modaledit input[name="input_edit_personalnummer_hidden"]').value = idToEdit;

            const myModal = new bootstrap.Modal(document.getElementById("modaledit"), {});
            myModal.show();
        });
    });
});

function resetIfInvalid(el){
    if (el.value === "")
        return;
    var options = el.list.options;
    for (var i = 0; i< options.length; i++) {
        if (el.value === options[i].value)
            return;
    }
    el.value = "";
}

new Calendar('#calendar', {
    language: 'de',
    style: 'background',
    maxDate: new Date(new Date().getTime() + 365 * 24 * 60 * 60 * 1000 * 10),
    allowOverlap: false,
    disabledWeekDays: [0],
    enableContextMenu: true,
    displayWeekNumber: true,
    enableRangeSelection: true,
})
