/*
 * Backbone of the client, 
 * responsible for executing the procedure calls,
 * and returing the result to the client
 */

#include <memory.h>
#include "bitacora.h"
#include <stdio.h>

/* Default timeout can be changed using clnt_control() */
static struct timeval TIMEOUT = { 25, 0 };

/*
 * Function which allocates the necessary space for the returning result,
 * executes the procedure call for ADD, returning the out value
 * of the call.
 */
char ** add_1(char ** name, CLIENT * clnt) {
	static char * result;
	
	// Set all the bits inside the result to 0
	memset( (char *) &result, 0, sizeof(result));
	
	// Exceute the call, with the in value of name, and the out value of result
	// and verify if it was succeful, returning result if so, or NULL otherwise
	if (clnt_call(clnt, ADD, xdr_wrapstring, name, xdr_wrapstring, &result, TIMEOUT) != RPC_SUCCESS)
		return (NULL);
	return (&result);
}

/*
 * Function which allocates the necessary space for the returning result,
 * executes the procedure call for SEARCH, returning the out value
 * of the call.
 */
char ** search_1(char ** name, CLIENT * clnt) {
	static char * result;

	// Set all the bits inside the result to 0
	memset( (char *) &result, 0, sizeof(result));
	
	// Exceute the call, with the in value of name, and the out value of result
	// and verify if it was succeful, returning result if so, or NULL otherwise
	if (clnt_call(clnt, SEARCH, xdr_wrapstring, name, xdr_wrapstring, &result, TIMEOUT) != RPC_SUCCESS)
		return (NULL);
	return (&result);
}
