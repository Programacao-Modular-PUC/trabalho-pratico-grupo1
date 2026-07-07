import { spinner, showModal } from "/assets/js/utils.js";
import { registrar } from "/assets/js/http.js";
import { setSession } from "/assets/js/session.js";

const PAGE_BY_PAPEL = {
  CLIENTE: "client-dashboard",
  ANFITRIAO: "hospede",
};

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
  } else if (input.id === "estado") {
    input.value = input.value.toUpperCase().replace(/[^A-Z]/g, "").slice(0, 2);
  }
}

function inputValidator(input) {
  if (input.id === "cpf") return /^\d{3}\.\d{3}\.\d{3}-\d{2}$/.test(input.value);
  if (input.id === "emailLogin") return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input.value);
  if (input.id === "telefone") return /^\(\d{2}\) \d{4,5}-\d{4}$/.test(input.value);
  if (input.id === "cep") return /^\d{5}-\d{3}$/.test(input.value);
  if (input.id === "estado") return /^[A-Z]{2}$/.test(input.value);
  if (input.id === "senha") return input.value.length >= 6;
  return input.value.trim() !== "";
}

async function initCadastro() {
  const form = document.getElementById("cadastro-form");
  const senhaInput = document.getElementById("senha");
  const toggleBtn = document.getElementById("toggle-password");

  const fieldIds = [
    "nome", "cpf", "telefone", "emailLogin", "senha",
    "cep", "numero", "rua", "bairro", "cidade", "estado",
  ];

  toggleBtn?.addEventListener("click", () => {
    const isPassword = senhaInput.type === "password";
    senhaInput.type = isPassword ? "text" : "password";
    toggleBtn.textContent = isPassword ? "Ocultar" : "Mostrar";
  });

  fieldIds.forEach((id) => {
    const input = document.getElementById(id);
    input?.addEventListener("input", () => {
      inputMask(input);
      input.classList.remove("error");
      input.closest(".password-wrapper")?.classList.remove("error");
    });
  });

  form?.addEventListener("submit", async (e) => {
    e.preventDefault();

    let valid = true;

    fieldIds.forEach((id) => {
      const input = document.getElementById(id);
      if (!input) return;

      if (!inputValidator(input)) {
        valid = false;
        input.classList.add("error");
        input.closest(".password-wrapper")?.classList.add("error");
      }
    });

    if (!valid) return;

    const papel = form.querySelector('input[name="papel"]:checked').value;

    const dto = {
      emailLogin: document.getElementById("emailLogin").value.trim(),
      senha: senhaInput.value,
      papel,
      nome: document.getElementById("nome").value.trim(),
      CPF: document.getElementById("cpf").value.replace(/\D/g, ""),
      telefone: document.getElementById("telefone").value,
      endereco: {
        rua: document.getElementById("rua").value.trim(),
        numero: document.getElementById("numero").value.trim(),
        bairro: document.getElementById("bairro").value.trim(),
        CEP: document.getElementById("cep").value,
        cidade: document.getElementById("cidade").value.trim(),
        estado: document.getElementById("estado").value.trim(),
      },
    };

    spinner.show();

    try {
      const usuario = await registrar(dto);
      const session = setSession(usuario);
      window.location.hash = PAGE_BY_PAPEL[session.papel] ?? "home";
    } catch (err) {
      showModal({
        type: "alert",
        title: "Erro ao criar conta",
        message: err.message || "Não foi possível criar sua conta. Tente novamente.",
      });
    } finally {
      spinner.hide();
    }
  });
}

initCadastro();
