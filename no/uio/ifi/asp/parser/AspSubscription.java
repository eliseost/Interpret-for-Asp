package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSubscription extends AspSyntax{
  AspExpr expr;

  AspSubscription(int n){
    super(n);
  }

  public static AspSubscription parse(Scanner s){
    enterParser("subscription");

    AspSubscription as = new AspSubscription(s.curLineNum());

    skip(s, leftBracketToken);
    as.expr = AspExpr.parse(s);
    skip(s, rightBracketToken);

    leaveParser("subscription");

    return as;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("[");
    expr.prettyPrint();
    prettyWrite("]");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = expr.eval(curScope);
    return v;
  }

}
