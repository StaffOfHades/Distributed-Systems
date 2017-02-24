/*
 * Client side implementation for callback of defined functions
 * Called by the user whenever an operation needs to take place.
 */

#include "bitacora.h"
#include <stdio.h>
#include <stdlib.h>

/*
 * Function that receives the host as a string, with the appropiate option,
 * and name as a pointer to a string.
 * Depending on the option sent, either one of two funcions are excuted
 * server side. 
 * The function then print the result.
 */
void bitacora_prog_1(char * host, char option, char ** name) {
	CLIENT * clnt;
	char ** result;
	
	// Try to create the host
	clnt = clnt_create(host, BITACORA_PROG, BITACORA_VERS, "udp");
	
	// Verify it was created succesfully
	if (clnt == NULL) {
		clnt_pcreateerror(host);
		exit(1);
	}
	
	// If the option is add, ask server to add the name
	if (option == 'a') {
		result = add_1(name, clnt);
		
	// Else, if the option is search, ask the server to search for the name
	} else if (option == 's') {
		result = search_1(name, clnt);
	}
	
	// Print the result, either an error or the message returned
	if (result == (char **) NULL) {
		clnt_perror(clnt, "call failed:");
	} else {
		printf("%s\n", *result);
	}
	
	// Make sure to finalize the connection & not leave it open
	clnt_destroy( clnt );
}

/*
 * Main needs to receive 4 arguments of the appropiate format, 
 * and if true, run the client function.
 * Otherwise, exit the program, indicatin why it failed.
 */
int main(int argc, char * argv[]) {
	char *host;

	// 4 arguemnts are needed: program, host, option & name
	if(argc != 4) {
		printf("usage: %s server_host [-option] name \n", argv[0]);
		printf("option:\n");
		printf("-a\tAdd to file\n");
		printf("-s\tSearch in file\n");
		exit(1);
	}
	host = argv[1];
	
	// Verify if a valid option was selected
	if (argv[2][1] != 'a' && argv[2][1] != 's' ) {
		printf("Option \"%s\" is invalid\n", argv[2]);
		exit(1);
	}
	
	// If succesful, run the client function
	bitacora_prog_1(host, argv[2][1], &argv[3]);
}
