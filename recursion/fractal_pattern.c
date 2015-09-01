#include <stdio.h>

int is_power2(int val)
{
   int res = 0;
   if (val > 1)
   {
      while ((1 != val) && (0 == (val % 2)))
         val = val / 2;

      if (1 == val)
         res = 1;
   }

   return res;
}

static void pattern(int n, int i)
{
   int x, y;
   if (n > 0) pattern(n / 2, i);
   else return;

   for (x = 0; x < i; x++) printf("  ");
   for (y = 0; y < n; y++) printf("* "); printf("\n");

   i += n / 2; n = n / 2;

   if (n > 0) pattern(n, i);
}

int main(int argc, char * argv[])
{
   if (3 != argc)
   {
      printf("Usage: ./fractal_pattern max_col_cnt max_col_offset\n"); 
      return 1;
   }

   int max_col_cnt = strtol(argv[1], NULL, 10);
   int max_col_offset = strtol(argv[2], NULL, 10);

   if (0 == is_power2(max_col_cnt))
   {
      printf("max_col_cnt needs to be power of 2\n"); 
      return 1;
   }
   pattern(max_col_cnt, max_col_offset);
   return 0;
}
