package com.example.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author lee
 */
public interface StudentRepository extends ElasticsearchRepository<ElasticsearchApplication.Student,String> {

}
