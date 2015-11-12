/*
 *Shannon Barrett
July 27th, 2014
 *This is parsetext.c
 *this program takes input from pingips.c and finds the round trip time
from ping, then outputs that value to the terminal.
 */

#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
void print_error(const char*, int);
const int BUFSZE = 300;
const int BUFSIZE = 1000;
extern int errno;
int main(int argc, char *argv[]){
	int pipe2tail = atoi(argv[1]);
	char *delim = "\n";
	char *delim1 = ",";
	char *delim2 = " ";
	char *saveptr;
	char *saveptr1;
	char *saveptr2;
	char *str;
	char *str1;
	char *str2;
	char *orgtoken;
	char *token;
	char *subtoken = NULL;
	int j;
	while(1){	

		char *buf = malloc(BUFSZE);
		int n = 0;
		int found_time = 0;
		char *ptr = malloc(BUFSIZE);
		//take input from pipe2tail
		if((n = read(pipe2tail, buf, BUFSZE)) == -1)
			print_error("Can not read form pipe2tail", errno);
		ptr = buf;
		//parse the message to find rount trip time
		for(j = 0, str = ptr; found_time == 0; j++, str = NULL){	
			orgtoken = strtok_r(str, delim, &saveptr);
			if(orgtoken == NULL)
				break;
			for(str1 = orgtoken; ; str1 = NULL){
				token = strtok_r(str1, delim1, &saveptr1);
				if(token == NULL)
					break;
				for(str2 = token; ; str2 = NULL){
					subtoken = strtok_r(str2, delim2, &saveptr2);
					if(subtoken == NULL)
						break;
					if(found_time == 1){
						break;
					}
					if(strcmp(subtoken, "time") == 0)
						found_time = 1;
				}
			}
		}	
		//write the roundtrip time to the terminal
		if(write(STDERR_FILENO, subtoken, strlen(subtoken)) == -1)
			print_error("Can not write to STDERR", errno);
		printf("\n");
		free(buf);
		sleep(1);
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
