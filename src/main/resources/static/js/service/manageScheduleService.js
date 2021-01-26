define(['app', "layer"], function (app, layer) {
    app.service('manageScheduleService', function ($http) {
        return {

            excelModelDownload: function (date) {
                console.log(date);
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
            }
        }
    })
});
