#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include<sys/types.h>

const int BUFSIZE = 1000;
char** ipBuffer;
char** msgBuffer;
int ipReady = 0;
int msgReady = 0;
int run = 1;
//Runs when ipReady == 0, meaning there is no readily available address
void* f1(void * p){
	while(run == 1){
		if(ipReady == 0 && msgReady == 0){
			char ipAddress[15];
			printf("Please enter an IP Address:\n ");
			scanf("%s", ipAddress);
			ipBuffer[0] = strdup(ipAddress);
			ipReady = 1;
		}	
	}//end of while loop
}//end f1

//Runs when ipReady == 1, meaning there is an ip address that needs attention
//and when msgReady == 0, meaning there is no message to be printed
void* f2(void *p){
	while(run == 1)
	{
		if(ipReady == 1 && msgReady == 0){
			char firstMsg[] = "ping -c 1 ";
			char secMsg[] = " > output.txt";
			char str[100];
			strcpy(str, firstMsg);
			strcat(str, ipBuffer[0]);
			strcat(str, secMsg);
		 	system(str);	
			FILE *fp = fopen("output.txt", "r+");
			char *ptr = malloc(BUFSIZE);
			int num_read = fread(ptr, 1, BUFSIZE, fp);
			char *delim1 = ",";
			char *delim2 = " ";
			char *saveptr1;
			char *saveptr2;
			char *str1;
			char *str2;
			char *token;
			char *subtoken;
			int j;
			int found_time = 0;
			for(j=0, str1 = ptr; found_time == 0; j++, str1 = NULL){
				token = strtok_r(str1, delim1, &saveptr1);
				if(token == NULL)
					break;
			
				for(str2 = token; ; str2 = NULL){
					subtoken = strtok_r(str2, delim2, &saveptr2);
					if(subtoken == NULL)
						break;
					if(found_time == 1)
						break;
					if(strcmp(subtoken, "time") == 0)
						found_time = 1;
	
				}
			}
			free(ptr);
			fclose(fp);
	
			char format1[] = "Round trip time to ";
			char format2[] = ": ";
			char finalstr[100];
			strcpy(finalstr, format1);
			strcat(finalstr, ipBuffer[0]);
			strcat(finalstr, format2);
			strcat(finalstr, subtoken);
	
			msgBuffer[0] = strdup(finalstr);
			
			free(ipBuffer[0]);
	
			ipReady = 0;
			msgReady = 1;
		}
	}//End while loop

}//end f2

//Runs when msgReady == 1, meaning there is a message in the buffer 
void* f3(void *p){
	while(run == 1){
		if(msgReady == 1){
			printf("%s\n", msgBuffer[0]);
			msgReady = 0;
			free(msgBuffer[0]);
		}
	}//end while loop
}//end f3
int main(int argc, char *argv[]){
	ipBuffer = malloc(BUFSIZE);
	if(ipBuffer == NULL){
		printf("Error allocating memory.\n");
		exit(1);
	}
	msgBuffer = malloc(BUFSIZE);
	if(msgBuffer == NULL){
		printf("Error allocating memory.\n");
		exit(1);
	}
	memset(ipBuffer, 0, BUFSIZE);
	memset(msgBuffer, 0, BUFSIZE);
	pthread_t thread_one;
	pthread_t thread_two;
	pthread_t thread_three;
	int o, t, e;
	o = pthread_create(&thread_one, NULL, f1, NULL);
	t = pthread_create(&thread_two, NULL, f2, NULL);
	e = pthread_create(&thread_three, NULL, f3, NULL);
	pthread_join(thread_one, NULL);
	pthread_join(thread_two, NULL);
	pthread_join(thread_three, NULL);
	
	
	free(ipBuffer);
	free(msgBuffer);
	return 0;
}
//END OF MAIN
