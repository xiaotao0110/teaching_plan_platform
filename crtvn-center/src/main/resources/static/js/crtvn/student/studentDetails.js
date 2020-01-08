$(function () {

    //考试科目下拉框
    $('#testSubjects').combobox({
        url: getBaseUrl() + "student/studentExamCourseYesSelect?mark=2&studentId=" + $('#studentId').val(),
        headers: {"Authorization": "Bearer " + Cookies.get("token")},
        valueField: 'id',
        textField: 'text',
        width: 165,
        onLoadSuccess: function () { //加载完成后,设置选中第一项
            var val = $(this).combobox('getData');
            for (var item in val[0]) {
                if (item == 'id') {
                    $(this).combobox('select', val[0][item]);
                }
            }
        },
    });

    //非考试科目下拉框
    $('#noTestSubjects').combobox({
        url: getBaseUrl() + "student/studentExamCourseNoSelect?mark=2&studentId=" + $('#studentId').val(),
        headers: {"Authorization": "Bearer " + Cookies.get("token")},
        valueField: 'id',
        textField: 'text',
        width: 165,
        onLoadSuccess: function () { //加载完成后,设置选中第一项
            var val = $(this).combobox('getData');
            for (var item in val[0]) {
                if (item == 'id') {
                    $(this).combobox('select', val[0][item]);
                }
            }
        },
        onSelect: function (record) {
            loadreason(record.id);
        }
    });

    //加载学生缺考理由
    function loadreason(courseId) {
        $.ajax({
            type: "post",
            url: getBaseUrl() + "student/studentReason",
            headers: {"Authorization": "Bearer " + Cookies.get("token")},
            data: {
                "studentId": $('#studentId').val(),
                "courseId": courseId
            },
            dataType: "json",
            success: function (data) {
                $('#reason').val(data.reason);
            }
        });
    }


    //考试状态下拉框
    $('#status').combobox({
        data: [{"id": 1, "text": "是"}, {"id": 2, "text": "否"}],
        valueField: 'id',
        textField: 'text',
        width: 165,
        onLoadSuccess: function () { //加载完成后,设置选中第一项
            var val = $(this).combobox('getData');
            for (var item in val[0]) {
                if (item == 'id') {
                    $(this).combobox('select', val[0][item]);
                }
            }
        },
        onChange: function (newValue, oldValue) {
            if (newValue == 2) {
                $('#reason').removeAttr("disabled");
            } else {
                $('#reason').val("");
                $('#reason').attr("disabled", "disabled");
            }
        }
    });

    var param = {};

    $('#emit1').click(function () {
        $('#noTestSubjects').combobox("disable");
        $('#testSubjects').combobox("enable");
        $('#status').combobox("enable");
        $('#keep').show();

        param.courseId = $('#testSubjects').combobox('getValue');

    });

    $('#emit2').click(function () {
        $('#noTestSubjects').combobox("enable");
        $('#testSubjects').combobox("disable");
        $('#status').combobox("enable");
        $('#keep').show();

        param.courseId = $('#noTestSubjects').combobox('getValue');

    });

    $('#keep').click(function () {
        var flag = true;
        param.studentId = $('#studentId').val();
        param.mark = $('#status').combobox('getValue');
        if (param.mark == 2) {
            if ($('#reason').val() == '' || $('#reason').val().length < 0) {
                flag = false;
                alert("输入缺考理由！");
            }
            param.reason = $('#reason').val();
        }
        if (flag) {
            $.ajax({
                type: "post",
                url: getBaseUrl() + "student/studentExamCourseStatus",
                data: param,
                dataType: "json",
                headers: {"Authorization": "Bearer " + Cookies.get("token")},
                success: function (data) {
                    var msg = "失败";
                    if (data.msg == "ok") {
                        msg = "成功";
                    }
                    $.messager.show({
                        title: '提示信息',
                        msg: '修改' + msg
                    });
                    $('#status').combobox("disable");
                    $('#noTestSubjects').combobox("enable");
                    $('#testSubjects').combobox("enable");
                    $('#keep').hide();

                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);
                }
            });
        }
    });


});