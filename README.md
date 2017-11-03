# programacao-linear



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
