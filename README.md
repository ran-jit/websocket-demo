# WebSocket Demo

This is a basic example of WebSocket pushes the server’s time to all connected clients.
<br/><br/>

## Server

The endpoint [Service](https://github.com/ran-jit/websocket-demo/blob/master/src/main/java/websocket/demo/Service.java) ("/ data") schedules the task and publishes the server time to the connected clients. The timer is implemented as lazy. So the timer is scheduled when the first client connects to the endpoint. After that, the message is sent by async to all the clients connected to every minute interval.
<br/><br/>

## Client

The [Web client](https://github.com/ran-jit/websocket-demo/blob/master/src/main/resources/index.html) is pretty simple. On the page load, the JavaScript code connects to the WebSocket endpoint on the server. The callback onMessage() is invoked when the message (server's time) arrives. In this callback, the current time is updated in UI. Here there is no page refresh, no heavy HTTP headers and, no AJAX long polling hacks either.

## How to test?
1. Run [Driver.java](https://github.com/ran-jit/websocket-demo/blob/master/src/main/java/websocket/demo/Driver.java).
2. Enter http://localhost:8080 on your browser.
