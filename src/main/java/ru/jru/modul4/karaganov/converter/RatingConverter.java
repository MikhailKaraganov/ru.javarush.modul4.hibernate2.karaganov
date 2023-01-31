package ru.jru.modul4.karaganov.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.jru.modul4.karaganov.entity.Rating;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {
        return rating.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String s) {
        Rating[] values = Rating.values();
        for (Rating rating : values) {
            if(rating.getValue().equalsIgnoreCase(s)){
                return rating;
            }
        }
        return null;
    }
}
