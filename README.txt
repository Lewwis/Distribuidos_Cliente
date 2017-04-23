Estructura del JSON

{
  "Tipo": 0,
  "Origen": "Usuario1",
  "Destino": [
    "Usuario2",
    "Usuario5"
  ],
  "Datos": "Mensaje"
}

Tipo: tipo de mensaje que se esta enviando. Puede ser
			0- Broadcast
			1- Confirmacion de recibido
			2- Mensaje directo a usuarios especificados 
			3- Solicitar usuarios conectados al servidor
			4- Solicitud de salida del servidor
			
Origen: nombre del usuario que envia el json

Destino: nombres de los usuarios que recibiran el mensaje. El mensaje solo utiliza este campo si es de tipo 4

Datos: mensaje encapsulado en el json