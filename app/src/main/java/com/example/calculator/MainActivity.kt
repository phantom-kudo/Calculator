package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        val screen:TextView = findViewById(R.id.tvInput)
        screen.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        val screen:TextView = findViewById(R.id.tvInput)
        screen.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        val screen:TextView = findViewById(R.id.tvInput)
        if(lastNumeric && !lastDot) {
            screen.append(".")
            lastNumeric=false
            lastDot=true
        }
    }

    fun onOperator(view: View) {
        val screen:TextView = findViewById(R.id.tvInput)
        if(lastNumeric && !isOperatorAdded(screen.text.toString())) {
            screen.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun zeroAfterDecimal(result : String) : String {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length-2)
        return value
    }

    private fun isOperatorAdded(value: String):Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEqual(view: View) {
        val screen:TextView = findViewById(R.id.tvInput)
        if(lastNumeric) {
            var tvValue =screen.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()) {
                        one =prefix+one
                    }

                    screen.text = zeroAfterDecimal((one.toDouble()-two.toDouble()).toString())
                }else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()) {
                        one =prefix+one
                    }

                    screen.text = zeroAfterDecimal((one.toDouble()+two.toDouble()).toString())
                }else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()) {
                        one =prefix+one
                    }

                    screen.text = zeroAfterDecimal((one.toDouble()*two.toDouble()).toString())
                }else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()) {
                        one =prefix+one
                    }

                    screen.text = zeroAfterDecimal((one.toDouble()/two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }


}