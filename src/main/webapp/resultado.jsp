<%-- 
    Document   : modelagem
    Created on : 17/11/2017, 20:36:00
    Author     : 949340
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <jsp:include page="/header.jsp" />
        
 <section class="box">
    <div class="form-inline centralizado">
         <h1 class="text-center title-problema">Resultados</h1>
        <% 

           // Pega as variaveis passadas por parametro do formulario, convertando o seu tipo para inteiro
           String retornoResultadoParaTela  = request.getAttribute("retornoResultadoParaTela").toString();
           %>
                  

                   <div class="input-group">
                    <span class="input-group-addon" id="basic-addon2"><%= retornoResultadoParaTela %></span>
                   </div>

               <div class="row">
                   <div class="col-md-12">
                        <button onclick="window.history.go(-2)"class="btn btn-default">Novo Problema</button>
                    </div>
               </div>
                   
           
   </div>
 </section>

                    <%-- JQUERY para trocar a label de maximizacao ou minimizacao --%>
                    <script>
                        $(document).ready(function(){
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