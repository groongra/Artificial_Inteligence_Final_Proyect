package jeops.parser;

/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
public interface ParserConstants {

  int EOF = 0;
  int SINGLE_LINE_COMMENT = 9;
  int FORMAL_COMMENT = 10;
  int MULTI_LINE_COMMENT = 11;
  int FALSE = 13;
  int IMPORT = 14;
  int NULL = 15;
  int PACKAGE = 16;
  int RETURN = 17;
  int TRUE = 18;
  int RULE = 19;
  int DEC = 20;
  int LOCAL_DEC = 21;
  int LOCAL = 22;
  int PRE = 23;
  int ACT = 24;
  int NEW = 25;
  int INSERT = 26;
  int DELETE = 27;
  int MODIFIED = 28;
  int PLUS = 29;
  int MINUS = 30;
  int MULTIPLY = 31;
  int DIVIDE = 32;
  int AND = 33;
  int OR = 34;
  int NOT = 35;
  int GREATER = 36;
  int LESS = 37;
  int EQUAL = 38;
  int LE = 39;
  int GE = 40;
  int DIF = 41;
  int ATRIBUICAO = 42;
  int CONST = 43;
  int FLOAT = 44;
  int INTEGER = 45;
  int DIGIT = 46;
  int IDENTIFIER = 47;
  int LETTER = 48;
  int INTEGER_LITERAL = 49;
  int DECIMAL_LITERAL = 50;
  int CHARACTER_LITERAL = 51;
  int STRING = 52;

  int DEFAULT = 0;
  int IN_SINGLE_LINE_COMMENT = 1;
  int IN_FORMAL_COMMENT = 2;
  int IN_MULTI_LINE_COMMENT = 3;

  String[] tokenImage = {
	"<EOF>",
	"\" \"",
	"\"\\t\"",
	"\"\\n\"",
	"\"\\r\"",
	"\"\\f\"",
	"\"//\"",
	"<token of kind 7>",
	"\"/*\"",
	"<SINGLE_LINE_COMMENT>",
	"\"*/\"",
	"\"*/\"",
	"<token of kind 12>",
	"\"false\"",
	"\"import\"",
	"\"null\"",
	"\"package\"",
	"\"return\"",
	"\"true\"",
	"\"rule\"",
	"\"declarations\"",
	"\"localdecl\"",
	"\"local\"",
	"\"preconditions\"",
	"\"actions\"",
	"\"new\"",
	"\"assert\"",
	"\"retract\"",
	"\"modified\"",
	"\"+\"",
	"\"-\"",
	"\"*\"",
	"\"/\"",
	"\"&&\"",
	"\"||\"",
	"\"!\"",
	"\">\"",
	"\"<\"",
	"\"==\"",
	"\"<=\"",
	"\">=\"",
	"\"!=\"",
	"\"=\"",
	"<CONST>",
	"<FLOAT>",
	"<INTEGER>",
	"<DIGIT>",
	"<IDENTIFIER>",
	"<LETTER>",
	"<INTEGER_LITERAL>",
	"<DECIMAL_LITERAL>",
	"<CHARACTER_LITERAL>",
	"<STRING>",
	"\".\"",
	"\";\"",
	"\"{\"",
	"\"}\"",
	"\"(\"",
	"\")\"",
	"\",\"",
  };

}