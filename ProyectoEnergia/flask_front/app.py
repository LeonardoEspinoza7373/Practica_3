from flask import Flask, render_template, request, redirect, url_for
import datetime
import json
import requests

app = Flask(__name__)

# Archivos para almacenar proyectos y transacciones
PROYECTOS_FILE = '/home/leonardo/Escritorio/ProyectoEnergia/java_backend/media/Proyectos.json'
TRANSACCIONES_FILE = '/home/leonardo/Escritorio/ProyectoEnergia/java_backend/media/Transacciones.json'

# Función para cargar proyectos desde el archivo JSON
def cargar_proyectos():
    try:
        with open(PROYECTOS_FILE, 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return []

# Función para guardar proyectos en el archivo JSON
def guardar_proyectos(proyectos):
    with open(PROYECTOS_FILE, 'w') as f:
        json.dump(proyectos, f, indent=4)

# Función para cargar transacciones desde el archivo JSON
def cargar_transacciones():
    try:
        with open(TRANSACCIONES_FILE, 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return []

# Función para guardar transacciones en el archivo JSON
def guardar_transacciones(transacciones):
    with open(TRANSACCIONES_FILE, 'w') as f:
        json.dump(transacciones, f, indent=4)

# Ruta para la página de inicio
@app.route('/')
def index():
    return redirect(url_for('mostrar_proyectos'))

# Ruta para mostrar todos los proyectos
@app.route('/mostrar_proyectos')
def mostrar_proyectos():
    proyectos = cargar_proyectos()
    return render_template('mostrar_proyectos.html', proyectos=proyectos)

# Ruta para crear o actualizar un proyecto
@app.route('/crear_proyecto', methods=['GET', 'POST'])
def crear_proyecto():
    if request.method == 'POST':
        proyecto = {
            'id': len(cargar_proyectos()) + 1,
            'nombre': request.form['nombre'],
            'descripcion': request.form['descripcion'],
            'inversion': float(request.form['inversion']),
            'tiempoVida': int(request.form['tiempoVida']),
            'fechaInicio': request.form['fechaInicio'],
            'fechaFin': request.form['fechaFin'],
            'inversionistas': request.form['inversionistas'].split(','),
            'electricidadGeneradaDiaria': float(request.form['electricidadGeneradaDiaria'])
        }
        proyectos = cargar_proyectos()
        proyectos.append(proyecto)
        guardar_proyectos(proyectos)

        # Registrar la transacción
        transaccion = {
            'id': len(cargar_transacciones()) + 1,
            'tipoOperacion': 'Crear Proyecto',
            'nombreProyecto': proyecto['nombre'],
            'fecha': datetime.datetime.now().strftime('%Y-%m-%d'),
            'descripcion': f'Se creó el proyecto {proyecto["nombre"]}.'
        }
        transacciones = cargar_transacciones()
        transacciones.append(transaccion)
        guardar_transacciones(transacciones)

        return redirect(url_for('mostrar_proyectos'))

    return render_template('proyecto_form.html', title='Crear Proyecto', proyecto={})

# Ruta para mostrar las transacciones
@app.route('/mostrar_transacciones')
def mostrar_transacciones():
    transacciones = cargar_transacciones()
    return render_template('mostrar_transacciones.html', transacciones=transacciones)

# Ruta para crear una transacción
@app.route('/crear_transaccion', methods=['GET', 'POST'])
def crear_transaccion():
    if request.method == 'POST':
        transaccion = {
            'id': len(cargar_transacciones()) + 1,
            'tipoOperacion': request.form['tipoOperacion'],
            'nombreProyecto': request.form['nombreProyecto'],
            'fecha': datetime.datetime.now().strftime('%Y-%m-%d'),
            'descripcion': request.form['descripcion']
        }
        transacciones = cargar_transacciones()
        transacciones.append(transaccion)
        guardar_transacciones(transacciones)
        return redirect(url_for('mostrar_transacciones'))

    proyectos = cargar_proyectos()  # Para mostrar proyectos en el formulario de transacción
    return render_template('transaccion_form.html', title='Crear Transacción', proyectos=proyectos)

# Ruta para modificar un proyecto
@app.route('/modificar_proyecto/<int:id>', methods=['GET', 'POST'])
def modificar_proyecto(id):
    proyectos = cargar_proyectos()
    proyecto = next((p for p in proyectos if p['id'] == id), None)

    if request.method == 'POST':
        if proyecto:
            # Actualizamos los datos del proyecto
            proyecto['nombre'] = request.form['nombre']
            proyecto['descripcion'] = request.form['descripcion']
            proyecto['inversion'] = float(request.form['inversion'])
            proyecto['tiempoVida'] = int(request.form['tiempoVida'])
            proyecto['fechaInicio'] = request.form['fechaInicio']
            proyecto['fechaFin'] = request.form['fechaFin']
            proyecto['inversionistas'] = request.form['inversionistas'].split(',')
            proyecto['electricidadGeneradaDiaria'] = float(request.form['electricidadGeneradaDiaria'])
            guardar_proyectos(proyectos)

            # Registrar la transacción
            transaccion = {
                'id': len(cargar_transacciones()) + 1,
                'tipoOperacion': 'Modificar Proyecto',
                'nombreProyecto': proyecto['nombre'],
                'fecha': datetime.datetime.now().strftime('%Y-%m-%d'),
                'descripcion': f'Se modificó el proyecto {proyecto["nombre"]}.'
            }
            transacciones = cargar_transacciones()
            transacciones.append(transaccion)
            guardar_transacciones(transacciones)

            return redirect(url_for('mostrar_proyectos'))

    return render_template('proyecto_form.html', title='Modificar Proyecto', proyecto=proyecto)

# Ruta para eliminar un proyecto
@app.route('/eliminar_proyecto/<int:id>', methods=['GET'])
def eliminar_proyecto(id):
    proyectos = cargar_proyectos()
    proyecto = next((p for p in proyectos if p['id'] == id), None)
    proyectos = [p for p in proyectos if p['id'] != id]
    guardar_proyectos(proyectos)

    # Registrar la transacción
    transaccion = {
        'id': len(cargar_transacciones()) + 1,
        'tipoOperacion': 'Eliminar Proyecto',
        'nombreProyecto': proyecto['nombre'] if proyecto else f'ID {id}',
        'fecha': datetime.datetime.now().strftime('%Y-%m-%d'),
        'descripcion': f'Se eliminó el proyecto con ID {id}.'
    }
    transacciones = cargar_transacciones()
    transacciones.append(transaccion)
    guardar_transacciones(transacciones)

    return redirect(url_for('mostrar_proyectos'))

# Ruta para eliminar una transacción
@app.route('/eliminar_transaccion/<int:id>', methods=['GET'])
def eliminar_transaccion(id):
    transacciones = cargar_transacciones()
    transacciones = [t for t in transacciones if t['id'] != id]
    guardar_transacciones(transacciones)
    return redirect(url_for('mostrar_transacciones'))

# Ruta para ordenar proyectos
@app.route('/ordenar', methods=['POST'])
def ordenar():
    metodo = request.form['metodo']  # "quick", "merge", "shell"
    criterio = request.form['criterio']  # "id" o "nombre"
    orden = request.form['orden']  # "asc" o "desc"

    # Llamada al backend de Java para ordenar los proyectos
    url = f"http://localhost:8080/proyecto/sort/{metodo}/{criterio}/{orden}"
    response = requests.get(url)

    if response.status_code == 200:
        proyectos = response.json()  # Datos JSON devueltos desde el backend
        return render_template('mostrar_proyectos.html', proyectos=proyectos)
    else:
        return "Error al ordenar los proyectos", 500

@app.route('/buscar', methods=['POST'])
def buscar():
    metodo_busqueda = request.form['metodo_busqueda']  # "secuencial" o "binaria"
    id_buscar = request.form['id_buscar']
    nombre_buscar = request.form['nombre_buscar']
    
    proyectos = cargar_proyectos()
    resultado = None

    # Verificamos si se debe buscar por ID o por nombre
    if metodo_busqueda == 'secuencial':
        if id_buscar:
            # Búsqueda Secuencial por ID
            resultado = next((p for p in proyectos if p['id'] == int(id_buscar)), None)
        elif nombre_buscar:
            # Búsqueda Secuencial por nombre
            resultado = next((p for p in proyectos if p['nombre'].lower() == nombre_buscar.lower()), None)
    
    elif metodo_busqueda == 'binaria':
        if id_buscar:
            # Búsqueda Binaria por ID
            proyectos.sort(key=lambda p: p['id'])  # Aseguramos que los proyectos estén ordenados por ID
            low, high = 0, len(proyectos) - 1
            while low <= high:
                mid = (low + high) // 2
                if proyectos[mid]['id'] == int(id_buscar):
                    resultado = proyectos[mid]
                    break
                elif proyectos[mid]['id'] < int(id_buscar):
                    low = mid + 1
                else:
                    high = mid - 1
        elif nombre_buscar:
            # Búsqueda Binaria por nombre
            proyectos.sort(key=lambda p: p['nombre'].lower())  # Ordenamos los proyectos por nombre
            low, high = 0, len(proyectos) - 1
            while low <= high:
                mid = (low + high) // 2
                if proyectos[mid]['nombre'].lower() == nombre_buscar.lower():
                    resultado = proyectos[mid]
                    break
                elif proyectos[mid]['nombre'].lower() < nombre_buscar.lower():
                    low = mid + 1
                else:
                    high = mid - 1

    return render_template('mostrar_proyectos.html', proyectos=proyectos, resultado=resultado)


if __name__ == '__main__':
    app.run(debug=True)
