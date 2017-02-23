#include <stdio.h>

void swap(int *number, int *number2);

void main(){
	int arr[10] = {9, 4, 3, 10, 5, 8, 7, 6, 2, 1};
	int i, reset, set = 1;
	
	while( set < 10 ){
		reset = 0;

		for( i=set-1; i>=0; i--){
			// 1-1. 기준이 되는 수보다 기준 앞에 있는 수가 더 클 경우 
			if(arr[i] > arr[set]){
				// 2. 두 수의 위치를 바꿉니다.
				swap(&arr[set], &arr[i]);				
				// 3. 위치를 바꿈으로써(-1) 기준 인덱스도 따라가기 위해 -연산을 수행합니다.
				set--;
				// 4. reset 변수는 기준 인덱스의 위치를 보존하기 위한 변수로 기준 인덱스를 -해준 만큼 +합니다.
				reset++;
			}
			// 1-2. 기준이 되는 수보다 기준 앞에 있는 수가 작을 경우 반복문을 종료합니다.
			else break;
		}

		set = set + reset + 1;
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