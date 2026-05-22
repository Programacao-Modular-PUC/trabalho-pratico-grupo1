package br.pucminas.sistemahospedagem.service;

import br.pucminas.sistemahospedagem.dto.RegistroDTO;
import br.pucminas.sistemahospedagem.model.Anfitriao;
import br.pucminas.sistemahospedagem.model.Cliente;
import br.pucminas.sistemahospedagem.model.Usuario;
import br.pucminas.sistemahospedagem.model.enums.TipoUsuario;
import br.pucminas.sistemahospedagem.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrar(RegistroDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setEmailLogin(dto.getEmailLogin());
        usuario.setSenha(dto.getSenha());
        usuario.setPapel(dto.getPapel());

        if (dto.getPapel() == TipoUsuario.CLIENTE) {
            Cliente cliente = new Cliente();
            cliente.setNome(dto.getNome());
            cliente.setCPF(dto.getCPF());
            cliente.setEmail(dto.getEmailLogin());
            cliente.setTelefone(dto.getTelefone());
            cliente.setEndereco(dto.getEndereco());
            usuario.setCliente(cliente);

        } else if (dto.getPapel() == TipoUsuario.ANFITRIAO) {
            Anfitriao anfitriao = new Anfitriao();
            anfitriao.setNome(dto.getNome());
            anfitriao.setCPF(dto.getCPF());
            anfitriao.setEmail(dto.getEmailLogin());
            anfitriao.setTelefone(dto.getTelefone());
            anfitriao.setEndereco(dto.getEndereco());
            usuario.setAnfitriao(anfitriao);
        }

        return usuarioRepository.save(usuario);
    }
}