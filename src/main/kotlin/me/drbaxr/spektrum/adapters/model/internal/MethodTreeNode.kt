package me.drbaxr.spektrum.adapters.model.internal

import me.drbaxr.spektrum.adapters.model.external.Method

data class MethodTreeNode(
    val identifier: String,
    val callerMethods: Set<MethodTreeNode>
) {
    fun toOrderString(trimmed: Boolean = false): String {
        val orderMap = getOrderMap()

        var orderString = "$identifier:\n"
        orderMap.keys.forEach { key ->
            orderString = orderString.plus("\tOrder $key:\n")

            val callers = orderMap[key] ?: listOf()
            callers.forEach { caller ->
                orderString = orderString.plus("\t\t${if (trimmed) Method.trimmedFullName(caller) else caller}\n")
            }
        }

        return orderString
    }

    fun getCallerMap(): Map<String, Int> {
        val orderMap = getOrderMap()
        val callerMap = mutableMapOf<String, Int>()
        orderMap.keys.forEach { order ->
            val methodFullNames = orderMap[order] ?: listOf()
            methodFullNames.forEach { fullName ->
                val currentOrder = callerMap[fullName]
                if (currentOrder == null || order < currentOrder) { // if a method calls another method with different orders, keep the lowest order
                    callerMap[fullName] = order
                }
            }
        }

        return callerMap
    }

    private fun getOrderMap(): Map<Int, List<String>> {
        val orderMap = mutableMapOf<Int, MutableList<String>>()
        getOrderMap(1, orderMap)

        return orderMap
    }

    // builds the order map in the map param
    private fun getOrderMap(currentOrder: Int, map: MutableMap<Int, MutableList<String>>) {
        if (callerMethods.isNotEmpty()) {
            if (map[currentOrder] == null) {
                map[currentOrder] = mutableListOf()
            }

            val currentOrderList = map[currentOrder] ?: mutableListOf()
            currentOrderList.addAll(callerMethods.map { it.identifier })

            callerMethods.forEach {
                it.getOrderMap(currentOrder + 1, map)
            }
        }
    }
}
