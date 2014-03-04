<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Stock Ticker</title>
</head>
<body>
  <h1>Stock Ticker</h1>

  Time now is: <span id="time"></span>

  <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
  <script src="/stockticker/resources/stomp.js"></script>
  <script src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
  <script>
    //Create stomp client over sockJS protocol (see Note 1)
    var socket = new SockJS("/stockticker/ws");
    var stompClient = Stomp.over(socket);

    // callback function to be called when stomp client is connected to server (see Note 2)
    var connectCallback = function() {
      console.log('websocket connected');
      stompClient.subscribe('/topic/time', function(frame){
        var msg = JSON.parse(frame.body);
        console.log(msg);
        $('#time').html(msg);
      });
    }; 

    // callback function to be called when stomp client could not connect to server (see Note 3)
    var errorCallback = function(error) {
         alert(error.headers.message);
    };

    // Connect as guest (Note 4)
    stompClient.connect("guest", "guest", connectCallback, errorCallback);
    
  </script>
</body>
</html>
