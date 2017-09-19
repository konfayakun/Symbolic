/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symbolic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Violets
 */
public class Summation{

    private ArrayList<Multiplication> multiplications;

    public Summation(String stringValue){
        this();
        for(String token:stringValue.split("\\+"))
            multiplications.add(new Multiplication(token).simplify());
    }

    public Summation(Multiplication... multiplication){
        this.multiplications=new ArrayList<>(Arrays.asList(multiplication));
    }

    public Summation(ArrayList<Multiplication> variables){
        this.multiplications=(ArrayList<Multiplication>) variables.clone();
    }

    public Summation(){
        multiplications=new ArrayList<>();
    }
    public Summation simplify(){
        return Summation.simplify(this);
    }
    public Summation sum(Summation sum){
        return sum(this,sum);
    }
    public Summation multiply(Summation multiplier){
        return multiply(this,multiplier);
    }
    public NumericVariable evaluate(HashMap<String,NumericVariable> initialValues){
        NumericVariable ret=NumericVariable.ZERO;
        for(Multiplication mul:this.multiplications){
            ret=ret.sum(mul.evaluate(initialValues));
        }
        return ret;
    }

    @Override
    public String toString(){
        String ret="";
        for(Multiplication variable:multiplications)
            if("".equals(ret))
                ret+=variable;
            else
                ret+="+"+variable;
        return ret;
    }

    public static Summation simplify(Summation toSimplify){
        Set<Multiplication> basis=new HashSet<>();
        HashMap<Multiplication,NumericVariable> coefficents=new HashMap<>();
        toSimplify.multiplications.stream().forEach((multiplication) -> {
            Multiplication base=multiplication.simplify();
            NumericVariable multiplicationCoefficent=(NumericVariable)(base.getVariables().get(0));
            if(basis.add(base))
                coefficents.put(base,multiplicationCoefficent);
            else
                coefficents.compute(base,(key,value) -> value.sum(multiplicationCoefficent));
            
        });
        ArrayList<Multiplication> newMultiplications=new ArrayList<>();
        basis.stream().forEach((base) -> {
            ArrayList<Variable> buffer=new ArrayList<>();
            buffer.add(coefficents.get(base));
            int len=base.getVariables().size();
            buffer.addAll(base.getVariables().subList(1,len));
            newMultiplications.add(new Multiplication(buffer));
        });
        return new Summation(newMultiplications);
    }
    public static Summation sum(Summation summation1,Summation summation2){
        ArrayList<Multiplication> newMultiplications=new ArrayList<>();
        newMultiplications.addAll(summation1.multiplications);
        newMultiplications.addAll(summation2.multiplications);
        return new Summation(newMultiplications);
    }
    public static Summation multiply(Summation multiplicand,Summation multiplier){
        ArrayList<Multiplication> newMultiplications=new ArrayList<>();
        multiplicand.multiplications.stream().forEach((mul1) -> {
            multiplier.multiplications.stream().forEach((mul2) -> {
                newMultiplications.add(mul1.multiply(mul2));
            });
        });
        return new Summation(newMultiplications);
    }

    public static void main(String[] args){
        Summation sum=new Summation("2x^2*y+7y^4+12m^3+y*35x^2+5x*2y");
        Summation sum1=new Summation("2x*y+2y");
        Summation sum2=new Summation("12y*x+7y+34");
        System.out.println(sum);
        System.out.println(Summation.simplify(sum));
        System.out.println(sum1);
        System.out.println(sum2);
        System.out.println(sum(sum1,sum2));
        System.out.println(sum(sum1,sum2).simplify());
        System.out.println("==============");
        Summation mul1=new Summation("x+y");
        Summation mul2=new Summation("z+t");
        System.out.println(mul1+"  "+mul2);
        System.out.println(multiply(mul1,mul2));
        System.out.println(multiply(mul1,mul2).simplify().multiplications);
        System.out.println("====================");
        Summation val=new Summation("2x+3y").multiply(new Summation("x^2+y^2"));
        Summation val2=new Summation("x+t");
        HashMap<String,NumericVariable> initialValues=new HashMap<>();
        initialValues.put("x",new NumericVariable("1/2"));
        initialValues.put("y",new NumericVariable("3"));
        
        System.out.println(val);
        System.out.println(val.evaluate(initialValues).simplify());
        
    }
}
