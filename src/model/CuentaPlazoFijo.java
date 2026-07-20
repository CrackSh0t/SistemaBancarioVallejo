package model;

public class CuentaPlazoFijo extends CuentaBancaria {
    private String fechaVencimiento;
    private double penalizacionRetiro;
    private int plazoMeses;

    public CuentaPlazoFijo(String numeroCuenta, String titular, double saldoInicial, String fechaApertura, String fechaVencimiento, int plazoMeses) {
        super(numeroCuenta, titular, saldoInicial, fechaApertura);
        this.fechaVencimiento = fechaVencimiento;
        this.plazoMeses = plazoMeses;
        this.penalizacionRetiro = 0.05; // 5% de penalización por retiro anticipado
    }

    // Polimorfismo: Tasa fija preferencial por permanencia prolongada
    @Override
    public double calcularInteres() {
        double tasaPreferencial = 0.07; // 7% anual
        double interesGenerado = this.saldo * (tasaPreferencial / 12) * plazoMeses;
        this.saldo += interesGenerado;
        return interesGenerado;
    }

    public boolean retirarAnticipado(double monto) {
        System.out.println("¡ADVERTENCIA! Retiro anticipado detectado antes de la fecha: " + fechaVencimiento);
        double costoPenalizacion = monto * penalizacionRetiro;
        double montoTotalADescontar = monto + costoPenalizacion;

        if (this.saldo >= montoTotalADescontar) {
            this.saldo -= montoTotalADescontar;
            System.out.println("Retiro anticipado procesado. Monto: S/. " + monto + " | Penalizacion cobrada: S/. " + costoPenalizacion);
            return true;
        }
        System.out.println("Error: Fondos insuficientes para cubrir el retiro y su penalizacion.");
        return false;
    }
}