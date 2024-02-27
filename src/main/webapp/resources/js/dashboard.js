const startTimeInput = document.querySelector('#entry-modal input[name="input_zeit_von"]');
const endTimeInput = document.querySelector('#entry-modal input[name="input_zeit_bis"]');
const icons = document.querySelectorAll(".delete-icon");

icons.forEach(icon => {
    icon.addEventListener('click', function (event) {
        let idToDelete = icon.getAttribute('data-id');

        fetch(`/dashboard?id=${idToDelete}`, {
            method: 'DELETE',
        })

            //Wenn antwort mit 200 OK
            .then(response => {
                if(response.ok) {

                    icon.parentElement.remove();
                } else {
                    console.error('Delete request failed');
                }
            })
            .catch(error => console.error('Error:', error));
    });
});

startTimeInput.addEventListener('change', function() {
    endTimeInput.min = startTimeInput.value;
});

// Event Listener damit die Zeit nicht größer als die Endzeit sein kann
endTimeInput.addEventListener('change', function() {
    startTimeInput.max = endTimeInput.value;
});


