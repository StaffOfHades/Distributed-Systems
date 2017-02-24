/*
 * Server side implementation of the two function ADD and SEARCH. 
 * Called by the client whenever an operation is needed2
 */

#include "bitacora.h"
#include <time.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

/*
 * Function that receives a string in the form of a pointer to a char pointer, 
 * gets the current time as a string, 
 * and saves them both into file for permanent storage.
 * The function then returns that same string as a result for the client.
 */
char ** add_1_svc(char ** name, struct svc_req *rqstp) {
	static char * result;
	
	// Create a time reference
	time_t timer = time(NULL);
	
	// Open the file
	FILE * file = fopen("bitacora_data.txt", "a+"); 
	
	// Make sure the file can be opened
	if (file == NULL) {
		printf("Error opening file!\n");
    	exit(1);
	}
	
	// Get the current time
	char * time_ = asctime(localtime(&timer));
	
	// Saves the time and name to a string, in order to return it
	asprintf(&result, "%s %s", *name, time_);

	// Saves the esult & close the file
	fprintf(file, "%s", result);
	fclose(file);

	// Return the result
	return &result;
}

/*
 * Function that receives a string in the form of a pointer to a char pointer, 
 * open the file, and then searches for the number of occurrences for the name.
 * The function then returns the number of times a name was found to the client.
 */
char ** search_1_svc(char ** name, struct svc_req *rqstp) {
	static char * result;

	// Open the file
	FILE * file = fopen("bitacora_data.txt", "r");

	// Make sure the file can be opened
	if (file == NULL){
    	printf("file does not exists");
   	 	return 0;
	}
	
	// Create a buffer of 100 bytes, and a string
 	char buff[1000];
  	char * s;
	
	// Max line size is set to 300
	const size_t line_size = 300;
	char * line = malloc(line_size);
	
	// Count starts at 0
	int count = 0;
	
	// As long we can find a line, keep searching for an occurence, and if found, update count
	while (fgets(line, line_size, file) != NULL) {
	  	s = strstr(line, *name);
	 	if (s != NULL) {
			count++;
	    }
	}
	
	// Make a string with the number of occurences found & return it
	asprintf(&result, "Server Found, %s %d times", *name, count);
	
	return &result;
}
