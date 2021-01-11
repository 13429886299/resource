/**
 * Created by tyh on 2019/06/25.
 */
define([], function () {
    return {
        defaultRoute: "/login",
        routes: {
            "login": {
                templateUrl: "views/login.html",
                url: "/login",
                dependencies: ["controller/loginController"],
                allowAnonymous: true
            },
            "main": {
                templateUrl: "views/main.html",
                url: "/main",
                dependencies: ["controller/mainController"],
                allowAnonymous: true
            },
            "main.orgManage": {
                templateUrl: "views/orgManage.html",
                url: "/orgManage",
                dependencies: ["controller/orgManageController"],
                allowAnonymous: true
            },
            "main.serverNodeSetting": {
                templateUrl: "views/serverNodeSetting.html",
                url: "/serverNodeSetting",
                dependencies: ["controller/serverNodeSettingController"],
                allowAnonymous: true
            },
            "main.timing": {
                templateUrl: "views/timing.html",
                url: "/timing",
                dependencies: ["controller/timingController"],
                allowAnonymous: true
            },
            "main.powerReboot": {
                templateUrl: "views/powerReboot.html",
                dependencies: ['controller/powerRebootController'],
                url: "/powerReboot",
                allowAnonymous: true
            },
            "main.importAndExport": {
                templateUrl: "views/importAndExport.html",
                url: "/importAndExport",
                dependencies: ["controller/importAndExportController"],
                allowAnonymous: true
            },
            "main.resetPassword": {
                templateUrl: "views/resetPassword.html",
                url: "/resetPassword",
                dependencies: ["controller/resetPasswordController"],
                allowAnonymous: true
            },
            "main.resourceManage": {
                templateUrl: "views/resmanage.html",
                url: "/resmanage",
                dependencies: ["controller/resmanageController"],
                allowAnonymous: true
            },
            // "main.studentSearch": {
            //     templateUrl: "views/studentSearch.html",
            //     url: "/studentSearch",
            //     dependencies: ["controller/studentSearchController"],
            //     allowAnonymous: true
            // },
            "main.gisMapView": {
                templateUrl: "views/gisMapView.html",
                url: "/gisMapView",
                dependencies: ["controller/gisMapController"],
                allowAnonymous: true
            }
        }
    }
});
