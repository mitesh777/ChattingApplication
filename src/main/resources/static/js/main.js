$(document).ready(function () {

    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();

        get_ajax_data();

    });

});

function get_ajax_data() {

    var search = {}

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "get",
        contentType: "application/json",
        url: "getdata/dataset",
        success: function (data) {
        	var json =JSON.stringify(data);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);
            $('#feedback').html(json);

        },
        error: function (e) {
        	
            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });
}
