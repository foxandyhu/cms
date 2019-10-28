var guestBookPageNo = 0;
Cms.guestbook = {
    // 加载留言
    load: function () {
        Cms.util.httpGet("/guestbook/load.html?pageNo=" + (++guestBookPageNo), null, function (response) {
            if (response) {
                $("#guestbooksContainer").append(response);
            } else {
                $("#moreGuestBooks").text("没有了");
            }
        });
    },
    // 发表留言
    post: function () {
        Cms.util.httpPost("/guestbook/post.html", $("#guestbookForm").serialize(), function () {
            $("#guestbookCaptcha").click();$("#guestbookForm")[0].reset();
            Cms.dialog.success("留言成功我们会尽快给您回复!");
        }, function () {
            var email = $(".message_email").val();
            var phone = $(".message_phone").val();
            if (Cms.validate.isNotNull(email)) {
                if (!Cms.validate.isEmail(email)) {
                    Cms.dialog.warning("邮箱格式不正确!");
                    $(".message_email").focus();
                    return false;
                }
            }
            if (Cms.validate.isNotNull(phone)) {
                if (!Cms.validate.isPhone(phone)) {
                    Cms.dialog.warning("手机号码格式不正确!");
                    $(".message_phone").focus();
                    return false;
                }
            }
            if (!email && !phone) {
                Cms.dialog.warning("邮箱或手机号码必填一项!");
                $(".message_email").focus();
                return false;
            }
            if (!Cms.validate.isNotNull($(".message_title").val())) {
                Cms.dialog.warning("标题不能为空!");
                $(".message_title").focus();
                return false;
            }
            if (!Cms.validate.isNotNull($(".message_txt").val())) {
                Cms.dialog.warning("内容不能为空!");
                $(".message_txt").focus();
                return false;
            }
            if (!Cms.validate.isNotNull($(".message_code").val())) {
                Cms.dialog.warning("验证码不能为空!");
                $(".message_code").focus();
                return false;
            }
            return true;
        }, function (response) {
            $("#guestbookCaptcha").click();
            Cms.dialog.error(response.responseText);
        });
    }
}