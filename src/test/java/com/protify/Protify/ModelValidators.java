package com.protify.Protify;

import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.User;
import org.assertj.core.api.SoftAssertions;

public class ModelValidators {
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
