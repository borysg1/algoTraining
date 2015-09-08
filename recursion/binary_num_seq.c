#include <stdio.h>
#include <stdlib.h>
#include <math.h>

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
}

static void numbers(char * prefix, int k)
{
   int limit = pow(2, k);
   int num = pow(2, k + 1);
   while (num > limit)
   {
      printf("%s", prefix);
      binaryPrint(limit);
      printf("\n");
      limit++;
   }
}

int main (int argc, char * argv[])
{
   if (3 != argc)
   {
      printf("Usage: binary_num_seq prefix number\n");
      printf("'prefix' is string of 0's and 1's\n");
      printf("'number' is positive integer number which specifies bit length of binary combination - all possible combination of specified length will be printed\n");
      exit(1);
   }

   int num = strtol(argv[2], NULL, 10);

   if (num < 0)
   {
      printf("number needa to be positive\n");
      exit(1);
   }

   numbers(argv[1], num);
   printf("\n");

   return 0;
}
