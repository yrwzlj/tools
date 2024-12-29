package com.yrw_.retry.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yrw_.retry.es.entity.User;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

@Data
public class EsTest {

    private final String es;

    public EsTest(String es) {
        this.es = es;
    }



    public static void main(String[] args) {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("120.79.176.136", 9200, "http"))
        );

        EsTest esTest = new EsTest("1");
        String es1 = esTest.getEs();

        try {
            //createIndex(esClient, "user01");
            //getIndex(esClient, "user01");
            //delIndex(esClient, "user01");

            //InsertContent(esClient);
            //updateContent(esClient,1);
            //getContent(esClient, "user01");
            //BulkInsertContent(esClient);
            searchContent(esClient);

            esClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }

    /**
     * 插入文档
     */
    public static void searchContent(RestHighLevelClient esClient) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user01");
        //searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        // 条件筛选
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(QueryBuilders.matchQuery("age","20"));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("name","sb"));
        // 范围查询
        //QueryBuilders.rangeQuery();
        // 模糊查询
        //QueryBuilders.fuzzyQuery();

        // 结果筛选
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 分页
        sourceBuilder.from(0);
        sourceBuilder.size(2);
        // 排序
//        sourceBuilder.sort("age", SortOrder.DESC);
        // 选取字段
//        String[] exclude = {"age"};
//        String[] include = {"name"};
//        sourceBuilder.fetchSource(include,exclude);
        // 高亮查询
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags();
//        highlightBuilder.postTags();
//        highlightBuilder.field();
//        sourceBuilder.highlighter();

        // AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group").field("age");
        sourceBuilder.aggregation(termsAggregationBuilder);

        searchRequest = searchRequest.source(sourceBuilder);

        SearchResponse search = esClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregation maxAge = search.getAggregations().asMap().get("maxAge");

        SearchHits hits = search.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }
    }


    /**
     * 插入文档
     */
    public static void BulkInsertContent(RestHighLevelClient esClient) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new IndexRequest().index("user01").id("1007").source(XContentType.JSON, "name", "qw", "age", "40"));
        bulkRequest.add(new IndexRequest().index("user01").id("1005").source(XContentType.JSON, "name", "as", "age", "20"));
        bulkRequest.add(new IndexRequest().index("user01").id("1006").source(XContentType.JSON, "name", "zx", "age", "20"));

        BulkResponse bulk = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.getTook());
        System.out.println(bulk.getItems());
    }

    /**
     * 插入文档
     */
    public static void InsertContent(RestHighLevelClient esClient) throws IOException {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user01").id("1001");

        User user01 = new User(1L,"zs", 21, "男");

        ObjectMapper objectMapper = new ObjectMapper();
        String user01Json = objectMapper.writeValueAsString(user01);

        indexRequest.source(user01Json, XContentType.JSON);

        IndexResponse index = esClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index);
    }

    /**
     * 更改文档
     */
    public static void updateContent(RestHighLevelClient esClient, Object o) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user01").id("1001");

        updateRequest.doc(XContentType.JSON, "name", "ls");

        UpdateResponse update = esClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.getGetResult());
    }

    /**
     * 查找文档
     */
    public static void getContent(RestHighLevelClient esClient, String indexName) throws IOException {
        GetRequest getRequest = new GetRequest();
        getRequest.index(indexName).id("1001");

        GetResponse getResponse = esClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
    }

    /**
     * 创建索引
     * @param esClient
     * @throws IOException
     */
    public static void createIndex(RestHighLevelClient esClient, String indexName) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = esClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }


    /**
     * 查询索引
     * @param esClient
     * @throws IOException
     */
    public static void delIndex(RestHighLevelClient esClient, String indexName) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);

        AcknowledgedResponse delete = esClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.toString());
    }
}
