package com.campuspick.demo.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
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

    @Column(name = "series_index")
    private Integer seriesIndex = 0;

    @Column(length = 100)
    private String url;

    @Column(length = 150, name = "short_description")
    private String shortDescription;

    @Lob
    @Column(columnDefinition = "BLOB")
    private String thumbnail;

    @Column
    @ColumnDefault("0")
    private Integer likes = 0;

    @Column
    @ColumnDefault("0")
    private Integer views = 0;

    @Column(name = "is_private", columnDefinition = "TINYINT")
    @ColumnDefault("0")
    private Boolean isPrivate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "released_at")
    private Date releasedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @Builder
    public Post(String title, String body, User user, Series series, int seriesIndex, String shortDescription, String thumbnail, String url, Boolean isPrivate) {
        this.title = title;
        this.body = body;
        this.seriesIndex = seriesIndex;
        this.shortDescription = shortDescription;
        this.thumbnail = thumbnail;
        this.url = url;
        this.isPrivate = isPrivate!=null && isPrivate;
        this.series = series;
        this.user = user;
    }

    // null 값이 들어올 경우 초기화
    @PrePersist
    public void prePersist(){
        this.likes = this.likes == null ? 0 : this.likes;
        this.views = this.views == null ? 0 : this.views;
        this.isPrivate = this.isPrivate == null ? false : this.isPrivate;
        this.url = this.url == null ? "/@"+this.user.getUsername()+"/"+this.title : this.url;
        this.shortDescription = this.shortDescription == null ? (this.body.length() > 150 ? this.body.substring(0,150) : this.body) : this.shortDescription;
        this.seriesIndex = this.seriesIndex == null ? 0 : this.seriesIndex;
        this.releasedAt = this.releasedAt == null ? new Date(): this.releasedAt;
    }

    public void updateSeries(Series series, int seriesIndex){
        this.seriesIndex = seriesIndex;
        this.series = series;
    }
}
