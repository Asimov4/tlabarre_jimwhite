####################
#
# James White UW ID 1138573 Net ID jimwhite
# Thibaut Labarre UW ID 1161410 Net ID tlabarre
# LING 573 Spring 2012, Professor Farrar
# D1 Due 2012-04-02
#
####################

LUCENE_HOME=/NLP_TOOLS/info_retrieval/lucene/lucene-3.5.0

Universe   = java

Executable  = $(LUCENE_HOME)/contrib/demo/lucene-demo-3.5.0.jar
JAR_Files	= $(LUCENE_HOME)/contrib/demo/lucene-demo-3.5.0.jar,$(LUCENE_HOME)/lucene-core-3.5.0.jar

Arguments   = org.apache.lucene.demo.SearchFiles -index index -query engineer
Log         = d1-query.log
Output 		= d1-query.out
Error	    = d1-query.err
Notification=Error
Queue
