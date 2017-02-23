#include <stdio.h>

void swap(int *number, int *number2);

void main(){
	int arr[10] = {9, 4, 3, 10, 5, 8, 7, 6, 2, 1};
	int i, reset, set = 1;
	
	while( set < 10 ){
		reset = 0;

		for( i=set-1; i>=0; i--){
			// 1-1. ������ �Ǵ� ������ ���� �տ� �ִ� ���� �� Ŭ ��� 
			if(arr[i] > arr[set]){
				// 2. �� ���� ��ġ�� �ٲߴϴ�.
				swap(&arr[set], &arr[i]);				
				// 3. ��ġ�� �ٲ����ν�(-1) ���� �ε����� ���󰡱� ���� -������ �����մϴ�.
				set--;
				// 4. reset ������ ���� �ε����� ��ġ�� �����ϱ� ���� ������ ���� �ε����� -���� ��ŭ +�մϴ�.
				reset++;
			}
			// 1-2. ������ �Ǵ� ������ ���� �տ� �ִ� ���� ���� ��� �ݺ����� �����մϴ�.
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