package ihanoi.ihanoi_backend.functions;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.StandardBasicTypes;

public class PostgresFullTextSearchContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry().registerPattern(
                "fts",
                "to_tsvector('simple', immutable_unaccent(?1)) @@ phraseto_tsquery('simple', immutable_unaccent(?2))",
                functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(StandardBasicTypes.BOOLEAN)
        );
    }
}