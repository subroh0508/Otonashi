package net.subroh0508.otonashi.core.vocabulary.common

import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

/*
 * This file was auto generated by otonashi-vocabularies-generator plugin
 *
 */

object RDFSchemaClass : IriVocabulary(
    "rdfs",
    "Class", "Container", "ContainerMembershipProperty", "Datatype", "Literal", "Resource"
) {
    val `class` by iriStore
    val container by iriStore
    val containerMembershipProperty by iriStore
    val datatype by iriStore
    val literal by iriStore
    val resource by iriStore
}
