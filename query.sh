#!/bin/sh
if [ -z "$LUCENE_HOME" ] ; then
   LUCENE_HOME=/NLP_TOOLS/info_retrieval/lucene/lucene-3.5.0
fi

java -cp $LUCENE_HOME/contrib/demo/lucene-demo-3.5.0.jar:$LUCENE_HOME/lucene-core-3.5.0.jar org.apache.lucene.demo.SearchFiles -index index $*
