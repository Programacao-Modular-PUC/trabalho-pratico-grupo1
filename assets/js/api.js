import { env } from "/frontend/env.js";
import { spinner, showModal } from "/frontend/assets/js/utils.js";

/**
  Futuramente alterar para api do backend pegando lista de cidades (enderecos) cadastradas, por enquanto usando api do nominatim
*/
export async function getCidades(query) {
  if (!query) return [];

  const url = `${env.cidadesApiUrl}?q=${encodeURIComponent(query)}&format=json&limit=10&addressdetails=1`;

  spinner.show();

  try {
    const response = await fetch(url, {
      headers: {
        "Accept-Language": "pt",
      },
    });
    const data = await response.json();

    const tiposValidos = [
      "city",
      "town",
      "village",
      "municipality",
      "hamlet",
      "suburb",
      "locality",
      "administrative",
    ];

    const filteredData = data?.filter((item) =>
      tiposValidos.includes(item.type),
    );

    const cidades =
      filteredData?.map((item) => {
        const city =
          item.name ||
          item.address?.city ||
          item.address?.town ||
          item.address?.village ||
          item.address?.municipality ||
          item.address?.hamlet ||
          item.address?.locality ||
          item.display_name.split(",")[0];

        const state = item.address?.state;
        const country = item.address?.country || "";

        return state ? `${city}, ${state}, ${country}` : `${city}, ${country}`;
      }) || [];

    const cidadesUnicas = [...new Set(cidades)].sort((a, b) =>
      a.localeCompare(b),
    );

    return cidadesUnicas;
  } catch (err) {
    console.error("Erro ao buscar cidades:", err);

    showModal({
        type: "alert",
        message: "Erro ao buscar cidades. Por favor, tente novamente.",
        onClose: () => {},
        onConfirm: () => {},
    });

    return [];
  } finally {
    spinner.hide();
  }
}
