$(function () {

    $('#startTime').datetimebox({showSeconds: false});
    $('#endTime').datetimebox({showSeconds: false});

    var bodyHeight = $(window).height();
    var bodyWidth = $(window).width();
    $("#timeDataGrid").datagrid({
        url: getBaseUrl() + "timeDate/timeDataGrid",
        headers: {"Authorization": "Bearer " + Cookies.get("token")},
        title: "课程信息列表",
        width: bodyWidth - 20,
        height: bodyHeight - 180,
        rownumbers: true,
        pagination: true,
        singleSelect: true,
        columns: [[
            {field: "id", hidden: "true"},
            {
                field: "stime", title: "开始时段", width: bodyWidth * 0.2,
                formatter: function (value, row, index) {
                    var d = new Date(value)
                    var t = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" +
                        d.getDate() + " " + d.getHours() + ":" + (d.getMinutes() == 0 ? "00" : d.getMinutes());
                    return t;
                }
            },

            {
                field: "etime", title: "结束时段", width: bodyWidth * 0.2,
                formatter: function (value, row, index) {
                    var d = new Date(value)
                    var t = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" +
                        d.getDate() + " " + d.getHours() + ":" + (d.getMinutes() == 0 ? "00" : d.getMinutes());
                    return t;
                }
            },
        ]]
    })

    $('#addTimeDL').dialog({
        title: '添加时段',
        iconCls: 'icon-add',
        width: 300,
        height: 200,
        buttons: [
            {
                text: '确定',
                handler: function () {
                    $.ajax({
                        type: "POST",
                        url: getBaseUrl() + "timeDate/addTime",
                        headers: {"Authorization": "Bearer " + Cookies.get("token")},
                        data: {
                            'stime': $("#startTime").combobox('getValue'),
                            'etime': $("#endTime").combobox('getValue')
                        },
                        success: function (result) {
                            var msg = "失败";
                            if (result.msg == "ok") {
                                msg = "成功";
                            }
                            $.messager.show({
                                title: '提示信息',
                                msg: '添加' + msg
                            });
                            $('#addTimeDL').dialog('close');

                            setTimeout(function () {
                                window.location.reload();
                            }, 2000);
                        }
                    });

                }
            },
            {
                text: '取消',
                handler: function () {
                    $('#addTimeDL').dialog('close');
                }
            }
        ]
    });
    //默认关闭
    $('#addTimeDL').dialog('close');
    $('#addTimeBtn').click(function () {
        $('#addTimeDL').dialog('open');
    });

    var id;
    $('#delTimeDL').dialog({
        title: '删除时段',
        iconCls: 'icon-no',
        width: 300,
        height: 150,
        buttons: [
            {
                text: '确定',
                handler: function () {
                    $.ajax({
                        type: "POST",
                        url: getBaseUrl() + "timeDate/delTime",
                        headers: {"Authorization": "Bearer " + Cookies.get("token")},
                        data: {"id": id},
                        success: function (result) {
                            if (true) {
                                var msg = "失败";
                                if (result.msg == "ok") {
                                    msg = "成功";
                                }
                                $.messager.show({
                                    title: '提示信息',
                                    msg: '添加' + msg
                                });
                                $('#delTimeDL').dialog('close');
                                setTimeout(function () {
                                    window.location.reload();
                                }, 2000);
                            }
                        }
                    });

                }
            },
            {
                text: '取消',
                handler: function () {
                    $('#delTimeDL').dialog('close');
                }
            }
        ]
    });
    //默认关闭
    $('#delTimeDL').dialog('close');
    $('#delTimeBtn').click(function () {
        var flag = true;
        var row = $('#timeDataGrid').datagrid('getSelected');
        if (row == null) {
            flag = false;
            alert("未选择，请选择要删除的列！");
        }
        if (flag) {
            id = row.id;
            $('#delTimeDL').dialog('open');
        }
    });
})