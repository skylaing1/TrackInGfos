const sidebarToggleTop = document.getElementById('sidebarToggleTop');
const sidebarnew = document.querySelector('.sidebarnew');
const body = document.querySelector('body');
const sidebar = body.querySelector('nav');
const toggle = body.querySelector(".toggle");


const modeSwitch = document.getElementById("darkModeSwitch"); //Mode Switch dem Element mit der ID "darkModeSwitch" zuweisen

const modeText = body.querySelector(".mode-text");
const setDarkModePreference = (isDarkMode) => {
  document.cookie = `darkMode=${isDarkMode}; expires=Fri, 31 Dec 9999 23:59:59 GMT; path=/`;
};

// Funktion für Cookie ablesen
const getDarkModePreference = () => {
  const cookies = document.cookie.split('; ');
  for (const cookie of cookies) {
    const [name, value] = cookie.split('=');
    if (name === 'darkMode') {
      return value === 'true';
    }
  }
  return false;
};

// Funktion für Dark Mode Button
const toggleDarkMode = () => {
  console.log("Dark mode button clicked");
  const isDarkMode = !getDarkModePreference();
  setDarkModePreference(isDarkMode);
  updateDarkMode(isDarkMode);
};


// Function for Dark Mode Update
const updateDarkMode = (isDarkMode) => {
  var color = isDarkMode ? "rgb(255,255,255)" : "rgb(0,0,0)";
  if (isDarkMode) {
    body.setAttribute("data-bs-theme", "dark");
    modeText.innerText = "Light mode";
    body.classList.remove("dark");
    body.classList.add("dark");
    modeSwitch.checked = true;
  } else {
    body.setAttribute("data-bs-theme", "light");
    modeText.innerText = "Dark mode";
    body.classList.remove("dark");
    modeSwitch.checked = false;
  }


  for (var i = 0; i < window.chartInstances.length; i++) {
    var chart = window.chartInstances[i];
    // Update fontColor in all possible places
    if (chart.options) {
      chart.options.defaultFontColor = color;
      if (chart.options.title) {
        chart.options.title.fontColor = color;
      }
    }
    if (chart.options.scales) {
      if (chart.options.scales.xAxes && chart.options.scales.xAxes[0] && chart.options.scales.xAxes[0].ticks) {
        chart.options.scales.xAxes[0].ticks.fontColor = color;
      }
      if (chart.options.scales.yAxes && chart.options.scales.yAxes[0] && chart.options.scales.yAxes[0].ticks) {
        chart.options.scales.yAxes[0].ticks.fontColor = color;
      }
    }
    chart.update();
  }
};

// Initialisierung
const initialDarkModePreference = getDarkModePreference();

updateDarkMode(initialDarkModePreference);

// Event listeners
toggle.addEventListener("click", () => {
  sidebar.classList.toggle("close");
});

sidebarToggleTop.addEventListener('click', () => {
  sidebar.classList.toggle('close');
});

modeSwitch.addEventListener("click", toggleDarkMode);

// Lädt wenn HTML bereit
window.addEventListener("DOMContentLoaded", () => {
  const isDarkMode = getDarkModePreference();
  updateDarkMode(isDarkMode);
});

// Function to get cookie value
const getCookie = (name) => {
  const value = "; " + document.cookie;
  const parts = value.split("; " + name + "=");
  if (parts.length == 2) return parts.pop().split(";").shift();
}

// Function to listen for cookie changes
const listenForCookieChanges = (cookieName, callback) => {
  let lastCookie = document.cookie;
  setInterval(() => {
    let cookie = document.cookie;
    if (cookie !== lastCookie) {
      lastCookie = cookie;
      if (getCookie(cookieName) !== undefined) {
        callback();
      }
    }
  }, 1000); // check every second
};

// Listen for changes to the 'darkMode' cookie
listenForCookieChanges('darkMode', () => {
  const isDarkMode = getDarkModePreference();
  updateDarkMode(isDarkMode);
});


// Ich hasse JS