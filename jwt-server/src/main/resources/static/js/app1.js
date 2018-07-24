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
    //'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJVU0VSX1JPTEUiOiJbXCJBRE1JTlwiLFwiQURNSU4xXCJdIiwiZXhwIjoxNTMyNDQ2NzQ3LCJ1c2VybmFtZSI6IjEwMDg2IiwidGltZXN0YW1wIjoxNTMyNDQzMTQ3ODYxfQ.71hO67nglLc9DUrj98vj-iJXQz-CnBdOyzGP7U7RND1qgXkhMukVliySjSKE7fPlStU7gLk1LCtPMB35fKc-xA'
    //10087
    'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJVU0VSX1JPTEUiOiJbXCJBRE1JTlwiLFwiQURNSU4xXCJdIiwiZXhwIjoxNTMyNDQ4MTc3LCJ1c2VybmFtZSI6IjEwMDg3IiwidGltZXN0YW1wIjoxNTMyNDQ0NTc3NTIxfQ.oNQ1x_EDHNqyzEBrYzodbFccvnAHWI_c0YMky6JCxJ71beZG8vNo8rfqiyZ3k39T__JrC6AehqeniLHYeefrIQ'

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

        /**  订阅了/user/topic/notifications 发送的消息,这里雨在控制器的 convertAndSendToUser 定义的地址保持一致, 
         *  这里多用了一个/user,并且这个user 是必须的,使用user 才会发送消息到指定的用户。 
         *  */
        stompClient.subscribe('/user/topic/notifications', function (greeting) {
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