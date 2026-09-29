package br.pucrs.vv.estacionamento;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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

        if (entrada == null || saida == null) {
            throw new IllegalArgumentException("Entrada e saída não podem ser nulas.");
        }

        // ----- antes de calcular a tarifa, valida o horário de entrada e saída ------
        validarHorario(entrada, saida);

        // Todo cliente tem 20 minutos de cortesia, ou seja, o valor a ser pago é zero.
        long minutos = Duration.between(entrada, saida).toMinutes();
        if (minutos <= MINUTOS_CORTESIA) {
            return 0.0;
        }

        // ------- início do cálculo da tarifa --------

        // Cálculo de pernoites: dias entre as datas, menos 1 se a saída for antes das 08:00
        long pernoites = ChronoUnit.DAYS.between(entrada.toLocalDate(), saida.toLocalDate());
        if (saida.toLocalTime().isBefore(HORA_FIM_PERNOITE)) {
            pernoites--;
        }

        double tarifa;
        if (pernoites > 0) {
            // Pela decisão A04, pernoite não soma horas adicionais
            tarifa = pernoites * VALOR_PERNOITE;
        } else {
            tarifa = VALOR_TARIFA_FIXA;
            // Acima de 1 hora, o valor é incrementado de R$ 5,00 a cada intervalo de 1 hora
            if (minutos > MINUTOS_TARIFA_FIXA) {
                long horasAdicionais = (minutos - MINUTOS_TARIFA_FIXA + 59) / 60;
                tarifa += horasAdicionais * VALOR_HORA_ADICIONAL;
            }
        }

        // Cliente VIP tem 50% de desconto sobre o valor final da tarifa.
        return vip ? tarifa * (1 - PERCENTUAL_DESCONTO_VIP) : tarifa;
    }

    private void validarHorario(LocalDateTime entrada, LocalDateTime saida) {
        LocalTime horarioEntrada = entrada.toLocalTime();
        LocalTime horarioSaida = saida.toLocalTime();

        if (horarioEntrada.isBefore(HORA_INICIO_ENTRADA)
            || horarioEntrada.isAfter(HORA_FIM_ENTRADA)) {
            throw new IllegalArgumentException("Entrada deve estar entre 08:00 e 23:59.");
        }

        // Saída bloqueada entre 02:00 e 07:59 (inclusive)
        boolean saidaBloqueada = !horarioSaida.isBefore(HORA_INICIO_BLOQUEIO_SAIDA)
                && !horarioSaida.isAfter(HORA_FIM_BLOQUEIO_SAIDA);
        if (saidaBloqueada) {
            throw new IllegalArgumentException("Saída bloqueada entre 02:00 e 07:59.");
        }

        if (saida.isBefore(entrada)) {
            throw new IllegalArgumentException("A hora de saída não pode ser anterior à hora de entrada.");
        }
    }
}

