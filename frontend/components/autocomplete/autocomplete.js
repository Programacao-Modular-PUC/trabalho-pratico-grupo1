import { getCidades } from "/frontend/assets/js/api.js";

async function atualizarLista(input, list) {
  const query = input.value.trim().toLowerCase();

  if (query?.length < 3) {
    list.innerHTML = "";
    list.classList.add("hidden");
    return;
  }

  const cidades = await getCidades(query);

  list.innerHTML = "";

  if (cidades.length === 0) {
    list.classList.add("hidden");
    return;
  }

  cidades.forEach((cidade) => {
    const e = document.createElement("span");
    e.textContent = cidade;
    e.addEventListener("click", () => {
      input.value = cidade;
      list.innerHTML = "";
      list.classList.add("hidden");
    });
    list.appendChild(e);
  });

  list.classList.remove("hidden");
}

async function initAutocomplete() {
  const input = document.getElementById("autocomplete-input");
  const list = document.getElementById("list");

  let timeout;

  input.addEventListener("input", () => {
    clearTimeout(timeout);

    timeout = setTimeout(() => {
      atualizarLista(input, list);
    }, 500);
  });

  input.addEventListener("focus", () => {
    if (list.children.length > 0) {
      list.classList.remove("hidden");
    }
  });

  document.addEventListener("click", (e) => {
    if (!e.target.closest(".form-group.autocomplete")) {
      list.classList.add("hidden");
    }
  });
}

initAutocomplete();
