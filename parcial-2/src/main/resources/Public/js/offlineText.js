Offline.on('down', function () {
  let offlineMessage = document.querySelector('.offline-ui-content');
  offlineMessage.textContent = 'Your device lost its internet connection';
});

Offline.on('reconnect:connecting', function () {
  let offlineMessage = document.querySelector('.offline-ui-content');
  offlineMessage.textContent = 'Attempting to reconnect...';
});


Offline.on('up', function () {
  let offlineMessage = document.querySelector('.offline-ui-content');
  offlineMessage.textContent = 'Your device is connected to the internet'; // Limpiar el texto cuando vuelva en línea si es necesario
});

document.addEventListener('DOMContentLoaded', function () {
  let retryButton = document.querySelector('.offline-ui-retry');
  if (retryButton) {
    retryButton.textContent = 'Retry';
  }
});