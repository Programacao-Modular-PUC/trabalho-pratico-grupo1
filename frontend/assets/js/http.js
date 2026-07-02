import { env } from "/env.js";

async function request(path, options = {}) {
  const response = await fetch(`${env.apiBaseUrl}${path}`, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });

  if (!response.ok) {
    let mensagem = `Erro ao comunicar com o servidor (${response.status}).`;

    try {
      const body = await response.json();
      if (body?.mensagem) mensagem = body.mensagem;
    } catch {
      // corpo vazio ou não-JSON, mantém mensagem genérica
    }

    throw new Error(mensagem);
  }

  if (response.status === 204) return null;

  return response.json();
}

export function login(emailLogin, senha) {
  return request("/auth/login", {
    method: "POST",
    body: JSON.stringify({ emailLogin, senha }),
  });
}

export function registrar(dto) {
  return request("/auth/registro", {
    method: "POST",
    body: JSON.stringify(dto),
  });
}

export function getCliente(id) {
  return request(`/clientes/${id}`);
}

export function getAnfitriao(id) {
  return request(`/anfitrioes/${id}`);
}

export function listAlugueisCliente(clienteId) {
  return request(`/api/alugueis/cliente/${clienteId}`);
}

export function listAlugueis() {
  return request("/api/alugueis");
}

export function listResidencias() {
  return request("/residencias");
}

export function listQuartos() {
  return request("/quartos");
}
