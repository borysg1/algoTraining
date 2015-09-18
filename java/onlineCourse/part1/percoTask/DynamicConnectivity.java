
public class DynamicConnectivity {

	/**
	 * @param args
	 */
	
	private int[] id;
	private int[] sz;
	
	public DynamicConnectivity(int N)
	{
		id = new int[N];
		sz = new int[N];

		for (int i = 0; i < N; i++)
		{
			id[i] = i;
			sz[i] = 1;
		}
	}
	
	public boolean connected(int p, int q)
	{
		return id[p] == id[q];
	}
	
	public void union(int p, int q)
	{
		int pid = id[p];
		int qid = id[q];
		
		for(int i = 0; i < id.length; i++)
		{
			if( id[i] == pid ) id[i] = qid;
		}
	}

	public void printArray()
	{
		for(int i = 0; i < id.length; i++)
		{
			System.out.println("id[" + i + "]=" + id[i]);
		}
	}

	private int quickRoot(int p)
	{
		while(id[p] != p) p = id[p];
		return p;
	}
	
	public boolean quickConnected(int p, int q)
	{
		return quickRoot(p)==quickRoot(q);
	}
	
	public void quickUnion(int p, int q)
	{
		id[quickRoot(p)] = quickRoot(q);
	}

	public void quickUnionWeighted(int p, int q)
	{
		int i = quickRoot(p);
		int j = quickRoot(q);
		
		if (i == j) return;
		
		if(sz[i] < sz[j])
		{
			id[i] = j;
			sz[j] += sz[i];
		}
		else
		{
			id[j] = i;
			sz[i] += sz[j];
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test start");
		
		//int size = StdIn.readInt();
		
		DynamicConnectivity union = new DynamicConnectivity(10);
		union.union(1, 2);
		union.union(2, 4);
		union.union(4, 8);
		
		System.out.println("Print table after union");
		union.printArray();

		DynamicConnectivity quickUnion = new DynamicConnectivity(10);
		quickUnion.quickUnion(1, 2);
		quickUnion.quickUnion(2, 4);
		quickUnion.quickUnion(4, 8);

		System.out.println("Print table after quickUnion");
		quickUnion.printArray();

	}

}
