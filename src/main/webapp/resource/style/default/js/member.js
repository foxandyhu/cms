Cms.member = {
    editPwd: function () {
        if (!$("input[name='password']").val()) {
            Cms.dialog.warning("请输入原密码!")
            return;
        }
        if (!$("input[name='newPwd']").val()) {
            Cms.dialog.warning("请输入新密码!")
            return;
        }
        if ($("input[name='newPwd']").val() !== $("input[name='confirmPwd']").val()) {
            Cms.dialog.warning("两次密码不一致!")
            return;
        }
        Cms.util.httpPost("/member/password.html", $("#pwdForm").serialize(), function () {
            Cms.dialog.success("修改成功");
            $("#pwdForm")[0].reset();
        }, function () {
            $(".edit_btn").attr("disabled", "disabled");
        }, null, function () {
            $(".edit_btn").removeAttr("disabled");
        });
    },
    showEditInfo: function () {
        Cms.util.loadJs(Cms.config.resServer + "/style/default/my97datepicker/WdatePicker.js", function () {
            $(".info_cc").hide();
            $(".form_cc").show();
            $("input[name='birthday']").bind("click", function () {
                WdatePicker({el: this, dateFmt: "yyyy-MM-dd"});
            });
        });
    },
    showEditFace: function () {
        Cms.util.httpGet("/member/face/edit.html", null, function (response) {
            Cms.dialog.normal("上传头像", response, null, null, true);
        });
    },
    initToolBtnFun: function () {
        $(".toolBtn").bind("click", function () {
            var $this = $(this);
            var data = $this.data();
            $("#previewFace").cropper(data.method, data.option);
        });
        $("#submitFace").bind("click", function () {
            Cms.member.uploadFace();
        });
    },
    faceChange: function (e) {
        var fileMaxSize = 1024 * 5;
        var target = $(e.target);
        var file = target[0].files[0];
        var size = file.size / 1024;
        if (size > fileMaxSize) {
            Cms.dialog.warning("图片最大为5M!");
            return false;
        }
        if (!file.type.match(/image.*/)) {
            Cms.dialog.warning('请选择正确的图片!');
            return;
        }
        var filename = document.querySelector("#avatar-name");
        var texts = document.querySelector("#avatarInput").value;
        filename.innerHTML = texts.match(/[^\\]+\.[^\(]+/i);

        var url = window.URL.createObjectURL(file);
        $("#previewFace").attr("src", url);

        var options = {
            aspectRatio: 1 / 1,
            preview: ".preview-lg",
            guides: false,
            autoCropArea: 0.5,
            crop: function (e) {
            }
        };
        $("#previewFace").cropper("destroy").cropper(options);
    },
    uploadFace: function () {
        var canvas = $("#previewFace").cropper("getCroppedCanvas", {width: 222, height: 222});
        //生成base64图片数据
        var dataUrl = canvas.toDataURL("image/png");
        $("#base64Face").val(dataUrl);
        $("#faceForm").submit();
    }
}
