Cms.news = {
    glide: function () {
        glide.layerGlide(true, 'iconBall', 'textBall', 'show_pic', 510, 2, 0.1, 'left');
    },
    commentsSlide: function () {
        var scrtime;
        var $ul = $("#con ul");
        var liFirstHeight = $ul.find("li:first").height();//第一个li的高度
        $ul.css({top: "-" + liFirstHeight - 20 + "px"});//利用css的top属性将第一个li隐藏在列表上方, 因li的上下padding:10px所以要-20
        $("#con").hover(function () {
            $ul.pause();//暂停动画
            clearInterval(scrtime);
        }, function () {
            $ul.resume();//恢复播放动画
            scrtime = setInterval(function scrolllist() {
                //动画形式展现第一个li
                $ul.animate({top: 0 + "px"}, 1500, function () {
                    //动画完成时
                    $ul.find("li:last").prependTo($ul);//将ul的最后一个剪切li插入为ul的第一个li
                    liFirstHeight = $ul.find("li:first").height();//刚插入的li的高度
                    $ul.css({top: "-" + liFirstHeight - 20 + "px"});//利用css的top属性将刚插入的li隐藏在列表上方  因li的上下padding:10px所以要-20
                });
            }, 3300);
        }).trigger("mouseleave");//通过trigger("mouseleave")函数来触发hover事件的第2个函数
    },
    updown: function (articleId) {
        $(".ups,.downs").bind("click", function () {
            var up = $(this).attr("data-type");
            Cms.util.httpGet("/updown-" + articleId + "-" + up + ".html", null, function () {
                var target;
                if (up === "1") {
                    target = $("#ups");
                } else {
                    target = $("#downs");
                }
                var num = parseInt(target.text(), 0);
                target.text(num + 1);
                $(".ups,.downs").unbind();
            }, null, function (response) {
                if (response.status === 401) {
                    Cms.common.login();
                } else {
                    Cms.dialog.error(response.responseText);
                }
            });
        });
    },
    score:function(articleId){
        $(".score li").bind("click", function () {
            var itemId = $(this).attr("data-type");
            Cms.util.httpGet("/score-"+articleId+"-"+itemId+".html",null,function(){
                var target=$("#score-item-"+itemId);
                var num=parseInt(target.text(),0);
                target.text(num + 1);
                $(".score li").unbind();
            },null,function(response){
                if (response.status === 401) {
                    Cms.common.login();
                } else {
                    Cms.dialog.error(response.responseText);
                }
            });
        });
    }
}
