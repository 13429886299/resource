define(['app'], function (app) {
    console.log('login service init...');
    app.service('loginService', function ($http) {
        var baseUrl = "/zmlh";
        return {
            login: function (name, pwd, cb) {
                var data = {user: name, pwd: pwd};
                $http.post(baseUrl + "/login", data)
                    .then(function (value) {
                        cb(value.data)
                    }).catch(function (reason) {
                })
            }
        }
    });
})
