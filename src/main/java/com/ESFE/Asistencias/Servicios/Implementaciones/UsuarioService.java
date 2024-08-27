package com.ESFE.Asistencias.Servicios.Implementaciones;

import com.ESFE.Asistencias.Entidades.Rol;
import com.ESFE.Asistencias.Entidades.Usuario;
import com.ESFE.Asistencias.Repositorio.IUsuarioRepository;
import com.ESFE.Asistencias.Servicios.Interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> buscarTodosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);

    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario crearOEditar(Usuario usuario) {
        return usuarioRepository.save(usuario);

    }

    @Override
    public void eliminarPorId(Integer id) {
        usuarioRepository.deleteById(id);

    }




}
