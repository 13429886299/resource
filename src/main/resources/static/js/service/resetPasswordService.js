/**
 * Created by dongr on 2017/6/30.
 */
define(['app', "layer"], function (app, layer) {
    app.service('resetPasswordService', function ($http) {
        var baseUrl = "/roller/resource";
        return {
            editPassword: function (data, cb) {
                $http({
                    method: 'post',
                    url: baseUrl + '/login/editpassword',
                    data: data
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
            resetAdminPassword: function (data, cb) {
                $http({
                    method: 'post',
                    url: '/fxrest/v5/geo/resources/authentication/login/resetpassword',
                    data: data
                }).then(function (data, status, headers, config) {
                    // console.log(data+"--------------------"+status);
                    cb(data.data);
                }).catch(function (data) {
                    cb(false);
                });
            }
        }
    });
});
