package com.campuspick.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
@Getter
@NoArgsConstructor
public class Posts {

    @Id
    private ObjectId id;

    private String author;

    private String context;

    private String title;

    @Builder
    public Posts(String author, String context, String title){
        this.author = author;
        this.context = context;
        this.title = title;
    }
}
