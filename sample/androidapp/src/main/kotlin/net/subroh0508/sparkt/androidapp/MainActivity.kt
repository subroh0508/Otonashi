package net.subroh0508.sparkt.androidapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.subroh0508.sparkt.R
import net.subroh0508.sparkt.core.IriRef
import net.subroh0508.sparkt.core.Prefix
import net.subroh0508.sparkt.core.SparqlQuery
import net.subroh0508.sparkt.core.Var

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        sendRequest.setOnClickListener { sendRequest() }
    }

    private fun sendRequest() {
        val nameVar = Var("name")
        val unitUrlVar = Var("unit_url")
        val unitNameVar = Var("unit_name")
        val titleVar = Var("title")

        val rdfType = IriRef("rdf:type")
        val imasIdol = IriRef("imas:Idol")
        val schemaName = IriRef("schema:name")
        val imasTitle = IriRef("imas:Title")
        val schemaMemberOf = IriRef("schema:memberOf")

        val query = SparqlQuery(
            "https://sparql.crssnky.xyz/spql/imas/query",
            listOf(
                Prefix("schema", "<http://schema.org/>"),
                Prefix("rdf", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"),
                Prefix("imas", "<https://sparql.crssnky.xyz/imasrdf/URIs/imas-schema.ttl#>"),
                Prefix("imasrdf", "<https://sparql.crssnky.xyz/imasrdf/RDFs/detail/>"),
                Prefix("foaf", "<http://xmlns.com/foaf/0.1/>"),
                Prefix("math", "<http://www.w3.org/2005/xpath-functions/math#>"),
                Prefix("xsd", "<https://www.w3.org/TR/xmlschema11-2/#>"),
                Prefix("rdfs", "<http://www.w3.org/2000/01/rdf-schema#>")
            )
        ).where {
            Var("s") be rdfType to imasIdol and
                    schemaName to nameVar and
                    imasTitle to titleVar and
                    schemaMemberOf to unitUrlVar
        }.select(nameVar, titleVar, unitUrlVar).limit(50)


        Log.d("query", query.toString())
        GlobalScope.launch {
            val result = KtorClient.get(query.toString(), Result::class)
            Log.d("result", Json.stringify(Result.serializer(), result))
        }
    }
}