package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmtList extends AspSyntax{
  ArrayList<AspSmallStmt> small = new ArrayList<>();
  boolean semicolon = false;

  AspSmallStmtList(int n){
    super(n);
  }

  public static AspSmallStmtList parse(Scanner s){
    enterParser("small stmt list");

    AspSmallStmtList ssl = new AspSmallStmtList(s.curLineNum());

    ssl.small.add(AspSmallStmt.parse(s));

    while(s.curToken().kind == semicolonToken){
      skip(s, semicolonToken);
      if(s.curToken().kind == newLineToken){
        ssl.semicolon = true;
        break;
      }else{
        ssl.small.add(AspSmallStmt.parse(s));
      }
    }

    if(s.curToken().kind == newLineToken){
      skip(s, newLineToken);
    }

    leaveParser("small stmt list");

    return ssl;
  }

  @Override
  public void prettyPrint() {
    small.get(0).prettyPrint();
    for (int i = 1;  i < small.size();  i++) {
      prettyWrite("; ");
       small.get(i).prettyPrint();
    }
    if(semicolon){
      prettyWrite("; ");
    }
     prettyWriteLn();
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
//-- Must be changed in part 4:
return null;
  }
}
