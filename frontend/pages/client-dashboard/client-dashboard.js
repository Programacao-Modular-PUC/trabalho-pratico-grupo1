import { showModal } from "/assets/js/utils.js";
import { StatusAluguel } from "/assets/enums/status-aluguel.js";
import { getCliente, listAlugueisCliente, listQuartos } from "/assets/js/http.js";
import { requireRole } from "/assets/js/session.js";

function toClientViewModel(cliente) {
  return {
    nome: cliente.nome,
    cpf: cliente.CPF,
    email: cliente.email,
    telefone: cliente.telefone,
    endereco: {
      cep: cliente.endereco?.CEP ?? "",
      rua: cliente.endereco?.rua ?? "",
      numero: cliente.endereco?.numero ?? "",
      bairro: cliente.endereco?.bairro ?? "",
      cidade: cliente.endereco?.cidade ?? "",
      estado: cliente.endereco?.estado ?? "",
    },
  };
}

function toTripViewModel(aluguel, quartosById) {
  const tipo = quartosById.get(aluguel.quarto?.id)?.tipo ?? "—";

  return {
    quarto: {
      tipo,
      residencia: aluguel.quarto?.residencia,
    },
    data_inicio: aluguel.dataPrevistaEntrada,
    data_fim: aluguel.dataPrevistaSaida,
    status: aluguel.status,
    pagamento: aluguel.pagamento,
  };
}

async function getClientData(clienteId) {
  const [cliente, alugueis, quartos] = await Promise.all([
    getCliente(clienteId),
    listAlugueisCliente(clienteId),
    listQuartos(),
  ]);

  const quartosById = new Map(quartos.map((q) => [q.id, q]));
  const trips = alugueis.map((aluguel) => toTripViewModel(aluguel, quartosById));

  return { ...toClientViewModel(cliente), alugueis: trips };
}

function formatDate(dateStr) {
  const date = new Date(dateStr);
  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
}

function loadClientTrips(alugueis) {
  const tripsContainer = document.querySelector(".trips");

  const trips = alugueis.map((trip) => {
    return `
      <div class="trip">
        <h3>${trip.quarto.residencia.endereco.cidade} / ${trip.quarto.tipo}</h3>
        <p>${formatDate(trip.data_inicio)} - ${formatDate(trip.data_fim)}</p>
        <span class="${trip.status.toLowerCase()}">${StatusAluguel[trip.status].toUpperCase()}</span>
        <button class="details">VER DETALHES</button>
      </div>
    `;
  });

  if (trips.length === 0) {
    tripsContainer.innerHTML = `
      <div class="no-trips">
        <img src="/assets/icons/briefcase.svg" alt="briefcase" />
        <p>Você ainda não tem viagens reservadas.</p>
      </div>
    `;
    return;
  } else {
    tripsContainer.innerHTML = trips.join("");
  }
}

function loadClientPayments(alugueis) {
  const paymentsContainer = document.querySelector(".payments");

  const payments = alugueis.filter((aluguel) => aluguel.pagamento).map((aluguel) => {
    const status = aluguel.pagamento.status;

    const shouldShowDate = status === "PAGO" || status === "ESTORNADO";

    return `
      <div class="payment">
        <h3>${aluguel.quarto.residencia.endereco.cidade} / ${aluguel.quarto.tipo}</h3>
        ${
          shouldShowDate
            ? `<p>${formatDate(aluguel.pagamento.dataPagamento)}</p>`
            : ""
        }
        <span class="${status.toLowerCase()}">${status}</span>
        <p>R$ ${aluguel.pagamento.valor.toFixed(2).replace(/\./g, ",")}</p>
      </div>
    `;
  });

  if (payments.length === 0) {
    paymentsContainer.innerHTML = `
      <div class="no-payments">
        <img src="/assets/icons/credit-card.svg" alt="credit-card" />
        <p>Você ainda não tem pagamentos registrados.</p>
      </div>
    `;
    return;
  } else {
    paymentsContainer.innerHTML = payments.join("");
  }
}

function renderClientData(client) {
  const nameInput = document.getElementById("nome");
  const cpfInput = document.getElementById("cpf");
  const emailInput = document.getElementById("email");
  const phoneInput = document.getElementById("telefone");
  const cepInput = document.getElementById("cep");
  const streetInput = document.getElementById("rua");
  const numberInput = document.getElementById("numero");
  const neighborhoodInput = document.getElementById("bairro");
  const cityInput = document.getElementById("cidade");
  const stateInput = document.getElementById("estado");

  nameInput.value = client.nome;
  nameInput.placeholder = client.nome;
  cpfInput.value = client.cpf;
  cpfInput.placeholder = client.cpf;
  emailInput.value = client.email;
  emailInput.placeholder = client.email;
  phoneInput.value = client.telefone;
  phoneInput.placeholder = client.telefone;
  cepInput.value = client.endereco.cep;
  cepInput.placeholder = client.endereco.cep;
  streetInput.value = client.endereco.rua;
  streetInput.placeholder = client.endereco.rua;
  numberInput.value = client.endereco.numero;
  numberInput.placeholder = client.endereco.numero;
  neighborhoodInput.value = client.endereco.bairro;
  neighborhoodInput.placeholder = client.endereco.bairro;
  cityInput.value = client.endereco.cidade;
  cityInput.placeholder = client.endereco.cidade;
  stateInput.value = client.endereco.estado;
  stateInput.placeholder = client.endereco.estado;
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
  } else if (input.id === "numero") {
    input.value = input.value.replace(/\D/g, "").replace(/^0+/, "");
  }
}

function inputValidator(input) {
  if (input.id === "cpf") {
    const cpfRegex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/;
    return cpfRegex.test(input.value);
  } else if (input.id === "email") {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(input.value);
  } else if (input.id === "telefone") {
    const phoneRegex = /^\(\d{2}\) \d{4,5}-\d{4}$/;
    return phoneRegex.test(input.value);
  } else if (input.id === "cep") {
    const cepRegex = /^\d{5}-\d{3}$/;
    return cepRegex.test(input.value);
  } else {
    return input.value.trim() !== "";
  }
}

function buttonsActions(clientData) {
  const resetButton = document.querySelector(".buttons .reset");
  const saveButton = document.querySelector(".buttons .save");

  resetButton.addEventListener("click", () => {
    renderClientData(clientData);

    saveButton.disabled = true;
    const inputs = document.querySelectorAll(".content input");
    inputs.forEach((input) => input.classList.remove("changed"));
  });

  saveButton.addEventListener("click", () => {
    showModal({
      type: "confirm",
      message: "Deseja salvar as alterações?",
      onClose: () => {},
      onConfirm: () => {
        clientData = {
          nome: document.getElementById("nome").value,
          cpf: document.getElementById("cpf").value,
          email: document.getElementById("email").value,
          telefone: document.getElementById("telefone").value,
          endereco: {
            cep: document.getElementById("cep").value,
            rua: document.getElementById("rua").value,
            numero: document.getElementById("numero").value,
            bairro: document.getElementById("bairro").value,
            cidade: document.getElementById("cidade").value,
            estado: document.getElementById("estado").value,
          },
        };

        saveButton.disabled = true;
        window.location.reload();
      },
    });
  });
}

function checkIfFormChanged() {
  const inputs = document.querySelectorAll(".content input");
  const saveButton = document.querySelector(".buttons .save");

  let hasChange = false;
  let allValid = true;

  inputs.forEach((input) => {
    const isValid = inputValidator(input);
    const isChanged = input.value !== input.placeholder;

    if (!isValid) {
      allValid = false;
    }

    if (isChanged) {
      hasChange = true;
      input.classList.add("changed");
    } else {
      input.classList.remove("changed");
    }
  });

  saveButton.disabled = !(hasChange && allValid);
}

function inputsActions() {
  const inputs = document.querySelectorAll(".content input");
  const saveButton = document.querySelector(".buttons .save");

  inputs.forEach((input) => {
    input.addEventListener("input", () => {
      inputMask(input);
      checkIfFormChanged();
    });
  });
}

function toggleActiveItem(selectedId) {
  const items = document.querySelectorAll(".sidebar .item");
  const contentSections = document.querySelectorAll(".content > div");

  items.forEach((item) => {
    if (item.id === selectedId) {
      item.classList.add("selected");
    } else {
      item.classList.remove("selected");
    }
  });

  contentSections.forEach((section) => {
    if (section.classList.contains(selectedId)) {
      section.classList.add("selected");
    } else {
      section.classList.remove("selected");
    }
  });
}

function onItemClick() {
  const items = document.querySelectorAll(".sidebar .item");

  items.forEach((item) => {
    item.addEventListener("click", async () => {
      toggleActiveItem(item.id);
    });
  });
}

async function initClientDashboard() {
  const session = requireRole("CLIENTE");
  if (!session) return;

  let clientData;

  try {
    clientData = await getClientData(session.clienteId);
  } catch (err) {
    showModal({
      type: "alert",
      title: "Erro ao carregar dados",
      message: err.message || "Não foi possível carregar seus dados.",
    });
    return;
  }

  onItemClick();
  renderClientData(clientData);
  loadClientTrips(clientData.alugueis);
  loadClientPayments(clientData.alugueis);
  buttonsActions(clientData);
  inputsActions();
}

initClientDashboard();
