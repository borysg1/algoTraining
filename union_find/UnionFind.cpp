#include <iostream>
#include "UnionFind.h"

UnionFind::UnionFind(int N)
{

   mUFTab = new int[N];
   tabSize = N;

   for(int i = 0; i < N; i++)
   {
      mUFTab[i] = i;
   }
}

UnionFind::~UnionFind()
{
   delete [] mUFTab;
}

bool UnionFind::connected(int p, int q)
{
   return (mUFTab[p] == mUFTab[q]) ? true : false;
}

void UnionFind::make_union(int p, int q)
{
   int pid = mUFTab[p];
   int qid = mUFTab[q];

   for(int i = 0; i < tabSize; i++)
   {
      if(mUFTab[i] == pid) mUFTab[i] = qid;
   }
}
