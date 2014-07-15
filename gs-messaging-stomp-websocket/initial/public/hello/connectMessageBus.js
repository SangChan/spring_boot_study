define(function(require) {

	var msgs = require('msgs');
	var SockJS = require('sockjs');

	require('msgs/adapters/webSocket');
	require('msgs/channels/bridges/stomp');

	function connectMessageBus(messageServiceUrl, onConnect) {
		var bus = msgs.bus();
		var socket = new SockJS(messageServiceUrl);

		socket.addEventListener('open', function () {
			var bridge = bus.stompWebSocketBridge('remote', socket, { ack: 'client' });

			bridge.controlBus.on('connected', onConnect);
			bridge.controlBus.on('error', function (error) {
				console.error('STOMP protocol error ' + error);
			});
		});

		return bus;
	}

	return connectMessageBus;
});
