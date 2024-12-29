package com.yrw_.retry.es.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user", shards = 3, replicas = 1)
public class User {

    @Id
    private Long id;

    @Field(type = FieldType.Auto)
    private String name;

    @Field(type = FieldType.Keyword)
    private Integer age;

    @Field(type = FieldType.Auto)
    private String sex;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    public static void main(String[] args) {
        User user01 = new User(1L, "1", 11, "1");
        User user02 = user01;
        user02.setId(2L);
        System.out.println(user01);

    }
}
