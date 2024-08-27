package com.ESFE.Asistencias.Repositorio;

import com.ESFE.Asistencias.Entidades.Grupo;
import com.ESFE.Asistencias.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

}
