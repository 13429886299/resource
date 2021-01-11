/**
 * Created by zm on 2016/12/6.
 */
define(['angular', 'app', 'jquery'], function (angular, app, $) {
    app.service('layoutService', function () {
        //屏幕高度布局
        return {
            //上标题的高度
            upTitleHeight: function () {
                var allHeight = document.body.clientHeight;
                var uptitleH = allHeight * 0.06;
                $("#titlebar").css('height', uptitleH);
            },
            //下标题的高度
            downTitleHeight: function () {
                var allHeight = document.body.clientHeight;
                var downtitleH = allHeight * 0.04;
                $("#statusbar").css("height", downtitleH);
            },
            //中间body的高度
            mainBodyHeight: function () {
                var allHeight = document.body.clientHeight;
                var mainBodytitleH = allHeight * 0.9;
                $("#mainbody").css("height", mainBodytitleH);
                $("#leftmenu").css("height", mainBodytitleH);
                $("#accord").css("height", allHeight * 0.85);
            },
            workcontentHeight: function () {
                var allHeight = document.body.clientHeight;
                var treeAreaHeight = allHeight * 0.94;
                $("#tree-area").css("height", treeAreaHeight);
                $("#work-area").css("height", treeAreaHeight);
                $("#work-area").css("top", allHeight * 0.11);
                $(".tree-area").css("height", treeAreaHeight);
                $(".work-area").css("height", treeAreaHeight);
                $(".work-area").css("top", allHeight * 0.037);
                $("#nodeInfo-in").css("height", treeAreaHeight);
                $("#authority-body").css("height", treeAreaHeight);
                $("#server_body").css("height", treeAreaHeight);
                $(".content_body").css("height", treeAreaHeight);
                $("#jstree_area").css("height", treeAreaHeight - 25);
                $(".jstree_area").css("height", treeAreaHeight - 56);
                $("#ALLScreen").css("height", treeAreaHeight);
                $("#content").css("height", treeAreaHeight + 25);

                $("#mapTree").css("height", treeAreaHeight - 44);
                $("#table-responsive").css("height", treeAreaHeight);
                $(".table-responsive").css("height", treeAreaHeight * 0.6);
                $("#map").css("height", treeAreaHeight - 10);
            }
        };
    });
});
