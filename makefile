JFLAGS = 
JC = gcc

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	src/io/Console.java \
	src/io/LPLogic.java \
	src/io//LPReader.java \
	src/io/LPWriter.java \
	src/io/Token.java \
	src/model/Comperator.java \
	src/model/Constraint.java \
	src/model/Expression.java \
	src/model/Header.java \
	src/model/LPProgram.java \
	src/model/Objective.java \
	src/model/Term.java \
	src/model/Variable.java \
	src/model/variableType.java \
	src/test/logicTest.java \
	src/test/writeTest.java
default: classes
classes $CLASSES:.java=.class)
clean:
	$(RM) *.class
demo:
	java -jar LinearProgram.jar -demo
	
