package com.example.Simulator.Status;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * Tracking 파라미터
 */
public class OrbitalStatus {

    @Getter
    @ToString
    public static class Track {
        /**
         * 접속 이름
         */
        public String userName;

        /**
         * 추적 모드
        */
        public String track;

        /**
         * TLE 정보
         */
        public String tle;

        /**
         * 피크 신호 추적 시 AZ 구동 Offset
         */
        public float azDynamicOffset;

        /**
         * 피크 신호 추적 시 EL 구동 Offset
         */
        public float elDynamicOffset;

        /**
         * 피크 신호 추적 시 EPH 시간 Offset
         */
        public float epochOffset;
    }

    @Getter
    @ToString
    public static class Request {

        private String entity;

        private Map<String, Object> set;

        private Map<String, Object> request;

        private String responseType;
    }
}
