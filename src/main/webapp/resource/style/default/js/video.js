Cms.video = {
    slide: function () {
        $(".video_banner").slide({
            titCell: ".hd li ",
            mainCell: ".bd",
            interTime: 2000,
            prevCell: ".banner_pre",
            nextCell: ".banner_next",
            effect: "fold",
            autoPlay: true,
            easing: "swing",
            trigger: "mouseover",
        });
    },
    play: function (url,preview) {
        new Player({
            el:document.querySelector('#player'),
            poster: preview,
            url: url,
            fluid: true,
            width: 850,
            height: 440,
            volume: 1,
            playNext: {
                urlList: [],
            }
        });
    }
}
