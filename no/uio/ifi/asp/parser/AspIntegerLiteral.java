package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIntegerLiteral extends AspAtom {
    long intLit;

    AspIntegerLiteral(int n) {
	     super(n);
    }

    public static AspIntegerLiteral parse(Scanner s) {
	     enterParser("integer literal");

	      AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());

        ail.intLit = s.curToken().integerLit;
        s.readNextToken();

	      leaveParser("integer literal");
	      return ail;
    }

    @Override
    public void prettyPrint() {
      prettyWrite("" + intLit);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeIntValue v = new RuntimeIntValue(intLit);
	    return v;
    }
}
