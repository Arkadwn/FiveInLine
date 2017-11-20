var io=require('socket.io')(8000);
var jugadas = new Array(0);
var jugadasId = new Array(0);
var auxJugada;
function jugada(socketAnfitrion, idAnfitrion, socketInvitado, idInvitado, configuracion){
	this.socketAnfitrion = socketAnfitrion;
	this.idAnfitrion = idAnfitrion;
	this.socketInvitado = socketInvitado;
	this.idInvitado = idInvitado;
	this.configuracion = configuracion;
}

function jugadaId(idAnfitrion){
	this.idAnfitrion = idAnfitrion;
}

io.on('connection', function(socket) {
	console.log('Se ha conectado: '+ socket);

	socket.on('peticionCreacionPartida', function(idJugador, configuracion){
		nuevaPartida = new jugada(socket, idJugador, null, null, configuracion);
		nuevaPartidaId = new jugadaId(idJugador);
		jugadas.push(nuevaPartida);
		jugadas.push(nuevaPartidaId);
	});//Anfitrion

	socket.on('peticionEnlacePartida', function(){
		socket.emit('jugadores', jugadasId);
	});//Invitado

	socket.on('emparejar',function(idAnfitrion, idInvitado){
		var socketAnfitrion;
		for(var i = 0; i < jugadas.leght; i++){
			auxJugada = jugadas[i];
			if(auxJugada.idAnfitrion == idAnfitrion){
				auxJugada.idInvitado = idInvitado;
				auxJugada.socketInvitado = socket;
				jugadas[i] = auxJugada;
				jugadasId.splice(i, 1);
				console.log('Se emparejo ' + idAnfitrion + 'con ' + idInvitado);
				socketAnfitrion = auxJugada.socketAnfitrion;
				socket.emit('respuestaEmparejamiento', idAnfitrion, configuracion);
				socketAnfitrion.emit('respuestaEmparejamiento', idInvitado);
				break;
			}
		}
	});//Invitado

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
				jugadasId.splice(i, 1);
			}
		}

	});

});

