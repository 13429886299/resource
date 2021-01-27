define(['app', "layer"], function (app, layer) {
    app.service('manageScheduleService', function ($http) {
        return {
            excelModelDownload: function (date) {
                $http({
                    url: "/zmlh/schedule/excel/" + date,
                    method: "GET",
                    responseType: 'arraybuffer'
                }).then(function (data) {
                    var blob = new Blob([data.data], {type: "application/vnd.ms-excel"}); //这里的格式
                    var objectUrl = URL.createObjectURL(blob);
                    //利用浏览器打开URL实现下载
                    var a = document.createElement('a');
                    document.body.appendChild(a);
                    a.setAttribute('style', 'display:none');
                    a.setAttribute('href', objectUrl);
                    var filename = "逐梦轮滑课程安排表.xlsx";
                    a.setAttribute('download', filename);
                    a.click(); //触发点击，下载文件
                    URL.revokeObjectURL(objectUrl);
                })
                    .catch(function (e) {
                        layer.alert("模板下载失败！" + e)
                    });
            },
            getTabData: function (pageNo, pageSize, time, cb) {
                $http.get("/zmlh/schedule/select/" + pageNo + "/" + pageSize + "/" + time)
                    .then(function (data) {
                        var d = data.data;
                        if (d.code == 200) {
                            cb(d.object);
                        } else {
                            layer.alert(d.object);
                        }
                    }).catch(function (e) {
                    layer.alert("获取表格资源失败");
                })
            },
            importScheduleExcel: function (formData, time, cb) {
                console.log("data:" + JSON.stringify(time));
                $.ajax({
                    url: '/zmlh/schedule/excel/' + time,
                    type: "POST",
                    data: formData,
                    processData: false,// 告诉jQuery不要去处理发送的数据
                    contentType: false,// 告诉jQuery不要去设置Content-Type请求头
                    dataType: "json",
                    success: function (data) {

                        //成功后的操作
                        cb(data);
                    },
                    error: function (e) {
                        layer.alert("上传失败")
                    }
                });
            }
        }
    })
});
