package com.example.segundoparcial;

public class Usuario {

    private Long id;
    private String username;
    private String rol;
    private Boolean admin;

    public Usuario() {
    }

    public Usuario(Long id, String username, String rol, Boolean admin) {
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                ", admin=" + admin +
                '}';
    }
}
