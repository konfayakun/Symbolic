/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symbolic;

import java.util.Objects;

/**
 *
 * @author Violets
 */
public class Variable{

    private String lable="";
    private NumericVariable power=NumericVariable.ONE;
    private NumericVariable coefficient=NumericVariable.ONE;

    public Variable(String lable,NumericVariable power,NumericVariable coefficient){
        this.lable=lable;
        this.power=power;
        this.coefficient=coefficient;
    }

    public Variable(String value){ //predict variable name
        String stringCoefficent="";
        char buffer=value.charAt(0);
        try{
        while(buffer<='9'&&buffer>='0'){
            stringCoefficent+=buffer;
            value=value.substring(1);
            buffer=value.charAt(0);
        }
        }catch(StringIndexOutOfBoundsException exception){
            
        }
        String[] tokens=value.split("\\^");
        if("".equals(stringCoefficent))
            this.coefficient=new NumericVariable("1");
        else
            this.coefficient=new NumericVariable(stringCoefficent);
        if(tokens.length==1||"".equals(tokens[1]))
            this.power=new NumericVariable("1");
        else
            this.power=new NumericVariable(tokens[1]);
        this.lable=tokens[0];
    }

    public void setName(String lable){
        this.lable=lable;
    }

    public void setPower(NumericVariable power){
        this.power=power;
    }

    public void setCoefficient(NumericVariable coefficient){
        this.coefficient=coefficient;
    }

    public NumericVariable getCoefficient(){
        return coefficient;
    }

    public String getLable(){
        return lable;
    }

    public NumericVariable getPower(){
        return power;
    }
    public Variable getBase(){
        return new Variable(this.lable,this.power,NumericVariable.ONE);
    }
//    public boolean isIsotonic(Variable variable){
//        return (this.lable.equals(variable.lable)&& this.power.equals(variable.power));
//    }
    
    

    public void setAttrib(String lable,NumericVariable power,NumericVariable coefficient){
        this.lable=lable;
        this.power=power;
        this.coefficient=coefficient;
    }

    public Variable(String completeVar,String variableName){
        String[] parts=completeVar.split(variableName);
        if(parts.length!=2)
            return;
        coefficient=new NumericVariable(parts[0]);
        power=new NumericVariable(parts[1].replace("^",""));
    }
    
    public NumericVariable evaluate(NumericVariable initialValue){
        try{
            return new NumericVariable(this+"");
        } catch(Exception e){
        }
        NumericVariable ret=NumericVariable.ONE;
        for(int i=0;i<power.integerValue();i++){
            ret=ret.multiply(initialValue);
        }
        ret=ret.multiply(coefficient);
        return ret;
    }
    
//    public Variable multiply(){
//        
//    }

    @Override
    public String toString(){
        if(power.equals(NumericVariable.ZERO) || lable.equals("")) return lable;
        String powerString=(NumericVariable.ONE.equals(power))?"":Utils.toSuperScriptNumbers(power+"");
        String coefficentString=(NumericVariable.ONE.equals(coefficient))?"":coefficient+"";
        return coefficentString+lable+powerString;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Variable))return false;
        Variable rhs=(Variable)obj;
        return (this.coefficient.equals(rhs.coefficient) && this.lable.equals(rhs.lable) && this.power.equals(rhs.power));
    }

    @Override
    public int hashCode(){
        int hash=3;
        hash=97*hash+Objects.hashCode(this.lable);
        hash=97*hash+Objects.hashCode(this.power);
        hash=5*hash+Objects.hashCode(this.coefficient);
        return hash;
    }
    public Variable(){
    }
    
}
