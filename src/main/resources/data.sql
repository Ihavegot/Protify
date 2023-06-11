INSERT INTO "user" (email, login, password) VALUES
('john@example.com', 'john123', 'password123'),
('alice@example.com', 'alice123', 'password123'),
('bob@example.com', 'bob123', 'password123'),
('jane@example.com', 'jane123', 'password123'),
('mark@example.com', 'mark123', 'password123'),
('sarah@example.com', 'sarah123', 'password123'),
('peter@example.com', 'peter123', 'password123'),
('emily@example.com', 'emily123', 'password123'),
('david@example.com', 'david123', 'password123'),
('anna@example.com', 'anna123', 'password123'),
('mike@example.com', 'mike123', 'password123'),
('amy@example.com', 'amy123', 'password123'),
('tom@example.com', 'tom123', 'password123'),
('lisa@example.com', 'lisa123', 'password123'),
('eric@example.com', 'eric123', 'password123'),
('chris@example.com', 'chris123', 'password123'),
('kate@example.com', 'kate123', 'password123'),
('julia@example.com', 'julia123', 'password123'),
('sam@example.com', 'sam123', 'password123'),
('olivia@example.com', 'olivia123', 'password123'),
('max@example.com', 'max123', 'password123'),
('lucas@example.com', 'lucas123', 'password123'),
('leo@example.com', 'leo123', 'password123'),
('emma@example.com', 'emma123', 'password123'),
('jake@example.com', 'jake123', 'password123'),
('matt@example.com', 'matt123', 'password123'),
('jennifer@example.com', 'jennifer123', 'password123'),
('ava@example.com', 'ava123', 'password123'),
('benjamin@example.com', 'benjamin123', 'password123'),
('daniel@example.com', 'daniel123', 'password123'),
('victoria@example.com', 'victoria123', 'password123'),
('natalie@example.com', 'natalie123', 'password123'),
('alex@example.com', 'alex123', 'password123'),
('anna@example.com', 'anna123', 'password123'),
('maya@example.com', 'maya123', 'password123'),
('paul@example.com', 'paul123', 'password123'),
('jason@example.com', 'jason123', 'password123'),
('george@example.com', 'george123', 'password123'),
('sophia@example.com', 'sophia123', 'password123'),
('lily@example.com', 'lily123', 'password123'),
('owen@example.com', 'owen123', 'password123'),
('jacob@example.com', 'jacob123', 'password123'),
('zoey@example.com', 'zoey123', 'password123'),
('charlotte@example.com', 'charlotte123', 'password123'),
('violet@example.com', 'violet123', 'password123'),
('liam@example.com', 'liam123', 'password123'),
('ella@example.com', 'ella123', 'password123'),
('avery@example.com', 'avery123', 'password123'),
('madison@example.com', 'madison123', 'password123'),
('jayden@example.com', 'jayden123', 'password123');

INSERT INTO artist (artist_name, name, surname) VALUES
                                                    ('AC/DC', 'Malcolm', 'Young'),
                                                    ('AC/DC', 'Angus', 'Young'),
                                                    ('The Beatles', 'John', 'Lennon'),
                                                    ('The Beatles', 'Paul', 'McCartney'),
                                                    ('The Beatles', 'George', 'Harrison'),
                                                    ('The Beatles', 'Ringo', 'Starr'),
                                                    ('Queen', 'Freddie', 'Mercury'),
                                                    ('Queen', 'Brian', 'May'),
                                                    ('Queen', 'John', 'Deacon'),
                                                    ('Queen', 'Roger', 'Taylor'),
                                                    ('Michael Jackson', 'Michael', 'Jackson'),
                                                    ('Madonna', 'Madonna', 'Ciccone'),
                                                    ('Prince', 'Prince', 'Rogers Nelson'),
                                                    ('David Bowie', 'David', 'Bowie'),
                                                    ('Elvis Presley', 'Elvis', 'Presley'),
                                                    ('Led Zeppelin', 'Jimmy', 'Page'),
                                                    ('Led Zeppelin', 'Robert', 'Plant'),
                                                    ('Led Zeppelin', 'John', 'Bonham'),
                                                    ('Led Zeppelin', 'John Paul', 'Jones'),
                                                    ('Pink Floyd', 'David', 'Gilmour'),
                                                    ('Pink Floyd', 'Roger', 'Waters'),
                                                    ('Pink Floyd', 'Syd', 'Barrett'),
                                                    ('Nirvana', 'Kurt', 'Cobain'),
                                                    ('Nirvana', 'Krist', 'Novoselic'),
                                                    ('Nirvana', 'Dave', 'Grohl'),
                                                    ('Metallica', 'James', 'Hetfield'),
                                                    ('Metallica', 'Lars', 'Ulrich'),
                                                    ('Metallica', 'Kirk', 'Hammett'),
                                                    ('Metallica', 'Robert', 'Trujillo'),
                                                    ('The Rolling Stones', 'Mick', 'Jagger'),
                                                    ('The Rolling Stones', 'Keith', 'Richards'),
                                                    ('The Rolling Stones', 'Charlie', 'Watts'),
                                                    ('The Rolling Stones', 'Ronnie', 'Wood'),
                                                    ('U2', 'Bono', 'Vox'),
                                                    ('U2', 'The Edge', 'Evans'),
                                                    ('U2', 'Adam', 'Clayton'),
                                                    ('U2', 'Larry', 'Mullen Jr.'),
                                                    ('Pearl Jam', 'Eddie', 'Vedder'),
                                                    ('Pearl Jam', 'Stone', 'Gossard'),
                                                    ('Pearl Jam', 'Jeff', 'Ament'),
                                                    ('Pearl Jam', 'Mike', 'McCready'),
                                                    ('Green Day', 'Billie', 'Joe Armstrong'),
                                                    ('Green Day', 'Mike', 'Dirnt'),
                                                    ('Green Day', 'Tré', 'Cool'),
                                                    ('Radiohead', 'Thom', 'Yorke'),
                                                    ('Radiohead', 'Jonny', 'Greenwood'),
                                                    ('Radiohead', 'Colin', 'Greenwood'),
                                                    ('Radiohead', 'Philip', 'Selway'),
                                                    ('The Doors', 'Jim', 'Morrison'),
                                                    ('The Doors', 'Ray', 'Manzarek'),
                                                    ('The Doors', 'Robby', 'Krieger'),
                                                    ('The Doors', 'John', 'Densmore');

--dla tabeli Songs
INSERT INTO songs (title, artist, song_file) VALUES
                                                 ('Shape of You', 1, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Thinking Out Loud', 1, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Perfect', 1, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Photograph', 1, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Castle on the Hill', 1, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Someone Like You', 2, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Hello', 2, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Rolling in the Deep', 2, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Set Fire to the Rain', 2, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Skyfall', 2, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Thriller', 3, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Beat It', 3, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Billie Jean', 3, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Smooth Criminal', 3, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Black or White', 3, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Bohemian Rhapsody', 4, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('We Will Rock You', 4, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Don''t Stop Me Now', 4, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Somebody to Love', 4, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Another One Bites the Dust', 4, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Livin'' on a Prayer', 5, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('You Give Love a Bad Name', 5, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Wanted Dead or Alive', 5, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('It''s My Life', 5, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Always', 5, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Stairway to Heaven', 6, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Whole Lotta Love', 6, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Immigrant Song', 6, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Black Dog', 6, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Kashmir', 6, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Smells Like Teen Spirit', 7, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Come as You Are', 7, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Lithium', 7, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Heart-Shaped Box', 7, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('In Bloom', 7, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Californication', 8, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Under the Bridge', 8, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Scar Tissue', 8, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Give It Away', 8, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Can''t Stop', 8, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Lose Yourself', 9, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Without Me', 9, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('The Real Slim Shady', 9, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Love the Way You Lie', 9, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Mockingbird', 9, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('What''s My Name?', 10, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Umbrella', 10, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Diamonds', 10, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('Work', 10, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'),
                                                 ('We Found Love', 10, 'https://www.youtube.com/watch?v=dQw4w9WgXcQ');

INSERT INTO playlist (user_id, title) VALUES (1, 'Playlist 1');
INSERT INTO playlist (user_id, title) VALUES (2, 'Playlist 2');
INSERT INTO playlist (user_id, title) VALUES (3, 'Playlist 3');
INSERT INTO playlist (user_id, title) VALUES (4, 'Playlist 4');
INSERT INTO playlist (user_id, title) VALUES (5, 'Playlist 5');
INSERT INTO playlist (user_id, title) VALUES (6, 'Playlist 6');
INSERT INTO playlist (user_id, title) VALUES (7, 'Playlist 7');
INSERT INTO playlist (user_id, title) VALUES (8, 'Playlist 8');
INSERT INTO playlist (user_id, title) VALUES (9, 'Playlist 9');
INSERT INTO playlist (user_id, title) VALUES (10, 'Playlist 10');
INSERT INTO playlist (user_id, title) VALUES (11, 'Playlist 11');
INSERT INTO playlist (user_id, title) VALUES (12, 'Playlist 12');
INSERT INTO playlist (user_id, title) VALUES (13, 'Playlist 13');
INSERT INTO playlist (user_id, title) VALUES (14, 'Playlist 14');
INSERT INTO playlist (user_id, title) VALUES (15, 'Playlist 15');
INSERT INTO playlist (user_id, title) VALUES (16, 'Playlist 16');
INSERT INTO playlist (user_id, title) VALUES (17, 'Playlist 17');
INSERT INTO playlist (user_id, title) VALUES (18, 'Playlist 18');
INSERT INTO playlist (user_id, title) VALUES (19, 'Playlist 19');
INSERT INTO playlist (user_id, title) VALUES (20, 'Playlist 20');
INSERT INTO playlist (user_id, title) VALUES (21, 'Playlist 21');
INSERT INTO playlist (user_id, title) VALUES (22, 'Playlist 22');
INSERT INTO playlist (user_id, title) VALUES (23, 'Playlist 23');
INSERT INTO playlist (user_id, title) VALUES (24, 'Playlist 24');
INSERT INTO playlist (user_id, title) VALUES (25, 'Playlist 25');
INSERT INTO playlist (user_id, title) VALUES (26, 'Playlist 26');
INSERT INTO playlist (user_id, title) VALUES (27, 'Playlist 27');
INSERT INTO playlist (user_id, title) VALUES (28, 'Playlist 28');
INSERT INTO playlist (user_id, title) VALUES (29, 'Playlist 29');
INSERT INTO playlist (user_id, title) VALUES (30, 'Playlist 30');
INSERT INTO playlist (user_id, title) VALUES (31, 'Playlist 31');
INSERT INTO playlist (user_id, title) VALUES (32, 'Playlist 32');
INSERT INTO playlist (user_id, title) VALUES (33, 'Playlist 33');
INSERT INTO playlist (user_id, title) VALUES (34, 'Playlist 34');
INSERT INTO playlist (user_id, title) VALUES (35, 'Playlist 35');
INSERT INTO playlist (user_id, title) VALUES (36, 'Playlist 36');
INSERT INTO playlist (user_id, title) VALUES (37, 'Playlist 37');
INSERT INTO playlist (user_id, title) VALUES (38, 'Playlist 38');
INSERT INTO playlist (user_id, title) VALUES (39, 'Playlist 39');
INSERT INTO playlist (user_id, title) VALUES (40, 'Playlist 40');
INSERT INTO playlist (user_id, title) VALUES (41, 'Playlist 41');
INSERT INTO playlist (user_id, title) VALUES (42, 'Playlist 42');
INSERT INTO playlist (user_id, title) VALUES (43, 'Playlist 43');
INSERT INTO playlist (user_id, title) VALUES (44, 'Playlist 44');
INSERT INTO playlist (user_id, title) VALUES (45, 'Playlist 45');
INSERT INTO playlist (user_id, title) VALUES (46, 'Playlist 46');
INSERT INTO playlist (user_id, title) VALUES (47, 'Playlist 47');
INSERT INTO playlist (user_id, title) VALUES (48, 'Playlist 48');
INSERT INTO playlist (user_id, title) VALUES (49, 'Playlist 49');
INSERT INTO playlist (user_id, title) VALUES (50, 'Playlist 50');

--many to many
INSERT INTO playlist_songs (playlists_id, songs_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5),
(2, 6),
(3, 1),
(3, 3),
(3, 5),
(4, 2),
(4, 4),
(4, 6),
(5, 1),
(5, 4),
(5, 5),
(6, 2),
(6, 3),
(6, 6),
(7, 1),
(7, 2),
(7, 5),
(8, 3),
(8, 4),
(8, 6),
(9, 1),
(9, 6),
(10, 2),
(10, 4),
(10, 5);
INSERT INTO Score (user_id, songs_id, score) VALUES
                                                 (7, 28, 4.5),
                                                 (2, 14, 3.2),
                                                 (1, 49, 4.8),
                                                 (3, 20, 2.7),
                                                 (4, 31, 4.1),
                                                 (6, 43, 3.9),
                                                 (5, 26, 3.6),
                                                 (3, 37, 2.3),
                                                 (7, 9, 4.7),
                                                 (2, 22, 3.8),
                                                 (1, 46, 4.5),
                                                 (4, 5, 3.1),
                                                 (6, 40, 3.6),
                                                 (5, 16, 2.9),
                                                 (3, 33, 2.5),
                                                 (7, 47, 4.2),
                                                 (2, 24, 3.5),
                                                 (1, 41, 4.9),
                                                 (4, 29, 3.7),
                                                 (6, 19, 3.4),
                                                 (5, 13, 2.2),
                                                 (3, 48, 2.8),
                                                 (7, 12, 4.3),
                                                 (2, 42, 3.3),
                                                 (1, 27, 4.4),
                                                 (4, 39, 3.0),
                                                 (6, 21, 3.7),
                                                 (5, 8, 2.6),
                                                 (3, 25, 2.9),
                                                 (7, 36, 4.1),
                                                 (2, 44, 3.8),
                                                 (1, 15, 4.5),
                                                 (4, 30, 3.3),
                                                 (6, 23, 3.5),
                                                 (5, 18, 2.8),
                                                 (3, 11, 2.2),
                                                 (7, 45, 4.6),
                                                 (2, 35, 3.6),
                                                 (1, 10, 4.9),
                                                 (4, 50, 3.4),
                                                 (6, 17, 3.1),
                                                 (5, 32, 2.4),
                                                 (3, 38, 2.7),
                                                 (7, 6, 4.0),
                                                 (2, 1, 3.9),
                                                 (1, 4, 4.7),
                                                 (4, 2, 3.2),
                                                 (6, 3, 3.8),
                                                 (5, 7, 2.6),
                                                 (3, 34, 2.5);