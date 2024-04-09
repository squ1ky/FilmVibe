CREATE TABLE IF NOT EXISTS Users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(64) NOT NULL,
    login VARCHAR(64) NOT NULL,
    name VARCHAR(64) NOT NULL,
    birthday DATE
);

CREATE TABLE IF NOT EXISTS Films (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS Films_Info (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    genre VARCHAR(64),
    mpa VARCHAR(10),
    release_date DATE,
    duration TIME,

    FOREIGN KEY (id) REFERENCES Films (id)
);

CREATE TABLE IF NOT EXISTS Film_Likes(
    id BIGINT,
    likes_quantity BIGINT,

    FOREIGN KEY (id) REFERENCES Films (id)
);

CREATE TABLE IF NOT EXISTS Film_Liked_By(
    film_id BIGINT,
    user_id BIGINT,

    FOREIGN KEY (film_id) REFERENCES Films (id),
    FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE TABLE IF NOT EXISTS Friends(
    user_id BIGINT,
    friend_id BIGINT,
    is_accepted BOOL NOT NULL,

    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (friend_id) REFERENCES Users (id)
);