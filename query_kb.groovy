import org.apache.lucene.store.FSDirectory
import org.apache.lucene.index.IndexReader
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.queryParser.QueryParser
import org.apache.lucene.util.Version


indexDirectory = FSDirectory.open(new File("index_kb"))

searcher = new IndexSearcher(indexDirectory)
analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT)
parser = new QueryParser(Version.LUCENE_CURRENT, "contents", analyzer)

query = parser.parse("menn*")

hits = searcher.search(query, 100)

for (hit in hits.scoreDocs) {
    def doc = searcher.doc(hit.doc)
    println( doc.get("path") )
    println doc.get("line_number")
    println doc.get("line_count")
    doc.fields.each { println it }
    println()
}

