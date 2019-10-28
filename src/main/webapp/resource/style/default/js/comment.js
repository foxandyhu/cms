Cms.comment = {
    //显示回复框
    showCmtReply: function (id) {
        var element = $("#reply" + id);
        element.toggle();
    },
    // 加载评论
    load: function (articleId, pageNo) {
        Cms.util.httpGet('/comment/article-' + articleId + ".html?pageNo=" + pageNo,null,function (response) {
            if (response) {
                $("#commentsContainer").append(response);
            } else {
                $("#moreComments").text("没有了");
            }
        });
    },
    // 回复评论
    reply: function (articleId, parentId,content) {
        Cms.util.httpPost("/comment/reply.html",{articleId: articleId, parentId: parentId,content: content},function(){
            Cms.dialog.success("发表成功,等待审核!");
            $(".reply").hide();
            $("textarea").val("");
        },function(){
            if(!content){
                Cms.dialog.warning("请输入评论内容!");
                return false;
            }
            return true;
        },function(response){
            if(response.status===401){
                Cms.common.login();
            }else{
            Cms.dialog.error(response.responseText);
            }
        });
    },
    // 发表评论
    post: function (articleId,content,captcha) {
        Cms.util.httpPost("/comment/post.html",{articleId: articleId, parentId: 0,captcha:captcha, content: content},function(){
            Cms.dialog.success("发表成功,等待审核!");
            $("textarea").val("");
            $(".captcha").val('');
            $('#captchaImg').click();
        },function(){
            if(!content){
                Cms.dialog.warning("请输入评论内容!");
                return false;
            }
            if(!captcha){
                Cms.dialog.warning("请输入验证码!");
                return false;
            }
            return true;
        },function (response) {
            if(response.status===401){
                Cms.common.login();
            }else{
                Cms.dialog.error(response.responseText);
            }
            $("#captchaImg").click();
        });
    },
    //顶踩评论
    updown:function(commentId,isUp){
        Cms.util.httpGet("/comment/updown-"+commentId+"-"+isUp+".html",null,function(){
            var count=parseInt($("#updown"+commentId).text(),0);
            $("#updown"+commentId).text(count+1);
            var text_box = $("#add-num"+commentId);
            text_box.show().html("<em class='add-animation'>+1</em>");
            $(".add-animation").addClass("hover");
        },null,function(response){
            if(response.status===401){
                Cms.common.login();
            }else{
                Cms.dialog.error(response.responseText);
            }
        });
    }
}