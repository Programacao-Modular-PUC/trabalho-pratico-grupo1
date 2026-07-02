const STORAGE_KEY = "reservasja_session";

export function setSession(usuario) {
  const session = {
    id: usuario.id,
    emailLogin: usuario.emailLogin,
    papel: usuario.papel,
    nome: usuario.cliente?.nome ?? usuario.anfitriao?.nome ?? "",
    clienteId: usuario.cliente?.id ?? null,
    anfitriaoId: usuario.anfitriao?.id ?? null,
  };

  localStorage.setItem(STORAGE_KEY, JSON.stringify(session));
  return session;
}

export function getSession() {
  const raw = localStorage.getItem(STORAGE_KEY);
  if (!raw) return null;

  try {
    return JSON.parse(raw);
  } catch {
    return null;
  }
}

export function clearSession() {
  localStorage.removeItem(STORAGE_KEY);
}

export function requireRole(papel) {
  const session = getSession();

  if (!session || session.papel !== papel) {
    window.location.hash = "login";
    return null;
  }

  return session;
}
