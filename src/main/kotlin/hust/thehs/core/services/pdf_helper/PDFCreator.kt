package hust.thehs.core.services.pdf_helper

class PDFCreator {
}

fun main() {
    var str = """
<html>

<body>
        <p style="margin:0cm;margin-bottom:.0001pt">Bonjour,<o:p></o:p>
        </p>
    </div>
</body>
</html>""".trimIndent()
    var myregex = """<body(.*?)>(.*?)</body>""".toRegex(RegexOption.DOT_MATCHES_ALL)
    println(str)

    var matches = myregex.find(str)
    val names = matches?.value
    println(names)
}