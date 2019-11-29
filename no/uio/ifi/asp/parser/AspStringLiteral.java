package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspStringLiteral extends AspAtom {
    String sLit;

    AspStringLiteral(int n) {
	     super(n);
    }

    public static AspStringLiteral parse(Scanner s) {
	     enterParser("string literal");

	      AspStringLiteral asl = new AspStringLiteral(s.curLineNum());

        if(s.curToken().stringLit == null){
          parserError("Expected string literal but found " +
    			s.curToken().kind + "!", s.curLineNum());
        }else{
          asl.sLit = s.curToken().stringLit;
          s.readNextToken();
        }

	      leaveParser("string literal");
	      return asl;
    }

    @Override
    public void prettyPrint() {
      prettyWrite('"' + sLit + '"');
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeStringValue v = new RuntimeStringValue(sLit);
	    return v;
    }
}
