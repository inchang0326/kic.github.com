#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <arpa/inet.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <string.h> 
#include <netinet/in.h>
#include <fcntl.h>
#include <ctype.h>

void errquit(char *mesg) { perror(mesg); exit(1); }
void display() {
	printf("========================================================\n");
	printf("|				  		       |\n");
        printf("| 1. Select 'ls'  to check the file list on its server |\n| 2. Select 'get' to download a file from its server   |\n| 3. Select 'put' to upload a file on its server       |\n| 4. Select 'del' to remove a file on its server       |\n| 5. Select 'ren' to rename a file on its server       |\n| 6. Select 'bye' to terminate\t\t\t       |\n");
	printf("|						       |\n");
        printf("========================================================\n");
}

int main(int argc, char **argv) {
	struct sockaddr_in serv_addr;
   	int i, cli_sock, fd, buf_len, file_len, sel_len, flag_len, conn = 1;
   	char buf[1024], filename[30], filepath[100], select[3], flag[3];

   	if(argc != 3) {
      		printf("Usage : %s server_ip server_port\n", argv[0]);
      		exit(1);
   	}

	while(1) {
   		// TCP socket open
   		cli_sock = socket(PF_INET, SOCK_STREAM, 0);
   		if(cli_sock == -1) {
      			errquit("Client >> Socket failed ");
   		}

   		memset(&serv_addr, 0, sizeof(serv_addr));

   		serv_addr.sin_family = AF_INET;
   		serv_addr.sin_addr.s_addr = inet_addr(argv[1]);
   		serv_addr.sin_port = htons(atoi(argv[2]));

   		if(connect(cli_sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) == -1) 
			errquit("Client >> Connection failed ");
   		if(conn) printf("\nClient >> Connected to the server\n");  
   		conn = 0;

		display();
   		printf("Client >> Select : ");
   		scanf("%s", select);

   		for(i=0; i<3; i++)
			select[i] = tolower(select[i]);

		if(send(cli_sock, select, strlen(select), 0) < 0)
			errquit("Client >> Write failed ");

		if((strncmp("ls", select, 2) == 0)) {
			char exception[] = ",";
			char *token = NULL;
			buf_len = recv(cli_sock, buf, 1024, 0);
			buf[buf_len] = 0;
			token = strtok(buf, exception);
			while( token != NULL ) {
				printf("%s  ", token);
				token = strtok(NULL, exception);
			}
			printf("\n");
		}
   		else if((strncmp("put", select, 3) == 0)) {
      			printf("Client >> Input file name : ");
      			scanf("%s", filename);
      			printf("Client >> Tying to upload [%s]....\n", filename);
      			file_len = strlen(filename) + 1;
      			strcpy(buf, filename);
      			fd = open(filename, O_RDONLY);
      			if(fd == -1) {
				char *nak = "NAK";
         			printf("Client >> No such file or directory\n");
				if(send(cli_sock, nak, 30, 0) < 0)
					errquit("Client >> Write failed ");
				continue;
     	 		}
      			else {
         			if(send(cli_sock, filename, 30, 0) < 0)
					errquit("Client >> Write failed");
         			while((file_len = read(fd, buf, 1)) == 1)
            				write(cli_sock, buf, 1);
				printf("Client >> File upload completed\n");
         		}
      		}
   		else if((strncmp("get", select, 3) == 0)) {
      			printf("Client >> Input file name : ");
      			scanf("%s", filename);
			printf("Clinet >> Input file path : ");
			scanf("%s", filepath);
			sprintf(filepath, "%s/%s", filepath, filename);
			printf("Client >> Selected filepath : [%s]\n", filepath);
      			file_len = strlen(filename) + 1;
      			strcpy(buf,filename);
      			if(send(cli_sock, filepath, 100, 0) < 0)
				errquit("Client >> Write failed");
     			flag_len = recv(cli_sock, flag, 3, 0);
      			flag[flag_len] = 0; 
     			printf("Client >> Tying to download [%s]....\n", filename);
    			if(strstr(flag, "ACK") != NULL) {
				while(1) {
					fflush(stdin);
					char permit[3];
					printf("Client >> Selct file open permission (r+/w+/rw+)\n:");
         				scanf("%s", permit);
         				if(strcmp("r+", permit) == 0) {
            					fd = open(filename, O_RDONLY | O_CREAT, S_IRWXU);
						break;
					}
         				else if(strcmp("w+", permit) == 0) {
            					fd = open(filename, O_WRONLY | O_CREAT, S_IRWXU);
						break;
					}
         				else if(strcmp("rw+", permit) == 0) {
            					fd = open(filename, O_RDWR | O_CREAT, S_IRWXU);
						break;
					}
					else {
						printf("Client >> No such permission\n");
						continue;
					}
				}
				char *ack = "ACK";
				if(send(cli_sock, ack, 3, 0) < 0)
					errquit("Client >> Write failed");
         			while((file_len = read(cli_sock, buf, 1)) == 1) {
               				write(fd, buf, 1);
				}
				printf("Client >> File download completed\n");
     			}
			if(strstr(flag, "NAK") != NULL) {
				printf("Client >> No such file or directory\n");
				continue;
			}
			if(strstr(flag, "BAN") != NULL) {
				printf("Client >> File access denied\n");
				continue;
			}
   		}
		else if((strncmp("del", select, 3) == 0)) {
			printf("Client >> Input file name : ");
			scanf("%s", filename);
			printf("Client >> Selected file : [%s]\n", filename);
			file_len = strlen(filename) + 1;
			if(send(cli_sock, filename, 30, 0) < 0)
                                errquit("Client >> Write failed");
                        flag_len = recv(cli_sock, flag, 3, 0);
                        flag[flag_len] = 0; 
                        printf("Client >> Tying to remove [%s]....\n", filename);
			if(strstr(flag, "ACK") != NULL) {
                        	printf("Client >> File deletion completed\n");
                        }
                        if(strstr(flag, "NAK") != NULL) {
                                printf("Client >> No such file or directory\n");
                                continue;
                        }
                        if(strstr(flag, "BAN") != NULL) {
                                printf("Client >> File access denied\n");
                                continue;
                        }
		}
		else if((strncmp("ren", select, 3) == 0)) {
			char new_filename[30];
			int new_file_len;
			printf("Client >> Input file name : ");
                        scanf("%s", filename);
			file_len = strlen(filename) + 1;
			if(send(cli_sock, filename, 30, 0) < 0) 
				errquit("Client >> Write failed");
			printf("Clinet >> Input new file name : ");
			scanf("%s", new_filename);
			new_file_len = strlen(new_filename) + 1;
			if(send(cli_sock, new_filename, 30, 0) < 0)
				errquit("Client >> Wrtie failed");
                        printf("Client >> Tying to change [%s] to [%s]....\n", filename, new_filename);
			flag_len = recv(cli_sock, flag, 3, 0);
			flag[flag_len] = 0;
			if(strstr(flag, "ACK") != NULL) {
                                printf("Client >> File name changed successfully\n");
                        }
                        if(strstr(flag, "NAK") != NULL) {
                                printf("Client >> No such file or directory\n");
                                continue;
                        }
		}
   		else if((strncmp("bye", select, 3) == 0)) {
			printf("Client >> Good bye..\n");
			close(fd);
			close(cli_sock);
      			exit(0);
   		}
   		else {
      			printf("Client >> Wrong instruction\n");
			continue;
   		}
   		if(shutdown(cli_sock, SHUT_WR) == -1) {
      			errquit("Client >> Shutdown failed ");
   		}
	}
   	close(fd);
   	close(cli_sock);   
	return 0;
}
