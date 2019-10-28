Cms.doc = {
    glide: function () {
        glide.layerGlide(true, 'iconBall', 'textBall', 'show_pic', 610, 2, 0.1, 'left');
    },
    load: function (url) {
        if (url) {
            pdfjsLib.pdfUrl = url;
            pdfjsLib.GlobalWorkerOptions.workerSrc = Cms.config.resServer + '/style/default/pdfjs/build/pdf.worker.min.js';
            pdfjsLib.loadScript(Cms.config.resServer + '/style/default/pdfjs/web/viewer.min.js');
        }
    }
}
