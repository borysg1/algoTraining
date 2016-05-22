#include <stdio.h>
#include <malloc.h>

#define NODE_MAX_CNT 1000

int V, E;

typedef struct NodeStruct {
   struct NodeStruct * next;
   int value;
} NodeS;

typedef struct ListStruct {
   NodeS * head;
   NodeS * tail;
   NodeS * current;
} ListS;

typedef struct GraphStruct {
   ListS neighbour[NODE_MAX_CNT];
} GraphS;

typedef struct StackStruct {
   NodeS * head;
} StackS;

int visited[NODE_MAX_CNT];

GraphS graph;
StackS stack;

void listAdd(ListS * list, NodeS * node)
{
   if (NULL == list->head && NULL == list->tail)
   {
      list->head = list->tail = node;
   }
   else
   {
      list->tail->next = node;
   }
}

void listReset(ListS * l)
{
   l->current = l->head;
}


NodeS * listNext(ListS * l)
{
   NodeS * n = l->current;
   if (NULL != n)
      l->current = l->current->next;

   return n;
}

NodeS * listRemHead(ListS * l)
{
   NodeS * n = l->head;

   if (NULL != n)
      l->head = l->head->next;

   return n;
      
}

void push(StackS * s, NodeS * n)
{
   if (NULL == s->head)
      s->head = n;
   else
   {
      n->next = s->head;
      s->head = n;
   }
}

NodeS * pop(StackS *s)
{
   NodeS * n;
   if (NULL == s->head)
      n = NULL;
   else
   {
      n = s->head;
      s->head = s->head->next;
   }

   return n;
}

void initGraph(int nodeCnt)
{
   int cntr;
   for (cntr = 0; cntr < nodeCnt; cntr++)
   {
      graph.neighbour[cntr].head = NULL;
      graph.neighbour[cntr].tail = NULL;
      graph.neighbour[cntr].current = NULL;
   }
}

void dfs(GraphS * g, int n)
{
   visited[n] = 1;
   NodeS * node;
   NodeS * stackNode;

   //printf("before listReset\n");
   listReset(&g->neighbour[n]); 
   //printf("after listReset\n");
   
   while (NULL != (node = listNext(&g->neighbour[n])))
   {
      if (0 == visited[node->value])
      {
         dfs(g, node->value);
      }
   }
 
   stackNode = (NodeS *)malloc(sizeof(*stackNode));   

   stackNode->next = NULL;
   stackNode->value = n;
   push(&stack,stackNode); 
}

int main (int argc, char * argv[])
{
   int testCnt;
   int cntr;

   scanf("%d", &testCnt);
   printf("Cases cnt %d \n", testCnt);
   
   for (cntr = 0; cntr < testCnt; cntr++)
   {
      int edgeCntr;
      int vertCntr;
      int start, end;
      NodeS * resultNode;
      scanf("%d %d", &V, &E);
      printf("V: %d, E: %d\n", V, E);
      
      initGraph(V);

      for (edgeCntr = 0; edgeCntr < E; edgeCntr++)
      {
         scanf("%d %d", &start, &end);
         NodeS * newNode = (NodeS *)malloc(sizeof(*newNode));
         newNode->next = NULL;
         newNode->value = end - 1;
         listAdd(&graph.neighbour[start - 1], newNode);
      }

      for (vertCntr = 0; vertCntr < V; vertCntr++)
      {
         if (0 == visited[vertCntr])
         {
            dfs(&graph, vertCntr);
         }
      }

      printf("#%d", cntr);
      /*Print result*/
      while (NULL != (resultNode = pop(&stack)))
      {
         printf(" %d", resultNode->value + 1);
      }
      printf("\n");

   }

   return 0;
}
