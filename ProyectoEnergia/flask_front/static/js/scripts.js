// Activar tooltips
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});

document.addEventListener("DOMContentLoaded", () => {
    const deleteButtons = document.querySelectorAll(".btn-danger");
    deleteButtons.forEach(button => {
        button.addEventListener("click", event => {
            const confirmed = confirm("¿Estás seguro de que deseas eliminar este proyecto?");
            if (!confirmed) {
                event.preventDefault();
            }
        });
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const tableRows = document.querySelectorAll("tbody tr");
    tableRows.forEach(row => {
        row.addEventListener("mouseover", () => {
            row.style.backgroundColor = "#e0f7fa";
        });
        row.addEventListener("mouseout", () => {
            row.style.backgroundColor = "";
        });
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const themeToggle = document.querySelector("#theme-toggle");
    themeToggle.addEventListener("click", () => {
        document.body.classList.toggle("dark-theme");
    });
});

function showToast(message) {
    const toastContainer = document.getElementById("toast-container");
    const toast = document.createElement("div");
    toast.className = "toast show align-items-center text-bg-success border-0";
    toast.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">${message}</div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>`;
    toastContainer.appendChild(toast);
    setTimeout(() => toast.remove(), 3000);
}

// Ejemplo de uso
showToast("Proyecto creado exitosamente");
