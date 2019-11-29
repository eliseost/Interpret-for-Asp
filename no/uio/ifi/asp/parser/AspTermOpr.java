package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTermOpr extends AspSyntax {

    TokenKind token;

    AspTermOpr(int n) {
	     super(n);
    }

    public static AspTermOpr parse(Scanner s) {
	     enterParser("term opr");

	     AspTermOpr ato = new AspTermOpr(s.curLineNum());

       ato.token = s.curToken().kind;
       s.readNextToken();

	     leaveParser("term opr");
       return ato;
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
