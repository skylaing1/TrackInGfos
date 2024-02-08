const startTimeInput = document.querySelector('#entry-modal input[name="input_zeit_von"]');
const endTimeInput = document.querySelector('#entry-modal input[name="input_zeit_bis"]');
const statusInput = document.querySelector('#entry-modal select[name="input_status"]');
const descriptionInput = document.querySelector('#entry-modal textarea[name="input_notizen"]');
const icon = document.getElementById("delete-icon");

icon.addEventListener("click", function() {
    idToDelete = icon.getAttribute('data-id');
    console.log(idToDelete);

    fetch(`/deleteEntry?id=${idToDelete}`, {
        method: 'DELETE',
    });

});
