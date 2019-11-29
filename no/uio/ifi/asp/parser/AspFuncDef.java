package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFuncDef extends AspCompStmt{
  AspExpr expr;
  ArrayList<AspName> names = new ArrayList<>();
  AspSuite suite;

  AspFuncDef(int n){
    super(n);
  }

  public static AspFuncDef parse(Scanner s){
    enterParser("func def");

    AspFuncDef afs = new AspFuncDef(s.curLineNum());

    skip(s, defToken);
    afs.names.add(AspName.parse(s));
    skip(s, leftParToken);
    if(s.curToken().kind == rightParToken){
      skip(s, rightParToken);
      skip(s, colonToken);
      afs.suite = AspSuite.parse(s);
    }else{
      afs.names.add(AspName.parse(s));
      while(s.curToken().kind == commaToken){
        skip(s, commaToken);
        afs.names.add(AspName.parse(s));
      }
      skip(s, rightParToken);
      skip(s, colonToken);
      afs.suite = AspSuite.parse(s);
    }

    leaveParser("func def");

    return afs;
  }

  @Override
  public void prettyPrint() {
    prettyWrite("def ");
    names.get(0).prettyPrint();
    prettyWrite(" (");
    //Dersom størrelsen er større enn 2 kan printer jeg alle navna med komma mellom,
    //utenom det siste navnet som ikke skal ha komma etter seg.
    //Er størrelsen ikke større enn 2 er den 2, og da printer jeg det siste navnet.
    if(names.size() > 2){
      for(int i = 1; i < names.size() - 1; i++){
        names.get(i).prettyPrint();
        prettyWrite(", ");
      }
      names.get(names.size()-1).prettyPrint();
    }
    if(names.size() == 2){
      names.get(1).prettyPrint();
    }
    prettyWrite("): ");
    suite.prettyPrint();
    prettyWriteLn();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
//-- Must be changed in part 4:
return null;
  }
}
