TARGET=manual

RM = rm -f

all: $(TARGET).dvi 

pdf: $(TARGET).pdf

ps: $(TARGET).ps

spellcheck:
	find . -type f -name '*.tex' -exec ./mspell.sh {} \; | more

$(TARGET).ps: $(TARGET).dvi
	@echo "============================"
	@echo "Compiling postscript file..."
	@echo "============================"
	dvips $(TARGET).dvi

$(TARGET).pdf: $(TARGET).dvi
	@echo "====================="
	@echo "Compiling pdf file..."
	@echo "====================="
	dvipdf $(TARGET).dvi

$(TARGET).dvi: $(TARGET).tex $(TARGET).toc $(TARGET).bbl $(TARGET).ind $(TARGET).nls
	@echo "===================="
	@echo "Creating dvi file..."
	@echo "===================="
	latex --src-specials $(TARGET).tex
	latex --src-specials $(TARGET).tex

$(TARGET).bbl: $(TARGET).aux 
	@echo "========================="
	@echo "Compiling bibliography..."
	@echo "========================="
	bibtex $(TARGET)

$(TARGET).toc: $(TARGET).tex *.tex
	@echo "===================="
	@echo "Creating toc file..."
	@echo "===================="
	latex --src-specials $(TARGET).tex

$(TARGET).ind: $(TARGET).tex *.tex $(TARGET).ist $(TARGET).idx
	@echo "======================"
	@echo "Creating index file..."
	@echo "======================"
	makeindex -s $(TARGET).ist -g $(TARGET)

$(TARGET).nls: $(TARGET).tex *.tex $(TARGET).ist $(TARGET).idx
	@echo "======================"
	@echo "Creating glossary file..."
	@echo "======================"
#	makeindex $(TARGET).glo -s nomencl.ist -o $(TARGET).gls
	makeindex $(TARGET).nlo -s nomencl.ist -o $(TARGET).nls

clean:
	@echo "==============="
	@echo "Cleanung out..."
	@echo "==============="
	$(RM) *.aux
	$(RM) *.ind
	$(RM) *.idx
	$(RM) *.ilg
	$(RM) *.glo
	$(RM) *.gls # old glossary file!
	$(RM) *.nls
	$(RM) *.lof
	$(RM) *.lot
	$(RM) *.toc
	$(RM) *.bbl
	$(RM) *.blg
	$(RM) *.dvi
	$(RM) *.ps
	$(RM) *.pdf

edit: $(TARGET).tex
	@echo "================="
	@echo "Starting Emacs..."
	@echo "================="
	kile $(TARGET).tex 2>&1 &

show:
	@echo "================"
	@echo "Starting KDVI..."
	@echo "================"
	kdvi $(TARGET).dvi >/dev/null 2>&1 &

showpdf:
	@echo "================"
	@echo "Starting KPDF..."
	@echo "================"
	okular $(TARGET).pdf >/dev/null 2>&1 &

showps:
	@echo "======================"
	@echo "Starting KGhostview..."
	@echo "======================"
	kghostview $(TARGET).ps >/dev/null 2>&1 &
