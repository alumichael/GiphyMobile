package com.michaelalu.chililabs.data.model

data class GiphyApiResponse(
    val data: List<Data> = listOf(),
    val meta: Meta? = null,
    val pagination: Pagination = Pagination()
)

data class Data(
    val analytics: Analytics? = null,
    val analytics_response_payload: String? = null,
    val bitly_gif_url: String? = null,
    val bitly_url: String? = null,
    val content_url: String? = null,
    val embed_url: String? = null,
    val id: String? = null,
    val images: Images? = null,
    val import_datetime: String? = null,
    val is_sticker: Int? = null,
    val rating: String? = null,
    val slug: String? = null,
    val source: String? = null,
    val source_post_url: String? = null,
    val source_tld: String? = null,
    val title: String? = null,
    val trending_datetime: String? = null,
    val type: String? = null,
    val url: String? = null,
    val user: User? = null,
    val username: String? = null
)

data class Meta(
    val msg: String? = null,
    val response_id: String? = null,
    val status: Int? = null
)

data class Pagination(
    val count: Int = 0,
    val offset: Int = 0,
    val total_count: Int = 0
)

data class Analytics(
    val onclick: Onclick? = null,
    val onload: Onload? = null,
    val onsent: Onsent? = null
)

data class Images(
    val `480w_still`: WStill? = null,
    val `4k`: K? = null,
    val downsized: Downsized? = null,
    val downsized_large: DownsizedLarge? = null,
    val downsized_medium: DownsizedMedium? = null,
    val downsized_small: DownsizedSmall? = null,
    val downsized_still: DownsizedStill? = null,
    val fixed_height: FixedHeight? = null,
    val fixed_height_downsampled: FixedHeightDownsampled? = null,
    val fixed_height_small: FixedHeightSmall? = null,
    val fixed_height_small_still: FixedHeightSmallStill? = null,
    val fixed_height_still: FixedHeightStill? = null,
    val fixed_width: FixedWidth? = null,
    val fixed_width_downsampled: FixedWidthDownsampled? = null,
    val fixed_width_small: FixedWidthSmall? = null,
    val fixed_width_small_still: FixedWidthSmallStill? = null,
    val fixed_width_still: FixedWidthStill? = null,
    val hd: Hd? = null,
    val looping: Looping? = null,
    val original: Original? = null,
    val original_mp4: OriginalMp4? = null,
    val original_still: OriginalStill? = null,
    val preview: Preview? = null,
    val preview_gif: PreviewGif? = null,
    val preview_webp: PreviewWebp? = null
)

data class User(
    val avatar_url: String? = null,
    val banner_image: String? = null,
    val banner_url: String? = null,
    val description: String? = null,
    val display_name: String? = null,
    val instagram_url: String? = null,
    val is_verified: Boolean? = null,
    val profile_url: String? = null,
    val username: String? = null,
    val website_url: String? = null
)

data class Onclick(
    val url: String? = null
)

data class Onload(
    val url: String? = null
)

data class Onsent(
    val url: String? = null
)

data class WStill(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class K(
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val width: String? = null
)

data class Downsized(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class DownsizedLarge(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class DownsizedMedium(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class DownsizedSmall(
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val width: String? = null
)

data class DownsizedStill(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class FixedHeight(
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val size: String? = null,
    val url: String? = null,
    val webp: String? = null,
    val webp_size: String? = null,
    val width: String? = null
)

data class FixedHeightDownsampled(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val webp: String? = null,
    val webp_size: String? = null,
    val width: String? = null
)

data class FixedHeightSmall(
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val size: String? = null,
    val url: String? = null,
    val webp: String? = null,
    val webp_size: String? = null,
    val width: String? = null
)

data class FixedHeightSmallStill(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class FixedHeightStill(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class FixedWidth(
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val size: String? = null,
    val url: String? = null,
    val webp: String? = null,
    val webp_size: String? = null,
    val width: String? = null
)

data class FixedWidthDownsampled(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val webp: String? = null,
    val webp_size: String? = null,
    val width: String? = null
)

data class FixedWidthSmall(
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val size: String? = null,
    val url: String? = null,
    val webp: String? = null,
    val webp_size: String? = null,
    val width: String? = null
)

data class FixedWidthSmallStill(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class FixedWidthStill(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class Hd(
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val width: String? = null
)

data class Looping(
    val mp4: String? = null,
    val mp4_size: String? = null
)

data class Original(
    val frames: String? = null,
    val hash: String? = null,
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val size: String? = null,
    val url: String? = null,
    val webp: String? = null,
    val webp_size: String? = null,
    val width: String? = null
)

data class OriginalMp4(
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val width: String? = null
)

data class OriginalStill(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class Preview(
    val height: String? = null,
    val mp4: String? = null,
    val mp4_size: String? = null,
    val width: String? = null
)

data class PreviewGif(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)

data class PreviewWebp(
    val height: String? = null,
    val size: String? = null,
    val url: String? = null,
    val width: String? = null
)