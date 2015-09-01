#include <stdio.h>

static void triangle(int m, int n)
{
   int cntr;
   if(m <= n)
   {
      for(cntr = 0; cntr < m; cntr++)
         printf("*");
      printf("\n");
      triangle(m + 1, n);
      for(cntr = 0; cntr < m; cntr++)
         printf("*");
      printf("\n");
   }
   return;
}

int main (int argc, char * argv[])
{
   triangle(3,5); 
   return 0;
}

