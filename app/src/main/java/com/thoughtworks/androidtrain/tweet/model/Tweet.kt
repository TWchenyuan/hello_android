package com.thoughtworks.androidtrain.tweet.model


class Tweet(
    private var content: String?,
    private var images: List<Image>?,
    private var sender: Sender?,
    private var comments: List<Comment>?
) {
    private var error: String? = null

    @com.google.gson.annotations.SerializedName("unknown error")
    private var unknownError: String? = null

    fun getContent(): String? {
        return content
    }

    fun setContent(content: String?): Tweet {
        this.content = content
        return this
    }

    fun getImages(): List<Image>? {
        return images
    }

    fun setImages(images: List<Image>?): Tweet {
        this.images = images
        return this
    }

    fun getSender(): Sender? {
        return sender
    }

    fun setSender(sender: Sender?): Tweet {
        this.sender = sender
        return this
    }

    fun getComments(): List<Comment>? {
        return comments
    }

    fun setComments(comments: List<Comment>?): Tweet {
        this.comments = comments
        return this
    }

    fun getError(): String? {
        return error
    }

    fun setError(error: String?) {
        this.error = error
    }

    fun getUnknownError(): String? {
        return unknownError
    }

    fun setUnknownError(unknownError: String?) {
        this.unknownError = unknownError
    }

    override fun toString(): String {
        return "content $content \n"
    }
}
