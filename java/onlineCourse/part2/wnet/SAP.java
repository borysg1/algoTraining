import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private Digraph copyG;

    /* constructor takes a digraph (not necessarily a DAG) */
    public SAP(Digraph G) {
        if (null == G)
            throw new java.lang.NullPointerException();

        copyG = new Digraph(G);
    }

    private boolean checkVertexInBound(int vertex) {
        return ((0 <= vertex) && (vertex < copyG.V()));
    }

    /* length of shortest ancestral path between v and w; -1 if no such path */
    public int length(int v, int w) {
        if ((!checkVertexInBound(v)) || (!checkVertexInBound(w)))
            throw new java.lang.IndexOutOfBoundsException();

        BreadthFirstDirectedPaths bfdDirPaths1 = new BreadthFirstDirectedPaths(copyG, v);
        BreadthFirstDirectedPaths bfdDirPaths2 = new BreadthFirstDirectedPaths(copyG, w);

        int minLength = Integer.MAX_VALUE;

        for (int vrtx = 0; vrtx < copyG.V(); vrtx++) {
            int dist1 = bfdDirPaths1.distTo(vrtx);
            int dist2 = bfdDirPaths2.distTo(vrtx);
            int dist = dist1 + dist2;
            if (Integer.MAX_VALUE != dist1 && Integer.MAX_VALUE != dist2 && dist < minLength)
                minLength = dist;
        }

        if (Integer.MAX_VALUE == minLength)
            minLength = -1;
        
        return minLength;
    }

    /*
     * a common ancestor of v and w that participates in a shortest ancestral
     * path; -1 if no such path
     */
    public int ancestor(int v, int w)
    {
        if ((!checkVertexInBound(v)) || (!checkVertexInBound(w)))
            throw new java.lang.IndexOutOfBoundsException();

        BreadthFirstDirectedPaths bfdDirPaths1 = new BreadthFirstDirectedPaths(copyG, v);
        BreadthFirstDirectedPaths bfdDirPaths2 = new BreadthFirstDirectedPaths(copyG, w);
        
        int minLength = Integer.MAX_VALUE;
        int commAncestor = Integer.MAX_VALUE;

        for (int vrtx = 0; vrtx < copyG.V(); vrtx++) {
            int dist1 = bfdDirPaths1.distTo(vrtx);
            int dist2 = bfdDirPaths2.distTo(vrtx);
            int dist = dist1 + dist2;
            if (Integer.MAX_VALUE != dist1 && Integer.MAX_VALUE != dist2 && dist < minLength)
            {
                minLength = dist;
                commAncestor = vrtx;
            }
        }

        if (Integer.MAX_VALUE == minLength)
        {
            commAncestor = -1;
        }

        return commAncestor;
    }

    /*
     * length of shortest ancestral path between any vertex in v and any vertex
     * in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        for (int vrtx : v) {
            if (!checkVertexInBound(vrtx))
                throw new java.lang.IndexOutOfBoundsException();
        }

        for (int vrtx : w) {
            if (!checkVertexInBound(vrtx))
                throw new java.lang.IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfdDirPaths1 = new BreadthFirstDirectedPaths(copyG, v);
        BreadthFirstDirectedPaths bfdDirPaths2 = new BreadthFirstDirectedPaths(copyG, w);
       
        int minPath = Integer.MAX_VALUE;

        for (int vrtx = 0; vrtx < copyG.V(); vrtx++) {
            int dist1 = bfdDirPaths1.distTo(vrtx);
            int dist2 = bfdDirPaths2.distTo(vrtx);
            int dist = dist1 + dist2;
            if (Integer.MAX_VALUE != dist1 && Integer.MAX_VALUE != dist2 && dist < minPath)
            {
                minPath = dist;
            }
        }

        if (Integer.MAX_VALUE == minPath)
        {
            minPath = -1;
        }

        return minPath;
    }

    /*
     * a common ancestor that participates in shortest ancestral path; -1 if no
     * such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        for (int vrtx : v) {
            if (!checkVertexInBound(vrtx))
                throw new java.lang.IndexOutOfBoundsException();
        }

        for (int vrtx : w) {
            if (!checkVertexInBound(vrtx))
                throw new java.lang.IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfdDirPaths1 = new BreadthFirstDirectedPaths(copyG, v);
        BreadthFirstDirectedPaths bfdDirPaths2 = new BreadthFirstDirectedPaths(copyG, w);
        
        int minLength = Integer.MAX_VALUE;
        int commAncestor = Integer.MAX_VALUE;

        for (int vrtx = 0; vrtx < copyG.V(); vrtx++) {
            int dist1 = bfdDirPaths1.distTo(vrtx);
            int dist2 = bfdDirPaths2.distTo(vrtx);
            int dist = dist1 + dist2;
            if (Integer.MAX_VALUE != dist1 && Integer.MAX_VALUE != dist2 && dist < minLength)
            {
                minLength = dist;
                commAncestor = vrtx;
            }
        }

        if (Integer.MAX_VALUE == minLength)
        {
            commAncestor = -1;
        }

        return commAncestor;        
        
    }

    /* do unit testing of this class */
    public static void main(String[] args) {
        System.out.println("Test started file name " + args[0]);
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        System.out.println("Graph vertices " + G.V() + " graph edges " + G.E());
        System.out.println(G.toString());
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
