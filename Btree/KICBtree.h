#include <stdio.h>
#include <stdlib.h>

static int cnt = 0;		//메모리 해제 확인 변수

/*	노드 구조체 정의
*/
struct node{								
	int n;				//노드의 키의 개수
    int keys[2];		//노드의 키 값을 저장하는 배열
    struct node *p[3];	//노드 포인터를 저장하는 자기 참조 구조체 배열
};
struct node *root;		//루트 노드 선언

/*	반환값에 대한 명세 
	1. duplicateKey - 삽입한 키 값 중복
	2. insertOkey   - 삽입 가능
	3. searchFailed - 삭제할 값이 트리 내 존재하지 않음
	4. uunderflow   - 키 삭제 후 노드의 언더플로우 발생
	5. success	    - 삽입 또는 삭제 성공
*/
enum keyStatus{ duplicateKey, insertOkey, searchFailed, underflow, success};

/*	메모리 해제
*/
void freeAll(struct node *ptr){	//각 노드에 할당된 메모리를 해제한다.
	if(ptr != NULL){			//현재 노드가 NULL이 아닐 경우
		int i;
		for(i=0;i<=ptr->n;i++){
			freeAll(ptr->p[i]);	//자식 노드들을 순회한다.
		}
		free(ptr);				//현재 노드의 메모리를 해제한다.
		cnt++;					//메모리 해제 수를 체클한다.				
	}
}
/*	중위 순회
*/
void inorderTraversal(struct node *ptr){	
	int cnt = 0;

	if(ptr != NULL){						//현재 노드가 NULL이 아닐 경우
		int i, j;
		for(i=0;i<ptr->n+1;i++){				//현재 노드의 키의 갯수 + 1 에 따라
			if(ptr->p[i] != NULL){			//자식 노드가 NULL이 아닐 경우
				inorderTraversal(ptr->p[i]);//자식 노드를 순회한다.
			}
			else		//단말노드인지 아닌지를 검사한다.
				cnt++;	//cnt가 2일 경우 왼쪽 자식과 중앙 자식 포인터가 모두 NULL이므로 단말 노드 취급한다.
			if(i < ptr->n && cnt != 2){					
				if(ptr->n == 2){				//노드의 키의 갯수가 두개면 둘 다 출력한다.
					printf("< %d,", ptr->n);
					for(j=0;j<ptr->n;j++){
						if(j == ptr->n-1)
							printf(" %d", ptr->keys[j]);
						else
							printf("  %d, ", ptr->keys[j]);
					}
					printf(" >\n");
				}
				else						//노드의 키의 갯수가 한개면 하나만 출력한다.
					printf("< %d,  %d >\n", ptr->n, ptr->keys[i]);
			}
		}
		cnt = 0;
	}
}

/*	키 값 삽입
*/
void insertKey(int k){
	struct node *newNode = NULL;//루트에서 오버 플로우 발생시 새로운 루트의 중앙 노드
    int key = 0;				//루트에서 오버 플로우 발생시 새로운 루트의 키 값				
    
	enum keyStatus value;
	value = enuminsertKey(root, k, &key, &newNode);

	if(value == duplicateKey){
        printf("이미 키가 있습니다.\n");
	}
	
	//첫 페이즈에선 루트 노드를 생성, 이후 페이즈에선 루트에서 오버 플로우가 발생했을 경우 노드를 쪼깨어 연결해준다.
    else if(value == insertOkey){							 
		struct node *newNode2 = root;						//새로운 루트의 왼쪽 노드 생성 후 기존 루트 노드 복사
        root = (struct node*)malloc(sizeof(struct node));	//새로운 루트 노드를 생성
        root->n = 1;											//새로운 루트 노드의 키의 갯수를 1로 초기화
        root->keys[0] = key;									//새로운 루트 노드에 첫번째 키의 값에 key 삽입 
        root->p[0] = newNode2;								//새로운 루트 노드의 왼쪽 자식 노드에 newNode2 복사 
        root->p[1] = newNode;								//새로운 루트 노드의 오른쪽 자식 노드를 newNode 복사
    }
}

/*	키의 적정 위치
*/
int searchPos(int k, int *key_arr, int n){	//키의 적정 위치 노드의 번호(0 - 왼쪽, 1 - 중앙, 2 - 오른쪽)를 반환한다.
											//또는 키가 위치할 위치(0 - 왼쪽, 1 - 오른쪽)를 반환한다.
    int pos = 0;							
    while(pos<n && k>key_arr[pos]){		
		pos++;								//입력받은 키 값과 현재 노드에 있는 키 값을 비교해 입력받은 키 값이 크다면 pos를 ++한다.
	}										
    return pos;								//pos를 반환한다.
}

/*	키 값 삽입
*/
enum keyStatus enuminsertKey(struct node *ptr, int k, int *key, struct node **newNode){
    int newKey;					//삽입된 키 값
								//삽입된 키 값에서 왼쪽 노드는 기존에 연결되어 있는 노드다.
	struct node *newPtr = NULL;	//삽입된 키 값의 오른쪽 노드를 가리키는 포인터
	int lastKey;				//오버플로우 발생 노드에서 가장 큰 키의 값
	struct node *lastPtr = NULL;//오버플로우 발생시 쪼개진 이후 오른쪽 노드의 오른쪽 자식노드	
	int i = 0, pos = 0, n = 0;
	enum keyStatus value;

    //자식의 노드가 NULL인지 아닌지를 판단한다. 
	//NULL일 경우 현재 노드에 키를 삽입해도 된다는 insertOkey 반환
	if(ptr == NULL){		
        *newNode = NULL;
        *key = k;
        return insertOkey;
    }
    //현재 노드의 키의 갯수를 저장한다.
	n = ptr->n;							
	pos = searchPos(k, ptr->keys, n);	
	//현재 노드에서 키 값의 중복을 검사한다.
	if(pos<n && k==ptr->keys[pos]){		
        return duplicateKey;
	}
    //자식 노드를 순회하면서 키 값이 들어갈 노드를 찾는다.
	value = enuminsertKey(ptr->p[pos], k, &newKey, &newPtr);

	if(value != insertOkey){//중복 검사	 
        return value;
	}
	if(n < 2){									//현재 노드의 키의 갯수가 2개가 넘어가지 않은 경우
        pos = searchPos(newKey, ptr->keys, n);	//키가 삽입될 위치를 찾는다.
		//새로운 키 값이 작을 경우 기존 키 값을 오른쪽으로 옮겨준다.
		for(i=n;i>pos;i--){						
            ptr->keys[i] = ptr->keys[i-1];		//오른쪽에 있는 키 값을 왼쪽에 복사한다.
            ptr->p[i+1] = ptr->p[i];				//오른쪽에 위치한 자식 포인터를 왼쪽 포인터로 복사한다.
        }
		//새로운 키 값이 기존 키 값보다 클 경우 기존 새로운 키 값을 오른쪽에 삽입한다.
		ptr->keys[pos] = newKey;					//새로운 키 값을 삽입한다.
        ptr->p[pos+1] = newPtr;					//새로운 키의 삽입으로 오른쪽 자식 노드에 newPtr을 복사한다.
		++ptr->n;								//현재 노드의 키의 갯수를 증가시킨다.

		return success;
    }
	if(pos == 2){		 //오버플로우 발생, newKey값 이 가장 큰 경우	 
        lastKey = newKey;//newKey값을 lastKey에 복사한다.
        lastPtr = newPtr;//newPtr 포인터를 newPtr 포인터에 복사한다.  
    }
	else{					   //오버플로우 발생, 새로운 키 값이 가장 크지 않을 경우 
        lastKey = ptr->keys[1];//기존 노드의 가장 큰 키 값을 lastKey에 복사한다.
        lastPtr = ptr->p[2];   //가장 큰 키 값의 오른쪽 포인터를 lastPtr에 복사한다.
        if(pos == 0){					//pos가 0일 때 
			ptr->keys[1] = ptr->keys[0];//키의 첫번째 값을 두번째로 이동시킨다.
            ptr->p[2] = ptr->p[1];		//노드의 중앙 노드를 오른쪽 노드로 이동시킨다.
		}
        ptr->keys[pos] = newKey;//첫번째 키 자리에 새로운 키 값을 삽입한다.
        ptr->p[pos+1] = newPtr;	//newPtr은 새로운 키의 오른쪽 자식 노드를 의미하며 newNode.
    }
	(*key) = ptr->keys[1];						   //오버플로우 발생 노드의 오른쪽 키 값을 쪼개진 이후 부모노드의 키 값으로 지정한다. 
    (*newNode) = (struct node*)malloc(sizeof(struct node));//오버플로우 발생, 쪼개진 이후 부모노드의 오른쪽 노드
    ptr->n = 1;									   //현재 노드의 키 갯수를 감소한다. 
	(*newNode)->n = 1;							   //오버플로우 발생, 쪼개진 이후 오른쪽 노드 키의 갯수
    (*newNode)->p[0] = ptr->p[2];				   //오른쪽 노드의 왼쪽 자식노드	
    (*newNode)->keys[0] = lastKey;				   //오른쪽 노드의 키 값
    (*newNode)->p[(*newNode)->n] = lastPtr;        //오른쪽 노드의 중앙 자식 노드

	return insertOkey;
}

/*	키 값 삭제
*/
void deleteKey(int key){
    struct node *newNode;
    enum keyStatus value;

    value = enumdeleteKey(root,key);
    if(value){
		//트리 내 삭제할 값이 존재하지 않는 다면
		if(value == searchFailed)
			printf("입력한 키 값이 존재하지 않습니다.\n");
		//루트 노드에서 언더플로우가 발생했다면 루트노드를 제거한다.
		else if(value == underflow){
			newNode = root;
			root = root->p[0];
			free(newNode);
		}
	}
}

/*	키 삭제
*/
enum keyStatus enumdeleteKey(struct node *ptr, int key){
    struct node **p;	//부모 노드의 자식 포인터 시작점을 가리킨다.
	int pivot;			//합병 또는 재분배 때 왼쪽노드와 오른쪽 노드를 나누는 분기점
	int min = 1;		//3차 비트리의 최소 키의 갯수
    int *key_arr;		//부모 노드 키 값들의 배열을 복사한다.
	struct node *lptr;	
	struct node *rptr;
	int pos = 0, i = 0, n = 0;
    enum keyStatus value;
    
	//서치 실패에 대한 브레이크 포인터로 재귀적으로 단말노드까지 내려갔을 때 
	//노드가 존재하지 않을 경우 값을 못찾았다는 것이므로 searchFailed를 반환한다.
	if(ptr == NULL)
		return searchFailed;

    n = ptr->n;			//현재 노드의 키의 갯수
    key_arr = ptr->keys;//현재 노드가 갖는 키의 배열을 복사한다.
    p = ptr->p;			//현재 노드의 자식 노드 포인터의 시작점을 복사한다.
    pos = searchPos(key, key_arr, n);
   
	//삭제할 값이 단말 노드에 있는 경우
	if(p[0] == NULL){
		if(pos == n || key < key_arr[pos])
        	return searchFailed;
		for(i=pos+1;i<n;i++){			//pos가 0이라면 즉, 첫번째 키를 삭제한다면, 
            key_arr[i-1] = key_arr[i];	//두번째 키 값을 첫번째 키 값으로 옮긴다.
            p[i] = p[i+1];				//세번째 포인터를 두번째 포인터로 옮긴다.
        }
		//키 값 삭제시 현재 노드가 최소 키의 갯수를 만족한다면, success를 반환하고 아니라면 underflow를 반환한다.
        return ( (--ptr->n >= min)? success : underflow);
    }

	//삭제할 값이 단말노드에 있지 않은 경우
    if(pos < n && key == key_arr[pos]){
        struct node *qp = p[pos];		//삭제할 값을 가지고 있는 노드의 왼쪽 자식노드
		struct node *qpRightChild;		//qp의 오른쪽 자식노드		 
        int nkey;						//삭제할 값을 가지고 있는 노드의 왼쪽 자식노드의 키의 갯수
        while(1){
            nkey = qp->n;				 
            qpRightChild = qp->p[nkey]; //qoRightChild는 qp의 가장 오른쪽 자식 노드가 된다.
            if(qpRightChild == NULL)	//qp가 단말노드인 경우
                break;
            qp = qpRightChild;			//qp가 단말노드가 아닐 경우 다시 qp를 = qpRightChild로 초기화 하여 while문 재수행
        }
		//삭제할 키 값의 왼쪽 자식노드로 내려가 가장 큰 키 값을 찾았다면(while문), 삭제할 키값과 qp가 가지는 키 값을 교환한다.
        key_arr[pos] = qp->keys[nkey-1];
        qp->keys[nkey - 1] = key;		
    }

	value = enumdeleteKey(p[pos], key);//1. 자식 노드를 순회하여 최소키의 갯수를 만족하는지 아닌지를 확인한다.
									   //2. 자식 노드를 순회하면서 삭제할 키값을 대체할 키 값을 찾는다.
	if(value != underflow)
        return value;

	//underflow 발생 시 왼쪽 형제 노드가 최소 키의 갯수를 만족한다면, 오른쪽 형제 노드에서 언더플로우가 발생한다면
    if(pos > 0 && p[pos-1]->n > min){ 
		
		pivot = pos - 1;//왼쪽 노드와 오른쪽 노드의 분기점
        lptr = p[pivot];//언더플로우 발생 시 왼쪽 형제노드
        rptr = p[pos];	//언더플로우가 발생한 오른쪽 노드

        rptr->p[rptr->n + 1] = rptr->p[rptr->n];//언더플로우가 발생한 오른쪽 노드의 왼쪽 포인터를 중앙포인터에 복사한다.
        rptr->n++;								//언더플로우가 발생한 오른쪽 노드의 키의 갯수를 증가시킨다.
        rptr->keys[0] = key_arr[pivot];			//언더플로우가 발생한 오른쪽 노드에 부모노드의 키 값을 복사한다.
        rptr->p[0] = lptr->p[lptr->n];			//언더플로우가 발생한 오른쪽 노드의 왼쪽 포인터에 왼쪽 형제노드의 오른쪽 포인터를 복사한다.
        key_arr[pivot] = lptr->keys[--lptr->n];	//부모노드의 키에 왼쪽 형제노드의 두번째 키 값을 복사한다.
        return success;
    }
	
	//underflow 발생 시 오른쪽 형제 노드가 최소 키의 갯수를 만족한다면, 왼쪽 형제 노드에서 언더플로우가 발생한다면
    if(pos<n && p[pos+1]->n > min){

		pivot = pos;		//왼쪽 노드와 오른쪽 노드의 분기점
		lptr = p[pivot];	//언더플로우가 발생한 왼쪽 노드
		rptr = p[pivot+1];	//언더플로우 발생 시 오른쪽 노드
		
		lptr->keys[lptr->n] = key_arr[pivot];//언더플로우가 발생한 왼쪽 노드의 키 값에 부모노드의 키 값을 복사한다.
		lptr->p[lptr->n + 1] = rptr->p[0];	 //언더플로우가 발생한 왼쪽 노드의 오른쪽 포인터에 오른쪽 형제 노드의 왼쪽포인터로 복사한다.
		key_arr[pivot] = rptr->keys[0];		 //부모노드에 오른쪽 형제 노드의 첫번째 키 값을 복사한다.
		lptr->n++;							 //언더플로우가 발생한 노드는 키 하나를 받았음으로 키의 갯수를 증가시킨다.
		rptr->n--;							 //오른쪽 형제 노드는 첫번째키 값을 부모노드에 주었음으로 키의 갯수를 감소시킨다.
		rptr->keys[0] = rptr->keys[1];		 //오른쪽 형제 노드의 오른쪽 키 값을 왼쪽 키 값으로 옮겨준다.
		rptr->p[0] = rptr->p[1];				 //오른쪽 형제 노드의 왼쪽 자식 포인터에 중앙 자식포인터를 복사한다. 
		rptr->p[rptr->n] = rptr->p[rptr->n + 1];//오른쪽 형제노드의 중앙 자식 포인터에 오른쪽 자식포인터를 복사한다.
		return success;
	}

	//언더 플로우가 발생하였는데, 왼쪽과 오른쪽 형제 노드 모두 최소 키의 갯수를 만족하지 못한다면
    if(pos == n)		//가장 오른쪽 포인터에서 오버플로우 발생
        pivot = pos-1;  //분기점을 pos-1로 초기화한다.
    else				//그 외 포인터에서 오버플로우 발생
        pivot = pos;	//분기점을 pos로 초기화한다.
	rptr = p[pivot+1];
	lptr = p[pivot];	
	//
	//          ex) 55삭제				ex) 65 삭제				ex) 45 삭제				ex) 45 삭제			ex) 55 삭제
	//				pivot = 1				pivot = 1			    pivt = 0			    pivt = 0		    pivt = 1
	//
	//				  40			  		  40		   			  40					  40				  40
	//				↙  ↘					↙  ↘					↙  ↘			  		↙  ↘		  	    ↙  ↘
	//			  10     50 60			  10     50 60			  10     50 60			  10     50			  10     50
	//           ↙↘   ↙ ↓ ↘		 ↙↘   ↙ ↓ ↘		 ↙↘   ↙ ↓ ↘		 ↙↘   ↙↘		 ↙↘   ↙↘
	//		    5   15 45  55  65		5   15 45  55  65		5   15 45  55  65		5   15 45  55 		5   15 45  55 
	//
	//          | lptr = 55			  |	* lptr = 55         |	* lptr = 45 		|	* lptr = 45		|	* lptr = 45 
	//			| rptr = 65			  |	* rptr = 65			|	* rptr = 55			|	* rptr = 55		|	* rptr = 55
	//
	lptr->keys[lptr->n] = key_arr[pivot];			//형제노드에서 언더플로우 발생 시 분기점에 있는 부모노드의 키 값을 가져온다.
    lptr->p[lptr->n + 1] = rptr->p[0];				//언더플로우 노드로 가져온 부모노드의 키 값의 오른쪽 포인터를 형제노드에 있는 첫번째 포인터에서 복사한다.
	for(i=0;i<rptr->n;i++){							//rptr에 키 값이 존재하다면
        lptr->keys[lptr->n + 1 + i] = rptr->keys[i];//rptr에 있는 값들을 lptr로 옮긴다.
        lptr->p[lptr->n + 2 + i] = rptr->p[i+1];		//rptr 에 있는 포인터도 lptr로 옮긴다.
    }
    lptr->n = lptr->n + rptr->n+1;					//rptr에서 가져온 키의 갯수만큼 lptr의 키의 갯수를 증가시킨다.
    free(rptr);										//rptr를 해제한다.
    for(i=pos+1;i<n;i++){							//부모노드의 분기점에 있는 키 값을 자식노드에게 주었으므로
        key_arr[i-1] = key_arr[i];					//부모노드의 키 값을 한 칸씩 옮긴다.
        p[i] = p[i+1];								//부모노드가 가리키는 자식 포인터도 한 칸씩 옮긴다.
    }
	//언더플로우가 발생한 부모노드의 키 갯수가 최소 키의 갯수를 만족한다면 success를 아니면 underflow를 반환하여 부모노드의 언더플로우를 처리한다.
	return ( (--ptr->n >= 1) ? success : underflow);
}