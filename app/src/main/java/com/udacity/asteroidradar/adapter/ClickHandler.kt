import com.udacity.asteroidradar.data.model.Asteroid

class ClickHandler(private val clickListener: (asteroid: Asteroid) -> Unit) {

    fun onClick(asteroid: Asteroid) {
        return clickListener(asteroid)
    }
}