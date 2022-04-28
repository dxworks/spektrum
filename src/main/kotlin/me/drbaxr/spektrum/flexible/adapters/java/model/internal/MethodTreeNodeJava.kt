package me.drbaxr.spektrum.flexible.adapters.java.model.internal

import me.drbaxr.spektrum.flexible.adapters.java.model.external.ProjectJava

data class MethodTreeNodeJava(
    val id: Long,
    val callerMethods: Set<MethodTreeNodeJava>
) {
    fun toOrderString(project: ProjectJava): String {
        val orderMap = getOrderMap()

        var orderString = "${project.getMethodHierarchyName(id)}\n"
        orderMap.keys.forEach { key ->
            orderString = orderString.plus("\tOrder $key:\n")

            val callers = orderMap[key] ?: listOf()
            callers.forEach { caller ->
                orderString = orderString.plus("\t\t${project.getMethodHierarchyName(caller)}\n")
            }
        }

        return orderString
    }

    fun getCallerMap(project: ProjectJava): Map<String, Int> {
        val orderMap = getOrderMap()
        val callerMap = mutableMapOf<String, Int>()

        orderMap.keys.forEach { order ->
            val methodIds = orderMap[order] ?: listOf()
            methodIds.forEach { id ->
                val fullName = project.getMethodHierarchyName(id)
                val currentOrder = callerMap[fullName]
                if (currentOrder == null || order < currentOrder) {
                    callerMap[fullName] = order
                }
            }
        }

        return callerMap
    }

    private fun getOrderMap(): Map<Int, List<Long>> {
        val orderMap = mutableMapOf<Int, MutableList<Long>>()
        getOrderMap(1, orderMap)

        return orderMap
    }

    // builds the order map in the map param
    private fun getOrderMap(currentOrder: Int, map: MutableMap<Int, MutableList<Long>>) {
        if (callerMethods.isNotEmpty()) {
            if (map[currentOrder] == null) {
                map[currentOrder] = mutableListOf()
            }

            val currentOrderList = map[currentOrder] ?: mutableListOf()
            currentOrderList.addAll(callerMethods.map { it.id })

            callerMethods.forEach {
                it.getOrderMap(currentOrder + 1, map)
            }
        }
    }
}