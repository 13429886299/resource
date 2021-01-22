/**
 * Created by xcz on 2016/11/7.
 */
/**主界面控制器 ********************************************************************************************************/
define(['angular', 'app', 'layer', 'jquery', 'service/mainService', 'service/layoutService', 'service/loginService', 'jquery-easyui'], function (angular, app, layer, $) {
    console.log('main controller init...');
    app.controller('mainController', function ($scope, $rootScope, $state, mainService, layoutService, loginService) {
        $scope.title_userName = getCookie("userName");
        if (!$scope.title_userName) {
            $state.go('login');
        }
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
        $scope.passwordInfo = {
            userName: "",
            oldPassword: "",
            newPassword: "",
            newPasswordAgain: ""
        };
        $scope.roleid = getCookie("roleId");

        //获取cookies
        function getCookie(name) {
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

        $scope.tbExit = function () {
            layer.confirm("确认退出？", {btn: ['确定', '取消'], title: "提示"}, function (index) {
                delCookie("userName");
                $state.go("login");
                layer.close(index);
                layer.close(layer.index);
            });
        };

        /***********************************************高级配置管理************************************************/
        $scope.password = "";
        $scope.seniorConfigUi = "";


        document.onkeydown = function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13 && EnterFlag == true) {
                return false;
            }
        };
        /***********************************************************************************************************/
        var idArr = ['orgManage', 'serverNodeSetting', 'mainMenuNode', 'resourceManage', 'timing', "addUser", 'powerReboot', 'importAndExport', 'resetPassword'];
        $scope.tabSwitch = function (id) {
            idArr.forEach(function (obj) {
                if (id == obj) {
                    $('#' + obj).addClass("active");
                } else {
                    $('#' + obj).removeClass("active");
                }
            });
        };


        $scope.changePassword = function () {
            init();
            showChangePassword();
        };

        function init() {
            $scope.passwordInfo.userName = $scope.title_userName;
            $scope.passwordInfo.oldPassword = "";
            $scope.passwordInfo.newPassword = "";
            $scope.passwordInfo.newPasswordAgain = "";
        }

        function showChangePassword() {
            layer.open({
                type: 1,
                title: "修改密码",
                color: "#5FB878",
                area: ["240px", "220px"],
                content: $("#change_password")
                , btn: ['保存', '取消']
                , yes: function (index, layero) {
                    if ($scope.passwordInfo.newPassword != $scope.passwordInfo.newPasswordAgain) {
                        layer.alert("新密码和校验密码不相同！");
                        return;
                    }
                    if ($scope.passwordInfo.newPassword == $scope.passwordInfo.oldPassword) {
                        layer.alert("新密码不允许与原密码相同！");
                        return;
                    }
                    loginService.changePassword($scope.passwordInfo, function (data) {
                        if (data == "success") {
                            //设置cookie
                            layer.alert("修改密码成功，请重新登录！！");
                            $state.go('login');
                        } else {
                            layer.alert("修改密码失败！！");
                        }
                    });
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮【按钮二】的回调
                }
                , cancel: function () {
                    //右上角关闭回调
                }
            });
        }
    })
});
