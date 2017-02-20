#include <stdio.h>
#include <stdlib.h>

static int cnt = 0;		//�޸� ���� Ȯ�� ����

/*	��� ����ü ����
*/
struct node{								
	int n;				//����� Ű�� ����
    int keys[2];		//����� Ű ���� �����ϴ� �迭
    struct node *p[3];	//��� �����͸� �����ϴ� �ڱ� ���� ����ü �迭
};
struct node *root;		//��Ʈ ��� ����

/*	��ȯ���� ���� �� 
	1. duplicateKey - ������ Ű �� �ߺ�
	2. insertOkey   - ���� ����
	3. searchFailed - ������ ���� Ʈ�� �� �������� ����
	4. uunderflow   - Ű ���� �� ����� ����÷ο� �߻�
	5. success	    - ���� �Ǵ� ���� ����
*/
enum keyStatus{ duplicateKey, insertOkey, searchFailed, underflow, success};

/*	�޸� ����
*/
void freeAll(struct node *ptr){	//�� ��忡 �Ҵ�� �޸𸮸� �����Ѵ�.
	if(ptr != NULL){			//���� ��尡 NULL�� �ƴ� ���
		int i;
		for(i=0;i<=ptr->n;i++){
			freeAll(ptr->p[i]);	//�ڽ� ������ ��ȸ�Ѵ�.
		}
		free(ptr);				//���� ����� �޸𸮸� �����Ѵ�.
		cnt++;					//�޸� ���� ���� üŬ�Ѵ�.				
	}
}
/*	���� ��ȸ
*/
void inorderTraversal(struct node *ptr){	
	int cnt = 0;

	if(ptr != NULL){						//���� ��尡 NULL�� �ƴ� ���
		int i, j;
		for(i=0;i<ptr->n+1;i++){				//���� ����� Ű�� ���� + 1 �� ����
			if(ptr->p[i] != NULL){			//�ڽ� ��尡 NULL�� �ƴ� ���
				inorderTraversal(ptr->p[i]);//�ڽ� ��带 ��ȸ�Ѵ�.
			}
			else		//�ܸ�������� �ƴ����� �˻��Ѵ�.
				cnt++;	//cnt�� 2�� ��� ���� �ڽİ� �߾� �ڽ� �����Ͱ� ��� NULL�̹Ƿ� �ܸ� ��� ����Ѵ�.
			if(i < ptr->n && cnt != 2){					
				if(ptr->n == 2){				//����� Ű�� ������ �ΰ��� �� �� ����Ѵ�.
					printf("< %d,", ptr->n);
					for(j=0;j<ptr->n;j++){
						if(j == ptr->n-1)
							printf(" %d", ptr->keys[j]);
						else
							printf("  %d, ", ptr->keys[j]);
					}
					printf(" >\n");
				}
				else						//����� Ű�� ������ �Ѱ��� �ϳ��� ����Ѵ�.
					printf("< %d,  %d >\n", ptr->n, ptr->keys[i]);
			}
		}
		cnt = 0;
	}
}

/*	Ű �� ����
*/
void insertKey(int k){
	struct node *newNode = NULL;//��Ʈ���� ���� �÷ο� �߻��� ���ο� ��Ʈ�� �߾� ���
    int key = 0;				//��Ʈ���� ���� �÷ο� �߻��� ���ο� ��Ʈ�� Ű ��				
    
	enum keyStatus value;
	value = enuminsertKey(root, k, &key, &newNode);

	if(value == duplicateKey){
        printf("�̹� Ű�� �ֽ��ϴ�.\n");
	}
	
	//ù ������� ��Ʈ ��带 ����, ���� ������� ��Ʈ���� ���� �÷ο찡 �߻����� ��� ��带 �ɱ��� �������ش�.
    else if(value == insertOkey){							 
		struct node *newNode2 = root;						//���ο� ��Ʈ�� ���� ��� ���� �� ���� ��Ʈ ��� ����
        root = (struct node*)malloc(sizeof(struct node));	//���ο� ��Ʈ ��带 ����
        root->n = 1;											//���ο� ��Ʈ ����� Ű�� ������ 1�� �ʱ�ȭ
        root->keys[0] = key;									//���ο� ��Ʈ ��忡 ù��° Ű�� ���� key ���� 
        root->p[0] = newNode2;								//���ο� ��Ʈ ����� ���� �ڽ� ��忡 newNode2 ���� 
        root->p[1] = newNode;								//���ο� ��Ʈ ����� ������ �ڽ� ��带 newNode ����
    }
}

/*	Ű�� ���� ��ġ
*/
int searchPos(int k, int *key_arr, int n){	//Ű�� ���� ��ġ ����� ��ȣ(0 - ����, 1 - �߾�, 2 - ������)�� ��ȯ�Ѵ�.
											//�Ǵ� Ű�� ��ġ�� ��ġ(0 - ����, 1 - ������)�� ��ȯ�Ѵ�.
    int pos = 0;							
    while(pos<n && k>key_arr[pos]){		
		pos++;								//�Է¹��� Ű ���� ���� ��忡 �ִ� Ű ���� ���� �Է¹��� Ű ���� ũ�ٸ� pos�� ++�Ѵ�.
	}										
    return pos;								//pos�� ��ȯ�Ѵ�.
}

/*	Ű �� ����
*/
enum keyStatus enuminsertKey(struct node *ptr, int k, int *key, struct node **newNode){
    int newKey;					//���Ե� Ű ��
								//���Ե� Ű ������ ���� ���� ������ ����Ǿ� �ִ� ����.
	struct node *newPtr = NULL;	//���Ե� Ű ���� ������ ��带 ����Ű�� ������
	int lastKey;				//�����÷ο� �߻� ��忡�� ���� ū Ű�� ��
	struct node *lastPtr = NULL;//�����÷ο� �߻��� �ɰ��� ���� ������ ����� ������ �ڽĳ��	
	int i = 0, pos = 0, n = 0;
	enum keyStatus value;

    //�ڽ��� ��尡 NULL���� �ƴ����� �Ǵ��Ѵ�. 
	//NULL�� ��� ���� ��忡 Ű�� �����ص� �ȴٴ� insertOkey ��ȯ
	if(ptr == NULL){		
        *newNode = NULL;
        *key = k;
        return insertOkey;
    }
    //���� ����� Ű�� ������ �����Ѵ�.
	n = ptr->n;							
	pos = searchPos(k, ptr->keys, n);	
	//���� ��忡�� Ű ���� �ߺ��� �˻��Ѵ�.
	if(pos<n && k==ptr->keys[pos]){		
        return duplicateKey;
	}
    //�ڽ� ��带 ��ȸ�ϸ鼭 Ű ���� �� ��带 ã�´�.
	value = enuminsertKey(ptr->p[pos], k, &newKey, &newPtr);

	if(value != insertOkey){//�ߺ� �˻�	 
        return value;
	}
	if(n < 2){									//���� ����� Ű�� ������ 2���� �Ѿ�� ���� ���
        pos = searchPos(newKey, ptr->keys, n);	//Ű�� ���Ե� ��ġ�� ã�´�.
		//���ο� Ű ���� ���� ��� ���� Ű ���� ���������� �Ű��ش�.
		for(i=n;i>pos;i--){						
            ptr->keys[i] = ptr->keys[i-1];		//�����ʿ� �ִ� Ű ���� ���ʿ� �����Ѵ�.
            ptr->p[i+1] = ptr->p[i];				//�����ʿ� ��ġ�� �ڽ� �����͸� ���� �����ͷ� �����Ѵ�.
        }
		//���ο� Ű ���� ���� Ű ������ Ŭ ��� ���� ���ο� Ű ���� �����ʿ� �����Ѵ�.
		ptr->keys[pos] = newKey;					//���ο� Ű ���� �����Ѵ�.
        ptr->p[pos+1] = newPtr;					//���ο� Ű�� �������� ������ �ڽ� ��忡 newPtr�� �����Ѵ�.
		++ptr->n;								//���� ����� Ű�� ������ ������Ų��.

		return success;
    }
	if(pos == 2){		 //�����÷ο� �߻�, newKey�� �� ���� ū ���	 
        lastKey = newKey;//newKey���� lastKey�� �����Ѵ�.
        lastPtr = newPtr;//newPtr �����͸� newPtr �����Ϳ� �����Ѵ�.  
    }
	else{					   //�����÷ο� �߻�, ���ο� Ű ���� ���� ũ�� ���� ��� 
        lastKey = ptr->keys[1];//���� ����� ���� ū Ű ���� lastKey�� �����Ѵ�.
        lastPtr = ptr->p[2];   //���� ū Ű ���� ������ �����͸� lastPtr�� �����Ѵ�.
        if(pos == 0){					//pos�� 0�� �� 
			ptr->keys[1] = ptr->keys[0];//Ű�� ù��° ���� �ι�°�� �̵���Ų��.
            ptr->p[2] = ptr->p[1];		//����� �߾� ��带 ������ ���� �̵���Ų��.
		}
        ptr->keys[pos] = newKey;//ù��° Ű �ڸ��� ���ο� Ű ���� �����Ѵ�.
        ptr->p[pos+1] = newPtr;	//newPtr�� ���ο� Ű�� ������ �ڽ� ��带 �ǹ��ϸ� newNode.
    }
	(*key) = ptr->keys[1];						   //�����÷ο� �߻� ����� ������ Ű ���� �ɰ��� ���� �θ����� Ű ������ �����Ѵ�. 
    (*newNode) = (struct node*)malloc(sizeof(struct node));//�����÷ο� �߻�, �ɰ��� ���� �θ����� ������ ���
    ptr->n = 1;									   //���� ����� Ű ������ �����Ѵ�. 
	(*newNode)->n = 1;							   //�����÷ο� �߻�, �ɰ��� ���� ������ ��� Ű�� ����
    (*newNode)->p[0] = ptr->p[2];				   //������ ����� ���� �ڽĳ��	
    (*newNode)->keys[0] = lastKey;				   //������ ����� Ű ��
    (*newNode)->p[(*newNode)->n] = lastPtr;        //������ ����� �߾� �ڽ� ���

	return insertOkey;
}

/*	Ű �� ����
*/
void deleteKey(int key){
    struct node *newNode;
    enum keyStatus value;

    value = enumdeleteKey(root,key);
    if(value){
		//Ʈ�� �� ������ ���� �������� �ʴ� �ٸ�
		if(value == searchFailed)
			printf("�Է��� Ű ���� �������� �ʽ��ϴ�.\n");
		//��Ʈ ��忡�� ����÷ο찡 �߻��ߴٸ� ��Ʈ��带 �����Ѵ�.
		else if(value == underflow){
			newNode = root;
			root = root->p[0];
			free(newNode);
		}
	}
}

/*	Ű ����
*/
enum keyStatus enumdeleteKey(struct node *ptr, int key){
    struct node **p;	//�θ� ����� �ڽ� ������ �������� ����Ų��.
	int pivot;			//�պ� �Ǵ� ��й� �� ���ʳ��� ������ ��带 ������ �б���
	int min = 1;		//3�� ��Ʈ���� �ּ� Ű�� ����
    int *key_arr;		//�θ� ��� Ű ������ �迭�� �����Ѵ�.
	struct node *lptr;	
	struct node *rptr;
	int pos = 0, i = 0, n = 0;
    enum keyStatus value;
    
	//��ġ ���п� ���� �극��ũ �����ͷ� ��������� �ܸ������� �������� �� 
	//��尡 �������� ���� ��� ���� ��ã�Ҵٴ� ���̹Ƿ� searchFailed�� ��ȯ�Ѵ�.
	if(ptr == NULL)
		return searchFailed;

    n = ptr->n;			//���� ����� Ű�� ����
    key_arr = ptr->keys;//���� ��尡 ���� Ű�� �迭�� �����Ѵ�.
    p = ptr->p;			//���� ����� �ڽ� ��� �������� �������� �����Ѵ�.
    pos = searchPos(key, key_arr, n);
   
	//������ ���� �ܸ� ��忡 �ִ� ���
	if(p[0] == NULL){
		if(pos == n || key < key_arr[pos])
        	return searchFailed;
		for(i=pos+1;i<n;i++){			//pos�� 0�̶�� ��, ù��° Ű�� �����Ѵٸ�, 
            key_arr[i-1] = key_arr[i];	//�ι�° Ű ���� ù��° Ű ������ �ű��.
            p[i] = p[i+1];				//����° �����͸� �ι�° �����ͷ� �ű��.
        }
		//Ű �� ������ ���� ��尡 �ּ� Ű�� ������ �����Ѵٸ�, success�� ��ȯ�ϰ� �ƴ϶�� underflow�� ��ȯ�Ѵ�.
        return ( (--ptr->n >= min)? success : underflow);
    }

	//������ ���� �ܸ���忡 ���� ���� ���
    if(pos < n && key == key_arr[pos]){
        struct node *qp = p[pos];		//������ ���� ������ �ִ� ����� ���� �ڽĳ��
		struct node *qpRightChild;		//qp�� ������ �ڽĳ��		 
        int nkey;						//������ ���� ������ �ִ� ����� ���� �ڽĳ���� Ű�� ����
        while(1){
            nkey = qp->n;				 
            qpRightChild = qp->p[nkey]; //qoRightChild�� qp�� ���� ������ �ڽ� ��尡 �ȴ�.
            if(qpRightChild == NULL)	//qp�� �ܸ������ ���
                break;
            qp = qpRightChild;			//qp�� �ܸ���尡 �ƴ� ��� �ٽ� qp�� = qpRightChild�� �ʱ�ȭ �Ͽ� while�� �����
        }
		//������ Ű ���� ���� �ڽĳ��� ������ ���� ū Ű ���� ã�Ҵٸ�(while��), ������ Ű���� qp�� ������ Ű ���� ��ȯ�Ѵ�.
        key_arr[pos] = qp->keys[nkey-1];
        qp->keys[nkey - 1] = key;		
    }

	value = enumdeleteKey(p[pos], key);//1. �ڽ� ��带 ��ȸ�Ͽ� �ּ�Ű�� ������ �����ϴ��� �ƴ����� Ȯ���Ѵ�.
									   //2. �ڽ� ��带 ��ȸ�ϸ鼭 ������ Ű���� ��ü�� Ű ���� ã�´�.
	if(value != underflow)
        return value;

	//underflow �߻� �� ���� ���� ��尡 �ּ� Ű�� ������ �����Ѵٸ�, ������ ���� ��忡�� ����÷ο찡 �߻��Ѵٸ�
    if(pos > 0 && p[pos-1]->n > min){ 
		
		pivot = pos - 1;//���� ���� ������ ����� �б���
        lptr = p[pivot];//����÷ο� �߻� �� ���� �������
        rptr = p[pos];	//����÷ο찡 �߻��� ������ ���

        rptr->p[rptr->n + 1] = rptr->p[rptr->n];//����÷ο찡 �߻��� ������ ����� ���� �����͸� �߾������Ϳ� �����Ѵ�.
        rptr->n++;								//����÷ο찡 �߻��� ������ ����� Ű�� ������ ������Ų��.
        rptr->keys[0] = key_arr[pivot];			//����÷ο찡 �߻��� ������ ��忡 �θ����� Ű ���� �����Ѵ�.
        rptr->p[0] = lptr->p[lptr->n];			//����÷ο찡 �߻��� ������ ����� ���� �����Ϳ� ���� ��������� ������ �����͸� �����Ѵ�.
        key_arr[pivot] = lptr->keys[--lptr->n];	//�θ����� Ű�� ���� ��������� �ι�° Ű ���� �����Ѵ�.
        return success;
    }
	
	//underflow �߻� �� ������ ���� ��尡 �ּ� Ű�� ������ �����Ѵٸ�, ���� ���� ��忡�� ����÷ο찡 �߻��Ѵٸ�
    if(pos<n && p[pos+1]->n > min){

		pivot = pos;		//���� ���� ������ ����� �б���
		lptr = p[pivot];	//����÷ο찡 �߻��� ���� ���
		rptr = p[pivot+1];	//����÷ο� �߻� �� ������ ���
		
		lptr->keys[lptr->n] = key_arr[pivot];//����÷ο찡 �߻��� ���� ����� Ű ���� �θ����� Ű ���� �����Ѵ�.
		lptr->p[lptr->n + 1] = rptr->p[0];	 //����÷ο찡 �߻��� ���� ����� ������ �����Ϳ� ������ ���� ����� ���������ͷ� �����Ѵ�.
		key_arr[pivot] = rptr->keys[0];		 //�θ��忡 ������ ���� ����� ù��° Ű ���� �����Ѵ�.
		lptr->n++;							 //����÷ο찡 �߻��� ���� Ű �ϳ��� �޾������� Ű�� ������ ������Ų��.
		rptr->n--;							 //������ ���� ���� ù��°Ű ���� �θ��忡 �־������� Ű�� ������ ���ҽ�Ų��.
		rptr->keys[0] = rptr->keys[1];		 //������ ���� ����� ������ Ű ���� ���� Ű ������ �Ű��ش�.
		rptr->p[0] = rptr->p[1];				 //������ ���� ����� ���� �ڽ� �����Ϳ� �߾� �ڽ������͸� �����Ѵ�. 
		rptr->p[rptr->n] = rptr->p[rptr->n + 1];//������ ��������� �߾� �ڽ� �����Ϳ� ������ �ڽ������͸� �����Ѵ�.
		return success;
	}

	//��� �÷ο찡 �߻��Ͽ��µ�, ���ʰ� ������ ���� ��� ��� �ּ� Ű�� ������ �������� ���Ѵٸ�
    if(pos == n)		//���� ������ �����Ϳ��� �����÷ο� �߻�
        pivot = pos-1;  //�б����� pos-1�� �ʱ�ȭ�Ѵ�.
    else				//�� �� �����Ϳ��� �����÷ο� �߻�
        pivot = pos;	//�б����� pos�� �ʱ�ȭ�Ѵ�.
	rptr = p[pivot+1];
	lptr = p[pivot];	
	//
	//          ex) 55����				ex) 65 ����				ex) 45 ����				ex) 45 ����			ex) 55 ����
	//				pivot = 1				pivot = 1			    pivt = 0			    pivt = 0		    pivt = 1
	//
	//				  40			  		  40		   			  40					  40				  40
	//				��  ��					��  ��					��  ��			  		��  ��		  	    ��  ��
	//			  10     50 60			  10     50 60			  10     50 60			  10     50			  10     50
	//           �ע�   �� �� ��		 �ע�   �� �� ��		 �ע�   �� �� ��		 �ע�   �ע�		 �ע�   �ע�
	//		    5   15 45  55  65		5   15 45  55  65		5   15 45  55  65		5   15 45  55 		5   15 45  55 
	//
	//          | lptr = 55			  |	* lptr = 55         |	* lptr = 45 		|	* lptr = 45		|	* lptr = 45 
	//			| rptr = 65			  |	* rptr = 65			|	* rptr = 55			|	* rptr = 55		|	* rptr = 55
	//
	lptr->keys[lptr->n] = key_arr[pivot];			//������忡�� ����÷ο� �߻� �� �б����� �ִ� �θ����� Ű ���� �����´�.
    lptr->p[lptr->n + 1] = rptr->p[0];				//����÷ο� ���� ������ �θ����� Ű ���� ������ �����͸� ������忡 �ִ� ù��° �����Ϳ��� �����Ѵ�.
	for(i=0;i<rptr->n;i++){							//rptr�� Ű ���� �����ϴٸ�
        lptr->keys[lptr->n + 1 + i] = rptr->keys[i];//rptr�� �ִ� ������ lptr�� �ű��.
        lptr->p[lptr->n + 2 + i] = rptr->p[i+1];		//rptr �� �ִ� �����͵� lptr�� �ű��.
    }
    lptr->n = lptr->n + rptr->n+1;					//rptr���� ������ Ű�� ������ŭ lptr�� Ű�� ������ ������Ų��.
    free(rptr);										//rptr�� �����Ѵ�.
    for(i=pos+1;i<n;i++){							//�θ����� �б����� �ִ� Ű ���� �ڽĳ�忡�� �־����Ƿ�
        key_arr[i-1] = key_arr[i];					//�θ����� Ű ���� �� ĭ�� �ű��.
        p[i] = p[i+1];								//�θ��尡 ����Ű�� �ڽ� �����͵� �� ĭ�� �ű��.
    }
	//����÷ο찡 �߻��� �θ����� Ű ������ �ּ� Ű�� ������ �����Ѵٸ� success�� �ƴϸ� underflow�� ��ȯ�Ͽ� �θ����� ����÷ο츦 ó���Ѵ�.
	return ( (--ptr->n >= 1) ? success : underflow);
}