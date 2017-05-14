Estructura del JSON

{
  "Tipo": 0,
  "Origen": "Usuario1",
  "Destino": "Usuario2",
  "Datos": "Mensaje",
  "Extra": "Extras"
}

Tipo: tipo de mensaje que se esta enviando. Puede ser
			0- Broadcast
			1- Acknowledge
			2- Mensaje directo a usuarios especificados 
			3- Solicitar usuarios conectados al servidor
			4- Bloquear mensajes con usuario
			5- Desbloquear mensajes con usuario
			6- Funciones de red social
			7- Para mostrar el clima
			8- Salir o entrar al servidor
			9- Enviar archivo
			10-Recibir archivo
			
Origen: nombre del usuario que envia el json
Destino: nombres de los usuarios que recibiran el mensaje. El mensaje solo utiliza este campo si es de tipo 4
Datos: mensaje encapsulado en el json

	Comandos para el chat	

		 "/p":	// Mensaje privado
		 "/c": 	// Usuarios conectados
		 "/b": 	// Bloquear usuario
		 "/d": 	// Desbloquar usuario
		 "/t": 	// Funciones de red social
		 "/w": 	// Mostrar el clima
		 "/e": 	// Salirse por comando
		 "/f": 	// Enviar archivo