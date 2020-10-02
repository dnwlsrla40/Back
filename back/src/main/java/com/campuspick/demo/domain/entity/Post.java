package com.campuspick.demo.domain.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @Column
    private int seriesIndex = 0;

    @Column(length = 100)
    private String url;

//    @Column(length = 150)
//    private String shortDescription;
//
//    @Lob
//    @Column
//    private Blob thumbnail;
//
//    @Column
//    private int likes = 0;
//
//    @Column
//    private int views = 0;
//
//    @Column(columnDefinition = "TINYINT")
//    private boolean isTemp = true;
//
//    @Column(columnDefinition = "TINYINT")
//    private boolean isPrivate = false;
//
//    @Column(columnDefinition = "TIMESTAMP")
//    private LocalDateTime releasedAt = LocalDateTime.now();

    @Builder
    public Post(String title, String body, User user, Series series, int seriesIndex, String url) {
        this.title = title;
        this.body = body;
        this.user = user;
        this.series = series;
        this.seriesIndex = seriesIndex;
//        this.shortDescription = shortDescription;
//        this.thumbnail = thumbnail;
        this.url = url;
    }

    public void updateSeries(Series series, int seriesIndex){
        this.seriesIndex = seriesIndex;
        this.series = series;
    }

}
