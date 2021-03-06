package net.subroh0508.otonashi.core.serializer

import kotlinx.serialization.*
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.SerialClassDescImpl
import net.subroh0508.otonashi.core.serializer.rdfelement.RDFElementSerializer

@Serializer(forClass = SparqlResponse.Results::class)
internal class ResultsSerializer<T: Any>(
    private val dataSerializer: KSerializer<T>
) : KSerializer<SparqlResponse.Results<T>> {
    override val descriptor: SerialDescriptor = object : SerialClassDescImpl("SparqlResponse.Results") {
        init {
            addElement("bindings")
        }
    }

    override fun deserialize(decoder: Decoder): SparqlResponse.Results<T> {
        val inp = decoder.beginStructure(descriptor)

        var bindings: List<SparqlResponse.Binding<T>> = listOf()
        loop@ while (true) {
            when (val i = inp.decodeElementIndex(descriptor)) {
                0 -> bindings = inp.decodeSerializableElement(
                        descriptor,
                        i,
                        ArrayListSerializer(RDFElementSerializer(dataSerializer))
                    )
                CompositeDecoder.READ_DONE -> break@loop
                else -> throw SerializationException("Unknown index $i")
            }
        }
        inp.endStructure(descriptor)
        return SparqlResponse.Results(bindings)
    }

    override fun serialize(encoder: Encoder, obj: SparqlResponse.Results<T>) = Unit
}