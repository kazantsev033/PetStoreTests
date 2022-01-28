package models

import java.util.*

data class PetPojo (
	val id: Int = 0,
	val category: Category? = null,
	val name: String = "",
	val photoUrls: List<String> = Collections.emptyList(),
	val tags: List<TagsItem> = Collections.emptyList(),
	val status: String = ""
)

data class TagsItem(val id: Int, val name: String)
data class Category(val id: Int, val name: String)