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

    li.appendChild(a);
    return li;
}

document.addEventListener('DOMContentLoaded', function () {
    // Get the pagination links container
    const paginationContainer = document.querySelector('.pagination');

    // Get the total number of rows and calculate the total number of pages
    const totalRows = parseInt(document.querySelector('tbody').dataset.totalRows);
    const totalPages = Math.ceil(totalRows / 30); // 30 Zeilen

    // Get the current page from the URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    let currentPage = parseInt(urlParams.get('page')) || 1;

    // Generate the pagination links
    paginationContainer.innerHTML = '';
    paginationContainer.appendChild(createPaginationLink('«', currentPage > 1 ? currentPage - 1 : 1, currentPage === 1, currentPage === 1));
    for (let i = Math.max(1, currentPage - 1); i <= Math.min(totalPages, currentPage + 1); i++) {
        paginationContainer.appendChild(createPaginationLink(i, i, i === currentPage));
    }
    paginationContainer.appendChild(createPaginationLink('»', currentPage < totalPages ? currentPage + 1 : totalPages, currentPage === totalPages, false, currentPage === totalPages));

    // Add event listeners to the pagination links
    paginationContainer.querySelectorAll('.page-link').forEach(function (link) {
        link.addEventListener('click', function (event) {
            event.preventDefault();

            // Update the current page number
            currentPage = parseInt(this.dataset.page);

            // Refresh the page with the new page number
            window.location.href = '?page=' + currentPage;
        });
    });
});



window.onload = function() {
    var headerCheckbox = document.getElementById('select-all');
    var rowCheckboxes = document.getElementsByClassName('row-checkbox');

    headerCheckbox.addEventListener('change', function() {
        for (var i = 0; i < rowCheckboxes.length; i++) {
            rowCheckboxes[i].checked = headerCheckbox.checked;
        }
    });

    for (var i = 0; i < rowCheckboxes.length; i++) {
        rowCheckboxes[i].addEventListener('change', function() {
            if (!this.checked) {
                headerCheckbox.checked = false;
            } else {
                var allChecked = true;
                for (var j = 0; j < rowCheckboxes.length; j++) {
                    if (!rowCheckboxes[j].checked) {
                        allChecked = false;
                        break;
                    }
                }
                headerCheckbox.checked = allChecked;
            }
        });
    }
}

