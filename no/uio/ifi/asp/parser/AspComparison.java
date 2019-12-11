package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspComparison extends AspSyntax {
    ArrayList<AspCompOpr> compOp = new ArrayList<>();
    ArrayList<AspTerm> terms = new ArrayList<>();

    AspComparison(int n) {
	super(n);
    }

    public static AspComparison parse(Scanner s) {
	enterParser("comparison");

	AspComparison ac = new AspComparison(s.curLineNum());

  ac.terms.add(AspTerm.parse(s));
  while(s.isCompOpr()){
    ac.compOp.add(AspCompOpr.parse(s));
    ac.terms.add(AspTerm.parse(s));
  }

	leaveParser("comparison");
	return ac;
    }

    @Override
    public void prettyPrint() {
      //Kode delvis hentet fra ukesoppgaver.
      terms.get(0).prettyPrint();
      for(int i = 1; i < terms.size(); i++){
        compOp.get(i-1).prettyPrint();
        terms.get(i).prettyPrint();
      }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v = terms.get(0).eval(curScope);
      RuntimeValue next = null;

      for (int i = 1; i < terms.size(); i++) {
        v = terms.get(i-1).eval(curScope);
        next = terms.get(i).eval(curScope);
        TokenKind k = compOp.get(i-1).token;
        switch (k) {
          case notEqualToken:
              v = v.evalNotEqual(next, this);

              if(! v.getBoolValue("not equal operand", this)){
                return v;
              }break;
          case lessEqualToken:
            v = v.evalLessEqual(next, this);
            if(! v.getBoolValue("less equal operand", this)){
              return v;
            }break;

          case greaterEqualToken:
          v = v.evalGreaterEqual(next, this);
          if(! v.getBoolValue("greater equal operand", this)){
            return v;
          }break;

          case doubleEqualToken:
          v = v.evalEqual(next, this);
          if(! v.getBoolValue("double equal operand", this)){
            return v;
          } break;

          case greaterToken:
          v = v.evalGreater(next, this);
          if(! v.getBoolValue("greater operand", this)){
            return v;
          } break;

          case lessToken:
          v = v.evalLess(next, this);
          if(! v.getBoolValue("less operand", this)){
            return v;
          } break;

          default:
              Main.panic("Illegal comp opr: " + k + "!");
        }
        //v = terms.get(i).eval(curScope);
      }
	     return v;
    }
}
