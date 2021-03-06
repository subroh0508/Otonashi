package net.subroh0508.otonashi.vocabulary.generator.internal

import java.io.File

class TtlCodeGenerator(
    private val parentDir: File,
    private val classNamePrefix: String,
    private val packageName: String
) {
    fun execute(rawText: String): List<File> {
        val lines = rawText.split("\n").filter { it.matchQuery() }

        if (lines.isEmpty()) {
            return emptyList()
        }

        val iriPrefix = lines[0].split(":")[0]

        val lineMap: Map<RdfType, MutableList<String>> = lines.fold(
            mutableMapOf(
                RdfType.CLASS to mutableListOf<String>(),
                RdfType.PROPERTY to mutableListOf<String>()
            )
        ) { acc, line ->
            val type = RdfType.fromRawText(line) ?: return@fold acc
            val matchResult = PATTERN.find(line) ?: return@fold acc

            acc[type]?.add(matchResult.groupValues[1].replace("$iriPrefix:", ""))

            acc
        }

        val vocabularyFiles = lineMap.map { (type, attributes) ->
            val code = buildCode(type, iriPrefix, attributes.apply { sort() })

            File(parentDir, "${type.className(classNamePrefix)}.kt").apply { writeText(code) }
        }

        val extFunctionFile = File(parentDir, "ext.kt").apply { writeText(buildExtFuncCode(lineMap, iriPrefix)) }

        return vocabularyFiles + listOf(extFunctionFile)
    }

    private fun buildCode(type: RdfType, iriPrefix: String, attributes: List<String>) = buildString {
        append("package $packageName\n")
        append("\n")
        append("import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary\n")
        append("\n")
        append(COMMENT)
        append("\n")
        append("object ${type.className(classNamePrefix)} : IriVocabulary(\n")
        append("    \"$iriPrefix\"")
        attributes.chunked(10).forEachIndexed { i, chunked ->
            append(",\n")
            append("    ${chunked.joinToString(", ") { "\"$it\"" }}")
        }
        append("\n")
        append(") {\n")
        attributes.forEach { attr ->
            append("    val ${attr.decapitalize()} by iriStore\n")
        }
        append("}\n")
    }

    private fun String.matchQuery() = matches(PATTERN)

    private fun buildExtFuncCode(lineMap: Map<RdfType, MutableList<String>>, iriPrefix: String) = buildString {
        append("package $packageName\n")
        append("\n")
        append("import net.subroh0508.otonashi.triples.TripleFacade\n")
        append("import net.subroh0508.otonashi.triples.extensions.get\n")
        append("import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary\n")
        append("\n")
        append(COMMENT)
        append("\n")

        if (!lineMap[RdfType.CLASS].isNullOrEmpty()) {
            append("val Set<IriVocabulary>.${iriPrefix}C get() = get(${RdfType.CLASS.className(classNamePrefix)}::class)\n")
            append("val TripleFacade.${iriPrefix}C get() = iri.${iriPrefix}C\n")
        }

        append("\n")

        if (!lineMap[RdfType.PROPERTY].isNullOrEmpty()) {
            append("val Set<IriVocabulary>.${iriPrefix}P get() = get(${RdfType.PROPERTY.className(classNamePrefix)}::class)\n")
            append("val TripleFacade.${iriPrefix}P get() = iri.${iriPrefix}P\n")
        }
    }

    companion object {
        private val PATTERN = """^([^<>"\s{}|\\^`]+:[^<>"\s{}|\\^`]+).*a.*([^<>"\s{}|\\^`]+:[^<>"\s{}|\\^`]+)*.*$""".toRegex()
        private val COMMENT = """
            /*
             * This file was auto generated by otonashi-vocabularies-generator plugin
             *
             */

        """.trimIndent()
    }
}