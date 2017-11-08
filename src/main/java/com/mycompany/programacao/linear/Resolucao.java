/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.programacao.linear;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.glp_prob;

/**
 *
 * @author paula
 */
public class Resolucao {
    
    // Criando constantes
    private static final String MAIORIGUAL  = "maiorIgual";
    private static final String MENORIGUAL  = "menorIgual";
    private static final String IGUAL       = "igual";

    // Criando propriedades(atributos) para utilizacao da classe
    private int qtdVarDecisoes;
    private int qtdRestricoes;
    private String[] funcaoObjetiva;
    private String[] restricoes;
    private String sinal;
    
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
   
    public Resolucao(int qtdVarDecisoes, int qtdRestricoes, String[] funcaoObjetiva, String[] restricoes, String sinal){
        this.qtdVarDecisoes = qtdVarDecisoes;
        this.qtdRestricoes  = qtdRestricoes;
        this.funcaoObjetiva = funcaoObjetiva;
        this.restricoes     = restricoes;
        this.sinal          = sinal;
     
    }
    
    public void resolver(){
     // Criando o problema para ser resolvido com o GLPK
     problemaProgramacaoLinear = GLPK.glp_create_prob();
    }
    /**
     * Retorna versão do GLPK
     * @return string
     */
    
    public static String getVersaoGLPK(){
      System.out.println(GLPK.GLP_ASN_MAX);
        return GLPK.glp_version();
    }
    
    
}
