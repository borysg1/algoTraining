import java.util.Set;
import java.util.Arrays;
import java.util.TreeSet;

import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination
{
   private static final int GAME_STATS_COL_CNT = 3;
   private static final int WINS_CNT_IDX = 0;
   private static final int LOSES_CNT_IDX = 1;
   private static final int LEFT_CNT_IDX = 2;

   private final int teamCnt;
   private final String [] teams;
   private final int [][] teamData;
   private int inputCapacity;

   public BaseballElimination(String filename)
   {
      In inputStream = new In(filename);

      teamCnt = inputStream.readInt();

      teams = new String[teamCnt];
      teamData = new int [teamCnt][teamCnt + GAME_STATS_COL_CNT];

      for (int cntr = 0; cntr < teamCnt; cntr++)
      {
         teams[cntr] = inputStream.readString();
         for (int teamCntr = 0; teamCntr < (teamCnt + GAME_STATS_COL_CNT); teamCntr++)
         {
            teamData[cntr][teamCntr] = inputStream.readInt();
         }
      }
   }

   private int calculateIdx(String name)
   {
      int index = Integer.MAX_VALUE;
      
      for (int cntr = 0; cntr < teamCnt; cntr++)
      {
         if (name.equals(teams[cntr]))
         {
            index = cntr;
            break;
         }
      }
      
      if (Integer.MAX_VALUE == index)
         throw new java.lang.IllegalArgumentException();
      else
         return index;
   }

   private Set<String> trivialEliminated(int index)
   {
      Set<String> elim = new TreeSet<String>();
      for (int cntr = 0; cntr < teamCnt; cntr++)
      {
         if ((cntr != index) && ((teamData[index][WINS_CNT_IDX] + 
                             teamData[index][LEFT_CNT_IDX]) <
                             (teamData[cntr][WINS_CNT_IDX])))
         {
            elim.add(teams[cntr]);
         }
      }
      return elim;
   }

   private FlowNetwork buildFlowNetwork(int index)
   {
      int secLayerVertCntr = 1;
      int secLayerCnt = secondLayerVertCnt();
      int thirdLayerCnt = thirdLayerVertCnt();
      boolean [] thirdLayerDone = new boolean[thirdLayerCnt];
      
      int vertCnt = 1 + secLayerCnt + thirdLayerCnt + 1;
   
      inputCapacity = 0;
      //System.out.println("vertCnt " + vertCnt);
      FlowNetwork net = new FlowNetwork(vertCnt);
      
      for (int rowCntr = 0; rowCntr < teamCnt; rowCntr++)
      {
         for (int colCntr = 0; colCntr < teamCnt; colCntr++)
         {
            if ((colCntr > rowCntr) && (colCntr != index) && (rowCntr != index))
            {
               //System.out.println("rowCntr: " + rowCntr + " colCntr: " + colCntr);
               int thirdLayerVertIdx1 = 1 + secLayerCnt + rowCntr;
               int thirdLayerVertIdx2 = 1 + secLayerCnt + colCntr;

               //System.out.println("secLayerVertCntr: " + secLayerVertCntr);
               FlowEdge ed1 = new FlowEdge(0, secLayerVertCntr, teamData[rowCntr][colCntr + GAME_STATS_COL_CNT]);
               inputCapacity += teamData[rowCntr][colCntr + GAME_STATS_COL_CNT];

               //System.out.println("thirdLayerVertIdx1: " + thirdLayerVertIdx1);
               //System.out.println("thirdLayerVertIdx2: " + thirdLayerVertIdx2);

               if (rowCntr > index)
                  thirdLayerVertIdx1--;

               if (colCntr > index)
                  thirdLayerVertIdx2--;

               //System.out.println("after thirdLayerVertIdx1: " + thirdLayerVertIdx1);
               //System.out.println("after thirdLayerVertIdx2: " + thirdLayerVertIdx2);

               FlowEdge ed2 = new FlowEdge(secLayerVertCntr, thirdLayerVertIdx1, Double.POSITIVE_INFINITY);
               FlowEdge ed3 = new FlowEdge(secLayerVertCntr, thirdLayerVertIdx2, Double.POSITIVE_INFINITY);

               net.addEdge(ed1);
               net.addEdge(ed2);
               net.addEdge(ed3);

               if (!thirdLayerDone[thirdLayerVertIdx1 - 1 - secLayerCnt])
               {
                  int flowValue = teamData[index][WINS_CNT_IDX] + teamData[index][LEFT_CNT_IDX] -
                              teamData[rowCntr][WINS_CNT_IDX];

                  if (flowValue > 0)
                  {
                     FlowEdge ed = new FlowEdge(thirdLayerVertIdx1, vertCnt - 1, flowValue);
                     net.addEdge(ed);
                  }

                  thirdLayerDone[thirdLayerVertIdx1 - 1 - secLayerCnt] = true;
               }

               if (!thirdLayerDone[thirdLayerVertIdx2 - 1 - secLayerCnt])
               {
                  int flowValue = teamData[index][WINS_CNT_IDX] + teamData[index][LEFT_CNT_IDX] -
                              teamData[colCntr][WINS_CNT_IDX];

                  if (flowValue > 0)
                  {
                     FlowEdge ed = new FlowEdge(thirdLayerVertIdx2, vertCnt - 1, flowValue);
                     net.addEdge(ed);
                  }

                  thirdLayerDone[thirdLayerVertIdx2 - 1 - secLayerCnt] = true;
               }
               
               secLayerVertCntr++;
            }
         }
      }
      return net;
   }
   
   private int secondLayerVertCnt()
   {
      int sum = 0;
      int cntr = teamCnt;
      
      while (--cntr > 0)
         sum += cntr;
      
      return sum - (teamCnt - 1);
   }
   
   private int thirdLayerVertCnt()
   {
      return teamCnt - 1;
   }

   /*number of teams*/
   public int numberOfTeams()
   {
      return teamCnt;
   }

   /*all teams*/
   public Iterable<String> teams()
   {
      System.out.println("Teams call " + Arrays.toString(teams));

      return Arrays.asList(teams);
   }

   /*number of wins for given team*/
   public int wins(String team)
   {
      int index = calculateIdx(team);
      return teamData[index][WINS_CNT_IDX];
   }

   /*number of losses for given team*/
   public int losses(String team)
   {
      int index = calculateIdx(team);
      return teamData[index][LOSES_CNT_IDX];
   }

   /*number of remaining games for given team*/
   public int remaining(String team)
   {
      int index = calculateIdx(team);
      return teamData[index][LEFT_CNT_IDX];
   }

   /*number of remaining games between team1 and team2*/
   public int against(String team1, String team2)
   {
      int index1 = calculateIdx(team1);
      int index2 = calculateIdx(team2);
      return teamData[index1][GAME_STATS_COL_CNT + index2];
   }

   /*is given team eliminated?*/
   public boolean isEliminated(String team)
   {
      int index = calculateIdx(team);
      
      if (!trivialEliminated(index).isEmpty())
         return true;
      
      FlowNetwork fn = buildFlowNetwork(index);
      FordFulkerson ff = new FordFulkerson(fn, 0, 1 + secondLayerVertCnt() + thirdLayerVertCnt()/* + 1 - 1*/);
      
      return inputCapacity != ff.value();
   
   }

   /*subset R of teams that eliminates given team; null if not eliminated*/
   public Iterable<String> certificateOfElimination(String team)
   {
      int index = calculateIdx(team);

      Set<String> elim;

      //System.out.println("trivial");
      elim = trivialEliminated(index);
      if (elim.size() == teamCnt - 1)
         return elim;
      
      FlowNetwork fn = buildFlowNetwork(index);
      FordFulkerson ff = new FordFulkerson(fn, 0, 1 + secondLayerVertCnt() + thirdLayerVertCnt()/* + 1 - 1*/);

      if (inputCapacity == ff.value())
      {
          if (0 != elim.size())
              return elim;
          else
              return null;
      }
      else
      {
         int thirdLayerStartIdx = 1 + secondLayerVertCnt();
         int thirdLayerVertCnt = thirdLayerVertCnt();
         for (int cntr = 0; cntr < thirdLayerVertCnt; cntr++)
         {
            if (ff.inCut(thirdLayerStartIdx + cntr))
            {
               int nameIdx = cntr;
               if (nameIdx >= index)
                  nameIdx++;
               elim.add(teams[nameIdx]);
            }
         }
         return elim;
      }
   }
   
   public static void main(String [] args)
   {
       BaseballElimination division = new BaseballElimination(args[0]);
       for (String team : division.teams()) {
           if (division.isEliminated(team)) {
               StdOut.print(team + " is eliminated by the subset R = { ");
               for (String t : division.certificateOfElimination(team)) {
                   StdOut.print(t + " ");
               }
               StdOut.println("}");
           }
           else {
               StdOut.println(team + " is not eliminated");
           }
       }      
   }
}
