package model;

public class CuentaAhorros extends CuentaBancaria {
    private double tasaInteres;
    private double limiteRetiro;
    private int contadorRetiros;

    public CuentaAhorros(String numeroCuenta, String titular, double saldoInicial, String fechaApertura, double tasaInteres, double limiteRetiro) {
        super(numeroCuenta, titular, saldoInicial, fechaApertura); 
        this.tasaInteres = tasaInteres;
        this.limiteRetiro = limiteRetiro;
        this.contadorRetiros = 0;
    }

    @Override
    public double calcularInteres() {
        double interesGenerado = this.saldo * this.tasaInteres;
        this.saldo += interesGenerado; // CORREGIDO
        return interesGenerado;
    }

@Override
    public boolean retirar(double monto) throws SaldoInsuficienteException, LimiteRetiroExcedidoException {
        // 1. Candado de Ahorros: Validar el límite permitido por transacción (S/. 500)
        if (monto > limiteRetiro) {
            throw new LimiteRetiroExcedidoException("Operación Rechazada: El monto máximo permitido por retiro en esta Cuenta de Ahorros es S/. " + limiteRetiro);
        }
        
        // 2. Candado Financiero: Validar que el monto no deje la cuenta en saldo negativo
        if (monto > this.saldo) {
            throw new SaldoInsuficienteException("Fondos Insuficientes: No puede retirar S/. " + monto + " porque su saldo disponible actual es S/. " + this.saldo);
        }
        
        // 3. Si pasa los filtros de ahorros, invoca al retiro de la superclase
        return super.retirar(monto);
    }
}