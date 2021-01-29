define(['angular', 'app', 'layer', 'jquery', 'service/manageScheduleTimeService'], function (angular, app, layer, $) {
    app.controller('manageScheduleTimeController', function ($scope, manageScheduleTimeService) {
        $scope.scheduleTimeList = [{id: "", startTime: "", endTime: ""}];
        $scope.seasonList = {1: "春天", 2: "夏天", 3: "秋天", 4: "冬天"};
        $scope.season = 1;
        $scope.getSeason = function () {
            let month = new Date().getMonth() + 1;
            $scope.season = Math.ceil(month / 3);
            for (let seasonListKey in $scope.seasonList) {
                if (seasonListKey == $scope.season) {
                    $scope.seasonName = $scope.seasonList[seasonListKey];
                }
            }
        }
    })
});
