package org.example.parcialfinalpoo.Clases;

import java.sql.Date;

public class Transaccion {
    private int id;
    private String fecha;
    private double montoTotal;
    private String descripcion;
    private int tarjetaId;

    public Transaccion(int id, String fecha, double montoTotal, String descripcion, int tarjetaId) {
        this.id = id;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.descripcion = descripcion;
        this.tarjetaId = tarjetaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
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

    public int getTarjetaId() {
        return tarjetaId;
    }

    public void setTarjetaId(int tarjetaId) {
        this.tarjetaId = tarjetaId;
    }
}
