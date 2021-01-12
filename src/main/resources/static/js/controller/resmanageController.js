/**
 * Created by xcz on 2016/11/11.
 */
/**资源管理控制器 ********************************************************************************************************/
define(['angular', 'app', 'layer', 'service/resmanageService', 'service/layoutService', 'service/utilService',], function (angular, app, layer) {
    console.log('resmanage controller init...');
    app.controller('resmanageController', function ($scope, resmanageService, layoutService, utilService, $log) {
        //页面高度自适应布局
        layoutService.upTitleHeight();
        layoutService.downTitleHeight();
        layoutService.mainBodyHeight();
        layoutService.workcontentHeight();
        window.onresize = function () {
            layoutService.upTitleHeight();
            layoutService.downTitleHeight();
            layoutService.mainBodyHeight();
            layoutService.workcontentHeight();
        };
        var curTNode = {};
        init();
        //默认隐藏PC机，摄像机界面
        $scope.BigLevels = [];
        $scope.SmallLevels = [];
        $scope.Sexs = [{id: "man", name: "男"}, {id: "woman", name: "女"}];

        function init() {
            //开始获取大等级
            resmanageService.getBigLevel(function (data) {
                if (data && data.length > 0) {
                    for (var i = 0, length = data.length; i < length; i++) {
                        $scope.BigLevels.push({id: data[i].dictKey, name: data[i].dictValue})
                    }

                }
            });
            //开始获取小等级
            resmanageService.getSmallLevel(function (data) {
                if (data && data.length > 0) {
                    for (var i = 0, length = data.length; i < length; i++) {
                        $scope.SmallLevels.push({id: data[i].dictKey, name: data[i].dictValue})
                    }
                }
            });
            //加载树数据
            resmanageService.loadTreeData(function (data) {
                $('#tree').bind('select_node.jstree', function (e) {
                    var ref = $('#tree').jstree(true),
                        sel = ref.get_selected(true);
                    if (!(sel[0])) return;
                    curTNode = {id: sel[0].id, text: sel[0].text, type: sel[0].type};
                    resmanageService.selectedBinding(curTNode, function (resultData) {
                        switch (resultData.resourceType) {

                        }
                    });
                    $scope.$apply();
                });
            });
        }

        $scope.student = {
            id: "",
            pid: "",
            userName: "",
            smallName: "",
            sex: "",
            phone: "",
            school: "",
            levelBig: "",
            levelSmall: "",
            allHours: "",
            used: "",
            remarks: ""
        };

        $scope.saveStudent = function () {
            if (!$scope.student.userName) {
                layer.alert("学生姓名不能为空！");
                return;
            }
            if ($scope.student.userName.length > 8) {
                layer.alert("学生姓名长度不能大于8位！");
                return;
            }
            if ($scope.student.smallName && $scope.student.smallName.length > 8) {
                layer.alert("学生别名长度不能大于8位！");
                return;
            }
            if (!$scope.student.phone) {
                layer.alert("学生电话不能为空！");
                return;
            }
            if (!utilService.isPhotoNumber($scope.student.phone) || $scope.student.phone.length > 11) {
                layer.alert("学生电话号码输入格式不正确！");
                return;
            }
            if (!$scope.student.levelBig) {
                layer.alert("学生大等级不能为空！");
                return;
            }
            if (!$scope.student.levelSmall) {
                layer.alert("学生小等级不能为空！");
                return;
            }
            if (!$scope.student.allHours) {
                layer.alert("学生总课程不能为空！");
                return;
            }
            if (!utilService.isNumber($scope.student.allHours)) {
                layer.alert("学生总课程为正整数！");
                return;
            }
            if (!$scope.student.used) {
                layer.alert("学生已使用课程不能为空！");
                return;
            }
            if (!utilService.isNumber($scope.student.used)) {
                layer.alert("学生已使用课程为正整数！");
                return;
            }
            if ($scope.student.remarks && $scope.student.remarks.length > 64) {
                layer.alert("学生备注长度不能超过于64位！");
                return;
            }
            resmanageService.saveStudent($scope.student);

        };
        $scope.clearStudent = function () {
            for (var index in $scope.student) {
                $scope.student[index] = "";
            }
        }

    })
});
