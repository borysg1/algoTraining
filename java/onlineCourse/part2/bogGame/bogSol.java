import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver
{
    /* 
     * Initializes the data structure using the given array of strings as the dictionary.
     * (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     */
    private int rowCnt;
    private int colCnt;

    private final MyTrieST<Character> trie;

    public BoggleSolver(String[] dictionary)
    {
        trie = new MyTrieST<Character>();

        for (int cntr = 0; cntr < dictionary.length; cntr++)
        {
            trie.put(dictionary[cntr], pointsCalc(dictionary[cntr].length()));
        }
    }
    
    private static char pointsCalc(int len)
    {
        if (len >= 3 && len <= 4)
            return 1;
        else if (len == 5)
            return 2;
        else if (len == 6)
            return 3;
        else if (len == 7)
            return 5;
        else if (len >= 8)
            return 11;
        else
            return 0;
    }
    
    private void traverseBoard(Set<String> matches, BoggleBoard board, String str, int x, int y,
                                int level, boolean [][] visited)
    {
        //System.out.println("Str: " + str);
        visited[x][y] = true;
        if (level > 2)
        {
            if (trie.contains(str))
                matches.add(str);
            else
            {
                //System.out.println("Size: " + ((Queue<String>)(trie.keysWithPrefix(str))).size());
                /*if (((Queue<String>) trie.keysWithPrefix(str)).size() == 0)
                {
                    return;
                }*/
                if (!trie.isPrefix(str))
                {
                    return;
                }
            }
        }

        /*left*/
        if (x > 0 && (!visited[x - 1][y]))
        {
            //System.out.println("New letter: " + board.getLetter(x - 1, y));
            if ('Q' == board.getLetter(x - 1, y))
            {
                traverseBoard(matches, board, str + "QU", x - 1, y, level + 2, visited);
            }
            else
            {
                traverseBoard(matches, board, str + board.getLetter(x - 1, y), x - 1, y, level + 1, visited);
            }
            visited[x - 1][y] = false;
        }
        /*left-top*/
        if (x > 0 && y > 0 && (!visited[x - 1][y - 1]))
        {
            //System.out.println("New letter: " + board.getLetter(x - 1, y - 1));
            if ('Q' == board.getLetter(x - 1, y - 1))
            {
                traverseBoard(matches, board, str + "QU", x - 1, y - 1, level + 2, visited);
            }
            else
            {
                traverseBoard(matches, board, str + board.getLetter(x - 1, y - 1), x - 1, y - 1, level + 1, visited);
            }
            visited[x - 1][y - 1] = false;
        }
        /*top*/
        if (y > 0 && (!visited[x][y - 1]))
        {
            //System.out.println("New letter: " + board.getLetter(x, y - 1));
            char letter = board.getLetter(x, y - 1);
            if ('Q' == letter)
            {
                traverseBoard(matches, board, str + "QU", x, y - 1, level + 2, visited);
            }
            else
            {
                traverseBoard(matches, board, str + letter, x, y - 1, level + 1, visited);
            }
            visited[x][y - 1] = false;
        }
        /*top-right*/
        if ((x < (rowCnt - 1)) && y > 0 && (!visited[x + 1][y - 1]))
        {
            //System.out.println("New letter: " + board.getLetter(x + 1, y - 1));
            char letter = board.getLetter(x + 1, y - 1);
            if ('Q' == letter)
            {
                traverseBoard(matches, board, str + "QU", x + 1, y - 1, level + 2, visited);
            }
            else
            {
                traverseBoard(matches, board, str + letter, x + 1, y - 1, level + 1, visited);
            }
            visited[x + 1][y - 1] = false;
        }
        /*right*/
        if (x < (rowCnt - 1) && (!visited[x + 1][y]))
        {
            //System.out.println("New letter: " + board.getLetter(x + 1, y));
            char letter = board.getLetter(x + 1, y);
            if ('Q' == letter)
            {
                traverseBoard(matches, board, str + "QU", x + 1, y, level + 2, visited);
            }
            else
            {
                traverseBoard(matches, board, str + letter, x + 1, y, level + 1, visited);
            }
            visited[x + 1][y] = false;
        }
        /*right-bottom*/
        if ((x < (rowCnt - 1)) && (y < (colCnt - 1)) && (!visited[x + 1][y + 1]))
        {
            //System.out.println("New letter: " + board.getLetter(x + 1, y + 1));
            char letter = board.getLetter(x + 1, y + 1); 
            if ('Q' == letter)
            {
                traverseBoard(matches, board, str + "QU", x + 1, y + 1, level + 2, visited);
            }
            else
            {
                traverseBoard(matches, board, str + letter, x + 1, y + 1, level + 1, visited);
            }
            visited[x + 1][y + 1] = false;
        }
        /*bottom*/
        if ((y < (colCnt - 1)) && (!visited[x][y + 1]))
        {
            //System.out.println("New letter: " + board.getLetter(x, y + 1));
            char letter = board.getLetter(x, y + 1);
            if ('Q' == letter)
            {
                traverseBoard(matches, board, str + "QU", x, y + 1, level + 2, visited);
            }
            else
            {
                traverseBoard(matches, board, str + letter, x, y + 1, level + 1, visited);
            }
            visited[x][y + 1] = false;
        }
        /*bottom-left*/
        if ((x > 0) && (y < (colCnt - 1)) && (!visited[x - 1][y + 1]))
        {
            //System.out.println("New letter: " + board.getLetter(x - 1, y + 1));
            char letter = board.getLetter(x - 1, y + 1); 
            if ('Q' == letter)
            {
                traverseBoard(matches, board, str + "QU", x - 1, y + 1, level + 2, visited);
            }
            else
            {
                traverseBoard(matches, board, str + letter, x - 1, y + 1, level + 1, visited);
            }
            visited[x - 1][y + 1] = false;
        }
    }
    
    /*
     *  Returns the set of all valid words in the given Boggle board, as an Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
        rowCnt = board.rows();
        colCnt = board.cols();
        Set<String> matches = new TreeSet<String>();
        for (int i = 0; i < rowCnt; i++)
        {
            for (int j = 0; j < colCnt; j++)
            {
                boolean [][] visited = new boolean[rowCnt][colCnt];
                char letter = board.getLetter(i, j);
                if ('Q' == letter)
                {
                    traverseBoard(matches, board, "QU", i, j, 2, visited);
                }
                else
                {
                    traverseBoard(matches, board, "" + letter, i, j, 1, visited);
                }
            }
        }
        return matches;
    }

    /*
     * Returns the score of the given word if it is in the dictionary, zero otherwise.
     * (You can assume the word contains only the uppercase letters A through Z.)
     */
    public int scoreOf(String word)
    {
        //return pointsCalc(word.length());
        char val = (char) trie.get(word);
        
        if (null == ((Character) val))
            return 0;
        else
            return val;
    }

    public static void main(String[] args)
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
