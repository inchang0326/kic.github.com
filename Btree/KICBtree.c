#include "KICBtree.h"

void printMenu() {
	printf("---�޴�---\n");
	printf(" 0. ����\n");
	printf(" 1. ����\n");
	printf(" 2. ����\n");
	printf(" 3. ���\n");
	printf("----------\n");
	printf(" >> ");
}

int main()
{
    int menu;
	int input;
    
	while(1){
		fflush(stdin);					//�Է� ���� ����
		printMenu();
        scanf("%d", &menu);

	switch(menu)
        {
        case 0:				
			freeAll(root);				//�� ��忡 �Ҵ�� ��� �޸� ����
			//printf("%d���� ����� �޸𸮸� �����Ͽ����ϴ�.\n",cnt);//�޸� ���� Ȯ���� ���� �޸� ������ ����� ���� ���
			return 1;					//���α׷� ����
        case 1:
		    printf("Insert key >> ");
            scanf("%d", &input);		//Ű �� �Է�
			insertKey(input);			//Ű ����
            break;
        case 2:
            printf("Remove key >> ");	
            scanf("%d",&input);			//Ű �� �Է�
            deleteKey(input);			//Ű ����
            break;
		case 3:
			printf("< n, k1, k2 >\n-------------\n");
			inorderTraversal(root);		//������ȸ�� B-tree ���
			break;
        default:
            printf("�߸��� ��ȣ�� �Է��߽��ϴ�.\n");
            break;
        }
    }
    return 0;
}
