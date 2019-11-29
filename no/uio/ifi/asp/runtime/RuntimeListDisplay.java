package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListDisplay extends RuntimeValue {
    ArrayList<RuntimeValue> list;

    public RuntimeListDisplay(ArrayList<RuntimeValue> l) {
	     list = l;
    }

    @Override
    protected String typeName() {
	     return "list";
    }

   @Override
    public String toString() {
	     return list.toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
      return (list.size() > 0);
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
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeIntValue){
        long times = v.getIntValue("* operand", where);
        ArrayList<RuntimeValue> n = new ArrayList<RuntimeValue>();

        for (int i = 0; i < times; i++) {
          n.addAll(list);
        }
        return new RuntimeListDisplay(n);
      }
      runtimeError("Type error for *.", where);
      return null;
    }

    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeIntValue){
        Integer i = (int) (long) v.getIntValue("[...]", where);
        
        if(i < list.size()-1){
          if(list.get(i) instanceof RuntimeIntValue){
            return new RuntimeIntValue(list.get(i).getIntValue("[...]", where));
          }else if(list.get(i) instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(list.get(i).getFloatValue("[...]", where));
          }else if(list.get(i) instanceof RuntimeStringValue){
            return new RuntimeStringValue(list.get(i).getStringValue("[...]", where));
          }/*else if(list.get(i) instanceof RuntimeListValue){
            return new RuntimeListValue(list.get(i));
          }*/else{
            runtimeError("Type error for [...]", where);
          }
        }else{
          runtimeError("List index " + i + " out of range!", where);
        }


      }else{
        runtimeError("A list index must be an integer", where);
      }

      return null;
    }
}
