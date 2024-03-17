/*
Mini demo de un cliente REST con Node.js
Para ejecutar este archivo, es necesario tener instalado Node.js
Para instalar Node.js, descargar el instalador desde la página oficial:
https://nodejs.org/es/ y seguir las instrucciones de instalación.

Para instalar las dependencias, ejecutar el siguiente comando:
npm install

Para ejecutar este archivo, ejecutar el siguiente comando:
node rest
o el script que se haya definido en el package.json
npm run run

En este caso la demo de javalin se movió del puerto 7000 al 3001, tener en cuenta esto al ejecutar el servidor de javalin.
*/

import axios from 'axios';
import to from './to.js';
const url = 'http://localhost:3001/api/estudiante';

const getEstudiantes = async () => {
  const [error, response] = await to(axios.get(url));
  if (error) {
    console.error("error", error.data);
    return;
  }
  console.log("all estudiantes response:", response.data);
}

const postEstudiante = async (estudiante) => {
  const [error, response] = await to(axios.post(url, estudiante));
  if (error) {
    console.error("error", error.data);
    return;
  }
  console.log("created estudiante response:", response.data);
}

const getOneEstudiante = async (matricula) => {
  const [error, response] = await to(axios.get(`${url}/${matricula}`));
  if (error) {
    console.error("error", error.data);
    return;
  }
  console.log("one estudiante response:", response.data);
}

const deleteEstudiante = async (matricula) => {
  const [error, response] = await to(axios.delete(`${url}/${matricula}`));
  if (error) {
    console.error("error", error.data);
    return;
  }
  console.log("remove estudiante response:", response.data);
}

const putEstudiante = async (estudiante) => {
  const [error, response] = await to(axios.put(url, estudiante));
  if (error) {
    console.error("error", error.data);
    return;
  }
  console.log("modify estudiante response:", response.data);
}

console.log("Elige una opción:");
console.log("1. Mostrar todos los estudiantes");
console.log("2. Agregar un estudiante");
console.log("3. Mostrar un estudiante");
console.log("4. Eliminar un estudiante");
console.log("5. Modificar un estudiante");

const stdin = process.openStdin();
stdin.addListener("data", function(d) {
  const option = d.toString().trim();
  switch (option) {
    case "1":
      getEstudiantes();
      break;
    case "2":
      postEstudiante({matricula: "10141415", nombre: "Vladimir Curiel", carrera: "ICC"});
      break;
    case "3":
      getOneEstudiante("10141415");
      break;
    case "4":
      deleteEstudiante("10141415");
      break;
    case "5":
      putEstudiante({matricula: "10141415", nombre: "Vladimir Curiel Modified", carrera: "ICC"});
      break;
    default:
      console.log("Opción no válida");
  }
  console.clear();
});

