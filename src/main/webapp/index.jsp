<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <jsp:include page="/header.jsp" />
<section class="container box">
    <form class="form" method="POST" action="modelar">
        <h1 class="text-center title-problema">Problema de transporte</h1>
        <div class="centralizado">
            <div class="form-group col-md-3 col-sm-12">
                <label>Quantidade de variáveis de decisão:</label>
                <input type="number" class="form-control" id="qtdVarDecisoes" name="qtdVarDecisoes" required>
             </div>
            <div class="form-group col-md-3 col-sm-12">
              <label>Quantidade de restrições:</label>
              <input type="number" class="form-control" id="qtdRestricoes" name="qtdRestricoes" required>
            </div>
        </div>
        <div class="row text-center">
             <button type="submit" class="btn btn-primary btn1">Continuar</button>
        </div>
      </form>
</section>

<jsp:include page="/footer.jsp" />