#include "KICBtree.h"

void printMenu() {
	printf("---메뉴---\n");
	printf(" 0. 종료\n");
	printf(" 1. 삽입\n");
	printf(" 2. 삭제\n");
	printf(" 3. 출력\n");
	printf("----------\n");
	printf(" >> ");
}

int main()
{
    int menu;
	int input;
    
	while(1){
		fflush(stdin);					//입력 버퍼 비우기
		printMenu();
        scanf("%d", &menu);

	switch(menu)
        {
        case 0:				
			freeAll(root);				//각 노드에 할당된 모든 메모리 해제
			//printf("%d개의 노드의 메모리를 해제하였습니다.\n",cnt);//메모리 해제 확인을 위한 메모리 해제한 노드의 갯수 출력
			return 1;					//프로그램 종료
        case 1:
		    printf("Insert key >> ");
            scanf("%d", &input);		//키 값 입력
			insertKey(input);			//키 삽입
            break;
        case 2:
            printf("Remove key >> ");	
            scanf("%d",&input);			//키 값 입력
            deleteKey(input);			//키 삭제
            break;
		case 3:
			printf("< n, k1, k2 >\n-------------\n");
			inorderTraversal(root);		//중위순회로 B-tree 출력
			break;
        default:
            printf("잘못된 번호를 입력했습니다.\n");
            break;
        }
    }
    return 0;
}
