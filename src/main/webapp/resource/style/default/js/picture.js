Cms.picture={
    slide:function(){
        $("#sider").lofJSidernews({interval: 3000, easing: "easeInOutQuad", duration: 1200, mainWidth: 840, auto: true});
    },
    hover:function(){
        $("#content_cn_picture").children("li").mouseover(function(){
            $("#content_cn_picture").children("li").removeClass("change_a1");
            $(this).addClass("change_a1");
            $("#content_cn_picture ").children("li").addClass("change_a");
        });
        $("#content_cn_picture").children("li").mouseout(function(){
            $("#content_cn_picture").children("li").removeClass("change_a");
            $("#content_cn_picture").children("li").addClass("change_a1");
            $(this).removeClass("change_a1");
        });
    },
    gallery:function(loader){
        $('.ad-gallery').adGallery({
            width: '90%',
            height: '100%',
            left_img_container : false,
            jump: true,
            cycle: 8,
            loader_image:loader
        });
    }
}