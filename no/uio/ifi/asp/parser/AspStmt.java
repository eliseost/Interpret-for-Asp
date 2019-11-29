package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspStmt extends AspSyntax{
  AspSmallStmtList small;
  AspCompStmt comp;

  AspStmt(int n){
    super(n);
  }

  public static AspStmt parse(Scanner s){
    enterParser("stmt");

    AspStmt as = new AspStmt(s.curLineNum());

    if(s.curToken().kind == defToken || s.curToken().kind == whileToken ||
      s.curToken().kind == forToken || s.curToken().kind == ifToken){
      as.comp = AspCompStmt.parse(s);
    }else{
      as.small = AspSmallStmtList.parse(s);
    }

    leaveParser("stmt");

    return as;
  }

  @Override
  public void prettyPrint() {
    if(small == null){
      comp.prettyPrint();
    }else{
      small.prettyPrint();
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
//-- Must be changed in part 4:
return null;
  }
}
