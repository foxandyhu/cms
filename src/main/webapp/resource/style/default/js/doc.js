Cms.doc = {
    load: function (url) {
        if (url) {
            pdfjsLib.pdfUrl = url;
            pdfjsLib.GlobalWorkerOptions.workerSrc = Cms.config.resServer + '/style/default/pdfjs/build/pdf.worker.min.js';
            pdfjsLib.loadScript(Cms.config.resServer + '/style/default/pdfjs/web/viewer.min.js');
        }
    }
}
