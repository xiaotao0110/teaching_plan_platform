$(function () {

    // 退出
    $('#login').click(function () {
        $.messager.confirm('提示', '是否退出?', function (r) {
            if (r) {
                this.location.href = "http://localhost:8080/crtvn/sys/login";
            }
        });

    });

    function setParams() {
        var params = {};

        if ($("#collegeId").combobox('getValue') == "") {
            params.collegeId = 0;
        } else {
            params.collegeId = parseInt($("#collegeId").combobox('getValue'));
        }
        params.academicianName = $('#name').val();
        params.password = $('#password').val();

        return params;
    }
    ;

    var bodyHeight = $(window).height();
    var bodyWidth = $(window).width();
    $("#adminDataGrid")
        .datagrid(
            {
                url: getBaseUrl() + $('#adminForm').attr('action'),
                headers: {"Authorization": "Bearer " + Cookies.get("token")},
                title: "教务人员信息列表",
                width: bodyWidth - 20,
                height: bodyHeight - 180,
                rownumbers: true,
                pagination: true,
                queryParams: setParams(),
                columns: [[
                    {
                        field: "collegeId",
                        hidden: "true"
                    },
                    {
                        field: "academicianId",
                        hidden: "true"
                    },
                    {
                        field: "collegeName",
                        title: "所属院系",
                        width: bodyWidth * 0.1
                    },
                    {
                        field: "employeeNo",
                        title: "职工号",
                        width: bodyWidth * 0.1
                    },
                    {
                        field: "academicianName",
                        title: "名称",
                        width: bodyWidth * 0.1
                    },
                    {
                        field: "password",
                        title: "密码",
                        width: bodyWidth * 0.1
                    },
                    {
                        field: "oper",
                        title: "操作",
                        width: bodyWidth * 0.05,
                        formatter: function (value, row, index) {
                            return '<a style="color:blue" href="' + getBaseUrl() + 'admin/deleteAcademician?academicianId='
                                + row.academicianId
                                + '">'
                                + "删除" + '</a>';
                        }
                    },]]
            });

    $('#adminSumbit').click(function () {
        $("#adminDataGrid").datagrid('load', setParams());// 加载数据，不加表格数据不会显示
    });

    var datas;
    $.ajax({
        type: "POST",
        url: "college/collegeSelect",
        headers: {"Authorization": "Bearer " + Cookies.get("token")},
        success: function (result) {

        }
    });

    // 院系信息下拉框
    $('#collegeCode').combobox({
        url: getBaseUrl() + "college/collegeSelect",
        valueField: 'id',
        textField: 'text',
        width: 165,
        onLoadSuccess: function () {
            datas = $('#collegeCode').combobox('getData');
            datas.shift();
        }
    });

    $('#addAdminDL')
        .dialog(
            {
                title: '添加教务人员',
                iconCls: 'icon-add',
                width: 300,
                height: 320,
                buttons: [
                    {
                        text: '确定',
                        handler: function () {
                            var flag = true;
                            var params = setParams();
                            if (params.collegeId == 0) {
                                flag = false;
                                alert("未选择学院!");
                            }
                            if (params.academicianName == ''
                                || params.academicianName == null) {
                                flag = false;
                                alert("未输入名字!");
                            } else {
                                var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]{2,5}$/ig;
                                if (!reg
                                    .test(params.academicianName)) {
                                    flag = false;
                                    alert("名字输入的格式不对,请重新输入！");
                                }
                            }

                            if (params.password == ''
                                || params.password == null) {
                                flag = false;
                                alert("未输入密码!");
                            } else {
                                var reg = /^[A-Za-z0-9]{6,16}$/ig;
                                if (!reg.test(params.password)) {
                                    flag = false;
                                    alert("密码输入的格式不对,请重新输入！");
                                }
                            }

                            if (flag) {
                                $.ajax({
                                    type: "POST",
                                    url: getBaseUrl() + "admin/addAcademician",
                                    data: params,
                                    success: function (result) {

                                        $.messager.show({
                                            title: '提示信息',
                                            msg: result.msg
                                        });

                                        $('#addAdminDL').dialog(
                                            'close');

                                    }
                                });
                            }
                        }
                    }, {
                        text: '取消',
                        handler: function () {
                            $('#addAdminDL').dialog('close');
                        }
                    }],
                onOpen: function () {
                    // 添加框 院系信息下拉框
                    $('#collegeId').combobox({
                        data: datas,
                        valueField: 'id',
                        textField: 'text',
                        width: 165
                    });

                }
            });
    // 默认关闭
    $('#addAdminDL').dialog('close');
    $('#addAdminBtn').click(function () {
        $('#addAdminDL').dialog('open');
    });

    function regex(nameStr, passwordStr) {

        var nameRegex = /^[A-Za-z\u4e00-\u9fa5]{2,10}$/;
        var passwordRegex = /^[a-zA-Z0-9]{6,16}$/;

        return nameRegex.test(nameStr) && passwordRegex.test(passwordStr);
    }
    ;

})