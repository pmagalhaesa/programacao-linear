/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.programacao.linear;
import org.gnu.glpk.GLPK;

/**
 *
 * @author paula
 */
public class Resolucao {

    /**
     * Construtor da classe
     */
    public Resolucao(){}
    
    /**
     * Retorna versão do GLPK
     * @return string
     */
    
    public static String getVersaoGLPK(){
      System.out.println(GLPK.GLP_ASN_MAX);
        return GLPK.glp_version();
    }
    
    
}
