define(['angular', 'app', 'layer', 'service/studentSearchService', 'service/resmanageService', 'service/utilService'], function (angular, app, layer) {
    console.log('studentSearch controller init...');
    app.controller('studentSearchController', function ($scope, $rootScope, studentSearchService, resmanageService, $log) {
        $scope.search = {studentName: "", studentPhone: "", studentCardNum: ""};
        $scope.Students = [];
        $scope.AllStudents = [];
        $scope.resourceData = [];
        $scope.BigLevels = [];
        $scope.SmallLevels = [];
        $scope.Sexs = [{id: "man", name: "男"}, {id: "woman", name: "女"}];
        $scope.Titles = [{titleName: "序号"}, {titleName: "姓名"}, {titleName: "班级"}, {titleName: "电话"}, {titleName: "会员卡"}, {titleName: "操作"}];
        $scope.initStudentSearch = function () {
            $rootScope.page = "studentSearch";
            // $scope.disConnect();
            layer.closeAll();
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
            $scope.getAllStudent();
        };

        $scope.getAllStudent = function () {
            studentSearchService.getAllStudent(function (data) {
                if (data) {
                    $scope.resourceData = data;
                    var length = data.length;
                    for (var i = 0; i < length; i++) {
                        if (data[i].resourceType == "student") {
                            var className = "";
                            for (var j = 0; j < length; j++) {
                                if (data[i].parentId == data[j].resourceId) {
                                    className = data[j].resourceName;
                                    break;
                                }
                            }
                            $scope.AllStudents.push({
                                resourceId: data[i].resourceId,
                                studentName: data[i].resourceName,
                                studentClass: className,
                                studentPhone: data[i].resourceDesc.phone,
                                studentCardNum: data[i].resourceDesc.cardId,
                                studentCardId: data[i].resourceDesc.num,
                                smallName: data[i].resourceDesc.smallName,
                                endTime: data[i].resourceDesc.endTime
                            })
                        }
                    }
                }
                $scope.pageSplit();
            });
        };
        $scope.student = {
            userName: "",
            smallName: "",
            sex: "",
            phone: "",
            school: "",
            levelBig: "",
            levelSmall: "",
            allHours: "",
            used: "",
            remarks: "",
            num: "",
            filePath: ""
        };

        $("#studentDetail").hide();
        $scope.detail = function (index) {
            layer.open({
                type: 1,
                title: "学生详细信息",
                color: "#5FB878",
                area: ["780", "260"],
                content: $("#studentDetail"),
                cancel: function () {
                    $scope.clear();
                }
            });
            if ($scope.resourceData) {
                for (var i = 0, ii = $scope.resourceData.length; i < ii; i++) {
                    if ($scope.resourceData[i].resourceId == $scope.Students[index].resourceId) {
                        var info = $scope.resourceData[i].resourceDesc;
                        $scope.student = {
                            userName: $scope.resourceData[i].resourceName,
                            smallName: info.smallName,
                            sex: info.sex,
                            phone: info.phone,
                            school: info.school,
                            levelBig: info.levelBig,
                            levelSmall: info.levelSmall,
                            allHours: info.allHours,
                            used: info.used,
                            remarks: info.remarks,
                            num: info.num,
                            filePath: "",
                            endTime: info.endTime,
                            startTime: info.startTime,
                            remain: info.remain,
                            cardId: info.cardId
                        };
                        // $scope.editLesson($scope.student.num);
                        if (info.filePath) {
                            $scope.student.filePath = "http://" + window.location.host + "/image/file/" + info.filePath;
                        }
                    }
                }
            }


        };

        /***************分页处理*************************/
        $scope.pageSize = 1;//默认为1
        $scope.num = 1;//默认跳转为1
        $scope.page = 1;//默认首页
        $scope.pageSplit = function () {
            $scope.pageSize = Math.ceil($scope.AllStudents.length / 10);
            $scope.splice(0, $scope.AllStudents);
            console.log("表格数据是：" + JSON.stringify($scope.Students));
        };

        $scope.selectPage = function (index) {
            $scope.page = index;
            $scope.splice((index - 1) * 10, $scope.AllStudents);
        };
        $scope.splice = function (index, arr) {
            $scope.Students = [];
            if (arr) {
                for (var i = index, ii = arr.length; i < ii; i++) {
                    if ($scope.Students.length < 10) {
                        $scope.Students.push(arr[i]);
                    }
                }
            }
        };
        $scope.previous = function () {
            if (1 == $scope.page) {
                return;
            }
            $scope.page = $scope.page - 1;
            $scope.splice(($scope.page - 1) * 10, $scope.AllStudents);
        };
        $scope.next = function () {
            if ($scope.pageSize == $scope.page) {
                return;
            }
            $scope.page = $scope.page + 1;
            $scope.splice(($scope.page - 1) * 10, $scope.AllStudents);
        };


        /*******************查询******************************/
        $scope.clear = function () {
            for (var index in $scope.student) {
                $scope.student[index] = "";
            }
        };

        $scope.search = function () {
            if ($scope.AllStudents) {
                $scope.Students = [];
                for (var i = 0, ii = $scope.AllStudents.length; i < ii; i++) {
                    var condition = true;
                    var resourceName = false, smallName = false, phone = false, num = false;
                    if ($scope.search.studentName) {
                        resourceName = $scope.AllStudents[i].studentName == $scope.search.studentName;
                        condition = condition && resourceName;
                    }
                    if ($scope.search.smallName) {
                        smallName = $scope.AllStudents[i].smallName == $scope.search.studentName;
                        condition = condition && smallName;
                    }
                    if ($scope.search.studentPhone) {
                        phone = $scope.AllStudents[i].studentPhone == $scope.search.studentPhone;
                        condition = condition && phone;
                    }
                    if ($scope.search.studentCardNum) {
                        num = $scope.AllStudents[i].studentCardNum == $scope.search.studentCardNum;
                        condition = condition && num;
                    }
                    if (condition) {
                        $scope.Students.push($scope.AllStudents[i]);
                    }
                }
            }
        };

        $scope.clearSearch = function () {
            $scope.pageSplit();
            for (var index in $scope.search) {
                $scope.search[index] = "";
            }
        };
        /***********读卡减课程**************************/
        var editLesson = true;
        $scope.editLesson = function (cardId) {
            $log.info("上课的学生是：" + cardId);
            var studentName = $scope.getStudentName(cardId);
            var endTime = $scope.getStudentTime(cardId);
            if (studentName == undefined || studentName == "" || !endTime) {
                layer.alert("会员卡不存在或者已经过期");
                return;
            }
            if (editLesson) {
                layer.open({
                    type: 1,//Layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）,
                    title: '确定是' + studentName + '上课？',   //标题
                    area: ['300px', '125px'],   //宽高
                    color: "#5FB878",
                    content: $("#studentCard"),//支持获取DOM元素
                    btn: ['确定', '取消'], //按钮组
                    scrollbar: false,//屏蔽浏览器滚动条
                    yes: function (index) {//layer.msg('yes');    //点击确定回调
                        layer.close(index);
                        studentSearchService.editStudentLessons(cardId, function (data) {
                            if (data == 200) {
                                $scope.getStudentInfo(cardId);
                                editLesson = true;
                            }
                        });
                    },
                    btn2: function (index) {//layer.alert('aaa',{title:'msg title'});  ////点击取消回调
                        layer.close(index);
                    },
                    success: function (layero) {
                        layero.find('.layui-layer-btn').css('text-align', 'center')
                    }
                });
            }

            editLesson = false;
        };
        $scope.getStudentName = function (cardId) {
            if ($scope.AllStudents.length > 0) {
                for (var i = 0, ii = $scope.AllStudents.length; i < ii; i++) {
                    if ($scope.AllStudents[i].studentCardId == cardId || $scope.AllStudents[i].studentCardNum == cardId) {
                        return $scope.AllStudents[i].studentName;
                    }
                }
            }
            return "";
        };
        $scope.getStudentTime = function (cardId) {
            if ($scope.AllStudents.length > 0) {
                for (var i = 0, ii = $scope.AllStudents.length; i < ii; i++) {
                    if ($scope.AllStudents[i].studentCardId == cardId || $scope.AllStudents[i].studentCardNum == cardId) {
                        var endTime = new Date($scope.AllStudents[i].endTime);
                        var todayTime = new Date();
                        if (endTime < todayTime) {
                            return false;
                        }
                    }
                }
            }
            return true;
        };
        $scope.getStudentInfo = function (cardId) {
            if ($scope.resourceData.length > 0) {
                for (var i = 0, ii = $scope.resourceData.length; i < ii; i++) {
                    if ($scope.resourceData[i].resourceDesc.num == cardId) {
                        $scope.resourceData[i].resourceDesc.remain = $scope.resourceData[i].resourceDesc.remain - 1;
                        $scope.student.remain = $scope.resourceData[i].resourceDesc.remain;
                        $scope.resourceData[i].resourceDesc.used = $scope.resourceData[i].resourceDesc.used + 1;
                        $scope.student.used = $scope.resourceData[i].resourceDesc.used;
                    }
                }
            }
            $scope.pageSplit();
        };
        /**************************上课***************************************/
        var isOpenCom = false, icdev = -1, hasCard = false, gl_fFailedShow = 0, gl_wantFunc = 0;
        var obj = embed_reader.getOBJ(READER_TYPE._reader_type_contactLess);
        $scope.connect = function () {
            try {
                $log.info("开始连接websocket:" + isOpenCom);
                if (isOpenCom == false)          //if reader link failed
                {
                    $scope.startClass = 'stop';
                    obj.initialcom(100, 115200);
                }
            } catch (e) {
                $scope.startClass = 'start';
                $log.info(e.message);
            }
        };
        $scope.disConnect = function () {
            $scope.startClass = 'start';
            obj.exit(icdev);
            isOpenCom = false; //Set unlink status
        };

        $scope.findCard = function () {
            obj.findcardStr(icdev, 0);//1);     //1: multy card mode
            gl_wantFunc = 0;
        };
        $scope.startClass = 'start';
        $scope.getResult = function () {
            $scope.connect();
            obj.onResult(function (rData) {
                switch (rData.FunctionID) {
                    case FUNCIDS._fid_adaptReader:
                        $scope.connect();
                        break;
                    case FUNCIDS._fid_initialcom: {
                        var rel = rData.RePara_Int;
                        if (0 == rel) {
                            var hdev = parseInt(rData.RePara_Str);
                            if (hdev != -1) {
                                icdev = hdev;
                                if (isOpenCom == true)
                                    break;
                                isOpenCom = true;             //Set reader link status
                                gl_fFailedShow = 0; //reset the flag whether show fail message or not
                                $log.info("Link reader ok");
                                obj.config_card(icdev, 0x41);//config to typeA
                            } else {
                                if (0 == gl_fFailedShow) {
                                    gl_fFailedShow = 1;//set the flag whether show fail message or not
                                    $log.info("Link reader error");
                                    isOpenCom = false;
                                }
                                setInterval($scope.connect(), 3000);
                            }
                        } else {
                            $log.info("Object load error");
                        }
                    }
                        break;
                    case FUNCIDS._fid_exit:
                        break;
                    case FUNCIDS._fid_beep:
                        $scope.findCard();
                        break;
                    case FUNCIDS._fid_halt:
                        obj.beep(icdev, 10);
                        break;
                    case FUNCIDS._fid_findCardStr: {
                        var strcard = rData.RePara_Str;
                        if (strcard != "") {
                            hasCard = true;
                            if (editLesson) {
                                $scope.editLesson(strcard);
                                editLesson = true;
                            }

                            obj.halt(icdev);
                        } else {
                            if (0 == gl_fFailedShow) {
                                gl_fFailedShow = 1;//set the flag whether show fail message or not
                            }

                            hasCard = false;        //Set no card status
                            $scope.findCard();
                        }
                    }
                        break;
                    case FUNCIDS._fid_config_card: {
                        $scope.findCard();
                    }
                        break;

                }

            });
            setInterval($scope.connect(), 3000);
        };
    })
});
