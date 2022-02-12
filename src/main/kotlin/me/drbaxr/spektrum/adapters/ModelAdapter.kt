package me.drbaxr.spektrum.adapters

import me.drbaxr.spektrum.main.model.HierarchyUnit

interface ModelAdapter {
    fun adapt(): Set<HierarchyUnit>
}