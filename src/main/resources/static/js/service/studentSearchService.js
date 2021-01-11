define(['app', 'layer', 'md5', 'jstree', 'uuid', 'service/utilService'], function (app, layer) {
    app.service('studentSearchService', function ($http) {
        var baseUrl = "/roller/resource";
        return {
            getAllStudent: function (cb) {
                $http.get(baseUrl + '/allStudent')
                    .then(function (data) {
                        var d = data.data;
                        cb(d);
                    })
                    .catch(function (e) {
                        layer.alert('加载资源失败' + e);
                    });
            },
            editStudentLessons: function (cardId, cb) {
                $http.get(baseUrl + '/student/lesson/card/' + cardId)
                    .then(function (data) {
                        var d = data.data;
                        layer.alert(d.message)
                        cb(d.code);
                    })
                    .catch(function (e) {
                        layer.alert('加载资源失败' + e);
                    });
            }
        }
    })
});
