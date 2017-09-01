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
class NumericVariable extends Variable{

    private String value;
    private int numer;
    private int denom;
    private boolean sahih;

    public NumericVariable(String value){
        this.value=value;
        if(value.contains("/")){
            String squ[]=value.split("/");
            numer=Integer.parseInt(squ[0]);
            denom=Integer.parseInt(squ[1]);
            sahih=false;
        } else{
            numer=Integer.parseInt(value);
            denom=1;
            sahih=true;
        }
    }

    public NumericVariable(int num,int den) throws Exception{
        if(den==0)
            throw new Exception("Denominator can't be zero!");
        numer=num;
        denom=den;
        if(den==1){
            value=String.valueOf(numer);
            sahih=true;
        } else{
            value=numer+"/"+denom;
            sahih=false;
        }
    }

    //geters:
    public String getValue(){
        return value;
    }

    public int getNumer(){
        return numer;
    }

    public int getDenom(){
        return denom;
    }

    ///------------------------------Useful functions:

    public NumericVariable multi(NumericVariable a){
        try{
            return new NumericVariable(numer*a.getNumer(),denom*a.getDenom());
        } catch(Exception ex){
        }
        return null;
    }

    public NumericVariable sum(NumericVariable b){
        int inde=Utils.gcc(denom,b.getDenom());
        try{
            return new NumericVariable(inde/denom*numer+inde/b.getDenom()*b.getNumer(),inde);
        } catch(Exception ex){
        }
        return null;
    }

    public NumericVariable simplify(){
        int factor=Utils.gcd(numer,denom);
        try{
            return new NumericVariable(numer/factor,denom/factor);
        } catch(Exception ex){
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
