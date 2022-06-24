package io.github.shakelang.shake.js.native.shake.lang

import io.github.shakelang.shake.js.native.NativeClass
import io.github.shakelang.shake.js.native.NativeField
import io.github.shakelang.shake.js.native.NativeFunction
import io.github.shakelang.shake.js.output.*
import io.github.shakelang.shake.processor.program.types.code.ShakeInvocation
import io.github.shakelang.shake.processor.program.types.code.values.ShakeFieldUsage
import kotlin.String

// package shake.lang;
//
// native class String {
//
//     native static String valueOf(byte[] bytes)
//     native static String valueOf(byte[] bytes, int offset, int length)
//     native static String valueOf(char[] chars)
//     native static String valueOf(char[] chars, int offset, int length)
//     native static String valueOf(byte b)
//     native static String valueOf(short c)
//     native static String valueOf(int i)
//     native static String valueOf(long l)
//     native static String valueOf(float f)
//     native static String valueOf(double d)
//     native static String valueOf(boolean b)
//     native static String valueOf(Object obj)
//
//     native int length
//     native char charAt(int index)
//     native int indexOf(String s)
//     native int indexOf(String s, int fromIndex)
//     native int lastIndexOf(String s)
//     native int lastIndexOf(String s, int fromIndex)
//     native String substring(int beginIndex)
//     native String substring(int beginIndex, int endIndex)
//     native String toLowerCase()
//     native String toUpperCase()
//     native String trim()
//     native String replace(char oldChar, char newChar)
//     native String replace(String oldStr, String newStr)
//     native String concat(String str)
//     native String[] split(String regex)
//     native String[] split(String regex, int limit)
//     native String toString()
//     native byte[] getBytes()
//     native char[] toCharArray()
//     native override boolean equals(Object obj)
//     native override int hashCode()
// }

class String : NativeClass {

    override val qualifiedName: String = "shake.lang.String"

    /// Static methods

    // valueOf(byte[] bytes)
    class FunctionValueOf0 : NativeFunction {
        override val signature: String = "valueOf(byte[] bytes)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if(args.size != 1) {
                throw IllegalArgumentException("String.valueOf(byte[] bytes) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("fromCharCode", JsField("String")), args)
        }
    }

    // valueOf(byte[] bytes, int offset, int length)
    class FunctionValueOf1 : NativeFunction {
        override val signature: String = "valueOf(byte[] bytes, int offset, int length)"
         override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
             if(args.size != 3) {
                 throw IllegalArgumentException("String.valueOf(byte[] bytes, int offset, int length) takes exactly 4 arguments")
             }
            return JsFunctionCall(JsField("fromCharCode", JsField("String")), listOf(
                JsFunctionCall(JsField("slice", args[0]), listOf(args[1], JsSubtract(args[2], args[1])))
            ))
         }
    }

    // valueOf(char[] chars)
    class FunctionValueOf2 : NativeFunction {
        override val signature: String = "valueOf(char[] chars)"
         override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
             if(args.size != 1) {
                 throw IllegalArgumentException("String.valueOf(char[] chars) takes exactly 2 arguments")
             }
             return JsFunctionCall(JsField("join", args[0]), listOf(JsStringLiteral("")))
         }
    }

    // valueOf(char[] chars, int offset, int length)
    class FunctionValueOf3 : NativeFunction {
        override val signature: String = "valueOf(char[] chars, int offset, int length)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 3) {
                throw IllegalArgumentException("String.valueOf(char[] chars, int offset, int length) takes exactly 4 arguments")
            }
            return JsFunctionCall(
                JsField(
                    "join",
                    JsFunctionCall(JsField("slice", args[0]), listOf(args[1], JsSubtract(args[2], args[1])))
                ),
                listOf()
            )
        }
    }

    // valueOf(byte b)
    class FunctionValueOf4 : NativeFunction {
        override val signature: String = "valueOf(byte b)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.valueOf(byte b) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("toString", args[0]), listOf())
        }
    }

    // valueOf(short c)
    class FunctionValueOf5 : NativeFunction {
        override val signature: String = "valueOf(short c)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.valueOf(short c) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("toString", args[0]), listOf())
        }
    }

    // valueOf(int i)
    class FunctionValueOf6 : NativeFunction {
        override val signature: String = "valueOf(int i)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.valueOf(int i) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("toString", args[0]), listOf())
        }
    }

    // valueOf(long l)
    class FunctionValueOf7 : NativeFunction {
        override val signature: String = "valueOf(long l)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.valueOf(long l) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("toString", args[0]), listOf())
        }
    }

    // valueOf(float f)
    class FunctionValueOf8 : NativeFunction {
        override val signature: String = "valueOf(float f)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.valueOf(float f) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("toString", args[0]), listOf())
        }
    }

    // valueOf(double d)
    class FunctionValueOf9 : NativeFunction {
        override val signature: String = "valueOf(double d)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.valueOf(double d) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("toString", args[0]), listOf())
        }
    }

    // valueOf(boolean b)
    class FunctionValueOf10 : NativeFunction {
        override val signature: String = "valueOf(boolean b)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.valueOf(boolean b) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("toString", args[0]), listOf())
        }
    }

    // valueOf(char c)
    class FunctionValueOf11 : NativeFunction {
        override val signature: String = "valueOf(char c)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.valueOf(Object obj) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("toString", args[0]), listOf())
        }
    }


    // instance fields

    // length
    class FieldLength : NativeField {
        override val signature: String = "length"
        override fun handle(fieldUsage: ShakeFieldUsage, receiver: JsValue?): JsValue {
            return JsField("length", receiver)
        }
    }

    // instance methods

    // charAt(int index)
    class MethodCharAt : NativeFunction {
        override val signature: String = "charAt(int index)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.charAt(int index) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("charAt", receiver), args)
        }
    }

    // indexOf(String str)
    class MethodIndexOf : NativeFunction {
        override val signature: String = "indexOf(String str)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.indexOf(String str) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("indexOf", receiver), args)
        }
    }

    // indexOf(String str, int fromIndex)
    class MethodIndexOf2 : NativeFunction {
        override val signature: String = "indexOf(String str, int fromIndex)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 2) {
                throw IllegalArgumentException("String.indexOf(String str, int fromIndex) takes exactly 2 arguments")
            }
            return JsFunctionCall(JsField("indexOf", receiver), args)
        }
    }

    // lastIndexOf(String str)
    class MethodLastIndexOf : NativeFunction {
        override val signature: String = "lastIndexOf(String str)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.lastIndexOf(String str) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("lastIndexOf", receiver), args)
        }
    }

    // lastIndexOf(String str, int fromIndex)
    class MethodLastIndexOf2 : NativeFunction {
        override val signature: String = "lastIndexOf(String str, int fromIndex)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 2) {
                throw IllegalArgumentException("String.lastIndexOf(String str, int fromIndex) takes exactly 2 arguments")
            }
            return JsFunctionCall(JsField("lastIndexOf", receiver), args)
        }
    }

    // substring(int beginIndex)
    class MethodSubstring : NativeFunction {
        override val signature: String = "substring(int beginIndex)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.substring(int beginIndex) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("substring", receiver), args)
        }
    }

    // substring(int beginIndex, int endIndex)
    class MethodSubstring2 : NativeFunction {
        override val signature: String = "substring(int beginIndex, int endIndex)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 2) {
                throw IllegalArgumentException("String.substring(int beginIndex, int endIndex) takes exactly 2 arguments")
            }
            return JsFunctionCall(JsField("substring", receiver), args)
        }
    }

    // toLowerCase()
    class MethodToLowerCase : NativeFunction {
        override val signature: String = "toLowerCase()"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.isNotEmpty()) {
                throw IllegalArgumentException("String.toLowerCase() takes exactly 0 arguments")
            }
            return JsFunctionCall(JsField("toLowerCase", receiver), listOf())
        }
    }

    // toUpperCase()
    class MethodToUpperCase : NativeFunction {
        override val signature: String = "toUpperCase()"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.isNotEmpty()) {
                throw IllegalArgumentException("String.toUpperCase() takes exactly 0 arguments")
            }
            return JsFunctionCall(JsField("toUpperCase", receiver), listOf())
        }
    }

    // trim()
    class MethodTrim : NativeFunction {
        override val signature: String = "trim()"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.isNotEmpty()) {
                throw IllegalArgumentException("String.trim() takes exactly 0 arguments")
            }
            return JsFunctionCall(JsField("trim", receiver), listOf())
        }
    }

    // replace(String oldSubstring, String newSubstring) TODO regex
    class MethodReplace : NativeFunction {
        override val signature: String = "replace(String oldSubstring, String newSubstring)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 2) {
                throw IllegalArgumentException("String.replace(String oldSubstring, String newSubstring) takes exactly 2 arguments")
            }
            return JsFunctionCall(JsField("replace", receiver), args)
        }
    }

    // concat(String str)
    class MethodConcat : NativeFunction {
        override val signature: String = "concat(String str)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.concat(String str) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("concat", receiver), args)
        }
    }

    // split(String regex) TODO regex
    class MethodSplit : NativeFunction {
        override val signature: String = "split(String regex)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 1) {
                throw IllegalArgumentException("String.split(String regex) takes exactly 1 argument")
            }
            return JsFunctionCall(JsField("split", receiver), args)
        }
    }

    // split(String regex, int limit) TODO regex
    class MethodSplit2 : NativeFunction {
        override val signature: String = "split(String regex, int limit)"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.size != 2) {
                throw IllegalArgumentException("String.split(String regex, int limit) takes exactly 2 arguments")
            }
            return JsFunctionCall(JsField("split", receiver), args)
        }
    }

    // toString()
    class MethodToString : NativeFunction {
        override val signature: String = "toString()"
        override fun handle(invokation: ShakeInvocation, args: List<JsValue>, receiver: JsValue?): JsValuedStatement {
            if (args.isNotEmpty()) {
                throw IllegalArgumentException("String.toString() takes exactly 0 arguments")
            }
            return JsFunctionCall(JsField("toString", receiver), listOf())
        }
    }

    override val functions: List<NativeFunction> = listOf(
        FunctionValueOf0(),
        FunctionValueOf1(),
        FunctionValueOf2(),
        FunctionValueOf3(),
        FunctionValueOf4(),
        FunctionValueOf5(),
        FunctionValueOf6(),
        FunctionValueOf7(),
        FunctionValueOf8(),
        FunctionValueOf9(),
        FunctionValueOf10(),
        FunctionValueOf11(),
        MethodCharAt(),
        MethodIndexOf(),
        MethodIndexOf2(),
        MethodLastIndexOf(),
        MethodLastIndexOf2(),
        MethodSubstring(),
        MethodSubstring2(),
        MethodToLowerCase(),
        MethodToUpperCase(),
        MethodTrim(),
        MethodReplace(),
        MethodSplit(),
        MethodSplit2(),
        MethodConcat(),
        MethodToString()
    )

    override val fields: List<NativeField> = listOf(
        FieldLength()
    )

}