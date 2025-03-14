package com.lapoushko.extension

import com.yandex.mapkit.geometry.Point

/**
 * @author Lapoushko
 */
fun Pair<Double, Double>?.setPoint(): Point? = this?.let {
    Point(it.first, it.second)
}