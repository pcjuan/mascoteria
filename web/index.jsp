<%@include  file="templates/header.jsp" %>

<div class="row">
    <div class="col s5 offset-s3 z-depth-3">
        <form method="POST" action="control.do">
            <div class="col s5 offset-s4">
                <div class="row">
                    <div class="col s10 offset-s2">
                        <h4>Mascotas</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12 l12 m12">
                        <label for="rut" class="active">Rut</label>
                        <input value="${cookie.rut.value}" name="rut" type="text" class="validate"  />
                    </div>
                </div>

                <div class="row">
                    <div class="input-field col s12 l12 m12">
                        <label for="clave">Clave</label>
                        <input type="password" name="clave" class="validate"  />
                    </div>
                </div>

                <div class="row">
                    <div class="input-field col s10 offset-s1">
                        <button type="submit" name="btn" class="btn waves-light waves-effect red darken-4" value="login" />Iniciar Sesión</button>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12 offset-s1">
                        <a href="registro.jsp">Si no tienes cuenta registrate aqui</a>
                    </div>
                </div>
            </div>  
        </form>

        <p class="${requestScope.tipo == 1? 'red': 'green'}-text">${requestScope.msg}</p>
    </div>
</div>

<%@include file="templates/footer.jsp" %>