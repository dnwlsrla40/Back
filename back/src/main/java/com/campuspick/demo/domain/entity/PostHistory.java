package com.campuspick.demo.domain.entity;

import com.campuspick.demo.dto.PostHistoryDto;
import com.campuspick.demo.util.TagHistoryUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class PostHistory {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", length = 100, nullable = false)
    private String body;

    @Column(columnDefinition = "TEXT", unique = true)
    private String url;

    // 작성완료("0" : 임시저장글, "1" : 작성완료)
    @Column(columnDefinition = "TINYINT", name = "is_release")
    @ColumnDefault("0")
    private Boolean isRelease;

    @Column
    private String tagList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PostHistory(String title, String body, String tagList, User user) {
        Assert.notNull(title, "title must not be null");
        Assert.notNull(body, "body must not be null");
        Assert.notNull(user, "user must not be null");

        this.title = title;
        this.body = body;
        this.tagList = tagList;
        this.user = user;
    }

    @PrePersist
    public void prePersist(){
        this.url = this.url == null ? "" : this.url;
    }

    public PostHistoryDto.PostHistoryCreateResponseDto postHistoryToPostHistoryCreateResponseDto(){

        List<TagHistoryUtil> tagList = new ArrayList<>();

        if(this.tagList != null){
            String[] tempTagList = this.tagList.split(",");

            for (int i = 0; i<tempTagList.length; i++) {
                TagHistoryUtil tagHistory = TagHistoryUtil.builder()
                        .name(tempTagList[i])
                        .build();
                tagList.add(tagHistory);
            }
        }

        PostHistoryDto.PostHistoryCreateResponseDto response = PostHistoryDto.PostHistoryCreateResponseDto.builder()
                .title(this.getTitle())
                .body(this.getBody())
                .url(this.getUrl())
                .tagList(tagList)
                .build();

        return response;
    }
}
