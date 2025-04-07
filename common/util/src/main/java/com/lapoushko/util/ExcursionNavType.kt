package com.lapoushko.util

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.lapoushko.feature.model.CategoryItem
import com.lapoushko.feature.model.ExcursionItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * @author Lapoushko
 */
val ExcursionNavType =
    object : NavType<ExcursionItem>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): ExcursionItem {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(
                    key,
                    ExcursionItem::class.java
                ) as ExcursionItem
            } else {
                bundle.getParcelable<ExcursionItem>(key) as ExcursionItem
            }
        }

        override fun parseValue(value: String): ExcursionItem {
            val decodedValue = URLDecoder.decode(value, "UTF-8")
            return Json.decodeFromString(decodedValue)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: ExcursionItem
        ) {
            bundle.putParcelable(key, value)
        }

        override fun serializeAsValue(value: ExcursionItem): String {
            val json = Json.encodeToString(value)
            return URLEncoder.encode(json, "UTF-8").replace("%", "%25")
        }

        override val name: String = ExcursionItem::class.java.name
    }

val CategoryNavType =
    object : NavType<CategoryItem>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): CategoryItem {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(
                    key,
                    CategoryItem::class.java
                ) as CategoryItem
            } else {
                bundle.getParcelable<CategoryItem>(key) as CategoryItem
            }
        }

        override fun parseValue(value: String): CategoryItem {
            val decodedValue = URLDecoder.decode(value, "UTF-8")
            return Json.decodeFromString(decodedValue)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: CategoryItem
        ) {
            bundle.putParcelable(key, value)
        }

        override fun serializeAsValue(value: CategoryItem): String {
            val json = Json.encodeToString(value)
            return URLEncoder.encode(json, "UTF-8").replace("%", "%25")
        }

        override val name: String = CategoryItem::class.java.name
    }