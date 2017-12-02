package com.binarymonks.me.core.blog.api

import java.time.ZonedDateTime


interface Blog {
    fun createBlogEntry(newBlogEntry: NewBlogEntry): BlogEntry
    fun updateBlogEntry(update: UpdateBlogEntry): BlogEntry
    fun getBlogEntryHeaders(): List<BlogEntryHeader>
    fun getBlogEntryById(id: Long): BlogEntry
}

data class NewBlogEntry(
        val title: String,
        val content: String = "",
        val published: Boolean = true
)

data class UpdateBlogEntry(
        val id: Long,
        val title: String,
        val content: String,
        val published: Boolean
)

data class BlogEntry(
        val id: Long,
        val title: String,
        val content: String,
        val published: Boolean,
        val created: ZonedDateTime,
        val updated: ZonedDateTime,
        val publishedOn: ZonedDateTime?
) {
    fun toUpdate(): UpdateBlogEntry = UpdateBlogEntry(
            id = id,
            title = title,
            content = content,
            published = published
    )

    fun toHeader(): BlogEntryHeader = BlogEntryHeader(
            id = id,
            title = title,
            published = published,
            updated = updated,
            created = created,
            publishedOn = publishedOn
    )
}

data class BlogEntryHeader(
        val id: Long,
        val title: String,
        val published: Boolean,
        val created: ZonedDateTime,
        val updated: ZonedDateTime,
        val publishedOn: ZonedDateTime?
)