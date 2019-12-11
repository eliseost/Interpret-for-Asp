package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspAssignment extends AspSyntax{
  ArrayList<AspSubscription> subs = new ArrayList<>();
  AspName name;
  AspExpr expr;

  AspAssignment(int n){
    super(n);
  }

  public static AspAssignment parse(Scanner s){
    enterParser("assignment");

    AspAssignment as = new AspAssignment(s.curLineNum());
    as.name = AspName.parse(s);

    while (s.curToken().kind == leftBracketToken){
      as.subs.add(AspSubscription.parse(s));
    }

    skip(s, equalToken);

    as.expr = AspExpr.parse(s);

    leaveParser("assignment");

    return as;
  }

  @Override
  public void prettyPrint() {
    name.prettyPrint();
    for(int i = 0; i < subs.size(); i++){
      subs.get(i).prettyPrint();
    }
    prettyWrite(" = ");
    expr.prettyPrint();
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue a = null;

    RuntimeValue ind = null;
    RuntimeValue value = expr.eval(curScope);
    

    if(subs.size() == 0){
      curScope.assign(name.na, value);
      trace(name.na + " = " + value.showInfo());
    }

    else if(subs.size() == 1){
      a = name.eval(curScope);
      ind = subs.get(0).eval(curScope);
      a.evalAssignElem(ind, value, this);
      trace(name.na + "[" + ind + "]" + " = " + value.showInfo());
    }
    else{
      for (int i = 0; i < subs.size()-1; i++) {
        a = name.eval(curScope);
        ind = subs.get(i).eval(curScope);
        a = a.evalSubscription(ind, this);
      }
      a.evalAssignElem(ind, value, this);
      trace(name.na + "[" + ind + "]" + " = " + value.showInfo());
    }

return a;
  }


}
