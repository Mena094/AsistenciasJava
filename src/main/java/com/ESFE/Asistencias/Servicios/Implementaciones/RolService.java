package com.ESFE.Asistencias.Servicios.Implementaciones;

import com.ESFE.Asistencias.Entidades.Rol;
import com.ESFE.Asistencias.Repositorio.IRolRepository;
import com.ESFE.Asistencias.Servicios.Interfaces.IRolService;
import com.ESFE.Asistencias.Servicios.Interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService implements IRolService {
    @Autowired
    private IRolRepository rolRepository;
    @Override
    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }
    @Override
    public Optional<Rol> buscarPorId(Integer id) {
        return rolRepository.findById(id);
    }
}
