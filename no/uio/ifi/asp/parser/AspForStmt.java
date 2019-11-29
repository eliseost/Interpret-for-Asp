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
//-- Must be changed in part 4:
return null;
  }
}
