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

// Funktion für Dark Mode Update
const updateDarkMode = (isDarkMode) => {
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

// Ich hasse JS