var io=require('socket.io')(8000);
var jugadas = new Array(1);
var auxJugada;
function jugada(socketAnfitrion, idAnfitrion, socketInvitado, idInvitado){
	this.socketAnfitrion = socketAnfitrion;
	this.idAnfitrion = idAnfitrion;
	this.socketInvitado = socketInvitado;
	this.idInvitado = idInvitado;
}

io.on('connection', function(socket) {
	console.log('Se ha conectado: '+ socket);

	socket.on('peticionCreacionPartida', function(idJugador){
		nuevaJugada = new jugada(socket, idJugador, null, null);
		jugadas.push(nuevaJugada);
	});
	socket.on('peticionEnlacePartida', function(){
		socket.send.emit('jugadores', jugadas);
	});

	socket.on('emparejar',function(idAnfitrion, idInvitado){
		var socketAnfitrion;
		for(var i = 0; i < jugadas.leght; i++){
			auxJugada = jugadas[i];
			if(auxJugada.idAnfitrion == idAnfitrion){
				auxJugada.idInvitado = idInvitado;
				auxJugada.socketInvitado = socket;
				jugadas[i] = auxJugada;
				console.log('Se emparejo ' + idAnfitrion + 'con ' + idInvitado);
				socketAnfitrion = auxJugada.socketAnfitrion;
				socket.send.emit('respuestaEmparejamiento', "Te emparejaste exitosamente con: " + idAnfitrion);
				socketAnfitrion.send.emit('respuestaEmparejamiento', "Te emparejaste exitosamente con: " + idInvitado);
				break;
			}
		}
	});

	socket.on('realizarJugada', function(jugada, jugadorContrincante){
		var socketContrincante;
		for (var i = 0; i < jugadas.length; i++) {
			auxJugada = jugadas[i];
			if(jugadorContrincante.tipo == "Anfitrion"){
				if(auxJugada.idAnfitrion == jugadorContrincante.idJugador){
					socketContrincante = auxJugada.socketAnfitrion;
					break;
				}
			}else{
				if(auxJugada.idInvitado == jugadorContrincante.idJugador){
					socketContrincante = auxJugada.socketInvitado;
					break;
				}
			}

		}

		socketContrincante.send.emit('jugadaRealizada', jugada);
		//envio de tiempo de espera para jugada
	});

	socket.on('TerminaPartida', function(idJugador){
		for (var i = 0; jugadas.length; i++) {
			auxJugada = jugadas[i];
			if(auxJugada.idInvitado == idJugador || auxJugada.idAnfitrion == idAnfitrion){
				jugadas.splice(i, 1);
			}
		}

	});

});

