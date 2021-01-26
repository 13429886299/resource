define(['angular', 'app', 'layer', 'jquery', 'service/manageScheduleService'], function (angular, app, layer, $) {
    app.controller('manageScheduleController', function ($scope, manageScheduleService) {
        $scope.chooseDate = new Date();
        $scope.excelModelDownload = function () {
            manageScheduleService.excelModelDownload(stringToDate($scope.chooseDate).getTime());
        };

        $scope.dateChange = function () {

        };

        function stringToDate(date) {
            var str = date.split('-');
            var d = new Date();
            d.setFullYear(Number(str[0]), Number(str[1]) - 1, Number(str[2]));
            return d;
        }

    })
});
