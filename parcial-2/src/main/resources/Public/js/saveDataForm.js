$(document).ready(() => {
  const db = new Dexie('myDb');
  db.version(1).stores({
    form: '++id, name, educationLevel, sector, username, latitude, longitude'
  });
  console.log(db);

  let editId = -1;

  console.log('Iniciando Jquery --> saveDataForm.js');

  $(document).on('submit', '#form', (e) => {
    console.log('Form submitted');
    e.preventDefault();
    const form = $(e.target).serializeArray();
    console.log(form);
    $(document).trigger('reset');

    console.log(form);
    if (form[4].name === 'idHidden') editId =  parseInt(form[4].value);


    if(editId !== -1) {
      console.log(editId)
      console.log(db.form)
      db.form.update(editId, {
        name: form[0].value,
        educationLevel: form[2].value,
        sector: form[1].value,
        username: form[3].value,
      }).then(updated => {
        if (updated) {
          console.log('Update successful');
        } else {
          console.log('Update failed: no record found with the provided id');
        }
      }).catch(error => {
        console.error('Update failed with error:', error);
      });
      editId = -1;
      alert('Successfully updated!');
      return;
    }


    getPosition().then((position) => {
      console.log('Position: ', position);
      db.form.add({
        name: form[0].value,
        educationLevel: form[1].value,
        sector: form[2].value,
        username: localStorage.getItem('username'),
        latitude: position[0],
        longitude: position[1],
      });
    }).then(() => {
      alert('Successfully saved!')
    });
  });
});



const getPosition = () =>{
  return new Promise((resolve, reject) => {
    console.log('Getting position...');
    if(!navigator.geolocation) {
      reject('Geolocation is not supported by your browser.');
    }
    navigator.geolocation.getCurrentPosition(
      (position) => {
        console.log('Position: ', position);
        const {latitude, longitude} = position.coords;
        resolve([latitude, longitude]);
      },
      (error) => {
        if (error.code !== error.PERMISSION_DENIED) {
          reject('Error occurred while getting your location. Please try again');
          return [];
        }
        reject('You denied the request to get your location. Please try again.');
      }
    );
  });
}