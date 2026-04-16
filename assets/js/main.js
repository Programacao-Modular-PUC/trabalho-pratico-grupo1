import { initRouter, loadComponent } from "./utils.js";

async function init() {
  await loadComponent("header");

  window.location.hash = window.location.hash || "#home";

  initRouter();
}

init();
