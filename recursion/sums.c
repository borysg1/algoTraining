#include <stdio.h>

int sum(int start, int tempSum, int aim)
{
   int next = start - 1;
   if (next > 0)
   {
      tempSum += next;
      //printf("next %d, tempSum %d, aim %d\n", next, tempSum, aim);
      if(tempSum == aim)
         return next;
      else if (tempSum > aim)
         return -1;
      else
         return sum(next, tempSum, aim);
   }
   else
   {
      return -1;
   }
}


int main(int argc, char * argv[])
{

   if (argc != 2)
   {
      printf("Application needs one positive number as an argument.\nAs an output it prints sum of min count of consecutive numbers which gives number specified as an argument.\nIf not such combination is possible IMPOSSIBLE is printed.\n");
      return -1;
   }

   int N = strtol(argv[1], NULL, 10);
   int cntr;
   int i;
   int res;

   int startFrom = N / 2 + N % 2;

   //printf("startFrom %d\n", startFrom);

   for(cntr = startFrom; cntr > 0; cntr--)
   {
      res = sum(cntr, cntr, N);
      if (-1 != res)
      {
         printf("%d = ", N);
         for (i = res; i < cntr; i++)
            printf("%d + ", i);
         printf("%d\n", cntr);
         break;
      }
   }

   if (-1 == res)
      printf("IMPOSSIBLE\n");

   return 0;
}

