let websocket;
let tiempoReconexion = 5000;
const chatId = window.location.pathname.split("/")[2];
const keyPressed = 13;

$(document).ready(() =>{
  console.log("Iniciando Jquery --> websocket.js");
  connect();

  $("#chat-button").click(()=> {
    connect();
  });

  $("#chat-input").keypress(event => {
    if (event.which === keyPressed) {
      enviarMensaje();
      event.preventDefault();
    }
  });
});

const recibirInformacionServidor = (mensaje) => {
  console.log("Recibiendo del servidor: " + mensaje.data);
  $("#chat-box").append('<article class="media w-50 ml-auto mt-4">' +
    '<body class="media-body">' +
    '<div class="bg-primary rounded px-2 py-2 mb-2">' +
    '<p class="text-white text-small">' + mensaje.data + '</p>' +
    '</div>' +
    '</body>' +
    '</article>');
  scrollToBottom();
}

const connect = () => {
  webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/mensajeServidor/" + chatId);

  webSocket.onmessage = (data) => { recibirInformacionServidor(data); }
  webSocket.onopen = () => console.log("Conectado - status " + this.readyState);
  webSocket.onclose = () => console.log("Desconectado - status " + this.readyState);
}

const enviarMensaje = () => {
  const jqueryChatInput = $("#chat-input");

  const mensaje = jqueryChatInput.val();
  webSocket.send(mensaje);
  jqueryChatInput.val("");

}

const verificarConexion = () => {
  if (!webSocket || webSocket.readyState === WebSocket.CLOSED) {
    const chatMessages = document.getElementById('chat-box');
    while (chatMessages.firstChild) {
      chatMessages.firstChild.remove();
    }
    connect();
  }
}

setInterval(verificarConexion, tiempoReconexion);

window.onbeforeunload = function() {
  if (webSocket) {
    webSocket.close();
  }
};

const scrollToBottom = () => {
  const chatBox = document.getElementById('chat-box');
  chatBox.scrollTop = chatBox.scrollHeight;
}
