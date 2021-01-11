/**
 * Created by Administrator on 2016/12/2.
 */
define(['angular', 'app', 'layer', 'jquery', 'service/layoutService'], function (angular, app, layer, $) {
    app.controller('powerRebootController', function ($scope, $http, layoutService) {
        //屏幕高度初始化
        layoutService.upTitleHeight();
        layoutService.downTitleHeight();
        layoutService.mainBodyHeight();
        layoutService.workcontentHeight();
        //屏幕大小改变事件
        window.onresize = function () {
            console.log("=================onsize================");
            layoutService.upTitleHeight();
            layoutService.downTitleHeight();
            layoutService.mainBodyHeight();
            layoutService.workcontentHeight();
        };
        //重启
        $scope.reboot = function () {
            var r = confirm("确定重启系统?");
            if (r == true) {
                $.ajax({
                    url: "/fxrest/v5/geo/resources/serversetting",
                    dataType: "text",
                    type: "post",
                    data: {
                        cmd: "reboot -nf"
                    },
                    success: function (d) {
                        layer.alert("重启成功");
                    },
                    error: function (e) {
                        layer.alert("重启失败");
                    }
                });
            }
        };
        // 重启
        // $scope.reboot = function () {
        //     var r = confirm("确定重启系统?");
        //     if (r == true) {
        //         $http.post("/fxrest/v5/geo/resources/serversetting", "reboot -nf"
        //         ).then(function (d) {
        //             alert(d.data)
        //             console.log(d)
        //         }).catch(function (reason) {
        //             console.log(reason)
        //         });
        //     }
        // };
    });
});

