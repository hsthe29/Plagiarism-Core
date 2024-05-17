//package hust.thehs.core.services.plagiarism.text_alignment.document
//
//import hust.thehs.core.PDFTextData
//import hust.thehs.core.Sentence
//import java.util.regex.Pattern
//
//abstract class Document(pdfTextData: PDFTextData) {
//    val listOfSentences = mutableListOf<Sentence>() // Original sentences
//    val listOfProcessedSentences = mutableListOf<Sentence>()
//    val filteredSentences = mutableListOf<Sentence>() // Original sentences
////    var filteredProcessedSentences: org.json.JSONArray = org.json.JSONArray()
////    var listOfEmbeddings: org.json.JSONArray? = null
////    var filteredEmbeddings: org.json.JSONArray? = null
//    val invertedIndex = HashMap<String, ArrayList<Int>>()
//    val filteredInvertedIndex = HashMap<String, ArrayList<Int>>()
//
//    private var text: String // text of document
//    private var nWords = 0
//    var idxMainContents: IntArray = intArrayOf(-1, -1)
//    private var isThesis = true
//    var withReferences: Boolean = false
//    var nSentences: Int = 0
//
//    constructor(text: String) {
//        this.text = text + "\n\n\n" // add \n\n\n for sentences extraction
//        splitSentences()
//        //        pipeline = new VnCoreNLP(new String[]{"wseg", "pos"});
//        this.getInvertedIndex()
//    }
//
//    constructor(text: String, withReferences: Boolean) {
//        this.text = text + "\n\n\n" // add \n\n\n for sentences extraction
//        this.withReferences = withReferences
//        splitSentences()
//        //        pipeline = new VnCoreNLP(new String[]{"wseg", "pos"});
//        this.getInvertedIndex()
//    }
//
//    constructor(text: String, isThesis: Boolean, withReferences: Boolean) {
//        this.text = text + "\n\n\n" // add \n\n\n for sentences extraction
//        this.isThesis = isThesis
//        this.withReferences = withReferences
//        splitSentences()
//        //        pipeline = new VnCoreNLP(new String[]{"wseg", "pos"});
//        this.getInvertedIndex()
//    }
//
//    private fun splitSentences() {
//        val mainContent = mainContent
//        //        try {
////            PrintWriter pr = new PrintWriter("log");
////            pr.write(mainContent);
////            pr.close();
////        } catch (Exception err) {
////            err.printStackTrace();
////        }
//        val sentences = mainContent.split(regexSplitSentences.toRegex()).dropLastWhile { it.isEmpty() }
//            .toTypedArray()
//
//        //        nSentences = sentences.length;
////        originalUrl = sentences[0].split("\\n")[0].trim();
//        for (i in 1 until sentences.size) {
//            var sentence = sentences[i].trim { it <= ' ' }
//            val sentenceWords = sentence.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size
//            if (sentenceWords == 0) continue
//            nWords += sentenceWords
//            if (sentenceWords >= minSentenceWords) {
//                sentence = sentence.replace("\\s+".toRegex(), " ")
//                val elm: org.json.JSONArray = org.json.JSONArray()
//                elm.put(i)
//                elm.put(sentence)
//                listOfSentences.put(elm)
//                nSentences += 1
//            } else {
//                sentence = sentence.replace("\\s+".toRegex(), " ")
//                val elm: org.json.JSONArray = org.json.JSONArray()
//                elm.put(i)
//                elm.put(sentence)
//                filteredSentences.put(elm)
//            }
//        }
//    }
//
//    val mainContent: String
//        get() {
//            if (!isThesis || text.length < minThesisPages * nCharsInPage) {
//                // if document is not in form of HUST's thesis or
//                // document is too short, return text immediately
//                idxMainContents[0] = -1
//                idxMainContents[1] = -1
//                return text
//            }
//
//            var start_index = indexPreamble(text, regexPreface, 5)
//            if (start_index == -1) start_index = indexPreamble(text, regexChapter(1), 5)
//            if (start_index == -1) start_index = indexPreamble(text, regexChapter2(1), 5)
//            if (start_index == -1) start_index = indexPreamble(text, regexAbbreviation, 5)
//            if (start_index == -1) start_index = indexPreamble(text, regexListOfTables, 5)
//            if (start_index == -1) start_index = indexPreamble(text, regexListOfFigures, 5)
//            if (start_index == -1) start_index = indexPreamble(text, regexThanks, 5)
//            if (start_index == -1) start_index = indexPreamble(text, regexAbstract, 5)
//            if (start_index == -1) start_index = 0
//
//            var end_index = -1
//            if (!withReferences) {
//                end_index = indexEndPart(text, regexExtra)
//            }
//
//            idxMainContents[0] = start_index
//            idxMainContents[1] = end_index
//
//            if (end_index == -1) return text.substring(start_index).trim { it <= ' ' }
//            return text.substring(start_index, end_index).trim { it <= ' ' }
//        }
//
//    private fun indexPreamble(text: String, regex: String, minPagesFollowed: Int): Int {
//        val pattern = Pattern.compile(regex)
//        val matcher = pattern.matcher(text)
//        val indexes = ArrayList<Int>()
//        indexes.add(-1)
//        while (matcher.find()) {
//            indexes.add(matcher.start())
//        }
//        /* find correct index */
//        for (i in indexes.indices.reversed()) {
//            if (text.length - indexes[i] > nCharsInPage * minPagesFollowed) return indexes[i]
//        }
//        return indexes[0]
//    }
//
//    private fun indexEndPart(text: String, regex: String, maxPagesFollowed: Int, minPagesFollowed: Float): Int {
//        val pattern = Pattern.compile(regex)
//        val matcher = pattern.matcher(text)
//        val indexes = ArrayList<Int>()
//        indexes.add(-1)
//        while (matcher.find()) {
//            indexes.add(matcher.start())
//        }
//        /* find correct index */
//        for (i in indexes.indices.reversed()) {
//            val nCharsFollowed = text.length - indexes[i]
//            if (nCharsFollowed < nCharsInPage * maxPagesFollowed &&
//                nCharsFollowed > nCharsInPage * minPagesFollowed
//            ) return indexes[i]
//        }
//        return indexes[0]
//    }
//
//    private fun indexEndPart(text: String, regex: String): Int {
//        val pattern = Pattern.compile(regex)
//        val matcher = pattern.matcher(text)
//        val indexes = ArrayList<Int>()
//        indexes.add(-1)
//        while (matcher.find()) {
//            indexes.add(matcher.start())
//        }
//
//        return indexes[indexes.size - 1]
//    }
//
//    abstract fun buildInvertedIndex()
//
//    abstract fun indexFilteredSentences()
//
//
//    fun updateInvertedIndex(key: String, index: Int) {
//        if (!invertedIndex.containsKey(key)) {
//            val senContainWord = ArrayList<Int>()
//            senContainWord.add(index)
//            invertedIndex[key] = senContainWord
//        } else {
//            invertedIndex[key]!!.add(index)
//        }
//    }
//
//    fun updateFilteredInvertedIndex(key: String, index: Int) {
//        if (!filteredInvertedIndex.containsKey(key)) {
//            val senContainWord = ArrayList<Int>()
//            senContainWord.add(index)
//            filteredInvertedIndex[key] = senContainWord
//        } else {
//            filteredInvertedIndex[key]!!.add(index)
//        }
//    }
//
//    fun getnWords(): Int {
//        return nWords
//    }
//
//    fun getListOfSentences(): org.json.JSONArray {
//        return listOfSentences
//    }
//
//    companion object {
//        //    String originalUrl;
//        /*
//    Regex used to split sentences
//     */
//        const val vnUpper: String = "A-ZẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ"
//        const val vnLower: String = "a-zắằẳẵặăấầẩẫậâáàãảạđếềểễệêéèẻẽẹíìỉĩịốồổỗộôớờởỡợơóòỏõọứừửữựưúùủũụýỳỷỹỵ"
//
//        //    final static String regexSentence = "(\\n+[" + vnUpper + "\\.\\-: ]+?(\\n{3,}))|([" + vnUpper + "]" +
//        //            "[\\s\\S]+?([\\.\\!\\?]|\\n{3,}))"; // full uppercase and normal sentences
//        const val regexPreface: String = "\\s+(LỜI NÓI ĐẦU|LỜI MỞ ĐẦU|MỞ ĐẦU|PHẦN MỞ ĐẦU|" +
//                "Lời nói đầu|Lời mở đầu|Mở đầu|Phần mở đầu)([^\\S\\n]+|)\\n"
//        const val regexAbbreviation: String = "\\s+(DANH MỤC (TỪ|) VIẾT TẮT|Danh mục (từ|) viết tắt)([\\s\\S]+|)?\\n\\s"
//        const val regexListOfTables: String = "\\s+(DANH MỤC (CÁC|) BẢNG|DANH SÁCH (CÁC|) BẢNG|" +
//                "Danh mục (các|) bảng|Danh sách (các|) bảng)([^\\S\\n]+|)\\n"
//        const val regexListOfFigures: String = "\\s+(DANH MỤC (CÁC|) HÌNH ẢNH|DANH SÁCH (CÁC|) HÌNH|" +
//                "Danh mục (các|) hình ảnh|Danh sách (các|) hình)([^\\S\\n]+|)\\n"
//        const val regexThanks: String = "\\s+(LỜI CẢM ƠN|Lời cảm ơn)([^\\S\\n]+|)\\n"
//        const val regexAbstract: String = "\\s+TÓM TẮT[\\s\\S]+?\\n\\s"
//        val romanNumbers: Array<String?> = arrayOf(
//            null, "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII",
//            "XIII", "XIV", "XV"
//        )
//
//
//        const val regexConclusion: String = "\\s+KẾT LUẬN"
//
//        //    final static String regexExtra = "\\s+([" + vnUpper + " ]|)(HẠN CHẾ|HƯỚNG PHÁT TRIỂN|TÀI LIỆU THAM KHẢO|" +
//        //            "Tài liệu tham khảo)";
//        const val regexExtra: String =
//            "((danh\\s+m(ụ|Ụ)c) | (t(à|À)i\\s+l(((ị|Ị)(ê|Ê))|(i(ệ|Ệ)))u))\\s+tham\\s+kh(ả|Ả)o \\s*((\\[1\\])|(1\\.))"
//
//        /* Config variables */
//        const val minSentenceWords: Int = 7
//        const val nCharsInPage: Int = 4000
//        const val minThesisPages: Int = 10
//
//        /*
//    Split sentences by regex
//    */
//        const val regexSplitSentences: String = "([\\.\\!\\?]\\s)|\\n{3,}"
//
//        private fun regexChapter(chapter: Int): String {
//            return "\\s+(CHƯƠNG|Chương) +(" + chapter + "|" +
//                    romanNumbers[chapter] + ")[^" + vnLower + vnUpper + "]+"
//        }
//
//        private fun regexChapter2(chapter: Int): String {
//            return "\\s+(PHẦN|Phần) +(" + chapter + "|" +
//                    romanNumbers[chapter] + ")[^" + vnLower + vnUpper + "]+"
//        }
//    }
//}