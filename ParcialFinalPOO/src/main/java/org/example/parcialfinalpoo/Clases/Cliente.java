package org.example.parcialfinalpoo.Clases;

public class Cliente {
    private int id;
    private String nombreCompleto;
    private String direccion;
    private String telefono;

    public Cliente(int id, String nombreCompleto, String direccion, String telefono) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
