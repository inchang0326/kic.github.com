#include <stdio.h>

void swap(int *number, int *number2);

void main(){

	int i, j, cnt = 0;
	int arr[10] = {9, 4, 3, 10, 5, 8, 7, 6, 2, 1};

	for(i=0;i<9;i++){
		cnt = 0;
		for(j=0;j<9-i;j++){
			if(arr[j] > arr[j+1]){
				swap(&arr[j], &arr[j+1]);
				cnt++;
			}
		}
		if(cnt == 0) break;
	}

	for(i=0;i<10;i++)
		printf("%d ", arr[i]);
}

void swap(int *x, int *y){
	int temp;
	temp = *x;
	*x = *y;
	*y = temp;
}
