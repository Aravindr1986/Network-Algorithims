/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nw_reliability;

/**
 *
 * @author Aravind
 */
public class Links
{
    int n1;
    int n2;
    int state;
    double reli_fact;
    void set_links_state(int s,int d,int stat)
    {
        n1=s;
        n2=d;
        state=stat;
    }
    
    void disp_link()
    {
        System.out.println("Node 1 :"+ n1+" Node 2 : "+n2+" state :"+state);
    }
}
