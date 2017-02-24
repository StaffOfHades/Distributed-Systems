/*
 * Header file with all definitions necessary to run the project,
 * as well as the methods to be exported locally.
 * Depending on the compiler, different definitions
 * and exporting are used.
 */


#ifndef _BITACORA_H_RPCGEN
	#include <rpc/rpc.h>

	#define _BITACORA_H_RPCGEN
	#define RPCGEN_VERSION	199506
	#define BITACORA_PROG ((rpc_uint)0x23451111)
	#define BITACORA_VERS ((rpc_uint)1)

	#ifdef __cplusplus
		#define ADD ((rpc_uint)1)
		#define SEARCH ((rpc_uint)2)

		extern "C" char ** add_1(char **, CLIENT *);
		extern "C" char ** add_1_svc(char **, struct svc_req *);
		
		extern "C" char ** search_1(char **, CLIENT *);
		extern "C" char ** search_1_svc(char **, struct svc_req *);

	#elif __STDC__
		#define ADD ((rpc_uint)1)
		#define SEARCH ((rpc_uint)2)
		
		extern char ** add_1(char **, CLIENT *);
		extern char ** add_1_svc(char **, struct svc_req *);
		
		extern char ** search_1(char **, CLIENT *);
		extern char ** search_1_svc(char **, struct svc_req *);

	#else /* Old Style C */
		#define ADD ((rpc_uint)1)
		#define SEARCH ((rpc_uint)2)
		
		extern char ** add_1();
		extern char ** add_1_svc();
		extern char ** search_1();
		extern char ** search_1_svc();

	#endif /* Old Style C */

#endif /* !_BITACORA_H_RPCGEN */
