/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symbolic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Violets
 */

public class Phrase{

    private String value;
    private ArrayList<Variable> atoms=new ArrayList<>();
    private ArrayList<String> operators=new ArrayList<>();

    public Phrase(String value){
        ArrayList<String> allOperators=new ArrayList<>();
        allOperators.addAll(Arrays.asList(new String[]{"+","-","*"}));
        this.value=value;
        
        for(int i=0;i<value.length();i++){
            if(allOperators.contains(value.charAt(i)+"")){
                operators.add(value.charAt(i)+"");
            }
        }
        atoms=Phrase.getAtoms(value);
    }

    @Override
    public String toString(){
        String ret="";
        for(int i=0;i<atoms.size();i++){
            ret+=atoms.get(i);
            if(i<atoms.size()-1)
                ret+=operators.get(i);
        }
        return ret;
    }

    public ArrayList<Variable> getAtoms(){
        return atoms;
    }

    public ArrayList<String> getOperators(){
        return operators;
    }

    public String getValue(){
        return value;
    }
    
    public Set<String> getAllUsedVariables(){
        Set<String> ret=new HashSet<>();
        for(Variable variable:atoms){
            if(variable instanceof NumericVariable)continue;
            ret.add(variable.getLable());
        }
        return ret;
    }
    

    public static ArrayList<Variable> getAtoms(String pr){
        ArrayList<Variable> ret=new ArrayList<>();
        String step1Process[]=pr.split("\\+|-|\\*");
        Arrays.stream(step1Process).forEach(e -> {
            if(Phrase.isNumericAtom(e))
                ret.add(new NumericVariable(e));
            else{
                ret.add(new Variable(e));
            }
        });
        return ret;
    }

    public static boolean isNumericAtom(String pr){
        return (pr.matches("[0-9]+|[0-9]++/+[0-9]+"));
    }
    
    public static void main(String[] args){
        Phrase p1=new Phrase("2x^3111111+5x^2+7y+1+234x*y");
        System.out.println(p1);
        System.out.println(p1.getAllUsedVariables());
    }
    
//    public static Phrase multiply(Phrase leftPfrase){
//        
//    }

}
