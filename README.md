# Labirinto IA v2.0.0

Busca em grade com Java 21, domínio imutável, algoritmos independentes e interface Swing.
Sem bibliotecas externas. Entrada: S início, E destino, X parede, . livre e 1–9 custo
de entrar na célula. Movimento ortogonal; custo do início não é cobrado.

## Compilar e testar

```sh
javac -encoding UTF-8 -d out src/maze/domain/*.java src/maze/search/*.java src/maze/ui/*.java tests/SearchTest.java LabirintoSolver.java
java -cp out SearchTest
java -cp out maze.ui.Main
```

No Windows PowerShell use `(Get-ChildItem src,tests -Recurse -Filter *.java).FullName`
como lista de fontes para javac, pois expansão de glob pode variar.

## Algoritmos e contrato

| Busca | Objetivo | Estrutura |
| --- | --- | --- |
| BFS | Menor número de passos, ignora pesos na decisão | Fila FIFO |
| Dijkstra | Menor custo positivo | Fila de prioridade por custo acumulado |
| A* | Menor custo positivo | Custo + Manhattan (admissível com custos ≥1) |

A implementação compartilha a expansão: BFS usa fila O(V+E); Dijkstra e A* usam
heap com custo O((V+E) log V).
Estados obsoletos são descartados e caminhos reconstruídos pelos predecessores.
Sem caminho retorna lista vazia/custo -1. Resultado inclui células expandidas.

## Testes e evolução

Validação de entradas, ausência de caminho, vizinhança, grade ponderada e comparação
A*/Dijkstra em 300 grades reproduzíveis. A grade não é modificada pela busca.
Interface permite editar a grade e selecionar algoritmo, limitada a 100×100 células.
A UI não é coberta pelos testes headless. A implementação v1 está preservada no Git.
