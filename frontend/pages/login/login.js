import { showModal } from "/assets/js/utils.js";

const USERS = [
  { email: "lucas.silva@example.com", password: "123456", page: "client-dashboard" },
  { email: "maria.santos@example.com", password: "123456", page: "hospede" },
];

async function initLogin() {
  const form = document.getElementById("login-form");
  const emailInput = document.getElementById("email");
  const passwordInput = document.getElementById("password");
  const toggleBtn = document.getElementById("toggle-password");
  const forgotBtn = document.getElementById("forgot-btn");

  toggleBtn?.addEventListener("click", () => {
    const isPassword = passwordInput.type === "password";
    passwordInput.type = isPassword ? "text" : "password";
    toggleBtn.textContent = isPassword ? "Ocultar" : "Mostrar";
  });

  forgotBtn?.addEventListener("click", () => {
    showModal({
      type: "alert",
      title: "Recuperar senha",
      message: "Funcionalidade ainda não disponível.",
    });
  });

  form?.addEventListener("submit", (e) => {
    e.preventDefault();

    const email = emailInput.value.trim();
    const password = passwordInput.value;
    const passwordWrapper = passwordInput.closest(".password-wrapper");
    let valid = true;

    if (!email) {
      emailInput.classList.add("error");
      valid = false;
    } else {
      emailInput.classList.remove("error");
    }

    if (!password) {
      passwordWrapper?.classList.add("error");
      valid = false;
    } else {
      passwordWrapper?.classList.remove("error");
    }

    if (!valid) return;

    const user = USERS.find((u) => u.email === email && u.password === password);
    if (user) {
      window.location.hash = user.page;
    } else {
      showModal({
        type: "alert",
        title: "Erro ao entrar",
        message: "E-mail ou senha incorretos. Verifique suas credenciais.",
      });
    }
  });

  emailInput?.addEventListener("input", () => emailInput.classList.remove("error"));
  passwordInput?.addEventListener("input", () => {
    passwordInput.closest(".password-wrapper")?.classList.remove("error");
  });
}

initLogin();
