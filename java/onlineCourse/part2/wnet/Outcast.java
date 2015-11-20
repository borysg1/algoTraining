public class Outcast {
    
    private WordNet wNet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns)
    {
        int minDist = -1;
        String minStr = null;
        int dist;
        for (String str : nouns)
        {
            dist = 0;
            for (String str_in : nouns)
            {
                dist += wNet.distance(str, str_in);
            }

            if (minDist < dist)
            {
                minDist = dist;
                minStr = str;
            }
        }
        
        return minStr;
    }

    // see test client below
    public static void main(String[] args) {

    }
};