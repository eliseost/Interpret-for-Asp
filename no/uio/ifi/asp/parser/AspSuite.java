package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax{
  ArrayList<AspStmt> stmts = new ArrayList<>();
  AspSmallStmtList smallStmt;

  AspSuite(int n){
    super(n);
  }

  public static AspSuite parse(Scanner s){
    enterParser("suite");

    AspSuite as = new AspSuite(s.curLineNum());

    if(s.curToken().kind == newLineToken){
      skip(s, newLineToken);
      skip(s, indentToken);
      as.stmts.add(AspStmt.parse(s));
      while(s.curToken().kind != dedentToken){
        as.stmts.add(AspStmt.parse(s));
      }
      skip(s, dedentToken);
    }else{
      as.smallStmt = AspSmallStmtList.parse(s);
    }

    leaveParser("suite");

    return as;
  }

  @Override
  public void prettyPrint() {
    if(stmts.size() == 0){
      smallStmt.prettyPrint();
    }else{
      prettyWriteLn();
      prettyIndent();
      for(int i = 0; i < stmts.size(); i++){
        stmts.get(i).prettyPrint();
      }
      prettyDedent();
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue small = null;
    RuntimeValue stm = null;
    if(smallStmt != null){
      small = smallStmt.eval(curScope);

    }
    else{
      for (int i = 0; i < stmts.size(); i++) {
        stm = stmts.get(i).eval(curScope);
      }
    }
return null;
  }
}
