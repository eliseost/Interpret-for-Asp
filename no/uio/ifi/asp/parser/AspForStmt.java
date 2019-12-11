package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompStmt{
  AspName name;
  AspExpr expr;
  AspSuite suite;

  AspForStmt(int n){
    super(n);
  }

  public static AspForStmt parse(Scanner s){
    enterParser("for stmt");

    AspForStmt afs = new AspForStmt(s.curLineNum());

    skip(s, forToken);
    afs.name = AspName.parse(s);
    skip(s, inToken);
    afs.expr = AspExpr.parse(s);
    skip(s, colonToken);
    afs.suite = AspSuite.parse(s);

    leaveParser("for stmt");

    return afs;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("for ");
    name.prettyPrint();
    prettyWrite(" in ");
    expr.prettyPrint();
    prettyWrite(": ");
    suite.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue list1 = expr.eval(curScope);
    RuntimeValue value = null;
    //String nValue = n.toString();
    RuntimeIntValue j = null;
    Long l = null;
    //if(list1 instanceof RuntimeListDisplay){
      for (int i = 0; i < list1.listSize(); i++) {
        l = new Long(i);
        j = new RuntimeIntValue(l);
        value = list1.evalSubscription(j, this);
        curScope.assign(name.na, value);
        trace("for #" + (i+1) + ": " + name.na + " = " + value + ":");
        suite.eval(curScope);

      //}
    }
return null;
  }
}
