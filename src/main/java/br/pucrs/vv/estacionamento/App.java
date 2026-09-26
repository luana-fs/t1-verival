package br.pucrs.vv.estacionamento;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        CalculadoraTarifa calculadora = new CalculadoraTarifa();

    // Cortesia de 20 minutos, cliente comum: R$ 0,00.
    imprimirTarifa("Cortesia", calculadora,
        LocalDateTime.of(2024, 6, 1, 8, 0),
        LocalDateTime.of(2024, 6, 1, 8, 20), false);

    // Até 1 hora de permanência, cliente comum: R$ 15,00.
    imprimirTarifa("Até 1 hora", calculadora,
        LocalDateTime.of(2024, 6, 1, 9, 0),
        LocalDateTime.of(2024, 6, 1, 10, 0), false);

    // Mais de 1 hora, com uma hora adicional, cliente comum: R$ 20,00.
    imprimirTarifa("Hora adicional", calculadora,
        LocalDateTime.of(2024, 6, 1, 10, 0),
        LocalDateTime.of(2024, 6, 1, 11, 1), false);

    // Tarifa de pernoite ao sair às 08:00 do dia seguinte: R$ 50,00.
    imprimirTarifa("Pernoite", calculadora,
        LocalDateTime.of(2024, 6, 1, 23, 0),
        LocalDateTime.of(2024, 6, 2, 8, 0), false);

    // Cliente VIP recebe 50% de desconto sobre a tarifa final: R$ 25,00.
    imprimirTarifa("Pernoite VIP", calculadora,
        LocalDateTime.of(2024, 6, 1, 23, 0),
        LocalDateTime.of(2024, 6, 2, 8, 0), true);

    // Cenários de erro: descomente um por vez para verificar a exceção.
    // Entrada antes das 08:00.
    // calculadora.calcularTarifa(LocalDateTime.of(2024, 6, 1, 7, 59),
    //         LocalDateTime.of(2024, 6, 1, 8, 30), false);

    // Saída entre 02:00 e 07:59.
    // calculadora.calcularTarifa(LocalDateTime.of(2024, 6, 1, 23, 0),
    //         LocalDateTime.of(2024, 6, 2, 7, 59), false);

    // Saída anterior à entrada.
    // calculadora.calcularTarifa(LocalDateTime.of(2024, 6, 1, 10, 0),
    //         LocalDateTime.of(2024, 6, 1, 9, 59), false);

    // Entrada ou saída nula.
    // calculadora.calcularTarifa(null, LocalDateTime.of(2024, 6, 1, 8, 0), false);
    }

    private static void imprimirTarifa(String cenario, CalculadoraTarifa calculadora,
                       LocalDateTime entrada, LocalDateTime saida,
                       boolean vip) {
    double tarifa = calculadora.calcularTarifa(entrada, saida, vip);
    System.out.printf("%s: R$ %.2f%n", cenario, tarifa);
    }
}
