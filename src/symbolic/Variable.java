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

class Variable{

    private String name;
    private NumericVariable pow;
    private NumericVariable zarib;

    public Variable(String name,NumericVariable pow,NumericVariable zarib){
        this.name=name;
        this.pow=pow;
        this.zarib=zarib;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setPower(NumericVariable pow){
        this.pow=pow;
    }

    public void setZarib(NumericVariable zarib){
        this.zarib=zarib;
    }

    public void setAttrib(String name,NumericVariable pow,NumericVariable zarib){
        this.name=name;
        this.pow=pow;
        this.zarib=zarib;
    }

    public Variable(String completeVar,String variableName){
        String[] parts=completeVar.split(variableName);
        if(parts.length!=2)
            return;
        zarib=new NumericVariable(parts[0]);
        pow=new NumericVariable(parts[1].replace("^",""));
    }

    public Variable(){
    }

}

