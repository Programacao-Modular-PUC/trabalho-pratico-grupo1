import {
  loadComponent,
  dateInputConfig,
  showModal,
} from "/frontend/assets/js/utils.js";

async function onSubmit() {
  const form = document.getElementById("search-form");
  form.addEventListener("submit", (e) => {
    e.preventDefault();

    const form = {
      dataInicial: document.getElementById("data-inicial").value,
      dataFinal: document.getElementById("data-final").value,
      cidade: document
        .getElementById("autocomplete-input")
        .value.trim()
        .toLowerCase(),
    };

    if (!form.dataInicial && !form.dataFinal && !form.cidade) {
      showModal({
        type: "alert",
        message:
          "Por favor, preencha pelo menos um campo para realizar a busca.",
        onClose: () => console.log("Alerta fechado"),
      });
      return;
    }

    if (
      form.dataFinal &&
      form.dataInicial &&
      form.dataInicial > form.dataFinal
    ) {
      showModal({
        type: "alert",
        message:
          "A data de check-in não pode ser posterior à data de check-out.",
        onClose: () => console.log("Alerta fechado"),
      });
    }
  });
}

async function initFormHeader() {
  await loadComponent("autocomplete");
  await dateInputConfig({ inputId: "data-inicial", minDate: "today" });
  await dateInputConfig({ inputId: "data-final", minDate: "today" });

  onSubmit();
}

initFormHeader();
