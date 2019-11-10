<%-- 
    Document   : SesionUsuario
    Created on : Nov 10, 2019, 12:36:02 AM
    Author     : jela3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script type="text/javascript">
            $(document).ready(function () {
                setTimeout(function () {
                    $(".mensaje").fadeOut(3000);
                }, 3000);
            });
        </script>
        <p class="mensaje">
            <%
                if (request.getParameter("mens") != null) {
                    out.println(request.getParameter("mens"));
                }
            %>
        </p>
        <form action="..\Cliente" method="post">
            <h1>Ingrese sus datos</h1>
            <br><br><br>
            <h3>Ingresa tu correo Electronico</h3>
            <input type="text" name="corr_compara">            
            <h3>Ingresa tu contraseña</h3>
            <input type="text" name="contra_compara">
            <br><br><br>
            <input type="hidden" value="login" name="accion">
            <input type="hidden" value="pag" name="place">
            <input type="submit" value="Acceder">

        </form>
    </body>
</html>
