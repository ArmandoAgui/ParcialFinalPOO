package org.example.parcialfinalpoo.Clases;

import java.sql.Date;

public class Transaccion {
    private Date fecha;
    private double montoTotal;
    private String descripcion;
    private Tarjeta tarjeta;

    public Transaccion(Date fecha, double montoTotal, String descripcion, Tarjeta tarjeta) {
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.descripcion = descripcion;
        this.tarjeta = tarjeta;
    }

    public Transaccion() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    @Override
    public String toString() {
        return tarjeta.getCliente().getNombre() + " - " + fecha + " - " + "$" + montoTotal + " - " + descripcion + " - " + tarjeta.getNumeroTarjeta() + " - " +
                tarjeta.getTipo() + " - " + tarjeta.getFacilitador();
    }
}
