#include <stdio.h>

void main(){
	
	int i,j;

	// ���̾Ƹ���� ���� ����
	/*
	for(i=0;i<10;i++){
		for(j=0;j<=i;j++){
			printf("*");
		}
		printf("\n");
	}

	for(i=10;i>0;i--){
		for(j=i;j>0;j--){
			printf("*");
		}
		printf("\n");
	}
	*/

	// ���̾Ƹ���� ������ ����
	/*
	for(i=0;i<10;i++){
		for(j=i;j<10;j++){
			printf(" ");
		}
		for(j=0;j<=i;j++){
			printf("*");
		}
		printf("\n");
	}

	for(i=0;i<10;i++){
		for(j=0;j<=i;j++){
			printf(" ");
		}
		for(j=i;j<10;j++){
			printf("*");
		}
		printf("\n");
	}
	*/

	// �ϼ��� ���̾Ƹ��
	for(i=0;i<10;i++){
		for(j=i;j<10;j++){
			printf(" ");
		}
		for(j=0;j<=i;j++){
			printf("*");
		}
		for(j=0;j<=i;j++){
			printf("*");
		}

		printf("\n");
	}

	for(i=0;i<10;i++){
		for(j=0;j<=i;j++){
			printf(" ");
		}
		for(j=i;j<10;j++){
			printf("*");
		}
		for(j=i;j<10;j++){
			printf("*");
		}
		printf("\n");
	}
}