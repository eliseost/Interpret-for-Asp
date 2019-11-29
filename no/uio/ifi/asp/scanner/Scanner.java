package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;
import java.lang.Double;
import java.lang.Integer;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;


    public Scanner(String fileName) {
	curFileName = fileName;
	indents.push(0);

	try {
	    sourceFile = new LineNumberReader(
			    new InputStreamReader(
				new FileInputStream(fileName),
				"UTF-8"));
	} catch (IOException e) {
	    scannerError("Cannot read " + fileName + "!");
	}
    }


    private void scannerError(String message) {
	String m = "Asp scanner error";
	if (curLineNum() > 0)
	    m += " on line " + curLineNum();
	m += ": " + message;

	Main.error(m);
    }


    public Token curToken() {
	while (curLineTokens.isEmpty()) {
	    readNextLine();
	}
	return curLineTokens.get(0);
    }


    public void readNextToken() {
	if (! curLineTokens.isEmpty())
	    curLineTokens.remove(0);
    }


    private void readNextLine() {
	curLineTokens.clear();

	// Read the next line:
	String line = null;
	try {
	    line = sourceFile.readLine();
	    if (line == null) {
        //Er line == null er enden av filen er nådd
        //Legger til korrekt antall dedents.
        while(indents.peek() > 0){

          indents.pop();
          curLineTokens.add(new Token(dedentToken, curLineNum()));
        }

        Token endToken = new Token(eofToken, curLineNum());
        curLineTokens.add(endToken);

        for (Token t: curLineTokens){
          Main.log.noteToken(t);
        }

		sourceFile.close();
		sourceFile = null;
    return;
    //Måtte legge til return her for at resten av metoden ikke skulle fortsette.

	    } else {
		Main.log.noteSourceLine(curLineNum(), line);
	    }
	} catch (IOException e) {
	    sourceFile = null;
	    scannerError("Unspecified I/O error!");
	}
  //Ignorerer linjer av kun blanke og kommentarer
  String trimmedLine = line.trim();
  if(trimmedLine.length() == 0 || trimmedLine.charAt(0) == '#'){
    return;
  }

  String newLine = expandLeadingTabs(line);

  int numBlancs = findIndent(newLine);

  setIndents(numBlancs);

  int counter = 0;
  String text = "";
  String digit = "";
  String fraction = "";
  char c = newLine.charAt(counter);
  String message = "";

  while(counter < newLine.length()){
    c = newLine.charAt(counter);

    //Med continue hoppes det over resten av while-løkken, og neste iterasjon begynner.
    if(Character.isWhitespace(c)){
      counter++;
      if(counter < newLine.length()){
        c = newLine.charAt(counter);
        continue;
      }else{
        break;
      }
    }
    //Dersom en # dukker opp i linjen skal resten av linjen ignoreres.
    //Hopper ut av while-løkka.
    if(c == '#'){
      break;
    }


    if(isLetterAZ(c) || c == '_'){
      text += Character.toString(c);
      counter++;
      if(counter < newLine.length()){
        c = newLine.charAt(counter);
        while(isLetterAZ(c) || isDigit(c) || c == '_'){
          text += Character.toString(c);
          counter++;
          if(counter < newLine.length()){
            c = newLine.charAt(counter);
          }else{
            break;
          }
        }
      }
      Token token = new Token(nameToken, curLineNum());
      token.name = text;
      token.checkResWords();
      curLineTokens.add(token);
      text = "";
    }

    else if(Character.toString(c).equals("'")|| c == '"'){
      char tegn = c;
      String string = "";
      counter++;
      if(counter < newLine.length()){
        c = newLine.charAt(counter);
      }
      while(c != tegn){
        string += Character.toString(c);
        counter ++;
        if(counter < newLine.length()){
          c = newLine.charAt(counter);
        }
        if(counter > newLine.length()){
          message = "String not terminated";
          scannerError(message);
        }
      }

      Token stringLitToken = new Token(stringToken, curLineNum());
      stringLitToken.stringLit = string;
      curLineTokens.add(stringLitToken);
      counter ++;
    }

    else if(isDigit(c)){
      while(isDigit(c)){
        digit += Character.toString(c);
        counter++;
        if(counter < newLine.length()){
          c = newLine.charAt(counter);
        }else{
          break;
        }
      }
      if(c == '.'){
        fraction = digit;
        fraction += Character.toString(c);
        counter++;
        if(counter < newLine.length() && isDigit(newLine.charAt(counter))){
          c = newLine.charAt(counter);
        }else{
          message = "Illegal use of decimal point";
          scannerError(message);
          //Error! Det er ingenting eller ikke ett tall bak desimalpunktum.
        }
        while(isDigit(c)){
          fraction += Character.toString(c);
          counter++;

          if(counter < newLine.length()){
            c = newLine.charAt(counter);
          }else{
            break;
          }
        }
        /*if(fraction.length() > 1 && fraction.charAt(0) == '0'){
          //Error! Et tall kan ikke begynne på 0.
          message = "Cannot start a number that is not 0 with 0";
          scannerError(message);
        }*/
        double frac = Double.parseDouble(fraction);
        Token doubleToken = new Token(floatToken, curLineNum());
        doubleToken.floatLit = frac;
        curLineTokens.add(doubleToken);
        digit = "";
      }
      else{
        if(digit.length() > 1 && digit.charAt(0) == '0'){
          //Error! Et tall kan ikke begynne på 0.
          message = "Cannot start a number that is not 0 with 0";
          scannerError(message);
        }
        int dig = Integer.parseInt(digit);
        Token intToken = new Token(integerToken, curLineNum());
        intToken.integerLit = dig;
        curLineTokens.add(intToken);
        digit = "";
      }
    }
    else if(c == '.'){
      message = "Illegal use of decimal point";
      scannerError(message);
      //Error, kan ikke ha et desimalpunktum før ett tall. )
    }
    else{
      int currentCount = isOperator(c, newLine, counter);
      if(currentCount > counter){
        counter = currentCount;
      }
      counter++;
    }
  }

	//-- Must be changed in part 1:

	// Terminate line:
	curLineTokens.add(new Token(newLineToken,curLineNum()));

	for (Token t: curLineTokens)
	    Main.log.noteToken(t);
    }

    public int curLineNum() {
	return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
	int indent = 0;

	while (indent<s.length() && s.charAt(indent)==' ') indent++;
	return indent;
    }

    private String expandLeadingTabs(String s) {
	String newS = "";
	for (int i = 0;  i < s.length();  i++) {
	    char c = s.charAt(i);
	    if (c == '\t') {
		      do {
		    newS += " ";
		} while (newS.length()%TABDIST > 0);
	    } else if (c == ' ') {
		newS += " ";
	    } else {
		newS += s.substring(i);
		break;
	    }
	}
	return newS;
    }

    private void setIndents(int numBlanc){
      //Legger til passende antall indents og dedents.
      if(numBlanc > indents.peek()){
        indents.push(numBlanc);
        curLineTokens.add(new Token(indentToken, curLineNum()));
      }
      while(numBlanc < indents.peek()){
        indents.pop();
        curLineTokens.add(new Token(dedentToken, curLineNum()));
      }if(numBlanc != indents.peek()){
        String message = "Wrong indentation";
        scannerError(message);
      }
    }


    private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }

    private int isOperator(char c, String l, int counter){
      int count = counter;
      String line = l;

      //lookahead
      if(c == '*'){
        curLineTokens.add(new Token(astToken, curLineNum()));
      }else if(c == '>'){
        if(count < line.length()-1){
          if(line.charAt(count + 1) == '='){
            curLineTokens.add(new Token(greaterEqualToken, curLineNum()));
            count++;
          }else{
            curLineTokens.add(new Token(greaterToken, curLineNum()));
          }
        }
        else{
          curLineTokens.add(new Token(greaterToken, curLineNum()));
        }
      }else if(c == '<'){
        if(count < line.length()-1){
          if(line.charAt(count + 1) == '='){
            curLineTokens.add(new Token(lessEqualToken, curLineNum()));
            count++;
          }else{
            curLineTokens.add(new Token(lessToken, curLineNum()));
          }
        }
        else{
          curLineTokens.add(new Token(lessToken, curLineNum()));
        }
      }else if(c == '-'){
        curLineTokens.add(new Token(minusToken, curLineNum()));
      }else if(c == '!'){
        if(count < line.length()-1){
          if(line.charAt(count + 1) == '='){
            curLineTokens.add(new Token(notEqualToken, curLineNum()));
            count++;
          }else{
            String message = "Illegal symbol";
            scannerError(message);
          }
        }
        else{
          String message = "Illegal symbol";
          scannerError(message);
        }
      }else if(c == '%'){
        curLineTokens.add(new Token(percentToken, curLineNum()));
      }else if(c == '+'){
        curLineTokens.add(new Token(plusToken, curLineNum()));
      }else if(c == '/'){
        if(count < line.length()-1){
          if(line.charAt(count + 1) == '/'){
            curLineTokens.add(new Token(doubleSlashToken, curLineNum()));
            count++;
          }else{
            curLineTokens.add(new Token(slashToken, curLineNum()));
          }
        }
        else{
          curLineTokens.add(new Token(slashToken, curLineNum()));
        }
      }else if(c == ':'){
        curLineTokens.add(new Token(colonToken, curLineNum()));
      }else if(c == ','){
        curLineTokens.add(new Token(commaToken, curLineNum()));
      }else if(c == '='){
        if(count < line.length()-1){
          if(line.charAt(count + 1) == '='){
            curLineTokens.add(new Token(doubleEqualToken, curLineNum()));
            count++;
          }else{
            curLineTokens.add(new Token(equalToken, curLineNum()));
          }
        }
        else{
          curLineTokens.add(new Token(equalToken, curLineNum()));
        }
      }else if(c == '{'){
        curLineTokens.add(new Token(leftBraceToken, curLineNum()));
      }else if(c == '['){
        curLineTokens.add(new Token(leftBracketToken, curLineNum()));
      }else if(c == '('){
        curLineTokens.add(new Token(leftParToken, curLineNum()));
      }else if(c == '}'){
        curLineTokens.add(new Token(rightBraceToken, curLineNum()));
      }else if(c == ']'){
        curLineTokens.add(new Token(rightBracketToken, curLineNum()));
      }else if(c == ')'){
        curLineTokens.add(new Token(rightParToken, curLineNum()));
      }else if(c == ';'){
        curLineTokens.add(new Token(semicolonToken, curLineNum()));
      }else{
        //Error! Ulovlig tegn!
        String message = "Illegal symbol";
        scannerError(message);
      }
      return count;
    }

    public boolean isCompOpr() {
      //< > ==  <= >= !=
	    TokenKind k = curToken().kind;
      if(k == lessToken){
         return true;
      }else if(k == greaterToken){
        return true;
      }else if(k == doubleEqualToken){
        return true;
      }else if(k == lessEqualToken){
        return true;
      }else if(k == greaterEqualToken){
        return true;
      }else if(k == notEqualToken){
        return true;
      }else{
        return false;
      }
    }


    public boolean isFactorPrefix() {
      //+ -
	TokenKind k = curToken().kind;
  if(k == plusToken || k == minusToken){
    return true;
  }return false;
    }


    public boolean isFactorOpr() {
      //* / % //
	TokenKind k = curToken().kind;
	if(k == astToken || k == slashToken || k == percentToken || k == doubleSlashToken){
    return true;
  }return false;
    }


    public boolean isTermOpr() {
      // + -
	TokenKind k = curToken().kind;
	if(k == plusToken || k == minusToken){
    return true;
  }return false;
    }


    public boolean anyEqualToken() {
	for (Token t: curLineTokens) {
	    if (t.kind == equalToken) return true;
	    if (t.kind == semicolonToken) return false;
	}
	return false;
    }
}
