package model;

public interface ProductoFinanciero {
    // Constantes definidas en su diagrama UML
    double TASA_MAXIMA = 0.15;
    double COMISION_MINIMA = 5.0;

    double calcularComision();
    String generarEstadoCuenta();
    boolean validarOperacion(double monto);
}