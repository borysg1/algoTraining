
class UnionFind
{
   public:
   UnionFind(int N);
   ~UnionFind();
   bool connected(int p, int q);
   void make_union(int p, int q);
   

   private:
   int * mUFTab;
   int tabSize;
};
