package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspCompStmt extends AspSyntax{

  AspCompStmt(int n){
    super(n);
  }

  static AspCompStmt parse(Scanner s){
    enterParser("comp stmt");

    AspCompStmt acs = null;

    switch(s.curToken().kind) {
      case defToken:
      acs = AspFuncDef.parse(s); break;
      case whileToken:
      acs = AspWhileStmt.parse(s); break;
      case ifToken:
      acs = AspIfStmt.parse(s); break;
      case forToken:
      acs = AspForStmt.parse(s); break;
    }

    leaveParser("comp stmt");

    return acs;
  }

  @Override
  public void prettyPrint() {
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
//-- Must be changed in part 4:
return null;
  }
}
