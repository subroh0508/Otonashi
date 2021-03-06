package net.subroh0508.otonashi.core.vocabulary.common

import net.subroh0508.otonashi.triples.TripleFacade
import net.subroh0508.otonashi.triples.extensions.get
import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

/*
 * This file was auto generated by otonashi-vocabularies-generator plugin
 *
 */


val Set<IriVocabulary>.rdfC get() = get(RDFClass::class)
val TripleFacade.rdfC get() = iri.rdfC

val Set<IriVocabulary>.rdfP get() = get(RDFProperty::class)
val TripleFacade.rdfP get() = iri.rdfP

val Set<IriVocabulary>.rdfsC get() = get(RDFSchemaClass::class)
val TripleFacade.rdfsC get() = iri.rdfsC

val Set<IriVocabulary>.rdfsP get() = get(RDFSchemaProperty::class)
val TripleFacade.rdfsP get() = iri.rdfsP

val commonVocabularies = arrayOf(RDFClass, RDFProperty, RDFSchemaClass, RDFProperty, RDFSchemaProperty)
