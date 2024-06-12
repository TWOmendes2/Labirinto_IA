# Labirinto Solver

Este projeto implementa um solucionador de labirintos em Java utilizando o algoritmo A* (A-star). Ele também inclui uma representação visual do labirinto e do caminho mais curto encontrado pelo agente através de uma interface gráfica Swing.

## Funcionalidades

- **Representação do Labirinto:**
  - O labirinto é representado por uma matriz onde cada célula pode ser:
    - 'S' para o ponto de início.
    - 'E' para o ponto de fim.
    - 'X' para obstáculos.
    - '0' para espaços vazios por onde o agente pode passar.

- **Implementação do Agente:**
  - O agente é capaz de encontrar o caminho mais curto do ponto de início ('S') ao ponto de fim ('E') utilizando o algoritmo A*.
  - Ele evita obstáculos ('X') e calcula o caminho baseado na heurística de distância Manhattan.

- **Visualização Gráfica:**
  - Utiliza Java Swing para representar visualmente o labirinto e o caminho encontrado.
  - O caminho é destacado em amarelo ('2') dentro do labirinto.

## Como Usar

### Pré-requisitos

- Java Development Kit (JDK) instalado.

### Execução

1. Clone o repositório:

git clone https://github.com/TWOmendes2/labirinto-solver.git
cd labirinto-solver


2. Compile e execute o código:

javac LabirintoSolver.java
java LabirintoSolver


3. Após executar o programa, a interface gráfica será exibida com o labirinto e o caminho mais curto encontrado.

## Exemplos de Labirintos

O código inclui vários labirintos predefinidos dentro do array `predefinedMazes` na classe `LabirintoSolver`. Cada labirinto é representado por uma matriz de caracteres ('S' para início, 'E' para fim, 'X' para obstáculos, '0' para espaços vazios).
