define(['app'], function (app) {
    console.log('login service init...');
    app.service('loginService', function ($http) {
        var baseUrl = "/roller/resource";
        return {
            getCode: function (cb) {
                $http({method: 'get', url: '/code/image', responseType: "blob"})
                    .then(function (data) {
                        var blob = new Blob([data.data], {type: "image/jpeg"});
                        cb(blob);
                    })
                    .catch(function (data) {
                    });
            },
            login: function (name, pwd, cb) {
                var data = {name: name, pwd: pwd};
                $http.post(baseUrl + "/login", data)
                    .then(function (value) {
                        cb(value.data)
                    }).catch(function (reason) {
                })
            }
        }
    });
})
