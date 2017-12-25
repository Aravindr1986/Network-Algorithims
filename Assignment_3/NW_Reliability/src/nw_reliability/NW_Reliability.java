
package nw_reliability;

import java.util.ArrayList;
import java.util.Random;

public class NW_Reliability
{
  static double reliability=0.0;
  double p=0.05;
  String NW_topo;
  Links links_desc[];
  ArrayList<Integer> []nodes=new ArrayList[5] ;                     //adjacency list for nodes
  static ArrayList<Integer> rand_nos;
  
  public NW_Reliability()
  {
      links_desc=new Links[10]; //Generating the links
      for(int i=0;i<5;i++)
          nodes[i]=new ArrayList();
  }
  private boolean isconnected() //bfs
  {
      ArrayList<Integer> q = new ArrayList();
      q.add(0);
      int visited[]=new int[5], sum=0;
      boolean flag=true;
      while(!q.isEmpty())
      {
          int curr=q.remove(0);
          for(int i=0;i<nodes[curr].size();i++)
          {
              int k=nodes[curr].get(i);
              if(visited[k]!=1)
              {
                    visited[k]=1;
                    sum+=1;
                    q.add(nodes[curr].get(i));
              }
          }          
      }
      if (sum!=5)
          flag=false;
      return flag;
  }
  public double relib_clac(int flag)
  {
      int index=0;
      int d[]={2,0,2,1,2,7,7,2,5,6};
      double p_l,x,local_reli=1;
       while(index<10)
          {   
            if(flag==0)
            {
                p_l=Math.pow(p, Math.ceil((double)d[index]/3.0));
            }
            else
                p_l=p;
            
            x=1-p_l;
            if(links_desc[index].state==1)
                  local_reli*=p_l;
            else
                 local_reli*=x;
            index++;
          } 
       return local_reli;
      
  }
  public void calc_reliability()
  {
      if(isconnected())
      { 
          reliability+=relib_clac(0);
      }
      if(NW_topo.equals("1111111111"))
      {
          System.out.println("P="+p+" Network Reliability of "+NW_topo+" :"+reliability);
          reliability=0.0;                   //Resetting the reliability value to zero for next p value calculation
      }
  }
  public void calc_reliability(int flg)
  {
        if(rand_nos.contains(flg))
        {
              if(!isconnected())
              { 
                  reliability+=relib_clac(1);
              }
        }
        else
        {   
            if(isconnected())
              {    
                  reliability+=relib_clac(1);
              }
        }
        if(NW_topo.equals("1111111111"))
        {
            System.out.println("P="+p+" Network Reliability of "+NW_topo+" :"+reliability);
            reliability=0.0;                   //Resetting the reliability value to zero for next p value calculation
        }
  }
  public void random_gen(int k) //generating random numbers
  {
       rand_nos=new ArrayList ();
       Random r=new Random();
       for(int i=0;i<k;i++)
       {
           int next=r.nextInt(1024);
          // System.out.println(next);
           rand_nos.add(next);
       }
  }
  public void links_details(String topo)   
  {
     int n1=0,link_count=0;
     int n2=n1+1;
     NW_topo=topo;                                  //taking backup of the network topography
     while(link_count<10)
     {
       int st=0;                                      
       if(topo.charAt(link_count)=='1')     //generating the adjacency list
       {
           st=1;
           nodes[n1].add(n2);
           nodes[n2].add(n1);
       }
       
       links_desc[link_count]=new Links();
       links_desc[link_count].set_links_state(n1,n2,st);
       link_count++;
       n2+=1;
       if(n2==5)
       {
           n1+=1;
           n2=n1+1;
       }
     }
  }
  
  public static void main(String[] args) 
  {
      int topology=1000;
      
    
       try
        {
            
         for(double k=0.05;k<1.05;k+=0.05)
          {
           for(int i=0;i<1024;i++)
           {
               
                NW_Reliability n = new NW_Reliability();
                n.p=k;
                String str=Integer.toBinaryString(i);
                n.links_details((("0000000000")+str).substring(str.length()));
                n.calc_reliability();
            }
          }
         for(int k=0;k<21;k++)
         {
            for(int round=0;round<5;round++)
            {
                   System.out.println("K="+k+" Round ="+round);
                   NW_Reliability n = new NW_Reliability();
                   n.random_gen(k);
                   for(int i=0;i<1024;i++)
                    {
                         NW_Reliability n1 = new NW_Reliability();
                         n1.p=0.9;
                         String str=Integer.toBinaryString(i);
                         n1.links_details((("0000000000")+str).substring(str.length()));
                         n1.calc_reliability(i);
                    }
            }
         }
        }
        catch(Exception e){System.out.println(e);}
      
  } 
}
