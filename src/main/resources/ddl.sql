CREATE TABLE IF NOT EXISTS users
(
    PRIMARY KEY (id),
    id       UUID         NOT NULL,
    username VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    email    VARCHAR(128) NOT NULL UNIQUE,
    birthday DATE         NOT NULL,
    avatar   BYTEA        NULL,
    role     VARCHAR(16)  NOT NULL
);

CREATE TABLE IF NOT EXISTS tags
(
    PRIMARY KEY (id),
    id   UUID        NOT NULL,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS tests
(
    PRIMARY KEY (id),
    id          UUID        NOT NULL,
    title       VARCHAR(64) NOT NULL UNIQUE,
    owner_id    UUID        NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    time        INT        NOT NULL,
    create_date TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS questions
(
    PRIMARY KEY (id),
    id      UUID         NOT NULL,
    test_id UUID         NOT NULL,
    FOREIGN KEY (test_id) REFERENCES tests (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    image   BYTEA       NULL,
    text    VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS answers
(
    PRIMARY KEY (id),
    id          UUID         NOT NULL,
    question_id UUID         NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    text        VARCHAR(256) NOT NULL UNIQUE,
    is_correct  BOOLEAN      NOT NULL
);


CREATE TABLE IF NOT EXISTS results
(
    PRIMARY KEY (id),
    id          UUID NOT NULL,
    test_id     UUID NOT NULL,
    FOREIGN KEY (test_id) REFERENCES tests (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    owner_id    UUID NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    group_code  VARCHAR NOT NULL,
    mark        INT  NOT NULL,
    create_date TIMESTAMP
);


CREATE TABLE IF NOT EXISTS test_tag
(
    test_id UUID NOT NULL,
    tag_id  UUID NOT NULL,
    PRIMARY KEY (test_id, tag_id),
    CONSTRAINT fk_test_tag_tests FOREIGN KEY (test_id) REFERENCES tests (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_test_tag_tags FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE ON UPDATE CASCADE
);