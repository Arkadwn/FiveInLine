var io=require('socket.io')(8000);

io.on('connection', function(socket, mensaje) {
	console.log('Se ha conectado: '+ mensaje);
	socket.on('saludoServidor', function(mensaje){
		socket.broadcast.emit('saludoCliente', mensaje);
	});
});

