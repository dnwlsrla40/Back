package com.campuspick.demo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

}
