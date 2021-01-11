/**
 * Created by xcz on 2016/11/7.
 */
/**登录控制器 ********************************************************************************************************/
define(['angular', 'app', 'layer', 'jquery', 'md5', 'service/loginService'], function (angular, app, layer) {
    console.log('login controller init...');
    app.controller('loginController', function ($scope, $timeout, loginService, $state) {
        $("#errormesg").hide();
        $scope.errortips = null;
        $scope.showAuthCode = '';
        $scope.user = {name: '', password: ''};
        var EnterFlag = true;
        //获取保存的用户名
        $scope.user.name = localStorage.getItem("rew");
        console.log("$scope.user.name-----" + $scope.user.name);
        //判断是否保存复选框选中状态
        if ($scope.user.name) {
            $scope.checkOrNO = true;
        } else {
            $scope.checkOrNO = false;
        }
        $scope.loginCtrl = function () {
            $scope.errortips = null;
            if ($scope.user.name && $scope.user.password) {
                var psw = $.md5($scope.user.password, 32);
                console.log($scope.user.password + "----------" + psw);
                loginService.login($scope.user.name, psw, function (data) {
                    if (data.code == 200) {
                        //设置cookie
                        window.localStorage.setItem("currentLoginUsername", $scope.user.name);
                        setCookie($scope.user.name, psw);
                        $state.go('main.resourceManage');
                    } else {
                        $scope.errortips = data.message;
                        $("#errormesg").show();
                    }
                });
            } else {
                $scope.errortips = "用户名或密码错误";
                $("#errormesg").show();
            }
            console.log("$scope.errortips:" + $scope.errortips);
        };
        //清空
        $scope.clear = function () {
            if ($scope.user.name && $scope.user.password) {
                $scope.user.name = "";
                $scope.user.password = "";
            }
        };

        function setCookie(userName, password) {
            //获取当前时间
            var date = new Date();
            var expiresTime = 600;
            //将date设置为60分钟以后的时间
            date.setTime(date.getTime() + expiresTime * 60000);
            //将userName和password两个cookie设置为60分钟后过期
            document.cookie = "userName=" + userName + ";expires=" + date.toGMTString();
            document.cookie = "password=" + password + ";expires=" + date.toGMTString();
        }

        // enter键登录
        document.onkeydown = function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13 && EnterFlag == true) {
                $scope.loginCtrl();
                $scope.$apply();
            }
        };
        $scope.renovate = function () {
            loginService.getCode(function (data) {
                console.log("验证码获取成功");
                $scope.regUrl = URL.createObjectURL(data);
                $scope.$applyAsync();
            });
        };
    });
});
