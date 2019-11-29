package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> exprs = new ArrayList<>();

    AspListDisplay(int n) {
	     super(n);
    }

    public static AspListDisplay parse(Scanner s) {
	     enterParser("list display");

	      AspListDisplay al = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);

        if(s.curToken().kind == rightBracketToken){
          skip(s, rightBracketToken);
        }else{
          al.exprs.add(AspExpr.parse(s));
          while(s.curToken().kind == commaToken){
            skip(s, commaToken);
            al.exprs.add(AspExpr.parse(s));
          }
          skip(s, rightBracketToken);
        }

	      leaveParser("list display");
	      return al;
    }

    @Override
    public void prettyPrint() {
      prettyWrite("[");
      if(exprs.size() > 0){
        exprs.get(0).prettyPrint();
        for(int i = 1; i < exprs.size(); i++){
          prettyWrite(", ");
          exprs.get(i).prettyPrint();
        }
      }
      prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v = null;
      ArrayList<RuntimeValue> list = new ArrayList<>();

      for (int i = 0; i < exprs.size(); i++) {
        v = exprs.get(i).eval(curScope);
        list.add(v);
      }
      RuntimeListDisplay ny = new RuntimeListDisplay(list);

	    return ny;
    }
}
