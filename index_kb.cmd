####################
#
# James White UW ID 1138573 Net ID jimwhite
# Thibaut Labarre Net ID tlabarre
# LING 571 Winter 2012, Professor Farrar
# DS2 - Index TAC 2009 Knowledge Base documents 
#
####################

LUCENE_HOME=/NLP_TOOLS/info_retrieval/lucene/lucene-3.5.0
GROOVY_HOME=groovy-1.8.6

Universe   = vanilla

Environment = JAVA_OPTS=-Xmx500m;PATH=$(GROOVY_HOME)/bin:/usr/kerberos/bin:/usr/local/bin:/bin:/usr/bin:/opt/git/bin:/opt/scripts:/condor/bin;CLASSPATH=src:$(LUCENE_HOME)/lucene-core-3.5.0.jar
Executable  = ./index_kb.groovy
Arguments   = /corpora/LDC/LDC09E58/58/data
Log         = index_kb.log
Output 		= index_kb.out
Error	    = index_kb.err
Notification=Error
Queue
