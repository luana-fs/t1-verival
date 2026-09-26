package br.pucrs.vv.estacionamento;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CalculadoraTarifa {

    // constantes de horário
    public static final int MINUTOS_CORTESIA = 20;
    public static final int MINUTOS_TARIFA_FIXA = 60;
    public static final double VALOR_TARIFA_FIXA = 15.0;
    public static final double VALOR_HORA_ADICIONAL = 5.0;
    public static final double VALOR_PERNOITE = 50.0;
    public static final double PERCENTUAL_DESCONTO_VIP = 0.5;

    // constantes de valor
    public static final LocalTime HORA_INICIO_ENTRADA = LocalTime.of(8, 0);
    public static final LocalTime HORA_FIM_ENTRADA = LocalTime.of(23, 59);
    public static final LocalTime HORA_INICIO_BLOQUEIO_SAIDA = LocalTime.of(2, 0);
    public static final LocalTime HORA_FIM_BLOQUEIO_SAIDA = LocalTime.of(7, 59);
    public static final LocalTime HORA_INICIO_PERNOITE = LocalTime.of(0, 0);
    public static final LocalTime HORA_FIM_PERNOITE = LocalTime.of(8, 0);

    public double calcularTarifa(LocalDateTime entrada, LocalDateTime saida, boolean vip) {

        System.out.println("Calculando tarifa para entrada: " + entrada + " e saída: " + saida);
        System.out.println("hora início entrada: " + HORA_INICIO_ENTRADA);

        if (entrada == null || saida == null) {
            throw new IllegalArgumentException("Entrada e saída não podem ser nulas.");
        }

        LocalTime horarioEntrada = entrada.toLocalTime();
        LocalTime horarioSaida = saida.toLocalTime();

        if (horarioEntrada.isBefore(HORA_INICIO_ENTRADA)
            || horarioEntrada.isAfter(HORA_FIM_ENTRADA)) {
            throw new IllegalArgumentException("Entrada e saída devem estar entre 08:00 e 23:59.");
        }

        if (horarioSaida.isBefore(HORA_INICIO_ENTRADA)
            || horarioSaida.isAfter(HORA_FIM_ENTRADA)) {
            throw new IllegalArgumentException("Entrada e saída devem estar entre 08:00 e 23:59.");
        }

        if (saida.isBefore(entrada)) {
            throw new IllegalArgumentException("A hora de saída não pode ser anterior à hora de entrada.");
        }

        // Todo cliente tem 20 minutos de cortesia, ou seja, o valor a ser pago é zero.
        long minutos = Duration.between(entrada, saida).toMinutes();
        if (minutos <= MINUTOS_CORTESIA) {
            return 0.0;
        }

        // Acima de 1 hora e que não seja pernoite, o valor é incrementado de R$5,00 
        // a cada intervalo de 1 hora (inclusive). 
        double tarifa = VALOR_TARIFA_FIXA;
        if (minutos > MINUTOS_TARIFA_FIXA) {
            long horasAdicionais = (minutos - MINUTOS_TARIFA_FIXA + 59) / 60;
            tarifa += horasAdicionais * VALOR_HORA_ADICIONAL;
        }


        // Caso o veículo saia após as 08:00 da manhã do dia seguinte à sua entrada, 
        // a tarifa é convertida para pernoite, cujo valor atual é de R$50,00 por pernoite.
        boolean virouODia = saida.toLocalDate().isAfter(entrada.toLocalDate());
        boolean entrouDeNoite = !entrada.toLocalTime().isBefore(HORA_INICIO_PERNOITE);
        boolean saiuAntesDoLimite = !saida.toLocalTime().isAfter(HORA_FIM_PERNOITE);
        boolean pernoite = virouODia && entrouDeNoite && saiuAntesDoLimite;
        if (pernoite) {
            tarifa = VALOR_PERNOITE;
        }

        // Cliente VIP tem 50% de desconto sobre o valor final da tarifa. 
        return vip ? tarifa * (1 - PERCENTUAL_DESCONTO_VIP) : tarifa;
    }
}
