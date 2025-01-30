## Nombre del Proyecto
Challenge Android
Aplicación de compras que permite explorar diversas categorías de productos, agregarlos al carrito y completar una compra simulada.

# Características de la Aplicación
A continuación, se describen los puntos solicitados y se presenta la evidencia de que fueron debidamente abordados.

### Listado de productos 

✅ Muestra todos los productos obtenidos desde FakeStoreAPI.<br>
✅ El primer producto en la lista es el producto destacado, determinado por el mayor puntaje (rating.rate * rating.count).<br>
✅ Cada producto muestra su título y precio.<br>
✅ Botón "+" para agregar productos al carrito.<br><br>

<img src="https://github.com/mau-vargas/ChallengeAndroid/blob/main/img/Screenshot_20250130_133019.png" alt="Pantalla principal" width="300"/>


### Categorías de productos 
✅ Se obtienen desde la API y se muestran en un **Navigation Drawer**.  <br>
✅ Al seleccionar una categoría, la pantalla lista solo los productos de esa categoría.  <br> <br>
<img src="https://github.com/mau-vargas/ChallengeAndroid/blob/main/img/Screenshot_20250130_133052.png" alt="Pantalla principal" width="300"/>

###  Detalle del producto  
✅ Al seleccionar un producto, se muestra su **descripción**, **rating**, y opción para **agregarlo al carrito**.<br><br>
<img src="https://github.com/mau-vargas/ChallengeAndroid/blob/main/img/Screenshot_20250130_133200.png" alt="Pantalla principal" width="300"/>

### Carrito de compras  
✅ Se gestiona **localmente** en el dispositivo. <br> 
✅ Se pueden **agregar y quitar productos**.  <br>
✅ Muestra el **monto total** de la compra.  <br>
✅ El botón **"Purchase"** no tiene funcionalidad real, solo simula la acción.  <br> <br>

<img src="https://github.com/mau-vargas/ChallengeAndroid/blob/main/img/Screenshot_20250130_133130.png" alt="Pantalla principal" width="300"/>

## Descripción de los servicios integrados en la App

| **Método HTTP** | **Endpoint**                                      | **Descripción**                                                    | **Parámetros**             | **Respuesta Esperada**                           |
|-----------------|---------------------------------------------------|--------------------------------------------------------------------|----------------------------|--------------------------------------------------|
| `GET`           | `https://fakestoreapi.com/products`               | Obtiene la lista de todos los productos disponibles.               | Ninguno                    | `Response<List<ProductResponse>>`                |
| `GET`           | `https://fakestoreapi.com/products/{id}`          | Obtiene un producto específico por su ID.                          | `id` (parámetro de ruta)   | `Response<ProductResponse>`                      |
| `GET`           | `https://fakestoreapi.com/products/categories`    | Obtiene todas las categorías de productos disponibles.             | Ninguno                    | `Response<List<String>>`                         |
| `GET`           | `https://fakestoreapi.com/products/category/{category}` | Obtiene los productos de una categoría específica.                | `category` (parámetro de ruta) | `Response<List<ProductResponse>>`              |





## Principales Librerías

| Librería                         | Versión     | Propósito                                                                 |
|-----------------------------------|-------------|---------------------------------------------------------------------------|
| **gson**                          | 2.11.0      | Biblioteca para convertir objetos Java a JSON y viceversa.               |
| **hilt-android**                  | 2.54        | Inyección de dependencias en Android usando Dagger hilt.                      |
| **junit**                         | 4.13.2      | Framework de pruebas unitarias para Java.                                |
| **livedata** | 2.8.7     | LiveData con soporte para Kotlin, para observar datos de manera reactiva. |
| **navigation** | 2.8.5    | Extensiones Kotlin para la navegación de fragmentos en Android.           |
| **lottie**                        | 6.5.2       | Animaciones vectoriales basadas en JSON para interfaces de usuario.      |
| **picasso**                       | 2.8         | Biblioteca para cargar y mostrar imágenes de manera eficiente.           |
| **retrofit**                       | 2.11.0     | Cliente HTTP para interactuar con APIs REST.                             |


## Descripción de la arquitectura implementada
Para la distribución del proyecto, se utilizó Clean Architecture con el objetivo de dividir las responsabilidades de manera clara y mantener un código más modular y legible. Las capas implementadas son las siguientes:

**Repository**: Esta capa es responsable de proveer los datos utilizados en la aplicación, ya sea de fuentes remotas (por ejemplo, mediante solicitudes de red) o locales (por ejemplo, utilizando Room para almacenamiento local).

**Domain**: En esta capa se implementaron los casos de uso (Use Cases), que facilitan la conexión entre la capa de presentación y la capa de datos (Repository). Los casos de uso gestionan la lógica de negocio y devuelven los resultados a través de Flow, que permite la gestión eficiente de los datos asíncronos. Esta capa actúa como un intermediario entre la UI y los datos.

**Presentation**: La capa de presentación se encarga de las vistas, ya sean creadas con Jetpack Compose, Actividades o Fragmentos. Aquí también se encuentran los ViewModels, que ejecutan los casos de uso para obtener la información necesaria y gestionan el estado de la interfaz de usuario. Los ViewModels exponen los datos necesarios para que la UI pueda reaccionar adecuadamente a los cambios.

### Patrón de Presentación Utilizado: MVVM
El proyecto sigue el patrón MVVM (Model-View-ViewModel) para gestionar la arquitectura de la aplicación.

La Vista (Fragment/Activity) observa los cambios en los datos a través de un LiveData, lo que permite actualizar la interfaz de usuario de forma reactiva.
El ViewModel actúa como intermediario entre la Vista y la capa de dominio. Se encarga de ejecutar los casos de uso necesarios para obtener o procesar la información.
Una vez que el ViewModel obtiene la respuesta, actualiza el LiveData, lo que notifica automáticamente a la Vista para reflejar los nuevos datos en la UI.



### Persistencia de Datos: SharedPreferences
Para la persistencia de datos en el carrito de compras, se decidió utilizar SharedPreferences, ya que es una solución rápida y eficiente para almacenar información de uso frecuente.
Esta elección se debe a que los datos del carrito son dinámicos y se modifican constantemente. Además, como el carrito se sincroniza entre múltiples plataformas (Android, iOS y Web), la información almacenada localmente no requiere una protección robusta ni un mantenimiento avanzado como el que ofrece SQLite con Room. Si el usuario desinstala la aplicación o borra los datos, estos deben recuperarse desde el backend.
En una implementación real, se consideraría el uso de EncryptedSharedPreferences para mejorar la seguridad de los datos almacenados.
La solución implementada guarda el carrito en formato JSON, lo que facilita su manipulación y serialización. Sin embargo, este enfoque tiene ciertas limitaciones, especialmente cuando se requiere reconstruir objetos complejos con dependencias entre clases. Para abordar este desafío, se implementó un mapper, permitiendo almacenar únicamente la información esencial y reconstruir los objetos de manera eficiente para la interacción del usuario. 

## Mejoras pendientes

Aunque se implementaron la mayoría de las funcionalidades solicitadas, hay ciertos aspectos que podrían mejorarse o añadirse para optimizar la aplicación. Estas son algunas de las áreas que planeaba abordar, pero por falta de tiempo no pude completarlas:

- **Respuestas con error de los servicios** No se abordo este tema, pero es fundamental para una aplicación estar preparada para poder reintentar los llamados a los servicios, o transparentar con el cliente alguna intermitencia.
- **Agregar Badge al carrito** No fue posible agregar la funcionalidad del Badge ya que me encontre con algunos problemas al querer implementarlo en el actionbar, se realizaron otras pruebas con otros elementos como botones y funcionó correctamente, dada la limitación de tiempo no segui insistendo con ese desarrollo.
- **Optimización del codigo** Si bien el desarrollo es acotado a pocas funcionalidad, repetí un poco de codigo que me hubiera gustado mejorar para tener una aplicación mas entendible y mantenible ya que al repetir codigo estamos dejando varios puntos que tocaría mejorar en el caso de querer cambiar ese comportamiento.

Estas mejoras serían importantes para la escalabilidad y la experiencia del usuario, pero debido a las limitaciones de tiempo no fueron implementadas en esta versión del proyecto.
 
## Pruebas

## Autor
**Mauricio Vargas Osorio**
**Desarrollador Android** 



