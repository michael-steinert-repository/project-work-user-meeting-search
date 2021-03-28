package de.share_your_idea.user_meeting_search.util;

import de.share_your_idea.user_meeting_search.entity.UserMeetingEntity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.List;

@Converter
public class UserMeetingEntityStringListConverter implements AttributeConverter<List<UserMeetingEntity>, String> {

    @Override
    public String convertToDatabaseColumn(List<UserMeetingEntity> userMeetingEntityList) {
        String userMeetingNameString = "";
        if (userMeetingEntityList != null) {
            for (UserMeetingEntity userMeetingEntity : userMeetingEntityList) {
                userMeetingNameString += userMeetingEntity.getMeetingName() + ";";
            }
        }
        return userMeetingNameString;
    }

    @Override
    public List<UserMeetingEntity> convertToEntityAttribute(String string) {
        return Collections.emptyList();
    }
}
