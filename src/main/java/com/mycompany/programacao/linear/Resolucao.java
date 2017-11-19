/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.programacao.linear;
import java.math.BigDecimal;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.GlpkException;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_prob;
import org.gnu.glpk.glp_smcp;

/**
 *
 * @author paula
 */
public class Resolucao {
    
    // Criando constantes
    public static final String MAIORIGUAL   = "maiorIgual";
    public static final String MENORIGUAL   = "menorIgual";
    public static final String IGUAL        = "igual";
    public static final String MAXIMIZACAO = "max";
    public static final String MINIMIZACAO  = "min";

    // Criando propriedades(atributos) para utilizacao da classe
    private int qtdVarDecisoes;
    private int qtdRestricoes;
    private double[] funcaoObjetivaValores;
    private double[][] restricoesValores;
    private String[] sinal;
    private String tipoProblema;
    
    /**
     * Propriedades(atributos) usadas com GLPK
    **/
    
    // Variavél utilizada para criar o problema inicial, onde serão jogados os resultados
    private  glp_prob problemaProgramacaoLinear = null;
            
    
            
            
            
     /**
     * Construtor da classe
     * @param int qtdVarDecisoes
     * @param int qtdRestricoes
     * @param String[] funcaoObjetiva
     * @param String[] restricoes
     * @param String sinal
     */
   
    public Resolucao(int qtdVarDecisoes, int qtdRestricoes, double[] funcaoObjetivaValores, double[][] restricoesValores, String[] sinal, String tipoProblema){
        this.qtdVarDecisoes             = qtdVarDecisoes;
        this.qtdRestricoes              = qtdRestricoes;
        this.funcaoObjetivaValores      = funcaoObjetivaValores;
        this.restricoesValores          = restricoesValores;
        this.sinal                      = sinal;
        this.tipoProblema               = tipoProblema;
    }
    
    /**
     * Cria o nome das colunas e os tipos
     * @param problema
     * @return problema
     */
    private glp_prob _defineColunas(glp_prob problema){

        // Criando as colunas com a quantidade de variaveis menos o elemento
        GLPK.glp_add_cols(problema, this.restricoesValores[0].length -1); // qtdVarDecisoes

         /**
          * 
          * Criando colunas
          */
        for(int i = 0 ; i <  this.restricoesValores[0].length -1; i++){ 
            int coluna = i+1;
            // Definindo o nome da coluna
            GLPK.glp_set_col_name(problema, coluna, "x"+coluna);

            // Define o tipo da coluna como valor continuo
            GLPK.glp_set_col_kind(problema, coluna, GLPKConstants.GLP_CV);
            
            // Regra de não negatividade x1 ≥ 0, x2 ≥ 0, x3 ≥ 0
            GLPK.glp_set_col_bnds(problema, coluna, GLPKConstants.GLP_LO, 0, 0);
            
            
        }
        return problema;
    }
    
    
    
    /**
     * Define os valores de DISPONIBILIDADE ao problema
     * @param problema
     * @return problema
     */
    private glp_prob _defineLinhas(glp_prob problema){
         
        
        // Criando as linhas com a quantidade de restrições
        GLPK.glp_add_rows(problema, this.restricoesValores.length);

         // Criando informações das tabelas de acordo com a quantidade de restrições e variaveis de decisão.
        for(int i = 0 ; i < this.restricoesValores.length; i++){
            int linha = i+1;
            
            /**
             * Definindo o nome da linha(restricao)
             * Exemplo: r1, r2, r3
             **/
            
            GLPK.glp_set_row_name(problema, linha, "r"+linha);

            /**
             * Definindo a DISPONIBILIDADE de cada restrição
             * NO GLPK conhecida como bounds(limites) 
             * Usado metodo que verifica qual o tipo de limite que deve ser adicionado
             **/
             double[] valoresDeLimites = this._valoresDosLimites(i); // esse eh a chave para funcionar minimizacao e maximizacao
            GLPK.glp_set_row_bnds(problema, linha, this._tipoDeLimite(i), valoresDeLimites[0], valoresDeLimites[1]);
            
        }
        return problema;
    }
   
    

    
    /**
     * Verifica qual o tipo de limite a ser definido de acordo com a restrição e define seu valor
     * Essa eh a chave, com ele definto certinho o que o GLPK chama de bounds!
     * ≤, =, ≥
     * @param problema
     * @return double[][]
     */
    private double[] _valoresDosLimites(int numeroDaRestricao){
        // define retorno
        double[] retorno = new double[2];
      
        // Se foi ≤
        if(this.sinal[numeroDaRestricao].equals(Resolucao.MENORIGUAL)){
             retorno[0] = 0;
             retorno[1] = this.restricoesValores[numeroDaRestricao][this.restricoesValores[numeroDaRestricao].length -1]; 
        }
        // se foi ≥
        else if(this.sinal[numeroDaRestricao].equals(Resolucao.MAIORIGUAL)){
             retorno[0] = this.restricoesValores[numeroDaRestricao][this.restricoesValores[numeroDaRestricao].length -1];
             retorno[1] = Double.POSITIVE_INFINITY;
        }
        return retorno;
    }
    
    /**
     * Verifica qual o tipo de limite a ser definido de acordo com a restrição
     * ≤, =, ≥
     * @param problema
     * @return int
     */
    private int _tipoDeLimite(int numeroDaRestricao){
        // Verifica o sinal que o usuario escolhei, se foi =
        if(this.sinal[numeroDaRestricao].equals(Resolucao.IGUAL)){
            return GLPKConstants.GLP_FX;
        }
        // Se foi ≤
        else if(this.sinal[numeroDaRestricao].equals(Resolucao.MENORIGUAL)){
            // Limite alto
            return GLPKConstants.GLP_UP; 
        }
        // se foi ≥
        else if(this.sinal[numeroDaRestricao].equals(Resolucao.MAIORIGUAL)){
            // lIMITE BAIXO
             return GLPKConstants.GLP_LO;
        }
        return GLPKConstants.GLP_UP;
    }
    
    /**
     * Cria as LINHAS de restrições e seus VALORES
     * @param problema
     * @return problema
     */
    private glp_prob _defineValores(glp_prob problema){
        
        // Criando um indice INTEIROS da forma que o swigtype entende.
        SWIGTYPE_p_int indicesSwig;
        
        // Criando valores da forma que o swigtype entende.
        SWIGTYPE_p_double valoresSwig;
        

        // Criando os indices inteiros e seus valores que o Swig entende do TAMANHO da QUANTIDADE DE (VARIAVEIS DE DECICOES + 1) usa a ultima casa para guardar outro valor calculado(CONFIRMAR ISSO)
        indicesSwig = GLPK.new_intArray(this.restricoesValores[0].length); //this.qtdVarDecisoes +1
        valoresSwig = GLPK.new_doubleArray(this.restricoesValores[0].length); //this.qtdVarDecisoes +1
        
        
         // Criando informações das tabelas de acordo com a quantidade de restrições e variaveis de decisão.
        for(int i = 0 ; i < this.restricoesValores.length; i++){
            int linha = i+1;
            int indiceSwig = 1;
            // Elimita o ultimo elemento ja que ele eh a DISPONIBILIDADE
            for(int k = 0 ; k < this.restricoesValores[i].length-1; k++){
               
                // Setando os itens dos indices criados
                GLPK.intArray_setitem(indicesSwig, indiceSwig, indiceSwig);
                 
                 
                 
                /**
                 * Adicionando os valores de cada variavel em cada restricao
                 * Exemplo: 2x1 +2x2 +6x3
                 **/
                GLPK.doubleArray_setitem(valoresSwig, indiceSwig, this.restricoesValores[i][k]); 
                
                indiceSwig++;
            }
            
            /**
             * Seta a MATRIZ(tabela)
             * Com i = restricao atual e this.qtdVarDecisoes(numero de restricoes ele tem)
             * Exemplo: r1(i) = 2x1 +2x2 +6x3 (total de 3)
             * Ficando (i, 3)
             **/
           
            GLPK.glp_set_mat_row(problema, linha, this.restricoesValores[i].length-1 , indicesSwig, valoresSwig); // this.qtdVarDecisoes
            
        }
        
        // Deleta indices para não estourar a memoria
        GLPK.delete_intArray(indicesSwig);
        GLPK.delete_doubleArray(valoresSwig);
        return problema;
    }
    
    

    /**
     * Define o objetivo do problema, setando seus valores
     * @param problema
     * @return problema
     */
    private glp_prob _defineObjetivo(glp_prob problema){
         
        // Dando nome para coluna do objetivo
        GLPK.glp_set_obj_name(problema, "z");
        
        /**
         * Definindo o tipo do problema
         * Maximição(max) ou Minimização(min)
         */
        if(this.tipoProblema.equals(Resolucao.MINIMIZACAO)){
            // Definindo como minimizacao
            GLPK.glp_set_obj_dir(problema, GLPKConstants.GLP_MIN);
        }else{
            // Definindo como maximizacao
            GLPK.glp_set_obj_dir(problema, GLPKConstants.GLP_MAX);
        }
       
        /**
         * Criando os valores dos valores(coeficientes) da função objetiva
         * Exemplo: z = 10x1 + 6x2 + 4x3
         */
        for(int i = 0 ; i < this.funcaoObjetivaValores.length; i++){
            int indice = i+1;
            // chamado de coeficiente pelo glpk( sao os valores que se encontram na funcao objetiva
            GLPK.glp_set_obj_coef(problema, indice, this.funcaoObjetivaValores[i]);
        }
       
        return problema;
    }
    
    /**
     * Define o tipo de metodo a ser usado e verifica se ele esta na estrutura certa para ser usado
     * @param problema
     * @return boolean
     */
    
    private boolean _podeSerSolucionado(glp_prob problema){
        
        // Cria parametro  de controle para o problema
        glp_smcp parametroDoProblema = new glp_smcp();
        
        // Definindo tipo do simplex
        parametroDoProblema.setMeth(GLPKConstants.GLP_DUALP);
        // Inicia o tipo do problema(simplex) e guardando dados de parametros criados por ele, inicia parametros de controle
        GLPK.glp_init_smcp(parametroDoProblema);

        /**
         * Retorno da chamada do metodo de simples, verificando a tabela montada esta no padrao correto, para ser solucionado ou não
         * Não significa que encontrou a solução otima, mas que é possível ter uma solução
         **/
        int ret = GLPK.glp_simplex(problema, parametroDoProblema);
        
        // Se for 0 entao ele PODE ser solucionado
        return ret == 0;
    }
    
    /**
     * Define o tipo de metodo a ser usado e verifica se ele esta na estrutura certa para ser usado
     * @param problema
     * @return boolean
     */
    
    private String[] _verSolucaoSimplex(glp_prob problema){
        
        // Cria um vetor de string para armazenar cada valor separadamente, do tamanho do numero de colunas do GLPK
        String[] resultados = new String[GLPK.glp_get_num_cols(problema) + 1 ];
        
        // Busca resultado a Funcao Objetiva F.O
        String nomeDaFuncaoObjetivo = GLPK.glp_get_obj_name(problema);
        Double valorDaFuncaoObjetiva = GLPK.glp_get_obj_val(problema);
        
        // Define a primeira posicao com o resultado da funcao objetiva
        resultados[0] = (nomeDaFuncaoObjetivo+ " = " + valorDaFuncaoObjetiva).replaceAll("\\.", ",");
        // Faz o loop em todas as colunas do problema, começando pela coluna 1, já que não possui coluna 0
        for (int i = 1; i <= GLPK.glp_get_num_cols(problema); i++) {
            // Pega o nome da coluna
            String nomeDaColuna = GLPK.glp_get_col_name(problema, i);
            
            // Busca o valor primário da coluna(solução básica)
//            // Converte o valor para double
            double valorDaColuna = GLPK.glp_get_col_prim(problema, i);
           /**
             * Monta os resultados em cada posicao do vetor
             * Exemplo:
             * Posicao 1 | x1=6
             * Posicao 2 | x2=3
            **/
            resultados[i] = (nomeDaColuna+ " = " + valorDaColuna).replaceAll("\\.", ",");
        }
        
        return resultados;
    }
    /**
     * Cria problema, montando a estrutura da tabela necessária para isso
     */
    public String[] criarProblema(){
     SWIGTYPE_p_int ind; 
     SWIGTYPE_p_double val;
     String[] retorno = new String[1];
     try{
        /**
         * Criando o problema para ser resolvido com o GLPK
         * É criado como vazio
         **/
        problemaProgramacaoLinear = GLPK.glp_create_prob();

        // Dando um nome para o problema
        GLPK.glp_set_prob_name(problemaProgramacaoLinear, "Problema de transporte");

       // Chama metodo para criar colunas de VARIAVEIS 
       problemaProgramacaoLinear = this._defineColunas(problemaProgramacaoLinear);

        //Chama metodo para definir os VALORES de DISPONIBILIDADE
       problemaProgramacaoLinear = this._defineLinhas(problemaProgramacaoLinear);

       // Chama metodo para criar linhas de RESTRIÇÕES 
       problemaProgramacaoLinear = this._defineValores(problemaProgramacaoLinear);

       // Chama metodo para definr o objetivo do problema
       problemaProgramacaoLinear = this._defineObjetivo(problemaProgramacaoLinear);

       // Verifica se pode ser solucionado de acordo com o padrão GLP
       if(this._podeSerSolucionado(problemaProgramacaoLinear)){
           // Guarda retorno para retornar
           retorno =  this._verSolucaoSimplex(problemaProgramacaoLinear);
       }else{
           // Monta retorno do mesmo tipo vetor de string, com apenas uma posicao dizendo qual a mensagem
           retorno[1] = "Problema não pode ser solucionado";
       }
     // Deleta o problema criado 
       GLPK.glp_delete_prob(problemaProgramacaoLinear);
       
      
    } catch (GlpkException ex) {
        ex.printStackTrace(); 
      }
      return retorno;
    }
    
    
    /**
     * Retorna versão do GLPK, só para verifica se ele está funcionando
     * @return string
     */
    
    public static String getVersaoGLPK(){
      System.out.println(GLPK.GLP_ASN_MAX);
        return GLPK.glp_version();
    }
    
    public static int resolveInequacao(String inequacao){
          // Se foi ≤ variavel de EXCESSO
        if(inequacao.equals(Resolucao.MENORIGUAL)){
            return 1; 
        }
        // se foi ≥ variavel de FOLGA
        else if(inequacao.equals(Resolucao.MAIORIGUAL)){
             return -1;
        }
        else{
            return 0;
        }
    }
    
}
