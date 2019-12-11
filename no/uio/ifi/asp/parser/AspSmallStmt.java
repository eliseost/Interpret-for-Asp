package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmt extends AspSyntax{
  AspReturnStmt ret;
  AspPassStmt pass;
  AspExprStmt expr;
  AspAssignment assign;

  AspSmallStmt(int n){
    super(n);
  }

  public static AspSmallStmt parse(Scanner s){
    enterParser("small stmt");

    AspSmallStmt ass = new AspSmallStmt(s.curLineNum());

    if (s.curToken().kind == returnToken){
      ass.ret = AspReturnStmt.parse(s);
    }
    else if(s.curToken().kind == passToken){
      ass.pass = AspPassStmt.parse(s);
    }
    else if(s.anyEqualToken()){
      ass.assign = AspAssignment.parse(s);
    }else{
      ass.expr = AspExprStmt.parse(s);
    }


    leaveParser("small stmt");

    return ass;
  }

  @Override
  public void prettyPrint() {
    if(ret != null){
      ret.prettyPrint();
    }
    if(pass != null){
      pass.prettyPrint();
    }
    if(expr != null){
      expr.prettyPrint();
    }
    if(assign != null){
      assign.prettyPrint();
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    if(ret != null){
      ret.eval(curScope);
    }
    if(pass != null){
      pass.eval(curScope);
    }
    if(expr != null){
      expr.eval(curScope);
    }
    if(assign != null){
      assign.eval(curScope);
    }
return null;
  }
}
