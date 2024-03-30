let webSocket;
let tiempoReconexion = 1000;

self.onmessage = (e) => {
  let form = e.data;
  webSocket.send(JSON.stringify(form));
};

const checkConnection = () => {
  if (!webSocket || webSocket.readyState === WebSocket.CLOSED) {
    connect();
  }
}

const connect = () => {
  webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/ws" );
  webSocket.onopen = () => console.log("Conectado - status " + this.readyState);
  webSocket.onclose = () => {
    console.log("Desconectado - status " + this.readyState);
    setTimeout(() => {
      connect();
    }, tiempoReconexion);
  }
}

connect();
setInterval(checkConnection, tiempoReconexion);