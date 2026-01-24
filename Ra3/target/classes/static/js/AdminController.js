var saludoUsuario = document.getElementById("saludoUsuario");
var datosMostrar = document.getElementById("datosMostrar");
var btnSalir = document.getElementById("btnSalir");
var btnUsuarios = document.getElementById("btnUsuarios");
var btnDepartamentos = document.getElementById("btnDepartamentos");

if (btnSalir) {
    btnSalir.addEventListener("click", () => { window.location.href = 'killSession'; });
}
if (btnUsuarios) {
    btnUsuarios.addEventListener("click", () => { window.location.href = '/control/usuarios'; });
}
if (btnDepartamentos) {
    btnDepartamentos.addEventListener("click", () => { window.location.href = '/departamentos'; });
}

fetch('/admin')
    .then((response) => {
        if (response.ok) return response.json();
        throw new Error(`Error ${response.status}: ${response.statusText}`);
    })
    .then((data) => {
        const fecha = data.fechaCreacion ? new Date(data.fechaCreacion).toLocaleDateString() : '-';
        var tablaHTML = `
      <table class="tablaDatos">
        <thead>
          <tr>
            <th>ID Usuario</th>
            <th>Username</th>
            <th>Email</th>
            <th>Rol</th>
            <th>Fecha Creación</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>${data.id}</td>
            <td>${data.username}</td>
            <td>${data.email ?? '-'}</td>
            <td>${data.rol}</td>
            <td>${fecha}</td>
          </tr>
        </tbody>
      </table>
    `;
        if (saludoUsuario) saludoUsuario.innerHTML = `Bienvenido de nuevo ${data.username}`;
        if (datosMostrar) datosMostrar.innerHTML = tablaHTML;
    })
    .catch((error) => console.error("Error al cargar datos del admin:", error));