import { showModal } from "/assets/js/utils.js";
import { StatusAluguel } from "/assets/enums/status-aluguel.js";

const hostMock = {
  nome: "Maria Santos",
  cpf: "987.654.321-00",
  email: "maria.santos@example.com",
  telefone: "(73) 99876-5432",
  avaliacao: 4.8,
  membro_desde: "Março de 2022",
  endereco: {
    cep: "45520-000",
    rua: "Rua da Praia",
    numero: "42",
    bairro: "Centro",
    cidade: "Maraú",
    estado: "BA",
  },
  quartos: [
    { id: 1, tipo: "INDIVIDUAL", cidade: "Maraú", estado: "BA", status: "DISPONIVEL", preco: 120.0 },
    { id: 2, tipo: "CASAL", cidade: "Barra Grande", estado: "BA", status: "OCUPADO", preco: 200.0 },
    { id: 3, tipo: "SUITE", cidade: "Maraú", estado: "BA", status: "DISPONIVEL", preco: 280.0 },
  ],
  reservas: [
    {
      id: 1, hospede: "Lucas Silva",
      quarto_tipo: "INDIVIDUAL", quarto_cidade: "Maraú",
      data_inicio: "2025-06-20", data_fim: "2025-06-25",
      status: "RESERVADO", valor: 600.0,
    },
    {
      id: 2, hospede: "Ana Costa",
      quarto_tipo: "SUITE", quarto_cidade: "Maraú",
      data_inicio: "2025-07-01", data_fim: "2025-07-07",
      status: "EM_ANDAMENTO", valor: 1680.0,
    },
    {
      id: 3, hospede: "Pedro Alves",
      quarto_tipo: "CASAL", quarto_cidade: "Barra Grande",
      data_inicio: "2025-05-10", data_fim: "2025-05-15",
      status: "CONCLUIDO", valor: 1000.0,
    },
  ],
  pagamentos: [
    { id: 1, hospede: "Ana Costa", valor: 1680.0, data: "2025-06-01", status: "PENDENTE" },
    { id: 2, hospede: "Pedro Alves", valor: 1000.0, data: "2025-05-10", status: "PAGO" },
    { id: 3, hospede: "Carla Nunes", valor: 600.0, data: "2025-04-15", status: "PAGO" },
  ],
};

const tipoLabel = {
  INDIVIDUAL: "Quarto Individual",
  CASAL: "Quarto Casal",
  SUITE: "Suíte",
};

const statusQuartoLabel = {
  DISPONIVEL: "Disponível",
  OCUPADO: "Ocupado",
};

function formatDate(dateStr) {
  const [year, month, day] = dateStr.split("-");
  return `${day}/${month}/${year}`;
}

function formatCurrency(value) {
  return `R$ ${value.toFixed(2).replace(".", ",").replace(/\B(?=(\d{3})+(?!\d))/g, ".")}`;
}

function getInitials(name) {
  return name
    .split(" ")
    .filter((_, i, arr) => i === 0 || i === arr.length - 1)
    .map((n) => n[0])
    .join("")
    .toUpperCase();
}

function renderHostProfile(host) {
  document.getElementById("host-avatar").textContent = getInitials(host.nome);
  document.getElementById("host-nome-display").textContent = host.nome;
  document.getElementById("host-avaliacao").textContent = `★ ${host.avaliacao}`;
  document.getElementById("host-quartos-count").textContent = `${host.quartos.length} imóveis`;
  document.getElementById("host-membro-desde").textContent = `Membro desde ${host.membro_desde}`;

  const fields = {
    nome: host.nome,
    cpf: host.cpf,
    email: host.email,
    telefone: host.telefone,
    cep: host.endereco.cep,
    rua: host.endereco.rua,
    numero: host.endereco.numero,
    bairro: host.endereco.bairro,
    cidade: host.endereco.cidade,
    estado: host.endereco.estado,
  };

  for (const [id, value] of Object.entries(fields)) {
    const input = document.getElementById(id);
    if (input) {
      input.value = value;
      input.placeholder = value;
    }
  }
}

function loadQuartos(quartos) {
  const container = document.querySelector(".quartos-list");

  if (!quartos.length) {
    container.innerHTML = `
      <div class="empty-state">
        <img src="/assets/icons/map-pin-house.svg" alt="" />
        <p>Você ainda não tem quartos cadastrados.</p>
      </div>`;
    return;
  }

  container.innerHTML = quartos
    .map(
      (q) => `
    <div class="quarto-card">
      <h3>${tipoLabel[q.tipo] || q.tipo}</h3>
      <p>${q.cidade}, ${q.estado}</p>
      <p class="preco">${formatCurrency(q.preco)} / noite</p>
      <span class="status ${q.status.toLowerCase()}">${statusQuartoLabel[q.status]}</span>
      <button>EDITAR</button>
    </div>`
    )
    .join("");

  container.querySelectorAll("button").forEach((btn) => {
    btn.addEventListener("click", () => {
      showModal({ type: "alert", title: "Editar Quarto", message: "Funcionalidade ainda não disponível." });
    });
  });
}

function loadReservas(reservas) {
  const container = document.querySelector(".reservas-list");

  if (!reservas.length) {
    container.innerHTML = `
      <div class="empty-state">
        <img src="/assets/icons/calendar-check.svg" alt="" />
        <p>Você ainda não tem reservas recebidas.</p>
      </div>`;
    return;
  }

  container.innerHTML = reservas
    .map(
      (r) => `
    <div class="reserva-card">
      <h3>${r.hospede}</h3>
      <p>${tipoLabel[r.quarto_tipo] || r.quarto_tipo} — ${r.quarto_cidade}</p>
      <p>${formatDate(r.data_inicio)} → ${formatDate(r.data_fim)}</p>
      <p>${formatCurrency(r.valor)}</p>
      <span class="${r.status.toLowerCase()}">${StatusAluguel[r.status] ?? r.status}</span>
      <button>VER DETALHES</button>
    </div>`
    )
    .join("");

  container.querySelectorAll("button").forEach((btn) => {
    btn.addEventListener("click", () => {
      showModal({ type: "alert", title: "Detalhes da Reserva", message: "Funcionalidade ainda não disponível." });
    });
  });
}

function loadPagamentos(pagamentos) {
  const container = document.querySelector(".pagamentos-list");

  if (!pagamentos.length) {
    container.innerHTML = `
      <div class="empty-state">
        <img src="/assets/icons/credit-card.svg" alt="" />
        <p>Você ainda não tem pagamentos registrados.</p>
      </div>`;
    return;
  }

  container.innerHTML = pagamentos
    .map(
      (p) => `
    <div class="pagamento-card">
      <h3>${p.hospede}</h3>
      ${p.status !== "PENDENTE" ? `<p>${formatDate(p.data)}</p>` : ""}
      <span class="${p.status.toLowerCase()}">${p.status}</span>
      <p>${formatCurrency(p.valor)}</p>
    </div>`
    )
    .join("");
}

function toggleActiveItem(selectedId) {
  document.querySelectorAll(".hospede-dashboard .sidebar .item").forEach((item) => {
    item.classList.toggle("selected", item.id === selectedId);
  });
  document.querySelectorAll(".hospede-dashboard .content > div").forEach((section) => {
    section.classList.toggle("selected", section.classList.contains(selectedId));
  });
}

function onItemClick() {
  document.querySelectorAll(".hospede-dashboard .sidebar .item").forEach((item) => {
    item.addEventListener("click", () => toggleActiveItem(item.id));
  });
}

function inputMask(input) {
  if (input.id === "cpf") {
    input.value = input.value
      .replace(/\D/g, "")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d{1,2})/, "$1-$2")
      .replace(/(-\d{2})\d+?$/, "$1");
  } else if (input.id === "telefone") {
    input.value = input.value
      .replace(/\D/g, "")
      .replace(/(\d{2})(\d)/, "($1) $2")
      .replace(/(\d{4,5})(\d{4})/, "$1-$2")
      .replace(/(-\d{4})\d+?$/, "$1");
  } else if (input.id === "cep") {
    input.value = input.value
      .replace(/\D/g, "")
      .replace(/(\d{5})(\d)/, "$1-$2")
      .replace(/(-\d{3})\d+?$/, "$1");
  }
}

function inputValidator(input) {
  if (input.id === "cpf") return /^\d{3}\.\d{3}\.\d{3}-\d{2}$/.test(input.value);
  if (input.id === "email") return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input.value);
  if (input.id === "telefone") return /^\(\d{2}\) \d{4,5}-\d{4}$/.test(input.value);
  if (input.id === "cep") return /^\d{5}-\d{3}$/.test(input.value);
  return input.value.trim() !== "";
}

function checkIfFormChanged() {
  const inputs = document.querySelectorAll(".hospede-dashboard .content input");
  const saveButton = document.querySelector(".hospede-dashboard .buttons .save");
  let hasChange = false;
  let allValid = true;

  inputs.forEach((input) => {
    const isValid = inputValidator(input);
    const isChanged = input.value !== input.placeholder;
    if (!isValid) allValid = false;
    if (isChanged) {
      hasChange = true;
      input.classList.add("changed");
    } else {
      input.classList.remove("changed");
    }
  });

  saveButton.disabled = !(hasChange && allValid);
}

function buttonsActions(hostData) {
  const resetButton = document.querySelector(".hospede-dashboard .buttons .reset");
  const saveButton = document.querySelector(".hospede-dashboard .buttons .save");

  resetButton.addEventListener("click", () => {
    renderHostProfile(hostData);
    saveButton.disabled = true;
    document.querySelectorAll(".hospede-dashboard .content input").forEach((input) =>
      input.classList.remove("changed")
    );
  });

  saveButton.addEventListener("click", () => {
    showModal({
      type: "confirm",
      message: "Deseja salvar as alterações?",
      onConfirm: () => {
        saveButton.disabled = true;
        window.location.reload();
      },
      onClose: () => {},
    });
  });
}

function inputsActions() {
  document.querySelectorAll(".hospede-dashboard .content input").forEach((input) => {
    input.addEventListener("input", () => {
      inputMask(input);
      checkIfFormChanged();
    });
  });
}

function addQuartoButton() {
  document.getElementById("btn-add-quarto")?.addEventListener("click", () => {
    showModal({ type: "alert", title: "Adicionar Quarto", message: "Funcionalidade ainda não disponível." });
  });
}

async function initHospede() {
  onItemClick();
  renderHostProfile(hostMock);
  loadQuartos(hostMock.quartos);
  loadReservas(hostMock.reservas);
  loadPagamentos(hostMock.pagamentos);
  buttonsActions(hostMock);
  inputsActions();
  addQuartoButton();
}

initHospede();
