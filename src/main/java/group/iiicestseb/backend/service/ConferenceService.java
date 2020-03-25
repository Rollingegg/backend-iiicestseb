package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Conference;

/**
 * @author jh
 * @date 2020/3/25
 */
public interface ConferenceService {

    /**
     * 根据会议名查找会议
     * @param name 会议名
     * @return 会议PO
     */
    Conference findConferenceByName(String name);

    /**
     * 插入会议
     * @param c 会议po
     */
    void insertConference(Conference c);

}
