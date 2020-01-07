$(function () {

    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth(); //获取当前月份(0-11,0代表1月)
    $("#startcd").append("<option value=" + (year - 1) + "-上学期" + ">" + (year - 1) + "-上学期" + "</option>");
    $("#startcd").append("<option value=" + (year - 1) + "-下学期" + ">" + (year - 1) + "-下学期" + "</option>");
    $("#startcd").append("<option value=" + year + "-上学期" + ">" + year + "-上学期" + "</option>");
    $("#startcd").append("<option value=" + year + "-下学期" + ">" + year + "-下学期" + "</option>");

    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) {
            sumbitFun();
        }
    }

    $('#loginId').click(function () {
        sumbitFun();
    });

    $('#codeImg').attr("src", getBaseUrl() + "login/verificationCode");
    $('#codeImg').click(function () {
        $("#codeImg").attr("src", getBaseUrl() + "login/verificationCode?time=" + new Date().getTime());
        $("#code").val("");
    });

    function sumbitFun() {

        var field = new Object();

        field.username = $('#name').val();
        field.password = $('#password').val();
        field.grant_type = 'password';
        field.scope = 'read';
        field.clientId = 'client_admin';
        field.clientSecret = 'client_admin';

        //先请求token
        $.ajax({
            url: getBaseUrl() + 'applyToken',
            data: field,
            type: 'post',
            dataType: 'JSON',
            success: function (data) {
                console.log(data)
            },
            error: function (xhr) {
            }
        });

        console.log(Cookies.get("token"))

        var startcd = $('#startcd').val();

        var identity = $('#identity').val();
        if (identity == 'admin') {
            $.ajax({
                url: getBaseUrl() + 'login/adminLogin',
                data: $('#loginForm').serializeArray(),
                type: 'post',
                dataType: "html",
                headers: {"Authorization": "Bearer " + Cookies.get("token")},
                success: function (data) {
                    document.write(data);
                },
                error: function (xhr) {
                }
            });
        } else if (identity == 'academician') {
            $.ajax({
                url: getBaseUrl() + 'login/academicianLogin',
                data: $('#loginForm').serializeArray(),
                type: 'post',
                dataType: "html",
                headers: {"Authorization": "Bearer " + Cookies.get("token")},
                success: function (data) {
                    window.location.href = getBaseUrl() + data + ".html";
                },
                error: function (xhr) {
                }
            });
        }
    }


});