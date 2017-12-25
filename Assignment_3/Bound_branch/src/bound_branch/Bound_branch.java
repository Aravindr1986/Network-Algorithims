
package bound_branch;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Nodes
{
    int x;
    int y;
    int degree; 
    Nodes(int x,int y,int n)
    {
        this.x=x;
        this.y=y;
        degree=0;
    }
    public void print_nodes()
    {
        System.out.println("x = "+x+" y = "+y+" degree = "+degree);
    }
   public int get_cost(Nodes n1,Nodes n2)
   {
       int length=(int)Math.sqrt( Math.abs(Math.pow((n1.x-n2.x),2.0)-Math.pow((n1.y-n2.y),2.0)));
       return length;
   }
}

public class Bound_branch
{
    
    void print_nw(int topo[][],int m,int n)
    {
        int i,j;
        for(i=0;i<m;i++)
        {
            for(j=0;j<n;j++)
            {
                System.out.print("\t"+topo[i][j]);
            }
            System.out.print("\n");
        }
    }
    public static void main(String[] args)
    {
        ArrayList<Nodes> q = new ArrayList();
        Random r= new Random(100);
        System.out.println("Enter the number of nodes:");
        Scanner in = new Scanner(System.in);
        int nm = in.nextInt();
        int sum=0;
        //System.out.println("Enter the value of k:");
        //int k = in.nextInt();
         
         int mat[][]=new int [nm][];
         int topo[][]=new int[nm][nm];
         int i,j;
          Bound_branch b=new Bound_branch();
         for(i=0;i<nm;i++)
         {
             for(j=0;j<nm;j++)
             {
                 if(i==j)
                     topo[i][j]=-1;
                 else
                     topo[i][j]=0;
             }
         }
          b.print_nw(topo, nm,nm);
         ArrayList<Integer> visited= new ArrayList();   //visited nodes
         for(i=0;i<nm;i++)
         {
             int x=r.nextInt(100);
             int y=r.nextInt(100);
             System.out.println("x="+x+"y="+y);
             Nodes n=new Nodes(x,y,nm);
             q.add(n);
         }
         System.out.println("Network Topology:");   
         for( i=0;i<nm;i++)   //presetting the NW topology
         {
             Nodes n1=q.get(i);
             mat[i]=new int[nm];
             for(j=0;j<nm;j++)
             {
                 Nodes n2=q.get(j);
                 mat[i][j]=n1.get_cost(n1, n2);
                 if(i==j)
                     mat[i][j]=Integer.MAX_VALUE;
                 System.out.print("\t"+mat[i][j]);
             }
             System.out.print("\n");
         }   
         
          visited.add(0);
          int l=0;
          while(l<100)
          {
                int smallest=Integer.MAX_VALUE,src=0,dest=0;
                for(i=0;i<visited.size();i++)//finding the smallest amoung the visited links
                {
                    int k=visited.get(i);
                    for(int c=0;c<nm;c++)
                    {
                        if(mat[k][c]<smallest)
                        {
                            if(c==src && dest==k)
                            {
                                System.out.println("c="+c+"k="+k);
                                continue;           //not to fall in local optimum
                            }
                            smallest=mat[k][c];
                            dest=c;
                            src=k;
                        }
                    }
                }
                //System.out.println("src= "+(src+1)+" dest="+(dest+1)+" smallest cost= "+smallest);    
                if(topo[src][dest]==0)
                {
                    System.out.println(src+","+dest);
                    sum+=mat[src][dest];
                    topo[src][dest]+=1;
                    if(topo[dest][src]==0)                  //Check if alternate path exist
                    {
                        topo[dest][src]+=1;
                    }
                    mat[src][dest]=Integer.MAX_VALUE;       //not revisit the link again
                    mat[dest][src]=Integer.MAX_VALUE;
                    visited.add(dest);                    //adding to visited list
                    for(i=0;i<nm;i++)                    //checking if the nodes connected to src have hop count <3 if the path is accepted
                    {
                        if((topo[i][src]+1)<=4 && topo[i][dest]==0 && topo[i][src]>0)        //if hop count through current selection is less than n and no other path selected
                        {
                               topo[i][dest]=topo[i][src]+1;
                               mat[i][dest]=Integer.MAX_VALUE;
                        }
                    }
                }
                else
                {
                   // System.out.println("Not taken");
                }
               // b.print_nw(topo, nm,nm);
                
                l++;
          }
          System.out.println("Total cost:"+sum);
                
         // b.print_nw(mat, nm,nm);
    }
    int hop_count(int src,int dest,int topo[][],int nm)
    {
        int flag=1,i,j;
        for(i=0;i<nm;i++)
        {
                if((topo[i][src]+1)>2 && topo[i][dest]==0)
                {
                    flag=0;
                }
        }
        return flag;
    }
   
}
