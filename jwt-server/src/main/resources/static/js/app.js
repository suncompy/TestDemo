var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}
var headers = {
    //10086
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJVU0VSX1JPTEUiOiJbXCJBRE1JTlwiLFwiQURNSU4xXCJdIiwiZXhwIjoxNTMyNDM5NjgxLCJ1c2VybmFtZSI6IjEwMDg2IiwidGltZXN0YW1wIjoxNTMyNDM2MDgxNzc2fQ.H4O6aMR2Ly7T9BT__IDEgp3lmrmomEY8wxivKfycZuwDzZgWHZA1sBcGcLGUspEZuU_TmgFltF01XBE4XtHVjg'
    //10087
    //'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJVU0VSX1JPTEUiOiJbXCJBRE1JTlwiLFwiQURNSU4xXCJdIiwiZXhwIjoxNTMyNDM5OTAxLCJ1c2VybmFtZSI6IjEwMDg3IiwidGltZXN0YW1wIjoxNTMyNDM2MzAxMzUxfQ.-ISy6sIFRa1xc0Hz5PW1CxaamixaCvh1vGfczek9xTvZefeY0LpUxAsMGqwSLXXxhvjkBz1f5TZ4dMr3oxVpTA'

};
function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect(headers, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).name);
        });

        /*stompClient.subscribe('/topic/callback', function (greeting) {
            showGreeting(greeting.body);
        });*/

        stompClient.subscribe('/topic/notifications', function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/chat", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});