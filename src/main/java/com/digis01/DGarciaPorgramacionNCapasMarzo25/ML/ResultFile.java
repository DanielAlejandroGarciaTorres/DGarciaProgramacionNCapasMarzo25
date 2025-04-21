package com.digis01.DGarciaPorgramacionNCapasMarzo25.ML;

public class ResultFile {

    private int Fila;
    private String Mensaje;
    private String Descripcion;

    public ResultFile(int Fila, String Mensaje, String Descripcion) {
        this.Fila = Fila;
        this.Mensaje = Mensaje;
        this.Descripcion = Descripcion;
    }

    public ResultFile() {

    }

    public int getFila() {
        return Fila;
    }

    public void setFila(int Fila) {
        this.Fila = Fila;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

}
