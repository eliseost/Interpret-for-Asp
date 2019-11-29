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
//-- Must be changed in part 4:
return null;
  }

}
