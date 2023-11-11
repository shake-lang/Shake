@file:Suppress("unused")

package io.github.shakelang.shake.shasambly.generator.simple

import io.github.shakelang.shake.shasambly.generator.basic.*
import io.github.shakelang.shake.shasambly.interpreter.natives.Natives
import io.github.shakelang.shake.util.primitives.bytes.toBytes

typealias SimpleShasamblyGeneratorFunction = SimpleShasambly.() -> Unit

interface SimpleShasambly {

    val natives: NativeFunctions
    val base: SimpleShasamblyGenerator
    val size: Int

    fun opcode(vararg opcodes: ShasamblyOpcode)

    fun incrStack(size: Int)
    fun decrStack()
    fun jumpStatic(target: Int)
    fun jumpStaticTo(target: Int)
    fun jumpStaticToRelative(target: Int)
    fun jumpDynamic()
    fun jumpIf(target: Int)
    fun jumpIfTo(target: Int)
    fun jumpIfToRelative(target: Int)
    fun sjumpStatic(target: Int)
    fun sjumpStaticTo(target: Int)
    fun sjumpIf(target: Int)
    fun sjumpIfTo(target: Int)
    fun invokeNative(address: Short, bytes: ByteArray = byteArrayOf())
    fun globAddr(address: Int)
    fun globalAddress(address: Int) = globAddr(address)

    fun getLocalByte(address: Int)
    fun getLocalShort(address: Int)
    fun getLocalInt(address: Int)
    fun getLocalLong(address: Int)
    fun getLocalFloat(address: Int)
    fun getLocalDouble(address: Int)
    fun getLocalBoolean(address: Int)

    fun b_get_local(address: Int)
    fun s_get_local(address: Int)
    fun i_get_local(address: Int)
    fun l_get_local(address: Int)
    fun f_get_local(address: Int)
    fun d_get_local(address: Int)

    fun storeLocalByte(address: Int)
    fun storeLocalShort(address: Int)
    fun storeLocalInt(address: Int)
    fun storeLocalLong(address: Int)
    fun storeLocalFloat(address: Int)
    fun storeLocalDouble(address: Int)
    fun storeLocalBoolean(address: Int)

    fun b_store_local(address: Int)
    fun s_store_local(address: Int)
    fun i_store_local(address: Int)
    fun l_store_local(address: Int)
    fun f_store_local(address: Int)
    fun d_store_local(address: Int)

    fun pushByte(byte: Byte)
    fun pushShort(short: Short)
    fun pushInt(int: Int)
    fun pushLong(long: Long)
    fun pushFloat(float: Float)
    fun pushDouble(double: Double)
    fun pushBoolean(boolean: Boolean)

    fun bpush(byte: Byte)
    fun spush(short: Short)
    fun ipush(int: Int)
    fun lpush(long: Long)
    fun fpush(float: Float)
    fun dpush(double: Double)
    fun bpush(boolean: Boolean)

    fun bpush(vararg bytes: Byte)
    fun spush(vararg shorts: Short)
    fun ipush(vararg ints: Int)
    fun lpush(vararg longs: Long)
    fun fpush(vararg floats: Float)
    fun dpush(vararg doubles: Double)
    fun bpush(vararg booleans: Boolean)

    fun pushBytes(vararg bytes: Byte)
    fun pushShorts(vararg shorts: Short)
    fun pushInts(vararg ints: Int)
    fun pushLongs(vararg longs: Long)
    fun pushFloats(vararg floats: Float)
    fun pushDoubles(vararg doubles: Double)
    fun pushBooleans(vararg booleans: Boolean)

    fun badd()
    fun bsub()
    fun bmul()
    fun bdiv()
    fun bmod()

    fun sadd()
    fun ssub()
    fun smul()
    fun sdiv()
    fun smod()

    fun iadd()
    fun isub()
    fun imul()
    fun idiv()
    fun imod()

    fun ladd()
    fun lsub()
    fun lmul()
    fun ldiv()
    fun lmod()

    fun fadd()
    fun fsub()
    fun fmul()
    fun fdiv()
    fun fmod()

    fun dadd()
    fun dsub()
    fun dmul()
    fun ddiv()
    fun dmod()

    fun beq()
    fun seq()
    fun ieq()
    fun leq()
    fun feq()
    fun deq()

    fun bbigger()
    fun sbigger()
    fun ibigger()
    fun lbigger()
    fun fbigger()
    fun dbigger()

    fun bsmaller()
    fun ssmaller()
    fun ismaller()
    fun lsmaller()
    fun fsmaller()
    fun dsmaller()

    fun bbiggereq()
    fun sbiggereq()
    fun ibiggereq()
    fun lbiggereq()
    fun fbiggereq()
    fun dbiggereq()

    fun bsmallereq()
    fun ssmallereq()
    fun ismallereq()
    fun lsmallereq()
    fun fsmallereq()
    fun dsmallereq()

    fun badd(b: Byte) {
        bpush(b)
        badd()
    }

    fun bsub(b: Byte) {
        bpush(b)
        bsub()
    }

    fun bmul(b: Byte) {
        bpush(b)
        bmul()
    }

    fun bdiv(b: Byte) {
        bpush(b)
        bdiv()
    }

    fun bmod(b: Byte) {
        bpush(b)
        bmod()
    }

    fun sadd(s: Short) {
        spush(s)
        sadd()
    }

    fun ssub(s: Short) {
        spush(s)
        ssub()
    }

    fun smul(s: Short) {
        spush(s)
        smul()
    }

    fun sdiv(s: Short) {
        spush(s)
        sdiv()
    }

    fun smod(s: Short) {
        spush(s)
        smod()
    }

    fun iadd(i: Int) {
        ipush(i)
        iadd()
    }

    fun isub(i: Int) {
        ipush(i)
        isub()
    }

    fun imul(i: Int) {
        ipush(i)
        imul()
    }

    fun idiv(i: Int) {
        ipush(i)
        idiv()
    }

    fun imod(i: Int) {
        ipush(i)
        imod()
    }

    fun ladd(l: Long) {
        lpush(l)
        ladd()
    }

    fun lsub(l: Long) {
        lpush(l)
        lsub()
    }

    fun lmul(l: Long) {
        lpush(l)
        lmul()
    }

    fun ldiv(l: Long) {
        lpush(l)
        ldiv()
    }

    fun lmod(l: Long) {
        lpush(l)
        lmod()
    }

    fun fadd(f: Float) {
        fpush(f)
        fadd()
    }

    fun fsub(f: Float) {
        fpush(f)
        fsub()
    }

    fun fmul(f: Float) {
        fpush(f)
        fmul()
    }

    fun fdiv(f: Float) {
        fpush(f)
        fdiv()
    }

    fun fmod(f: Float) {
        fpush(f)
        fmod()
    }

    fun dadd(d: Double) {
        dpush(d)
        dadd()
    }

    fun dsub(d: Double) {
        dpush(d)
        dsub()
    }

    fun dmul(d: Double) {
        dpush(d)
        dmul()
    }

    fun ddiv(d: Double) {
        dpush(d)
        ddiv()
    }

    fun dmod(d: Double) {
        dpush(d)
        dmod()
    }

    fun beq(b: Byte) {
        bpush(b)
        beq()
    }

    fun seq(s: Short) {
        spush(s)
        seq()
    }

    fun ieq(i: Int) {
        ipush(i)
        ieq()
    }

    fun leq(l: Long) {
        lpush(l)
        leq()
    }

    fun feq(f: Float) {
        fpush(f)
        feq()
    }

    fun deq(d: Double) {
        dpush(d)
        deq()
    }

    fun bbigger(b: Byte) {
        bpush(b)
        bbigger()
    }

    fun sbigger(s: Short) {
        spush(s)
        sbigger()
    }

    fun ibigger(i: Int) {
        ipush(i)
        ibigger()
    }

    fun lbigger(l: Long) {
        lpush(l)
        lbigger()
    }

    fun fbigger(f: Float) {
        fpush(f)
        fbigger()
    }

    fun dbigger(d: Double) {
        dpush(d)
        dbigger()
    }

    fun bsmaller(b: Byte) {
        bpush(b)
        bsmaller()
    }

    fun ssmaller(s: Short) {
        spush(s)
        ssmaller()
    }

    fun ismaller(i: Int) {
        ipush(i)
        ismaller()
    }

    fun lsmaller(l: Long) {
        lpush(l)
        lsmaller()
    }

    fun fsmaller(f: Float) {
        fpush(f)
        fsmaller()
    }

    fun dsmaller(d: Double) {
        dpush(d)
        dsmaller()
    }

    fun bbiggereq(b: Byte) {
        bpush(b)
        bbiggereq()
    }

    fun sbiggereq(s: Short) {
        spush(s)
        sbiggereq()
    }

    fun ibiggereq(i: Int) {
        ipush(i)
        ibiggereq()
    }

    fun lbiggereq(l: Long) {
        lpush(l)
        lbiggereq()
    }

    fun fbiggereq(f: Float) {
        fpush(f)
        fbiggereq()
    }

    fun dbiggereq(d: Double) {
        dpush(d)
        dbiggereq()
    }

    fun bsmallereq(b: Byte) {
        bpush(b)
        bsmallereq()
    }

    fun ssmallereq(s: Short) {
        spush(s)
        ssmallereq()
    }

    fun ismallereq(i: Int) {
        ipush(i)
        ismallereq()
    }

    fun lsmallereq(l: Long) {
        lpush(l)
        lsmallereq()
    }

    fun fsmallereq(f: Float) {
        fpush(f)
        fsmallereq()
    }

    fun dsmallereq(d: Double) {
        dpush(d)
        dsmallereq()
    }

    fun byteAdd()
    fun byteSub()
    fun byteMul()
    fun byteDiv()
    fun byteMod()

    fun shortAdd()
    fun shortSub()
    fun shortMul()
    fun shortDiv()
    fun shortMod()

    fun intAdd()
    fun intSub()
    fun intMul()
    fun intDiv()
    fun intMod()

    fun longAdd()
    fun longSub()
    fun longMul()
    fun longDiv()
    fun longMod()

    fun floatAdd()
    fun floatSub()
    fun floatMul()
    fun floatDiv()
    fun floatMod()

    fun doubleAdd()
    fun doubleSub()
    fun doubleMul()
    fun doubleDiv()
    fun doubleMod()

    fun byteEq()
    fun shortEq()
    fun intEq()
    fun longEq()
    fun floatEq()
    fun doubleEq()

    fun byteEquals()
    fun shortEquals()
    fun intEquals()
    fun longEquals()
    fun floatEquals()
    fun doubleEquals()

    fun byteBigger()
    fun shortBigger()
    fun intBigger()
    fun longBigger()
    fun floatBigger()
    fun doubleBigger()

    fun byteSmaller()
    fun shortSmaller()
    fun intSmaller()
    fun longSmaller()
    fun floatSmaller()
    fun doubleSmaller()

    fun byteBiggerEq()
    fun shortBiggerEq()
    fun intBiggerEq()
    fun longBiggerEq()
    fun floatBiggerEq()
    fun doubleBiggerEq()

    fun byteBiggerEquals()
    fun shortBiggerEquals()
    fun intBiggerEquals()
    fun longBiggerEquals()
    fun floatBiggerEquals()
    fun doubleBiggerEquals()

    fun byteSmallerEq()
    fun shortSmallerEq()
    fun intSmallerEq()
    fun longSmallerEq()
    fun floatSmallerEq()
    fun doubleSmallerEq()

    fun byteSmallerEquals()
    fun shortSmallerEquals()
    fun intSmallerEquals()
    fun longSmallerEquals()
    fun floatSmallerEquals()
    fun doubleSmallerEquals()

    fun bnot()
    fun boolNot() = bnot()
    fun booleanNot() = bnot()

    fun b_get_global(address: Int) = opcode(ShasamblyOpcodeBGetGlobal(address))
    fun s_get_global(address: Int) = opcode(ShasamblyOpcodeSGetGlobal(address))
    fun i_get_global(address: Int) = opcode(ShasamblyOpcodeIGetGlobal(address))
    fun l_get_global(address: Int) = opcode(ShasamblyOpcodeLGetGlobal(address))

    fun b_get_global_dynamic() = opcode(ShasamblyOpcodeBGetGlobalDynamic())
    fun s_get_global_dynamic() = opcode(ShasamblyOpcodeSGetGlobalDynamic())
    fun i_get_global_dynamic() = opcode(ShasamblyOpcodeIGetGlobalDynamic())
    fun l_get_global_dynamic() = opcode(ShasamblyOpcodeLGetGlobalDynamic())

    fun b_store_global(address: Int) = opcode(ShasamblyOpcodeBStoreGlobal(address))
    fun s_store_global(address: Int) = opcode(ShasamblyOpcodeSStoreGlobal(address))
    fun i_store_global(address: Int) = opcode(ShasamblyOpcodeIStoreGlobal(address))
    fun l_store_global(address: Int) = opcode(ShasamblyOpcodeLStoreGlobal(address))

    fun b_store_global_dynamic() = opcode(ShasamblyOpcodeBStoreGlobalDynamic())
    fun s_store_global_dynamic() = opcode(ShasamblyOpcodeSStoreGlobalDynamic())
    fun i_store_global_dynamic() = opcode(ShasamblyOpcodeIStoreGlobalDynamic())
    fun l_store_global_dynamic() = opcode(ShasamblyOpcodeLStoreGlobalDynamic())

    fun bneg() = opcode(ShasamblyOpcodeBNeg())
    fun sneg() = opcode(ShasamblyOpcodeSNeg())
    fun ineg() = opcode(ShasamblyOpcodeINeg())
    fun lneg() = opcode(ShasamblyOpcodeLNeg())
    fun fneg() = opcode(ShasamblyOpcodeFNeg())
    fun dneg() = opcode(ShasamblyOpcodeDNeg())

    fun b_neg() = bneg()
    fun s_neg() = sneg()
    fun i_neg() = ineg()
    fun l_neg() = lneg()
    fun f_neg() = fneg()
    fun d_neg() = dneg()

    fun babs() = opcode(ShasamblyOpcodeBAbs())
    fun sabs() = opcode(ShasamblyOpcodeSAbs())
    fun iabs() = opcode(ShasamblyOpcodeIAbs())
    fun labs() = opcode(ShasamblyOpcodeLAbs())
    fun fabs() = opcode(ShasamblyOpcodeFAbs())
    fun dabs() = opcode(ShasamblyOpcodeDAbs())

    fun b_abs() = babs()
    fun s_abs() = sabs()
    fun i_abs() = iabs()
    fun l_abs() = labs()
    fun f_abs() = fabs()
    fun d_abs() = dabs()

    fun cast(from: UInt, to: UInt) {
        opcode(ShasamblyPrimitiveCastOpcode(from.toInt(), to.toInt()))
    }

    operator fun invoke(it: SimpleShasamblyGeneratorFunction) {
        it(this)
    }

    fun lateinit(size: Int): (ShasamblyOpcode) -> Unit
    fun relative(it: SimpleShasamblyGeneratorFunction)
    fun run(it: SimpleShasamblyGeneratorFunction) = it(this)

    fun doWhileLoop(it: SimpleShasamblyGeneratorFunction) {
        relative {
            it(this)
            jumpIfTo(0)
        }
    }

    fun doWhileLoop(cond: SimpleShasamblyGeneratorFunction, it: SimpleShasamblyGeneratorFunction) {
        relative {
            it(this)
            cond(this)
            jumpIfTo(0)
        }
    }

    fun whileLoop(cond: SimpleShasamblyGeneratorFunction, it: SimpleShasamblyGeneratorFunction) {
        relative {
            cond(this)
            bnot()
            val init = lateinit(5)
            it(this)
            jumpStaticTo(0)
            val end = size
            init(ShasamblyOpcodeJumpIfToIndex(end))
        }
    }

    fun forLoop(
        decl: SimpleShasamblyGeneratorFunction,
        cond: SimpleShasamblyGeneratorFunction,
        round: SimpleShasamblyGeneratorFunction,
        it: SimpleShasamblyGeneratorFunction
    ) {
        decl(this)
        whileLoop(cond) {
            it(this)
            round(this)
        }
    }

    fun ifElse(
        cond: SimpleShasamblyGeneratorFunction,
        orElse: SimpleShasamblyGeneratorFunction? = null,
        it: SimpleShasamblyGeneratorFunction
    ) {
        relative {
            cond(this)
            if (orElse == null) {
                ineg()
                val init = lateinit(5)
                it(this)
                val end = size
                init(ShasamblyOpcodeJumpIfToIndex(end))
            } else {
                val elseJump = lateinit(5)
                orElse(this)
                val endJump = lateinit(5)
                elseJump(ShasamblyOpcodeJumpIfToIndex(size))
                it(this)
                endJump(ShasamblyOpcodeJumpIfToIndex(size))
            }
        }
    }

    fun push_rel_addr(relIdx: Int) {
        opcode(ShasamblyOpcodeIPushAddress(relIdx))
    }
}

open class RelativeShasamblyGeneratorPart(
    override val base: SimpleShasamblyGenerator,
    val parent: SimpleShasambly,
    generator: SimpleShasamblyGeneratorFunction
) : SimpleShasambly by parent {

    override val natives = NativeFunctions(this)

    val basePosIndex = base.size
    val basePos = base.positionOfIndex(basePosIndex)

    init {
        generator.invoke(this)
    }

    override fun jumpStatic(target: Int) = parent.jumpStatic(basePos + target)
    override fun jumpStaticTo(target: Int) = parent.jumpStaticTo(basePosIndex + target)
    override fun jumpIf(target: Int) = parent.jumpIf(basePos + target)
    override fun jumpIfTo(target: Int) = parent.jumpIfTo(basePosIndex + target)

    override fun relative(it: SimpleShasamblyGeneratorFunction) {
        RelativeShasamblyGeneratorPart(base, this, it)
    }
}

class SimpleShasamblyGenerator(generator: SimpleShasamblyGeneratorFunction) :
    ShasamblyGenerator(mutableListOf()),
    SimpleShasambly {

    override val natives = NativeFunctions(this)
    override val base = this

    init {
        generator.invoke(this)
    }

    override fun opcode(vararg opcodes: ShasamblyOpcode) {
        this.addAll(opcodes)
    }

    override fun incrStack(size: Int) = opcode(ShasamblyOpcodeIncrStack(size))
    override fun decrStack() = opcode(ShasamblyOpcodeDecrStack())
    override fun jumpStatic(target: Int) = opcode(ShasamblyOpcodeJumpStatic(target))
    override fun jumpStaticTo(target: Int) = opcode(ShasamblyOpcodeJumpStaticToIndex(target))
    override fun jumpStaticToRelative(target: Int) = opcode(ShasamblyOpcodeJumpStaticToRelativeIndex(target))
    override fun jumpDynamic() = opcode(ShasamblyOpcodeJumpDynamic())
    override fun jumpIf(target: Int) = opcode(ShasamblyOpcodeJumpIf(target))
    override fun jumpIfTo(target: Int) = opcode(ShasamblyOpcodeJumpIfToIndex(target))
    override fun jumpIfToRelative(target: Int) = opcode(ShasamblyOpcodeJumpIfToRelativeIndex(target))
    override fun sjumpStatic(target: Int) = jumpStatic(target)
    override fun sjumpStaticTo(target: Int) = jumpStaticTo(target)
    override fun sjumpIf(target: Int) = jumpIf(target)
    override fun sjumpIfTo(target: Int) = jumpIfTo(target)
    override fun invokeNative(address: Short, bytes: ByteArray) = opcode(ShasamblyOpcodeInvokeNative(address, bytes))
    override fun globAddr(address: Int) = opcode(ShasamblyOpcodeGlobAddr(address))

    override fun getLocalByte(address: Int) = opcode(ShasamblyOpcodeBGetLocal(address))
    override fun getLocalShort(address: Int) = opcode(ShasamblyOpcodeSGetLocal(address))
    override fun getLocalInt(address: Int) = opcode(ShasamblyOpcodeIGetLocal(address))
    override fun getLocalLong(address: Int) = opcode(ShasamblyOpcodeLGetLocal(address))
    override fun getLocalFloat(address: Int) = getLocalInt(address)
    override fun getLocalDouble(address: Int) = getLocalLong(address)
    override fun getLocalBoolean(address: Int) = getLocalByte(address)

    override fun b_get_local(address: Int) = getLocalByte(address)
    override fun s_get_local(address: Int) = getLocalShort(address)
    override fun i_get_local(address: Int) = getLocalInt(address)
    override fun l_get_local(address: Int) = getLocalLong(address)
    override fun f_get_local(address: Int) = getLocalFloat(address)
    override fun d_get_local(address: Int) = getLocalDouble(address)

    override fun storeLocalByte(address: Int) = opcode(ShasamblyOpcodeBStoreLocal(address))
    override fun storeLocalShort(address: Int) = opcode(ShasamblyOpcodeSStoreLocal(address))
    override fun storeLocalInt(address: Int) = opcode(ShasamblyOpcodeIStoreLocal(address))
    override fun storeLocalLong(address: Int) = opcode(ShasamblyOpcodeLStoreLocal(address))
    override fun storeLocalFloat(address: Int) = storeLocalInt(address)
    override fun storeLocalDouble(address: Int) = storeLocalLong(address)
    override fun storeLocalBoolean(address: Int) = storeLocalByte(address)

    override fun b_store_local(address: Int) = storeLocalByte(address)
    override fun s_store_local(address: Int) = storeLocalShort(address)
    override fun i_store_local(address: Int) = storeLocalInt(address)
    override fun l_store_local(address: Int) = storeLocalLong(address)
    override fun f_store_local(address: Int) = storeLocalFloat(address)
    override fun d_store_local(address: Int) = storeLocalDouble(address)

    override fun pushByte(byte: Byte) = opcode(ShasamblyOpcodeBPush(byte))
    override fun pushShort(short: Short) = opcode(ShasamblyOpcodeSPush(short))
    override fun pushInt(int: Int) = opcode(ShasamblyOpcodeIPush(int))
    override fun pushLong(long: Long) = opcode(ShasamblyOpcodeLPush(long))
    override fun pushFloat(float: Float) = opcode(ShasamblyOpcodeIPush(float))
    override fun pushDouble(double: Double) = opcode(ShasamblyOpcodeLPush(double))
    override fun pushBoolean(boolean: Boolean) = opcode(ShasamblyOpcodeBPush(boolean))

    override fun bpush(byte: Byte) = pushByte(byte)
    override fun spush(short: Short) = pushShort(short)
    override fun ipush(int: Int) = pushInt(int)
    override fun lpush(long: Long) = pushLong(long)
    override fun fpush(float: Float) = pushFloat(float)
    override fun dpush(double: Double) = pushDouble(double)
    override fun bpush(boolean: Boolean) = pushBoolean(boolean)

    override fun bpush(vararg bytes: Byte) = pushBytes(*bytes)
    override fun spush(vararg shorts: Short) = pushShorts(*shorts)
    override fun ipush(vararg ints: Int) = pushInts(*ints)
    override fun lpush(vararg longs: Long) = pushLongs(*longs)
    override fun fpush(vararg floats: Float) = pushFloats(*floats)
    override fun dpush(vararg doubles: Double) = pushDoubles(*doubles)
    override fun bpush(vararg booleans: Boolean) = pushBooleans(*booleans)

    override fun pushBytes(vararg bytes: Byte) = bytes.forEach { pushByte(it) }
    override fun pushShorts(vararg shorts: Short) = shorts.forEach { pushShort(it) }
    override fun pushInts(vararg ints: Int) = ints.forEach { pushInt(it) }
    override fun pushLongs(vararg longs: Long) = longs.forEach { pushLong(it) }
    override fun pushFloats(vararg floats: Float) = floats.forEach { pushFloat(it) }
    override fun pushDoubles(vararg doubles: Double) = doubles.forEach { pushDouble(it) }
    override fun pushBooleans(vararg booleans: Boolean) = booleans.forEach { pushBoolean(it) }

    override fun badd() = opcode(ShasamblyOpcodeBAdd())
    override fun bsub() = opcode(ShasamblyOpcodeBSub())
    override fun bmul() = opcode(ShasamblyOpcodeBMul())
    override fun bdiv() = opcode(ShasamblyOpcodeBDiv())
    override fun bmod() = opcode(ShasamblyOpcodeBMod())

    override fun sadd() = opcode(ShasamblyOpcodeSAdd())
    override fun ssub() = opcode(ShasamblyOpcodeSSub())
    override fun smul() = opcode(ShasamblyOpcodeSMul())
    override fun sdiv() = opcode(ShasamblyOpcodeSDiv())
    override fun smod() = opcode(ShasamblyOpcodeSMod())

    override fun iadd() = opcode(ShasamblyOpcodeIAdd())
    override fun isub() = opcode(ShasamblyOpcodeISub())
    override fun imul() = opcode(ShasamblyOpcodeIMul())
    override fun idiv() = opcode(ShasamblyOpcodeIDiv())
    override fun imod() = opcode(ShasamblyOpcodeIMod())

    override fun ladd() = opcode(ShasamblyOpcodeLAdd())
    override fun lsub() = opcode(ShasamblyOpcodeLSub())
    override fun lmul() = opcode(ShasamblyOpcodeLMul())
    override fun ldiv() = opcode(ShasamblyOpcodeLDiv())
    override fun lmod() = opcode(ShasamblyOpcodeLMod())

    override fun fadd() = opcode(ShasamblyOpcodeFAdd())
    override fun fsub() = opcode(ShasamblyOpcodeFSub())
    override fun fmul() = opcode(ShasamblyOpcodeFMul())
    override fun fdiv() = opcode(ShasamblyOpcodeFDiv())
    override fun fmod() = opcode(ShasamblyOpcodeFMod())

    override fun dadd() = opcode(ShasamblyOpcodeDAdd())
    override fun dsub() = opcode(ShasamblyOpcodeDSub())
    override fun dmul() = opcode(ShasamblyOpcodeDMul())
    override fun ddiv() = opcode(ShasamblyOpcodeDDiv())
    override fun dmod() = opcode(ShasamblyOpcodeDMod())

    override fun beq() = opcode(ShasamblyOpcodeBEq())
    override fun seq() = opcode(ShasamblyOpcodeSEq())
    override fun ieq() = opcode(ShasamblyOpcodeIEq())
    override fun leq() = opcode(ShasamblyOpcodeLEq())
    override fun feq() = opcode(ShasamblyOpcodeFEq())
    override fun deq() = opcode(ShasamblyOpcodeDEq())

    override fun bbigger() = opcode(ShasamblyOpcodeBBigger())
    override fun sbigger() = opcode(ShasamblyOpcodeSBigger())
    override fun ibigger() = opcode(ShasamblyOpcodeIBigger())
    override fun lbigger() = opcode(ShasamblyOpcodeLBigger())
    override fun fbigger() = opcode(ShasamblyOpcodeFBigger())
    override fun dbigger() = opcode(ShasamblyOpcodeDBigger())

    override fun bsmaller() = opcode(ShasamblyOpcodeBSmaller())
    override fun ssmaller() = opcode(ShasamblyOpcodeSSmaller())
    override fun ismaller() = opcode(ShasamblyOpcodeISmaller())
    override fun lsmaller() = opcode(ShasamblyOpcodeLSmaller())
    override fun fsmaller() = opcode(ShasamblyOpcodeFSmaller())
    override fun dsmaller() = opcode(ShasamblyOpcodeDSmaller())

    override fun bbiggereq() = opcode(ShasamblyOpcodeBBiggerEq())
    override fun sbiggereq() = opcode(ShasamblyOpcodeSBiggerEq())
    override fun ibiggereq() = opcode(ShasamblyOpcodeIBiggerEq())
    override fun lbiggereq() = opcode(ShasamblyOpcodeLBiggerEq())
    override fun fbiggereq() = opcode(ShasamblyOpcodeFBiggerEq())
    override fun dbiggereq() = opcode(ShasamblyOpcodeDBiggerEq())

    override fun bsmallereq() = opcode(ShasamblyOpcodeBSmallerEq())
    override fun ssmallereq() = opcode(ShasamblyOpcodeSSmallerEq())
    override fun ismallereq() = opcode(ShasamblyOpcodeISmallerEq())
    override fun lsmallereq() = opcode(ShasamblyOpcodeLSmallerEq())
    override fun fsmallereq() = opcode(ShasamblyOpcodeFSmallerEq())
    override fun dsmallereq() = opcode(ShasamblyOpcodeDSmallerEq())

    override fun byteAdd() = badd()
    override fun byteSub() = bsub()
    override fun byteMul() = bmul()
    override fun byteDiv() = bdiv()
    override fun byteMod() = bmod()

    override fun shortAdd() = sadd()
    override fun shortSub() = ssub()
    override fun shortMul() = smul()
    override fun shortDiv() = sdiv()
    override fun shortMod() = smod()

    override fun intAdd() = iadd()
    override fun intSub() = isub()
    override fun intMul() = imul()
    override fun intDiv() = idiv()
    override fun intMod() = imod()

    override fun longAdd() = ladd()
    override fun longSub() = lsub()
    override fun longMul() = lmul()
    override fun longDiv() = ldiv()
    override fun longMod() = lmod()

    override fun floatAdd() = fadd()
    override fun floatSub() = fsub()
    override fun floatMul() = fmul()
    override fun floatDiv() = fdiv()
    override fun floatMod() = fmod()

    override fun doubleAdd() = dadd()
    override fun doubleSub() = dsub()
    override fun doubleMul() = dmul()
    override fun doubleDiv() = ddiv()
    override fun doubleMod() = dmod()

    override fun byteEq() = beq()
    override fun shortEq() = seq()
    override fun intEq() = ieq()
    override fun longEq() = leq()
    override fun floatEq() = feq()
    override fun doubleEq() = deq()

    override fun byteEquals() = beq()
    override fun shortEquals() = seq()
    override fun intEquals() = ieq()
    override fun longEquals() = leq()
    override fun floatEquals() = feq()
    override fun doubleEquals() = deq()

    override fun byteBigger() = bbigger()
    override fun shortBigger() = sbigger()
    override fun intBigger() = ibigger()
    override fun longBigger() = lbigger()
    override fun floatBigger() = fbigger()
    override fun doubleBigger() = dbigger()

    override fun byteSmaller() = bsmaller()
    override fun shortSmaller() = ssmaller()
    override fun intSmaller() = ismaller()
    override fun longSmaller() = lsmaller()
    override fun floatSmaller() = fsmaller()
    override fun doubleSmaller() = dsmaller()

    override fun byteBiggerEq() = bbiggereq()
    override fun shortBiggerEq() = sbiggereq()
    override fun intBiggerEq() = ibiggereq()
    override fun longBiggerEq() = lbiggereq()
    override fun floatBiggerEq() = fbiggereq()
    override fun doubleBiggerEq() = dbiggereq()

    override fun byteBiggerEquals() = bbiggereq()
    override fun shortBiggerEquals() = sbiggereq()
    override fun intBiggerEquals() = ibiggereq()
    override fun longBiggerEquals() = lbiggereq()
    override fun floatBiggerEquals() = fbiggereq()
    override fun doubleBiggerEquals() = dbiggereq()

    override fun byteSmallerEq() = bsmallereq()
    override fun shortSmallerEq() = ssmallereq()
    override fun intSmallerEq() = ismallereq()
    override fun longSmallerEq() = lsmallereq()
    override fun floatSmallerEq() = fsmallereq()
    override fun doubleSmallerEq() = dsmallereq()

    override fun byteSmallerEquals() = bsmallereq()
    override fun shortSmallerEquals() = ssmallereq()
    override fun intSmallerEquals() = ismallereq()
    override fun longSmallerEquals() = lsmallereq()
    override fun floatSmallerEquals() = fsmallereq()
    override fun doubleSmallerEquals() = dsmallereq()

    override fun bnot() = opcode(ShasamblyOpcodeBoolNot())

    override fun lateinit(size: Int): (ShasamblyOpcode) -> Unit {
        val opcode = ShasamblyLateinitOpcode(size)
        this.opcode(opcode)
        return { opcode.init(it) }
    }

    override fun relative(it: SimpleShasamblyGeneratorFunction) {
        RelativeShasamblyGeneratorPart(this, this, it)
    }
}

class NativeFunctions(val base: SimpleShasambly) {

    /**
     * Print and remove the first byte from the stack
     */
    fun printByte() = base.invokeNative(Natives.printByte)

    /**
     * Print and remove the first short from the stack
     */
    fun printShort() = base.invokeNative(Natives.printShort)

    /**
     * Print and remove the first int from the stack
     */
    fun printInt() = base.invokeNative(Natives.printInt)

    /**
     * Print and remove the first long from the stack
     */
    fun printLong() = base.invokeNative(Natives.printLong)

    /**
     * Print and remove the first float from the stack
     */
    fun printFloat() = base.invokeNative(Natives.printFloat)

    /**
     * Print and remove the first double from the stack
     */
    fun printDouble() = base.invokeNative(Natives.printDouble)

    /**
     * Print a line separator character to the console
     */
    fun printLineEnding() = base.invokeNative(Natives.printLineEnding)

    /**
     * Print the first byte as a utf8 character to the console
     */
    fun printUtf8() = base.invokeNative(Natives.printUtf8)

    /**
     * Declare a global with a given size
     * (leaves the global address on top of the stack)
     */
    fun declareGlobal(csize: Int) = base.invokeNative(Natives.declareGlobal, csize.toBytes())

    /**
     * Declare a global with a given size (the size is the first integer on top of the stack, it will be removed)
     * (leaves the global address on top of the stack)
     */
    fun declareGlobal() = base.invokeNative(Natives.declareGlobalDynamic)

    /**
     * Free a global with a given size
     * The globals address is the first integer on the stack and will be removed
     */
    fun freeGlobal(csize: Int) = base.invokeNative(Natives.freeGlobal, csize.toBytes())

    /**
     * Free a global with a given size
     * The address is the first integer on the stack and will be removed
     * The size is the second integer on the stack and will also be removed
     */
    fun freeGlobal() = base.invokeNative(Natives.freeGlobalDynamic)

    /**
     * Swap two bytes on the stack (the indexes are given as arguments, for example 0 means the top byte,
     * on the stack, 1 the second...)
     */
    fun bswap(first: UByte, second: UByte) =
        base.invokeNative(Natives.bSwap, byteArrayOf(*first.toBytes(), *second.toBytes()))

    /**
     * Swap two shorts on the stack (the indexes are given as arguments, for example 0 means the top short,
     * on the stack, 2 the second...)
     */
    fun sswap(first: UByte, second: UByte) =
        base.invokeNative(Natives.sSwap, byteArrayOf(*first.toBytes(), *second.toBytes()))

    /**
     * Swap two integers on the stack (the indexes are given as arguments, for example 0 means the top integer,
     * on the stack, 2 the second...)
     */
    fun iswap(first: UByte, second: UByte) =
        base.invokeNative(Natives.iSwap, byteArrayOf(*first.toBytes(), *second.toBytes()))

    /**
     * Swap two longs on the stack (the indexes are given as arguments, for example 0 means the top integer,
     * on the stack, 2 the second...)
     */
    fun lswap(first: UByte, second: UByte) =
        base.invokeNative(Natives.lSwap, byteArrayOf(*first.toBytes(), *second.toBytes()))

    /**
     * Duplicate the top byte on the stack
     */
    fun bdup() = base.invokeNative(Natives.bDup)

    /**
     * Duplicate the top short on the stack
     */
    fun sdup() = base.invokeNative(Natives.sDup)

    /**
     * Duplicate the first integer on the stack
     */
    fun idup() = base.invokeNative(Natives.iDup)

    /**
     * Duplicate the first long on the stack
     */
    fun ldup() = base.invokeNative(Natives.lDup)
}

fun shasambly(f: SimpleShasamblyGeneratorFunction) = SimpleShasamblyGenerator(f).generate()
