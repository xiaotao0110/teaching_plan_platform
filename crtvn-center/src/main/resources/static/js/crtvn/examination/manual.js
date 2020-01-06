$(function () {

    var count = 0;     //学生数量
    var data = [];     //教室数组
    var isFlag = false; //是否已清除

    //清除自动排考的记录
    $('#delAutomatic').click(function () {
        $.messager.confirm('提示', '确认清空自动排考记录？', function (r) {
            if (r) {
                $.ajax({
                    type: "post",
                    url: getBaseUrl() + "examination/delAutomatic",
                    dataType: "json",
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            height: 200,
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

    //清除已生成的考试信息
    $('#delexam').click(function () {
        $.messager.confirm('提示', '确认清空已生成的考试记录？', function (r) {
            if (r) {
                isFlag = true;
                $.ajax({
                    type: "post",
                    url: getBaseUrl() + "examination/delexam",
                    dataType: "json",
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            height: 200,
                            msg: data.msg
                        });
                    }
                });
            }
        });
    });


    //排考条件start
    //院系信息下拉框
    $('#college').combobox({
        url: getBaseUrl() + "college/collegeSelect",
        valueField: 'id',
        textField: 'text',
        width: 165,
        onChange: function () {
            loadTeacher();
            loadClassroom();
            loadTeacher();
        },
        onLoadSuccess: function () {
            var val = $(this).combobox('getData');
            for (var item in val[0]) {
                if (item == 'id') {
                    $(this).combobox('select', val[0][item]);
                }
            }
            loadTeacher();
            profession();
            loadClassroom();
            loadTeacher();
        },

    });

    function profession() {
        $('#aprofession').combobox({
            url: getBaseUrl() + "profession/professionSelect?collegeId=" + $('#collegeId').val(),
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
                aloadCourse();
            },
            onChange: function () {
                aloadCourse();
            }
        });
    }

    function aloadCourse() {
        // 课程代号下拉框   闭卷
        $('#acourse').combobox(
            {
                url: getBaseUrl() + "course/courseSelect?professionId="
                    + $('#aprofession').combobox('getValue') + "&courseManner=" + 1
                    + "&courseMark=" + 2,
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
                onChange: function () {

                    $('#aclassroom').combogrid('clear');
                    $('#ateacherCode').combogrid('clear');
                    $('#aetime').combobox('clear');

                    var id = $("#acourse").combobox('getValue');
                    if (id != 0) {
                        $.ajax({
                            type: "post",
                            url: getBaseUrl() + "student/studentCountbyCourse",
                            data: {"courseId": id},
                            dataType: "json",
                            success: function (data) {
                                count = data.count;
                                $.messager.show({
                                    title: '提示信息',
                                    height: 200,
                                    msg: '该课程有' + count + '名学生</br>' + '非考试学生有' + data.notExamcount + '名'
                                });
                                count = count - data.notExamcount;
                            }
                        });
                    }
                }
            });

    };

    function loadClassroom() {
        //教室
        $('#aclassroom').combogrid({
            url: getBaseUrl() + 'classroom/classroomDataGrid',
            panelWidth: 700,
            panelHeight: 370,
            idField: 'classroomId',
            textField: 'code',
            rownumbers: true,
            pagination: true,
            singleSelect: false,
            multiple: true,
            editable: false,
            queryParams: {"mark": 1, "collegeId": $('#college').combobox('getValue')},
            columns: [[
                {field: "ck", width: 50, checkbox: true,},
                {field: "classroomId", hidden: "true"},
                {field: "status", hidden: "true"},
                {field: "thisId", hidden: "true"},
                {field: "collegeName", title: "所属院系", width: 180},
                {field: "building", title: "楼栋", width: 80},
                {field: "code", title: "教室编号", width: 100},
                {field: "seats", title: "座位数", width: 80},
                {field: "number", title: "考场人数", width: 80},
                {
                    field: "oper", title: "说明", width: 100,
                    formatter: function (value, row, index) {
                        if (row.thisId == 0 || row.thisId == null || row.thisId == '') {
                            return "非本学院";
                        } else {
                            if (row.status == 1) {
                                return "已安排";
                            } else if (row.status == 2) {
                                return "未安排";
                            }
                        }
                    }
                },
            ]],
            onChange: function () {
                $('#ateacherCode').combogrid('clear');
            },
            onBeforeLoad: function () {
                copyArr();
            },
            onLoadSuccess: function () {
                copyArr();
            }
        });
    }

    function copyArr() {
        var $aclassroomG = $('#aclassroom').combogrid('grid');
        var dataArr = $aclassroomG.datagrid('getData').rows;
        if (dataArr != undefined && dataArr.length != 0) {
            for (var i = 0; i < dataArr.length; i++) {
                var a = parseInt(dataArr[i].classroomId);
                if (data.length == 0) {
                    data = data.concat(dataArr);
                    break;
                }
                for (var j = 0; j < data.length; j++) {
                    var b = parseInt(data[j].classroomId);
                    if (a == b) {
                        break;
                    }
                }
                if (data.length == j) {
                    data.push(dataArr[i]);
                }
            }
        }
    }

    function loadTeacher() {

        //教师
        $('#ateacherCode').combogrid({
            url: getBaseUrl() + 'teacher/teacherDataGrid',
            panelWidth: 700,
            panelHeight: 370,
            idField: 'teacherId',
            textField: 'teacherName',
            rownumbers: true,
            pagination: true,
            singleSelect: false,
            multiple: true,
            editable: false,
            queryParams: {
                "collegeId": $('#college').combobox('getValue'),
                "mark": 1
            },
            columns: [[
                {field: "ck", width: 50, checkbox: true,},
                {field: "collegeId", hidden: "true"},
                {field: "thisId", hidden: "true"},
                {field: "professionId", hidden: "true"},
                {field: "teacherId", hidden: "true"},
                {field: "courseId", hidden: "true"},
                {field: "mark", hidden: "true"},
                {field: "status", hidden: "true"},
                {field: "collegeName", title: "所属院系", width: 125},
                {field: "professionName", title: "所属专业", width: 150},
                {field: "teacherCode", title: "教师工号", width: 90},
                {field: "teacherName", title: "教师名称", width: 80},
                {field: "phone", title: "联系电话", width: 100},
                {
                    field: "oper", title: "操作", width: 70,
                    formatter: function (value, row, index) {
                        if (row.status == 1) {
                            return "已安排";
                        } else if (row.status == 2) {
                            return "未安排";
                        }
                    }
                },
            ]]
        });

    }


    //时间
    $('#aetime').combobox(
        {
            url: getBaseUrl() + "timeDate/timeSelect?mark=" + 2,
            valueField: 'id',
            textField: 'text',
            width: 300,
            onLoadSuccess: function () { // 加载完成后,设置选中第一项
                var val = $(this).combobox('getData');
                for (var item in val[0]) {
                    if (item == 'id') {
                        $(this).combobox('select', val[0][item]);
                    }
                }
            },
        });

    //排考条件end
    //----------------------------------------------------------------------------------
    //查询条件start
    // 二级联动
    // 专业信息下拉框
    $('#qprofession').combobox({
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
            qloadCourse();
            qloadTeacher1();
            qloadTeacher2();
        },
        onChange: function () {
            qloadCourse();
            qloadTeacher1();
            qloadTeacher2();
        }
    });

    function qloadCourse() {
        // 课程代号下拉框
        $('#qcourse').combobox(
            {
                url: getBaseUrl() + "course/courseSelect?professionId="
                    + $('#qprofession').combobox('getValue') + "&courseManner=" + 1,
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
                }
            });

    };

    function qloadTeacher1() {
        //此处mark表示是(1:自动2:手动)

        var urlStr = getBaseUrl() + "examination/examTeacherSelect?mark=" + 2 + "&status=" + 1
            + "&professionId=" + $('#qprofession').combobox('getValue');
        qloadTeacher("qteacherCode1", urlStr);
    };

    function qloadTeacher2() {
        var urlStr = getBaseUrl() + "examination/examTeacherSelect?mark=" + 2 + "&status=" + 2
            + "&professionId=" + $('#qprofession').combobox('getValue');
        qloadTeacher("qteacherCode2", urlStr);
    };

    function qloadTeacher(id, urlStr) {
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

    //查询条件end

    //考场datagrid
    function setParams() {
        var params = {};

        params.mark = 2;

        if ($("#qprofession").combobox('getValue') == "") {
            params.professionId = 0;
        } else {
            params.professionId = parseInt($("#qprofession").combobox('getValue'));
        }

        if ($("#qcourse").combobox('getValue') == "") {
            params.courseId = 0;
        } else {
            params.courseId = parseInt($("#qcourse").combobox('getValue'))
        }

        if ($("#qteacherCode1").combobox('getValue') == "") {
            params.teacherId1 = 0;
        } else {
            params.teacherId1 = parseInt($("#qteacherCode1").combobox('getValue'));
        }

        if ($("#qteacherCode2").combobox('getValue') == "") {
            params.teacherId2 = 0;
        } else {
            params.teacherId2 = parseInt($("#qteacherCode2").combobox('getValue'));
        }

        return params;
    };

    var bodyHeight = $(window).height();
    var bodyWidth = $(window).width();
    $("#manualDataGrid").datagrid({
        url: getBaseUrl() + "examination/examinationDataGrid",
        title: "考场信息列表",
        width: bodyWidth - 20,
        height: bodyHeight - 320,
        rownumbers: true,
        pagination: true,
        queryParams: setParams(),
        columns: [[
            {field: "examinationId", hidden: "true"},
            {field: "examinationCode", title: "考场号", width: bodyWidth * 0.07},
            {field: "professionName", title: "专业", width: bodyWidth * 0.13},
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
                    return '<a style="color:blue" href="examination/examinationDetails?examinationId='
                        + row.examinationId + '">' + "详情" + '</a>';
                }
            },
        ]]
    })

    $('#manualSumbit').click(function () {
        $("#manualDataGrid").datagrid('load', setParams());//加载数据，不加表格数据不会显示
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

    $('#examSumbit').click(function () {
        if (isFlag) { //已清除
            isFlag = false;
        } else {
            $.messager.alert("提示", '请按照红色提示先操作', function () {
            });
            return;
        }
        $.messager.confirm('提示', '是否生成考试信息?', function (r) {
            if (r) {
                $.ajax({
                    type: "post",
                    url: getBaseUrl() + "examination/exam",
                    data: {"mark": 2},
                    dataType: "json",
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            height: 200,
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


    //准备排考参数
    var flag = true;

    function manualParams() {
        var params = {};

        params.mark = 2;

        if ($("#acourse").combobox('getValue') == 0) {
            flag = messagerWind("请选择一个课程");
            return;
        } else {
            flag = true;
            params.courseId = parseInt($("#acourse").combobox('getValue'))
        }

        if ($("#aetime").combobox('getValue') == 0) {
            flag = messagerWind("请选择一个时间段");
            return;
        } else {
            flag = true;
            params.etimeId = parseInt($("#aetime").combobox('getValue'));
        }

        var setsNumber = 0; //座位数
        var classrooms = $('#aclassroom').combogrid('getValues');
        if (classrooms.length == 0) {
            flag = messagerWind("请选择教室信息");
            return;
        } else {
            for (var i = 0; i < classrooms.length; i++) {
                for (var j = 0; j < data.length; j++) {
                    if (classrooms[i] == data[j].classroomId) {
                        setsNumber += data[j].number;
                        break;
                    }
                }
            }

            if (setsNumber < count) {
                $.messager.show({
                    title: '提示信息',
                    height: 200,
                    msg: '该课程有' + count + '名学生,现分配' + setsNumber + '座位数，座位数不足，请合理分配！'
                });
                flag = false;
                return;
            } else {
                $.messager.alert("提示",
                    '<h4>该课程应考学生有  <font color="#FF0000">' + count + '</font>名' + '</br>'
                    + '现分配 <font color="#FF0000">' + classrooms.length + '</font>个考场' + '</br>'
                    + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                    + '共计 <font color="#FF0000">' + setsNumber + '</font>座位数' + '</br>'
                    + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
                    + '请为每个考场分配<font color="#FF0000">两位</font>监考老师</h4>',
                    "info", function () {
                    }
                );
                flag = true;
                params.classrooms = classrooms.join(',');
            }
        }

        var teachers = $('#ateacherCode').combogrid('getValues');
        if (teachers.length == 0) {
            flag = messagerWind("请分配老师");
            return;
        } else {
            if (teachers.length != classrooms.length * 2) {
                $.messager.show({
                    title: '提示信息',
                    height: 200,
                    msg: '该课程有' + classrooms.length + '个考场,现分配' + teachers.length + '位老师，分配不合理，请合理分配!'
                });
                flag = false;
                return;
            } else {
                params.teachers = teachers.join(',');
            }
        }

        return params;

    }

    function messagerWind(msg) {
        $.messager.show({
            title: '提示信息',
            height: 200,
            msg: msg
        });
        return false;
    }

    //排考
    $('#manualArrangement').click(function () {
        var params = manualParams();
        if (flag) {
            $.ajax({
                type: "post",
                url: getBaseUrl() + "examination/manualInfo",
                data: params,
                dataType: "json",
                success: function (data) {
                    $.messager.show({
                        title: '提示信息',
                        height: 200,
                        msg: data.msg
                    });
                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);
                }
            });
        }

    });

    $('#manualAfterDL').window({
        title: '已安排课程',
        iconCls: 'icon-tip',
        width: bodyWidth - 565,
        height: bodyHeight - 215,
    });

    //默认关闭
    $('#manualAfterDL').dialog('close');
    $('#manualAfter').click(function () {
        $('#manualAfterDL').dialog('open');
        $("#courseDataGrid").datagrid({
            url: getBaseUrl() + 'course/courseDataGrid',
            title: "课程信息列表",
            width: bodyWidth - 600,
            height: bodyHeight - 250,
            rownumbers: true,
            pagination: true,
            queryParams: {"courseMark": 1},
            columns: [[
                {field: "courseId", hidden: "true"},
                {field: "professionName", title: "所属专业", width: bodyWidth * 0.13},
                {field: "courseCode", title: "课程代码", width: bodyWidth * 0.13},
                {field: "courseName", title: "课程名称", width: bodyWidth * 0.13},
                {
                    field: "oper", title: "操作", width: bodyWidth * 0.05,
                    formatter: function (value, row, index) {
                        return "<font color='#FF0000'>双击删除</font>";
                    }
                },
            ]],
            onClickRow: function (rowIndex, rowData) {
                $.messager.confirm('提示', '确认删除该课程的安排？', function (r) {
                    if (r) {
                        $.ajax({
                            type: "post",
                            url: getBaseUrl() + "course/delCourse",
                            dataType: "json",
                            data: {"courseId": rowData.courseId, "courseMark": 2},
                            success: function (data) {
                                $.messager.show({
                                    title: '提示信息',
                                    height: 200,
                                    msg: data.msg
                                });
                                setTimeout(function () {
                                    window.location.reload();
                                }, 2000);
                            }
                        });
                    }
                });
            }

        });
    });

})