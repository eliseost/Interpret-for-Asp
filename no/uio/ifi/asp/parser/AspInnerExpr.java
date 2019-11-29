package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspInnerExpr extends AspAtom {
    AspExpr expr;

    AspInnerExpr(int n) {
	     super(n);
    }

    public static AspInnerExpr parse(Scanner s) {
	     enterParser("inner expr");

	      AspInnerExpr al = new AspInnerExpr(s.curLineNum());

        skip(s, leftParToken);
        al.expr = AspExpr.parse(s);
        skip(s, rightParToken);

	      leaveParser("inner expr");
	      return al;
    }

    @Override
    public void prettyPrint() {
      prettyWrite("(");
      expr.prettyPrint();
      prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v = expr.eval(curScope);
	    return v;
    }
}
