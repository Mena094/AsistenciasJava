package com.ESFE.Asistencias.Entidades;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @NotBlank(message = "El nombre de usuario es requerido")
    private String login;

    @NotBlank(message = "La contraseña es requerida")
    private String clave;

    private int status;

    //Establece una relación muchos a muchos entre la entidad Usuario y la entidad Rol.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "usuario_rol",
             joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre de usuario es requerido") String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank(message = "El nombre de usuario es requerido") String login) {
        this.login = login;
    }

    public @NotBlank(message = "La contraseña es requerida") String getClave() {
        return clave;
    }

    public void setClave(@NotBlank(message = "La contraseña es requerida") String clave) {
        this.clave = clave;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
    //Metodo para agregar roles
    public void agregar(Rol tempRol){
        if(roles == null){
            roles = new LinkedList<>();
        }
        roles.add(tempRol);
    }

}
