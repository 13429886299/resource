/**
 * Created by dongr on 2017/6/30.
 */
define(['angular', 'app', 'layer', 'jquery', 'md5', 'service/layoutService', 'service/resetPasswordService'], function (angular, app, layer, $) {
    app.controller('resetPasswordController', function ($scope, $state, layoutService, resetPasswordService) {
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
        var userName = getCookie("userName");
        if (userName == "superadmin") {
            $("#password_div").hide();
            $("#reset_password_div").show();
        } else {
            $("#reset_password_div").hide();
            $("#password_div").show();
        }

        $scope.passwordInfo = {
            userName: "",
            oldPassword: "",
            newPassword: "",
            newPasswordAgain: ""
        };
        //修改密码确认框
        $scope.editPassword = function () {
            if (testInput()) {
                $("#edit_password_confirm_div").show();
                // $("#edit_password_exit_div").show();
            }
        }
        $scope.editConfirm = function () {

            $("#edit_password_confirm_div").hide();
            // $("#edit_password_exit_div").hide();

            editAdminPassword();
        }
        $scope.editCancel = function () {
            $("#edit_password_confirm_div").hide();
            $("#edit_password_exit_div").hide();
        };

        function testInput() {
            var oldPsw = $.md5($scope.passwordInfo.oldPassword, 32);
            var password = getCookie("password");
            if (!$scope.passwordInfo.oldPassword) {
                layer.alert("旧密码不能为空!");
                return false;
            }
            if (oldPsw != password) {
                layer.alert("旧密码不正确!");
                return false;
            }
            if (!$scope.passwordInfo.newPassword) {
                layer.alert("新密码不能为空!");
                return false;
            }
            if ($scope.passwordInfo.newPassword.length < 6) {
                layer.alert("密码不能小于六位数!");
                return false;
            }
            if ($scope.passwordInfo.newPassword.length > 16) {
                layer.alert("密码不能大于16位数!");
                return false;
            }
            if ($scope.passwordInfo.newPasswordAgain != $scope.passwordInfo.newPassword) {
                layer.alert("两次输入的密码不一致!");
                return false;
            }
            $scope.passwordInfo.newPassword = $.md5($scope.passwordInfo.newPassword, 32);
            $scope.passwordInfo.newPasswordAgain = $.md5($scope.passwordInfo.newPasswordAgain, 32);
            $scope.passwordInfo.userName = getCookie("userName");
            $scope.passwordInfo.oldPassword = oldPsw;
            if (!$scope.passwordInfo.userName) {
                layer.alert("登录超时,请重新登录!");
                delCookie("userName");
                delCookie("password");
                $state.go("login");
                return false;
            }
            return true;
        }

        function editAdminPassword() {
            //修改密码
            resetPasswordService.editPassword($scope.passwordInfo, function (result) {
                if (result) {
                    layer.alert("密码修改成功,请重新登录!");
                    delCookie("userName");
                    delCookie("password");
                    $state.go("login");
                } else {
                    layer.alert("密码修改失败!");
                }
            });

        };


        //重置密码确认框
        $scope.resetAdminPassword = function () {
            var psw1 = $.md5("123456", 32);
            var psw2 = $.md5("superadmin", 32);
            console.log("123456" + "=" + psw1 + "----------superadmin=" + psw2);
            $("#reset_password_confirm_div").show();
            $("#reset_password_exit_div").show();
        }
        //确认退出
        $scope.resetConfirm = function () {
            $("#reset_password_confirm_div").hide();
            $("#reset_password_exit_div").hide();
            resetPassword();
        };
        //取消退出
        $scope.resetCancel = function () {
            $("#reset_password_confirm_div").hide();
            $("#reset_password_exit_div").hide();
        };

        function resetPassword() {
            var userName = getCookie("userName");
            if (!userName) {
                layer.alert("登录超时,请重新登录!");
                delCookie("userName");
                delCookie("password");
                $state.go("login");
                return;
            }
            var data = {"userName": "admin", "newPassword": $.md5("123456", 32)};
            //修改密码
            resetPasswordService.resetAdminPassword(data, function (result) {
                if (result) {
                    layer.alert("初始化管理员密码成功!");
                } else {
                    layer.alert("初始化管理员密码失败!");
                }
            });

        }

        //获取cookies
        function getCookie(name) {
            console.log("cookie----" + JSON.stringify(document.cookie));
            var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg)) {
                return arr[2];
            } else {
                return null;
            }
        }

        //删除cookies
        function delCookie(name) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = getCookie(name);
            if (cval != null)
                document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
        }
    });
});

