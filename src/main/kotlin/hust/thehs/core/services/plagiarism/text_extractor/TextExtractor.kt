package hust.thehs.core.services.plagiarism.text_extractor


import hust.thehs.absolutePathOf
import hust.thehs.core.PDFTextPage
import hust.thehs.core.TokenizedSentence
import io.ktor.utils.io.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.pdfbox.text.PDFTextStripperByArea
import org.xml.sax.ContentHandler
import vn.pipeline.Annotation
import vn.pipeline.VnCoreNLP
import java.awt.geom.Rectangle2D
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


private val REGEX_BODY = """<body>(.*?)</body>""".toRegex(RegexOption.DOT_MATCHES_ALL)
private val REGEX_DIV = """<div class="(.*?)">(.*?)</div>""".toRegex(RegexOption.DOT_MATCHES_ALL)
private val REGEX_P = """<p>(.*?)</p>""".toRegex(RegexOption.DOT_MATCHES_ALL)
val regexTextRemove: Array<String> = arrayOf(
    "Sinh\\s+viên\\s+thực\\s+hiện[\\s\\S]+?\\n"
)

enum class ParseFormat {
    TEXT, HTML
}


private fun urlOf(filePath: String): URL {
    val file = File(filePath)
    return if (file.isFile) {
        file.toURI().toURL()
    } else {
        URL(filePath)
    }
}

//fun parseText(filePath: String, extension: String?, toJson: Boolean = true): String {
//    val context = ParseContext()
//    val detector = DefaultDetector()
//    val parser = AutoDetectParser(detector)
//
//    if (extension == null) {
//        val encoded = Files.readAllBytes(Paths.get(filePath))
//        return String(encoded, StandardCharsets.UTF_8)
//    }
//
//    val metadata = Metadata()
//
//    val url = urlOf(filePath)
//    val input: InputStream = TikaInputStream.get(url, metadata)
//    val handler: ContentHandler = if(toJson) ToXMLContentHandler() else BodyContentHandler()
//    parser.parse(input, handler, metadata, context)
//    input.close()
//    val content = handler.toString()
//    return content
//}



//fun convertPDFToText(filePath: String, toJson: Boolean = true): String {
//    val extension = FilenameUtils.getExtension(filePath)
//    val content = parseText(filePath, extension, toJson)
//    val mainPath = filePath.dropLast(extension.length + 1)
//    if (toJson) {
//        val bodyContent = REGEX_BODY.find(content)?.groupValues?.get(1)
//        val divContent = bodyContent?.let { REGEX_DIV.findAll(it) }?.map { it.groupValues[2] }
//
//        val pdfTextData = mutableListOf<PDFTextPage>()
//        if (divContent != null) {
//            for ((i, div) in divContent.withIndex()) {
//                pdfTextData.add(PDFTextPage(page = i,
//                    text = buildString {
//                        val pContent = REGEX_P.findAll(div).map { it.groupValues[1] }
//                        for (p in pContent) {
//                            val trailedText = p.replace("\n", "")
//                            append(trailedText)
//                            if (trailedText.last() != ' ') {
//                                append(' ')
//                            }
//                        }
//                    }
//                ))
//            }
//        }
//        val json = Json.encodeToString<List<PDFTextPage>>(pdfTextData)
//        val writer = PrintWriter("$mainPath.json")
//        writer.write(json)
//        writer.close()
//        return "$mainPath.json"
//    } else {
//        val writer = PrintWriter("$mainPath.txt")
//        writer.write(content)
//        writer.close()
//        return "$mainPath.txt"
//    }
//}

fun createRecord(resourcePath: String) {
    require(resourcePath.endsWith(".json")) {"Create database record is only available for json file" }

    val dataObj = Json.decodeFromString<Array<PDFTextPage>>(File(resourcePath).readText())
    val vnCoreNLP = VnCoreNLP(arrayOf("wseg", "pos"))
    for (pageData in dataObj) {
        val text = pageData.text
        val sentences = text.split("""([.?!])\s*(?=[A-Z])""".toRegex())
        for (sentence in sentences) {
            val annotation = Annotation(sentence)
            vnCoreNLP.annotate(annotation)
            val words = annotation.words
            val tokenizedSentence = TokenizedSentence(numWords = if (sentence.isEmpty()) 0 else sentence.split("""\s+""".toRegex()).size)
            for (word in words) {
                val token = word.form
                val posTag = word.posTag
                tokenizedSentence.tokens.add(token)
                tokenizedSentence.posTags.add(posTag)
            }
            pageData.tokenization.add(tokenizedSentence)
        }
    }
    val json = Json.encodeToString<List<PDFTextPage>>(dataObj.toList())
    val writer = PrintWriter(resourcePath)
    writer.write(json)
    writer.close()
}

// top=2cm, bottom=2cm, left=3.5cm, right=2.5cm


class TextExtractor(top: CM = 0.0.cm,
                    bottom: CM = 0.0.cm,
                    left: CM = 0.0.cm,
                    right: CM = 0.0.cm
    ) {
    private val A4_DPI_WIDTH = 21.0.cm.toDpiSize
    private val A4_DPI_HEIGHT = 29.7.cm.toDpiSize

    private val REGION_NAME = "body"

    private val pdfStripper = PDFTextStripperByArea()

    init {
        val width = A4_DPI_WIDTH.value - right.toDpiSize.value - left.toDpiSize.value
        val height = A4_DPI_HEIGHT.value - bottom.toDpiSize.value - top.toDpiSize.value

//        println("top: ${top.toDpiSize.value}")
//        println("left: ${left.toDpiSize.value}")
//        println("width: $width")
//        println("height: $height")

        pdfStripper.addRegion(this.REGION_NAME, Rectangle2D.Double(
            left.toDpiSize.value,
            top.toDpiSize.value,
            width,
            height))
    }

    private fun checkExtension(filePath: String) {
        assert(filePath.split('.').last() == "pdf") { "Please input PDF file" }
    }

    private fun processPage(text: String): String {
        val text1 = text.replace(13.toChar().toString(), "")
        val paragraphs = text1.split(".\n")
        val processedParagraphs = paragraphs.map { p ->
            buildString {
                append(p.replace('\n', ' '))
            }
        }
        return processedParagraphs.joinToString(".\n")
    }

    fun run(filePath: String, format: ParseFormat = ParseFormat.TEXT) {
        this.checkExtension(filePath)

        val file = File(filePath)

        val document = Loader.loadPDF(file)

        when (format) {
            ParseFormat.TEXT -> {
                val textPages = mutableListOf<String>()

                for(page in document.pages) {
                    pdfStripper.extractRegions(page)
                    val text = pdfStripper.getTextForRegion(this.REGION_NAME)
                    textPages.add(text)
                }

                for (i in textPages) {
                    println(i)
                    println("_---------------------------------------------------_")
                }
            }
            ParseFormat.HTML -> {
                TODO("Not Implemented!")
            }
        }

    }
}

fun main() {
    println(absolutePathOf("datn/datn_trandangtuyen_final_2.2m.pdf"))
//    val savePath = convertPDFToText(absolutePathOf("check/01.202281-NguyenDanhNam.pdf"), true)
//    createRecord(savePath)
// top=2cm, bottom=2cm, left=3.5cm, right=2.5cm

    val textExtractor = TextExtractor(
        top=2.0.cm,
        bottom=2.0.cm,
        left=2.0.cm,
        right=2.0.cm
    )

    textExtractor.run(absolutePathOf("datn/datn_trandangtuyen_final_2.2m.pdf"))
}