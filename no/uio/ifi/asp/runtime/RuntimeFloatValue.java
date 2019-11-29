package no.uio.ifi.asp.runtime;

import java.lang.Math;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double floatValue;

    public RuntimeFloatValue(double v) {
	     floatValue = v;
    }

    @Override
    protected String typeName() {
	     return "float";
    }

  @Override
    public String toString() {
      String s = floatValue +"";
	     return s;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
      return (floatValue > 0.0);
    }

    /*@Override
    public double getIntValue(String what, AspSyntax where){
      return intValue;
    }*/

    @Override
    public double getFloatValue(String what, AspSyntax where){
      return floatValue;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeFloatValue(floatValue + v.getFloatValue("+ operand", where));
      }
      runtimeError("Type error for +.", where);
      return null;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeFloatValue(floatValue - v.getFloatValue("- operand", where));
      }
      runtimeError("Type error for -.", where);
      return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeFloatValue(floatValue * v.getFloatValue("* operand", where));
      }
      runtimeError("Type error for *.", where);
      return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeFloatValue(floatValue / v.getFloatValue("/ operand", where));
      }
      runtimeError("Type error for /.", where);
      return null;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeFloatValue(Math.floor(floatValue / v.getFloatValue("// operand", where)));
      }
      runtimeError("Type error for //.", where);
      return null;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where){
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeFloatValue(floatValue - v.getFloatValue("% operand", where)
        * Math.floor(floatValue / v.getFloatValue("% operand", where)));
      }
      runtimeError("Type error for %.", where);
      return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeBoolValue(floatValue == v.getFloatValue("== operand", where));
      }else if(v instanceof RuntimeNoneValue){
        return new RuntimeBoolValue(false);
      }
      runtimeError("Type error for ==.", where);
      return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeBoolValue(floatValue != v.getFloatValue("!= operand", where));
      }else if(v instanceof RuntimeNoneValue){
        return new RuntimeBoolValue(true);
      }
      runtimeError("Type error for !=.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeBoolValue(floatValue < v.getFloatValue("< operand", where));
      }
      runtimeError("Type error for <.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeBoolValue(floatValue <= v.getFloatValue("<= operand", where));
      }
      runtimeError("Type error for <=.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeBoolValue(floatValue > v.getFloatValue("> operand", where));
      }
      runtimeError("Type error for >.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
      if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue){
        return new RuntimeBoolValue(floatValue >= v.getFloatValue(">= operand", where));
      }
      runtimeError("Type error for >=.", where);
      return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where){
      return new RuntimeFloatValue(- floatValue);
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where){
      return new RuntimeFloatValue(+ floatValue);
    }

}
