package net.subroh0508.sparkt.core.order

import net.subroh0508.sparkt.core.triples.TripleItem
import net.subroh0508.sparkt.core.triples.Var

data class Asc(private val value: Var) : TripleItem {
    override fun toString(): String = "ASC($value)"
}