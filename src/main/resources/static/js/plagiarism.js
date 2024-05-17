var PDFJS_VERSION = '2.0.943'

pdfjsLib.GlobalWorkerOptions.workerSrc = '/assets/pdfjs-'+PDFJS_VERSION+'/build/pdf.worker.js';
var CMAP_URL = '/assets/pdfjs-'+PDFJS_VERSION+'/cmaps/';

var UploadedDocViewer = {
    pdfFindController: null,
    init: function(pdfUrl) {
        var container = document.getElementById('viewerContainer');
        var pdfLinkService = new pdfjsViewer.PDFLinkService();
        this.pdfFindController = new pdfjsViewer.PDFFindController({
            linkService: pdfLinkService
        });
        this.pdfFindController.renderDeffer = new $.Deferred();
        // this.pdfFindController.markRefs = [];

        var pdfViewer = new pdfjsViewer.PDFViewer({
            container: container,
            linkService: pdfLinkService,
            findController: this.pdfFindController
        });
        pdfLinkService.setViewer(pdfViewer);

        document.addEventListener('pagesinit', function() {
            pdfViewer.currentScaleValue = 'page-width';
        });

        pdfjsLib.getDocument({
            url: pdfUrl,
            cMapUrl: CMAP_URL,
            cMapPacked: true
        }).then(function(pdfDocument) {
            pdfViewer.setDocument(pdfDocument);
            pdfLinkService.setDocument(pdfDocument, null);
        });
    },
    zoomIn: function() {

    },
    zoomOut: function() {

    },
    search: function(text, state) {
        if (!state) state = {
            query: text,
            phraseSearch: true,
            highlightAll: true,
            caseSensitive: true
        }
        this.pdfFindController.executeCommand('find', state);
    },
    markRef: function() {
        $('.highlight').addClass('sp2');
    },
    highlight: function(text, srcIdx, senIdx, percentage) {
        // console.log(text);
        _this = this;
        _this.pdfFindController.renderDeffer = new $.Deferred();
        state = {
            query: text,
            phraseSearch: true,
            highlightAll: true,
            caseSensitive: true,
            afterRendersFunc: _this.markRef
        }
        _this.search(text, state);
    }
};


var SourceDocList = {
    add: function() {

    },
    expand: function() {

    },
    collapse: function() {

    }
};

var SourceDocViewer = {
    expand: function() {

    },
    collapse: function() {

    },
    scrollIntoViewer: function(senIdx) {

    },
    highlight: function(sentence, senIdx) {

    }
}

var Action = {
    scrollIntoViewer: function() {

    },
    showDetail: function() {

    }
};

var InitViewer = {
    init: function(res) {
        this.res = res;
        this.curSrcDocIdx = 0;
        this.curPairIdx = 0;
        UploadedDocViewer.init(res.uploadedUrl);
    },
    increaseIndexes: function() {
        if (this.curPairIdx == this.res.s[this.curSrcDocIdx].s.length - 1) {
            if (this.curSrcDocIdx == this.res.s.length - 1) return false;
            this.curSrcDocIdx ++;
            this.curPairIdx = 0;
            return true;
        } else {
            this.curPairIdx++;
            return true;
        }
    },
    nextMatch: function() {
        res = this.res;
        var curPairIdx = this.curPairIdx;
        var curSrcDocIdx = this.curSrcDocIdx;
        var upSenIdx = res.s[curSrcDocIdx].s[curPairIdx][0];
        var percentage = res.s[curSrcDocIdx].s[curPairIdx][2];
        var upSen = res.s[curSrcDocIdx].s[curPairIdx][8];
        UploadedDocViewer.highlight(upSen, this.curSrcDocIdx, upSenIdx, percentage);

        if (this.increaseIndexes()) {
            _this = this;
            $.when(UploadedDocViewer.pdfFindController.renderDeffer).done(function () {
                setTimeout(function() {
                    _this.nextMatch();
                }, 300);

            });
        }
    },
    highlight: function() {
        this.nextMatch();
    }
}

function nextMatch() {

}

function initViewer(res) {
    // res.s[0].s = [res.s[0].s[2]];
    // res.s = [res.s[0]];
    // res.s[0].s = [res.s[0].s[0], res.s[0].s[1]];
    InitViewer.init(res);
    InitViewer.highlight();

    // $.each(res.s, function(idxDoc, srcDoc) {
    //     $.each(srcDoc.s, function(idxPair, pair) {
    //         var upSenIdx = pair[0];
    //         var percentage = pair[2];
    //         var upSen = pair[8];
    //         UploadedDocViewer.highlight(upSen, idxDoc, upSenIdx, percentage);
    //     });
    // });
}
