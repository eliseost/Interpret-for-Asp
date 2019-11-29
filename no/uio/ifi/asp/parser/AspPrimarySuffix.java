package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPrimarySuffix extends AspSyntax {
    AspArgument arg;
    AspSubscription sub;

    AspPrimarySuffix(int n) {
	     super(n);
    }

    public static AspPrimarySuffix parse(Scanner s) {
	     enterParser("primary suffix");

	      AspPrimarySuffix aps = new AspPrimarySuffix(s.curLineNum());

        if(s.curToken().kind == leftParToken){
          aps.arg = AspArgument.parse(s);
        }
        if(s.curToken().kind == leftBracketToken){
          aps.sub = AspSubscription.parse(s);
        }

	      leaveParser("primary suffix");
	      return aps;
    }


    @Override
    public void prettyPrint() {
      if(sub != null){
        sub.prettyPrint();
      }
      if(arg != null){
        arg.prettyPrint();
      }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v = null;
      if(sub != null){
        v = sub.eval(curScope);
      }else{
        v = arg.eval(curScope);
      }
	return v;
    }
}
