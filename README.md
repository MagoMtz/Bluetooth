Pantalla 1 | Pantalla 2
-----------|-----------
<img src="https://raw.githubusercontent.com/MagoMtz/Bluetooth/ff3bbfcfe4990052a34182f6beb68e1ed029fc4d/imagesforreadme/pantalla1.png" width="300" alt="pantalla 1"/>|<img src="https://raw.githubusercontent.com/MagoMtz/Bluetooth/ff3bbfcfe4990052a34182f6beb68e1ed029fc4d/imagesforreadme/pantalla2.png" width="300" alt="pantalla 2"/>

Botón | Acción
------|------
<img src="https://raw.githubusercontent.com/MagoMtz/Bluetooth/ff3bbfcfe4990052a34182f6beb68e1ed029fc4d/imagesforreadme/guardardispositivo.png" width="150" alt="guardar dispositivo"/>|Guardar dispositivo
<img src="https://raw.githubusercontent.com/MagoMtz/Bluetooth/ff3bbfcfe4990052a34182f6beb68e1ed029fc4d/imagesforreadme/scannear.png" width="150" alt="actualizar lista"/>|Actualizar lista
<img src="https://raw.githubusercontent.com/MagoMtz/Bluetooth/ff3bbfcfe4990052a34182f6beb68e1ed029fc4d/imagesforreadme/btnpantalla2.png" width="150" alt="botón segunda pantalla"/>|Ir a segunda pantalla
<img src="https://raw.githubusercontent.com/MagoMtz/Bluetooth/ff3bbfcfe4990052a34182f6beb68e1ed029fc4d/imagesforreadme/reordenar.png" width="150" alt="reordenar lista"/>|Reordenar lista por fecha de creación

# Tareas realizadas
1. Se creó la actividad NearDevicesActivity, la cual tiene un Recycler View para mostrar los dispositivos bluetooth cercanos.
2. Se creó el menú para NearDevicesActivity. Se definieron dos acciones: refrescar lista y ver los dispositivos guardados.
3. Se creó la segunda actividad (CloudDevicesActivity). También tiene un Recycler View para mostrar los dispositivos guardados en el servidor.
4. Se creó el menú para CloudDevicesActivity. Se definió solo una acción. La de reordenar los dispositivos por fecha de creación.
4. Se creó el adaptador DevicesAdapter, el cual se utiliza en las dos actividades.
5. Se crearon: vista, interactuadores, presentadores de ambas actividades.
6. Se definió la lógica de negocio de la segunda actividad.
7. Se creó la interfaz para retrofit, su cliente y la instancia única de acceso.
8. Se consume el WS [https://grin-bluetooth-api.herokuapp.com/devices/](devices) para mostrar los dispositivos guardados en el servidor.
9. Se implementa la lógica para poder ordenar la lista de a cuerdo a la fecha de creación.
10. Se desarrolla la lógica de negocio para detectar dispositivos bluetooth cercanos.
11. Se implementan las clases para utilizar el Bluetooth Adapter.
12. Se crea el Broadcast Receiver para obtener la información de los dispositivos bluetooth cercanos.
13. Se agregan los dispositivos encontrados a la lista de dispositivos disponibles.
14. Se implementa la petición de permisos en tiempo de ejecución.
15. Se guardan los dispositivos con volley. Al utilizar retrofit, automáticamente se agrega la cabecera "Content-Length" que indica el tamaño del body enviado. Esa cabecera hacía que el servidor regresara un error.
16. Se implementa una base de datos con Room para almacenar los dispositivos que se intentan guardar cuando no hay conexión a internet.
17. Se implementa que en cada evento `onResume()` de la actividad principal, se intente guardar cada uno de los dispositivos que hay en la base de datos. Cuando el dispositivo se guarda exitosamente, se elimina su registro de la base de datos.
