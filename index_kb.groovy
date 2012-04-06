#!/usr/bin/env groovy

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.util.Version
import org.apache.lucene.document.Document
import org.apache.lucene.index.FieldInfo
import org.apache.lucene.document.Field
import org.apache.lucene.document.NumericField

println (args[0])

kb_dir = new File(args[0])

indexDirectory = FSDirectory.open(new File("index_kb"))

IndexWriter writer = new IndexWriter(indexDirectory, new IndexWriterConfig(Version.LUCENE_CURRENT, new StandardAnalyzer(Version.LUCENE_CURRENT)))
//writer.useCompoundFile = false

file_count = 0
entity_count = 0

kb_dir.eachFile  { file ->
    if (!file.isDirectory() && file.canRead() && file.exists()) {
        println file
        file.withReader { LineNumberReader reader ->

            def xml_reader = new XmlKBEntityReader(reader)

            while (xml_reader.hasNext()) {
                xml_reader.next()
                def lineNumber = xml_reader.entityLineNumber
                List<String> lines = xml_reader.readLines()
//                println lines
                def entity = new XmlSlurper().parseText(lines.join('\n'))

//                entity.each { println it.name() ; println it ; it.children().each { println "${it.name()} : $it" } ; println() }
//                entity.facts.fact.each { println "${it.name()} ${it.@name} : ${it.text()}" }

                def entity_id = entity.@id.toString()
                def entity_type = entity.@type.toString()
                def entity_title = entity.@wiki_title.toString()
                def entity_name = entity.'@name'.toString()
                def facts_class = entity.facts.'@class'.toString()
                def wiki_text = entity.wiki_text.toString()

//                println lineNumber
//                println entity_id
//                println entity_type
//                println entity_title
//                println entity_name
//                println wiki_text

                def doc = new Document()

                def pathField = new Field("path", file.getPath(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
                pathField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY);
                doc.add(pathField);

                def lineField = new NumericField("line", Field.Store.YES, true)
                lineField.intValue = lineNumber

                def idField = new Field("entity_id", entity_id, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS)
                idField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY)
                doc.add(idField)

                def typeField = new Field("entity_type", entity_type, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS)
                typeField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY)
                doc.add(typeField)

                def titleField = new Field("entity_title", entity_title, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS)
                titleField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY)
                doc.add(titleField)

                def classField = new Field("entity_class", facts_class, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS)
                classField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY)
                doc.add(classField)

                def nameField = new Field("entity_name", entity_name, Field.Store.YES, Field.Index.ANALYZED)
                nameField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY)
                doc.add(nameField)

                def textField = new Field("contents", wiki_text, Field.Store.NO, Field.Index.ANALYZED)
                textField.setIndexOptions(FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
                doc.add(textField)

                writer.addDocument(doc);
                ++entity_count
            }
        }

        ++file_count
    }
}

println "Added ${entity_count} entities from ${file_count} files to kb index."

