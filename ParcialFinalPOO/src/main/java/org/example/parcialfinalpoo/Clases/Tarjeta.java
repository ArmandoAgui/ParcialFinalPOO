package org.example.parcialfinalpoo.Clases;

public class Tarjeta {
    private String numeroTarjeta;
    private String fechaExpiracion;
    private String tipo;
    private String facilitador;
    private Cliente cliente;

    public Tarjeta(String numeroTarjeta, String fechaExpiracion, String tipo, String facilitador, Cliente cliente) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.tipo = tipo;
        this.facilitador = facilitador;
        this.cliente = cliente;
    }

    public Tarjeta() {
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
