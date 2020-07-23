
import com.google.gson.annotations.SerializedName
data class CategoryDto(
    @SerializedName("category_list")
    var categoryList: List<Category>
)

data class Category(
    @SerializedName("category_id")
    var categoryId: String,
    @SerializedName("category_image")
    var categoryImage: String,
    @SerializedName("category_name")
    var categoryName: String
)
