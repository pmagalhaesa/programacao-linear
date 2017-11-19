/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.programacao.linear.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mycompany.programacao.linear.Resolucao;
/**
 *
 * @author paula
 */

public class ResolucaoServlet extends HttpServlet {

     public static double formataCampos(String campo){
         if(campo.trim().equals("")){
             return 0;
         }
         // Troca virgula por ponto e faz a conversao para double
         return Double.parseDouble(campo.replaceAll(",", "."));
     }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            

            // Pega as variaveis passadas por parametro do formulario, convertando o seu tipo para inteiro
           int qtdVarDecisoes  = Integer.parseInt(request.getParameter("qtdVarDecisoes"));
           int qtdRestricoes   = Integer.parseInt(request.getParameter("qtdRestricoes"));
           
           String[] funcaoObjetivaTela  = request.getParameterValues("funcaoObjetiva");
           String[] restricoesTela      = request.getParameterValues("restricoes");
           String tipoProblemaTela      = request.getParameter("tipoProblema");
           String[] sinalTela           = request.getParameterValues("sinal");
           
         
           // Guarda a quantidade de inequacoes
            int quantidadeDeInequacoes = 0;
            int quantidadeVariaveisArtificiais =0;
            /**
             * Descobre quantas inequacoes foram informadas
            **/
            for(int i = 0 ; i < sinalTela.length; i++){
                // Verifica se eh ≥ ou ≤
                if(sinalTela[i].equals(Resolucao.MAIORIGUAL) || sinalTela[i].equals(Resolucao.MENORIGUAL)){
                    // soma a quantidade total de inequacoes
                    quantidadeDeInequacoes++;
                }
                
                // Soma quantidade total de variaveis articifiais
                 if(sinalTela[i].equals(Resolucao.MAIORIGUAL)){
                     quantidadeVariaveisArtificiais++;
                 }
            }
           // São as variaveis de decisoes mais a quantidade de inequacoes e mais um que a DISPONIBILIDADE
           double[][] restricoesValores = new double[qtdRestricoes][(qtdVarDecisoes + quantidadeDeInequacoes + quantidadeVariaveisArtificiais + 1)];
           
           
            
            // Variavel para utilizar na separação dos valores de cada restrição
            boolean proximaRestricao = false;
            
            // Variavel usada para contar a restricao;
            int contadorRestricao               = 0;
            int contadorValoresDaRestricao      = 0;
            
            /**
             * Faz a transformação dos dados separando de uma forma melhor bidimensional
             * Primeira posicão é o número da restrição
             * Segunda posicão são os valores das variáveis
            **/
            for (int i = 0; i < restricoesTela.length; i++) {
                
                // Verificação para saber se a posição atual é igual número de variaveis de restrições, se for então já uma nova restrição
                if((i+1)%(qtdVarDecisoes+1) == 0){
                    // Preenche o valor da DISPONIBILIDADE sendo a ULTIMA POSICAO
                    restricoesValores[contadorRestricao][(qtdVarDecisoes + quantidadeDeInequacoes + quantidadeVariaveisArtificiais)] = ResolucaoServlet.formataCampos(restricoesTela[i]);
                    proximaRestricao = true;
                    contadorValoresDaRestricao = 0;
                    
                    // Incrementa para ir a próxima restrição.
                    contadorRestricao++;
                }else{
                    proximaRestricao = false;
                }
                
                // Apenas grava nessa linha se a próxima restrição for FALSA, porque se não o ultimo elemento(DISPONIBILIDADE) nunca será gravado.
                if(!proximaRestricao){
                    // Grava na restrição os seus valores, valor por valor.
                    restricoesValores[contadorRestricao][contadorValoresDaRestricao] = ResolucaoServlet.formataCampos(restricoesTela[i]);
                    // Incrementa o contador de valores das variaveis de decisões
                    contadorValoresDaRestricao++;
                }
            
            }
             /**
             * Define os valores corretos para as variaveis de axcesso ou folga
             * indiceVariaveisExtras usado para saber onde colocar os valores, em qual posicao
            **/
             int indiceVariaveisExtras = qtdVarDecisoes;
             for(int i = 0 ; i< sinalTela.length ; i++){
                // Verifica se eh folga ou acesso e retorna o valor 1 ou -1 ou 0
                int valorVariavelInequacao = Resolucao.resolveInequacao(sinalTela[i]);
                
                // Se for diferente de 0 significa que essa restricao eh uma inequacao, entao atribui o valor a ela.
                if(valorVariavelInequacao != 0){
                    restricoesValores[i][indiceVariaveisExtras] = valorVariavelInequacao;
                    /**
                    * Soma posicao, para que ele preenche na posicao correta
                    * Exemplo: 1x6 = 1; -1x7
                   **/
                    indiceVariaveisExtras++;
                }
             }

               /**
             * Define os valores corretos para as variaveis artificiais
             * indiceVariaveisExtras usado para saber onde colocar os valores, em qual posicao
            **/
             indiceVariaveisExtras = qtdVarDecisoes +quantidadeDeInequacoes;
             for(int i = 0 ; i< sinalTela.length ; i++){
    
                  // Verifica se tem que adicionar variavel artificial na linha
                if(sinalTela[i].equals(Resolucao.MAIORIGUAL)){
                     restricoesValores[i][indiceVariaveisExtras] = Resolucao.resolveInequacao(sinalTela[i]);
                    /**
                    * Soma posicao, para que ele preenche na posicao correta
                    * Exemplo: 1x6 = 1; -1x7
                   **/
                    indiceVariaveisExtras++;
                 }
             }
             
                 
           // Criando vetores para armazenar os dados no tipo correto(FLOAT) e no formato correto,   
            // Adiciona todas as variaveis de folga, excesso, artificial na funcao objetiva
        
           double[] funcaoObjetivaValores = new double[qtdVarDecisoes + quantidadeDeInequacoes + quantidadeVariaveisArtificiais];
           
           // Converte de String para FLOAT
            for (int i = 0; i < funcaoObjetivaTela.length; i++) {
            // Faz conversão do que veio da tela
            // se for minimizacao multiplica por -1
             /**
             * Definindo o tipo do problema
             * Maximição(max) ou Minimização(min)
             */
            if(tipoProblemaTela.equals(Resolucao.MINIMIZACAO)){
                // Definindo como minimizacao
                 funcaoObjetivaValores[i] = ResolucaoServlet.formataCampos(funcaoObjetivaTela[i]);
            }else{
                // Definindo como maximizacao
                 funcaoObjetivaValores[i] = ResolucaoServlet.formataCampos(funcaoObjetivaTela[i]);
            }
            
            }
         
            
             // Cria um novo objeto para chamar a solução e resolver o problema
             Resolucao resolucao= new Resolucao(qtdVarDecisoes, qtdRestricoes, funcaoObjetivaValores, restricoesValores, sinalTela, tipoProblemaTela);
             
             // Armazena o resultado de todas as operacoes
             String[] resultados = resolucao.criarProblema();
             String retornoResultadoParaTela="";
             
              /**
             * Monta string no  loop para mostrar os resultados
             * Exemplo: x1=3 , x2=9
            **/
             for(int i=0; i< resultados.length ; i++){
                 // Verificando se eh a ultima posicao para nao colocar virgula atoa no final do resultado
                 if(!(i > qtdVarDecisoes)){
                      if(i == resultados.length - 1){
                    retornoResultadoParaTela += " "+resultados[i];
                    }
                    else{
                       retornoResultadoParaTela += " "+resultados[i]+ ";";
                    }
                 }
                
             }
             
           
             // Cria atributo para que seja possivel pegar a pagina resultado.jsp
            request.setAttribute("retornoResultadoParaTela", retornoResultadoParaTela);
           // Definindo para onde vai ser redirecionado, no caso página modelagem.jsp
           request.getRequestDispatcher("resultado.jsp").forward(request, response);
        } catch(Exception ex) {
          System.out.println(ex.getMessage());
        }
    }

    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
