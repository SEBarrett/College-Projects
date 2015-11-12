/*
 * server.c
 *
 * From APitUE, 2nd edition
 *
 */

#include <netdb.h> 
#include <errno.h> 
#include <syslog.h> 
#include <sys/socket.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <signal.h>
#include <fcntl.h>

#define BUFLEN 300 
#define QLEN 10

#ifndef HOST_NAME_MAX 
	#define HOST_NAME_MAX 256 
#endif

extern int errno;

int initserver(int, const struct sockaddr *, socklen_t, int);
void serve(int);
void print_error(char *);
void daemonize(const char *);

int main(int argc, char *argv[]) {
	struct addrinfo 	*ailist, *aip; 
	struct addrinfo 	hint;
	int 			sockfd,	err, n; 
	char 			*host;

    	if (argc != 1) {
        	printf("usage: ping");
		return 0;
	}

	#ifdef _SC_HOST_NAME_MAX
		n = sysconf(_SC_HOST_NAME_MAX); 
		if(n<0) /*bestguess*/
	#endif
        
	n = HOST_NAME_MAX;
    	host = malloc(n);
    
	if (host == NULL)
		print_error("malloc error"); 
	if (gethostname(host, n) < 0)
		print_error("gethostname error"); 
	else 
		printf("hostname: %s\n", host);

	//daemonize("ping"); 
	hint.ai_flags = AI_CANONNAME; 
	hint.ai_family = 0; 
	hint.ai_socktype = SOCK_STREAM; 
	hint.ai_protocol = 0; 
	hint.ai_addrlen = 0; 
	hint.ai_canonname = NULL; 
	hint.ai_addr = NULL; 
	hint.ai_next = NULL;

	if ((err = getaddrinfo(host, "15999", &hint, &ailist)) != 0) { 
		print_error("ping: getaddrinfo error: ");
        	exit(1);
	}

	printf("server: attempting to initialize\n");
	
	for (aip = ailist; aip != NULL; aip = aip->ai_next) {
		printf("initializing server\n");
		if ((sockfd = initserver(SOCK_STREAM, aip->ai_addr, aip->ai_addrlen, QLEN)) >= 0) {
			printf("server initialized\n");
            		serve(sockfd);
			printf("client has been served\n");
			exit(0); 
		}
	}

	exit(1); 
}

void serve(int sockfd)
{
	int clfd;
	int n;
	int length;
	FILE *fp;
	char buf[BUFLEN];
	char firstMsg[] = "ping -c 1 ";
	char secMsg[] = " > output.txt";
	char str[100];
	char line[80];
	char msg[500];
	for (;;) {
		
        	clfd = accept(sockfd, NULL, NULL);
        	if (clfd < 0) {
			syslog(LOG_ERR, "ping: accept error: %s", strerror(errno));
			exit(1); 
		}
		while ((n = recv(clfd, buf, BUFLEN, 0)) > 0){ 
			write(STDOUT_FILENO, buf, n);
			 printf("\nRecieving ip address: %s\n", buf);
                	strcpy(str, firstMsg);
                	strcat(str, buf);
                	strcat(str, secMsg);
                	printf("Message being sent to system: %s\n", str);
                	system(str);
               	 	fp = fopen("output.txt", "r+");
                	if(fp == NULL)
                        	print_error("Error opening file");
                	while(fgets(line, 80, fp) != NULL){
                        	strcat(msg, line);
                	}
			printf("Message being sent from server: %s EOM\n", msg);
                	length = strlen(msg);
			printf("Message length %d\n", length);
                	msg[length] = '\0';
			length = strlen(msg);
                	send(clfd, msg, length , 0);
                	fclose(fp);
               	}
		if (n < 0)
                       	print_error("recv error");


        	close(clfd);
	} 
}

int initserver(int type, const struct sockaddr *addr, socklen_t alen, int qlen) {
    	int fd;
    	int err = 0;

	if ((fd = socket(addr->sa_family, type, 0)) < 0) 
		return(-1);

	if (bind(fd, addr, alen) < 0) { 
		err = errno;
        	goto errout;
    	}

	if (type == SOCK_STREAM || type == SOCK_SEQPACKET) { 
		if (listen(fd, qlen) < 0) {
			err = errno;
            		goto errout;
        	}
    	}

    	return(fd);

errout:
    	close(fd);
	errno = err;
    	return(-1);
}

void print_error(char *str) {
	printf("%s: %s\n", str, strerror(errno));
	exit(1);
}

void daemonize(const char *cmd) {
	int i;
	int fd0; 
	int fd1;
	int fd2;
	pid_t pid;
	struct rlimit rl;
	struct sigaction sa;
	
	umask(0);

	/*
￼￼	 * Get maximum number of file descriptors.
	 */
	if (getrlimit(RLIMIT_NOFILE, &rl) < 0)
		print_error("can't get file limit");

	/*
	 * Become a session leader to lose controlling TTY. 
	 */
	if ((pid = fork()) < 0) 
		print_error("can't fork");
	else if (pid != 0) /* parent */ 
		exit(0);

	setsid();

	/*
	 * Ensure future opens won't allocate controlling TTYs. 
	 */
	sa.sa_handler = SIG_IGN; 
	sigemptyset(&sa.sa_mask); 
	sa.sa_flags = 0;

	if (sigaction(SIGHUP, &sa, NULL) < 0)
		print_error("can't ignore SIGHUP"); 
	if ((pid = fork()) < 0)
		print_error("can't fork"); 
	else if (pid != 0) /* parent */
		exit(0);

	/*
	 * Change the current working directory to the root so 
	 * we won't prevent file systems from being unmounted. 
	 */
	if (chdir("/") < 0)
		print_error("%s: can't change directory to /");

	/*
	 * Close all open file descriptors. 
	 */
	if (rl.rlim_max == RLIM_INFINITY)
    		rl.rlim_max = 1024;
	for (i = 0; i < rl.rlim_max; i++) 
		close(i);

	/*
	 * Attach file descriptors 0, 1, and 2 to /dev/null. 
	 */
	fd0 = open("/dev/null", O_RDWR);
	fd1 = dup(0);
	fd2 = dup(0);
	
	/*
	 * Initialize the log file.
	openlog(cmd, LOG_CONS, LOG_DAEMON);
	if (fd0 != 0 || fd1 != 1 || fd2 != 2) {
		syslog(LOG_ERR, "unexpected file descriptors %d %d %d", fd0, fd1, fd2);
		exit(1); 
	}
  	*/
}

// END OF FILE
