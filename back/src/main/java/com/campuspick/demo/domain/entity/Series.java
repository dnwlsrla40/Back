package com.campuspick.demo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Series {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50, nullable = false)
    private String name;

    @Lob
    @Column
    private Blob thumbnail;

    @Column(length = 100, nullable = false)
    private String seriesUrl;

    @Builder
    public Series(User user, String name, Blob thumbnail, String url){
        this.user = user;
        this.name = name;
        this.thumbnail = thumbnail;
        this.seriesUrl = url;
    }

}
