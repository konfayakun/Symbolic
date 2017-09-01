/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symbolic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Violets
 */

public class Phrase{

    String value;
    ArrayList<Variable> atoms=new ArrayList<>();
    ArrayList<String> operators=new ArrayList<>();

    public Phrase(String value){
        operators.addAll(Arrays.asList(new String[]{"+","-","*","/"}));
        this.value=value;
        String buffer="";

        for(int i=0;i<value.length();i++){

        }
    }

    public static ArrayList<Variable> atoms(String pr){
        ArrayList<Variable> ret=new ArrayList<>();
        String step1Process[]=pr.split("\\+|-|\\*");
        Arrays.stream(step1Process).forEach(e -> {
            if(isAtom(e))
                ret.add(new NumericVariable(e));
            else{

            }
        });
        return ret;
    }

    public static boolean isAtom(String pr){
        return (pr.matches("[0-9]+|[0-9]++/+[0-9]+"));
    }

}
