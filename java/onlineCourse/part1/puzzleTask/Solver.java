import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class Solver {
   
   private MinPQ<SearchNode> prioQueue;
   private MinPQ<SearchNode> twinPrioQueue;
   private Stack<Board> solutionStack;
   private Queue<Board> solutionQueue;
   
   private boolean solved;
   private boolean twinSolved;

   
   // find a solution to the initial board (using the A* algorithm)
   public Solver(Board initial)
   {
      prioQueue = new MinPQ<SearchNode>();
      twinPrioQueue = new MinPQ<SearchNode>();
      solutionStack = new Stack<Board>();
      solutionQueue = new Queue<Board>();
      
      solved = false;
      twinSolved = false;
      
      Board initialTwinBoard = initial.twin();

      SearchNode node = new SearchNode(initial, null, 0, null);
      SearchNode twinNode = new SearchNode(initialTwinBoard, null, 0, null);

      prioQueue.insert(node);
      twinPrioQueue.insert(twinNode);
      
      SearchNode currentNode = prioQueue.delMin();
      SearchNode currentTwinNode = twinPrioQueue.delMin();

      Board currentBoard = currentNode.getCurrent();
      Board currentTwinBoard = currentTwinNode.getCurrent();
      
      solutionQueue.enqueue(currentBoard);
      
      solved = currentBoard.isGoal();
      twinSolved = currentTwinBoard.isGoal();
      
      while (!solved && !twinSolved)
      {
         //System.out.println("ORG");
         for (Board temp : currentBoard.neighbors())
         {
            /*optimization*/
            if (!temp.equals(currentNode.getPrevious()))
            {
               SearchNode tempNode = new SearchNode(temp, currentNode.getCurrent(),
                                                currentNode.getCurrentMoves() + 1, 
                                                currentNode);
               prioQueue.insert(tempNode);
            }
         }
         //System.out.println("");
         
         //System.out.println("TWIN");
         for (Board tempTwin : currentTwinBoard.neighbors())
         {
            /*optimization*/
            if (!tempTwin.equals(currentTwinNode.getPrevious()))
            {
               SearchNode tempNode = new SearchNode(tempTwin,
                                            currentTwinNode.getCurrent(),
                                            currentTwinNode.getCurrentMoves() + 1,
                                            currentTwinNode);
               twinPrioQueue.insert(tempNode);
            }
         }
         //System.out.println("");
         currentNode = prioQueue.delMin();
         //System.out.println(currentNode.getCurrent().toString());
         currentTwinNode = twinPrioQueue.delMin();
         
         currentBoard = currentNode.getCurrent();
         currentTwinBoard = currentTwinNode.getCurrent();
         solutionQueue.enqueue(currentBoard);

         solved = currentBoard.isGoal();
         twinSolved = currentTwinBoard.isGoal();
         
      }
      
      if (solved)
      {
         SearchNode solutionSearchNode = currentNode;
         do
         {
            
            solutionStack.push(solutionSearchNode.getCurrent());
            solutionSearchNode = solutionSearchNode.getPrevNode();
         } while (null != solutionSearchNode);
      }
   }

   // is the initial board solvable?
   public boolean isSolvable()
   {
      return solved;
   }

   // min number of moves to solve initial board; -1 if unsolvable
   public int moves()
   {
      if (solved)
      {
         //return solutionQueue.size() -1;
         return solutionStack.size() - 1;
      }
      else
      {
         return -1;
      }
   }

   // sequence of boards in a shortest solution; null if unsolvable
   public Iterable<Board> solution()
   {
      if (solved)
      {
         //return solutionQueue;
         return solutionStack;
      }
      else
      {
         return null;
      }
   }

   private class SearchNode implements Comparable<SearchNode>
   {
      private Board currentBoard;
      private Board prevBoard;
      private int movesCnt;
      private SearchNode prevNode;

      public SearchNode(Board curr, Board prev, int moves, SearchNode prevSNode)
      {
         currentBoard = curr;
         prevBoard = prev;
         movesCnt = moves;
         prevNode = prevSNode;
         //System.out.println("moves: " + moves + " manhatan: " + curr.manhattan());
      }

      public Board getCurrent()
      {
         return currentBoard;
      }

     public Board getPrevious()
      {
         return prevBoard;
      }

     public int getCurrentMoves()
      {
         return movesCnt;
      }

     public SearchNode getPrevNode()
     {
        return prevNode;
     }
   
     
     @Override
     public int compareTo(SearchNode arg0) {
        
        int thisManhatan = this.currentBoard.manhattan();
        int thatManhatan = arg0.currentBoard.manhattan();
        int thisPrio = this.movesCnt + thisManhatan;
        int thatPrio = arg0.movesCnt + thatManhatan;
        if (thisPrio == thatPrio)
        {
           return thisManhatan -thatManhatan;
        }
        else
        {
           return thisPrio - thatPrio;
        }
      }
     
   }
   
   // solve a slider puzzle
   public static void main(String[] args) 
   {
      // create initial board from file
      In in = new In(args[0]);
      int N = in.readInt();
      int[][] blocks = new int[N][N];
      for (int i = 0; i < N; i++)
          for (int j = 0; j < N; j++)
              blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);

      // solve the puzzle
      Solver solver = new Solver(initial);

      // print solution to standard output
      if (!solver.isSolvable())
          StdOut.println("No solution possible");
      else {
          StdOut.println("Minimum number of moves = " + solver.moves());
          try {
            Thread.sleep(1);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
          for (Board board : solver.solution())
              StdOut.println(board.toString());
      }
   }
}