$(document).ready(function () {
  $('.pagination a').click(function (e) {
    e.preventDefault();
    const page = $(this).attr('href');
    $.ajax({
      url: page,
      dataType: 'html',
      success: function (d) {
        const contenido = $(d).find("#articulos");
        $("#articulos").html(contenido.html());
      }
    });
  });
});