/**
 * Created by dongr on 2017/6/30.
 */
define(['app', "layer"], function (app, layer) {
    app.service('resetPasswordService', function ($http) {
        return {
            editPassword: function (data, cb) {
                $http({
                    method: 'post',
                    url: '/zmlh/password/update',
                    data: {"user": data.userName, "pwd": data.newPassword}
                }).then(function (data, status, headers, config) {
                    var d = data.data;
                    console.log("返回值是：" + JSON.stringify(d));
                    if (d.code == 200) {
                        cb(true);
                    } else {
                        cb(false);
                    }
                }).catch(function (data) {
                    cb(false);
                });
            },
            resetAdminPassword: function (userId, login, cb) {
                $http({
                    method: 'put',
                    url: '/zmlh/password/reset/' + userId + '/' + login,
                }).then(function (data, status, headers, config) {
                    cb(data.data);
                }).catch(function (data) {
                    cb(false);
                });
            }
        }
    });
});
