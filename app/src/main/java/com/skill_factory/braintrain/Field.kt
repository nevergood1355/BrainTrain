package com.skill_factory.braintrain

import kotlin.random.Random

const val EMPTY = 0
const val PAINTED = 1

class Field(val size: Int) {
    val model = Array(size) { Array(size) { Random.nextInt(2) } }
    val temp = Array(size) { Array(size) { EMPTY } }
}