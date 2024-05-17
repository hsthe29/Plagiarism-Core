//package hust.thehs.core.services.plagiarism.text_extractor
//
//import com.typesafe.config.Optional
//import hust.thehs.absolutePathOf
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.Transient
////import org.apache.commons
//import java.io.File
//import java.io.IOException
//import java.io.InputStream
//import java.net.URL
//
//
//class TestExtractor {
//    fun processRecord(filePath: String): Map<String, Any> {
//        val map: MutableMap<String, Any> = HashMap()
//        val metadata = Metadata()
//        try {
//            val file = File(filePath)
//            val url = if (file.isFile) {
//                file.toURI().toURL()
//            } else {
//                URL(filePath)
//            }
//            val input: InputStream = TikaInputStream.get(url, metadata)
//
//                try {
//                    val handler = BodyContentHandler()
//                    val parser = AutoDetectParser()
//                    val parseContext = ParseContext()
//                    parser.parse(input, handler, metadata, parseContext)
//                    println(metadata)
//                    map["text"] = handler.toString().replace("\n|\r|\t".toRegex(), " ")
////                    map["title"] = metadata[TikaCoreProperties.TITLE]
//                    map["pageCount"] = metadata["xmpTPg:NPages"]
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                } finally {
//                    if (input != null) {
//                        try {
//                            input.close()
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }
//                }
//        } catch (exception: Exception) {
//            exception.printStackTrace()
//        }
//        return map
//    }
//
//    companion object {
//        @JvmStatic
//        fun main(arg: Array<String>) {
////            val webPagePdfExtractor = TestExtractor()
////            val extractedMap = webPagePdfExtractor.processRecord(absolutePathOf("check/01.202281-NguyenDanhNam.pdf"))
////            println(extractedMap["text"])
//        }
//    }
//}