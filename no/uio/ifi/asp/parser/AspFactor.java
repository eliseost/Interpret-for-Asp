package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorPrefix> factorpre = new ArrayList<>();
    ArrayList<AspPrimary> prim = new ArrayList<>();
    ArrayList<AspFactorOpr> factoropr = new ArrayList<>();

    AspFactor(int n) {
	super(n);
    }

    public static AspFactor parse(Scanner s) {
	enterParser("factor");

	AspFactor af = new AspFactor(s.curLineNum());

  if(s.isFactorPrefix()){
    af.factorpre.add(AspFactorPrefix.parse(s));
  }
  af.prim.add(AspPrimary.parse(s));

  while(s.isFactorOpr()){
    af.factoropr.add(AspFactorOpr.parse(s));

    if(s.isFactorPrefix()){
      af.factorpre.add(AspFactorPrefix.parse(s));
    }
    af.prim.add(AspPrimary.parse(s));
  }

	leaveParser("factor");
	return af;
    }

    @Override
    public void prettyPrint() {
      if(factorpre.size() > 0){
        factorpre.get(0).prettyPrint();

        prim.get(0).prettyPrint();
        for(int i = 1; i < prim.size(); i++){
          factoropr.get(i-1).prettyPrint();
          factorpre.get(i).prettyPrint();
          prim.get(i).prettyPrint();
        }
      }else{
        prim.get(0).prettyPrint();
        for(int i = 1; i < prim.size(); i++){
          factoropr.get(i-1).prettyPrint();
          prim.get(i).prettyPrint();
        }
      }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v = prim.get(0).eval(curScope);
      RuntimeValue next = null;

      if(factorpre.size() > 0){
        TokenKind k = factorpre.get(0).token;
        switch (k) {
          case minusToken:
              v = v.evalNegate(this); break;
          case plusToken:
              v = v.evalPositive(this); break;
          default:
              Main.panic("Illegal term opr: " + k + "!");
        }

        for (int i = 1; i < prim.size(); i++) {
          //Må lage en til RuntimeValue som representerer den neste primary,
          //som kan endres av factor prefix.

          next = prim.get(i).eval(curScope);

          TokenKind k3 = factorpre.get(i).token;
          switch (k3) {
            case minusToken:
                next = next.evalNegate(this); break;
            case plusToken:
                next = next.evalPositive(this); break;
            default:
                Main.panic("Illegal term opr: " + k3 + "!");
          }


          TokenKind k2 = factoropr.get(i-1).token;
          switch (k2) {
            case doubleSlashToken:
                v = v.evalIntDivide(next, this); break;
            case percentToken:
                v = v.evalModulo(next, this); break;
            case slashToken:
                //Hvis neste tall er null, kan ikke dele!
                v = v.evalDivide(next, this); break;
            case astToken:
                v = v.evalMultiply(next, this); break;
            default:
                Main.panic("Illegal factor opr: " + k2 + "!");
          }


        }
      }else{
        for (int i = 1; i < prim.size(); i++) {
          TokenKind k4 = factoropr.get(i-1).token;
          switch (k4) {
            case doubleSlashToken:
                v = v.evalIntDivide(prim.get(i).eval(curScope), this); break;
            case percentToken:
                v = v.evalModulo(prim.get(i).eval(curScope), this); break;
            case slashToken:
                //Hvis neste tall er null, kan ikke dele!
                v = v.evalDivide(prim.get(i).eval(curScope), this); break;
            case astToken:
                v = v.evalMultiply(prim.get(i).eval(curScope), this); break;
            default:
                Main.panic("Illegal factor opr: " + k4 + "!");
          }
        }
      }
	   return v;
    }
}
