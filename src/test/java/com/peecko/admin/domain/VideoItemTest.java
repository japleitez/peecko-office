package com.peecko.admin.domain;

import static com.peecko.admin.domain.PlayListTestSamples.*;
import static com.peecko.admin.domain.VideoItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VideoItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoItem.class);
        VideoItem videoItem1 = getVideoItemSample1();
        VideoItem videoItem2 = new VideoItem();
        assertThat(videoItem1).isNotEqualTo(videoItem2);

        videoItem2.setId(videoItem1.getId());
        assertThat(videoItem1).isEqualTo(videoItem2);

        videoItem2 = getVideoItemSample2();
        assertThat(videoItem1).isNotEqualTo(videoItem2);
    }

    @Test
    void playListTest() throws Exception {
        VideoItem videoItem = getVideoItemRandomSampleGenerator();
        PlayList playListBack = getPlayListRandomSampleGenerator();

        videoItem.setPlayList(playListBack);
        assertThat(videoItem.getPlayList()).isEqualTo(playListBack);

        videoItem.playList(null);
        assertThat(videoItem.getPlayList()).isNull();
    }
}
