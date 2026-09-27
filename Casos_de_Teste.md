# Casos de Teste Oficiais - T1 V&V

Esta tabela cobre todas as partições de equivalência (seção 5) e fronteiras (seção 6), seguindo estritamente as decisões de negócio (seção 3) e o padrão do documento.

| ID | Técnica (Partição ou Limite + qual CV/CI/fronteira cobre) | Entrada | Saída | VIP | Esperado |
| :--- | :--- | :--- | :--- | :--- | :--- |
| T01 | Limite Cortesia (Antes, 19 min) (CV01) | 05/10/2026 10:00 | 05/10/2026 10:19 | Não | R$ 0,00 |
| T02 | Limite Cortesia (No limite, 20 min) (CV01) | 05/10/2026 10:00 | 05/10/2026 10:20 | Não | R$ 0,00 |
| T03 | Limite Cortesia (Depois, 21 min) (CV02) | 05/10/2026 10:00 | 05/10/2026 10:21 | Não | R$ 15,00 |
| T04 | Limite Tarifa Fixa (Antes, 59 min) (CV02) | 05/10/2026 10:00 | 05/10/2026 10:59 | Não | R$ 15,00 |
| T05 | Limite Tarifa Fixa (No limite, 60 min) (CV02) | 05/10/2026 10:00 | 05/10/2026 11:00 | Não | R$ 15,00 |
| T06 | Limite Tarifa Fixa (Depois, 61 min) (CV03) | 05/10/2026 10:00 | 05/10/2026 11:01 | Não | R$ 20,00 |
| T07 | Limite Hora Adicional (Antes, 119 min) (CV03) | 05/10/2026 10:00 | 05/10/2026 11:59 | Não | R$ 20,00 |
| T08 | Limite Hora Adicional (No limite, 120 min) (CV03) | 05/10/2026 10:00 | 05/10/2026 12:00 | Não | R$ 20,00 |
| T09 | Limite Hora Adicional (Depois, 121 min) (CV03) | 05/10/2026 10:00 | 05/10/2026 12:01 | Não | R$ 25,00 |
| T10 | Limite Início Entrada (Antes, 07:59) (CI01) | 05/10/2026 07:59 | 05/10/2026 10:00 | Não | Exceção |
| T11 | Limite Início Entrada (No limite, 08:00) (CV06) | 05/10/2026 08:00 | 05/10/2026 10:00 | Não | R$ 20,00 |
| T12 | Limite Início Entrada (Depois, 08:01) (CV06) | 05/10/2026 08:01 | 05/10/2026 10:00 | Não | R$ 20,00 |
| T13 | Limite Fim Entrada (Antes, 23:58) (CV06) | 05/10/2026 23:58 | 06/10/2026 00:18 | Não | R$ 0,00 |
| T14 | Limite Fim Entrada (No limite, 23:59) (CV06) | 05/10/2026 23:59 | 06/10/2026 00:19 | Não | R$ 0,00 |
| T15 | Limite Fim Entrada (Depois, 00:00) (CI01) | 06/10/2026 00:00 | 06/10/2026 08:00 | Não | Exceção |
| T16 | Limite Início Bloqueio Saída (Antes, 01:59) (CV08) | 05/10/2026 23:00 | 06/10/2026 01:59 | Não | R$ 25,00 |
| T17 | Limite Início Bloqueio Saída (No limite, 02:00) (CI02) | 05/10/2026 23:00 | 06/10/2026 02:00 | Não | Exceção |
| T18 | Limite Início Bloqueio Saída (Depois, 02:01) (CI02) | 05/10/2026 23:00 | 06/10/2026 02:01 | Não | Exceção |
| T19 | Limite Fim Bloqueio Saída (Antes, 07:58) (CI02) | 05/10/2026 23:00 | 06/10/2026 07:58 | Não | Exceção |
| T20 | Limite Fim Bloqueio Saída (No limite, 07:59) (CI02) | 05/10/2026 23:00 | 06/10/2026 07:59 | Não | Exceção |
| T21 | Limite Fim Bloqueio Saída (Depois, 08:00) (CV04) | 05/10/2026 23:00 | 06/10/2026 08:00 | Não | R$ 50,00 |
| T22 | Limite 1->2 Pernoites (Antes, 01:59 no 2º dia seguinte) (CV04) | 05/10/2026 10:00 | 07/10/2026 01:59 | Não | R$ 50,00 |
| T23 | Limite 1->2 Pernoites (No limite, 08:00 no 2º dia seguinte) (CV05) | 05/10/2026 10:00 | 07/10/2026 08:00 | Não | R$ 100,00 |
| T24 | Limite 1->2 Pernoites (Depois, 08:01 no 2º dia seguinte) (CV05) | 05/10/2026 10:00 | 07/10/2026 08:01 | Não | R$ 100,00 |
| T25 | Limite Ordem Temporal (Antes, -1 min) (CI03) | 05/10/2026 10:00 | 05/10/2026 09:59 | Não | Exceção |
| T26 | Limite Ordem Temporal (No limite, 0 min) (CV01) | 05/10/2026 10:00 | 05/10/2026 10:00 | Não | R$ 0,00 |
| T27 | Limite Ordem Temporal (Depois, +1 min) (CV01) | 05/10/2026 10:00 | 05/10/2026 10:01 | Não | R$ 0,00 |
| T28 | Partição VIP com Cortesia (CV10, CV01) | 05/10/2026 10:00 | 05/10/2026 10:15 | Sim | R$ 0,00 |
| T29 | Partição VIP com Hora Adicional (CV10, CV03) | 05/10/2026 10:00 | 05/10/2026 12:30 | Sim | R$ 12,50 |
| T30 | Partição VIP com Pernoite (CV10, CV04) | 05/10/2026 20:00 | 06/10/2026 10:00 | Sim | R$ 25,00 |
| T31 | Partição Nulidade - Entrada nula (CI04) | nulo | 05/10/2026 10:00 | Não | Exceção |
| T32 | Partição Nulidade - Saída nula (CI05) | 05/10/2026 10:00 | nulo | Não | Exceção |
| T33 | Partição Tarifa sem teto - 1079 min (CV08) | 05/10/2026 08:00 | 06/10/2026 01:59 | Não | R$ 100,00 |
