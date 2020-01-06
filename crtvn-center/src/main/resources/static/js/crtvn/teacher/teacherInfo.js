$(function () {

    function setParams() {
        var params = {};

        if ($("#college").combobox('getValue') == "") {
            params.collegeId = 0;
        } else {
            params.collegeId = parseInt($("#college").combobox('getValue'));
        }

        if ($("#profession").combobox('getValue') == "") {
            params.professionId = 0;
        } else {
            params.professionId = parseInt($("#profession").combobox('getValue'));
        }

        if ($("#teacherCode").combobox('getValue') == "") {
            params.teacherId = 0;
        } else {
            params.teacherId = parseInt($("#teacherCode").combobox('getValue'));
        }

        if ($("#courseCode").combobox('getValue') == "") {
            params.courseId = 0;
        } else {
            params.courseId = parseInt($("#courseCode").combobox('getValue'));
        }

        if ($("#status").combobox('getValue') == "") {
            params.mark = 0;
        } else {
            params.mark = parseInt($("#status").combobox('getValue'))
        }

        params.teacherName = $("#teacherName").val();

        return params;
    };

    var bodyHeight = $(window).height();
    var bodyWidth = $(window).width();
    $("#teacherDataGrid").datagrid({
        url: getBaseUrl() + $('#teacherForm').attr('action'),
        title: "教师信息列表",
        width: bodyWidth - 20,
        height: bodyHeight - 180,
        rownumbers: true,
        pagination: true,
        queryParams: setParams(),
        columns: [[
            {field: "collegeId", hidden: "true"},
            {field: "thisId", hidden: "true"},
            {field: "professionId", hidden: "true"},
            {field: "teacherId", hidden: "true"},
            {field: "courseId", hidden: "true"},
            {field: "mark", hidden: "true"},
            {field: "collegeName", title: "所属院系", width: bodyWidth * 0.1},
            {field: "professionName", title: "所属专业", width: bodyWidth * 0.1},
            {field: "teacherCode", title: "教师工号", width: bodyWidth * 0.1},
            {field: "teacherName", title: "教师名称", width: bodyWidth * 0.1},
            {field: "phone", title: "联系电话", width: bodyWidth * 0.1},
            {
                field: "oper", title: "操作", width: bodyWidth * 0.05,
                formatter: function (value, row, index) {
                    if (row.thisId == 0 || row.thisId == null || row.thisId == '') {
                        return "<a>详情</a>"
                    } else {
                        return '<a style="color:blue" href="' + getBaseUrl() + 'teacher/teacherDetails?collegeId='
                            + row.collegeId + '&professionId=' + row.professionId + ''
                            + '&teacherId=' + row.teacherId + ''
                            + '&courseId=' + row.courseId + ''
                            + '&teacherName=' + row.teacherName + ''
                            + '&mark=' + row.mark + '"+">'
                            + "详情" + '</a>';
                    }
                }
            },
        ]]
    });

    $('#teacherSumbit').click(function () {
        $("#teacherDataGrid").datagrid('load', setParams());//加载数据，不加表格数据不会显示
    });


    // 院系信息下拉框
    $('#college').combobox({
        url: getBaseUrl() + "college/collegeSelect",
        valueField: 'id',
        textField: 'text',
        width: 165,
        onChange: function () {
            profession();
            teacher();
        }
    });

    function profession() {
        var collegeId = $('#college').combobox('getValue');
        if (collegeId == '') {
            collegeId = 0;
        }
        // 专业信息下拉框
        $('#profession').combobox({
            url: getBaseUrl() + "profession/professionSelect?collegeId=" + collegeId,
            valueField: 'id',
            textField: 'text',
            width: 165,
            onChange: function () {
                teacher();
                course();
            }
        });
    };

    function teacher() {

        var collegeId = $('#college').combobox('getValue');
        if (collegeId == '') {
            collegeId = 0;
        }

        var professionId = $('#profession').combobox('getValue');
        if (professionId == '') {
            professionId = 0;
        }

        // 教师工号信息下拉框
        $('#teacherCode').combobox(
            {
                url: getBaseUrl() + "teacher/teacherSelect?collegeId=" + collegeId
                    + "&professionId=" + professionId,
                valueField: 'id',
                textField: 'text',
                width: 165,
            });

    };

    function course() {
        var professionId = $('#profession').combobox('getValue');
        if (professionId == '') {
            professionId = 0;
        }

        //课程代号下拉框
        $('#courseCode').combobox({
            url: getBaseUrl() + "course/courseSelect?professionId=" + professionId,
            valueField: 'id',
            textField: 'text',
            width: 165,

        });

    };

    // 初始化加载一次
    profession();
    teacher();
    course();

    // 考试状态下拉框
    $('#status').combobox({
        data: [
            {"id": 0, "text": "全部"},
            {"id": 1, "text": "是"},
            {"id": 2, "text": "否"}
        ],
        valueField: 'id',
        textField: 'text',
        width: 165
    });


});