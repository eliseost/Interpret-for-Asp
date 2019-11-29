package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspAtom extends AspSyntax {

    AspAtom(int n){
      super(n);
    }

    public static AspAtom parse(Scanner s) {
     enterParser("atom");

     AspAtom aa = null;
     //Kode hentet fra forelesning.
     switch (s.curToken().kind) {
     case falseToken:
         aa = AspBooleanLiteral.parse(s); break;
     case trueToken:
         aa = AspBooleanLiteral.parse(s); break;
     case floatToken:
         aa = AspFloatLiteral.parse(s); break;
     case integerToken:
         aa = AspIntegerLiteral.parse(s); break;
     case leftBraceToken:
         aa = AspDictDisplay.parse(s); break;
     case leftBracketToken:
         aa = AspListDisplay.parse(s); break;
     case leftParToken:
         aa = AspInnerExpr.parse(s); break;
     case nameToken:
         aa = AspName.parse(s); break;
     case noneToken:
         aa = AspNoneLiteral.parse(s); break;
     case stringToken:
         aa = AspStringLiteral.parse(s); break;
     default:
         parserError("Expected an expression atom but found a " +
         s.curToken().kind + "!", s.curLineNum());
    }

	      leaveParser("atom");
	      return aa;
  }

}
