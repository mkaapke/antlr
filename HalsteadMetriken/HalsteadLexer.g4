lexer grammar HalsteadLexer;
OPERATOR: SCSPEC | TYPE_QUAL | RESERVED | OPERATORS;
OPERAND: IDENTIFIER | TYPESPEC | CONSTANT;
IGNORE: COMMENT | ')' | '}' | ']' | ':' | 'do' | INCLUDE | WS;

IDENTIFIER: ([a-zA-Z]|[0-9])+;
TYPESPEC: 'bool' | 'char' | 'double' | 'float' | 'int' | 'long' | 'short' | 'signed' | 'unsigned' | 'void';
CONSTANT: '"' .*? '"' | '\'' .*? '\'';

SCSPEC: 'auto' | 'extern' | 'inline' | 'register' | 'static' | 'typedef' | 'virtual' | 'mtuable';
TYPE_QUAL:  'const' | 'friend' | 'volatile';
RESERVED: 'asm' | 'break' | 'case' | 'class' | 'continue' | 'default' | 'delete' | 'while' WS? '(' | 'else' | 'enum' | 'for' WS? '(' | 'goto' |
          'if' WS? '(' | 'new' | 'operator' | 'private' | 'protected' | 'public' | 'return' | 'sizeof' |
          'struct' | 'switch' WS? '(' | 'this' | 'union' | 'namespace' | 'using' | 'try' | 'catch' |
          'throw' | 'const_cast' | 'static_cast' | 'dynamic_cast' | 'reinterpret_cast' |
          'typeid' | 'template' | 'explicit' | 'true' | 'false' | 'typename' ;
OPERATORS: '!'|'!='|'%'|'%='|'&'|'&&'|'&='|'('|'*'|'*='
           |'+'|'++'|'+='|','|'-'|'--'|'-='|'->'|'...'|'/'
           |'/='|'::'|'<'|'<<'|'<<='|'<='|'=='|'>'|'>='|'>>'
           |'>>='|'?'|'['|'^'|'^='|'{'|'||'|'='|'~'|';' ;

COMMENT: '//' .*? '\r'? '\n' | '/*' .*? '*/';
INCLUDE: '#include' .*? '\n';

WS : [ \t\r\n]+ -> skip;
