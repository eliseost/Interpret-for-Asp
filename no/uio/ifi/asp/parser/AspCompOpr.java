package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspCompOpr extends AspSyntax {

    TokenKind token;

    AspCompOpr(int n) {
	     super(n);
    }

    public static AspCompOpr parse(Scanner s) {
	     enterParser("comp opr");

	     AspCompOpr aco = new AspCompOpr(s.curLineNum());

       aco.token = s.curToken().kind;

       s.readNextToken();

	     leaveParser("comp opr");
       return aco;
    }

    @Override
    public void prettyPrint() {
      prettyWrite(" " + token.toString() + " ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }
}
