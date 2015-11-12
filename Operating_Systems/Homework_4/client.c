/*
 * client.c
 *
 * From APitUE, 2nd edition
 *
 */

#include <netdb.h>
#include <errno.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>

#define MAXADDRLEN 256
#define BUFLEN 128

extern int errno;

int connect_retry(int, const struct sockaddr *, socklen_t);
void print_ping(int);
void print_error(char *);
void sendIP(int, char*);

int main(int argc, char *argv[]) {
	struct addrinfo *ailist, *aip; struct addrinfo hint;
	int sockfd, err;
    
	if (argc != 3)
        	print_error("usage: ping hostname");
    
	hint.ai_flags = 0;
    	hint.ai_family = 0;
    	hint.ai_socktype = SOCK_STREAM;
    	hint.ai_protocol = 0;
    	hint.ai_addrlen = 0;
    	hint.ai_canonname = NULL;
    	hint.ai_addr = NULL;
    	hint.ai_next = NULL;

	if ((err = getaddrinfo(argv[1], "15999", &hint, &ailist)) != 0) 
		print_error("getaddrinfo error: ");

	for (aip = ailist; aip != NULL; aip = aip->ai_next) {
		if ((sockfd = socket(aip->ai_family, SOCK_STREAM, 0)) < 0)
			err = errno;
		if (connect_retry(sockfd, aip->ai_addr, aip->ai_addrlen) < 0) {
            		err = errno;
        	} else {
			//send ipaddress in here
                	printf("client sending ipaddress %s\n", argv[2]);
                        sendIP(sockfd, argv[2]);
                        printf("client IP address has been sent\n");
          		print_ping(sockfd);
                        exit(0);
                		
        	}
		
	}

	fprintf(stderr, "can't connect to %s: %s\n", argv[1],
      	strerror(err));
    	exit(1);
}

void print_ping(int sockfd) {
	int n;
	char buf[BUFLEN];
	printf("client Receiving message:\n");
	while ((n = recv(sockfd, buf, BUFLEN, 0)) > 0){ 
		write(STDOUT_FILENO, buf, n);
	}
    	if (n < 0)
       		print_error("recv error");

}

void print_error(char *str) {
	printf("%s: %s\n", str, strerror(errno));
	exit(1);
}

#define MAXSLEEP 128
int connect_retry(int sockfd, const struct sockaddr *addr, socklen_t alen) 
{
	int nsec;

	/*
	 * Try to connect with exponential backoff. 
	 */
	for (nsec = 1; nsec <= MAXSLEEP; nsec <<= 1) { 
		if (connect(sockfd, addr, alen) == 0) 
			return(0); // Connection accepted.
        	
		if (nsec <= MAXSLEEP/2)
            		sleep(nsec); // Delay before trying again.
	}
    	return(-1);
}
void sendIP(int sockfd, char *ipAddr)

{
	printf("sendIP: address: %s\n", ipAddr);
        send(sockfd, ipAddr, strlen(ipAddr), 0);                
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

// END OF FILE	
