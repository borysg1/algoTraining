#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static void letters(char c)
{
   if ('A' == c)
   {
      printf("%c",c);
   }
   else
   {
      letters(c - 1);
      printf("%c",c);
      letters(c - 1);
   }
}

int main (int argc, char * argv[])
{
   if ((2 != argc) || (1 != strlen(argv[1])) || ('A' > argv[1][0]) || ('Z' < argv[1][0]))
   {
      printf("Usage: ./letter_pattern letter\n");
      printf("where capital_letter is single capital character from 'A' to 'Z'\n");
      printf("Program output:\n");
      printf("If letter is 'A' then print 'A'\n");
      printf("For other values generaly name it c\n");
      printf(" - the output for previous letter (c - 1)\n");
      printf(" - followed by letter c itself\n");
      printf(" - followed by by second copy of output for the previous letter (c - 1)\n");
      exit(1);
   }

   letters(argv[1][0]);
   printf("\n");
   return 0;
}
