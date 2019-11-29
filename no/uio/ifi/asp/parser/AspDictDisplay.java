package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.Hashtable;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDictDisplay extends AspAtom {
    ArrayList<AspStringLiteral> stringlits = new ArrayList<>();
    ArrayList<AspExpr> exprs = new ArrayList<>();

    AspDictDisplay(int n) {
	     super(n);
    }

    public static AspDictDisplay parse(Scanner s) {
	     enterParser("dict display");

	      AspDictDisplay ad = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);

        if(s.curToken().kind == rightBraceToken){
          skip(s, rightBraceToken);
        }else{
          ad.stringlits.add(AspStringLiteral.parse(s));
          skip(s, colonToken);
          ad.exprs.add(AspExpr.parse(s));
          while(s.curToken().kind == commaToken){
            skip(s, commaToken);
            ad.stringlits.add(AspStringLiteral.parse(s));
            skip(s, colonToken);
            ad.exprs.add(AspExpr.parse(s));
          }
          skip(s, rightBraceToken);
        }

	      leaveParser("dict display");
	      return ad;
    }


    @Override
    public void prettyPrint() {
      prettyWrite("{");
      if(stringlits.size() > 1){
        for(int i = 0; i < stringlits.size()-1; i++){
          stringlits.get(i).prettyPrint();
          prettyWrite(":");
          exprs.get(i).prettyPrint();
          prettyWrite(", ");
        }
        stringlits.get(stringlits.size()-1).prettyPrint();
        prettyWrite(":");
        exprs.get(stringlits.size()-1).prettyPrint();
      }
      if(stringlits.size() == 1){
        stringlits.get(stringlits.size()-1).prettyPrint();
        prettyWrite(":");
        exprs.get(stringlits.size()-1).prettyPrint();
      }
      prettyWrite("} ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

      RuntimeValue v = null;
      String w = "";
      Hashtable<String, RuntimeValue> dict = new Hashtable<String, RuntimeValue>();

      for (int i = 0; i < exprs.size(); i++) {
        v = exprs.get(i).eval(curScope);
        w = stringlits.get(i).eval(curScope).toString();
        dict.put(w, v);
      }

      RuntimeDictDisplay d = new RuntimeDictDisplay(dict);
	    return d;
    }
}
