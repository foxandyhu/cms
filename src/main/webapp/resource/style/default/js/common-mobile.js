var Cms = {
    config: {
        resServer: 'http://res.servers.com:60'
    }
};
Cms.common = {
    //  图片延时加载
    lazyLoad: function () {
        function init() {
            $("img[original]").each(function () {
                var self = $(this);
                if (self.attr("lazyloadpass") === undefined && self.attr("original") && (!self.attr("src") || (self.attr("src") && self.attr("original") != self.attr("src")))) {
                    if ((self.offset().top < ($(window).height() + $(document).scrollTop())) && (self.offset().left < ($(window).width() + $(document).scrollLeft()))) {
                        self.attr("src", self.attr("original"));
                        self.attr("lazyloadpass", "1");
                        self.animate({opacity: "1"}, 400);
                    }
                }
            });
        }

        try {
            init();
            var itv;
            $(window).scroll(function () {
                clearTimeout(itv);
                itv = setTimeout(init, 200);
            });
            $(window).resize(function () {
                clearTimeout(itv);
                itv = setTimeout(init, 200);
            });
        } catch (e) {
        }
    },
    // 回到顶部
    backTop: function () {
        try {
            $("#goTopBtn").click(function () {
                document.documentElement.scrollTop = document.body.scrollTop = 0;
            });
            $(window).bind("scroll", function () {
                if (document.documentElement.scrollTop > 0 || document.body.scrollTop > 0) {
                    $("#goTopBtn").css("display", 'block');
                } else {
                    $("#goTopBtn").hide();
                }
            });
        } catch (e) {
        }
    },
    login: function () {
        Cms.util.httpGet("/login.html", null, function (response) {
            Cms.dialog.normal("登录", response, null, null, true);
        });
    },
    init: function () {
        this.lazyLoad();
        this.backTop();
    }
}

Cms.util = {
    // js加载css
    loadCss: function (url) {
        $("head").append("<link>");
        var css = $("head").children(":last");
        css.attr({
            rel: "stylesheet",
            type: "text/css",
            href: url
        });
    },
    loadJs: function (url, callback) {
        $.getScript(url, function () {
            callback();
        });
    },
    // js分页
    page: function (elementId, pageNum, totalNum, totalList, callback) {
        $("#" + elementId).paging({
            pageNum: pageNum, // 当前页面
            totalNum: totalNum, // 总页码
            totalList: totalList, // 记录总数量
            callback: function (num) {
                callback(num);
            }
        });
    },
    // Http Post
    httpPost: function (url, params, success, beforeSend, error, complete, contentType) {
        $.ajax({
            url: url, type: "POST", data: params,
            contentType: contentType ? contentType : "application/x-www-form-urlencoded",
            beforeSend: function () {
                return !beforeSend || beforeSend();
            },
            success: function (response) {
                success(response);
            },
            error: function (response) {
                if (error) {
                    error(response);
                } else {
                    Cms.dialog.error(response.responseText);
                }
            },
            complete: function () {
                if (complete) {
                    complete();
                }
            },
        });
    },
    // Http Get
    httpGet: function (url, params, success, beforeSend, error, complete, contentType) {
        $.ajax({
            url: url, type: "GET", data: params,
            contentType: contentType ? contentType : "application/x-www-form-urlencoded",
            beforeSend: function () {
                return !beforeSend || beforeSend();
            },
            success: function (response) {
                success(response);
            },
            error: function (response) {
                if (error) {
                    error(response);
                } else {
                    Cms.dialog.error(response.responseText);
                }
            },
            complete: function () {
                if (complete) {
                    complete();
                }
            },
        });
    }
}

Cms.dialog = {
    init: function (callback) {
        if (!window.dialog) {
            Cms.util.loadJs(Cms.config.resServer + "/style/default/artdialog/dialog.js", function () {
                callback();
            });
        } else {
            callback();
        }
    },
    build: function (id,title, content,time) {
        this.init(function () {
            var d=dialog({
                id: id, fixed: true, title: title, content: content
            });
            d.show();
            if(time){
                setTimeout(function () {
                    d.close().remove();
                }, time*1000);
            }
        });
    },
    normal: function (title, content,time) {
        this.build("normal_dialog",title,content,time);
    },
    update: function (title, content) {
        var d = dialog.getCurrent();
        if (title) {
            d.title(title);
        }
        if (content) {
            d.content(content);
        }
    },
    success: function (content) {
        this.build('tip_dialog','提示', content,  3);
    },
    error: function (content) {
        this.build('tip_dialog','错误', content, 3);
    },
    warning: function (content) {
        this.build('tip_dialog','警告', content, 3);
    },
    close: function () {
        dialog.getCurrent().close();
    }
}

Cms.validate = {
    isEmail: function (value) {
        var pattern = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        return this.isNotNull(value) && value.match(pattern);
    },
    isPhone: function (value) {
        var pattern = /^1[3|4|5|8|7|9][0-9]\d{8}$/;
        return this.isNotNull(value) && value.match(pattern);
    },
    isNotNull: function (value) {
        return value && value.length > 0;
    }
}

$(function () {
    Cms.common.init();
});