package com.protify.Protify;

import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import org.assertj.core.api.SoftAssertions;

public class ModelValidators {


    public static void validateArtist(SoftAssertions softly, Artist actual, Artist expected){
        softly.assertThat(actual.getId()).isEqualTo(expected.getId());
        softly.assertThat(actual.getArtistName()).isEqualTo(expected.getArtistName());
        softly.assertThat(actual.getName()).isEqualTo(expected.getName());
        softly.assertThat(actual.getSurname()).isEqualTo(expected.getSurname());
        softly.assertThat(actual.getSongs()).isNull();
    }

    public  static  void validateSongs(SoftAssertions softly, Songs actual, Songs expected){
        softly.assertThat(actual.getId()).isEqualTo(expected.getId());
        softly.assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        softly.assertThat(actual.getArtist()).isNull();
        softly.assertThat(actual.getPlaylists()).isNull();
        softly.assertThat(actual.getSongFile()).isNull();
    }


  
    public static void validateUser(SoftAssertions softly, User actual, User expected) {
        softly.assertThat(actual.getId()).isEqualTo(expected.getId());
        softly.assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        softly.assertThat(actual.getLogin()).isEqualTo(expected.getLogin());
        softly.assertThat(actual.getPassword()).isNull();
        softly.assertThat(actual.getPlaylists()).isNull();
    }


    public static void validatePlaylist(SoftAssertions softly, Playlist actual, Playlist expected) {
        softly.assertThat(actual.getId()).isEqualTo(expected.getId());
        softly.assertThat(actual.getUser()).isNull();
        softly.assertThat(actual.getSongs()).isNull();

    }
}
