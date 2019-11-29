package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax {
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
	super(n);
    }

    public static AspExpr parse(Scanner s) {
	enterParser("expr");

	AspExpr ae = new AspExpr(s.curLineNum());
  ae.andTests.add(AspAndTest.parse(s));
  while(s.curToken().kind == orToken){
    skip(s, orToken);
    ae.andTests.add(AspAndTest.parse(s));
  }

	leaveParser("expr");
	return ae;
    }


    @Override
    public void prettyPrint() {
      //Kode hentet fra forelesning.
      int nPrinted = 0;
      for (AspAndTest aat: andTests) {
        if (nPrinted > 0){
          prettyWrite(" or ");
        }
        aat.prettyPrint();
        ++nPrinted;
      }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v = andTests.get(0).eval(curScope);
      for (int i = 1; i < andTests.size(); i++) {
        if(v.getBoolValue("or operand", this)){
          return v;
        }
        v = andTests.get(i).eval(curScope);
        }return v;
      }
    
}
