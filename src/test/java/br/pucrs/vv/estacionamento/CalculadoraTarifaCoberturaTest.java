package br.pucrs.vv.estacionamento;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

/** Casos adicionais criados a partir da análise de cobertura do JaCoCo. */
@DisplayName("CalculadoraTarifa - casos novos da análise de cobertura")
class CalculadoraTarifaCoberturaTest {

    private CalculadoraTarifa calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraTarifa();
    }

    @ParameterizedTest(name = "{0} - {1} | {2} -> {3} | VIP={4} | esperado exceção")
    @CsvSource(delimiter = '|', textBlock = """
        # ID | Objetivo do caso | Entrada | Saída | VIP
        T34 | Entrada após 23:59 com segundos | 05/10/2026 23:59:30 | 06/10/2026 08:00:00 | false
        """)
    void deveRejeitarDadosInvalidos(String id,
                                    String objetivo,
                                    @ConvertWith(DataHoraConverter.class) LocalDateTime entrada,
                                    @ConvertWith(DataHoraConverter.class) LocalDateTime saida,
                                    boolean vip) {
        assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularTarifa(entrada, saida, vip),
                id + ": deveria lançar IllegalArgumentException");
    }

    /** Converte "dd/MM/yyyy HH:mm" ou "dd/MM/yyyy HH:mm:ss" para LocalDateTime. */
    static class DataHoraConverter implements ArgumentConverter {
        private static final DateTimeFormatter FORMATO =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm[:ss]");

        @Override
        public Object convert(Object origem, ParameterContext contexto) {
            if (origem == null) {
                return null;
            }
            try {
                return LocalDateTime.parse(origem.toString().trim(), FORMATO);
            } catch (DateTimeParseException e) {
                throw new ArgumentConversionException("Data/hora inválida: " + origem, e);
            }
        }
    }
}