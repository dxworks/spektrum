package me.drbaxr.spektrum.adapters.model.internal

data class MethodTreeNode(
    val identifier: String,
    val callerMethods: Set<MethodTreeNode>
) {
    fun toOrderString(): String {
        val orderMap = mutableMapOf<Int, MutableList<String>>()
        getOrderMap(1, orderMap)
        return orderMap.toString()
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
