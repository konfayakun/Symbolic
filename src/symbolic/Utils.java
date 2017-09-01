/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symbolic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Violets
 */
public class Utils {
    
    public static ArrayList<Integer> Prime_N(int n){
        ArrayList<Integer> avals =new ArrayList<>();
        Queue<Integer> mq=new LinkedList<>();
        Queue<Integer> bu=new LinkedList<>();
        int maxele;
        for(int i=2;i<n;i++){
            mq.add(i);
            
        }
        maxele=n-1;
        if(mq.size()==0) return new ArrayList<Integer>();
        int cpval=mq.poll();
        
        while(cpval*cpval<=maxele){
            avals.add(cpval);
            while(mq.size()>0){
                int buffer=mq.poll();
                if(buffer%cpval!=0){
                    bu.add(buffer);
                    maxele=buffer;
                }
            }
            mq=bu;
//            System.out.println(mq);
            bu=new LinkedList<>();
            cpval=mq.poll();
        }
        avals.add(cpval);
        while(mq.size()>0)
            avals.add(mq.poll());
        return avals;
    } 
    public static int gcc(int a,int b){
        int min,max;
        if(a>b){
            min=b;max=a;
        }else{
            min=a;max=b;
        }
        int buffer=a*b;
        int n=(int)Math.sqrt(min);
//        System.out.println(n);
        ArrayList<Integer> avl=Prime_N(min/2);
//        System.out.println(avl);
        avl.add(min);
        for(int num:avl){
            if(min%num==0 && max%num==0){
                buffer/=num;
            }
        }
        return buffer;
    }
    public static void main(String[] args){
        System.out.println(gcc(100,27));
        System.out.println(gcd(200,12));
    }
    public static int gcd(int number1, int number2) {
        if(number2 == 0)
            return number1;
        return gcd(number2, number1%number2);
    }
}