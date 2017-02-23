#include <stdio.h>

void main(){
	
	int i,j;

	// 다이아몬드의 왼쪽 반쪽
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

	// 다이아몬드의 오른쪽 반쪽
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

	// 완성된 다이아몬드
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