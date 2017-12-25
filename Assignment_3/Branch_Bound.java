package branch_bound;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Node
{
    int x;
    int y;
    int degree; 
    
    Node(int x,int y,int n)
    {
        this.x=x;
        this.y=y;
        degree=0;
    
    }
   public void print_nodes()
   {
       System.out.println("x = "+x+" y = "+y+" degree = "+degree);
   }
   public int get_cost(Node n1,Node n2)
   {
       int length=(int)Math.sqrt( Math.abs(Math.pow((n1.x-n2.x),2.0)-Math.pow((n1.y-n2.y),2.0)));
       return length;
   }
}
class Links
{
    Node n1;
    Node n2;
    int cost;
    public Links(Node n1,Node n2,int cost)
    {
        this.n1=n1;
        this.n2=n2;
        this.cost=cost;
    }
}
public class Branch_Bound 
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
        ArrayList<Node> q = new ArrayList();
        
        Random r= new Random(100);
        System.out.println("Enter the number of nodes:");
        Scanner in = new Scanner(System.in);
        int nm = in.nextInt();
        int cost=0;
        int sum=0;
        int mat[][]=new int [nm][];             //cost matrix         
        int topo[][]=new int[nm][nm];           //topology matrix
        int hops[][]=new int[nm][nm];           //hops matrix
        ArrayList<Integer> []nod = new ArrayList[nm];
        int i,j;
        Branch_Bound b=new Branch_Bound();
        for(i=0;i<nm;i++)  //generation the nodes
        {
            int x=r.nextInt(100);
            int y=r.nextInt(100);
            System.out.println("x="+x+"y="+y);
            Node n=new Node(x,y,nm);
            q.add(n);
        }
        ArrayList<Integer> Stack= new ArrayList();   //visited nodes
        for( i=0;i<nm;i++)   //presetting with cost value the NW topology
        {
            Node n1=q.get(i);
            mat[i]=new int[nm];
            nod[i]=new ArrayList();
            for(j=0;j<nm;j++)
            {
                Node n2=q.get(j);
                int c;
                if(i==j)
                {
                    c=Integer.MAX_VALUE;
                    topo[i][j]=-1;
                    mat[i][j]=c;
                }
                else
                {
                   c= n1.get_cost(n1, n2);
                    mat[i][j]=c;
                    topo[i][j]=0;
                }
                System.out.print("\t"+mat[i][j]);
            }
            System.out.print("\n");
        }
        b.print_nw(topo, nm,nm);        
        Stack.add(0);   //Adding zero node for the tree
        int l=0,hop=0;
        l=Stack.remove(0);    //retreive the first element of the stack
        int counter=0;
        
        while(!b.isconnected(nod,nm,q))//!b.isconnected(hops,nm))
        {
            int k=b.smallest(l,mat,topo,nm);    //Getting the smallest from the current node
          //  System.out.println("smallest node ="+k+" hop="+hop+" current_node:"+l+" Stack size="+Stack.size());
           // System.out.println("Connectedness :" + b.isconnected(nod,nm,q));
            if((hop+1)<=4 && k>-1 )
            {
                //System.out.println("yes");
                topo[l][k]=1;                   //select the link in the topology
                topo[k][l]=1;
                cost+=mat[l][k];
                System.out.print(" ("+l+","+k+")");
                hops[k][l]=hop+1;
                hops[l][k]=hop+1; 
                nod[l].add(k);
                nod[k].add(l);
                q.get(l).degree+=1;
                q.get(k).degree+=1;
                Stack.add(0,l);                //add the node to top of the stack
                l=k;
                hop++;
               
            }
            else
            {
                //System.out.println("No");
                l=Stack.remove(0);
                hop--;
            }
            counter++;
        }
        System.out.println("Topology");
        b.print_nw(topo,nm,nm);
        System.out.println("Hops");
        b.print_nw(hops, nm,nm);
        for(i=0;i<nm;i++)
        {
            System.out.println("Degree of node :"+i+" :"+q.get(i).degree);
        }
        System.out.println("Cost : "+cost);
    }
    
    boolean isconnected( ArrayList<Integer> []nod,int n,ArrayList<Node> no)
    {
        boolean flag=true;
      try{
      ArrayList<Integer> q = new ArrayList();
      q.add(0);
      int visited[]=new int[n], sum=0;
      
      while(!q.isEmpty())
      {
          int curr=q.remove(0);
          for(int i=0;i<nod[curr].size();i++)
          {
              int k=nod[curr].get(i);
              if(visited[k]!=1)
              {
                    visited[k]=1;
                    sum+=1;
                    q.add(nod[curr].get(i));
              }
          }          
      }
      if (sum!=n)
          flag=false;
      for(int k=0;k<n;k++)
          if(no.get(k).degree<3)
          {
            flag=false;
            break;
          }
      
      }
      catch(Exception e){/*System.out.println("Here :"+e);*/}
      return flag;
  }
        
    int smallest(int i,int mat[][],int topo[][],int n)
    {
        int j,smallest=Integer.MAX_VALUE,idx=-1;
        int flag=0;
        try
        {
        for(j=0;j<n;j++)
        {
            if(smallest>mat[i][j] && topo[i][j]==0)
            {
                smallest=mat[i][j];
                idx=j;
            }
        }
        
        //System.out.println("idx:"+idx+" smallest:"+smallest);
        for(int k=0;k<n;k++)
        {
           // System.out.println("mat:"+mat[idx][k]+" smallest:"+smallest);
            if(mat[idx][k]>smallest && idx!=k)
            {
                flag=1;
            }
        }
      
        }catch(Exception e){/*System.out.println("Here!@"+e);*/}
        if(flag==0)
            idx=-1;
        return idx;
    }
}
