# SCHEMA

# --- !Ups

ALTER TABLE "recipes_ingredients"
  DROP COLUMN scales;

# --- !Downs

ALTER TABLE "recipes_ingredients"
  ADD COLUMN scales VARCHAR(255) NOT NULL;
