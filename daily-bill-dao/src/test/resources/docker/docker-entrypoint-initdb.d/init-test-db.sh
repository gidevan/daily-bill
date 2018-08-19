#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE daily-bill-test;
    GRANT ALL PRIVILEGES ON DATABASE daily-bill-test TO postgres;
EOSQL