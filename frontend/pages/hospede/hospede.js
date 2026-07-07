import { showModal } from "/assets/js/utils.js";
import { StatusAluguel } from "/assets/enums/status-aluguel.js";
import { getAnfitriao, listResidencias, listQuartos, listAlugueis } from "/assets/js/http.js";
import { requireRole } from "/assets/js/session.js";

const tipoLabel = {
  INDIVIDUAL: "Quarto Individual",
  CASAL: "Quarto Casal",
  SUITE: "Suíte",
};

// O backend não modela disponibilidade por quarto (só por intervalo de datas),
// então não há um status DISPONIVEL/OCUPADO real para exibir aqui.
const statusQuartoLabel = {
  DISPONIVEL: "Disponível",
};

async function getHostData(anfitriaoId) {
  const [anfitriao, todasResidencias, todosQuartos, todosAlugueis] = await Promise.all([
    getAnfitriao(anfitriaoId),
    listResidencias(),
    listQuartos(),
    listAlugueis(),
  ]);

  const residencias = todasResidencias.filter((r) => r.anfitriaoId === anfitriaoId);
  const residenciaIds = new Set(residencias.map((r) => r.id));
  const residenciasById = new Map(residencias.map((r) => [r.id, r]));
  const quartos = todosQuartos.filter((q) => residenciaIds.has(q.residenciaId));
  const quartosById = new Map(quartos.map((q) => [q.id, q]));

  const reservasDoHost = todosAlugueis.filter((a) => residenciaIds.has(a.residencia?.id));

  return {
    nome: anfitriao.nome,
    cpf: anfitriao.CPF,
    email: anfitriao.email,
    telefone: anfitriao.telefone,
    avaliacao: null,
    membro_desde: null,
    endereco: {
      cep: anfitriao.endereco?.CEP ?? "",
      rua: anfitriao.endereco?.rua ?? "",
      numero: anfitriao.endereco?.numero ?? "",
      bairro: anfitriao.endereco?.bairro ?? "",
      cidade: anfitriao.endereco?.cidade ?? "",
      estado: anfitriao.endereco?.estado ?? "",
    },
    quartos: quartos.map((q) => ({
      id: q.id,
      tipo: q.tipo,
      cidade: residenciasById.get(q.residenciaId)?.cidade ?? "",
      estado: residenciasById.get(q.residenciaId)?.estado ?? "",
      status: "DISPONIVEL",
      preco: q.valorBase,
    })),
    reservas: reservasDoHost.map((a) => ({
      id: a.id,
      hospede: a.cliente?.nome ?? "—",
      quarto_tipo: quartosById.get(a.quarto?.id)?.tipo ?? "—",
      quarto_cidade: residenciasById.get(a.residencia?.id)?.cidade ?? "",
      data_inicio: a.dataPrevistaEntrada?.split("T")[0],
      data_fim: a.dataPrevistaSaida?.split("T")[0],
      status: a.status,
      valor: a.valorFinal,
    })),
    pagamentos: reservasDoHost
      .filter((a) => a.pagamento)
      .map((a) => ({
        id: a.id,
        hospede: a.cliente?.nome ?? "—",
        valor: a.pagamento.valor,
        data: a.pagamento.dataPagamento,
        status: a.pagamento.status,
      })),
  };
}

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
  document.getElementById("host-avaliacao").textContent = host.avaliacao ? `★ ${host.avaliacao}` : "—";
  document.getElementById("host-quartos-count").textContent = `${host.quartos.length} imóveis`;
  document.getElementById("host-membro-desde").textContent = host.membro_desde ? `Membro desde ${host.membro_desde}` : "—";

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
  const session = requireRole("ANFITRIAO");
  if (!session) return;

  let hostData;

  try {
    hostData = await getHostData(session.anfitriaoId);
  } catch (err) {
    showModal({
      type: "alert",
      title: "Erro ao carregar dados",
      message: err.message || "Não foi possível carregar seus dados.",
    });
    return;
  }

  onItemClick();
  renderHostProfile(hostData);
  loadQuartos(hostData.quartos);
  loadReservas(hostData.reservas);
  loadPagamentos(hostData.pagamentos);
  buttonsActions(hostData);
  inputsActions();
  addQuartoButton();
}

initHospede();
