define(['app', "layer"], function (app, layer) {
    app.service('manageScheduleTimeService', function ($http) {
        return {
            getAllScheduleTime: function () {
                $http.get("").then(function (data) {

                }).catch(function (e) {

                })
            },
        }
    })
});
