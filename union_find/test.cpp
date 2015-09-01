#include <iostream>

#include "UnionFind.h"

using namespace std;

static void checkConnected(UnionFind * obj, int p, int q);

int main (int argc, char * argv [] )
{
   cout << "Start" << endl;

   UnionFind uf(10);

   checkConnected(&uf, 1, 2);

   uf.make_union(1, 2);

   cout << "After union" << endl;
   checkConnected(&uf, 1, 2);

   return 0;
}

void checkConnected(UnionFind *obj, int p, int q)
{
   if ( obj->connected (p,q) )
   {
      cout << "Connected" << endl;
   }
   else
   {
      cout << "Not connected" << endl;
   }
}
