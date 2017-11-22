# Programação Linear
> Em matemática, problemas de Programação Linear (PL) são problemas de optimização nos quais a função objetivo e as restrições são todas lineares. Programação Linear é uma importante área da optimização por várias razões.
# Problema de MIX de Produtos
## O que busca resolver?
> Busca identificar qual o mix de produtos que gera a maior lucro de acordo com um criterios de custos e restrições
## Sugestão de modelo otimização
> Produzir no mínimo 20 computadores sendo que existem 2 modelos e lucros diferentes com gabinetes pequeno e grandes e unidades de disco diferentes. Existem no estoque 60 unidades do gabinete pequeno, 50 unidades do gabinete grande e 120 unidades de disco. 
# Problema de Transporte
## O que busca resolver?
> Resolve problemas de programação linear.
## Sugestão de modelo de otimização
> Melhorar o fluxo de transporte de mercadorias da fábrica até os revendedores

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
	- Quando na linha **Z** **NÃO** aparece nenhum **valor negativo**
- Condições de **entrada** na **base**
	- O **maior** valor **positivo** na linha **Z** indica a variável **Pj** entra na base
- Condições de **saída** da **base**
	- Depois de obter a variável de **entrada**, determina-se a variável de **saída** pr meio do **menor quociente P0/Pj** dos valores **estritamente negativos**
- Dica
	- O problema de **minimizar Z** é equivamente ao problema de **maximizar (-1) Z**. Onde quando tiver a **solução** basta **multiplicar** por **-1** 


# GLPK
> É um pacote de soluções para resolver problema linear de larga escala
- Possui uma biblioteca em **C**
- Faz integração com o **java** por meio do **JNI**
	- **JNI** permite integrar linguagens **diferentes** de **java**

	## Instalação
	### Windows
	- Baixe o GLPK nesse [link](https://sourceforge.net/projects/winglpk/)
	- Copie a pasta extraiada para `C:\Arquivos de Problemas\GLPK`
	- Copie os arquivos **glpk_4_63_java.dll** e **glpk_4_63.dll** para dentro da pasta `C:\Windows\System32` e `C:\Windows\SysWOW64`

## Explicação

- Função objetiva (1.1)
	- Teorico
		- `z = c1xm+1 + c2xm+2 + . . . + cnxm+n + c0`
	- Exemplo
		- `z = 10x1 + 6x2 + 4x3`
- Restrições (1.2)
	- Teorico
		- `x1 = a11xm+1 + a12xm+2 +...+ a1nxm+n`
		-  `x2 = a21xm+1 + a22xm+2 +...+ a2nxm+n`
		-  ..............
		- `xm =am1xm+1 +am2xm+2 +...+amnxm+n`
	- Exemplo
		- `p= x1+x2+x3`
		- `q=10x1 +4x2 +5x3`
		- `r= 2x1 +2x2 +6x3`
- Limites das variáveis (1.3)
	- Teorico
		-	`l1≤ x1 ≤ u1`
		- `l2≤ x2 ≤u2`
		- .....
		- `lm+n ≤ xm+n ≤ um+n`
	- Exemplo
		- `−∞ < p ≤ 100`
		- `−∞ < q ≤ 600`
		- `−∞ < r ≤ 300`
		- `0 ≤ x1 < +∞`
		- `0 ≤ x2 < +∞`
		- `0 ≤ x3 < +∞`


Nome 																							| Formulas Teorica  																| Formulas Exemplo
--------------------------------------------------|---------------------------------------------------|-----------
Função objetivo 																	| z = c1xm+1 + c2xm+2 + . . . + cnxm+n + c0  				| z = 10x1 + 6x2 + 4x3 
Restrições																				| x1 = a11xm+1 + a12xm+2 +...+ a1nxm+n 							| 10x1 +4x2 +5x3 ≤ 600 
Limites das variáveis 														| l1≤ x1 ≤ u1  																			| ≤ 600

Nome 							| GLPK		 														| Aula 									| Explicação 
------------------|-------------------------------------|-----------------------|-----------
x1,x2,x3  				| Variáveis auxiliares(auxiliary)			| Restrições(R1,R2,R3)	| São as **linhas(rows)** da matriz de restrições
Xm+1, Xm+2, Xm+n  | Variáveis estrutural(structural)		| Valores das restrições| São as **colunas(columns)** da matriz de restrições
z									| Função objetiva(objective function)	| Função objetiva				| ≤, =, ≥ Aplicadas nas variáveis auxiliares e estruturais
c1,c2,c3  				| (objective coefficients)						| ??????								| 
c0								| Termo constante(shift) 							| ??????
a11,a12,amn 			| Coeficientes das restrições 				| ??????
l1,l2,lm+n 				| Limites inferiores (lower bounds)		| ??????
u1,u2,um+n 				| Limites superiores(upper bounds) 		| ??????



### Resolução
> Para resolver o problema deve atender as seguintes critérios levando em conta a explicação acima:
- Satisfazer todas as restrições **(1.2)**
- Estar dentro dos limites **(1.3)**
- Fornecer o menor(minimização) ou maior(maximização) da função objetivo **(1.1)**
