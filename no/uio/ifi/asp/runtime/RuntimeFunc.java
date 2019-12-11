package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.*;

public class RuntimeFunc extends RuntimeValue{
  AspFuncDef def;
  RuntimeScope defScope;
  String string;

  public RuntimeFunc(AspFuncDef d, RuntimeScope s){
    def = d;
    defScope = s;
  }

  public RuntimeFunc(String s){
    string = s;
  }

  @Override
  protected String typeName() {
     return "func";
  }

  @Override
  public String showInfo(){
    if(string == null){
      return def.getList().get(0).getName();
    }
    return string;
  }

  @Override
  public String toString(){
    return def.getList().get(0).toString();
  }

  @Override
  public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> akparam, AspSyntax where){
    //Sjekk antall parametre
    //Opprette skop
    //Tilordne aktuelle parametre til formelle, assignment, diverse kall på assign
    //Hvor ska runFunction være?

      if(akparam.size() != def.getList().size() -1){
        //Må ta minus 1 fordi lista i def inneholder navnet på funksjonen i tillegg til parametrene.
        runtimeError("Number of parametres not equal", where);
      }
      RuntimeScope newScope = new RuntimeScope(defScope);

      for (int i = 1; i < def.getList().size(); i++) {
        newScope.assign(def.getList().get(i).getName(), akparam.get(i-1));
      }
      //runFunction(newScope);
      try{
        def.getBody().eval(newScope);
      }catch (RuntimeReturnValue rrv){
        return rrv.value;
      }

      return new RuntimeNoneValue();
  }
  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {

return null;  // Required by the compiler!
  }
}
