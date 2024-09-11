# SmartJob
Reto Técnico para SmartJob - BCI Chile

# Indicaciones
1. El script que está en la raíz es para tener una referencia, pues se está usando H2, una BD en memoria que se crea y se elimina cada vez que se ejecuta la aplicación
2. Importar el proyecto en IDE de preferencia, en mi caso utilizo Spring Tools Suite
	- Seleccionamos la opción "File" > "Import"
	- Escogemos la opción "Existing Maven Projects" > "Next"
	- Ubicamos la raíz del proyecto y click en "Finish"
3. Click derecho al proyecto y seleccionamos "Run as" > "Maven Clean"
4. Click derecho al proyecto y seleccionamos "Run as" > "Maven Install" para la instalación de dependencias del proyecto
Advertencia, la aplicación está corriendo en el puerto 8091, verificar que esté libre.
5. Ejecutar la aplicación seleccionando "Run as" > "Spring Boot App".
6. En la carpeta raíz del repositorio hay una colección de Postman, listas para probar la app.
7. Ejecutar el servicio "Logeo" que se encuentra en la colección de Postman, para autenticar.
7. El servicio solicitado en el reto técnico está ubicado en la colección Postman como "User - Crear"

# Marcos Bayona Rijalba