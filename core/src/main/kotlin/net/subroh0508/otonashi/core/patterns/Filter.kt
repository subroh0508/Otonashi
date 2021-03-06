package net.subroh0508.otonashi.core.patterns

import net.subroh0508.otonashi.core.operators.nodes.Node
import net.subroh0508.otonashi.core.vocabulary.Vocabulary

class Filter internal constructor(
    private val node: Node? = null,
    vocabulary: Vocabulary
) : GraphPattern("FILTER", vocabulary) {
    override fun toString()
            = if (node != null) {
                buildString {
                    append("$prefix(")
                    append(node.toString())
                    append(")")
                }
            } else {
                super.toString()
            }
}