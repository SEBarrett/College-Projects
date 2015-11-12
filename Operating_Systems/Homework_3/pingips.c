/*
Shannon Barrett
July 27th, 2014
This is PINGS.C 
takes message from pipe, uses ping command then sends the output via pipe
to parsetext.c

*/
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/shm.h>
#include <unistd.h>
#include <string.h>
#include <ctype.h>
#include <sys/ipc.h>
#include <errno.h>


void print_error(const char*, int);
extern int errno;
const int BUFSIZE = 300;

int main(int argc, char *argv[]){
	char firstMsg[] = "ping -c 1 ";
	char secMsg[] = " > output.txt";
	char str[100];
	char msg[275], line[80];
	char *msgptr;
	char ch;
	int pipe1tail = atoi(argv[1]);
	int pipe2head = atoi(argv[2]);
	int n;
	while(1){
		sleep(1);
		int length;
		char *buf = malloc(BUFSIZE);
		FILE *fp;
		//read input from readips.c
		if((n = read(pipe1tail, buf, BUFSIZE-2)) == -1)
			print_error("Can not read from pipe1tail", errno);
		//create message to be sent to system
		strcpy(str, firstMsg);
		strcat(str, buf);
		strcat(str, secMsg);
		system(str);
		//open the output file
		fp = fopen("output.txt", "r+");
		if(fp == NULL){
			print_error("Error opening file", errno);
		}
		//copy contents of file into msg
		while(fgets(line, 80, fp) != NULL){
			strcat(msg, line);
		}
		length = strlen(msg);
		//set ending of msg to null
		msg[length] = '\0';
		//write message to pipe2head
		if(write(pipe2head, msg, 300) == -1)
			print_error("Can not write to pipe2head", errno);
		msg[0] = '\0';
		fclose(fp);
		free(buf);
	}
	return 0;
}
//end of main
void print_error(const char* str, int code){
	printf("%s: %s\n", str, strerror(code));
	exit(-1);	
}
//end of print_error
//EOF

