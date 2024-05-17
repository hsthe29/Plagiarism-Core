package hust.thehs.core.services.plagiarism

import hust.thehs.core.services.plagiarism.text_extractor.TextExtractor
//import org.bouncycastle.asn1.x500.style.RFC4519Style.title
import java.util.*


class Plagiarism {

    fun run() {
//        val textResult: String
//
//        /*
//        Parse text uploaded file.
//         */
//        val textExtractor = TextExtractor(uploadedDoc)
//        textExtractor.run()
//        val uploadedText = textExtractor.text
//        logNewFile("uploadedText.txt", uploadedText)
//
//        val language: String = Utils.detectLanguage(uploadedText).toString()
//
//        if (language != "vi") {
//            result = ErrorHandler.errorResponse(ErrorHandler.UNSUPPORTED_LANGUAGE)
//            textResult = result.toString()
//            writeResult(textResult, false)
//            return
//        } else {
////                convert to pdf before text extractor due to error when convert vietnamese from doc to pdf
//            val extension: String = FilenameUtils.getExtension(originalFileName)
//            if (extension.lowercase(Locale.getDefault()) != "pdf") {
////                result = ErrorHandler.errorResponse(ErrorHandler.WRONG_FORMAT);
////                textResult = result.toString();
////                writeResult(textResult, false);
////                return;
//                this.uploadedDoc = convertToPDF(this.uploadedDoc, extension)
//                //                check if this.uploadedDoc exists, not exists -> convert error
//            }
//
//            /*
//            Search for candidate documents.
//             */
//            val candidateSearch: CandidateSearch = CandidateSearch(
//                uploadedText, nCandidates, logdir, vi2en,
//                givenKeywords, extractDocumentKeywords
//            )
//            candidateSearch.run()
//
//            val searchFiles: JSONArray = candidateSearch.getCandidateDocs()
//            var candidateDocs: JSONArray? = getCandidateDocs(searchFiles)
//
//            /*
//        Check plagiarism
//         */
//            val textAlignment: TextAlignment =
//                TextAlignment(uploadedText, candidateDocs, threshold, useLog, vi2en, checkReferences)
//            textAlignment.run()
//            /*
//        Filter candidateDocs, remove doc which has no pairs
//         */
//            candidateDocs = filterCandidateDocs(candidateDocs)
//            textAlignment.setCandidateDocs(candidateDocs)
//            /*
//        Write result
//         */
//            val response: JSONObject = JSONObject()
//            response.put("n", originalFileName)
//            response.put("t", uploadedText)
//            response.put("s", candidateDocs)
//            response.put("m", textAlignment.getIdxMainContents())
//            response.put("submitTime", submitTime)
//            response.put("releaseTime", getCurrentTime())
//            response.put("checkReferences", checkReferences)
//            response.put("vi2en", vi2en)
//
//            response.put("accountName", accountName)
//            response.put("studentNumber", studentNumber)
//            response.put("program", program)
//            response.put("title", title)
//            response.put("userEmail", userEmail)
//
//            //        info
//            info.put("nSentences", textAlignment.getnSusSentences())
//            senBasedResult = textAlignment.convertResultToSentenceBased()
//            linkFiles = textAlignment.getLinkFiles()
//            info.put("nSimilarSentences", senBasedResult.keySet().size())
//            response.put("nSentences", textAlignment.getnSusSentences())
//            response.put("nSimilarSentences", senBasedResult.keySet().size())
//
//            percentage = info.getInt("nSimilarSentences") * 100f / info.getInt("nSentences")
//            response.put("r", percentage)
//            val extraPercentages: FloatArray = calculatePlagiarismPercentage(
//                senBasedResult,
//                response.getJSONArray("s"), textAlignment.getnSusSentences()
//            )
//            response.put("ratioInternalUniversity", extraPercentages[0])
//            response.put("ratioForeignUniversity", extraPercentages[1])
//            response.put("ratioInternet", extraPercentages[2])
//
//            //                for highlight and report
//            val marks = ArrayList<Int>()
//            val it: Iterator<String> = senBasedResult.keys()
//            val keys = ArrayList<Int>()
//            while (it.hasNext()) keys.add(it.next().toInt())
//            Collections.sort(keys)
//            val susSenIdx2Mark: JSONObject = JSONObject()
//            for (i in keys.indices) susSenIdx2Mark.put("" + keys[i], i + 1)
//
//            val keysArray: JSONArray = JSONArray()
//            for (key in keys) keysArray.put(key)
//            response.put("susSenIndices", keysArray)
//            response.put("susSenIdx2Mark", susSenIdx2Mark)
//            result = response
//
//            //            write json result to file
//            textResult = searchForHighlight().toString()
//            writeResult(textResult, true)
//        }
    }
}