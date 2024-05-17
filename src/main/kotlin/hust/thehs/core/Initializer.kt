package hust.thehs.core

import hust.thehs.absolutePathOf
import kotlinx.coroutines.sync.Semaphore
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


object Initializer {
    val semaphoreVnCoreNLP = Semaphore(1)
    val VI_STOPWORDS = readStopWordsFromFile("stopwords/stopwords_vi.txt")
    val EN_STOPWORDS = readStopWordsFromFile("stopwords/stopwords_en.txt")

    private fun readStopWordsFromFile(resourceFile: String): HashSet<String> {
        var line: String
        val stopWords = HashSet<String>()
        File(absolutePathOf(resourceFile)).forEachLine {
            line = it.trim()
            if (line.isNotEmpty()) {
                stopWords.add(line)
            }
        }
        return stopWords
    }
}