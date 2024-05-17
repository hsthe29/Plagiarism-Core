package hust.thehs.core

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.SerialName

@Serializable
data class TokenizedSentence(
    val tokens: MutableList<String> = mutableListOf(),
    val posTags: MutableList<String> = mutableListOf(),
    @SerialName("num_words")
    val numWords: Int = 0)

@Serializable
data class PDFTextPage(
    val page: Int,
    val text: String,
    val tokenization:  MutableList<TokenizedSentence> = mutableListOf()
)
