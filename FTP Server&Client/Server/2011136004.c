#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <arpa/inet.h>
#include <sys/stat.h> 
#include <sys/types.h>
#include <sys/socket.h>  
#include <string.h> 
#include <netinet/in.h>  
#include <fcntl.h>
#include <pwd.h>
#include <grp.h>
#include <dirent.h>

void errquit(char *mesg) { perror(mesg); exit(1); }

int main(int argc, char **argv) {
	int serv_sock, cli_sock, cli_addr_len, fd, file_len, file_path_len, sel_len, conn = 1;
   	struct sockaddr_in serv_addr, cli_addr;
	char buf[1024], filename[30], filepath[100], select[3];
	struct stat file_stat;

   	if(argc != 2) {
      		printf("Usage : %s port\n", argv[0]);
      		exit(1);
   	}
   
   	// TCP socket open
   	serv_sock = socket(PF_INET, SOCK_STREAM, 0);  
   	if(serv_sock == -1) {
      		errquit("Server >> Socket failed ");
   	}

   	memset(&serv_addr, 0, sizeof(serv_addr));

   	serv_addr.sin_family = AF_INET;
   	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
   	serv_addr.sin_port = htons(atoi(argv[1]));

   	// binding
   	if(bind(serv_sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) == -1)
      		errquit("Server >> Bind failed ");

   	while(1) {
		// wating
      		if(listen(serv_sock, 5) == -1) errquit("Server >> Listen failed ");
      		printf("Server >> Waiting for a request\n\n");
      		cli_addr_len = sizeof(cli_addr);
		
		// accepting
      		cli_sock = accept(serv_sock, (struct sockaddr *)&cli_addr, &cli_addr_len);
      		if(cli_sock == -1) errquit("Server >> Accept failed ");
		if(conn) printf("Server >> A user connected\n");
         	conn = 0;
      
      		sel_len = recv(cli_sock, select, 3, 0);
      		select[sel_len] = 0;

		if(strstr(select, "ls") != NULL) {
			DIR *dir;
			struct dirent *ent;
			char buffer[1024] = "";
			if((dir = opendir ("/home/nstl/Server")) != NULL) {
				while((ent = readdir (dir)) != NULL) {
    					strcat(buffer, ent->d_name);
					strcat(buffer, ",");
				}
  				closedir(dir);
				if(send(cli_sock, buffer, 1024, 0) < 0)
					errquit("Server >> Write failed ");
				printf("Server >> Listing file completed\n");
			} 
			else {
				printf("Server >> Listing files failed\n");
				continue;
			}
		}
      		else if(strstr(select, "get") != NULL) {
			char flag[3];
			int flag_len, i;
			char permission[10];
         		printf("Server >> GET : Ready to download\n");
         		file_path_len = recv(cli_sock, filepath, 100, 0);
         		filepath[file_path_len] = 0;
                        if(stat(filepath, &file_stat) < 0) {
				char *nak = "NAK";
				if(send(cli_sock, nak, 3, 0) < 0)
					errquit("Server >> Write failed");
                     		printf("Server >> No such file or directory\n");
				continue;
			}
			struct passwd *pw = getpwuid(file_stat.st_uid);
    			struct group  *gr = getgrgid(file_stat.st_gid);
                        if(S_ISDIR(file_stat.st_mode)) permission[0] = 'd';
                        else permission[0] = '-';
                        if(file_stat.st_mode & S_IRUSR) permission[1] = 'r';
                        else permission[1] = '-';
                        if(file_stat.st_mode & S_IWUSR) permission[2] = 'w';
                        else permission[2] = '-';
                        if(file_stat.st_mode & S_IXUSR) permission[3] = 'x';
                        else permission[3] = '-';
                        if(file_stat.st_mode & S_IRGRP) permission[4] = 'r';
                        else permission[4] = '-';
                        if(file_stat.st_mode & S_IWGRP) permission[5] = 'w';
                        else permission[5] = '-';
                        if(file_stat.st_mode & S_IXGRP) permission[6] = 'x';
                        else permission[6] = '-';
                        if(file_stat.st_mode & S_IROTH) permission[7] = 'r';
                        else permission[7] = '-';
                        if(file_stat.st_mode & S_IWOTH) permission[8] = 'w';
                        else permission[8] = '-';
                        if(file_stat.st_mode & S_IXOTH) permission[9] = 'x';
                        else permission[9] = '-';
                        if( ((strstr(pw->pw_name, "root") != NULL) && (permission[7] == '-')) || ((strstr(pw->pw_name, "nstl") != NULL) && (permission[1] == '-')) ) {
				char *ban = "BAN";
				if(send(cli_sock, ban, 3, 0) < 0)
					errquit("Server >> Write failed");
	                      	printf("Server >> File download denided\n");
				printf("Server >> Authority : %s\n", pw->pw_name);
				printf("Server >> File permission : ");
				for(i=0;i<10;i++) {
					printf("%c", permission[i]);
					if(i==9) printf("\n");
				}
				continue;
                      	}
         		strcpy(buf, filepath); 
         		fd = open(filepath, O_RDONLY);
         		if(fd == -1) {
          			char *nak = "NAK";
         			if(send(cli_sock, nak, 3, 0) < 0)
					errquit("Server >> Write failed");
            			printf("Server >> No such file or directory\n");
				continue;
         		}
         		else {
         			char *ack = "ACK";
         			if(send(cli_sock, ack, 3, 0) < 0)
					errquit("Server >> Write failed");
            			printf("Server >> Trying to download....\n");
				flag_len = recv(cli_sock, flag, 3, 0);
				flag[flag_len] = 0;
				if(strstr(flag, "ACK") != NULL) {
            				while((file_len = read(fd, buf, 1)) == 1) {
               					write(cli_sock, buf, 1);
            				}
            				printf("Server >> File download completed\n");
				}
         		}
      		}
		else if(strstr(select, "put") != NULL) {
        		printf("Server >> Ready to upload\n");
        		file_len = recv(cli_sock, filename, 30, 0);
        		filename[file_len] = 0;
			if(strstr(filename, "NAK") != NULL) {
				printf("Server >> No such file or directory\n");
				continue;
			}

        		fd = open(filename, O_WRONLY|O_CREAT, S_IRUSR|S_IWUSR);
			if(fd == -1) {
				errquit("Server >> File open failed ");
				continue;
			}
			else {
            			printf("Server >> Trying to upload....\n");
            			while((file_len = read(cli_sock, buf, 1)) == 1) {
               				write(fd, buf, 1);
            			}
            			printf("Server >> File upload completed\n");
			}
		}
		else if(strstr(select, "del") != NULL) {
			char permission[10];
			int i;
			printf("Server >> DEL : Ready to remove\n");
                        file_len = recv(cli_sock, filename, 30, 0);
                        filename[file_len] = 0;
                        if(stat(filename, &file_stat) < 0) {
                                char *nak = "NAK";
                                if(send(cli_sock, nak, 3, 0) < 0)
                                        errquit("Server >> Write failed");
                                printf("Server >> No such file or directory\n");
                                continue;
                        }
                        struct passwd *pw = getpwuid(file_stat.st_uid);
                        struct group  *gr = getgrgid(file_stat.st_gid);
                        if(S_ISDIR(file_stat.st_mode)) permission[0] = 'd';
                        else permission[0] = '-';
                        if(file_stat.st_mode & S_IRUSR) permission[1] = 'r';
                        else permission[1] = '-';
                        if(file_stat.st_mode & S_IWUSR) permission[2] = 'w';
                        else permission[2] = '-';
                        if(file_stat.st_mode & S_IXUSR) permission[3] = 'x';
                        else permission[3] = '-';
                        if(file_stat.st_mode & S_IRGRP) permission[4] = 'r';
                        else permission[4] = '-';
                        if(file_stat.st_mode & S_IWGRP) permission[5] = 'w';
                        else permission[5] = '-';
                        if(file_stat.st_mode & S_IXGRP) permission[6] = 'x';
                        else permission[6] = '-';
                        if(file_stat.st_mode & S_IROTH) permission[7] = 'r';
                        else permission[7] = '-';
                        if(file_stat.st_mode & S_IWOTH) permission[8] = 'w';
                        else permission[8] = '-';
                        if(file_stat.st_mode & S_IXOTH) permission[9] = 'x';
                        else permission[9] = '-';
                        if( ((strstr(pw->pw_name, "root") != NULL) && (permission[8] == '-')) || ((strstr(pw->pw_name, "nstl") != NULL) && (permission[2] == '-')) ) {
                                char *ban = "BAN";
                                if(send(cli_sock, ban, 3, 0) < 0)
                                        errquit("Server >> Write failed");
                                printf("Server >> Selected file is protected from being removed\n");
                                printf("Server >> Authority : %s\n", pw->pw_name);
                                printf("Server >> FIle permission : ");
				for(i=0;i<10;i++) {
					printf("%c", permission[i]);
					if(i==9) printf("\n");
				}
                                continue;
                        }
                        fd = open(filename, O_RDONLY);
                        if(fd == -1) {
                                char *nak = "NAK";
                                if(send(cli_sock, nak, 3, 0) < 0)
                                        errquit("Server >> Write failed");
                                printf("Server >> No such file or directory\n");
                                continue;
                        }
                        else {
				int result;
                                char *ack = "ACK";
                                if(send(cli_sock, ack, 3, 0) < 0)
                                        errquit("Server >> Write failed");
                                printf("Server >> Trying to remove....\n");
                                result = remove(filename);
				if(result == 0) printf("Server >> file deleted successfully\n");
				else errquit("Server >> file deletion failed ");
                        }
		}
		else if(strstr(select, "ren") != NULL) {
			char new_filename[30];
			int new_file_len;
			printf("Server >> REN : Ready to rename\n");
                        file_len = recv(cli_sock, filename, 30, 0);
                        filename[file_len] = 0;
			new_file_len = recv(cli_sock, new_filename, 30, 0);
			new_filename[new_file_len] = 0;
			fd = open(filename, O_RDONLY);
			if(fd == -1) {
                                char *nak = "NAK";
                                if(send(cli_sock, nak, 3, 0) < 0)
                                        errquit("Server >> Write failed");
                                printf("Server >> No such file or directory\n");
                                continue;
                        }
                        else {
                                int result;
                                char *ack = "ACK";
                                if(send(cli_sock, ack, 3, 0) < 0)
                                        errquit("Server >> Write failed");
                                printf("Server >> Trying to rename....\n");
                                result = rename(filename, new_filename);
                                if(result == 0) printf("Server >> file name changed successfully\n");
                                else errquit("Server >> file rename failed ");
                        }
		}
		else if(strstr(select, "bye") != NULL) {
			printf("Server >> A user disconnected\n");
			conn = 1;
		}
      		else
         		printf("Server >> Wrong instruction\n");
		if(shutdown(cli_sock, SHUT_WR) == -1) { 
			printf("Server >> Shutdown failed\n");
		}
		fflush(stdin);
   	}
   	close(fd);
   	close(serv_sock);
   	close(cli_sock);   
	return 0;
}
