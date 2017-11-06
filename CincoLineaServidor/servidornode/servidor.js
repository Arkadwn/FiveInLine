var io=require('socket.io')(8000);



io.on('connection', function(socket, mensaje) {
	console.log('Se ha conectado: '+ mensaje);
	socket.on('peticionCreacionPartida', function(ip){
		//socket.broadcast.emit('saludoCliente', mensaje);
		//codigo para guardar ipAnfritiones
	});
	socket.on('peticionEnlacePartida',function(ip){
		socket.send.emit('jugadores', jugadores);
	});

	socket.on('emparejar',function(ipAnfitrion,ipInvitdo){
		//codigo para emparejar
	});

	socket.on('jugada',function(ip, jugada){
		//revisar conexion

		socket.broadcast.emit(jugada);
		//envio de tiempo de espera para jugada
	});

	socket.on('disconnect',function{
		io.emit('user disconnect');
	});




});

