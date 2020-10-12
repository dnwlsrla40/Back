package com.campuspick.demo.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
public class PostImage {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "BLOB", nullable = false)
    private String path;

    @Column(name = "file_size")
    @ColumnDefault("0")
    private Long fileSize;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PostImage(String name, String path, Long fileSize, User user) {
        Assert.notNull(name, "name must not be null");
        Assert.notNull(path, "path must not be null");
        Assert.notNull(user, "user must not be null");

        this.name = name;
        this.path = path;
        this.fileSize = fileSize;
        this.user = user;
    }

    // null 값이 들어올 경우 초기화
    @PrePersist
    public void prePersist(){
        this.fileSize = this.fileSize == null ? 0 : this.fileSize;
    }
}
