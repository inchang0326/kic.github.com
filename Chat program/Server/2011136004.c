#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <fcntl.h>
#include <sys/socket.h>
#include <sys/file.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <time.h>

#define MAXLINE 511
#define MAX_SOCK 1024

typedef struct {
	char client_id[50];
	char client_ip[50];
	char client_ftime[50];
}clientinfo;

clientinfo myClient[10];

char *EXIT_STRING = "exit";
char *FTIME_STRING = "hello";

int maxfdp1;
int num_members = 0;
int clisock_list[MAX_SOCK];
int listen_sock;

void addClient(int s, struct sockaddr_in *newcliaddr);
int getmax();
void removeClient(int s);
int tcp_listen(int host, int port, int backlog);
void errquit(char *mesg) { perror(mesg); exit(1); }
void display();

int main(int argc, char *argv[]) {
	struct sockaddr_in cliaddr;
	char buf[MAXLINE + 1];
	int i, j, nbyte, accp_sock, addrlen = sizeof(struct sockaddr_in);
	fd_set read_fds;

	if(argc != 2) {
		printf("Usage : %s port\n", argv[0]);
		exit(0);
	}

	listen_sock = tcp_listen(INADDR_ANY, atoi(argv[1]), 5);

	while(1) {
		FD_ZERO(&read_fds);
		FD_SET(listen_sock, &read_fds);
		for(i=0; i<num_members; i++)
			FD_SET(clisock_list[i], &read_fds);
		maxfdp1 = getmax() + 1;
		puts("Server >> Wating for requests from clients..");
		if(select(maxfdp1, &read_fds, NULL, NULL, NULL) < 0)
			errquit("Select Failed");

		if(FD_ISSET(listen_sock, &read_fds)) {
			accp_sock = accept(listen_sock, (struct sockaddr *)&cliaddr, &addrlen);
			if(accp_sock == -1) errquit("Accept Failed");
			addClient(accp_sock, &cliaddr);
			display();
			printf("Server >> No.%d member added\n", num_members);
		}

		for(i=0; i<num_members; i++) {
			if(FD_ISSET(clisock_list[i], &read_fds)) {
				nbyte = recv(clisock_list[i], buf, MAXLINE, 0);
				if(nbyte<=0) {
					removeClient(i);
					continue;
				}
				buf[nbyte] = 0;
				if(strstr(buf, EXIT_STRING) != NULL) {
					removeClient(i);
					continue;
				}
				if(strstr(buf, FTIME_STRING) != NULL) {
					for(j=0; j<num_members; j++) {
						send(clisock_list[j], myClient[j].client_ftime, sizeof(myClient[j].client_ftime), 0);
					}
				}
				for(j=0; j<num_members; j++) {
					send(clisock_list[j], buf, nbyte, 0);
				}
				printf("%s\n", buf);
			}
		}
	}
	return 0;
}

void addClient(int s, struct sockaddr_in *newcliaddr) {
	char buf[50];
	char *client_name;
	char client_name_temp[50];
	char exception[] = " []:";
	char exception2[] = "\n";
	int nbyte = 0;
	
	inet_ntop(AF_INET, &newcliaddr->sin_addr, buf, sizeof(buf));
	strcpy(myClient[num_members].client_ip, buf);
	time_t seconds = time(NULL);

	char *time = strtok(ctime(&seconds), exception2);
	strcpy(myClient[num_members].client_ftime, time);
	
	clisock_list[num_members] = s;

	nbyte = recv(clisock_list[num_members], client_name_temp, 50, 0);
	client_name_temp[nbyte] = 0;

	client_name = strtok(client_name_temp, exception);
	strcpy(myClient[num_members].client_id, client_name);

	num_members++;
}

void removeClient(int s) {
	printf("One of members [%s] is disconnected\n", myClient[s].client_id);
	close(clisock_list[s]);
	int i;

	for(i=s+1; i<num_members; i++) {
		strcpy(myClient[i-1].client_id, myClient[i].client_id);
		strcpy(myClient[i-1].client_ip, myClient[i].client_ip);
		strcpy(myClient[i-1].client_ftime, myClient[i].client_ftime);
	}

	if(s != num_members-1)
		clisock_list[s] = clisock_list[num_members-1];
	num_members--;
	printf("Now the total number of members is %d\n\n", num_members);
}

int getmax() {
	int max = listen_sock;
	int i;
	for(i=0; i<num_members; i++) {
		if(clisock_list[i] > max)
			max = clisock_list[i];
	}
	return max;
}

int tcp_listen(int host, int port, int backlog) {
	int sd;
	struct sockaddr_in servaddr;
	
	sd = socket(AF_INET, SOCK_STREAM, 0);
	if(sd == -1) {
		perror("Socket Failed");
		exit(1);
	}

	bzero((char*)&servaddr, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(host);
	servaddr.sin_port = htons(port);
	if(bind(sd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) {
		perror("Bind Failed");
		exit(1);
	}

	listen(sd, backlog);
	return sd;
}

void display() {
	int i;
	printf("=========================================================\n|\t\t\t\t\t\t\t|\n");
	printf("|\tNow the total number of members is %d\t\t|\n", num_members);
	for(i=0; i<num_members; i++) {
		printf("|\tMember name    : %s\t\t\t\t|\n|\tConnected IP   : %s\t\t|\n|\tConnected time : %s\t|\n|\t\t\t\t\t\t\t|\n", myClient[i].client_id, myClient[i].client_ip, myClient[i].client_ftime);
	}
	printf("=========================================================\n");
}


