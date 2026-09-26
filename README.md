# t1-verival

## Como executar no GitHub Codespaces

O Codespace é um ambiente virtual que permite rodar o projeto sem instalar nada na sua máquina. 

### Executar o projeto

Acesse o repositório no GitHub, clique em **Code** > **Codespaces** > **Create codespace on main**. Quando o ambiente terminar de iniciar, abra o terminal integrado do VS Code.

No terminal, rode:

```bash
make build
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

Na primeira execução, o Makefile pode baixar ferramentas e dependências. Aguarde o término dos comandos antes de executar o próximo.