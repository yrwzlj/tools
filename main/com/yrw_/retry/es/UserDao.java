package com.yrw_.retry.es;

import com.yrw_.retry.es.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserDao extends ElasticsearchRepository<User, Long> {
}
