package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTerm extends AspSyntax {
    ArrayList<AspTermOpr> termOpr = new ArrayList<>();
    ArrayList<AspFactor> factor = new ArrayList<>();

    AspTerm(int n) {
	super(n);
    }

    public static AspTerm parse(Scanner s) {
	enterParser("term");

	AspTerm at = new AspTerm(s.curLineNum());

  at.factor.add(AspFactor.parse(s));
  while(s.isTermOpr()){
    at.termOpr.add(AspTermOpr.parse(s));
    at.factor.add(AspFactor.parse(s));
  }

	leaveParser("term");
	return at;
    }

    @Override
    public void prettyPrint() {
      //Kode delvis hentet fra ukesoppgaver.
      factor.get(0).prettyPrint();
      for(int i = 1; i < factor.size(); i++){
        termOpr.get(i-1).prettyPrint();
        factor.get(i).prettyPrint();
      }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v = factor.get(0).eval(curScope);
      for (int i = 1; i < factor.size(); i++) {
        TokenKind k = termOpr.get(i-1).token;
        switch (k) {
          case minusToken:
              v = v.evalSubtract(factor.get(i).eval(curScope), this); break;
          case plusToken:
              v = v.evalAdd(factor.get(i).eval(curScope), this); break;
          default:
              Main.panic("Illegal term opr: " + k + "!");
        }
      }
       return v;
     }
}
