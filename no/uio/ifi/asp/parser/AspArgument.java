package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArgument extends AspSyntax {
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspArgument(int n) {
	     super(n);
    }

    public static AspArgument parse(Scanner s) {
	     enterParser("argument");

	      AspArgument ap = new AspArgument(s.curLineNum());
        skip(s, leftParToken);
        if(s.curToken().kind == rightParToken){
          skip(s, rightParToken);
        }else{
          ap.expr.add(AspExpr.parse(s));
          while(s.curToken().kind == commaToken){
            skip(s, commaToken);
            ap.expr.add(AspExpr.parse(s));
          }
          skip(s, rightParToken);
        }

	      leaveParser("argument");
	      return ap;
    }

    @Override
    public void prettyPrint() {
      prettyWrite("(");
      if(expr.size() == 1){
        expr.get(0).prettyPrint();
      }
      if(expr.size() > 1){
        for(int i = 0; i < expr.size()-1; i++){
          expr.get(i).prettyPrint();
          prettyWrite(", ");
        }
        expr.get(expr.size()-1).prettyPrint();
      }
      prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      ArrayList<RuntimeValue> l = new ArrayList<RuntimeValue>();
      for (int i = 0; i < expr.size(); i++) {
        AspExpr e = expr.get(i);
        l.add(expr.get(i).eval(curScope));
      }

      RuntimeListDisplay v = new RuntimeListDisplay(l);
	    return v;
    }
}
