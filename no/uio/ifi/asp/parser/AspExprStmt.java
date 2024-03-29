package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExprStmt extends AspSyntax{
  AspExpr expr;

  AspExprStmt(int n){
    super(n);
  }

  public static AspExprStmt parse(Scanner s){
    enterParser("expr stmt");

    AspExprStmt aes = new AspExprStmt(s.curLineNum());
    aes.expr = AspExpr.parse(s);

    leaveParser("expr stmt");

    return aes;
  }

  @Override
  public void prettyPrint() {
      expr.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = expr.eval(curScope);
    trace(String.format(v.toString()));

    return v;
  }
}
