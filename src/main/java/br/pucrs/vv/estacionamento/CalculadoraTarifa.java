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
    public static final int HORA_INICIO_ENTRADA = LocalTime.of(8, 0).getHour();
    public static final int HORA_FIM_ENTRADA = LocalTime.of(23, 59).getHour();
    public static final int HORA_INICIO_BLOQUEIO_SAIDA = LocalTime.of(2, 0).getHour();
    public static final int HORA_FIM_BLOQUEIO_SAIDA = LocalTime.of(7, 59).getHour();
    public static final int HORA_INICIO_PERNOITE = LocalTime.of(0, 0).getHour();
    public static final int HORA_PERNOITE = LocalTime.of(8, 0).getHour();

    public double calcularTarifa(LocalDateTime entrada, LocalDateTime saida, boolean vip) {

        if (entrada == null || saida == null) {
            throw new IllegalArgumentException("Entrada e saída não podem ser nulas.");
        }

        LocalTime horarioEntrada = entrada.toLocalTime();
        LocalTime horarioSaida = saida.toLocalTime();
        if (horarioEntrada.isBefore(LocalTime.of(HORA_INICIO_ENTRADA, 0))
            || horarioEntrada.isAfter(LocalTime.of(HORA_FIM_ENTRADA, 59))) {
            throw new IllegalArgumentException("Entrada e saída devem estar entre 08:00 e 23:59.");
        }

        if (horarioSaida.isBefore(LocalTime.of(HORA_INICIO_ENTRADA, 0))
            || horarioSaida.isAfter(LocalTime.of(HORA_FIM_ENTRADA, 59))) {
            throw new IllegalArgumentException("Entrada e saída devem estar entre 08:00 e 23:59.");
        }

        if (saida.isBefore(entrada)) {
            throw new IllegalArgumentException("A hora de saída não pode ser anterior à hora de entrada.");
        }

        long minutos = Duration.between(entrada, saida).toMinutes();
        if (minutos <= MINUTOS_CORTESIA) {
            return 0.0;
        }

        double tarifa = VALOR_TARIFA_FIXA;
        if (minutos > MINUTOS_TARIFA_FIXA) {
            long horasAdicionais = (minutos - MINUTOS_TARIFA_FIXA + 59) / 60;
            tarifa += horasAdicionais * VALOR_HORA_ADICIONAL;
        }

        return vip ? tarifa * (1 - PERCENTUAL_DESCONTO_VIP) : tarifa;
    }
}
