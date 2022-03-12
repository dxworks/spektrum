package me.drbaxr.spektrum.flexible.adapters

import me.drbaxr.spektrum.fixed.model.HierarchyUnit

interface ModelAdapter {
    fun adapt(): Set<HierarchyUnit>
}