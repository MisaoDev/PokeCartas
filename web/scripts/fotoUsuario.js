$('document').ready(function () {
    $("#iFoto").change(function () {
        if (this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#imgFoto').attr('src', e.target.result);
                $('#imgFoto').removeClass('d-none');
            }
            reader.readAsDataURL(this.files[0]);
        }
    });
});
