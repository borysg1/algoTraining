#include <stdio.h>
#include <malloc.h>

typedef struct elemStruct
{
   struct elemStruct * next;
   int val;
}elemS;

static elemS * listHead = NULL;
static elemS * listTail = NULL;

int isEmpty()
{
   if (NULL == listHead && NULL == listTail)
      return 1;
   else
      return 0;
}

void enqueue(int val)
{
   elemS * newElem = (elemS*)malloc(sizeof(elemS));
   newElem->val = val;
   newElem->next = NULL;
   if (isEmpty())
   {
      listHead = listTail = newElem;
   }
   else
   {
      elemS * tempTail = listTail;
      listTail = newElem;
      tempTail->next = listTail;
   }
}

int dequeue(void)
{
   int res = -1;
   if (isEmpty())
   {
      printf("Queue empty!\n");
   }
   else if (listHead == listTail)
   {
      res = listHead->val;
      free(listHead);
      listHead = NULL;
      listTail = NULL;
   }
   else
   {
      elemS * tempHead = listHead;
      listHead = tempHead->next;
      res = tempHead->val;
      free(tempHead);
   }

   return res;
}

void printList(void)
{
   if(isEmpty())
   {
      printf("Queue empty!\n");
   }
   else
   {
      elemS * tempElem = listHead;
      do
      {
         printf("%d\t",tempElem->val);
         tempElem = tempElem->next;
      }while(NULL != tempElem);
   }
   printf("\n\n");
}

int main(int argc, char * argv[])
{
   int elemVal;
   printf("enqueue\n");
   enqueue(1);
   printf("enqueue\n");
   enqueue(3);
   printf("enqueue\n");
   enqueue(5);

   printList();

   printf("deqeue\n");
   elemVal = dequeue();
   printf("elemVal %d\n", elemVal);
   printList();

   printf("deqeue\n");
   elemVal = dequeue();
   printf("elemVal %d\n", elemVal);
   printList();
   
   printf("enqueue\n");
   enqueue(123);
   printList();

   printf("deqeue\n");
   elemVal = dequeue();
   printf("elemVal %d\n", elemVal);
   printList();

   printf("deqeue\n");
   elemVal = dequeue();
   printf("elemVal %d\n", elemVal);
   printList();

   printf("enqueue\n");
   enqueue(4444);
   printList();

   printf("deqeue\n");
   elemVal = dequeue();
   printf("elemVal %d\n", elemVal);
   printList();

   return 0;
}
