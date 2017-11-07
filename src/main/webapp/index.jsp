<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <jsp:include page="/header.jsp" />
<section class="container">
    <form class="form" method="POST" action="modelar">
        <h1 class="text-center">Problema de transporte</h1>
        <div class="form-group">
          <label>Quantidade de variáveis de decisão</label>
          <input type="number" class="form-control" id="qtdVarDecisoes" name="qtdVarDecisoes" required>
        </div>
        <div class="form-group">
          <label>Quantidade de restrições</label>
          <input type="number" class="form-control" id="qtdRestricoes" name="qtdRestricoes" required>
        </div>
        <button type="submit" class="btn btn-primary">Continuar</button>
      </form>
</section>

<jsp:include page="/footer.jsp" />