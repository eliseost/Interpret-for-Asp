package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNoneLiteral extends AspAtom {
    String value;

    AspNoneLiteral(int n) {
	     super(n);
    }

    public static AspNoneLiteral parse(Scanner s) {
	     enterParser("none literal");

	      AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());

        anl.value = s.curToken().kind.toString();
        s.readNextToken();

	      leaveParser("none literal");
	      return anl;
    }

    @Override
    public void prettyPrint() {
      prettyWrite("" + value);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      trace("None");
      RuntimeNoneValue v = new RuntimeNoneValue();

      return v;
    }
}
