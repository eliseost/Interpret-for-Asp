package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspReturnStmt extends AspSyntax{
  AspExpr expr;

  AspReturnStmt(int n){
    super(n);
  }

  public static AspReturnStmt parse(Scanner s){
    enterParser("return stmt");

    AspReturnStmt ars = new AspReturnStmt(s.curLineNum());
    skip(s, returnToken);
    ars.expr = AspExpr.parse(s);

    leaveParser("return stmt");

    return ars;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("return ");
    expr.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
//-- Must be changed in part 4:
return null;
  }
}