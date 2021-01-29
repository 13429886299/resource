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
            "main.manageSchedule": {
                templateUrl: "views/manageSchedule.html",
                url: "/manageSchedule",
                dependencies: ["controller/manageScheduleController"],
                allowAnonymous: true
            },
            "main.manageScheduleTime": {
                templateUrl: "views/manageScheduleTime.html",
                url: "/manageSchedule",
                dependencies: ["controller/manageScheduleTimeController"],
                allowAnonymous: true
            }

        }
    }
});
