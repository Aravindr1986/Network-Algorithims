
package shortestpath;
import java.util.*;
import java.util.Scanner;
class ShortestPath
{
     int V;
     int sum;
     int link[][];
     public ShortestPath(int n)
     {
         link=new int[n][n];
     }
     void set_v(int x)
     {
         V=x;
         sum=0;
         
     }
    int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;
 
        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }
 
        return min_index;
    }
    void printSolution(int dist[], int n,int src)
    {
        int b[]= {2,0,2,1,2,7,7,2,5,6,2,0,2,1,2,7,7,2,5,6,2,0,2,1,2,7,7,2,5,6};
        System.out.println("Vertex   Distance from Source :"+src);
        for (int i = 0; i < V; i++)
        {
            int s=dist[i]*Math.abs(b[src]-b[i]);
            sum+=s;
            System.out.println(i+" \t\t "+dist[i]+"\t\t"+s);
        }
        if((src+1)==V)
        {
             System.out.println("Total cost of Network : "+sum);
             int cnt=0;
             for(int k=0;k<V;k++)
             {
                 for(int j=0;j<V;j++)
                 {
                     if(link[k][j]==0&& k!=j)
                         cnt++;
                 }
             }
             System.out.println("Unsued link count:"+cnt);
        }
    }
    void dijkstra(int graph[][], int src)
    {
        int dist[] = new int[V]; 
        Boolean sptSet[] = new Boolean[V];
        for (int i = 0; i < V; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
         dist[src] = 0;
         
        for (int count = 0; count < V-1; count++)
        {
            int x=0;
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;
            for (int v = 0; v < V; v++)
                if (!sptSet[v] && graph[u][v]!=0 && dist[u] != Integer.MAX_VALUE &&  dist[u]+graph[u][v] < dist[v])
                {
                             dist[v] = dist[u] + graph[u][v];
                             System.out.println("v:"+v+" u:"+u+" dist_v: "+dist[v]+" dist_u: "+ dist[u]+" graph[u][v]:"+graph[u][v]);
                             x=v;
                }
            link[u][x]=1;
        }
         printSolution(dist, V,src);
    }
    public static void main (String[] args)
    {
        System.out.println("Enter the nmber of nodes:");
        Scanner in = new Scanner(System.in);
        int no_of_nodes = in.nextInt();
        System.out.println("Enter the value of k:");
        int k = in.nextInt();
        Random r=new Random();
        int graph[][] = new int[no_of_nodes][no_of_nodes];
       for(int i=0;i<no_of_nodes;i++)
       {
           for(int j=0;j<no_of_nodes;j++)
           {
               if(i!=j)
                graph[i][j]=300;
           }
       }
       int cnt[]=new int[no_of_nodes];
       for(int i=0;i<no_of_nodes;i++)
       {
           while(cnt[i]<k)
           {
               int ind=(r.nextInt(10000)%no_of_nodes);
               if(graph[i][ind]!=1 && ind!=i)
               {
                   graph[i][ind]=1;
                   cnt[i]++;
               }
           }
       }
       for(int i=0;i<no_of_nodes;i++)
       {
           System.out.print("graph["+i+"]:\t");
           for(int j=0;j<no_of_nodes;j++)
           {
               System.out.print("\t" +graph[i][j]);
           }
           System.out.println("\n");
       }
        ShortestPath t = new ShortestPath(no_of_nodes);
        t.set_v(no_of_nodes);
        for(int i=0;i<no_of_nodes;i++)
        {
            t.dijkstra(graph, i);
        }
       
    }
}