#include <stdio.h>

void swap(int *number, int *number2);

void main(){

	int i, j, maxIndex;
	int arr[10] = {9, 4, 3, 10, 5, 8, 7, 6, 2, 1};

	for(i=0;i<10;i++){
		
		maxIndex = 0;
		j = 0;

		for(j=0;j<10;j++){
			if(arr[j] < arr[maxIndex]){
				swap(&arr[j], &arr[maxIndex]);
				maxIndex++;
			}
			else maxIndex = j;
		}
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