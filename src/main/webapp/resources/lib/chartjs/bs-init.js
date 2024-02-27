window.chartInstances = [];

document.addEventListener('DOMContentLoaded', function() {

	var charts = document.querySelectorAll('[data-bss-chart]');

	for (var chart of charts) {
		window.chartInstances.push(new Chart(chart, JSON.parse(chart.dataset.bssChart)));
	}
}, false);