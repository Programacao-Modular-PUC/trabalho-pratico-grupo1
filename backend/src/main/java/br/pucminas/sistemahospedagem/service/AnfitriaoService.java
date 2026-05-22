package br.pucminas.sistemahospedagem.service;

import br.pucminas.sistemahospedagem.dto.AnfitriaoDTO;
import br.pucminas.sistemahospedagem.model.Anfitriao;
import br.pucminas.sistemahospedagem.repository.AnfitriaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnfitriaoService {

    @Autowired
    private AnfitriaoRepository anfitriaoRepository;

    public Anfitriao criar(AnfitriaoDTO dto) {
        Anfitriao anfitriao = new Anfitriao();
        anfitriao.setNome(dto.getNome());
        anfitriao.setCPF(dto.getCPF());
        anfitriao.setEmail(dto.getEmail());
        anfitriao.setTelefone(dto.getTelefone());
        anfitriao.setEndereco(dto.getEndereco());
        return anfitriaoRepository.save(anfitriao);
    }

    public List<Anfitriao> listarTodos() {
        return anfitriaoRepository.findAll();
    }

    public Optional<Anfitriao> buscarPorId(Long id) {
        return anfitriaoRepository.findById(id);
    }

    public Anfitriao atualizar(Long id, AnfitriaoDTO dto) {
        Anfitriao anfitriao = anfitriaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anfitrião não encontrado"));
        anfitriao.setNome(dto.getNome());
        anfitriao.setCPF(dto.getCPF());
        anfitriao.setEmail(dto.getEmail());
        anfitriao.setTelefone(dto.getTelefone());
        anfitriao.setEndereco(dto.getEndereco());
        return anfitriaoRepository.save(anfitriao);
    }

    public void deletar(Long id) {
        anfitriaoRepository.deleteById(id);
    }
}