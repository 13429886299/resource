/**
 * Created by xcz on 2016/11/07.
 */
define(['angular', 'routerConfig', 'routerLoader', 'angularAMD', 'router', 'ui-bootstrap', 'jquery-easyui', 'bootstrap', 'util'],
    function (angular, config, loader, angularAMD) {
    var app = angular.module('myApp', ['ui.router', 'ui.bootstrap']);
    /**
     * 配置路由
     */
    app.config(function ($stateProvider, $urlRouterProvider) {
        if (config.routes != undefined) {
            angular.forEach(config.routes, function (route, path) {
                $stateProvider.state(path, {
                    templateUrl: route.templateUrl,
                    url: route.url,
                    resolve: loader(route.dependencies),
                    //allowAnonymous: route.allowAnonymous
                });
            });
        }
        // 默认路由
        if (config.defaultRoute != undefined) {
            $urlRouterProvider.when("", config.defaultRoute);
        }
    });
    /**
     * 配置国际化
     */
    // app.config(function ($translateProvider) {
    //     $translateProvider.translations('en', i18n_en);
    //     $translateProvider.translations('zh', i18n_zh);
    //     //默认语言
    //     $translateProvider.preferredLanguage('zh');
    //     $translateProvider.useSanitizeValueStrategy('escaped');
    // });
    /**
     * 清除IE11下get请求缓存
     */
    app.config(['$httpProvider', function ($httpProvider) {
        //initialize get if not there
        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};
        }
        //disable IE ajax request caching
        $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
        // extra
        $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
        $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    }]);
    /**
     * 运行程序初始化状态
     */
    app.run(function ($rootScope) {
        $rootScope.status = {
            user: {}
        };

    });
    return angularAMD.bootstrap(app);
});