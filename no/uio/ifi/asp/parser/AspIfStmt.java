package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIfStmt extends AspCompStmt{
  ArrayList<AspExpr> exprs = new ArrayList<>();
  ArrayList<AspSuite> suites = new ArrayList<>();

  AspIfStmt(int n){
    super(n);
  }

  public static AspIfStmt parse(Scanner s){
    enterParser("if stmt");

    AspIfStmt ais = new AspIfStmt(s.curLineNum());

    skip(s, ifToken);
    ais.exprs.add(AspExpr.parse(s));
    skip(s, colonToken);
    ais.suites.add(AspSuite.parse(s));

    while(s.curToken().kind == elifToken){
      skip(s, elifToken);
      ais.exprs.add(AspExpr.parse(s));
      skip(s, colonToken);
      ais.suites.add(AspSuite.parse(s));
    }
    if(s.curToken().kind == elseToken){
      skip(s, elseToken);
      skip(s, colonToken);
      ais.suites.add(AspSuite.parse(s));
    }

    leaveParser("if stmt");

    return ais;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("if ");
    exprs.get(0).prettyPrint();
    prettyWrite(": ");
    suites.get(0).prettyPrint();

    for(int i = 1; i < exprs.size(); i++){
      prettyWrite("elif ");
      exprs.get(i).prettyPrint();
      prettyWrite(": ");
      suites.get(i).prettyPrint();
    }
    if(suites.size() > exprs.size()){
      prettyWrite("else: ");
      suites.get(suites.size()-1).prettyPrint();
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    boolean noTrue = true;
    RuntimeValue e = null;
    RuntimeValue s = null;
    for (int i = 0; i < exprs.size(); i++) {
      e = exprs.get(i).eval(curScope);
      if(e.getBoolValue("if test", this)){
        noTrue = false;
        trace("if True alt # " + (i+1));
        suites.get(i).eval(curScope);
        break;
      }
    }
    if(noTrue && suites.size() > exprs.size()){
      trace("else: ...");
      suites.get(exprs.size()).eval(curScope);
    }
    //Returnerer null?
    return null;
  }
}
