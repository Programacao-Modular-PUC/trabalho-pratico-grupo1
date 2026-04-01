export const spinner = {
  show() {
    document.getElementById("content")?.classList.add("hidden");
    document.getElementById("loader")?.classList.remove("hidden");
  },
  hide() {
    document.getElementById("content")?.classList.remove("hidden");
    document.getElementById("loader")?.classList.add("hidden");
  },
};

async function setComponentAsset(component, type) {
  const id = `${component}-${type}`;
  document.getElementById(id)?.remove();

  const basePath = `/frontend/components/${component}/${component}`;

  const config = {
    css: {
      create: () => {
        const el = document.createElement("link");
        el.rel = "stylesheet";
        el.href = `${basePath}.css`;
        return el;
      },
      parent: document.head,
    },
    js: {
      create: () => {
        const el = document.createElement("script");
        el.type = "module";
        el.src = `${basePath}.js`;
        return el;
      },
      parent: document.body,
    },
  };

  const asset = config[type];
  if (!asset) return;

  const el = asset.create();
  el.id = id;

  asset.parent.appendChild(el);
}

export async function loadComponent(component) {
  const element = document.getElementById(component);
  const path = `/frontend/components/${component}/${component}.html`;

  if (!element) return;

  try {
    const response = await fetch(path);
    if (!response.ok) throw new Error();

    const html = await response.text();
    element.innerHTML = html;

    await new Promise(requestAnimationFrame);

    await setComponentAsset(component, "css");
    await setComponentAsset(component, "js");
  } catch (err) {
    const message = `Erro ao carregar componente: ${component}.`;

    console.error(message, err);

    const action = () => {
      element.innerHTML = "";
      window.location.hash = "home";
    };

    showModal({
      type: "alert",
      message: message,
      onClose: action(),
      onConfirm: action(),
    });
  }
}

function setAsset(path, type) {
  const id = `page-${type}`;
  document.getElementById(id)?.remove();
  path = `${path}.${type}`;

  const config = {
    css: {
      create: () => {
        const el = document.createElement("link");
        el.rel = "stylesheet";
        el.href = path;
        return el;
      },
      parent: document.head,
    },
    js: {
      create: () => {
        const el = document.createElement("script");
        el.type = "module";
        el.src = path;
        return el;
      },
      parent: document.body,
    },
  };

  const asset = config[type];
  if (!asset) return;

  const el = asset.create();
  el.id = id;

  asset.parent.appendChild(el);
}

async function loadPage(page) {
  const element = document.getElementById("content");
  const path = `/frontend/pages/${page}/${page}`;

  if (!element) return;

  try {
    const response = await fetch(path + ".html");
    if (!response.ok) throw new Error();

    const html = await response.text();
    element.innerHTML = html;

    await new Promise(requestAnimationFrame);

    setAsset(path, "css");
    setAsset(path, "js");
  } catch (err) {
    const message = "Erro ao carregar página.";

    console.error(message, err);

    const action = () => {
      window.location.hash = "#home";
    };

    showModal({
      type: "alert",
      message: message,
      onClose: action(),
      onConfirm: action(),
    });
  }
}

function navigate() {
  document.addEventListener("click", (event) => {
    const link = event.target.closest(".link");
    if (!link) return;

    event.preventDefault();

    const page = link.dataset.page;
    window.location.hash = page;
  });
}

export function initRouter() {
  async function handleRoute() {
    spinner.show();
    let page = window.location.hash.substring(1);
    if (!page) page = "home";

    await loadPage(page);
    spinner.hide();
  }

  // escuta voltar/avançar
  window.addEventListener("hashchange", handleRoute);

  handleRoute();

  navigate();
}

export function showModal({
  type = "alert", // "alert" ou "confirm"
  title = "Alerta!",
  message = "",
  onConfirm = null, // função chamada ao confirmar (OK / SIM)
  onClose = null, // função chamada ao fechar (NÃO ou clicar fora)
} = {}) {
  const overlay = document.createElement("div");
  overlay.className = "modal-overlay";

  const modal = document.createElement("div");
  modal.className = "modal-box";
  modal.innerHTML = `
    <h2>${title}</h2>
    <p>${message}</p>
    <div class="modal-buttons">
      ${
        type === "alert"
          ? `<button class="confirm">OK</button>`
          : `
        <button class="confirm">SIM</button>
        <button class="cancel">NÃO</button>
      `
      }
    </div>
  `;

  overlay.appendChild(modal);
  document.body.appendChild(overlay);

  function closeModal(triggerConfirm = false) {
    document.body.removeChild(overlay);
    if (triggerConfirm && typeof onConfirm === "function") onConfirm();
    if (!triggerConfirm && typeof onClose === "function") onClose();
  }

  modal.querySelectorAll("button").forEach((btn) => {
    btn.addEventListener("click", () => {
      if (btn.classList.contains("confirm")) {
        closeModal(true);
      } else {
        closeModal(false);
      }
    });
  });

  overlay.addEventListener("click", (e) => {
    if (e.target === overlay) closeModal(false);
  });

  return overlay;
}

function loadFlatpickr(inputId, minDate, maxDate) {
  flatpickr("#" + inputId, {
    dateFormat: "d/m/Y",
    maxDate: maxDate || null,
    minDate: minDate || null,
    allowInput: true,
    locale: {
      firstDayOfWeek: 0,
      weekdays: {
        shorthand: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"],
        longhand: [
          "Domingo",
          "Segunda-feira",
          "Terça-feira",
          "Quarta-feira",
          "Quinta-feira",
          "Sexta-feira",
          "Sábado",
        ],
      },
      months: {
        shorthand: [
          "Jan",
          "Fev",
          "Mar",
          "Abr",
          "Mai",
          "Jun",
          "Jul",
          "Ago",
          "Set",
          "Out",
          "Nov",
          "Dez",
        ],
        longhand: [
          "Janeiro",
          "Fevereiro",
          "Março",
          "Abril",
          "Maio",
          "Junho",
          "Julho",
          "Agosto",
          "Setembro",
          "Outubro",
          "Novembro",
          "Dezembro",
        ],
      },
    },
  });
}

export function dateInputConfig({ inputId, minDate, maxDate } = {}) {
  const input = document.getElementById(inputId);

  if (!input) return;

  loadFlatpickr(inputId, minDate, maxDate);

  input.addEventListener("input", (e) => {
    let value = e.target.value.replace(/\D/g, "");

    if (value.length > 8) value = value.slice(0, 8);

    if (value.length > 4) {
      value = value.replace(/(\d{2})(\d{2})(\d{1,4})/, "$1/$2/$3");
    } else if (value.length > 2) {
      value = value.replace(/(\d{2})(\d{1,2})/, "$1/$2");
    }

    e.target.value = value;
  });

  input.addEventListener("blur", () => {
    const [dia, mes, ano] = input.value.split("/");

    if (!dia || !mes || !ano) return;

    if (dia > 31 || mes > 12) {
      alert("Data inválida");
      input.value = "";
    }

    if (maxDate) {
      const maxDateObj = new Date(maxDate);
      const inputDate = new Date(`${ano}-${mes}-${dia}`);

      if (inputDate > maxDateObj) {
        input.value = "";
      }
    } else if (minDate) {
      const minDateObj = new Date(minDate);
      const inputDate = new Date(`${ano}-${mes}-${dia}`);

      if (inputDate < minDateObj) {
        input.value = "";
      }
    }
  });
}
