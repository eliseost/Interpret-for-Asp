package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFloatLiteral extends AspAtom {
    double fLit;

    AspFloatLiteral(int n) {
	     super(n);
    }

    public static AspFloatLiteral parse(Scanner s) {
	     enterParser("float literal");

	      AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());

        afl.fLit = s.curToken().floatLit;
        s.readNextToken();

	      leaveParser("float literal");
	      return afl;
    }

    @Override
    public void prettyPrint() {
      prettyWrite("" + fLit);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeFloatValue v = new RuntimeFloatValue(fLit);
      return v;
    }
}
