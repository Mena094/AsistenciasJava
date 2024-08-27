package com.ESFE.Asistencias.Repositorio;

import com.ESFE.Asistencias.Entidades.Rol;
import com.ESFE.Asistencias.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<Rol, Integer> {
}
