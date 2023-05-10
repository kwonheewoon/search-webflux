DROP TABLE IF EXISTS popular_search_keyword;


CREATE TABLE popular_search_keyword (
                                        id SERIAL PRIMARY KEY,
                                        keyword VARCHAR(255) NOT NULL,
                                        search_volume BIGINT NOT NULL
);