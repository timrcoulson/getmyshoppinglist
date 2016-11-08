# PRODUCTS schema

# --- !Ups

CREATE TABLE PRODUCTS (
  id            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  ingredient_id BIGINT(20)   NOT NULL,
  name          VARCHAR(255) NOT NULL,
  quantity      BIGINT(20)   NOT NULL,
  unit          VARCHAR(255) NOT NULL,
  price         BIGINT(20)   NOT NULL,
  url           VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE PRODUCT_SOURCES (
  name VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL
);

CREATE TABLE RECIPES (
  id                BIGINT(20)   NOT NULL AUTO_INCREMENT,
  name              VARCHAR(255) NOT NULL,
  link              VARCHAR(255) NOT NULL,
  serves            BIGINT(20)   NOT NULL,
  active            BOOL         NOT NULL,
  alt_cost_per_head BIGINT(20)   NOT NULL,
  img               VARCHAR(255) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  method            VARCHAR(255) NOT NULL,
  prep_time         VARCHAR(255) NOT NULL,
  cooking_time      VARCHAR(255) NOT NULL,
  type              VARCHAR(255) NOT NULL,
  deleted_at        DATETIME     NOT NULL
);

CREATE TABLE RECIPES_INGREDIENTS (
  ingredient_id BIGINT(20)   NOT NULL,
  recipe_id     BIGINT(20)   NOT NULL,
  unit          VARCHAR(255) NOT NULL,
  scales        VARCHAR(255) NOT NULL,
  quantity      BIGINT(20)   NOT NULL
);

CREATE TABLE INGREDIENTS (
  id    BIGINT(20)   NOT NULL AUTO_INCREMENT,
  name  VARCHAR(255) NOT NULL,
  aisle VARCHAR(255) NOT NULL,
  keeps BOOL         NOT NULL,
  exact BOOL         NOT NULL,
  url   VARCHAR(255) NOT NULL
);

# --- !Downs

DROP TABLE PRODUCTS;
DROP TABLE PRODUCT_SOURCES;
DROP TABLE RECIPES;
DROP TABLE INGREDIENTS;
