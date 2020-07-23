$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

$(function() {
    $('form').submit(function(e) {
        e.preventDefault();
    });
    $('.btn-delete-with-path').click(function() {
        var url = $(this).attr("url-attr") + $('#inputToDelete').val();
        console.log(url);
        $.ajax({
            type: "POST",
            url: url,

            success: function(response) {
                alert("Success");
                window.location.href="../app";
            },

            error : function(xhr, ajaxOptions, thrownError) {
                if(xhr.status == 404) {
                    alert('status:' + xhr.status + ', status text: ' + xhr.statusText);
                }
            }
        });
    });
    $('.btn-manage-entity').click(function() {
        if($('#inputEmail').length) {
            if(!validateEmail($('#inputEmail').val())) {
                alert("Email is not valid");
                return;
            }
        }
        var jsonText = JSON.stringify($('form').serializeObject());
        var url = $(this).attr("url-attr");
        $.ajax({
            type: "POST",
            url: url,
            data: jsonText,
            crossDomain: true,
            contentType : "application/json",
            dataType: "json",

            success: function(response) {
                if(typeof response === 'string') {
                    if(response.text == 'Busy') {
                        alert("Success\nWarning: the person is busy this time")
                    }
                }
                else {
                    alert("Success");
                }

                window.location.href="../app";
            },

            error : function(xhr, ajaxOptions, thrownError) {
                if(xhr.status == 404) {
                    alert('Not found');
                }
                if(xhr.status == 500) {
                    alert('Internal server error');
                }
                else {
                    if(xhr.responseText == 'Busy') {
                        alert("Success\nWarning: the person is busy this time")
                    }
                    else {
                        alert("Success");
                    }
                    window.location.href="../app";
                }
            }
        });
    });
});

function validateEmail(mail)
{
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
    {
        return (true)
    }
    return (false)
}
