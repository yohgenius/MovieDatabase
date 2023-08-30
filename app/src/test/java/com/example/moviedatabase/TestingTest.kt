package com.example.moviedatabase

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.Stack

class TestingTest {
    var openBraceCount: Int = 0
    var closeBraceCount: Int = 0

    var alteredList: MutableList<String> = mutableListOf()


    fun cekFizzBuzz(total: Int) {
        for (i in 1..total) {
            if (i % 3 == 0 && i % 5 == 0) {
                println("FizzBuzz")
            } else if (i % 3 == 0) {
                println("Fizz")
            } else {
                println(i)
            }
        }
    }

    fun checkBrace(brace: String) {
//        for (i in brace){
//            if (brace == "("){
//                openBraceCount++
//            }
//        }
        openBraceCount = brace.count {
            it.toString() == "("
        }
        closeBraceCount = brace.count {
            it.toString() == ")"
        }

        if (openBraceCount == closeBraceCount) {
            println("true")
        } else {
            println("false")
        }

    }

    fun checkBracePair(brace: String): Boolean {
        val stack = Stack<String>()

        brace.forEach {
            when (it.toString()) {
                "{" -> stack.push("}")
                "(" -> stack.push(")")
                "[" -> stack.push("]")

                else -> {
                    if (stack.isEmpty() || stack.pop() != it.toString()) {
                        return false
                    }
                }
            }
        }
        return stack.isEmpty()
    }

    fun asteriskReplacer(listNumber: Int) {


        for (i in 1..listNumber) {
            if (i %5 == 0 || i % 5 ==3){
                println("*")
            }else{
                println(i)
            }
//            if (i % 3 == 0) {
//                alteredList.add("xx")
//            } else if (i % 5 == 0) {
//                alteredList.add("*")
//            } else {
//                alteredList.add(i.toString())
//            }
        }
//        println(alteredList)



    }

    fun tryHashMap(target: Int) {
        val numberList: Array<Int> = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        var firstNumer: Int
        for (i in 1..numberList.size) {
            if (i == target-i){
                firstNumer = i
                println(firstNumer)
            }
        }

    }

    @Test
    fun testHashmap() {
        tryHashMap(14)
    }

    @Test
    fun runFIzzBuzz() {
        cekFizzBuzz(100)
    }

    @Test
    fun runBraceCheck() {
        checkBrace("())(")
    }

    @Test
    fun runBracePairCheck() {
//        checkBracePair("(]")
        assertTrue(checkBracePair("()[]"))

    }

    @Test
    fun runAsteriskTest() {
        asteriskReplacer(10)
    }


    fun twoSum(nums: IntArray, target: Int): IntArray {
        val map = hashMapOf<Int, Int>()
        for(i in 0..nums.size - 1){
            if(map.containsKey(target - nums[i])){
                val tmp = map.get(target - nums[i])!!.toInt()
                val ans : IntArray = intArrayOf(tmp,i)
                return ans
            }
            map.put(nums[i],i)
        }
        return intArrayOf()
    }

    @Test
    fun runTwoSum(){
        println(twoSum(IntArray(6), 2).toString())
    }
}