package de.share_your_idea.user_meeting_search.util;

import de.share_your_idea.user_meeting_search.entity.UserEntity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.List;

@Converter
public class UserEntityStringListConverter implements AttributeConverter<List<UserEntity>, String> {

    @Override
    public String convertToDatabaseColumn(List<UserEntity> userEntityList) {
        String userEntityString = "";
        if (userEntityList != null) {
            for (UserEntity userEntity : userEntityList) {
                userEntityString += userEntity.getUsername() + "," + userEntity.getUserRole() + "," + userEntity.getAuthorizationToken() + ";";
            }
        }
        return userEntityString;
    }

    @Override
    public List<UserEntity> convertToEntityAttribute(String string) {
        return Collections.emptyList();
    }
}
