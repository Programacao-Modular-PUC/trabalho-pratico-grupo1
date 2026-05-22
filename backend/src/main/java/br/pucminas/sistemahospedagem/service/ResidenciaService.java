package br.pucminas.sistemahospedagem.service;

import br.pucminas.sistemahospedagem.dto.residencia.ResidenciaRequestDTO;
import br.pucminas.sistemahospedagem.model.Anfitriao;
import br.pucminas.sistemahospedagem.model.Residencia;
import br.pucminas.sistemahospedagem.model.Quarto;
import br.pucminas.sistemahospedagem.repository.ResidenciaRepository;
import br.pucminas.sistemahospedagem.repository.AnfitriaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidenciaService {

    private final ResidenciaRepository residenciaRepository;
    private final AnfitriaoRepository anfitriaoRepository;

    public List<Residencia> listarTodas() {
        return residenciaRepository.findAll();
    }

    public Residencia buscarPorId(Long id) {
        return residenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Residência não encontrada"));
    }

    public Residencia criar(ResidenciaRequestDTO dto) {
        Anfitriao anfitriao = anfitriaoRepository.findById(dto.anfitriaoId())
                .orElseThrow(() -> new EntityNotFoundException("Anfitrião não encontrado"));

        Residencia residencia = new Residencia();
        residencia.setTelefone(dto.telefone());
        residencia.setEmail(dto.email());
        residencia.setEndereco(dto.endereco());
        residencia.setAnfitriao(anfitriao);

        return residenciaRepository.save(residencia);
    }

    public Residencia atualizar(Long id, ResidenciaRequestDTO dto) {
        Residencia residencia = buscarPorId(id);

        residencia.setTelefone(dto.telefone());
        residencia.setEmail(dto.email());
        residencia.setEndereco(dto.endereco());

        if (dto.anfitriaoId() != null) {
            Anfitriao anfitriao = anfitriaoRepository.findById(dto.anfitriaoId())
                    .orElseThrow(() -> new EntityNotFoundException("Anfitrião não encontrado"));
            residencia.setAnfitriao(anfitriao);
        }

        return residenciaRepository.save(residencia);
    }

    public void deletar(Long id) {
        residenciaRepository.deleteById(id);
    }

    public String gerarRelatorio(Long id, int mes) {
        Residencia residencia = buscarPorId(id);
        return residencia.gerarRelatorioOcupacao(mes);
    }

    public List<Quarto> buscarQuartosLivres(Long id, LocalDateTime inicio, LocalDateTime fim) {
        Residencia residencia = buscarPorId(id);
        return residencia.buscarQuartosLivres(inicio, fim);
    }
}