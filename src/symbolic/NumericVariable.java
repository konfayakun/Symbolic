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
        return Utils.toSuperScriptNumbers(parts[0])+"⁄"+Utils.toSubScriptNumbers(parts[1]);
    }
}
