<%-- 
    Document   : modelagem
    Created on : 03/11/2017, 20:43:00
    Author     : 949340
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <jsp:include page="/header.jsp" />
        <form class="form-inline" method="POST" action="/modelar">
            <div class="form-group">
              <label>Quantidade de váriavéis de decisão</label>
              <input type="number" class="form-control" id="qtdVarDecisoes" required>
            </div>
            <div class="form-group">
              <label>Quantidade de restrições</label>
              <input type="number" class="form-control" id="qtdRestricoes" required>
            </div>
            <button type="submit" class="btn btn-default">Continuar</button>
          </form>
<jsp:include page="/footer.jsp" />