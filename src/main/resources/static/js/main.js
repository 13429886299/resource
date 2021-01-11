/**
 * Created by tyh on 2019/06/25.
 */
require.config({
    baseUrl: "js",
    paths: {
        "jquery": "lib/jquery/jquery-1.11.3.min",
        "angular": "lib/angular/angular",
        "angularAMD": "lib/angularAMD/angularAMD",
        "router": "lib/angular-ui-router/angular-ui-router",
        "bootstrap": "lib/bootstrap/bootstrap.min",
        "ui-bootstrap": "lib/bootstrap/ui-bootstrap-tpls",
        "slider": "lib/jquery-slider/jquery-ui.min",
        "jquery-easyui": "lib/jquery-easyui-1.4.3/jquery.easyui.min",
        "md5": "lib/jquery-md5/jQuery.md5",
        "io": "lib/socket-io/socket.io-1.3.5",
        "uuid": "lib/uuid/Utility",
        "util": "lib/common/util",
        'ng_file_upload': 'lib/angular-file-upload/ng-file-upload.min',
        'ng_file_upload_shim': 'lib/angular-file-upload/ng-file-upload-shim.min',
        'routerConfig': 'router/routes',
        'routerLoader': 'router/loader',
        'layer': 'lib/layer/dist/layer',
        "jstree": "lib/jstree/jstree",
        "jquery-ztree": "lib/ztree/js/jquery-1.4.4.min",
        "ztree-core": "lib/ztree/js/jquery.ztree.core-3.5",
        "ztree-excheck": "lib/ztree/js/jquery.ztree.excheck-3.5",
        "ztree-exedit": "lib/ztree/js/jquery.ztree.exedit-3.5",
        "ztree": "lib/ztree/js/jquery.ztree.all-3.5"
    },
    shim: {
        "angular": {
            deps: ["jquery"],
            exports: 'angular'
        },
        "angularAMD": ["angular"],
        "router": ["angular"],
        "ui-bootstrap": ["angular"],
        "slider": ["jquery"],
        "jquery-easyui": ["jquery"],
        "bootstrap": ["jquery"],
        "ztree-core": ["jquery-ztree"],
        "ztree-excheck": ["jquery-ztree"],
        "ztree-exedit": ["jquery-ztree"]
    },
    urlArgs: "bust=" + (new Date()).getTime(), //防止读取缓存，调试用
    //启动程序 js/scropts/app.js
    deps: ['app']
});
