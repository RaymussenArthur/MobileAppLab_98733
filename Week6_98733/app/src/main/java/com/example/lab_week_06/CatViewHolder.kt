package com.example.lab_week_06

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_06.model.CatModel
import com.example.lab_week_06.model.CatBreed
import com.example.lab_week_06.model.Gender

private const val FEMALE_SYMBOL = "\u2640"
private const val MALE_SYMBOL = "\u2642"
private const val UNKNOWN_SYMBOL = "?"

class CatViewHolder(
    private val containerView: View,
    private val imageLoader: ImageLoader,
    private val onClickListener: CatAdapter.OnClickListener
) : RecyclerView.ViewHolder(containerView) {

    private val catBiographyView: TextView = containerView.findViewById(R.id.cat_biography)
    private val catBreedView: TextView = containerView.findViewById(R.id.cat_breed)
    private val catGenderView: TextView = containerView.findViewById(R.id.cat_gender)
    private val catNameView: TextView = containerView.findViewById(R.id.cat_name)
    private val catPhotoView: ImageView = containerView.findViewById(R.id.cat_photo)

    fun bindData(cat: CatModel) {
        containerView.setOnClickListener { onClickListener.onItemClick(cat) }

        imageLoader.loadImage(cat.imageUrl, catPhotoView)
        catNameView.text = cat.name
        catBiographyView.text = cat.biography

        catBreedView.text = when (cat.breed) {
            CatBreed.AmericanCurl -> "American Curl"
            CatBreed.BalineseJavanese -> "Balinese-Javanese"
            CatBreed.ExoticShorthair -> "Exotic Shorthair"
        }

        catGenderView.text = when (cat.gender) {
            Gender.Female -> FEMALE_SYMBOL
            Gender.Male -> MALE_SYMBOL
            else -> UNKNOWN_SYMBOL
        }
    }
}
