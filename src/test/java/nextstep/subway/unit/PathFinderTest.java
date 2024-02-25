package nextstep.subway.unit;

import nextstep.subway.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class PathFinderTest {
    private Station 교대역;
    private Station 강남역;
    private Station 양재역;
    private Station 남부터미널역;
    private Station 수내역;
    private Station 정자역;
    private Line 이호선;
    private Line 신분당선;
    private Line 삼호선;

    /**
     * 교대역    --- *2호선* ---   강남역
     * |                        |
     * *3호선*                   *신분당선*
     * |                        |
     * 남부터미널역  --- *3호선* ---   양재
     */
    @BeforeEach
    void setUp() {
        교대역 = new Station("교대역");
        강남역 = new Station("강남역");
        양재역 = new Station("양재역");
        남부터미널역 = new Station("남부터미널역");

        이호선 = Line.builder().name("2호선").color("GREEN").upStation(강남역).downStation(교대역).distance(3L).build();
        신분당선 = Line.builder().name("신분당선").color("RED").upStation(강남역).downStation(양재역).distance(10L).build();
        삼호선 = Line.builder().name("3호선").color("ORANGE").upStation(교대역).downStation(남부터미널역).distance(2L).build();

        삼호선.addSection(Section.builder()
                .line(삼호선)
                .upStation(남부터미널역)
                .downStation(양재역)
                .distance(3L)
                .build());
    }

    @DisplayName("지하철 최단거리를 조회한다.")
    @Test
    void 지하철구간_최단거리_조회() {
        PathFinder pathFinder = new PathFinder();
        Path path = pathFinder.findPath(Arrays.asList(이호선, 신분당선, 삼호선), 교대역, 양재역);

        assertThat(path.getPath()).containsExactly(교대역, 남부터미널역, 양재역);
        assertThat(path.getDistance()).isEqualTo(5);
    }
}
