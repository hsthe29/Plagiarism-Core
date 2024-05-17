//package hust.thehs.core.services.plagiarism.text_alignment.document
//
//import hust.thehs.core.Initializer
//import hust.thehs.core.services.plagiarism.text_extractor.TextExtractor
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import vn.pipeline.Annotation
//import vn.pipeline.VnCoreNLP
//import java.util.*
//import kotlinx.coroutines.launch
//
//
//class ViDocument(text: String, isThesis: Boolean, checkReferences: Boolean) :
//    Document(text, isThesis, checkReferences) {
//
//    override fun buildInvertedIndex(): Unit =
//        with(CoroutineScope(Dispatchers.Main)) {
//            launch {
//                try {
//                    Initializer.semaphoreVnCoreNLP.acquire()
//                    val pipeline = VnCoreNLP(arrayOf("wseg"))
//                    val annotation = Annotation(sentence.text)
//
//                    try {
//                        pipeline.annotate(annotation)
//                    } catch (oom: OutOfMemoryError) {
//                        println("Error: OOM when annotate sentence!")
//                    }
//
//                    val words = annotation.words
//
//                    val processedSentence = buildString {
//                        for (annotatedWord in words) {
//                            val word = annotatedWord.form.lowercase()
//                            if (WordUtils.isMainWord(word)) { // WordDatabase.isIndexWord(word)
//                                updateInvertedIndex(word, i)
//                            }
//
//                            if (WordUtils.isLegalWord(word)) {
//                                append(word)
//                                append(' ')
//                            }
//                        }
//                    }.trim()
//
//                    listOfProcessedSentences.add(Sentence(sentence.index, processedSentence))
//
//                    for (i in 0 until listOfSentences.length()) {
//                        var processedSentence = ""
//                        val annotation = Annotation(listOfSentences.getJSONArray(i).getString(1))
//                        try {
//                            pipeline.annotate(annotation)
//                        } catch (oom: OutOfMemoryError) {
//                            println("Error: OOM when annotate sentence!")
//                        }
//
//                        for (taggedWord in annotation.words) {
//                            val posTag = taggedWord.posTag
//                            val word = taggedWord.form
//                            if (posTag.startsWith("N")) {
//                                // Check key exist or not
//                                updateInvertedIndex(word.lowercase(Locale.getDefault()), i)
//                            }
//
//                            if (posTag != "CH") processedSentence = processedSentence + taggedWord.form + " "
//                        }
////                            val elm: org.json.JSONArray = org.json.JSONArray()
////                            elm.put(listOfSentences.getJSONArray(i).getInt(0))
////                            elm.put(processedSentence.trim { it <= ' ' })
////                            listOfProcessedSentences.put(elm)
//                    }
//                } catch (err: Exception) {
//                    err.printStackTrace()
//                } finally {
//                    Initializer.semaphoreVnCoreNLP.release()
//                }
//            }
//        }
//
//    override fun indexFilteredSentences() {
//        try {
//            if (filteredInvertedIndex.isEmpty()) {
//                Initializer.semaphoreVnCoreNLP.acquire()
//                val pipeline = VnCoreNLP(arrayOf("wseg", "pos"))
//                for (i in 0 until filteredSentences.length()) {
//                    val annotation = Annotation(filteredSentences.getJSONArray(i).getString(1))
//                    //            setAnnotation(listOfSentences.getJSONArray(i).getString(1));
//                    pipeline.annotate(annotation)
//                    //            Init.pipelineAnnotate(annotation);
//                    val annoOfSen = annotation.toString().split("\\n+".toRegex()).dropLastWhile { it.isEmpty() }
//                        .toTypedArray()
//
//
//                    for (annoOfWord in annoOfSen) {
//                        val results = annoOfWord.split("\\t".toRegex()).dropLastWhile { it.isEmpty() }
//                            .toTypedArray()
//                        val word = results[1]
//                        val typeOfWord = results[2]
//
//                        if (typeOfWord.contains("N")) {
//                            // Check key exist or not
//                            updateFilteredInvertedIndex(word, i)
//                        }
//                    }
//                }
//            } else {
//                println("Already indexes. Not do that again.")
//            }
//        } catch (err: Exception) {
//            err.printStackTrace()
//        } finally {
//            Initializer.semaphoreVnCoreNLP.release()
//        }
//    }
//
//}