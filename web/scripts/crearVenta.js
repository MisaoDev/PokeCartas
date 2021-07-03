$(document).ready(function() {

  $('#iProducto').on('change', function() {
    var option = $('option:selected', this);
    var pid  = this.value;

    $.ajax({
      type: 'POST',
      url:  '/AjaxController',
      dataType: 'json',
      data: {
        requestType: 'TraerProducto',
        pid: pid
      },
      success: function(data) {
        $('#iPid').val(data.id);
        $('#iProductoTipo').val(data.tipo);
        $('#iProductoDescripción').val(data.descripción);
        $('#iProductoPrecio').val(data.precio);
        $('#iProductoDescuento').val(data.descuento);
        $('#iProductoStock').val(data.stock);
        $('#iPrecioUnitario').val(data.precioFinal);
        calculate();
      },
      error: function(jqXhr, textStatus, errorThrown) {
        alert(errorThrown);
      }
    });
  });

  $('#iPrecioUnitario, #iCantidad').on('keyup', calculate);

});

// Calcular el total a partir del precio unitario y cantidad
function calculate() {
  var total = $('#iPrecioUnitario').val() * $('#iCantidad').val();
  $('#iTotal').val(total);
}
