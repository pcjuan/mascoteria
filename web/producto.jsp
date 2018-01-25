<%@include file="templates/header.jsp" %>
<%@include file="templates/menu.jsp" %>

<c:if test="${not empty sessionScope.admin}">

    <c:set var="productos" scope="page" value="<%=this.service.getProductos()%>"/>
    <c:set var="categoria" scope="page" value="<%=this.service.getCategorias()%>"/>

    <form method="POST" action="control.do" enctype="multipart/form-data">
        <div class="row">
            <div class="col s5 offset-s3 z-depth-3">
                <div class="row">
                    <div class="col s10 offset-s2">
                        <h4>Nuevo Producto</h4>
                    </div>
                </div>
                <form action="control.do" method="POST">
                    <div class="row">
                        <div class="input-field col s12 l12 m12">
                            <label for="nombre" class="active">Nombre</label>
                            <input name="nombre" type="text" class="validate"  />
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 l12 m12">
                            <label for="precio" class="active">Precio</label>
                            <input name="precio" type="text" class="validate"  />
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 l12 m12">
                            <label for="unidad" class="active">Unidad</label>
                            <input name="unidad" type="text" class="validate"  />
                        </div>
                    </div>
                    <div class="row">

                        <div class="input-field col s12">
                            <textarea name="descripcion" id="descripcion" class="materialize-textarea"></textarea>
                            <label for="descripcion">Descripcion</label>
                        </div>
                    </div>
                    <div class="row">
                        <select name="idCategoria">
                            <c:forEach items="${pageScope.categoria}" var="c">
                                <option value="${c.idCategoria}">
                                    ${c.nombreCategoria}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row">
                        <div class="file-field input-field">
                            <div class="btn">
                                <span>Foto</span>
                                <input name ="foto" type="file">
                            </div>
                            <div class="file-path-wrapper">
                                <input class="file-path validate" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <button name="btn" value="agregarProducto" class="btn">
                            Crear
                        </button>
                    </div>
                </form>
                <br>
                <p class="${requestScope.tipo == 1? 'red': 'green'}-text">${requestScope.msg}</p>
            </div>
        </div>
    </form>

    <div class="row">
        <div class="col s5 offset-s3 z-depth-3">
            <table class="bordered striped">
                <thead>
                    <tr>
                        <th>Id Producto</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Unidades</th>
                        <th>Descripcion</th>
                        <th>Categoria</th>
                        <th>Foto</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pageScope.productos}" var="p">
                        <tr>
                            <td>${p.idProducto}</td>
                            <td>${p.nombreProducto}</td>
                            <td>$${p.precioProducto}</td>
                            <td>${p.unidadesProducto}</td>
                            <td>${p.descripcionProducto}</td>
                            <td>${p.categoria.nombreCategoria}</td>
                            <td>
                                <ct:TagImage array="${p.fotoProducto}" tam="50"></ct:TagImage>
                                </td>
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