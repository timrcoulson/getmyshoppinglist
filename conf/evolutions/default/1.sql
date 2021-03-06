# Schema

# --- !Ups

CREATE TABLE "products" (
  id            UUID PRIMARY KEY,
  ingredient_id UUID NOT NULL,
  name     VARCHAR(255) NOT NULL,
  quantity BIGINT       NOT NULL,
  unit     VARCHAR(255) NOT NULL,
  price    BIGINT       NOT NULL,
  url      VARCHAR(255) NOT NULL
);

CREATE TABLE "product_sources" (
  id            UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  url  VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL
);

CREATE TABLE "recipes" (
  id           UUID PRIMARY KEY,
  name              VARCHAR(255) NOT NULL,
  link              VARCHAR(255) NOT NULL,
  serves            BIGINT       NOT NULL,
  active            BOOL         NOT NULL,
  img               VARCHAR(255) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  method            TEXT NOT NULL,
  prep_time         VARCHAR(255) NOT NULL,
  cooking_time      VARCHAR(255) NOT NULL,
  type              VARCHAR(255) NOT NULL,
  deleted_at        TIMESTAMP(0)
);

CREATE TABLE "recipes_ingredients" (
  ingredient_id UUID NOT NULL,
  recipe_id     UUID NOT NULL,
  unit     VARCHAR(255) NOT NULL,
  scales   VARCHAR(255) NOT NULL,
  quantity BIGINT       NOT NULL
);

CREATE TABLE "ingredients" (
  id    UUID PRIMARY KEY,
  name  VARCHAR(255) NOT NULL,
  aisle VARCHAR(255) NOT NULL,
  keeps BOOL         NOT NULL,
  exact BOOL         NOT NULL
);

# --- !Downs

DROP TABLE "products";
DROP TABLE "product_sources";
DROP TABLE "recipes";
DROP TABLE "ingredients";
DROP TABLE "recipes_ingredients;
