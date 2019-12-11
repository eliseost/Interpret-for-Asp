package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhileStmt extends AspCompStmt{
  AspExpr expr;
  AspSuite suite;

  AspWhileStmt(int n){
    super(n);
  }

  public static AspWhileStmt parse(Scanner s){
    enterParser("while stmt");

    AspWhileStmt aws = new AspWhileStmt(s.curLineNum());

    skip(s, whileToken);
    aws.expr = AspExpr.parse(s);
    skip(s, colonToken);
    aws.suite = AspSuite.parse(s);

    leaveParser("while stmt");

    return aws;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("while ");
    expr.prettyPrint();
    prettyWrite(": ");
    suite.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    while(true){
      RuntimeValue t = expr.eval(curScope);
      if(! t.getBoolValue("while loop test", this)){
        break;
      }
      trace("while True: ...");
      suite.eval(curScope);
    }
    trace("while False:");
    return null;
  }
}
