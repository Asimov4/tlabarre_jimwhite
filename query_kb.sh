#!/bin/sh
if [ -z "$LUCENE_HOME" ] ; then
   LUCENE_HOME=/NLP_TOOLS/info_retrieval/lucene/lucene-3.5.0
fi

if [ -z "$GROOVY_HOME" ] ; then
   GROOVY_HOME=groovy-1.8.6
fi

export CLASSPATH=src:$LUCENE_HOME/lucene-core-3.5.0.jar

# Examples:
# ./query_kb.sh --entity_ref E0175811
# ./query_kb.sh --entity_id E0662857 entity_id:E0662857
# ./query_kb.sh --entity_class 'Infobox Philosopher' --entity_class Infobox_Philosopher

./query_kb.groovy $*
