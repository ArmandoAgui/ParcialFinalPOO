package org.example.parcialfinalpoo.Clases;

public class Tarjeta {
    private int id;
    private String clienteId;
    private String NoTarjeta;
    private String facilitador;
    private String fechaCaducidad;
    private String tipo;

    // Constructor
    public Tarjeta(int id, String clienteId, String NoTarjeta, String facilitador, String fechaCaducidad, String tipo) {
        this.id = id;
        this.clienteId = clienteId;
        this.NoTarjeta = NoTarjeta;
        this.facilitador = facilitador;
        this.fechaCaducidad = fechaCaducidad;
        this.tipo = tipo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getNoTarjeta() {
        return NoTarjeta;
    }

    public void setNoTarjeta(String noTarjeta) {
        this.NoTarjeta = noTarjeta;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
