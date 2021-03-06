package com.syf;
import java.util.*;
import java.lang.StringBuilder;

/**
* @file Scanner.java
* @CopyRight (C.txt) https://github.com/Sngunfei
* @brief
* @author syfnico
* @email syfnico@foxmail.com
* @date 2018-3-31
*/

public class Scanner {
	
	private ArrayList<Character> buffer;
	private int tail = 0;
	private char ch;

	public boolean isEnd(){
		return tail >= buffer.size();
	}

	Scanner(){
		Stdio stdio = new Stdio();
		stdio.readFile("C:\\Users\\86234\\workspace\\FirstProj\\src\\com\\syf\\program.txt");
		this.buffer = stdio.getBuffer();
		this.buffer.trimToSize();
		this.tail = 0;
	}
	
	private char getchar(){
		return buffer.get(tail++).charValue();
	}
	
	private void retract(){
		this.tail--;
		this.ch = ' ';
	}
	
	private boolean isAlpha(char ch){
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
	}
	
	private boolean isDigit(char ch){
		return ch >= '0' && ch <= '9';
	}
	
	private boolean isAlnum(char ch){
		return isAlpha(ch) || isDigit(ch);
	}

	public Token token_scan(){
		StringBuilder sb = new StringBuilder();
		if(tail == buffer.size())
			return new Token("#");
		ch = getchar();
		while(ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r'){
			ch = getchar();
		}
		if(tail <= buffer.size() && isAlpha(ch)){
			sb.append(ch);
			ch = getchar();
			while(isAlnum(ch)){
				sb.append(ch);
				ch = getchar();
			}
			retract();
			String name = sb.toString();
			//return new Word(name);
			if(Code.KEYWORD.contains(name))
				return new Token(name.toUpperCase());
			//return new Token(name);
			return new Token("ID");
		}else if(isDigit(ch)){
			sb.append(ch);
			ch = getchar();
			while(isDigit(ch)){
				sb.append(ch);
				ch = getchar();
			}
			if(ch == '.'){
				sb.append(ch);
				ch = getchar();
				while(isDigit(ch)){
					sb.append(ch);
					ch = getchar();
				}
				retract();
				float num = Float.parseFloat(sb.toString());
				//return new Unsigned_Float(num);
				return new Token("REAL");
			}
			retract();
			int num = Integer.parseInt(sb.toString());
			//return new Unsigned_Int(num);
			return new Token("NUM");
		}else
			switch(ch){
				case '*':sb.append(ch);
						 ch = getchar();
						 if(ch == '='){
							 return new Token(Code.OP.get("*="));
						 }else{
							 retract();
							 return new Token("*");
						 }
				case '+':sb.append(ch);
						 ch = getchar();
						 if(ch == '+'){
							 return new Token(Code.OP.get("++"));
						 }else if(ch == '='){
							 return new Token(Code.OP.get("+="));
						 }else{
							 retract();
							 return new Token("+");
						 }
				case '-':sb.append(ch);
						 ch = getchar();
						 if(ch == '-'){
							 return new Token(Code.OP.get("--"));
						 }else if(ch == '='){
							 return new Token(Code.OP.get("-="));
						 }else if(ch == '>'){
							 return new Token(Code.OP.get("->"));
						 }else{
							 retract();
							 return new Token("-");
						 }
				case '=':sb.append(ch);
						 ch = getchar();
						 if(ch == '='){
							 return new Token(Code.OP.get("=="));
						 }else{
							 retract();
							 return new Token("=");
						 }
				case '%':sb.append(ch);
						 return new Token("%");
				case '>':sb.append(ch);
						 ch = getchar();
						 if(ch == '>'){
						 	ch = getchar();
						 	if(ch == '=')
						 		return new Token(Code.OP.get(">>="));
						 	retract();
						 	return new Token(Code.OP.get(">>"));
						 }else if(ch == '='){
							 return new Token(Code.OP.get(">="));
						 }else{
							 retract();
							 return new Token(">");
						 }
				case '<':sb.append(ch);
						 ch = getchar();
						 if(ch == '<'){
						 	ch = getchar();
						 	if(ch == '=')
						 		return new Token(Code.OP.get("<<="));
						 	retract();
						 	return new Token(Code.OP.get("<<"));
						 }else if(ch == '='){
							 return new Token(Code.OP.get("<="));
						 }else{
							 retract();
							 return new Token("<");
						 }
				case '&':sb.append(ch);
						 ch = getchar();
						 if(ch == '&')
							return new Token(Code.OP.get("&&"));
						 if(ch == '=')
						 	return new Token(Code.OP.get("&="));
						 retract();
						 return new Token("&");
				case '|':sb.append(ch);
						 ch = getchar();
						 if(ch == '|')
						 	return new Token(Code.OP.get("||"));
						 if(ch == '=')
						 	return new Token(Code.OP.get("|="));
						 retract();
						 return new Token("|");
				case '(':sb.append(ch);
						 return new Token("(");
				case ')':sb.append(ch);
						 return new Token(")");
				case '[':sb.append(ch);
				 	     return new Token("[");
				case ']':sb.append(ch);
						 return new Token("]");
				case '{':sb.append(ch);
						 return new Token("{");
				case '}':sb.append(ch);
						 return new Token("}");
				case '/':sb.append(ch);
						 ch = getchar();
						 if(ch == '='){
							 return new Token(Code.OP.get("/="));
						 }else if(ch == '*') {
							 char ch1;
							 Loop:while (true) {
								 ch = getchar();
								 while (ch != '*')
									 ch = getchar();
								 ch1 = getchar();
								 if (ch1 != '/') {
									 retract();
									 break Loop;
								 }
								 return null;
							 }
						 }else {
							 retract();
							 return new Token("\\");
						 }
				case ',':sb.append(ch);
						 return new Token(",");
				case ':':sb.append(ch);
						 return new Token(":");
				case ';':sb.append(ch);
						 return new Token(";");
				case '!':sb.append(ch);
						 return new Token("!");
				case '"':sb.append(ch);
						 return new Token("\"");
			}
			return null;
	}
	
//	private ArrayList<Token> scanner(Stdio scan, String fileName){
//		scan.readFile(fileName);
//		this.buffer = scan.getBuffer();
//		//System.out.println(buffer.size());
//		this.tail = 0;
//		ArrayList<Token> tokens = new ArrayList<>();
//		Token token = token_scan();
//		while(tail < buffer.size()){
//			//System.out.println(token.getSymbol());
//			tokens.add(token);
//			token = token_scan();
//		}
//		return tokens;
//	}
//
//	public static void main(String[] args) {
//		Scanner test = new Scanner();
//		Stdio scan = new Stdio();
//		String fileName = "F:\\123.c";
//		ArrayList<Token> tokens = test.scanner(scan, fileName);
//	}
}