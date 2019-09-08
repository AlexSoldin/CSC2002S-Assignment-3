JAVAC = /usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=Vector.class CloudData.class Resultant.class Cloud.class CloudClassification.class

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

run:
	java -cp $(BINDIR) CloudClassification

docs:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java

clean:
	rm $(BINDIR)/*.class
