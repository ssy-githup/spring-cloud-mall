package ai.ssy.vo;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.util.List;

public class GatewayDefine {

    private String id;

    private String uri;

    private String predicates;

    private String filters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPredicates() {
        return this.predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }

    public List<PredicateDefinition> getPredicateDefinition() {
        if (this.predicates != null) {
            List<PredicateDefinition> predicateDefinitionList = JSON.parseArray(this.predicates, PredicateDefinition.class);
            return predicateDefinitionList;
        } else {
            return null;
        }
    }

    public String getFilters() {
        return filters;
    }

    public List<FilterDefinition> getFilterDefinition() {
        if (this.filters != null &&!"".equals(this.filters.trim())) {
            List<FilterDefinition> filterDefinitionList = JSON.parseArray(this.filters, FilterDefinition.class);
            return filterDefinitionList;
        } else {
            return null;
        }
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "GatewayDefine{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", predicates='" + predicates + '\'' +
                ", filters='" + filters + '\'' +
                '}';
    }
}
