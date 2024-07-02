parser grammar ShakeParser;

options {
	tokenVocab = ShakeLexer;
}

// MISC

shakeIdentifier:
	(identifier = IDENTIFIER)		
	| (identifier = IDENTIFIER2)	;

shakeModifier:
	KEYWORD_PUBLIC
	| KEYWORD_PRIVATE
	| KEYWORD_PROTECTED
	| KEYWORD_STATIC
	| KEYWORD_FINAL
	| KEYWORD_ABSTRACT
	| KEYWORD_NATIVE
	| KEYWORD_SYNCHRONIZED
	| KEYWORD_INLINE
	| KEYWORD_OVERRIDE
	| KEYWORD_OPERATOR;

shakeModifierList: shakeModifier*;

shakeNamespace: (identifier = shakeIdentifier)
	| (identifier = shakeIdentifier) (dot = DOT) (
		child = shakeNamespace
	);

shakeType: (namespace = shakeNamespace) (shakeTypeArguments)?;

genericDeclaration: (name = shakeIdentifier) ((colon = COLON) (
		type = shakeType
	))?;

genericsDeclaration: (lt = LT) (
		genericDeclaration (COMMA genericDeclaration)*
	) (gt = GT);

funArgs: (lt = LPAREN) (
		(args = funArgDeclaration (COMMA funArgDeclaration)*)?
	) (gt = RPAREN);

funArgDeclaration: (name = shakeIdentifier) (colon = COLON) (
		type = shakeType
	);

shakeBlock: (lt = LCURLY) (shakeBlockChild)* (gt = RCURLY)
	| (shakeBlockChild);

shakeBlockChild: shakeStatement;

shakeArgument:
	shakeValue							
	| shakeIdentifier ASSIGN shakeValue	;

shakeArguments:
	LPAREN ((shakeArgument) (COMMA shakeArgument) COMMA?)* RPAREN;

shakeTypeArgument: shakeType;
shakeTypeArguments:
	LT ((shakeTypeArgument) ((COMMA shakeTypeArgument) COMMA?)*)? GT;

// Shake File
shakeFile: shakeFileChild*;

shakeFileChild:
	shakePackageDeclaration
	| shakeImportDeclaration
	| shakeFunDeclaration
	| shakeFieldDeclaration
	| shakeClassDeclaration;

shakePackageDeclaration: KEYWORD_PACKAGE (namespace=shakeNamespace);
shakeImportDeclaration: KEYWORD_IMPORT (namespace=shakeNamespace);

shakeFunDeclaration:
	shakeModifierList (fun = KEYWORD_FUN) (
		generics = genericsDeclaration
	)? ((expanding = shakeNamespace) DOT)? (
		name = shakeIdentifier
	) (args = funArgs) ((colon = COLON) (returnType = shakeType))? (
		block = shakeBlock
	)?;

shakeFieldDeclaration:
	shakeModifierList (var = KEYWORD_VAR | var = KEYWORD_VAL) (
		(expanding = shakeNamespace) DOT
	)? (name = shakeIdentifier) (
		(colon = COLON) (variableType = shakeType)
	)? ((assign = ASSIGN) (value = shakeValue))?;

shakeConstructorDeclaration:
	shakeModifierList (fun = KEYWORD_CONSTRUCTOR) (
		identifier = shakeIdentifier
	)? (args = funArgs) (block = shakeBlock)?;

shakeClassChild:
	shakeFieldDeclaration
	| shakeFunDeclaration
	| shakeConstructorDeclaration
	| shakeClassDeclaration;

shakeClassContents: LCURLY (shakeClassChild)* RCURLY;

superClassList: shakeType (COMMA shakeType)*;

shakeClassDeclaration:
	shakeModifierList (class = KEYWORD_CLASS) (
		name = shakeIdentifier
	) (generics = genericsDeclaration)? (
		COLON (superClasses = superClassList)
	)? (contents = shakeClassContents)?;

// Shake Statement
shakeStatement: shakeVarDeclaration;

shakeVarDeclaration:
	shakeModifierList ((var = KEYWORD_VAR) | (var = KEYWORD_VAL)) (
		(expanding = shakeNamespace) DOT
	)? (name = shakeIdentifier) (
		(colon = COLON) (variableType = shakeType)
	)? ((assign = ASSIGN) (value = shakeValue))?;

shakeWhile:
	KEYWORD_WHILE LPAREN (condition = shakeValue) RPAREN (
		body = shakeBlock
	);

shakeDoWhile:
	KEYWORD_DO (body = shakeBlock) KEYWORD_WHILE LPAREN (
		condition = shakeValue
	) RPAREN;

shakeFor:
	KEYWORD_FOR LPAREN (init = shakeStatement) SEMICOLON (
		condition = shakeValue
	) SEMICOLON (round = shakeValue) (body = shakeBlock);

shakeIf:
	KEYWORD_IF LPAREN (condition = shakeValue) RPAREN (
		thenBlock = shakeBlock
	)
	| KEYWORD_IF LPAREN (condition = shakeValue) RPAREN (
		thenBlock = shakeBlock
	) KEYWORD_ELSE (elseBlock = shakeBlock);

// Value

shakeValue: shakeAssignment;


// TODO Ternary
shakeAssignment
    : shakeLogicalOr (ASSIGN shakeAssignment)?
    | shakeLogicalOr ADD_ASSIGN shakeAssignment
    | shakeLogicalOr SUB_ASSIGN shakeAssignment
    | shakeLogicalOr MUL_ASSIGN shakeAssignment
    | shakeLogicalOr DIV_ASSIGN shakeAssignment
    | shakeLogicalOr MOD_ASSIGN shakeAssignment
    | shakeLogicalOr POW_ASSIGN shakeAssignment
    ;

shakeLogicalOr
    : shakeLogicalXor (LOGIC_OR shakeLogicalXor)*
    ;

shakeLogicalXor
    : shakeLogicalAnd (LOGIC_XOR shakeLogicalAnd)*
    ;

shakeLogicalAnd
    : shakeBitwiseOr (LOGIC_AND shakeBitwiseOr)*
    ;

shakeBitwiseOr
    : shakeBitwiseXor (BIT_OR shakeBitwiseXor | BIT_NOR shakeBitwiseXor)*
    ;

shakeBitwiseXor
    : shakeBitwiseAnd (BIT_XOR shakeBitwiseAnd | BIT_XNOR shakeBitwiseAnd)*
    ;

shakeBitwiseAnd
    : shakeEquality (BIT_AND shakeEquality | BIT_NAND shakeEquality)*
    ;

shakeEquality
    : shakeRelation (EQUALS shakeRelation | NEQ shakeRelation)*
    ;

shakeRelation
    : shakeShift (LT shakeShift | GT shakeShift | LTE shakeShift | GTE shakeShift)*
    ;

shakeShift
    : shakeAddition (LT LT shakeAddition | GT GT shakeAddition | GT GT GT shakeAddition)*
    ;

shakeAddition
    : shakeMultiplication (ADD shakeMultiplication | SUB shakeMultiplication)*
    ;

shakeMultiplication
    : shakePower (MUL shakePower | DIV shakePower | MOD shakePower)*
    ;

shakePower
    : shakeCast (POW shakeCast)*
    ;

shakeCast
    : shakeAccess (KEYWORD_AS shakeType)?
    ;

shakeAccess
    : shakeMandory (DOT shakeAccess | LBRACK shakeValue RBRACK | shakeArguments (shakeTypeArguments)?)*
    ;

shakeMandory
    : shakeSign
    | INCR shakeMandory
    | shakeMandory INCR
    | DECR shakeMandory
    | shakeMandory DECR
    ;

shakeSign
    : shakePriority
    | SUB shakeSign
    | ADD shakeSign
    | LOGIC_NOT shakeSign
    | BIT_NOT shakeSign
    ;

shakePriority
    : shakeLiteral
    | LPAREN shakeValue RPAREN
    ;


shakeLiteral: (literal = CHAR)
	| (literal = STRING)
	| (literal = INTEGER)
	| (literal = FLOAT)
	| (literal = BIN_NUMBER)
	| (literal = HEX_NUMBER)
	| (literal = OCT_NUMBER)
	| (literal = IDENTIFIER)
	| (literal = IDENTIFIER2)
	| (literal = KEYWORD_TRUE)
	| (literal = KEYWORD_FALSE)
	| (literal = KEYWORD_THIS)
	| (literal = KEYWORD_SUPER)		;