/*
Shannon Barrett
July 27th, 2014
This is READIPS.C asks for user input then passes the string the pingip.s via pipe

*/
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>

void print_error(const char*, int);
extern int errno;
const int BUFSZE = 128;
int main(int argc, char *argv[]){
	char *mssg = "Enter IP address: ";
	char *end = "exit";
	int pipe1head = atoi(argv[1]);
	while(1){
		int n;
		char*buf = malloc(BUFSZE);
		//print message to terminal
		if(write(STDOUT_FILENO, mssg, strlen(mssg)) == -1)
			print_error("Can not write to STDOUT", errno);
		//take output off terminal
		if((n = scanf("%s", buf)) == EOF)
			print_error("EOF read from STDIN", errno);
		if(strcmp(buf, end) == 0)
			break;
		//pass message via pipe1head
		if(write(pipe1head, buf, strlen(buf)) == -1)
			print_error("Can not write to pipe1head", errno);
		free(buf);
		sleep(1);
	}
	return 0;
}//End of main
void print_error(const char* str, int code){
	printf("%s: %s\n", str, strerror(code));
	exit(-1);

}//end of print_error

//EOF
