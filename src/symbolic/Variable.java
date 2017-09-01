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
public class Variable{

    private String lable;
    private NumericVariable power;
    private NumericVariable coefficient;

    public Variable(String lable,NumericVariable power,NumericVariable coefficient){
        this.lable=lable;
        this.power=power;
        this.coefficient=coefficient;
    }

    public Variable(String value){ //predict variable name
        String stringCoefficent="";
        char buffer=value.charAt(0);
        while(buffer<='9'&&buffer>='0'){
            stringCoefficent+=buffer;
            value=value.substring(1);
            buffer=value.charAt(0);
        }
        String[] tokens=value.split("\\^");
        if("".equals(stringCoefficent))
            this.coefficient=new NumericVariable("1");
        else
            this.coefficient=new NumericVariable(stringCoefficent);
        if("".equals(tokens[1]))
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

    @Override
    public String toString(){
        return coefficient+lable+Utils.toSuperScriptNumbers(power+"");
    }
    

    public Variable(){
    }
    
}
