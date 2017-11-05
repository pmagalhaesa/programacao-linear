
# Programação Linear
> Em matemática, problemas de Programação Linear (PL) são problemas de optimização nos quais a função objetivo e as restrições são todas lineares. Programação Linear é uma importante área da optimização por várias razões.

# Problema de Alocação
## O que busca resolver?
> Resolve problemas em que o objetivo é encontrar a **função que minimiza** o custo somado de todas as alocações, respeitando as restrições existentes.

## Sugestão de modelo de otimização
> Alocar funcionários a quaisquer tarefas, sendo que cada alocação tem um custo que varia dependendo da tarefa a ser exercida e agente que irá trabalhar e com o objetivo de realizar todas as tarefas sendo que uma pessoa pode exercer apenas uma tarefa e a soma do custo total das alocações seja minimizado.



# LINKS
- http://www.phpsimplex.com/pt/problema_transporte_mercadorias.htm
- http://www.phpsimplex.com/pt/teoria_metodo_simplex.htm
- http://www.phpsimplex.com/simplex/simplex.htm?l=pt

# Simplex
- Deve atender apenas desigualdades do tipo `<=`
	- Restrições do tipo `>=` ou `=` devem ser padrõnizados, caso **não** seja possível tera que utilizar algum algoritmo como o de **Duas Fases**
- Seus coeficientes independentes sejam **maiores** ou **iguais** a **0**

## Condições
- O objetivo é **maximizar** ou **minimzar** o valor da **função objetiva**
- **TODAS** as **restrições** devem ser equações de **igualdade**
- **TODAS** as váriaveis (**xi**) devem ser **positivar** ou **nulas**
- Os termos independentes (**bi**) de cada equação devem ser **não negativos**

## Tipos de Otimização
### Minimização
- Critério de **parada**
	- Quando na linha **Z** **NÃO** aparece nenhum **valor positivo**
- Condições de **entrada** na **base**
	- O **maior** valor **positivo** na linha **Z** indica a variável **Pj** entra na base
- Condições de **saída** da **base**
	- Depois de obter a variável de **entrada**, determina-se a variável de **saída** pr meio do **menor quociente P0/Pj** dos valores **estritamente negativos**
- Dica
	- O problema de **minimizar Z** é equivamente ao problema de **maximizar (-1) Z**. Onde quando tiver a **solução** basta **multiplicar** por **-1** 

