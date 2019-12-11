package no.uio.ifi.asp.runtime;

import java.lang.Math;
import java.lang.StringBuilder;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String stringLit;

    public RuntimeStringValue(String s) {
	     stringLit = s;
    }

    @Override
    protected String typeName() {
	     return "string";
    }

    @Override
    public String showInfo(){
      if(stringLit.indexOf('\'') >= 0){
        return '"' + stringLit + '"';
      }else{
        return "'" + stringLit + "'";
      }
    }

  @Override
    public String toString() {
	     return stringLit;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
      return (stringLit.length() > 0);
    }

    @Override
    public String getStringValue(String what, AspSyntax where){
      return stringLit;
    }

    @Override
    public long getIntValue(String what, AspSyntax where){
      try{
        return Long.parseLong(stringLit);
      }catch (java.lang.NumberFormatException e){
        runtimeError("Cannot convert string to long", where);
      }
      return 0;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where){
      try{
        return Double.parseDouble(stringLit);
      }catch (java.lang.NumberFormatException e){
        runtimeError("Cannot convert string to double", where);
      }
      return 0.0;
    }



    @Override
    public RuntimeValue evalLen(AspSyntax where){
      return new RuntimeIntValue(new Long(stringLit.length()));
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeStringValue){
        return new RuntimeStringValue(stringLit + v.getStringValue("+ operand", where));
      }
      runtimeError("Type error for +.", where);
      return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeIntValue){
        StringBuilder r =  new StringBuilder();
        long times = v.getIntValue("* operand", where);
        for (int i = 0; i < times; i++) {
          r.append(stringLit);
        }
        String n = r.toString();
        return new RuntimeStringValue(n);
      }
      runtimeError("Type error for *.", where);
      return null;
    }


    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeStringValue){
        RuntimeValue n = new RuntimeBoolValue(stringLit == v.getStringValue("== operand", where));
        return new RuntimeBoolValue(stringLit.equals(v.getStringValue("== operand", where)));
      }else if(v instanceof RuntimeNoneValue){
        return new RuntimeBoolValue(false);
      }
      runtimeError("Type error for ==.", where);
      return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeStringValue){
        return new RuntimeBoolValue(stringLit != v.getStringValue("!= operand", where));
      }else if(v instanceof RuntimeNoneValue){
        return new RuntimeBoolValue(true);
      }
      runtimeError("Type error for !=.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeStringValue){
        return new RuntimeBoolValue(stringLit.length() < v.getStringValue("< operand", where).length());
      }
      runtimeError("Type error for <.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeStringValue){
        return new RuntimeBoolValue(stringLit.length() <= v.getStringValue("<= operand", where).length());
      }
      runtimeError("Type error for <=.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeStringValue){
        return new RuntimeBoolValue(stringLit.length() > v.getStringValue("> operand", where).length());
      }
      runtimeError("Type error for >.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeStringValue){
        return new RuntimeBoolValue(stringLit.length() >= v.getStringValue(">= operand", where).length());
      }
        runtimeError("Type error for >=.", where);
      return null; // Required by the compiler
    }



}
