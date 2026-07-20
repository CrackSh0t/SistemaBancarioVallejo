package model;

public interface OperacionBancaria {
    double LIMITE_DIARIO = 5000.0;

    boolean ejecutar();
    boolean reversar();
    double obtenerMonto();
}