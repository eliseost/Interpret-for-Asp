package no.uio.ifi.asp.runtime;

import java.util.Hashtable;
import java.util.*;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictDisplay extends RuntimeValue {
    Hashtable<String, RuntimeValue> dict;

    public RuntimeDictDisplay(Hashtable<String, RuntimeValue> d) {
	     dict = d;
    }

    @Override
    protected String typeName() {
	     return "dictionary";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
      return (dict.size() > 0);
    }


    @Override
    public String toString() {
      String p = "{ ";
      Enumeration enu = dict.keys();
      while(enu.hasMoreElements()){
        Object next = enu.nextElement();
        p += "'" + next.toString() + "' : ";
        RuntimeValue nextKey = dict.get(next);
        if(nextKey instanceof RuntimeStringValue){
          p += "'" + nextKey.toString() + "', ";
        }else{
          p += nextKey.toString() + ", ";
        }

      }
      String np = p.substring(0, p.length() -2);
      np += "}";
	    return np;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeNoneValue){
        return new RuntimeBoolValue(false);
      }
      runtimeError("Type error for ==.", where);
      return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeNoneValue){
        return new RuntimeBoolValue(true);
      }
      runtimeError("Type error for !=.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeStringValue){
        String s = v.getStringValue("[...]", where);
        long value = 1;
        String ns = "'" + s + "'";
        if(dict.get(s) == null){
          runtimeError("Dictionary key " + ns + " undefined!", where);
        }else{
          return new RuntimeIntValue(dict.get(s).getIntValue("[...]", where));
        }
      }
      return null;
    }
}
