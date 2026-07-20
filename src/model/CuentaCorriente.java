package model;

public class CuentaCorriente extends CuentaBancaria {
    private double sobregiroPermitido;
    private int chequesEmitidos;
    private double comisionPorCheque;

    public CuentaCorriente(String numeroCuenta, String titular, double saldoInicial, String fechaApertura, double sobregiroPermitido) {
        super(numeroCuenta, titular, saldoInicial, fechaApertura);
        this.sobregiroPermitido = sobregiroPermitido;
        this.chequesEmitidos = 0;
        this.comisionPorCheque = 12.50;
    }

    // Polimorfismo: Las cuentas corrientes corporativas por lo general no generan intereses directos
    @Override
    public double calcularInteres() {
        return 0.0; 
    }

    // Polimorfismo en la validación: Soporta quedarse con saldo negativo hasta el límite del sobregiro
    @Override
    public boolean validarOperacion(double monto) {
        return monto > 0 && this.estado && (this.saldo + this.sobregiroPermitido >= monto);
    }

    public boolean emitirCheque(double monto) {
        System.out.println("Procesando emision de cheque...");
        if (validarOperacion(monto + comisionPorCheque)) {
            this.saldo -= (monto + comisionPorCheque);
            chequesEmitidos++;
            System.out.println("Cheque emitido con exito por S/. " + monto + " (Comision: S/. " + comisionPorCheque + ")");
            return true;
        }
        System.out.println("Error: Fondos e insuficientes incluso con linea de sobregiro.");
        return false;
    }
}