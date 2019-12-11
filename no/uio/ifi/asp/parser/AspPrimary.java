package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPrimary extends AspSyntax {
    ArrayList<AspPrimarySuffix> primsuff = new ArrayList<>();
    AspAtom atom;

    AspPrimary(int n) {
	     super(n);
    }

    public static AspPrimary parse(Scanner s) {
	     enterParser("primary");

	      AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atom = AspAtom.parse(s);
        while(s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken){
          ap.primsuff.add(AspPrimarySuffix.parse(s));
        }

	      leaveParser("primary");
	      return ap;
    }

    @Override
    public void prettyPrint() {
      atom.prettyPrint();
      for (int i = 0; i < primsuff.size(); i++) {
        primsuff.get(i).prettyPrint();
      }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v = atom.eval(curScope);
      RuntimeValue suff = null;
      for (int i = 0; i < primsuff.size(); i++) {
        if(primsuff.get(i).sub != null){
          //Er ikke sub lik null har vi en eller flere subscritions
          suff = primsuff.get(i).eval(curScope);
          v = v.evalSubscription(suff, this);
        }else{
          //Det er arguments, altså et funksjonskall
          suff = primsuff.get(i).eval(curScope);
          //Suff er RuntimeListDisplay
          trace("Call function " + v.showInfo() + " with params " + suff.showInfo());

          v = v.evalFuncCall(suff.getList(), this);
        }
      }
	    return v;
    }
}
