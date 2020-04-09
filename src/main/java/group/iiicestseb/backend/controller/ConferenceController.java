package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.ConferenceService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/3/25
 */
@RestController
@RequestMapping("/conference")
public class ConferenceController {

    @Resource(name = "Regedit")
    ConferenceService conferenceService;


    public Response getConferenceStatistics() {
        return null;
    }


}
