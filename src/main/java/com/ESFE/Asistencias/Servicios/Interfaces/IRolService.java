package com.ESFE.Asistencias.Servicios.Interfaces;


import com.ESFE.Asistencias.Entidades.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    List<Rol> obtenerTodos();
    Optional<Rol> buscarPorId(Integer id);
}
