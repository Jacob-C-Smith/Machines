# Machines makefile
#
# Written by Jacob Smith

JFLAGS= -cp .:lib/*

.SUFFIXES: .class .java

.java.class:
	javac $(JFLAGS) $<

all: dfa_test example # nfa_test  

dfa_test: test/dfa/DFATest.class

example: main.class Colorful.class

test: test/java/AppTest.java

run_dfa_test: 
	java -cp .:./lib/* test/dfa/DFATest

clean:
		rm -rf *.class;