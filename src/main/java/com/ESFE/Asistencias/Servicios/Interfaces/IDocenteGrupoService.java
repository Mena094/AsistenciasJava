package com.ESFE.Asistencias.Servicios.Interfaces;

import com.ESFE.Asistencias.Entidades.DocenteGrupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IDocenteGrupoService {

    Page<DocenteGrupo> buscarTodosPaginados(Pageable pageable);

    List<DocenteGrupo> obtenerTodos();

    Optional<DocenteGrupo> buscarPorId(Integer id);

    DocenteGrupo crearOEditar(DocenteGrupo docenteGrupo);

    void eliminarPorId(Integer id);
}
