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

## Caputas de pantall
### Login con el usuario "user"
![Login](https://raw.githubusercontent.com/ismaelviss/hulk-store-app/master/app/src/main/resources/login.png)

### Catalogo de productos
![Products](https://raw.githubusercontent.com/ismaelviss/hulk-store-app/master/app/src/main/resources/catalogo_productos.png)

### Ticket de compra
![Ticket](https://raw.githubusercontent.com/ismaelviss/hulk-store-app/master/app/src/main/resources/ticket.png)

## tecnologías usadas
* Android con Kotlin
    * Dagger
    * MVVM
    * LiveData
    * Mockito
    * MockWebServer