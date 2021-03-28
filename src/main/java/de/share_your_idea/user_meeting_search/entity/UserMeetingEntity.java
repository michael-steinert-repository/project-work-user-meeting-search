package de.share_your_idea.user_meeting_search.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/*
Entity contains only the Properties specific to the Domain.
There is no Relation between User and Meeting, because it is not needed in the Domain of User-Meeting-Search.
Target of the DDD is an Entity customized to their Domain.
*/

@Entity(name = "MeetingEntity")
@Table(name = "meeting_entity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserMeetingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "meeting_id",
            updatable = false
    )
    private Long meetingId;

    @Column(
            name = "meeting_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotBlank(message = "MeetingName must be not empty")
    private String meetingName;

    @Column(
            name = "communication_link",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotBlank(message = "CommunicationLink must be not empty")
    private String communicationLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMeetingEntity that = (UserMeetingEntity) o;
        return Objects.equals(meetingName, that.meetingName) && Objects.equals(communicationLink, that.communicationLink);
    }
}
