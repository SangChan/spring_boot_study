var curl;
(function () {

    curl.config({
        main: 'hello',
		paths: {
			sockjs: 'lib/sockjs/sockjs'
		},
        packages: {
            // Your application's packages
            hello: { location: 'hello' },
            // Third-party packages
            curl: { location: 'lib/curl/src/curl' },
            msgs: { location: 'lib/msgs', main: 'msgs' },
            stomp: { location: 'lib/stomp-websocket', main: 'dist/stomp' },
            when: { location: 'lib/when', main: 'when' }
        }
    });

}());
