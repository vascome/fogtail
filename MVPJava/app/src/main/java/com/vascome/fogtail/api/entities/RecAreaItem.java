package com.vascome.fogtail.api.entities;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

// This class is immutable, it has correctly implemented hashCode and equals.
// Thanks to AutoValue https://github.com/google/auto/tree/master/value.
@AutoValue
public abstract class RecAreaItem {
    private static final String JSON_PROPERTY_ID = "id";
    private static final String JSON_PROPERTY_IMAGE_PREVIEW_URL = "image_preview_url";
    private static final String JSON_PROPERTY_TITLE = "title";
    private static final String JSON_PROPERTY_SHORT_DESCRIPTION = "short_description";

    @NonNull
    public static Builder builder() {
        return new AutoValue_RecAreaItem.Builder();
    }

    @NonNull
    public static TypeAdapter<RecAreaItem> typeAdapter(Gson gson) {
        return new AutoValue_RecAreaItem.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_ID)
    public abstract String id();

    @NonNull
    @SerializedName(JSON_PROPERTY_IMAGE_PREVIEW_URL)
    public abstract String imagePreviewUrl();

    @NonNull
    @SerializedName(JSON_PROPERTY_TITLE)
    public abstract String title();

    @NonNull
    @SerializedName(JSON_PROPERTY_SHORT_DESCRIPTION)
    public abstract String shortDescription();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder id(@NonNull String id);

        @NonNull
        public abstract Builder imagePreviewUrl(@NonNull String imagePreviewUrl);

        @NonNull
        public abstract Builder title(@NonNull String title);

        @NonNull
        public abstract Builder shortDescription(@NonNull String shortDescription);

        @NonNull
        public abstract RecAreaItem build();
    }
}
