package com.dezdeqness.tmdb.presentation.features.feed

import java.util.UUID

sealed class FeedEvent {
    val id: String = UUID.randomUUID().toString()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FeedEvent

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()
}

data class ErrorEvent(
    val errorMessage: String,
) : FeedEvent()
