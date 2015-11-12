/*
Shannon Barrett
July 27th, 2014
This is MAIN.C 
This is the driver program. Creates three child processes to 
run the other three programs (readips.c, pingips.c, parsetext.c)
*/

#include <stdio.h>
#include <sys/shm.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <errno.h>


void print_error(const char*, int);
extern int errno;

int main(int argc, char *argv[]){
	pid_t first_pid, second_pid, third_pid;
	int pipe1[2], pipe2[2];
	char *prog1 = "readips";	
	char *prog2 = "pingips";
	char *prog3 = "parsetext";
	char pipe1head[8], pipe1tail[8];
	char pipe2head[8], pipe2tail[8];	
	//create pipe1
	if(pipe(pipe1) == -1){
		print_error("Can not create pipe 1", errno);
	}
	else{
		sprintf(pipe1tail, "%d", pipe1[0]);
		sprintf(pipe1head, "%d", pipe1[1]);
	}
		
	//creation of child processes
	if((first_pid = fork()) == -1)
		print_error("Can not create child", errno);
	else if(first_pid == 0){//child 1
		close(pipe1[0]);
		if(execl(prog1, prog1, pipe1head, NULL) == -1)
			print_error("Cannot exec readips", errno);
	}
	else{ //parent
		close(pipe1[1]);
		if(pipe(pipe2) == -1)
			print_error("Can not create pipe", errno);
		else{
			sprintf(pipe2tail, "%d", pipe2[0]);
			sprintf(pipe2head, "%d", pipe2[1]);
		}
		if((second_pid = fork()) == -1)
			print_error("Can not create child", errno);
		else if(second_pid == 0){ //child 2
			close(pipe2[0]);
			if(execl(prog2, prog2, pipe1tail, pipe2head, NULL) == -1)	
				print_error("Can not exec pingips", errno);
		}	
		else{ //parent
			if((third_pid = fork()) == -1)
				print_error("Can not create child",errno);
			else if(third_pid == 0){ //child 3
				close(pipe2[1]);
				if(execl(prog3, prog3, pipe2tail, NULL) == -1)
					print_error("Can not exec parsetext", errno);
			}
		}
				
	}
	//wait for the three processes to return
	int returnstatus;
	waitpid(first_pid, &returnstatus, 0);
	waitpid(second_pid, &returnstatus, 0);
	waitpid(third_pid, &returnstatus, 0);	

	return 0;
}
//END OF MAIN
void print_error(const char* str, int code){
	printf("%s: %s\n", str, strerror(code));
	exit(-1);
}
//END OF print_error


//END OF FILE
