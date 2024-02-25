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
        fetch(`/managment?id=${idToDelete}`, {
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