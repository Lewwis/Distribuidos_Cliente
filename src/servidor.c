#include <sys/types.h> 
#include <sys/socket.h> 
#include <stdio.h> 
#include <stdlib.h>
#include <netinet/in.h> 
#include <arpa/inet.h> 
#include <unistd.h> 
#include <string.h>
#include <pthread.h>

#define MAX_CLIENTS 100

int totClients=0;
int client_sockfd[MAX_CLIENTS];
pthread_t thread_id[MAX_CLIENTS];

void* connectClient(void *arg){
	int i;
	int *n=(int *) arg;
	int mynum=*n;
	char hello[256] = "-----Un usuario a ingresado en el canal\n";
	char bye[256] = "-----Un usuario ha dejado el canal\n";
	printf("Usuario a ingresado al chat. ID : %d\n",mynum);
	
	for(i=0; i<totClients; i++) {
		write(client_sockfd[i], hello, 256);
	}
	
	while (1){
		char string[256] = "";
		long recibido = read(client_sockfd[mynum], string, 256);
		printf("ID[%d] Mensaje recibido, tamaño de bytes : %ld \n",mynum, recibido);
		
		if(recibido > 0) {
			printf("ID[%d] Mensaje enviado\n",mynum);
			for(i=0; i<totClients; i++) {
				write(client_sockfd[i], string, 256);
			}
		} else {
			printf("ID[%d] Cerrando sockete. Error al recibir el mensaje\n", mynum);
			
			for(i=0; i<totClients; i++) {
				if(i != mynum)
					write(client_sockfd[i], bye, 256);
			}
			
			close(client_sockfd[mynum]);
			pthread_exit(NULL);
		}
	}
}

int main() 
{ 
    int server_sockfd; 
    int server_len, client_len; 
    int pid, param[MAX_CLIENTS];
    struct sockaddr_in server_address; 
    struct sockaddr_in client_address; 

    /* Crear un socket sin nombre para el server */ 
    server_sockfd = socket(AF_INET, SOCK_STREAM, 0); 

    /* Nombrar el socket */ 
    server_address.sin_family = AF_INET; 
    server_address.sin_addr.s_addr = htonl(INADDR_ANY); 
    server_address.sin_port = htons(9734); 
    server_len = sizeof(server_address); 

    bind(server_sockfd, (struct sockaddr *)&server_address, server_len); 
    listen(server_sockfd, 5); 


    while(1) {
        printf("Servidor en espera\n"); 
        client_sockfd[totClients] = accept(server_sockfd,(struct sockaddr *)&client_address, &client_len); 
		param[totClients]=totClients;
		pthread_create(&thread_id[totClients],NULL,connectClient,(void *) &param[totClients]);
		totClients++;
	}
} 


