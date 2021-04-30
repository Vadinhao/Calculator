package com.example.Сalculator

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.Сalculator.adapter.ItemAdapter
import com.example.Сalculator.data.DataSource
import com.example.Сalculator.databinding.ActivityMainBinding

private const val TAG = "MyApp"

class MainActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calk = Calculator(this)
        val myDataset = DataSource().loadData()
        binding.recyclerView1.setHasFixedSize(true)
        binding.recyclerView1.adapter = ItemAdapter(this, myDataset, this, calk, binding)
    }

    override fun onItemClick(
        position: Int,
        calculator: Calculator,
        context: Context,
        binding: ActivityMainBinding
    ) {
        val strOnClickedItem: String =
            this.getString(DataSource().loadData()[position].stringResourceId)
        val currentOperator: AllOperations
        when {
            isOperator(strOnClickedItem) -> {
                currentOperator = when (strOnClickedItem) {
                    this.getString(R.string.addition) -> AllOperations.ADDITION
                    this.getString(R.string.subtraction) -> AllOperations.SUBTRACTION
                    this.getString(R.string.multiplication) -> AllOperations.MULTIPLICATION
                    this.getString(R.string.division) -> AllOperations.DIVISION
                    this.getString(R.string.percentage) -> AllOperations.PERCENTAGE
                    else -> AllOperations.EMPTY
                }
                calculator.operation(currentOperator)
                binding.textView.text = calculator.s2
            }
            isChangeSign(strOnClickedItem) -> {
                calculator.charge()
                outputResult(binding, calculator)
            }
            isEquality(strOnClickedItem) -> {
                calculator.equality()
                outputResult(binding, calculator)
            }
            isClear(strOnClickedItem) -> {
                calculator.clear()
                outputResult(binding, calculator)
            }
            isComma(strOnClickedItem) -> {
                calculator.number(strOnClickedItem)
                outputResult(binding, calculator)
            }
            isEmpty(strOnClickedItem) -> {
                calculator.equality()
                outputResult(binding, calculator)
            }
            isNumber(strOnClickedItem) -> {
                calculator.number(strOnClickedItem)
                outputResult(binding, calculator)
            }
        }
    }

    private fun isOperator(str: String): Boolean {
        return str == this.getString(R.string.division) ||
                str == this.getString(R.string.multiplication) ||
                str == this.getString(R.string.subtraction) ||
                str == this.getString(R.string.addition) ||
                str == this.getString(R.string.percentage)
    }

    private fun isEquality(str: String): Boolean {
        return str == this.getString(R.string.equality)
    }

    private fun isClear(str: String): Boolean {
        return str == this.getString(R.string.ac)
    }

    private fun isChangeSign(str: String): Boolean {
        return str == this.getString(R.string.ch)
    }

    private fun isComma(str: String): Boolean {
        return str == this.getString(R.string.comma)
    }

    private fun isEmpty(str: String): Boolean {
        return str == this.getString(R.string.empty)
    }

    private fun isNumber(str: String): Boolean {
        return (str.toInt() > -1 && str.toInt() < 10)
    }

    private fun outputResult(binding: ActivityMainBinding, calculator: Calculator){
        if (calculator.isOns1)
            binding.textView.text = calculator.s1
        else
            binding.textView.text = calculator.s2
    }
}

class Calculator(private val contextResources: Context) {
    var s1: String = contextResources.getString(R.string._0)
    var s2: String = contextResources.getString(R.string._0)
    private var op = OperationFactory.getButtonOperation(AllOperations.EMPTY)
    var isOns1: Boolean = true
    private var isCommas1: Boolean = true
    private var isCommas2: Boolean = true

    fun operation(str: AllOperations) {
        op = OperationFactory.getButtonOperation(str)
        isOns1 = false
    }

    fun number(str: String) {
        //s1 or s2, isOnlyZero and isCommas?
        if (isOns1) {
            if(s1 == contextResources.getString(R.string._0) && str == contextResources.getString(R.string._0)){}
            else if(s1 == contextResources.getString(R.string._0) && str == contextResources.getString(R.string.comma)){
                s1 += str
                isCommas1 = false
            }
            else if(s1 == contextResources.getString(R.string._0))
                s1 = str
            else if(s1 != contextResources.getString(R.string._0) && str == contextResources.getString(R.string.comma) && isCommas1)
                s1 += str
            else
                s1 += str
        } else {
            if(s2 == contextResources.getString(R.string._0) && str == contextResources.getString(R.string._0)){}
            else if(s2 == contextResources.getString(R.string._0) && str == contextResources.getString(R.string.comma)){
                s2 += str
                isCommas2 = false
            }
            else if(s2 == contextResources.getString(R.string._0))
                s2 = str
            else if(s2 != contextResources.getString(R.string._0) && str == contextResources.getString(R.string.comma) && isCommas2)
                s2 += str
            else
                s2 += str
        }
    }

    fun equality() {
        s1 = op.operation(s1.toDouble(), s2.toDouble()).toBigDecimal().toPlainString()
        s2 = contextResources.getString(R.string._0)
        isOns1 = true
    }

    fun charge() {
        if (isOns1)
            s1 = (s1.toDouble() * -1.0).toBigDecimal().toPlainString()
        else
            s2 = (s2.toDouble() * -1.0).toBigDecimal().toPlainString()
    }

    fun clear() {
        s1 = contextResources.getString(R.string._0)
        s2 = contextResources.getString(R.string._0)
        op = OperationFactory.getButtonOperation(AllOperations.EMPTY)
        isOns1 = true
        isCommas1 = true
        isCommas2 = true
    }
}

interface InterfaceSetOperation {
    fun operation(d1: Double, d2: Double): Double
}

enum class AllOperations {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    PERCENTAGE,
    EMPTY
}

class OperationsAdd : InterfaceSetOperation {
    override fun operation(d1: Double, d2: Double): Double {
        return d1 + d2
    }
}

class OperationsSub : InterfaceSetOperation {
    override fun operation(d1: Double, d2: Double): Double {
        return d1 - d2
    }
}

class OperationsMul : InterfaceSetOperation {
    override fun operation(d1: Double, d2: Double): Double {
        return Math.round(d1 * d2 * 100000.0) / 100000.0
    }
}

class OperationsDiv : InterfaceSetOperation {
    override fun operation(d1: Double, d2: Double): Double {
        return Math.round(d1 / d2 * 100000.0) / 100000.0
    }
}

class OperationsPer : InterfaceSetOperation {
    override fun operation(d1: Double, d2: Double): Double {
        return d1 / 100.0
    }
}

class OperationsEmp : InterfaceSetOperation {
    override fun operation(d1: Double, d2: Double): Double {
        return d1
    }
}

object OperationFactory {
    fun getButtonOperation(type: AllOperations): InterfaceSetOperation {
        return when (type) {
            AllOperations.ADDITION -> {
                OperationsAdd()
            }
            AllOperations.SUBTRACTION -> {
                OperationsSub()
            }
            AllOperations.MULTIPLICATION -> {
                OperationsMul()
            }
            AllOperations.DIVISION -> {
                OperationsDiv()
            }
            AllOperations.PERCENTAGE -> {
                OperationsPer()
            }
            AllOperations.EMPTY -> {
                OperationsEmp()
            }
        }
    }
}