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
public class Multiplication{

    private ArrayList<Variable> variables;

    public Multiplication(String stringValue){
        this();
        for(String token:stringValue.split("\\*"))
            variables.add(new Variable(token));
    }

    public Multiplication(Variable... variables){
        this.variables=new ArrayList<>(Arrays.asList(variables));
    }

    public Multiplication(ArrayList<Variable> variables){
        this.variables=(ArrayList<Variable>) variables.clone();
    }

    public ArrayList<Variable> getVariables(){
        return variables;
    }
    

    public Multiplication(){
        variables=new ArrayList<>();
    }
    public Multiplication simplify(){
        return simplify(this);
    }
    public Multiplication multiply(Multiplication multiplier){
        return multiply(this,multiplier);
    }
    public NumericVariable evaluate(HashMap<String,NumericVariable> initialValues){
        NumericVariable ret=NumericVariable.ONE;
        for(Variable var:this.variables){
            ret=(var instanceof NumericVariable)?ret.multiply((NumericVariable)var):ret.multiply(var.evaluate(initialValues.get(var.getLable())));
        }
        return ret;
    }

    @Override
    public String toString(){
        String ret="";
        for(Variable variable:variables)
            if(variable instanceof NumericVariable || variable == null){
                ret+=(((NumericVariable)variable).equals(NumericVariable.ONE))?"":variable;
            }
            else
                ret+=variable+".";
        return(variables.size()==1 && variables.get(0) instanceof NumericVariable)?ret:ret.substring(0,ret.length()-1);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Multiplication)) return false;
        Multiplication rhs=((Multiplication)obj).simplify();
        Multiplication lhs=this.simplify();
        if(rhs.variables.size()!=this.variables.size()) return false;
        if(rhs.variables.size()==1) return true;
        for(int i=1;i<rhs.variables.size();i++){
            if(!rhs.variables.get(i).equals(lhs.variables.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash=7;
        Multiplication simplified=this;
        if(simplified.variables.size()==1) return 0;
        for(int i=1;i<simplified.variables.size();i++){
            hash+=simplified.variables.get(i).hashCode();
        }
        return hash;
    }

    public static Multiplication multiply(Multiplication multiplicand,Multiplication multiplier){
        ArrayList<Variable> newVariables=new ArrayList<>();
        newVariables.addAll(multiplicand.variables);
        newVariables.addAll(multiplier.variables);
        return new Multiplication(newVariables);
    }
    public static Multiplication simplify(Multiplication toSimplify){
        Set<String> basis=new HashSet<>();
        HashMap<String,NumericVariable> powers=new HashMap<>();
        NumericVariable coefficent=NumericVariable.ONE;
        for(Variable variable:toSimplify.variables) {
            if(variable.getCoefficient()==null){
                coefficent=coefficent.multiply((NumericVariable)variable);
                continue;
            }
            coefficent=coefficent.multiply(variable.getCoefficient());
            String base=variable.getLable();
            if(basis.add(base))
                powers.put(base,variable.getPower());
            else
                powers.compute(base,(key,value) -> value.sum(variable.getPower()));
        }
        ArrayList<Variable> newVariabels=new ArrayList<>();
        newVariabels.add(coefficent);
        basis.stream().forEach((base) -> {
            newVariabels.add(new Variable(base,powers.get(base),NumericVariable.ONE));
        });
        Multiplication ret=new Multiplication(newVariabels);
        return ret;
    }

    public static void main(String[] args){
        Multiplication mul=new Multiplication("2X^2*6X*5Y^3*X^3");
        Multiplication mul1=new Multiplication("3x^2*y").simplify();
        Multiplication mul2=new Multiplication("y*20x^2").simplify();
        
        System.out.println(multiply(mul1,mul2).simplify());
        System.out.println(mul1.simplify());
        System.out.println(mul2.simplify());
        
        System.out.println(mul1.variables);
        System.out.println(mul2.variables);
        
        
        System.out.println(Multiplication.simplify(mul));
        System.out.println(mul);
    }
}