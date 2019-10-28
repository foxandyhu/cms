Cms.index = {
    //  焦点图轮播
    focusArticle: function () {
        function byid(id) {
            return document.getElementById(id);
        }

        function bytag(tag, obj) {
            return (typeof obj == 'object' ? obj : byid(obj)).getElementsByTagName(tag);
        }

        function inlize() {
            oPicLis[0].style.filter = 'alpha(opacity:100)';
            oPicLis[0].style.opacity = 100;
            oPicLis[0].style.zIndex = 5;
        };

        function changePic() {
            for (var i = 0; i < oPicLis.length; i++) {
                doMove(oPicLis[i], 'opacity', 0);
                oPicLis[i].style.zIndex = 0;
                oBtnLis[i].className = '';
            }
            ;
            doMove(oPicLis[iActive], 'opacity', 100);
            oPicLis[iActive].style.zIndex = 5;
            oBtnLis[iActive].className = 'active';
            if (iActive == 0) {
                doMove(bytag('ul', oBtn)[0], 'left', 0);
            } else if (iActive >= oPicLis.length - 2) {
                doMove(bytag('ul', oBtn)[0], 'left', -(oPicLis.length - 3) * (oBtnLis[0].offsetWidth + 4));
            } else {
                doMove(bytag('ul', oBtn)[0], 'left', -(iActive - 1) * (oBtnLis[0].offsetWidth + 4));
            }
        };

        function autoplay() {
            if (iActive >= oPicLis.length - 1) {
                iActive = 0;
            } else {
                iActive++;
            }
            changePic();
        };

        function getStyle(obj, attr) {
            if (obj.currentStyle) {
                return obj.currentStyle[attr];
            } else {
                return getComputedStyle(obj, false)[attr];
            }
        };

        function doMove(obj, attr, iTarget) {
            clearInterval(obj.timer);
            obj.timer = setInterval(function () {
                    var iCur = 0;
                    if (attr == 'opacity') {
                        iCur = parseInt(parseFloat(getStyle(obj, attr)) * 100);
                    } else {
                        iCur = parseInt(getStyle(obj, attr));
                    }
                    var iSpeed = (iTarget - iCur) / 6;
                    iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
                    if (iCur == iTarget) {
                        clearInterval(obj.timer);
                    } else {
                        if (attr == 'opacity') {
                            obj.style.opacity = (iCur + iSpeed) / 100;
                        } else {
                            obj.style.filter = 'alpha(opacity:' + (iCur + iSpeed) + ')';
                        }
                    }
                },
                30)
        };
        try {
            var option = option ? option : {};
            var opt = {
                oFocus: option.oFocus ? option.oFocus : 'tFocus',
                oPic: option.oPic ? option.oPic : 'tFocus-pic',
                oBtn: option.oBtn ? option.oBtn : 'tFocus-btn',
                tLeft: option.tLeft ? option.tLeft : 'tFocus-leftbtn',
                tRight: option.tRight ? option.tRight : 'tFocus-rightbtn',
                prev: option.prev ? option.prev : 'prev',
                next: option.next ? option.next : 'next'
            };
            var oPic = byid(opt.oPic);
            var oPicLis = bytag('li', oPic);
            var oBtn = byid(opt.oBtn);
            var oBtnLis = bytag('li', oBtn);
            var iActive = 0;
            for (var i = 0; i < oPicLis.length; i++) {
                oBtnLis[i].sIndex = i;
                oBtnLis[i].onclick = function () {
                    if (this.sIndex == iActive) return;
                    iActive = this.sIndex;
                    changePic();
                }
            }
            ;
            byid(opt.tLeft).onclick = byid(opt.prev).onclick = function () {
                iActive--;
                if (iActive == -1) {
                    iActive = oPicLis.length - 1;
                }
                changePic();
            };
            byid(opt.tRight).onclick = byid(opt.next).onclick = function () {
                iActive++;
                if (iActive == oPicLis.length) {
                    iActive = 0;
                }
                changePic();
            };
            aTimer = setInterval(autoplay, 3000);
            inlize();
            byid(opt.oFocus).onmouseover = function () {
                clearInterval(aTimer);
            }
            byid(opt.oFocus).onmouseout = function () {
                aTimer = setInterval(autoplay, 3000);
            }
        } catch (e) {
        }
    },
    // 鼠标移上去滚动效果
    hotScoll: function () {
        try {
            $(".ps_one").mouseover(function () {
                $(".ps_one").removeClass("on");
                $(this).addClass("on");
            });
        } catch (e) {
        }
    },
    gallery: function () {
        try {
            window.setInterval(function () {
                $('.og_next').click();
            }, 5000);
            var linum = $('.mainlist li').length;
            var w = linum * 250;
            var showLength = 5;
            var mw = showLength * 250;
            $('.piclist').css('width', w + 'px');
            $('.swaplist').html($('.mainlist').html());
            $('.og_next').click(function () {
                if ($('.swaplist,.mainlist').is(':animated')) {
                    $('.swaplist,.mainlist').stop(true, true);
                }
                if ($('.mainlist li').length > showLength) {
                    var ml = parseInt($('.mainlist').css('left'));
                    var sl = parseInt($('.swaplist').css('left'));
                    if (ml <= 0 && ml > w * -1) {
                        $('.swaplist').css({left: mw + 'px'});
                        $('.mainlist').animate({left: ml - mw + 'px'}, 'slow');
                        if (ml == (w - mw) * -1) {
                            $('.swaplist').animate({left: '0px'}, 'slow');
                        }
                    } else {
                        $('.mainlist').css({left: mw + 'px'});
                        $('.swaplist').animate({left: sl - mw + 'px'}, 'slow');
                        if (sl == (w - mw) * -1) {
                            $('.mainlist').animate({left: '0px'}, 'slow');
                        }
                    }
                }
            });
            $('.og_prev').click(function () {
                if ($('.swaplist,.mainlist').is(':animated')) {
                    $('.swaplist,.mainlist').stop(true, true);
                }
                if ($('.mainlist li').length > 4) {
                    var ml = parseInt($('.mainlist').css('left'));
                    var sl = parseInt($('.swaplist').css('left'));
                    if (ml <= 0 && ml > w * -1) {
                        $('.swaplist').css({left: w * -1 + 'px'});
                        $('.mainlist').animate({left: ml + mw + 'px'}, 'slow');
                        if (ml == 0) {
                            $('.swaplist').animate({left: (w - mw) * -1 + 'px'}, 'slow');
                        }
                    } else {
                        $('.mainlist').css({left: (w - mw) * -1 + 'px'});
                        $('.swaplist').animate({left: sl + mw + 'px'}, 'slow');
                        if (sl == 0) {
                            $('.mainlist').animate({left: '0px'}, 'slow');
                        }
                    }
                }
            });
            $('.og_prev,.og_next').hover(function () {
                $(this).fadeTo('fast', 1);
            }, function () {
                $(this).fadeTo('fast', 0.7);
            })
        } catch (e) {
        }
    },
    init: function () {
        this.focusArticle();
        this.hotScoll();
        this.gallery();
    }
}

$(function () {
    Cms.index.init();
});