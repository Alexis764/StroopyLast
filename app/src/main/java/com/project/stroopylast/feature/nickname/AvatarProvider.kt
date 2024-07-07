package com.project.stroopylast.feature.nickname

import com.project.stroopylast.R
import javax.inject.Inject

class AvatarProvider @Inject constructor() {

    operator fun invoke(): List<AvatarModel> {
        return listOf(
            AvatarModel(R.drawable.img_1, true),
            AvatarModel(R.drawable.img_2),
            AvatarModel(R.drawable.img_3),
            AvatarModel(R.drawable.img_4),
            AvatarModel(R.drawable.img_5),
            AvatarModel(R.drawable.img_6),
            AvatarModel(R.drawable.img_7),
            AvatarModel(R.drawable.img_8),
            AvatarModel(R.drawable.img_9)
        )
    }

}