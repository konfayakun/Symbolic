/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symbolic;

/**
 *
 * @author Violets
 */
//public class NumericVariable {
//
//}
public class NumericVariable extends Variable{

    private String value;
    private int numerator;
    private int denominator;
    private boolean isInt;

    public NumericVariable(String value){
        this.value=value;
        if(value.contains("/")){
            String squ[]=value.split("/");
            numerator=Integer.parseInt(squ[0]);
            denominator=Integer.parseInt(squ[1]);
            isInt=false;
        } else{
            numerator=Integer.parseInt(value);
            denominator=1;
            isInt=true;
        }
    }

    public NumericVariable(int numerator,int denominator) throws Exception{
        if(denominator==0)
            throw new Exception("Denominator can't be zero!");
        this.numerator=numerator;
        this.denominator=denominator;
        if(denominator==1){
            value=String.valueOf(numerator);
            isInt=true;
        } else{
            value=numerator+"/"+denominator;
            isInt=false;
        }
    }

    public int getDenominator(){
        return denominator;
    }

    public int getNumerator(){
        return numerator;
    }

    public String getValue(){
        return value;
    }

 

    ///------------------------------Useful functions:

    public NumericVariable multiply(NumericVariable a){
        try{
            return new NumericVariable(numerator*a.getNumerator(),denominator*a.getDenominator());
        } catch(Exception exception){
        }
        return null;
    }

    public NumericVariable sum(NumericVariable b){
        int commonDenominator=Utils.lcm(denominator,b.getDenominator());
        try{
            return new NumericVariable(commonDenominator/denominator*numerator+commonDenominator/b.getDenominator()*b.getNumerator(),commonDenominator);
        } catch(Exception exception){
        }
        return null;
    }

    public NumericVariable simplify(){
        int factor=Utils.gcd(numerator,denominator);
        try{
            return new NumericVariable(numerator/factor,denominator/factor);
        } catch(Exception exception){
        }
        return null;
    }

    @Override
    public String toString(){
        String parts[]=value.split("/");
        if(parts.length==1)return value;
        String ret=value.replace("/","⁄");
        return toSuperScriptNumbers(parts[0])+"⁄"+toSubScriptNumbers(parts[1]);
    }

    String toSuperScriptNumbers(String s){
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
    String toSubScriptNumbers(String s){
        String ret="";
        for(int i=0;i<s.length();i++){
            char buffer=s.charAt(i);
            ret+=(char)('₀'+(buffer-'0'));
        }
        return ret;
    }

    public static void main(String[] args){
//        System.out.println("aa*+b".split("\\+|-|\\*|=")[2]);
        System.out.println(Phrase.isAtom("5/2"));
        NumericVariable s=new NumericVariable("10/2");
        NumericVariable p=new NumericVariable("3/5");
        System.out.println(s.toSuperScriptNumbers("0123456789"));
        
        System.out.println(s.toSubScriptNumbers("0123456789"));
        System.out.println(s.sum(p));
        System.out.println(s.sum(p).simplify());
        System.out.println(s);
        System.out.println(s.simplify());
    }
}
