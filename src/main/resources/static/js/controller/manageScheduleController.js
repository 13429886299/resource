define(['angular', 'app', 'layer', 'jquery', 'service/manageScheduleService'], function (angular, app, layer, $) {
    app.controller('manageScheduleController', function ($scope, manageScheduleService) {
        var month = new Date().getMonth() + 1, day = new Date().getDate();
        $scope.chooseDate = new Date().getFullYear() + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
        $scope.pageNo = 1;
        $scope.num = 1;
        $scope.scheduleTimeTitle = ["序号", "姓名", "日期", "时间", "类型", "是否消课"];
        $scope.scheduleTimeData = [];
        $scope.excelModelDownload = function () {
            manageScheduleService.excelModelDownload(stringToDate($scope.chooseDate).getTime());
        };

        $scope.dateChange = function () {
            $scope.getTabData();
        };

        $scope.getTabData = function () {
            manageScheduleService.getTabData($scope.pageNo, 10, $scope.chooseDate + "T00:00:00.282Z", function (data) {
                $scope.pageSize = Math.ceil(data.total / data.size);
                $scope.scheduleTimeData = [];
                if (data.records && data.records.length > 0) {
                    $scope.scheduleTimeData = data.records;
                }
            })
        };

        $scope.selectPage = function (pageNo) {
            $scope.pageNo = pageNo;
            $scope.getTabData();
        };

        $scope.previous = function () {
            if ($scope.pageNo == 1) {
                layer.alert("已经是第一页了");
                return;
            }
            $scope.pageNo = $scope.pageNo - 1;
            $scope.getTabData();
        };

        $scope.next = function () {
            if ($scope.pageNo == $scope.pageSize) {
                layer.alert("已经是最后一页了");
                return;
            }
            $scope.pageNo = $scope.pageNo + 1;
            $scope.getTabData();
        };

        $scope.checkRecord = function () {

        };

        $scope.timeCompare = function () {
            return new Date($scope.chooseDate) >= new Date(new Date().getFullYear() + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day));
        };

        //确认上传
        $scope.importExcel = function () {
            //Excel文件导入到数据库中
            //检验导入的文件是否为Excel文件
            var uploadFile = document.getElementById("uploadFile").value;
            if (uploadFile == null || uploadFile == '') {
                layer.alert("请选择要上传的Excel文件");
                return;
            } else {
                var fileExtend = uploadFile.substring(uploadFile.lastIndexOf('.')).toLowerCase();
                if (fileExtend == '.xls' || fileExtend == '.xlsx') {
                } else {
                    layer.alert("请选择要上传的Excel文件");
                    return;
                }
            }
            //提交表单
            var formData = new FormData();
            formData.append('file', $('#uploadFile')[0].files[0]);
            manageScheduleService.importScheduleExcel(formData, $scope.chooseDate + "T12:30:00.272Z", function (data) {
                if (data.code == 200) {
                    $scope.getTabData();
                    layer.alert("上传成功");
                } else {
                    layer.alert(data.object)
                }
            })
        };

        function stringToDate(date) {
            var str = date.split('-');
            var d = new Date();
            d.setFullYear(Number(str[0]), Number(str[1]) - 1, Number(str[2]));
            return d;
        }

    })
});
