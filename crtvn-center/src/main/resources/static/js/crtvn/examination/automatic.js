$(function () {

    $('#automaticInfo').click(function () {
        $.messager.confirm('提示', '确认自动排考？', function (r) {
            if (r) {
                $.ajax({
                    type: "post",
                    url: getBaseUrl() + "examination/automaticInfo",
                    data: {"mark": 1, "renew": 1},
                    dataType: "json",
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.msg
                        });
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    }
                });
            }
        });

    });

    $('#automaticRenew').click(function () {
        $.messager.confirm('提示', '确认重新排考？', function (r) {
            if (r) {
                $.ajax({
                    type: "post",
                    url: getBaseUrl() + "examination/automaticInfo",
                    data: {"mark": 1, "renew": 2},
                    dataType: "json",
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.msg
                        });
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    }
                });
            }
        });

    });

    $('#examSumbit').click(function () {
        $.messager.confirm('提示', '是否生成考试信息?', function (r) {
            if (r) {
                $.ajax({
                    type: "post",
                    url: getBaseUrl() + "examination/exam",
                    data: {"mark": 1},
                    dataType: "json",
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.msg
                        });
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    }
                });
            }
        });

    });

    function setParams() {
        var params = {};

        params.mark = 1;

        if ($("#profession").combobox('getValue') == "") {
            params.professionId = 0;
        } else {
            params.professionId = parseInt($("#profession").combobox('getValue'));
        }

        if ($("#courseCode").combobox('getValue') == "") {
            params.courseId = 0;
        } else {
            params.courseId = parseInt($("#courseCode").combobox('getValue'))
        }

        if ($("#teacherCode1").combobox('getValue') == "") {
            params.teacherId1 = 0;
        } else {
            params.teacherId1 = parseInt($("#teacherCode1").combobox('getValue'));
        }

        if ($("#teacherCode2").combobox('getValue') == "") {
            params.teacherId2 = 0;
        } else {
            params.teacherId2 = parseInt($("#teacherCode2").combobox('getValue'));
        }

        return params;
    };

    var bodyHeight = $(window).height();
    var bodyWidth = $(window).width();
    $("#examinationsDataGrid").datagrid({
        url: getBaseUrl() + "examination/examinationDataGrid",
        title: "考场信息列表",
        width: bodyWidth - 20,
        height: bodyHeight - 180,
        rownumbers: true,
        pagination: true,
        queryParams: setParams(),
        columns: [[
            {field: "examinationId", hidden: "true"},
            {field: "examinationCode", title: "考场号", width: bodyWidth * 0.08},
            {field: "professionName", title: "专业", width: bodyWidth * 0.12},
            {field: "courseCode", title: "课程代码", width: bodyWidth * 0.07},
            {field: "courseName", title: "课程名称", width: bodyWidth * 0.15},
            {field: "classroomCode", title: "教室", width: bodyWidth * 0.07},
            {field: "teacherName1", title: "监考人员一", width: bodyWidth * 0.08},
            {field: "teacherName2", title: "监考人员二", width: bodyWidth * 0.08},
            {
                field: "stime", title: "开始时段", width: bodyWidth * 0.1,
                formatter: function (value, row, index) {
                    var d = new Date(value)
                    var t = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" +
                        d.getDate() + " " + d.getHours() + ":" + (d.getMinutes() == 0 ? "00" : d.getMinutes());
                    return t;
                }
            },

            {
                field: "etime", title: "结束时段", width: bodyWidth * 0.1,
                formatter: function (value, row, index) {
                    var d = new Date(value)
                    var t = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" +
                        d.getDate() + " " + d.getHours() + ":" + (d.getMinutes() == 0 ? "00" : d.getMinutes());
                    return t;
                }
            },
            {field: "number", title: "人数", width: bodyWidth * 0.05},
            {
                field: "oper", title: "操作", width: bodyWidth * 0.05,
                formatter: function (value, row, index) {
                    return '<a style="color:blue" href="' + getBaseUrl() + 'examination/examinationDetails?examinationId='
                        + row.examinationId + '">' + "详情" + '</a>';
                }
            },
        ]]
    })

    $('#automaticSumbit').click(function () {
        $("#examinationsDataGrid").datagrid('load', setParams());//加载数据，不加表格数据不会显示
    });

    //下载考场
    $('#downloadE').click(function () {

        $.messager.confirm('提示', '是否下载查询的考场信息？', function (r) {
            if (r) {
                var params = setParams();
                window.location.href = getBaseUrl() + "examination/exportExcelExamination?professionId="
                    + params.professionId + "&courseId=" + params.courseId
                    + "&teacherId1=" + params.teacherId1 + "&teacherId2=" + params.teacherId2
                    + "&mark=" + params.mark;
            }
        });

    });

    //下载考试
    $('#downloade').click(function () {
        $.messager.confirm('提示', '是否下载查询到的考场对应的考试信息？', function (r) {
            if (r) {
                var params = setParams();
                window.location.href = getBaseUrl() + "examination/exportExcelExamn?professionId="
                    + params.professionId + "&courseId=" + params.courseId
                    + "&teacherId1=" + params.teacherId1 + "&teacherId2=" + params.teacherId2
                    + "&mark=" + params.mark;
            }
        });

    });

    // 二级联动
    // 专业信息下拉框
    $('#profession').combobox({
        url: getBaseUrl() + "profession/professionSelect",
        valueField: 'id',
        textField: 'text',
        width: 165,
        onLoadSuccess: function () { // 加载完成后,设置选中第一项
            var val = $(this).combobox('getData');
            for (var item in val[0]) {
                if (item == 'id') {
                    $(this).combobox('select', val[0][item]);
                }
            }
            loadCourse();
            loadTeacher1();
            loadTeacher2();
        },
        onChange: function () {
            loadCourse();
            loadTeacher1();
            loadTeacher2();
        }
    });

    function loadCourse() {
        // 课程代号下拉框
        $('#courseCode').combobox(
            {
                url: getBaseUrl() + "course/courseSelect?professionId="
                    + $('#profession').combobox('getValue') + "&courseManner=" + 1,
                valueField: 'id',
                textField: 'text',
                width: 165,
                onLoadSuccess: function () { // 加载完成后,设置选中第一项
                    var val = $(this).combobox('getData');
                    for (var item in val[0]) {
                        if (item == 'id') {
                            $(this).combobox('select', val[0][item]);
                        }
                    }
                },
            });

    };

    function loadTeacher1() {
        var urlStr = getBaseUrl() + "examination/examTeacherSelect?mark=" + 1 + "&status=" + 1
            + "&professionId=" + $('#profession').combobox('getValue');
        loadTeacher("teacherCode1", urlStr);
    };

    function loadTeacher2() {
        var urlStr = getBaseUrl() + "examination/examTeacherSelect?mark=" + 1 + "&status=" + 2
            + "&professionId=" + $('#profession').combobox('getValue');
        loadTeacher("teacherCode2", urlStr);
    };

    function loadTeacher(id, urlStr) {
        // 课程代号下拉框
        $('#' + id + '').combobox(
            {
                url: urlStr,
                valueField: 'id',
                textField: 'text',
                width: 165,
                onLoadSuccess: function () { // 加载完成后,设置选中第一项
                    var val = $(this).combobox('getData');
                    for (var item in val[0]) {
                        if (item == 'id') {
                            $(this).combobox('select', val[0][item]);
                        }
                    }
                },
            });

    }

})