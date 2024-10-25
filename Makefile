# Machines makefile
#
# Written by Jacob Smith

JFLAGS= -cp .:lib/*

.SUFFIXES: .class .java

.java.class:
	javac $(JFLAGS) $<

all: dfa_test nfa_test example  

dfa_test: test/dfa/DFATest.class
nfa_test: test/nfa/NFATest.class

example: main.class Colorful.class

test: test/java/AppTest.java

run_dfa_test: 
	java -cp .:./lib/* test/dfa/DFATest

run_nfa_test: 
	java -cp .:./lib/* test/nfa/NFATest

clean:
		rm -rf *.class;