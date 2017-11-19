<%-- 
    Document   : modelagem
    Created on : 03/11/2017, 20:43:00
    Author     : 949340
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <jsp:include page="/header.jsp" />
        
 <section class="box">
    <form class="form-inline centralizado" method="POST" action="resolucao">
        
        <% 

           // Pega as variaveis passadas por parametro do formulario, convertando o seu tipo para inteiro
           int qtdVarDecisoes  = Integer.parseInt(request.getParameter("qtdVarDecisoes"));
           int qtdRestricoes   = Integer.parseInt(request.getParameter("qtdRestricoes"));


           %>
           <input type="hidden" name="qtdVarDecisoes" value="<%= qtdVarDecisoes %>" />
           <input type="hidden" name="qtdRestricoes" value="<%= qtdRestricoes %>" />
         
            <div class="input-group">
                <label>Tipo do Problema:</label>
                      <select name="tipoProblema" id="tipoProblema" class="form-control col-md-2">
                       <option value="max">Maximização</option>
                       <option value="min" selected>Minimização</option>
                     </select>
            </div>

           
           <div class="row">
               <div class="form-group col-md-12">
                   <label>F.O <i class="fa fa-long-arrow-right" aria-hidden="true"></i> <span id="tipoLabel">Min</span> Z = </label>

                       <%        
                           
                      /**
                       * 
                       * Faz loop para montar a funcão objetiva(FO)
                       * Dependo da quantidade de variavéis de decisão
                       * Exemplo: 2
                       * F.O = x1 + 7x2 
                       * 
                      **/

                       for(int i = 0 ; i< qtdVarDecisoes; i ++){ %>
                           <div class="input-group col-md-3">
                               <input type="number" step="0.0001" class="form-control" name="funcaoObjetiva" required>
                               <%-- Soma +1 no contador para não mostrar 5X0 + 3X1 --%>
                               <span class="input-group-addon" id="basic-addon2">x<span class="letraPequena"><%= (i+1)%></span></span>
                           </div>
                           <%-- Verifica se é a ultima variável de rstrição, para não exibir o formato 2x1 + 3x2 + 5x3 + --%>
                               <label><%= ((i+1) != qtdVarDecisoes ? "<i class='fa fa-plus' aria-hidden='true'></i>": "") %></label> 
                    <% }%>

               </div>
           </div>

           <%                 
          
           /**
            * 
            * Faz loop para montar os campos(LINHAS) de restrições
            * Exemplo: 6x1 + 7x2 <= 0
           **/

           for(int i = 0 ; i< qtdRestricoes; i ++){  %>     
               <div class="row">
                   <div class="col-md-12">
                   <%-- Soma +1 no contador para não mostrar R0 --%>
                   <label>R<%= (i+1)%> <i class="fa fa-long-arrow-right" aria-hidden="true"></i></label>
                  <%
                   for(int j = 0 ; j< qtdVarDecisoes; j ++){ %>
                      <div class="input-group col-md-2">
                          <input type="number" step="0.0001" class="form-control" name="restricoes" required>
                          <%-- Soma +1 no contador para não mostrar 5X0 + 3X1 --%>
                          <span class="input-group-addon" id="basic-addon2">x<span class="letraPequena"><%= (j+1)%></span></span>
                      </div>
                      <%-- Verifica se é a ultima variável de rstrição, para não exibir o formato 2x1 + 3x2 + 5x3 + --%>
                          <label><%= ((j+1) != qtdVarDecisoes ? "<i class='fa fa-plus' aria-hidden='true'></i>": "") %></label> 
           <% } %>
                    <div class="input-group">
                         <select name="sinal" class="form-control col-md-1">
                          <option value="menorIgual">≤</option>
                          <option value="maiorIgual">≥</option>
                          <option value="igual">=</option>
                        </select>
                    </div>
               
                    <div class="input-group col-md-2">
                          <%-- Restricoes --%>
                          <input type="number" step="0.0001" class="form-control" name="restricoes" required>
                      </div>
               </div>
           </div>
                   <% }
                    String todasVariaveisDecicoes = "";
                   /**
                    * Loop para montar as restrições de não negatividade
                    * Exemplo: x1, x2, x3 ≥ 0
                    */

                   for(int i = 0 ; i< qtdVarDecisoes; i ++){
                        if((i+1) != qtdVarDecisoes){
                            todasVariaveisDecicoes += "x<span class='letraPequena'>"+(i+1)+"</span>, ";
                        }else{
                            todasVariaveisDecicoes += "x<span class='letraPequena'>"+(i+1)+"</span> ≥ 0";
                        }
                   }
                   %>

                   <div class="input-group">
                    <span class="input-group-addon" id="basic-addon2"><%= todasVariaveisDecicoes %></span>
                   </div>

               <div class="row">
                   <div class="col-md-12">
                        <button type="submit" class="btn btn-primary">Continuar</button>
                        <button type="button" class="btn btn-default" onclick="history.back()">Voltar</button>
                    </div>
               </div>
               
   </form>
 </section>

                    <%-- JQUERY para trocar a label de maximizacao ou minimizacao --%>
                    <script>
                        $(document).ready(function(){
                             var tipo = $("#tipoProblema").find(":selected").val();
                                if(tipo == "max"){
                                    $("#tipoLabel").text("Max");
                                }else{
                                      $("#tipoLabel").text("Min");
                                }
                            $("#tipoProblema").change(function(){
                                var tipo = $(this).find(":selected").val();
                                if(tipo == "max"){
                                    $("#tipoLabel").text("Max");
                                }else{
                                      $("#tipoLabel").text("Min");
                                }
                            });
                            
                            //Ajax, quando formulario for enviado
//                            $("form").submit(function(evt){
//                                
//                                // Cancelando o evento de enviar do formulario
//                                evt.preventDefault();
//                                evt.stopPropagation();
//                                
//                                $.post("resolucao",{}, function(resposta){
//                                    $("#resultados").text(resposta);
//                                    $("#resposta").show();
//                                });
//                            });
                        });
                    </script>
<jsp:include page="/footer.jsp" />