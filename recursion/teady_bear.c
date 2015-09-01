#include <stdio.h>
#include <stdlib.h>

static int bears(int n)
{
   int res = 0;
   printf("Cnt %d\n", n);
   if (42 == n)
   {
      return 1;
   }
   else if (42 > n)
   {
      return 0;
   }
   else if (0 == (n % 5))
   {
      res = bears(n - 42);
      if (0 == res)
      {
         if (0 == (n % 2))
         {
            res = bears(n / 2);
            if (0 == res)
            {
               if ((0 == (n % 3)) || (0 == (n % 4)))
               {
                  int last_dig = n % 10;
                  int next_last_dig = (n % 100) / 10;
                  int multi = last_dig * next_last_dig;
                  printf("multi %d\n", multi);
                  res = bears(n - multi);
               }
            }
         }
         if ((0 == (n % 3)) || (0 == (n % 4)))
         {
            int last_dig = n % 10;
            int next_last_dig = (n % 100) / 10;
            int multi = last_dig * next_last_dig;
            printf("multi %d\n", multi);
            res = bears(n - multi);
            if (0 == res)
            {
               if (0 == (n % 2))
               {
                  res = bears(n / 2);
               }
            }
         }
      }
   }
   else if (0 == (n % 2))
   {
      res = bears(n / 2);
      if (0 == res)
      {
         if (0 == (n % 5))
         {
            res = bears(n - 42);
            if ( 0 == res)
            {
               if ((0 == (n % 3)) || (0 == (n % 4)))
               {
                  int last_dig = n % 10;
                  int next_last_dig = (n % 100) / 10;
                  int multi = last_dig * next_last_dig;
                  printf("multi %d\n", multi);
                  res = bears(n - multi);
               }
            }
         }
         if ((0 == (n % 4)) || (0 == (n % 4)))
         {
            int last_dig = n % 10;
            int next_last_dig = (n % 100) / 10;
            int multi = last_dig * next_last_dig;
            printf("multi %d\n", multi);
            res = bears(n - multi);
            if (0 == res)
            {
               if (0 == (n % 5))
               {
                  res = bears(n - 42);
               }
            }
         }
      }
   }
   else if ((0 == (n % 3)) || (0 == (n % 4)))
   {
      int last_dig = n % 10;
      int next_last_dig = (n % 100) / 10;
      int multi = last_dig * next_last_dig;
      printf("multi %d\n", multi);
      res = bears(n - multi);
      if (0 == res)
      {
         if (0 == (n % 2))
         {
            res = bears(n / 2);
            if (0 == res)
            {
               if (0 == (n % 5))
               {
                  res = bears(n -42);
               }
            }
         }
         if (0 == (n % 5))
         {
            res = bears(n - 42);
            if (0 == res)
            {
               if (0 == (n % 2))
               {
                  res = bears(n / 2);
               }
            }
         }
      }
   }
   else
   {
      return 0;
   }
   
   return res;
}

int main(int argc, char * argv[])
{
   long int bear_num = strtol(argv[1], NULL, 10);
   int res = bears(bear_num);
   printf("Result: %d\n", res);
   return 0;
}
