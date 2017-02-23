#include <stdio.h>

void main(){
	int data[5] = {37, 14, 17, 40, 35};
	int minValueIndex = 0;
	int i, j, temp = 0;

	for(i=0;i<5;i++){
		for(j=i+1;j<5;j++){
			if(data[j] < data[minValueIndex]){
				minValueIndex = j;
			}
		}
		temp = data[i];
		data[i] = data[minValueIndex];
		data[minValueIndex] = temp;
	}

	for(i=0;i<5;i++){
		printf("%d ", data[i]);
	}
}
