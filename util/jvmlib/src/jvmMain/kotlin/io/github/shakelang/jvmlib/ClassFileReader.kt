package io.github.shakelang.jvmlib

import java.io.*

@Throws(IOException::class)
fun main(args: Array<String>) {
    if (args.size != 2) throw Error("Need class file as first and second argument, and not more than two arguments")
    val input = File(args[0])
    val output = File(args[1])
    if (!input.exists()) throw Error("This class-file does not exist!")
    val inputStream: InputStream = BufferedInputStream(FileInputStream(input))
    val clazz = ClassFileReader.readClass(inputStream)

    if (!output.exists()) output.createNewFile()
    val out = BufferedOutputStream(FileOutputStream(output))
    clazz.dump(out)
    println(clazz)

    out.flush()
    out.close()

}