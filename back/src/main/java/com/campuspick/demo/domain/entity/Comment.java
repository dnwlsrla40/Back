package com.campuspick.demo.domain.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

//    @ManyToOne
//    @JoinColumn(name = "reply_to")
//    private Comment replyTo;

    @OneToMany
    @JoinColumn
    private List<Comment> reComment;

//    @Column
//    private int level;

//    @Column(columnDefinition = "TINYINT")
//    private boolean hasReplies = false;

    @Column(columnDefinition = "TINYINT")
    private boolean isRoot;

    @Column(columnDefinition = "TINYINT")
    private boolean deleted = false;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime wroteAt = LocalDateTime.now();

    @Builder
    public Comment(Post post, User user, String text, boolean isRoot) {
        this.post = post;
        this.user = user;
        this.text = text;
        this.isRoot = isRoot;
//        this.replyTo = replyTo;
//        this.level = level;

    }

}
