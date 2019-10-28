Cms.login = {
    init: function () {
        $(".qiehuan").click(function () {
            $(this).parents(".login").hide().siblings().show();
        });
        $(".login-tab li").click(function () {
                $(this).addClass("login-on").siblings().removeClass("login-on");
                $(".login-style").eq($(this).index()).show().siblings().hide();
            }
        )
        if (window.location.href.indexOf("op=register") > 0) {
            $(".login-tab li")[2].click();
        }
    },
    tip: function (msg) {
        $(".tishi").show().text(msg);
    },
    validateNormal: function () {
        if ($.trim($("#txtUser").val()).length < 1) {
            Cms.login.tip("请输入用户名!");
            return false;
        }
        if ($.trim($("#txtPwd").val()).length < 1) {
            Cms.login.tip("请输入密码!");
            return false;
        }
        if ($("#txtCaptcha").length > 0) {
            if ($.trim($("#txtCaptcha").val()).length < 1) {
                Cms.login.tip("请输入验证码!");
                return false;
            }
        }
        return true;
    },
    validateDynamic: function () {
        if ($.trim($("#txtPhone").val()) < 1) {
            Cms.login.tip("请输入手机号码!");
            return false;
        }
        if ($.trim($("#txtDynamicPwd").val()).length < 1) {
            Cms.login.tip("请输入动态密码!");
            return false;
        }
        if ($("#txtCaptcha").length > 0) {
            if ($.trim($("#txtCaptcha").val()).length < 1) {
                Cms.login.tip("请输入验证码!");
                return false;
            }
        }
        return true;
    },
    submit: function (formId) {
        Cms.util.httpPost("/login.html", $("#" + formId).serialize(), function () {
            Cms.dialog.close();
        }, function () {
            if ("dynamicForm" === formId) {
                return Cms.login.validateDynamic();
            }
            return Cms.login.validateNormal();
        }, function (response) {
            Cms.dialog.update(null, response.responseText);
        });
    },
    register: function () {
        if (!Cms.validate.isEmail($("#regUserName").val())) {
            Cms.dialog.warning("请输入正确的邮箱地址!")
            return false;
        }
        if (!$("#regPwd").val()) {
            Cms.dialog.warning("请输入密码!")
            return false;
        }
        if ($("#regPwd").val() !== $("#regConfirmPwd").val()) {
            Cms.dialog.warning("两次密码不一致!")
            return false;
        }
        if (!$("#regCode").val()) {
            Cms.dialog.warning("请输入验证码!")
            return false;
        }
        if (!$("#regAgree").is(":checked")) {
            Cms.dialog.warning("请同意网站协议!")
            return false;
        }
        return true;
    },
    resendActiveMail: function (email) {
        Cms.util.httpGet("/active/mail.html", {email: email}, function () {
            Cms.dialog.success("发送成功,请查收您的邮箱!");
        }, null, null, function () {
            $("#resendBtn").hide();
        });
    },
    activeSuccessToIndex: function (seconds) {
        var timer = setInterval(function () {
            setText(seconds--);
        }, 1000);
        function setText(second) {
            if(second<0){
                clearInterval(timer);
                window.location.href="/index.html";
            }else{
                $("#seconds").text(second);
            }
        }
    },
    validateForgetPwd:function(){
        if (!Cms.validate.isEmail($("#txtUser").val())) {
            Cms.dialog.warning("请输入正确的邮箱地址!")
            return false;
        }
        if (!$("#forgetPwdCode").val()) {
            Cms.dialog.warning("请输入验证码!")
            return false;
        }
    },
    validateForgetRestPwd:function(){
        if (!$("#pwd1").val()) {
            Cms.dialog.warning("请输入您的新密码!")
            return false;
        }
        if ($("#pwd2").val() !== $("#pwd1").val()) {
            Cms.dialog.warning("两次密码不一致!")
            return false;
        }
        if (!$("#forgetPwdCode").val()) {
            Cms.dialog.warning("请输入验证码!")
            return false;
        }
    },
}
Cms.login.init();