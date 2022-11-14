package com.kelvinfocus.secondpage

object Endpoints {
    // Base endpoints
    const val REDDIT_BASE_URL = "https://www.reddit.com/api/v1/"
    const val OAUTH_BASE_ENDPOINT = "https://oauth.reddit.com/"

    // Auth endpoints
    const val REDDIT_ACCESS_TOKEN_ENDPOINT = "access_token"
    const val REDDIT_AUTH_FULL_ENDPOINT = REDDIT_BASE_URL + "authorize"
    const val REDDIT_TOKEN_FULL_ENDPOINT = REDDIT_BASE_URL + REDDIT_ACCESS_TOKEN_ENDPOINT
}