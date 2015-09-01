#include <sstream>
#include <iostream>
#include <string>

using namespace std;

static long long int factorial(long long int nr);


int main (int argc, char * argv[])
{
   long long int i;
   string str(argv[1]);
   istringstream iss(str);
   iss >> i;
   cout << "Factorial for " << i << " equals: " << factorial(i) << endl; 
   return 0;
}

static long long int factorial(long long int nr)
{
   if(nr > 1)
   {
      return nr * factorial(nr - 1);
   }
   else
   {
      return 1;
   }
}
