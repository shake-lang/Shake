package com.shakelang.shake.parser.api

import io.kotest.core.spec.style.FreeSpec

class Tests : FreeSpec(
    {

        generateTests {
            provider {

                name = "fields"

                primitiveTypes.forEach { (type, typeName) ->
                    combineAttributes().forEachIndexed { i, attributeInfo ->

                        val templates = listOf(
                            template("fields/field") to "field",
                            template("fields/initialized-field") to "initialized_field",
                        )


                        templates.forEach {

                            val template = it.first

                            it.first.apply(
                                replaceTemplate(
                                    "%access%" to attributeInfo.accessModifier,
                                    "%static%" to if (attributeInfo.isStatic) "true" else "false",
                                    "%final%" to if (attributeInfo.isFinal) "true" else "false",
                                    "%attributes%" to attributeInfo.attributeString,
                                    "%type%" to type,
                                    "%type_name%" to typeName,
                                    "%name%" to "field",
                                ),
                            )

                            test("${typeName}_${it.second}$i", isIgnored = false) {
                                this.input = template.code
                                this.expectedJson = template.json
                            }
                        }
                    }
                }
            }

            this.provider {

                name = "methods"

                primitiveTypes.forEach { (type, typeName) ->
                    combineAttributes().forEachIndexed { i, attributeInfo ->

                        val templates = listOf(
                            template("methods/method") to "method",
                        )


                        templates.forEach {

                            val template = it.first

                            it.first.apply(
                                com.shakelang.shake.parser.api.replaceTemplate(
                                    "%access%" to attributeInfo.accessModifier,
                                    "%static%" to if (attributeInfo.isStatic) "true" else "false",
                                    "%final%" to if (attributeInfo.isFinal) "true" else "false",
                                    "%attributes%" to attributeInfo.attributeString,
                                    "%type%" to type,
                                    "%type_name%" to typeName,
                                    "%name%" to "m",
                                ),
                            )

                            test("0/${typeName}_${it.second}$i", isIgnored = false) {
                                this.input = template.code
                                this.expectedJson = template.json
                            }
                        }
                    }
                }

                primitiveTypes.forEach { (type, typeName) ->
                    primitiveTypes.forEach { (type0, typeName0) ->
                        combineAttributes().forEachIndexed { i, attributeInfo ->

                            val templates = listOf(
                                template("methods/parameter-method") to "method",
                            )


                            templates.forEach {

                                val template = it.first

                                it.first.apply(
                                    com.shakelang.shake.parser.api.replaceTemplate(
                                        "%access%" to attributeInfo.accessModifier,
                                        "%static%" to if (attributeInfo.isStatic) "true" else "false",
                                        "%final%" to if (attributeInfo.isFinal) "true" else "false",
                                        "%attributes%" to attributeInfo.attributeString,
                                        "%type%" to type,
                                        "%type_name%" to typeName,
                                        "%name%" to "m",
                                        "%type0_name%" to typeName0,
                                        "%type0%" to type0,
                                    ),
                                )

                                test("1/${typeName}/${typeName0}/${it.second}$i", isIgnored = false) {
                                    this.input = template.code
                                    this.expectedJson = template.json
                                }
                            }
                        }
                    }
                }

                primitiveTypes.forEach { (type0, typeName0) ->
                    primitiveTypes.forEach { (type1, typeName1) ->
                        combineAttributes().forEachIndexed { i, attributeInfo ->

                            val templates = listOf(
                                template("methods/parameter2-method") to "method",
                            )


                            templates.forEach {

                                val template = it.first

                                it.first.apply(
                                    com.shakelang.shake.parser.api.replaceTemplate(
                                        "%access%" to attributeInfo.accessModifier,
                                        "%static%" to if (attributeInfo.isStatic) "true" else "false",
                                        "%final%" to if (attributeInfo.isFinal) "true" else "false",
                                        "%attributes%" to attributeInfo.attributeString,
                                        "%type%" to "void",
                                        "%type_name%" to "void",
                                        "%name%" to "m",
                                        "%type0_name%" to typeName0,
                                        "%type0%" to type0,
                                        "%type1_name%" to typeName1,
                                        "%type1%" to type1,
                                    ),
                                )

                                test("2/${typeName0}/${typeName1}/${it.second}$i", isIgnored = false) {
                                    this.input = template.code
                                    this.expectedJson = template.json
                                }
                            }
                        }
                    }
                }
            }


            provider {

                name = "classes"

                primitiveTypes.forEach { (type, typeName) ->
                    combineAttributes().forEachIndexed { i, attributeInfo ->

                        val templates = listOf(
//                            template("classes/class") to "class",
                            template("classes/class-field") to "class_field",
                            template("classes/initialized-class-field") to "class_field_initialized",
//                            template("classes/inner-class") to "inner_class",
                        )


                        templates.forEach {

                            val template = it.first

                            it.first.apply(
                                replaceTemplate(
                                    "%access%" to attributeInfo.accessModifier,
                                    "%static%" to if (attributeInfo.isStatic) "true" else "false",
                                    "%final%" to if (attributeInfo.isFinal) "true" else "false",
                                    "%attributes%" to attributeInfo.attributeString,
                                    "%type%" to type,
                                    "%type_name%" to typeName,
                                    "%name%" to "test",
                                ),
                            )

                            test("${typeName}_${it.second}$i", isIgnored = false) {
                                this.input = template.code
                                this.expectedJson = template.json
                            }
                        }
                    }
                }

                combineAttributes().forEachIndexed { i, attributeInfo ->

                    val templates = listOf(
                        template("classes/class") to "class",
                        template("classes/inner-class") to "inner_class",
                    )


                    templates.forEach {

                        val template = it.first

                        it.first.apply(
                            replaceTemplate(
                                "%access%" to attributeInfo.accessModifier,
                                "%static%" to if (attributeInfo.isStatic) "true" else "false",
                                "%final%" to if (attributeInfo.isFinal) "true" else "false",
                                "%attributes%" to attributeInfo.attributeString,
                                "%name%" to "test",
                            ),
                        )

                        test("${it.second}$i", isIgnored = false) {
                            this.input = template.code
                            this.expectedJson = template.json
                        }

                    }
                }
            }
        }
    },
)
