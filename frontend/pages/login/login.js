import { spinner, showModal } from "/assets/js/utils.js";
import { login } from "/assets/js/http.js";
import { setSession } from "/assets/js/session.js";

const PAGE_BY_PAPEL = {
  CLIENTE: "client-dashboard",
  ANFITRIAO: "hospede",
};

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

  form?.addEventListener("submit", async (e) => {
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

    spinner.show();

    try {
      const usuario = await login(email, password);
      const session = setSession(usuario);
      window.location.hash = PAGE_BY_PAPEL[session.papel] ?? "home";
    } catch (err) {
      showModal({
        type: "alert",
        title: "Erro ao entrar",
        message: err.message || "E-mail ou senha incorretos. Verifique suas credenciais.",
      });
    } finally {
      spinner.hide();
    }
  });

  emailInput?.addEventListener("input", () => emailInput.classList.remove("error"));
  passwordInput?.addEventListener("input", () => {
    passwordInput.closest(".password-wrapper")?.classList.remove("error");
  });
}

initLogin();
