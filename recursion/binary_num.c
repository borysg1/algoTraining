#include <stdio.h>
#include <stdlib.h>

static void binaryPrint(int n)
{
   if (1 < n)
   {
      binaryPrint(n / 2);
      if ( 1 == (n % 2))
         printf("1");
      else
         printf("0");
   }
   else
   {
      if (0 == n)
         printf("0");
      if (1 == n)
         printf("1");
   }
}

int main (int argc, char * argv[])
{
   if (2 != argc)
   {
      printf("Usage: binary_num number\n");
      printf("'number' is positive integer number\n");
      exit(1);
   }

   int num = strtol(argv[1], NULL, 10);

   if (num < 0)
   {
      printf("number needa to be positive\n");
      exit(1);
   }

   binaryPrint(num);
   printf("\n");

   return 0;
}
