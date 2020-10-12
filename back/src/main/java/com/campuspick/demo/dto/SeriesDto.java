package com.campuspick.demo.dto;

import com.campuspick.demo.domain.entity.Series;
import com.campuspick.demo.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor
@Data
public class SeriesDto {
    private String name;

    private String thumbnail;

    private String url;

    private User user;

    @Builder
    public SeriesDto(String name, String thumbnail, String url, User user){
        Assert.notNull(name, "name must be not null");
        Assert.notNull(user, "user must be not null");

        this.name = name;
        this.thumbnail = thumbnail;
        this.url = url;
        this.user = user;
    }

    public Series SeriesDtoToEntity(){
        Series series = Series.builder()
                .name(this.name)
                .thumbnail(this.thumbnail)
                .url(this.url)
                .user(this.user)
                .build();
        return series;
    }

}
