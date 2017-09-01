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
        ArrayList<Integer> primes =new ArrayList<>();
        Queue<Integer> mainQueue=new LinkedList<>();
        Queue<Integer> bufferQueue=new LinkedList<>();
        int maxElement;
        for(int i=2;i<n;i++){
            mainQueue.add(i);
            
        }
        maxElement=n-1;
        if(mainQueue.size()==0) return new ArrayList<>();
        int cpval=mainQueue.poll();
        
        while(cpval*cpval<=maxElement){
            primes.add(cpval);
            while(mainQueue.size()>0){
                int buffer=mainQueue.poll();
                if(buffer%cpval!=0){
                    bufferQueue.add(buffer);
                    maxElement=buffer;
                }
            }
            mainQueue=bufferQueue;
//            System.out.println(mq);
            bufferQueue=new LinkedList<>();
            cpval=mainQueue.poll();
        }
        primes.add(cpval);
        while(mainQueue.size()>0)
            primes.add(mainQueue.poll());
        return primes;
    } 
    public static int lcm(int a,int b){
        int min,max;
        if(a>b){
            min=b;max=a;
        }else{
            min=a;max=b;
        }
        int buffer=a*b;
        int n=(int)Math.sqrt(min);
        ArrayList<Integer> primes=Prime_N(min/2);
        primes.add(min);
        buffer=primes.stream().filter((num) -> (min%num==0 && max%num==0)).map((num) -> num).reduce(buffer,(accumulator,_item) -> accumulator/_item);
        return buffer;
    }
    public static void main(String[] args){
        System.out.println(lcm(100,27));
        System.out.println(gcd(200,12));
    }
    public static int gcd(int number1, int number2) {
        if(number2 == 0)
            return number1;
        return gcd(number2, number1%number2);
    }
    
    
    public static String toSuperScriptNumbers(String s){
        String ret="";
        for(int i=0;i<s.length();i++){
            char buffer=s.charAt(i);
            if(buffer<'1'||buffer>'3')
                ret=ret+(char) (8308+buffer-'4');
            else{
                if(buffer=='1')
                    ret+=(char) 185;
                if(buffer=='2')
                    ret+=(char) 178;
                if(buffer=='3')
                    ret+=(char) 179;
            }
        }
        return ret;
    }
    public static String toSubScriptNumbers(String s){
        String ret="";
        for(int i=0;i<s.length();i++){
            char buffer=s.charAt(i);
            ret+=(char)('₀'+(buffer-'0'));
        }
        return ret;
    }

}