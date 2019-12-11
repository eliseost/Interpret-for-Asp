package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
      //len
      assign("len", new RuntimeFunc("len") {
        @Override
        public RuntimeValue evalFuncCall(
                  ArrayList<RuntimeValue> actualParams,
                  AspSyntax where) {
              checkNumParams(actualParams, 1, "len", where);
              return actualParams.get(0).evalLen(where);
            }});

      //print
      assign("print", new RuntimeFunc("print") {
        @Override
        public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
          for (int i = 0; i < actualParams.size(); i++) {
            if(i > 0){
              System.out.print(" ");
            }
            System.out.print(actualParams.get(i).toString());
          }
          System.out.println();
          return new RuntimeNoneValue();
        }});

      //str
      assign("str", new RuntimeFunc("str") {
        @Override
        public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
          checkNumParams(actualParams, 1, "str", where);
          RuntimeValue string = new RuntimeStringValue(actualParams.get(0).toString());
          return string;
        }
      });

      //range
      assign("range", new RuntimeFunc("range") {
        @Override
        public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
          checkNumParams(actualParams, 2, "range", where);
          ArrayList<RuntimeValue> list = new ArrayList<RuntimeValue>();

          if(actualParams.get(0) instanceof RuntimeIntValue && actualParams.get(1) instanceof RuntimeIntValue){
            Integer a = (int) (long) actualParams.get(0).getIntValue("[...]", where);
            Integer b = (int) (long) actualParams.get(1).getIntValue("[...]", where);

            for (int i = a; i < b; i++) {
              Long j = (long) i;
              list.add(new RuntimeIntValue(j));
            }
            return new RuntimeListDisplay(list);
          }
          else{
            RuntimeValue.runtimeError("Range indeces not integers", where);
          }
        return new RuntimeNoneValue();
      }
      });

      //int
      assign("int", new RuntimeFunc("int") {
        @Override
        public RuntimeValue evalFuncCall(
                  ArrayList<RuntimeValue> actualParams,
                  AspSyntax where) {
              checkNumParams(actualParams, 1, "int", where);
              return new RuntimeIntValue(actualParams.get(0).getIntValue("int func", where));
            }});

      //float
      assign("float", new RuntimeFunc("float") {
        @Override
        public RuntimeValue evalFuncCall(
                  ArrayList<RuntimeValue> actualParams,
                  AspSyntax where) {
              checkNumParams(actualParams, 1, "float", where);
              return new RuntimeFloatValue(actualParams.get(0).getFloatValue("float func", where));
            }});

      //input
      assign("input", new RuntimeFunc("input") {
        @Override
        public RuntimeValue evalFuncCall(
                  ArrayList<RuntimeValue> actualParams,
                  AspSyntax where) {
              checkNumParams(actualParams, 1, "input", where);

              if(actualParams.get(0) instanceof RuntimeStringValue){
                System.out.println(actualParams.get(0).toString());
                return new RuntimeStringValue(keyboard.nextLine());
              }else{
                RuntimeValue.runtimeError("Input value must be string", where);
              }
              return new RuntimeNoneValue();
            }});

          }


    private void checkNumParams(ArrayList<RuntimeValue> actArgs,
				int nCorrect, String id, AspSyntax where) {
	if (actArgs.size() != nCorrect)
	    RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
