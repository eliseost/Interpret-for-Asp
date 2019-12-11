package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspName extends AspAtom{
  String na;

  AspName(int n){
    super(n);
  }

  public static AspName parse(Scanner s){
    enterParser("name");

    AspName an = new AspName(s.curLineNum());
    an.na = s.curToken().name;
    s.readNextToken();

    leaveParser("name");
    return an;
  }
  @Override
  public void prettyPrint() {
    prettyWrite(na);
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return curScope.find(na, this);
  }

  public String getName(){
    return na;
  }
}
