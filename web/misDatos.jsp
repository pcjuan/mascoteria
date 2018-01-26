<%@include file="templates/header.jsp" %>
<%@include file="templates/menu.jsp" %>

<c:if test="${not empty sessionScope.admin}">
    <c:set var="usuario" scope="page" value="${sessionScope.admin}"/>
</c:if>
<c:if test="${not empty sessionScope.person}">
    <c:set var="usuario" scope="page" value="${sessionScope.person}"/>
</c:if>

<c:if test="${not empty sessionScope.admin || not empty sessionScope.person}">

    <div class="row">
        <div class="col s5 offset-s3 z-depth-3">
            <div class="row">
                <div class="col s10 offset-s2">
                    <h4>Mis Datos</h4>
                </div>
            </div>
            <form action="control.do" method="POST">
                <div class="row">
                    <div class="input-field col s12 l12 m12">
                        <label for="rut" class="active">Rut</label>
                        <input type="hidden" name="rut" value="${usuario.rutUser}">
                        <input disabled type="text" value="${usuario.rutUser}" class="validate"  />
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12 l12 m12">
                        <label for="nombre" class="active">Nombre</label>
                        <input type="hidden" name="nombre" value="${usuario.nombreUser}">
                        <input disabled value="${usuario.nombreUser}" type="text" class="validate"  />
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12 l12 m12">
                        <label for="apellido" class="active">Apellido</label>
                        <input type="hidden" name="apellido" value="${usuario.apellidoUser}">
                        <input disabled value="${usuario.apellidoUser}" type="text" class="validate"  />
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12 l12 m12">
                        <label for="correo" class="active">Correo actual ${usuario.emailUser}</label>
                        <input name="correo" type="text" class="validate"  />
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12 l12 m12">
                        <label for="clave" class="active">Clave</label>
                        <input name="clave" type="password" class="validate"  />
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12 l12 m12">
                        <label for="confirmarClave" class="active">Confirmar Clave</label>
                        <input name="confirmarClave" type="password" class="validate"  />
                    </div>
                </div>
                <div class="row">
                    <button type="submit" name="btn" value="editarUsuario" class="btn waves-effect red darken-4">
                        Editar
                    </button>
                </div>
            </form>
            <br>
            <p class="${requestScope.tipo == 1? 'red': 'green'}-text">${requestScope.msg}</p>
        </div>
    </div>

</c:if>

<%@include file="templates/footer.jsp" %>