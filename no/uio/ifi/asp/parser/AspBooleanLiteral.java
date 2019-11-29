package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspBooleanLiteral extends AspAtom {
    boolean value;

    AspBooleanLiteral(int n) {
	     super(n);
    }

    public static AspBooleanLiteral parse(Scanner s) {
	     enterParser("boolean literal");

	      AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());

        if(s.curToken().kind == trueToken){
          abl.value = true;
        }
        if(s.curToken().kind == falseToken){
          abl.value = false;
        }

        s.readNextToken();

	      leaveParser("boolean literal");
	      return abl;
    }

    @Override
    public void prettyPrint() {
      prettyWrite(" " + value);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeBoolValue v = new RuntimeBoolValue(value);
	    return v;
    }
}
