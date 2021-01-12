/**
 * Created by xcz on 2016/11/11.
 */
/**资源管理服务 ********************************************************************************************************/
define(['app', 'layer', 'md5', 'jstree', 'uuid', 'service/utilService', 'ng_file_upload', 'ng_file_upload_shim'], function (app, layer) {
    console.log('resmanage service init...');
    app.service('resmanageService', function ($http, loginService, utilService, Upload, $log) {
        var treeData = [];
        var originResourceData = [];
        var curTNodeService = {};
        var oldCardId;
        //中队的资源id集合,目的是 切换中队的图标
        var baseUrl = "/zmlh";
//*************************************************资源树操作模块*************************************
        var status = "add";
        //获取资源和设备树的原始数据
        this.loadTreeData = function (cb) {
            loadtreeData(cb);
        };

        function loadtreeData(cb) {
            $http.get(baseUrl + '/resource/student')
                .then(function (data) {
                    var d = data.data;
                    if (d.code == 200) {
                        console.log("d---" + JSON.stringify(d));
                        //对得到的数据进行排序
                        originResourceData = d.object;
                        console.log("originResourceData" + JSON.stringify(originResourceData));
                        createJsData();
                        // sortData(d);
                        cb("");
                    } else {
                        layer.alert(d.object);
                    }
                })
                .catch(function (e) {
                    layer.alert('加载资源失败');
                });

        }


        function toJsTreeDeviceData(data) {
            var arr = ["node", 'student', 'class'];
            arr.forEach(function (obj) {
                dev_toJsTreeData(data, obj);
            })
        }

        function dev_toJsTreeData(data, type) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].resourceType == type) {
                    if (data[i].parentId == 'rot') {
                        var td = {};
                        td.id = data[i].resourceId;
                        td.text = data[i].resourceName;
                        td.type = data[i].resourceType;
                        td.parent = "#";
                        treeData.push(td);
                    } else {
                        var td = {};
                        td.id = data[i].resourceId;
                        td.text = data[i].resourceName;
                        td.type = data[i].resourceType;
                        td.parent = data[i].parentId;
                        treeData.push(td);
                    }
                }
            }
        }

        function createJsData() {
            updateTree();
        }


        function updateTree() {
            treeData = [];
            //创建资源数数据
            toJsTreeDeviceData(originResourceData);
            //根据创建的数据生成资源数
            createTree('#tree');
        }

        //利用创建的数据treeData生成树
        function createTree(tag) {
            //自定义图标
            $(tag).bind("loaded.jstree", function (e, data) {
                if (treeData.length) {
                    var inst = data.instance;
                    var obj = inst.get_node(e.target.firstChild.firstChild.lastChild);
                    inst.select_node(obj);
                    inst.open_node(obj);
                }

            }).jstree({
                "core": {
                    "animation": 0,
                    "check_callback": function (operation, node, node_parent, node_position, more) {
                        if (node_parent.id != "#") {
                            return true
                        } else {
                            return false
                        }
                    },
                    "multiple": false,
                    "checkbox": {
                        "keep_selected_style": false
                    },
                    "themes": {"stripes": false},
                    'data': treeData
                },
                "types": {
                    "types": {"disabled": {"check_node": false, "uncheck_node": false}},
                    '#': {
                        "max_children": 50,
                        'max_depth': 16,
                        'valid_children': ["node", "class", "student"]
                    },
                    "node": {
                        "icon": "images/resourcetree/node.png",
                        "valid_children": ["class", "student"]
                    },
                    "class": {
                        "icon": "images/resourcetree/depart.png",
                        "valid_children": ["student"]
                    },
                    "student": {
                        "icon": "images/resourcetree/user.png",
                        "valid_children": []
                    }
                },
                //右键菜单
                "contextmenu": {select_node: true, show_at_node: true, items: createContextMenuItems},
                "plugins": [
                    "contextmenu",
                    "search",
                    "types",
                    "wholerow",
                    "dnd"
                ]
            });
        }

        //树节点的右键菜单
        function createContextMenuItems() {
            var removeItem = {
                "remove": {
                    "separator_before": false,
                    "icon": false,
                    "separator_after": false,
                    "label": "删除",
                    "action": treenode_delete
                }
            };
            return removeItem;
        }

        //删除一个树结点
        function treenode_delete() {
            if (!curTNodeService) {
                layer.alert('请先选择一个节点再删除');
                return;
            }
            var id = curTNodeService.id;
            var url = baseUrl;
            switch (curTNodeService.type) {
                case "class":
                    url = url + "/class/";
                    deleteResources(url, id);
                    break;
                case"student":
                    url = url + "/student/";
                    deleteResources(url, id);
                    break;
            }

        }

        function deleteResources(url, id) {
            layer.confirm('确定删除吗', {btn: ['确定', '取消'], title: "提示"}, function () {
                url += id;
                console.log("url:" + url);
                $.ajax({
                    type: 'delete',
                    url: url,
                    success: function (d, s, j) {
                        if (s != 'success') {
                            layer.alert('删除失败');
                            return;
                        }
                        $("").hide();
                        delete_tn();
                        deleteResourceData(id);
                    },
                    error: function (e) {
                        layer.alert('删除失败');
                    }
                });
                layer.close(layer.index);
            });

        }


        function deleteResourceData(id) {
            for (var i = 0; i < originResourceData.length; i++) {
                if (originResourceData[i].resourceId == id) {
                    originResourceData[i] = {};
                }
            }
        }

        // 增加树节点
        function add_tn(type, node) {
            var ref = $('#tree').jstree(true);
            var sel;
            if (!curTNodeService.id) {
                sel = '#';
            } else {
                sel = curTNodeService.id;
            }
            if (type == 'org' || type == 'class') ref.create_node(sel, node);
            else {
                ref.create_node(sel, node);
            }
            // ref.open_node(sel);
        }

        //重新命名树节点
        function rename_tn(name) {
            var ref = $('#tree').jstree(true);
            var sel;
            if (!curTNodeService.id) {
                return;
            } else
                sel = curTNodeService.id;
            ref.rename_node(sel, name);
        }

        //删除选中的树节点
        function delete_tn() {
            var ref = $('#tree').jstree(true);
            var sel;
            if (!curTNodeService.id) {
                return;
            } else
                sel = curTNodeService.id;
            ref.delete_node(sel);
        }

        //资源树选中结点信息绑定，并显示
        this.selectedBinding = function (curTNode, cb) {
            $("body>div.combo-p>div.combo-panel:visible").panel("close");
            curTNodeService = curTNode;
            var resultData = {
                resourceType: "",
                class: {}, student: {}
            };
            if (curTNodeService) {
                var id = curTNodeService.id;
                for (var i = 0; i < originResourceData.length; i++) {
                    var curRes = originResourceData[i];
                    var info = originResourceData[i].resourceDesc;
                    if (id == curRes.resourceId) {
                        status = "modify";
                        resultData.resourceType = curRes.resourceType;
                        switch (curRes.resourceType) {
                            case'class':
                                resultData.class.id = curRes.resourceId;
                                resultData.class.name = curRes.resourceName;
                                resultData.class.pid = curRes.parentId;
                                showInfo('class');
                                break;
                            case "student":
                                resultData.student.id = curRes.resourceId;
                                resultData.student.name = curRes.resourceName;
                                resultData.student.parentId = curRes.parentId;
                                resultData.student.smallName = info.smallName;
                                resultData.student.sex = info.sex;
                                resultData.student.phone = info.phone;
                                resultData.student.school = info.school;
                                resultData.student.levelBig = info.levelBig;
                                resultData.student.levelSmall = info.levelSmall;
                                resultData.student.allHours = info.allHours;
                                resultData.student.used = info.used;
                                resultData.student.remarks = info.remarks;
                                resultData.student.num = info.num;
                                resultData.student.filePath = info.filePath;
                                resultData.student.cardId = info.cardId;
                                resultData.student.endTime = info.endTime;
                                resultData.student.startTime = info.startTime;
                                resultData.student.remain = info.remain;
                                oldCardId = info.num;
                                showInfo('student');
                                break;

                        }
                    }
                }
                cb(resultData);
            }
        };

        this.changeStatus = function (model) {
            status = "add";
            oldCardId = "";
            showInfo(model);
        };
        /*************************************************/
        this.saveClass = function (classData) {
            $http.post(baseUrl + "/class", classData)
                .then(function (d, s, l) {
                    var data = d.data;
                    if (data.result != -1) {
                        if (status != "modify") {
                            originResourceData.push({
                                resourceId: data.id,
                                resourceName: classData.name,
                                parentId: "root",
                                resourceType: "class",
                                resourceDesc: {}
                            });
                            add_tn("class", {id: data.id, text: classData.name, type: "class"});
                            layer.alert("保存成功！")
                        } else {
                            for (var i = 0, length = originResourceData.length; i < length; i++) {
                                if (classData.id = originResourceData[i].resourceId) {
                                    originResourceData[i] = {
                                        resourceId: data.id,
                                        resourceName: classData.name,
                                        parentId: "root",
                                        resourceType: "class",
                                        resourceDesc: {}
                                    }
                                }
                            }
                            rename_tn(classData.name);
                            layer.alert("修改成功！")
                        }

                    }


                })
                .catch(function (e) {
                    layer.alert("操作失败！" + JSON.stringify(e))
                })
        };


        /***********************添加学生**************************/
        this.saveStudent = function (student, file, cb) {
            var obj = cardRepeat(student.num);
            if (obj.flag) {
                layer.alert("会员卡已经被" + obj.name + "使用了");
                return;
            }
            $http.post(baseUrl + "/resource/student", student)
                .then(function (d, s, l) {
                    var data = d.data;
                    oldCardId = student.num;
                    if (data.code == 200) {
                        if (status != "modify") {
                            originResourceData.push({
                                resourceId: data.object,
                                resourceName: student.userName,
                                parentId: student.pid,
                                resourceType: "student",
                                resourceDesc: {
                                    smallName: student.smallName,
                                    sex: student.sex,
                                    phone: student.phone,
                                    school: student.school,
                                    levelBig: student.levelBig,
                                    levelSmall: student.levelSmall,
                                    allHours: student.allHours,
                                    used: student.used,
                                    remarks: student.remarks,
                                    num: student.num,
                                    startTime: student.startTime,
                                    endTime: student.endTime,
                                    cardId: student.cardId,
                                    remain: student.remain
                                }
                            })
                            ;
                            add_tn("student", {id: data.id, text: student.userName, type: "student"});
                            layer.alert("保存成功！")
                        } else {
                            for (var i = 0, length = originResourceData.length; i < length; i++) {
                                if (student.id = originResourceData[i].resourceId) {
                                    originResourceData[i] = {
                                        resourceId: data.id,
                                        resourceName: student.userName,
                                        parentId: student.pid,
                                        resourceType: "student",
                                        resourceDesc: {
                                            smallName: student.smallName,
                                            sex: student.sex,
                                            phone: student.phone,
                                            school: student.school,
                                            levelBig: student.levelBig,
                                            levelSmall: student.levelSmall,
                                            allHours: student.allHours,
                                            used: student.used,
                                            remarks: student.remarks,
                                            num: student.num,
                                            startTime: student.startTime,
                                            endTime: student.endTime,
                                            cardId: student.cardId,
                                            remain: student.remain
                                        }
                                    }
                                }
                            }
                            rename_tn(student.userName);
                            layer.alert("修改成功！")
                        }
                        if (file) {
                            uploadFile(data.id, file);
                        }
                        cb();
                    }
                })
                .catch(function (e) {
                    layer.alert("操作失败！" + e);
                })
        };

        function cardRepeat(cardId) {
            var obj = {flag: false, name: ""};
            if (cardId != oldCardId) {
                for (var i = 0, ii = originResourceData.length; i < ii; i++) {
                    if (cardId == originResourceData[i].resourceDesc.num) {
                        obj.flag = true;
                        obj.name = originResourceData[i].resourceName;
                        break
                    }
                }
            }
            return obj;
        }

        /***************上传图片*************************/
        var ifImport = true;

        function uploadFile(id, file) {
            if (ifImport) {
                ifImport = false;
                Upload.upload({
                    // 服务端接收
                    url: baseUrl + "/student/" + id,
                    method: 'POST',
                    // 上传的同时带的参数
                    file: file
                }).success(function (data, status, headers, config) {
                    $log.info("上传文件成功");
                }).error(function (data, status, headers, config) {
                    $log.info("上传文件失败");
                });
                ifImport = true;
            }
        };


//*************************************************公共操作区***************************************

        function showInfo(mode) {
            var arr = ["#student", "#class"];
            hideResourceDiv(arr);
            switch (mode) {
                case 'student':
                    $('#student').css('display', 'block');
                    break;
                case "class":
                    $("#class").show();
                    break;
            }
        }

        this.hideDiv = function (arr) {
            hideResourceDiv(arr);
        };

        function hideResourceDiv(arr) {
            if (arr && arr.length > 0) {
                for (var i = 0, ii = arr.length; i < ii; i++) {
                    $(arr[i]).hide();
                }
            }
        }

        this.getDict = function (cb) {
            $http.get(baseUrl + "/dict/all/select")
                .then(function (d) {
                    debugger;
                    var data = d.data;
                    if (data.code == 200) {
                        cb(data.object)
                    } else {
                        layer.alert(data.message);
                    }
                })
                .catch(function (e) {
                    layer.alert('获取小等级等级失败！');
                });
        };

        this.getCardId = function (cb) {
            $http.get(baseUrl + "/cardId")
                .then(function (d) {
                    var data = d.data;
                    if (data.code == 200) {
                        cb(JSON.parse(data.message))
                    } else {
                        layer.alert(data.message);
                    }
                })
                .catch(function (e) {
                    layer.alert('活得卡号失败');
                });
        };

    });
});
