package br.pucrs.vv.estacionamento;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        CalculadoraTarifa calculadora = new CalculadoraTarifa();

        LocalDateTime entrada = LocalDateTime.of(2024, 6, 1, 9, 0);
        LocalDateTime saida = LocalDateTime.of(2024, 6, 1, 11, 30);
        boolean vip = true;
        double tarifa = calculadora.calcularTarifa(entrada, saida, vip);
        System.out.printf("Tarifa: R$ %.2f%n", tarifa);
    }
}
