/*
 *
 * Simulation of the lottery system algorithm 
 * Shannon Barrett
 * July 16th, 2014
 * suggested command to execute: gcc lottery.c -o test -lm 
 */ 

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>


int main(int argc, char* argv[]){
	int DEBUG = 1; // if 0 debugging statements will print if 1 only required statements will print
	int numP; // user input from command line. Number of Processes.
	int numS; // user input from command line. Number of Seconds
	int randNum;// user input from command line. random number generator seed value
	int i; // used in for loops
	int c; // used in for loops
	int counter; // number of occurances of each process
	int lowerBound; //floor of percentages when testing range
	int upperBound; // ceiling of percentages when testing range
	int val; // random value chosen 
	double pRunTime; // actual run time for each process
	double num; //assigned percentage
	int summationTotal = 0;// bottom half of the fraction
	time_t sec; // time the program starts running the simulation
	time_t difference; //time when the program needs to stop the simulation
	char* one; // first value of user input
	char* two; // second value of user input
	char* three; // third value of user input
	int *data; // stores the order the processes run
	int *totals; // stores the frequencies the processes are run
	data = malloc(sizeof(int));
	int sizeofList = 0; /* counter of list of processes */ 
	//check for proper number of user input from command line
	if(argc != 4){
		printf("usage: %s filename\n", argv[0]);
		exit(5);
	}
	else if(argc == 4){
		// check that proper number of elements are integers
		for(i = 1; i < 4; i++){
			if((isdigit(*argv[i])) == 0){
				printf("Usage: %s filename\n", argv[0]);
				exit(5);
			}
		}
		//assign argv[] values to char*
		if(DEBUG == 0) printf("Proper number of arguments entered\n");
		one = argv[1];
		two = argv[2];
		three = argv[3];
		if(DEBUG == 0) printf("Num of Processes: %s, Num of Seconds: %s, Random Generator Seed: %s\n", argv[1], argv[2], argv[3]);
		
		//convert char* to int
		numP = atoi(one);
		numS = atoi(two);
		randNum = atoi(three);
		if(DEBUG == 0) printf("Num of Processes: %d Num of Seconds: %d Random Generator Seed: %d\n", numP, numS, rand);
	}
	totals = malloc((numP + 1)*sizeof(int));//reserve memory for each one of the processes frequencies
	srand(randNum); // seed the number generator
	sec = time(NULL); //start of the time
	difference = sec + numS; //start of the time plus the seconds the program needs to run 
	if(DEBUG == 0) printf("Time %ld Running Time: %d\n", sec, difference);
	int totalProcesses[numP];//assign array to hold each of the processes
	int percentage[numP]; // assign array to hold the percentage value that they receive
	//assigns each value the proper number of processes
	for(i = 0; i < numP; i++){
		totalProcesses[i] = (i + 1);
	}
	//print the processes 
	if(DEBUG == 0){
		for(i = 0; i < numP; i++){
			printf("Processes #%d value: %d\n", i, totalProcesses[i]);
		}
	}
	//create the summation 
	for(i = 1; i <= numP; i ++){
		summationTotal = (summationTotal + i);
	}
	if(DEBUG == 0) printf("Summation Total: %d\n", summationTotal);
	while(sec != difference){	
		if(DEBUG == 0) printf("New Process Being Chosen\n");
		for(i = 0; i < numP; i++){
			if(DEBUG == 0) printf("Processes #%d value: %d\n", i, totalProcesses[i]);
			num = (((double)totalProcesses[i]/(double)summationTotal)*100);
			num = ceil(num);
			if(DEBUG == 0) printf("Number: %f\n", num);
			percentage[i] = (int)num;
		}
		if(DEBUG == 0){
			for(i = 0; i <numP; i ++){
				printf("Percentages: %d\n", percentage[i]);
			}
		}
		val = (rand() % 100);
		if(DEBUG == 0) printf("Random Value: %d\n", val);
		// check if the value is in range of the processes percentages
		lowerBound = 0;
		upperBound = percentage[0];
		for(i = 0; i < (numP); i++){
			if(DEBUG == 0) printf("lowerBound = %d upperBound = %d Value in Question: %d\n", lowerBound, upperBound, val);
			if(val >= lowerBound && val < upperBound){
				if(sizeofList > 0){//increases size of memory as the array expands
					data  = realloc(data, ((sizeofList + 1)*sizeof(int)));
				}
				printf(" %d ", totalProcesses[i]);
				data[sizeofList] = totalProcesses[i]; /* put the value into the data array */ 
				if(DEBUG == 0) printf("%d in range of %d and %d\n", val, lowerBound, upperBound);			
				sizeofList ++; 
				if(DEBUG == 0)printf("Data Storage Slot: %d\n", sizeofList);
				break;
			} 
			else{ // increase the range of percentages if value was not in range
				lowerBound = upperBound;
				upperBound = upperBound + percentage[i+1];
				if(DEBUG == 0)printf("lowerBound = %d upperBound = %d\n", lowerBound, upperBound);
			}
		}
		sec  = time(NULL); // increases sec
	} //end of WHILE loop
	if(DEBUG == 0) printf("Processes in Order:\n");
	//prints the order in which the processes ran 
	for(i = 0; i < sizeofList; i++){
		if(DEBUG == 0) printf(" %d ", data[i]);
	}
	// counts the frequency of each of the processes
	for(c = 0; c < numP; c++){
		counter = 0;
		for(i = 0; i <= sizeofList; i++){
			if(data[i] == totalProcesses[c]){
				counter++;
			}
			if(i == sizeofList){
				totals[c] = counter;
			}
		}
	}
	if(DEBUG == 0) printf("\n");
	if(DEBUG == 0) printf("Occurences for each process:\n");
	for(i = 0; i < numP; i++){
		if(DEBUG == 0) printf("Process #%d: %d ", totalProcesses[i], totals[i]);
	}
	printf("\n");
	// final output print statement
	for(i = 0; i < numP; i++){	
		pRunTime =((double)totals[i]/(double)sizeofList);
		printf("Process #%d Assigned Percentage: %d/%d Running Time: %f seconds \n", totalProcesses[i], totalProcesses[i], summationTotal, pRunTime);
	}
	free(data); // release stored memory for data and totals
	free(totals);
	
	return 0;
}


