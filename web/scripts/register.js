// Verifica que las contraseñas coincidan
$('#iPassword, #iPasswordConfirm').on('keyup', function () {
  if ($('#iPassword').val() == $('#iPasswordConfirm').val()) {
    $('#passDontMatch').html('Las contraseñas coinciden').css('color', 'green');
  } else {
    $('#passDontMatch').html('Las contraseñas no coinciden').css('color', 'red');
    $('#iPassword', '#iPasswordConfirm').removeClass("is-valid");
    $('#iPassword', '#iPasswordConfirm').addClass("is-invalid");
  }
});

// Previene la validación del navegador para usar la de bootstrap
(function() {
  window.addEventListener('load', function() {
    var forms = document.getElementsByClassName('needs-validation');

    // Iterar para cada form que requiere validación
    Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (btnNeedsValidation == true) {
          var dontSubmit = false;

          // Formulario inválido
          if (form.checkValidity() === false) {
            dontSubmit = true;
            console.log("invalid form");
          }
          // Contraseñas no coinciden
          if ($('#iPassword').val() != $('#iPasswordConfirm').val()) {

            dontSubmit = true;
            console.log("passwords don't match");

          } else if ($('#iPasswordConfirm').val()) {
            $('#iPasswordConfirm').removeClass("is-invalid");
            $('#iPasswordConfirm').addClass("is-valid");
            console.log("passwords match - is not empty")
          }

          if (dontSubmit == true) {
            event.preventDefault();
            event.stopPropagation();
          }

          // Habilitar estilos de validación de bootstrap
          form.classList.add('was-validated');
        }
      }, false);
    });
  }, false);
})();
