package br.pucrs.vv.estacionamento;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Classe driver dos casos de teste da CalculadoraTarifa.
 *
 * Os dados abaixo são uma cópia fiel da tabela oficial (Casos_de_Teste.md).
 * Qualquer alteração de caso deve ser feita PRIMEIRO na tabela e depois aqui,
 * mantendo o mesmo ID (T01, T02, ...).
 *
 * Os casos estão separados em dois testes parametrizados:
 *  - deveCalcularTarifa: casos válidos, que devem retornar um valor em R$;
 *  - deveRejeitarDadosInvalidos: casos que devem lançar IllegalArgumentException.
 */
@DisplayName("CalculadoraTarifa - casos de teste oficiais")
class CalculadoraTarifaTest {

    private static final double DELTA = 0.001;

    private CalculadoraTarifa calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraTarifa();
    }

    @ParameterizedTest(name = "{0} - {1} | {2} -> {3} | VIP={4} | esperado R$ {5}")
    @CsvSource(delimiter = '|', textBlock = """
        # ID | Objetivo do caso                        | Entrada          | Saída            | VIP   | Esperado
        T01  | Cortesia - antes do limite (19 min)     | 05/10/2026 10:00 | 05/10/2026 10:19 | false |   0.00
        T02  | Cortesia - no limite (20 min)           | 05/10/2026 10:00 | 05/10/2026 10:20 | false |   0.00
        T03  | Cortesia - depois do limite (21 min)    | 05/10/2026 10:00 | 05/10/2026 10:21 | false |  15.00
        T04  | Tarifa fixa - antes do limite (59 min)  | 05/10/2026 10:00 | 05/10/2026 10:59 | false |  15.00
        T05  | Tarifa fixa - no limite (60 min)        | 05/10/2026 10:00 | 05/10/2026 11:00 | false |  15.00
        T06  | Tarifa fixa - depois do limite (61 min) | 05/10/2026 10:00 | 05/10/2026 11:01 | false |  20.00
        T07  | Hora adicional - antes (119 min)        | 05/10/2026 10:00 | 05/10/2026 11:59 | false |  20.00
        T08  | Hora adicional - no limite (120 min)    | 05/10/2026 10:00 | 05/10/2026 12:00 | false |  20.00
        T09  | Hora adicional - depois (121 min)       | 05/10/2026 10:00 | 05/10/2026 12:01 | false |  25.00
        T11  | Início entrada - no limite (08:00)      | 05/10/2026 08:00 | 05/10/2026 10:00 | false |  20.00
        T12  | Início entrada - depois (08:01)         | 05/10/2026 08:01 | 05/10/2026 10:00 | false |  20.00
        T13  | Fim entrada - antes (23:58)             | 05/10/2026 23:58 | 06/10/2026 00:18 | false |   0.00
        T14  | Fim entrada - no limite (23:59)         | 05/10/2026 23:59 | 06/10/2026 00:19 | false |   0.00
        T16  | Bloqueio saída - antes (01:59)          | 05/10/2026 23:00 | 06/10/2026 01:59 | false |  25.00
        T21  | Fim bloqueio saída - depois (08:00)     | 05/10/2026 23:00 | 06/10/2026 08:00 | false |  50.00
        T22  | 1 -> 2 pernoites - antes (01:59)        | 05/10/2026 10:00 | 07/10/2026 01:59 | false |  50.00
        T23  | 1 -> 2 pernoites - no limite (08:00)    | 05/10/2026 10:00 | 07/10/2026 08:00 | false | 100.00
        T24  | 1 -> 2 pernoites - depois (08:01)       | 05/10/2026 10:00 | 07/10/2026 08:01 | false | 100.00
        T26  | Ordem temporal - no limite (0 min)      | 05/10/2026 10:00 | 05/10/2026 10:00 | false |   0.00
        T27  | Ordem temporal - depois (+1 min)        | 05/10/2026 10:00 | 05/10/2026 10:01 | false |   0.00
        T28  | VIP com cortesia                        | 05/10/2026 10:00 | 05/10/2026 10:15 | true  |   0.00
        T29  | VIP com hora adicional                  | 05/10/2026 10:00 | 05/10/2026 12:30 | true  |  12.50
        T30  | VIP com pernoite                        | 05/10/2026 20:00 | 06/10/2026 10:00 | true  |  25.00
        T33  | Tarifa sem teto (1079 min)              | 05/10/2026 08:00 | 06/10/2026 01:59 | false | 100.00
        """)
    void deveCalcularTarifa(String id,
                            String objetivo,
                            @ConvertWith(DataHoraConverter.class) LocalDateTime entrada,
                            @ConvertWith(DataHoraConverter.class) LocalDateTime saida,
                            boolean vip,
                            double esperado) {

        double obtido = assertDoesNotThrow(
                () -> calculadora.calcularTarifa(entrada, saida, vip),
                id + ": caso válido não deveria lançar exceção");

        assertEquals(esperado, obtido, DELTA,
                id + ": valor da tarifa diferente do esperado");
    }

    @ParameterizedTest(name = "{0} - {1} | {2} -> {3} | VIP={4} | esperado exceção")
    @CsvSource(delimiter = '|', nullValues = "nulo", textBlock = """
        # ID | Objetivo do caso                         | Entrada          | Saída            | VIP
        T10  | Início entrada - antes do limite (07:59) | 05/10/2026 07:59 | 05/10/2026 10:00 | false
        T15  | Fim entrada - depois do limite (00:00)   | 06/10/2026 00:00 | 06/10/2026 08:00 | false
        T17  | Bloqueio saída - no limite (02:00)       | 05/10/2026 23:00 | 06/10/2026 02:00 | false
        T18  | Bloqueio saída - depois (02:01)          | 05/10/2026 23:00 | 06/10/2026 02:01 | false
        T19  | Fim bloqueio saída - antes (07:58)       | 05/10/2026 23:00 | 06/10/2026 07:58 | false
        T20  | Fim bloqueio saída - no limite (07:59)   | 05/10/2026 23:00 | 06/10/2026 07:59 | false
        T25  | Ordem temporal - antes (-1 min)          | 05/10/2026 10:00 | 05/10/2026 09:59 | false
        T31  | Entrada nula                             | nulo             | 05/10/2026 10:00 | false
        T32  | Saída nula                               | 05/10/2026 10:00 | nulo             | false
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

    /**
     * Converte o texto "dd/MM/yyyy HH:mm" da tabela para LocalDateTime.
     * O valor "nulo" na tabela chega aqui como null e é repassado como null
     * (necessário para os casos T31 e T32).
     */
    static class DataHoraConverter implements ArgumentConverter {
        private static final DateTimeFormatter FORMATO =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        @Override
        public Object convert(Object origem, ParameterContext contexto) {
            if (origem == null) {
                return null;
            }
            try {
                return LocalDateTime.parse(origem.toString().trim(), FORMATO);
            } catch (DateTimeParseException e) {
                throw new ArgumentConversionException(
                        "Data/hora inválida na tabela: " + origem, e);
            }
        }
    }
}
