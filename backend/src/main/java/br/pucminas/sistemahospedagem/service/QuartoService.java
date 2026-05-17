package br.pucminas.sistemahospedagem.service;

import br.pucminas.sistemahospedagem.dto.quarto.QuartoRequestDTO;
import br.pucminas.sistemahospedagem.model.Quarto;
import br.pucminas.sistemahospedagem.model.QuartoCasal;
import br.pucminas.sistemahospedagem.model.QuartoIndividual;
import br.pucminas.sistemahospedagem.repository.QuartoRepository;
import br.pucminas.sistemahospedagem.repository.ResidenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuartoService {

    private final QuartoRepository quartoRepository;
    private final ResidenciaRepository residenciaRepository;

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public Quarto buscarPorId(Long id) {
        return quartoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
    }

    public List<Quarto> buscarPorResidencia(Long residenciaId) {
        return quartoRepository.findByResidenciaId(residenciaId);
    }

    public Quarto criar(QuartoRequestDTO dto) {
        var residencia = residenciaRepository.findById(dto.residenciaId())
                .orElseThrow(() -> new EntityNotFoundException("Residência não encontrada"));

        Quarto quarto;
        String tipo = dto.tipo().toUpperCase();
        if ("INDIVIDUAL".equals(tipo)) {
            QuartoIndividual qi = new QuartoIndividual();
            qi.setNumero(dto.numero());
            qi.setValorBase(dto.valorBase());
            qi.setCapacidadeMaxima(dto.capacidadeMaxima());
            qi.setNumeroCamasSolteiro(dto.numeroCamasSolteiro() != null ? dto.numeroCamasSolteiro() : 1);
            qi.setValorAdicionalPorCama(dto.valorAdicionalPorCama() != null ? dto.valorAdicionalPorCama() : 0.0);
            quarto = qi;
        } else if ("CASAL".equals(tipo)) {
            QuartoCasal qc = new QuartoCasal();
            qc.setNumero(dto.numero());
            qc.setValorBase(dto.valorBase());
            qc.setCapacidadeMaxima(dto.capacidadeMaxima());
            qc.setTipoCamaCasal(dto.tipoCamaCasal());
            qc.setBercoInstalado(dto.bercoInstalado() != null ? dto.bercoInstalado() : false);
            qc.setValorAdicionalBerco(dto.valorAdicionalBerco() != null ? dto.valorAdicionalBerco() : 0.0);
            qc.setValorAdicionalConforto(dto.valorAdicionalConforto() != null ? dto.valorAdicionalConforto() : 0.0);
            quarto = qc;
        } else {
            throw new IllegalArgumentException("Tipo de quarto inválido");
        }

        quarto.setResidencia(residencia);

        return quartoRepository.save(quarto);
    }

    public Quarto atualizar(Long id, QuartoRequestDTO dto) {
        Quarto existente = buscarPorId(id);

        // não permite mudança de tipo para simplificar
        if (dto.tipo() != null && !dto.tipo().equalsIgnoreCase(getTipoParaEntity(existente))) {
            throw new IllegalArgumentException("Não é permitido alterar o tipo do quarto");
        }

        existente.setNumero(dto.numero() != null ? dto.numero() : existente.getNumero());
        existente.setValorBase(dto.valorBase() != null ? dto.valorBase() : existente.getValorBase());
        existente.setCapacidadeMaxima(dto.capacidadeMaxima() != null ? dto.capacidadeMaxima() : existente.getCapacidadeMaxima());

        if (dto.residenciaId() != null) {
            var residencia = residenciaRepository.findById(dto.residenciaId())
                    .orElseThrow(() -> new EntityNotFoundException("Residência não encontrada"));
            existente.setResidencia(residencia);
        }

        if (existente instanceof QuartoIndividual ind) {
            if (dto.numeroCamasSolteiro() != null) ind.setNumeroCamasSolteiro(dto.numeroCamasSolteiro());
            if (dto.valorAdicionalPorCama() != null) ind.setValorAdicionalPorCama(dto.valorAdicionalPorCama());
        } else if (existente instanceof QuartoCasal casal) {
            if (dto.tipoCamaCasal() != null) casal.setTipoCamaCasal(dto.tipoCamaCasal());
            if (dto.bercoInstalado() != null) casal.setBercoInstalado(dto.bercoInstalado());
            if (dto.valorAdicionalBerco() != null) casal.setValorAdicionalBerco(dto.valorAdicionalBerco());
            if (dto.valorAdicionalConforto() != null) casal.setValorAdicionalConforto(dto.valorAdicionalConforto());
        }

        return quartoRepository.save(existente);
    }

    public void deletar(Long id) {
        quartoRepository.deleteById(id);
    }

    private String getTipoParaEntity(Quarto q) {
        if (q instanceof QuartoIndividual) return "INDIVIDUAL";
        if (q instanceof QuartoCasal) return "CASAL";
        return "UNKNOWN";
    }
}
