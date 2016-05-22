#include <iostream>

int dim;
char ** matrix;

int main(int argc, char * argv[])
{
   std::cout << "Specify matrix dimension" << std::endl;
   std::cin >> dim;
   std::cout << "Specified matrix dimension: " << dim << std::endl;
   
   matrix = new char*[dim];

   for (int idx = 0; idx < dim; idx++)
   {
      matrix[idx] = new char[dim];
   }

  
   std::cout << "Specify matrix elements" << std::endl;

   for(int i = 0; i < dim; i++)
   {
      for(int j = 0; j < dim; j++)
      {
         std::cin >> matrix[i][j];
      }
   }
   
   for (int idx = 0; idx < dim; idx++)
   {
      delete matrix[idx];
   }
   
   delete matrix;

   return 0;

}

int max_constellation_size(char ** mtrx, int dim)
{
   int size = 0;

   int * idx_tab = new int[]

   return size;
}
