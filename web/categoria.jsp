<%@include file="templates/header.jsp" %>
<%@include file="templates/menu.jsp" %>

<c:if test="${not empty sessionScope.admin}">

    <div class="row">
        <div class="col s5 offset-s3 z-depth-3">
            <form method="POST" action="control.do">
                <div class="col s5 offset-s4">
                    <div class="row">
                        <div class="col s10 offset-s2">
                            <h4>Nueva Categoria</h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 l12 m12">
                            <label for="nombreCategoria" class="active">Nombre</label>
                            <input name="nombreCategoria" type="text" class="validate"  />
                        </div>
                    </div>

                    <div class="row">
                        <div class="input-field col s10 offset-s1">
                            <button type="submit" name="btn" class="btn waves-light waves-effect red darken-4" value="agregarCategoria" />Guardar</button>
                        </div>
                    </div>
                </div>  
            </form>

            <p class="${requestScope.tipo == 1? 'red': 'green'}-text">${requestScope.msg}</p>
        </div>
    </div>

    <div class="row">
        <div class="col s5 offset-s3 z-depth-3">
            <c:set var="cat" scope="page" value="<%=this.service.getCategorias()%>"/>
            <table class="bordered striped">
                <thead>
                    <tr>
                        <th>Id Categoria</th>
                        <th>Nombre</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pageScope.cat}" var="c">
                        <tr>
                            <td>${c.idCategoria}</td>
                            <td>${c.nombreCategoria}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</c:if>

<c:if test="${not empty sessionScope.person}">
    <%        request.getRequestDispatcher("inicio.jsp").forward(request, response);
    %>
</c:if>

<%@include file="templates/footer.jsp" %>