# hulk-store-app

Se implemento la siguiente:
* login con JWT - solo esta disponible el usuario "user" con la contraseña "user"
* consulta de productos disponibles
* compra de productos seleccionados

## instrucciones
Para levantar el proyecto se necesitar setear la url del proyecto REST FULL en el archivo OkHttpClientConfig ubicado en el package configurations
modificar la variable URL
Ejemplo:
const val URL = "http://192.168.2.101:8080/v1/"


## tecnologías usadas
* Android con Kotlin
    * Dagger
    * MVVM
    * LiveData
    * Mockito
    * MockWebServer