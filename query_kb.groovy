#!/usr/bin/env groovy

import org.apache.lucene.store.FSDirectory
import org.apache.lucene.index.IndexReader
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.queryParser.QueryParser
import org.apache.lucene.util.Version
import org.apache.lucene.search.TermQuery
import org.apache.lucene.index.Term
import org.apache.lucene.search.Query

indexDirectory = FSDirectory.open(new File("index_kb"))

searcher = new IndexSearcher(indexDirectory)
analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT)
parser = new QueryParser(Version.LUCENE_CURRENT, "contents", analyzer)

args = args as List

while (args && (args.head().startsWith('--'))) {
    def field = args.remove(0).substring(2)
    def value = args.remove(0)
    do_query(new TermQuery(new Term(field, value)))
}

if (args) do_query(parser.parse(args.join(' ')))

def do_query(Query query)
{
    println query

    hits = searcher.search(query, 5)

    println "${hits.totalHits} hits"
    
    for (hit in hits.scoreDocs) {
	    println()

        def doc = searcher.doc(hit.doc)
        println(doc.get("path"))
//        println doc.get("line_number")
//        println doc.get("line_count")
        doc.fields.each { println it }
    }
}
