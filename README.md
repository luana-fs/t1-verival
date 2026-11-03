# t1-verival

## Como executar no GitHub Codespaces

O Codespace é um ambiente virtual que permite rodar o projeto sem instalar nada na sua máquina. 

### Executar o projeto

Acesse o repositório no GitHub, clique em **Code** > **Codespaces** > **Create codespace on main**. Quando o ambiente terminar de iniciar, abra o terminal integrado do VS Code.

No terminal, rode:

```bash
make run
```

Esse comando inicia o container de desenvolvimento, compila o projeto com Maven e executa a aplicação. A saída da tarifa será exibida no terminal.

### Comandos disponíveis

| Comando | O que faz |
| --- | --- |
| `make build` | Prepara a imagem do container. |
| `make up` | Inicia o container de desenvolvimento. |
| `make compile` | Compila o projeto com Maven. |
| `make run` | Inicia o container, compila e executa a aplicação. |
| `make shell` | Abre um terminal Bash dentro do container. |
| `make down` | Para o container. |
| `make clean` | Remove os arquivos compilados na pasta `target`. |
| `mvn clean test` | Roda os testes e gera o relatório de cobertura do JaCoCo em `target/site/jacoco/index.html`. |
| `mvn test-compile org.pitest:pitest-maven:mutationCoverage` | Roda o teste de mutação e gera o relatório do PIT em `target/pit-reports/index.html`. |

Na primeira execução, o Makefile pode baixar ferramentas e dependências. Aguarde o término dos comandos antes de executar o próximo.

## Trabalho 2: cobertura (JaCoCo) e mutação (PIT)

No terminal do Codespace, rode os comandos abaixo na pasta do projeto:

```bash
mvn clean test
mvn test-compile org.pitest:pitest-maven:mutationCoverage
```

O primeiro gera o relatório de cobertura em `target/site/jacoco/index.html`. O segundo gera o relatório de mutação em `target/pit-reports/index.html`. Para ver os relatórios no Codespace, instale a extensão **Live Preview** (Microsoft), clique com o botão direito no `index.html` e escolha **Show Preview**.

A classe `App` fica fora das duas análises porque é só uma demonstração com `main`, sem regra de negócio.

