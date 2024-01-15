const sidebarToggleTop = document.getElementById('sidebarToggleTop');
const sidebarnew = document.querySelector('.sidebarnew');
const body = document.querySelector('body'),
sidebar = body.querySelector('nav'),
toggle = body.querySelector(".toggle"),
modeSwitch = body.querySelector(".toggle-switch"),
modeText = body.querySelector(".mode-text");

toggle.addEventListener("click" , () =>{
sidebar.classList.toggle("close");
})

sidebarToggleTop.addEventListener('click', () => {
sidebar.classList.toggle('close');
});

modeSwitch.addEventListener("click", () => {
  const currentTheme = body.getAttribute("data-bs-theme");
  body.classList.toggle("dark");

  if (currentTheme === "dark") {
    body.setAttribute("data-bs-theme", "light");
    modeText.innerText = "Dark mode";
  } else {
    body.setAttribute("data-bs-theme", "dark");
    modeText.innerText = "Light mode";
  }

});