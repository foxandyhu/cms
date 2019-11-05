Cms.mobile = {
    glide: function () {
        glide.layerGlide(true, 'iconBall', 'textBall', 'show_pic', '100%', 2, 0.1, 'left');
    },
    loadMore: function (channel) {
        var noMore = false;
        var loading = false;
        var page = 1;
        $(window).scroll(function () {
            if (loading || noMore) {
                return;
            }
            if ($(window).scrollTop() >= $(document).height() - $(window).height() - 60) {
                $(".loading").show();
                loading = true;
                $("body").animate({scrollTop: $(document).height() - $(window).height()}, "normal", function () {
                    page++;
                    var url = "/" + channel + "/list.html?mobileAjax=true&pageNo=" + page;
                    Cms.util.httpGet(url, {}, function (response) {
                        if (!response) {
                            $(".loading").show();
                            $(".loading").text("没有更多内容了哦!");
                            noMore = true;
                        } else {
                            $("#newsContainers").append(response);
                            $(".loading").hide();
                            noMore = false;
                        }
                        loading = false;
                    }, function () {
                        $(".loading").show();
                    }, function () {
                        if (page > 1) {
                            page--;
                        }
                    });
                });
            }
        });
    },
    loadMoreSearch: function (word) {
        var noMore = false;
        var loading = false;
        var page = 1;
        $(window).scroll(function () {
            if (loading || noMore) {
                return;
            }
            if ($(window).scrollTop() >= $(document).height() - $(window).height() - 60) {
                $(".loading").show();
                loading = true;
                $("body").animate({scrollTop: $(document).height() - $(window).height()}, "normal", function () {
                    page++;
                    var url = "/search.html";
                    Cms.util.httpGet(url, {mobileAjax: true, word: word, pageNo: page}, function (response) {
                        if (!response) {
                            $(".loading").show();
                            $(".loading").text("没有更多内容了哦!");
                            noMore = true;
                        } else {
                            $("#searchContainer").append(response);
                            $(".loading").hide();
                            noMore = false;
                        }
                        loading = false;
                    }, function () {
                        $(".loading").show();
                    }, function () {
                        if (page > 1) {
                            page--;
                        }
                    });
                });
            }
        });
    },
    loadComments: function (articleId, pageNo) {
        Cms.util.httpGet('/comment/article-' + articleId + ".html?pageNo=" + pageNo,null,function (response) {
            if (response) {
                $("#commentsContainer").append(response);
            } else {
                $("#moreComments").text("没有了");
            }
        });
    },
    loadGuestBooks:function(){
        Cms.util.httpGet("/guestbook/load.html?pageNo=" + (++guestBookPageNo), null, function (response) {
            if (response) {
                $("#guestbooksContainer").append(response);
            } else {
                $("#moreGuestBooks").text("没有了");
            }
        });
    },
    postComment: function (articleId,content,captcha) {
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
    postGuestBook: function () {
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
    },
    playVideo: function (url,preview) {
        new Player({
            el:document.querySelector('#player'),
            poster: preview,
            url: url,
            fluid: true,
            height: 560,
            volume: 1,
            playNext: {
                urlList: [],
            }
        });
    },
    initPhotoSwipe:function (gallerySelector) {
        try{
            var parseThumbnailElements = function (el) {
                var thumbElements = el.childNodes,numNodes = thumbElements.length, items = [], figureEl, linkEl,item;
                for (var i = 0; i < numNodes; i++) {
                    figureEl = thumbElements[i];
                    if (figureEl.nodeType !== 1) {
                        continue;
                    }
                    linkEl = figureEl.children[1].children[0];
                    var image=figureEl.children[1].children[0].children[0];
                    var width=300,height=400;
                    if (typeof image.naturalWidth === undefined) {
                        var img = new Image();
                        img.src = image.src;
                        width = img.width;
                        height = img.height;
                    }
                    else {
                        width = image.naturalWidth;
                        height = image.naturalHeight;
                    }
                    item = {src: linkEl.getAttribute('href'), w:width, h:height};
                    if (figureEl.children.length > 1) {
                        item.title = figureEl.children[0].innerHTML;
                    }
                    if (linkEl.children.length > 0) {
                        item.msrc = linkEl.children[0].getAttribute('src');
                    }
                    item.el = figureEl;
                    items.push(item);
                }
                return items;
            };
            var closest = function closest(el, fn) {
                return el && (fn(el) ? el : closest(el.parentNode, fn));
            };
            var onThumbnailsClick = function (e) {
                e = e || window.event;
                e.preventDefault ? e.preventDefault() : e.returnValue = false;
                var eTarget = e.target || e.srcElement;
                var clickedListItem = closest(eTarget, function (el) {
                    return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
                });
                if (!clickedListItem) {
                    return;
                }
                var clickedGallery = clickedListItem.parentNode,
                    childNodes = clickedListItem.parentNode.childNodes,
                    numChildNodes = childNodes.length,
                    nodeIndex = 0,
                    index;
                for (var i = 0; i < numChildNodes; i++) {
                    if (childNodes[i].nodeType !== 1) {
                        continue;
                    }
                    if (childNodes[i] === clickedListItem) {
                        index = nodeIndex;
                        break;
                    }
                    nodeIndex++;
                }
                if (index >= 0) {
                    console.log(index);
                    openPhotoSwipe(index, clickedGallery);
                }
                return false;
            };
            var photoswipeParseHash = function () {
                var hash = window.location.hash.substring(1),
                    params = {};
                if (hash.length < 5) {
                    return params;
                }
                var vars = hash.split('&');
                for (var i = 0; i < vars.length; i++) {
                    if (!vars[i]) {
                        continue;
                    }
                    var pair = vars[i].split('=');
                    if (pair.length < 2) {
                        continue;
                    }
                    params[pair[0]] = pair[1];
                }

                if (params.gid) {
                    params.gid = parseInt(params.gid, 10);
                }
                return params;
            };
            var openPhotoSwipe = function (index, galleryElement, disableAnimation, fromURL) {
                var pswpElement = document.querySelectorAll('.pswp')[0], gallery, options, items;
                items = parseThumbnailElements(galleryElement);
                options = {
                    fullscreenEl: false,
                    galleryUID: galleryElement.getAttribute('data-pswp-uid'),
                    getThumbBoundsFn: function (index) {
                        var thumbnail = items[index].el.getElementsByTagName('img')[0],
                            pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
                            rect = thumbnail.getBoundingClientRect();
                        return {x: rect.left, y: rect.top + pageYScroll, w: rect.width};
                    }
                };
                if (fromURL) {
                    if (options.galleryPIDs) {
                        for (var j = 0; j < items.length; j++) {
                            if (items[j].pid == index) {
                                options.index = j;
                                break;
                            }
                        }
                    } else {
                        options.index = parseInt(index, 10) - 1;
                    }
                } else {
                    options.index = parseInt(index, 10);
                }
                if (isNaN(options.index)) {
                    return;
                }
                if (disableAnimation) {
                    options.showAnimationDuration = 0;
                }
                gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items, options);
                gallery.init();
            };
            var galleryElements = document.querySelectorAll(gallerySelector);
            for (var i = 0, l = galleryElements.length; i < l; i++) {
                galleryElements[i].setAttribute('data-pswp-uid', i + 1);
                galleryElements[i].onclick = onThumbnailsClick;
            }
            var hashData = photoswipeParseHash();
            if (hashData.pid && hashData.gid) {
                openPhotoSwipe(hashData.pid, galleryElements[hashData.gid - 1], true, true);
            }
        }catch(e){
            Cms.dialog.error(e);
        }
    }
}