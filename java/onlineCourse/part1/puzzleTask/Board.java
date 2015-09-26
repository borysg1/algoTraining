import edu.princeton.cs.algs4.Stack;

public class Board {

   private final int boardDim;
   private final int [] [] boardData;
   private int blankXPos;
   private int blankYPos;
   
   // construct a board from an N-by-N array of blocks
   // (where blocks[i][j] = block in row i, column j)
   public Board(int[][] blocks)
   {
      if (null == blocks)
         throw new java.lang.NullPointerException();
      else
      {
         boardDim = blocks[0].length;
         boardData = new int[boardDim][boardDim];
         for (int row = 0; row < boardDim; row++)
         {
            for (int col = 0; col < boardDim; col++)
            {
               boardData[row][col] = blocks[row][col];
               if (0 == blocks[row][col])
               {
                  blankXPos = row;
                  blankYPos = col;
               }
            }
         }
      }
   }

   // board dimension N
   public int dimension()
   {
      return boardDim;
   }
   
   // number of blocks out of place
   public int hamming()
   {
      int numOutOfBlock = 0;
      
      for (int row = 0; row < boardDim; row++)
         for (int col = 0; col < boardDim; col++)
            if (0 != boardData[row][col] && ((row*boardDim + col) 
                  != (boardData[row][col]-1)))
               numOutOfBlock++;

      return numOutOfBlock;
   }

   // sum of Manhattan distances between blocks and goal
   public int manhattan()
   {
      int manhattanResult = 0;
      
      for (int row = 0; row < boardDim; row++)
      {
         for (int col = 0; col < boardDim; col++)
         {
            if (0 != boardData[row][col])
            {
               int value = boardData[row][col] -1;
               int targetRow = value / boardDim;
               int targetCol = value % boardDim;
               int rowDist;
               int colDist;
               if (targetRow > row)
               {
                  rowDist = targetRow -row;
               }
               else
               {
                  rowDist = row - targetRow;
               }

               if (targetCol > col)
               {
                  colDist = targetCol -col;
               }
               else
               {
                  colDist = col - targetCol;
               }

               
               manhattanResult += rowDist + colDist;
            }
         }
      }

      return manhattanResult;
   }
   

   // is this board the goal board?
   public boolean isGoal()
   {
      boolean result = true;

      /*Last cell should contain '0'*/
      if (0 == boardData[boardDim-1][boardDim-1])
      {
         for (int row = 0; row < boardDim; row++)
         {
            for (int col = 0; col < boardDim; col++)
            {
               if ((row* boardDim + col) != (boardData[row][col]-1) 
                     && (row != (boardDim-1) || col != (boardDim-1)))
               {
                  result = false;
                  break;
               }
            }
            if (!result)
            {
               break;
            }
         }
      }
      else
      {
         result = false;
      }

      return result;
   }
   
   // a boadr that is obtained by exchanging two adjacent blocks in the same row
   public Board twin()
   {
      int [] [] twinBoard = new int[boardDim][boardDim];
      int first = -1;
      int firstRow = -1;
      int firstCol = -1;
      boolean found = false;
      for (int row = 0; row < boardDim; row++)
      {
         for (int col = 0; col < boardDim; col++)
         {
            twinBoard[row][col] = boardData[row][col];
            
            if (!found)
            {
               if (0 != boardData[row][col])
               {
                  if (-1 == first)
                  {
                     first = boardData[row][col];
                     firstRow = row;
                     firstCol = col;
                  }
                  else if (firstRow == row)
                  {
                     twinBoard[firstRow][firstCol] = boardData[row][col];
                     twinBoard[row][col] = first;
                     found = true;
                  }
                  else
                  {
                     first = boardData[row][col];
                     firstRow = row;
                     firstCol = col;
                  }
               }
               else
               {
                  first = -1;
                  firstRow = -1;
                  firstCol = -1;
               }
            }
         }
      }
      return new Board(twinBoard);
   }
   
   // does this board equal y?
   public boolean equals(Object y)
   {
      if (this == y)
         return true;
      
      if (null == y)
         return false;
      
      if (y.getClass() != this.getClass())
         return false;
      
      Board that = (Board) y;
      
      if (this.boardDim != that.boardDim)
         return false;
      
      for (int row = 0; row < boardDim; row++)
      {
         for (int col = 0; col < boardDim; col++)
         {
            if (this.boardData[row][col] != that.boardData[row][col])
            {
               return false;
            }
         }
      }
      return true;
   }

   // all neighboring boards
   public Iterable<Board> neighbors()
   {
      Stack<Board> neighbors = new Stack<Board>();

      /*Shift blank up*/
      int shiftXTop = blankXPos -1;
      if (0 <= shiftXTop)
      {
         int [][] shiftXTopData = new int[boardDim][boardDim];
         for (int row = 0; row < boardDim; row++)
         {
            for (int col = 0; col < boardDim; col++)
            {
               shiftXTopData[row][col] = boardData[row][col];
            }
         }
         
         shiftXTopData[blankXPos -1][blankYPos] = boardData[blankXPos][blankYPos];
         shiftXTopData[blankXPos][blankYPos] = boardData[blankXPos -1][blankYPos];
         Board shiftXTopBoard = new Board(shiftXTopData);
         //neighbors.insert(shiftXTopBoard);
         neighbors.push(shiftXTopBoard);
/*         System.out.println("Up");
         System.out.println(shiftXTopBoard.toString());
         System.out.println("manhatan: " + shiftXTopBoard.manhattan());
         System.out.println("");*/
      }

      /*Shift blank down*/
      int shiftXBottom = blankXPos + 1;
      if (boardDim > shiftXBottom)
      {
         int [][] shiftXBottomData = new int[boardDim][boardDim];
         for (int row = 0; row < boardDim; row++)
         {
            for (int col = 0; col < boardDim; col++)
            {
               shiftXBottomData[row][col] = boardData[row][col];
            }
         }
         shiftXBottomData[blankXPos +1][blankYPos] = 
                                              boardData[blankXPos][blankYPos];
         shiftXBottomData[blankXPos][blankYPos] = 
                                              boardData[blankXPos + 1][blankYPos];
         Board shiftXBottomBoard = new Board(shiftXBottomData);
         //neighbors.enqueue(shiftXBottomBoard);
         neighbors.push(shiftXBottomBoard);
/*         System.out.println("Bottom");
         System.out.println(shiftXBottomBoard.toString());
         System.out.println("manhatan: " + shiftXBottomBoard.manhattan());
         System.out.println("");*/
      }

      /*Shift blank left*/
      int shiftYLeft = blankYPos - 1;
      if (0 <= shiftYLeft)
      {
         int [][] shiftYLeftData = new int [boardDim][boardDim];
         for (int row = 0; row < boardDim; row++)
         {
            for (int col = 0; col < boardDim; col++)
            {
               shiftYLeftData[row][col] = boardData[row][col];
            }
         }
         shiftYLeftData[blankXPos][blankYPos - 1] = 
                                             boardData[blankXPos][blankYPos];
         shiftYLeftData[blankXPos][blankYPos] = 
                                             boardData[blankXPos][blankYPos - 1];
         Board shiftYLeftBoard = new Board(shiftYLeftData);
         //neighbors.enqueue(shiftYLeftBoard);
         neighbors.push(shiftYLeftBoard);
/*         System.out.println("Left");
         System.out.println(shiftYLeftBoard.toString());
         System.out.println("manhatan: " + shiftYLeftBoard.manhattan());
         System.out.println("");*/
      }
      
      /*Shift blank right*/
      int shiftYRight = blankYPos + 1;
      if (boardDim > shiftYRight)
      {
         int [][] shiftYRightData = new int [boardDim][boardDim];
         for (int row = 0; row < boardDim; row++)
         {
            for (int col = 0; col < boardDim; col++)
            {
               shiftYRightData[row][col] = boardData[row][col];
            }
         }
         shiftYRightData[blankXPos][blankYPos + 1] = boardData[blankXPos][blankYPos];
         shiftYRightData[blankXPos][blankYPos] = boardData[blankXPos][blankYPos + 1];
         Board shiftYRightBoard = new Board(shiftYRightData);
         //neighbors.enqueue(shiftYRightBoard);
         neighbors.push(shiftYRightBoard);
/*         System.out.println("Right");
         System.out.println(shiftYRightBoard.toString());
         System.out.println("manhatan: " + shiftYRightBoard.manhattan());
         System.out.println("");*/
      }

      return neighbors;
   }

   // string representation of this board (in the output format specified below)
   /*public String toString()
   {
      String output = new String(Integer.valueOf(boardDim ) + "\n");
      for (int row = 0; row < boardDim; row++)
      {
         output = output.concat(" ");
         for (int col = 0; col < boardDim; col++)
         {
            output = 
                output.concat((Integer.valueOf(boardData[row][col])) + " ");
         }
         output = output.concat("\n");
      }
      
      return output;
   }*/
   
   public String toString() {
      StringBuilder s = new StringBuilder();
      s.append(boardDim + "\n");
      for (int i = 0; i < boardDim; i++) {
          for (int j = 0; j < boardDim; j++) {
              s.append(String.format("%2d ", boardData[i][j]));
          }
          s.append("\n");
      }
      return s.toString();
  }
   
   
   // unit tests (not graded)
   public static void main(String[] args) 
   {

   }
}