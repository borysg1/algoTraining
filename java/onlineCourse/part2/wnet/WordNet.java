import java.util.HashMap;
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {
    private final int V;
    private final HashMap<String, ArrayList<Integer>> hMap;
    private final HashMap<Integer, String> revHMap;
    private final Digraph graph;
    private final SAP localSap;

    private class Node {
        private int id;
        private String synset;

        public Node(String fileLine) {
            String[] tokens = fileLine.split(",");
            id = Integer.parseInt(tokens[0]);
            synset = tokens[1];
        }
    }

    public WordNet(String synsets, String hypernyms) {
        if (null == synsets || null == hypernyms)
            throw new java.lang.NullPointerException();

        In inSynsetF = new In(synsets);
        In inHypernymF = new In(hypernyms);
        int linesCnt = 0;

        hMap = new HashMap<String, ArrayList<Integer>>();
        revHMap = new HashMap<Integer, String>();
        /*
         * Get line number count in synsets.txt file - this will give digraph
         * size
         */
        while (inSynsetF.hasNextLine()) {
            Node tmp = new Node(inSynsetF.readLine());
            revHMap.put(tmp.id, tmp.synset);
            String [] nouns = tmp.synset.split(" ");
            for (int cntr = 0; cntr < nouns.length; cntr++) {
                ArrayList<Integer> values = hMap.get(nouns[cntr]);
                if (null == values)
                {
                    values = new ArrayList<Integer>();
                }
                values.add(tmp.id);
                hMap.put(nouns[cntr], values);
            }
            linesCnt++;
        }

        V = linesCnt;
        graph = new Digraph(V);

        while (inHypernymF.hasNextLine()) {
            String delim = ",";
            String[] hypernymListStr = inHypernymF.readLine().split(delim);
            int[] hypernymListInt = new int[hypernymListStr.length];
            for (int cntr = 0; cntr < hypernymListInt.length; cntr++)
                hypernymListInt[cntr] = Integer.parseInt(hypernymListStr[cntr]);

            /* Create graph */
            for (int cntr = 1; cntr < hypernymListInt.length; cntr++)
                graph.addEdge(hypernymListInt[0], hypernymListInt[cntr]);
        }

        DirectedCycle myCycle = new DirectedCycle(graph);
        int rootNo = 0;
        int outDegree = 0;
        
        if (null != myCycle.cycle())
            throw new java.lang.IllegalArgumentException();
        
        for (int vrtx = 0; vrtx < graph.V(); vrtx++)
        {
            outDegree = graph.outdegree(vrtx);
            if (0 == outDegree)
                rootNo++;
        }
        
        if (1 != rootNo)
            throw new java.lang.IllegalArgumentException();
        
        localSap = new SAP(graph);

        inSynsetF.close();
        inHypernymF.close();
    }

    public Iterable<String> nouns() {
        return hMap.keySet();
    }

    public boolean isNoun(String word) {
        if (null == word)
            throw new java.lang.NullPointerException();

        return hMap.keySet().contains(word);

    }

    public int distance(String nounA, String nounB) {
        if (null == nounA || null == nounB)
            throw new java.lang.NullPointerException();

        if ((!isNoun(nounA)) || (!isNoun(nounB)))
            throw new java.lang.IllegalArgumentException();

        /* Implement BFS to find shortest path */
        int distance = Integer.MAX_VALUE;

        distance = localSap.length(hMap.get(nounA), hMap.get(nounB));

        if (Integer.MAX_VALUE == distance)
            distance = -1;

        return distance;
    }

    public String sap(String nounA, String nounB) {
        if (null == nounA || null == nounB)
            throw new java.lang.NullPointerException();

        if ((!isNoun(nounA)) || (!isNoun(nounB)))
            throw new java.lang.IllegalArgumentException();

        /* Implement BFS to find shortest path */
        int idx = Integer.MAX_VALUE;

        ArrayList<Integer> listIdxA = hMap.get(nounA);
        ArrayList<Integer> listIdxB = hMap.get(nounB);
        
        idx = localSap.ancestor(listIdxA, listIdxB);
        
        return revHMap.get(idx);
    }

    public static void main(String[] args) {
        String synsets = args[0];
        String hypernyms = args[1];

        WordNet wNet = new WordNet(synsets, hypernyms);
        
        System.out.println("Distance " + wNet.distance("endorser", "nomad"));
    
    }
}
