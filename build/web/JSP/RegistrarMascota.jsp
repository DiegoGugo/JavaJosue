<%-- 
    Document   : RegistrarMascota
    Created on : Nov 10, 2019, 2:59:28 AM
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
      <form action='..\Cliente' method=post enctype="multipart/form-data">
            <br><br><h3>Nombre</h3><input type=text name='nomp'><br>
            <h3>Nacimiento (Una fecha aproximada)</h3>
            <input type="date"  name="fecha">
            <h3>Genero</h3>
            <select name="generop">
                <option>Macho</option>
                <option>Hembra</option>
                <option>Tren</option>
            </select><br>
            <h3>talla</h3>
            <input type=text name='tallap'><br>
            <h3>Foto</h3>
            <input type="file" name="imagenp" accept="image/x-png,image/gif,image/jpeg"><br>
            <br>
            <input type="hidden" name="place" value="pag">
            <input type="hidden" name="accion" value="registrarM">
            <input type=submit value='Aceptar'>
        </form>
    </body>
</html>
