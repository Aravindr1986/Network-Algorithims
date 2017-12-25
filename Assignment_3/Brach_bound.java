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
    void Check_connection(int topo[][])
    {
        
    }
    public static void main(String[] args) 
    {
        ArrayList<Node> q = new ArrayList();
        Random r= new Random(100);
        System.out.println("Enter the number of nodes:");
        Scanner in = new Scanner(System.in);
        int nm = in.nextInt();
        int sum=0;
        int mat[][]=new int [nm][];             //cost matrix         
        int topo[][]=new int[nm][nm];           //topology matrix
        int hops[][]=new int[nm][nm];           //hops matrix
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
        while(counter<15)
        {
            int k=b.smallest(l,mat,topo,nm);    //Getting the smallest from the current node
            System.out.println("k ="+k+" hop="+hop+" l:"+l+" S="+Stack.size());
            if((hop+1)<=2 && k>-1)
            {
                topo[l][k]=1;                   //select the link in the topology
                topo[k][l]=1;                   //select the link in the topology
                q.get(l).degree+=1;
                q.get(k).degree+=1;
                
                Stack.add(0,l);                //add the node to top of the stack
                l=k;
                hop++;
            }
            else
            {
                l=Stack.remove(0);
                hop--;
            }
            counter++;
        }
        b.print_nw(topo,nm,nm);
    }
    int smallest(int i,int mat[][],int topo[][],int n)
    {
        int j,smallest=Integer.MAX_VALUE,idx=-1;
        for(j=0;j<n;j++)
        {
            if(smallest>mat[i][j] && topo[i][j]==0)
            {
                smallest=mat[i][j];
                idx=j;
            }
        }
        return idx;
    }
}
