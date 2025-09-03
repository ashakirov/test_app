package com.testtapyou.ui

import android.content.Context

class ResourceProviderImpl(
    val context: Context
) : ResourceProvider {

    override fun getString(id: Int): String {
        return context.getString(id)
    }
}