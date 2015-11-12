all: test

test: readips pingips parsetext
	gcc main.c -o test
readips: readips.c
	gcc readips.c -o readips
pingips: pingips.c
	gcc  pingips.c -o pingips
parsetext: parsetext.c
	gcc parsetext.c -o parsetext




clean: 
	rm test readips pingips parsetext output.txt
